<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: maslov
  Date: 28.01.2020
  Time: 12:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${clientDoc.phoneNumber} Уведомления Гостя</title>
</head>
<body>
<h1>${clientDoc.phoneNumber} ${clientDoc.firstName} ${clientDoc.id}</h1>
<hr/>
<c:forEach items="${notifications}" var="notificationDoc">
    <div>
        <b>${notificationDoc.title}</b>
        <p>${notificationDoc.description}</p>
        <span>id:${notificationDoc.id}, ${notificationDoc.readStatus}, orderId:${notificationDoc.orderId}, createDate: <fmt:formatDate dateStyle="short" timeStyle="short" value="${notificationDoc.createdAt}"/></span>
        <br/>
        <br/>
        <hr/>
        <br/>
        <br/>
    </div>
</c:forEach>


</body>
</html>
