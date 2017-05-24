package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private MealRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new MealRepositoryImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setCharacterEncoding("UTF-8");
            String id = request.getParameter("id");
            Meal meal = new Meal(id.isEmpty()? null: Integer.parseInt(id),LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
             LOG.info("Save meal", meal);
            repository.save(meal);
            response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if(action == null) {
            LOG.debug("redirect to meal list");
            request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(repository.getAll(),
                    LocalTime.MIN, LocalTime.MAX,2000));

            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
        else if("delete".equals(action)) {
            LOG.debug("remove meal");
            repository.delete(Integer.parseInt(request.getParameter("id")));
            response.sendRedirect("meals");
        }
        else if("update".equals(action)) {
            LOG.debug("update meal");
            request.setAttribute("meal", repository.get(Integer.parseInt(request.getParameter("id"))));
            request.getRequestDispatcher("/meal.jsp").forward(request, response);
        } else if("create".equals(action)) {
            LOG.debug("create new meal");

            request.setAttribute("meal", new Meal(LocalDateTime.now(), " ", 0));
            request.getRequestDispatcher("/meal.jsp").forward(request, response);
        }

    }
}
