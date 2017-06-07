package ru.javawebinar.topjava.service;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.JpaUtil;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

/**
 * Created by jerom on 07.06.2017.
 */
public class AbstractOrmUserServiceTest extends AbstractUserServiceTest {
    @Autowired
    protected JpaUtil jpaUtil;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void testValidation() throws Exception {
        Assume.assumeTrue(environment.acceptsProfiles(Profiles.DATAJPA, Profiles.JPA));
        validateRootCause(() -> service.save(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "User", "mail@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "User", "mail@yandex.ru", "password", 9, true, Collections.emptySet())), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "User", "mail@yandex.ru", "password", 10001, true, Collections.emptySet())), ConstraintViolationException.class);
    }
}
