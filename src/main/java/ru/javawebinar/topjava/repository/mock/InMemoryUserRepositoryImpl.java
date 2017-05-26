package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private static final Comparator<User> USER_NAME = Comparator.comparing(User::getName).thenComparing(User::getEmail);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger(0);
    //init
    {
        save(new User(2, "Yassen", "some.yandex.ru", "password", Role.ROLE_USER));
        save(new User(3, "YAdmin", "admin.gmail.ru", "Apassword", Role.ROLE_ADMIN));
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if(user.isNew()) {
            user.setId(id.incrementAndGet());
        }
        return repository.put(user.getId(), user);
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        List<User> users = new ArrayList<>(repository.values());
        users.sort(USER_NAME);
        return users;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return repository.values().stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }
}
