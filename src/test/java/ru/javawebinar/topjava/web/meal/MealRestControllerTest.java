package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by jerom on 16.06.2017.
 */
public class MealRestControllerTest extends AbstractControllerTest {
    final static String REST_URL = MealRestController.REST_URL + "/";
    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {

        List<Meal> excpect = Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(excpect, mealService.getAll(USER_ID));

    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(EXCEED_MODEL_MATCHER.contentListMatcher(MealsUtil.getWithExceeded(MEALS, USER.getCaloriesPerDay())));

    }

    @Test
    public void testCreateWithLocation() throws Exception {
        Meal meal = getCreated();
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(meal)));
        meal.setId(ADMIN_MEAL2.getId() + 1);
        MATCHER.assertCollectionEquals(Arrays.asList(meal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), mealService.getAll(USER_ID));

    }

    @Test
    public void testUpdate() throws Exception {
        Meal meal = getUpdated();
        mockMvc.perform(put(REST_URL + MEAL1_ID).contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(meal))).andExpect(status().isOk());
    }

    @Test
    public void testGetBetween() throws Exception {
        String start = "from=" + MEAL1.getDateTime().toString();
        String end = "to=" + MEAL3.getDateTime().toString();
        mockMvc.perform(get(REST_URL + "between?" + start + "&" + end))
                .andExpect(status().isOk()).andDo(print());

    }

}