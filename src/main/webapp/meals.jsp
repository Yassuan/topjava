<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal List</title>
    <style>
        .red{color:red}
        .green{color:green}
    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal List</h2>

<table border="1px"  cellpadding="10" cellspacing="0">

    <tr>
        <th>description</th>
        <th>calories</th>
        <th>Time</th>
        <th colspan="2"><a href="meals?action=create">Add Meal</a></th>
        </tr>
    <c:forEach var="meal" items="${meals}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed" scope="page"/>
        <tr class="${meal.exceed? 'red' : 'green'}">
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><%= TimeUtil.toString(meal.getDateTime())%></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
        </tr>

    </c:forEach>
</table>
</body>
</html>
