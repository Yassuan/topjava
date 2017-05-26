package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);
    @Autowired
    private MealService service;

    public  List<MealWithExceed> getAll()  {
        int userId = AuthorizedUser.id();
        LOG.info("get all for user: " + userId);
        return MealsUtil.getFilteredWithExceeded(service.getAll(userId), LocalTime.MIN, LocalTime.MAX, AuthorizedUser.getCaloriesPerDay());
    }

    public Meal save(Meal meal) {
        int userId = AuthorizedUser.id();
        LOG.info("save " + meal + " user: " + userId);
        return service.save(userId, meal);
    }

    public void update(Meal meal) {
        int userId = AuthorizedUser.id();
        LOG.info("update " + meal + " user: " + userId);
        service.update(userId, meal);
    }

    public void delete(int id) {
        int userId = AuthorizedUser.id();
        LOG.info(" delete " + id + " user: " + userId);
        service.delete(userId, id);
    }

    public Meal get(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("get " + id + " user: " + userId);
        return service.get(userId, id);
    }

}