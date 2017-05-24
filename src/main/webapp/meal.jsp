
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
    <style>
        input{

            display: block;
        }

    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal editor</h2>
    <form action="meals" method="post">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
        <input type="hidden" name="id" value="${meal.id}">
        Description:<input type="text" name="description" value="${meal.description}"> <br>
        Calories:  <input type="number" name="calories" value="${meal.calories}"><br>
        Date Time: <input type="datetime-local" name="dateTime" value="${meal.dateTime}"><br>
        <button type="submit">Save</button>

    </form>
</body>
</html>
