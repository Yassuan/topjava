package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Map<Integer,Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger(0);
    private static final Comparator<Meal> Meal_TIME = Comparator.comparing(Meal::getDateTime).reversed();

    {
       for(Meal meal: MealsUtil.MEALS) {
           save(2, meal);
       }
    }


    @Override
    public Meal save(int userId, Meal meal) {
        LOG.info("save " + meal);
        Map<Integer, Meal> meals;
        if(repository.get(userId) == null) {
            meals = new ConcurrentHashMap<>();
            repository.put(userId, meals);
        }
        meals = repository.get(userId);

        if(meal.isNew()) {
            meal.setId(id.incrementAndGet());
        }

        return meals.put(meal.getId(), meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        LOG.info("delete " + id);
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        LOG.info("get " + id);
        return repository.get(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        LOG.info("get all " + userId);
        List<Meal> meals = new ArrayList<>(repository.get(userId).values());
        meals.sort(Meal_TIME);
        return meals;
    }
}

