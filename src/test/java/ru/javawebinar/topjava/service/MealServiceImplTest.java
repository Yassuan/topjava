package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by jerom on 01.06.2017.
 */
@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)

public class MealServiceImplTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator populator;

    @Before
    public void setUp() {
        populator.execute();
    }
    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> actual = service.getBetweenDateTimes(USER_MEAL_1.getDateTime(), USER_MEAL_2.getDateTime(), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL_2, USER_MEAL_1), actual);
    }
    @Test
    public void get() throws Exception {
        MATCHER.assertEquals(USER_MEAL_1, service.get(USER_MEAL_1.getId(), USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        MATCHER.assertEquals(USER_MEAL_1, service.get(100, USER_ID));
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_MEAL_1.getId(), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(100002, ADMIN_ID);
    }


    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(USER_MEALS, service.getAll(USER_ID));
    }

    @Test
    public void update() throws Exception {
        USER_MEAL_6.setDescription("updated Meal_1");
        USER_MEAL_6.setCalories(10000);
        service.update(USER_MEAL_6, USER_ID);

    }

    @Test
    public void save() throws Exception {
        Meal meal = new Meal(LocalDateTime.now(), "new Admin Meal", 150);
        service.save(meal, ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(meal, ADMIN_MEAL_2, ADMIN_MEAL_1), service.getAll(ADMIN_ID));
    }

}