<%@ page import="tech.maslov.sandbox.order.routes.OrderApiRoutes" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Тестовая Онлайн Оплата</title>
</head>
<body>
<h1>Тестовая онлайн оплата</h1>
<form action="<%= OrderApiRoutes.PAY%>" method="post">
    <input type="hidden" name="orderId" value="${orderId}"/>
    <input type="hidden" name="success" value="true"/>
    <button type="submit">Подтвердить онлайн оплату</button>
</form>
<br/>
<br/>
<br/>
<br/>
<form action="<%= OrderApiRoutes.PAY%>" method="post">
    <input type="hidden" name="orderId" value="${orderId}"/>
    <input type="hidden" name="success" value="false"/>
    <button type="submit">Отклонить онлайн оплату</button>
</form>
</body>
</html>
