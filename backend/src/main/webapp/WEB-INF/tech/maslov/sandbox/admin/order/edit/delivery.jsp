<%@ page import="tech.maslov.sandbox.order.routes.OrderAdminRoutes" %>
<%@ page import="tech.maslov.sandbox.order.models.DeliveryInfo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: maslov
  Date: 28.01.2020
  Time: 06:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-lg-6">
        <form action="<%= OrderAdminRoutes.EDIT%>" method="POST">
            <input type="hidden" name="id" value="${doc.id}"/>
            <input type="hidden" name="status" value="<%= DeliveryInfo.STATUS.CLOSED%>"/>
            <button type="submit" class="btn btn-danger">Закрыть заказ</button>
        </form>
    </div>

    <div class="col-lg-6">
        <form action="<%= OrderAdminRoutes.EDIT%>" method="POST">
            <input type="hidden" name="id" value="${doc.id}"/>
            <select type="text" name="courierId">
                <c:forEach items="${couriers.values()}" var="courier">
                    <option value="${courier.id}">${courier.phoneNumber} ${courier.firstName}</option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn-success">Изменить курьера</button>
        </form>
    </div>
</div>

<div class="row">
    <div class="col-lg-6">
        <c:set var="fieldName" value="doc.deliveryInfo.status" scope="request"/>
        <c:set var="fieldTitle" value="Статус доставки" scope="request"/>
        <c:set var="fieldValue" value="${doc.deliveryInfo.status}" scope="request"/>
        <jsp:include page="infoField.jsp"/>
    </div>

    <div class="col-lg-6">
        <c:set var="fieldName" value="doc.deliveryInfo.type" scope="request"/>
        <c:set var="fieldTitle" value="Тип доставки" scope="request"/>
        <c:set var="fieldValue" value="${doc.deliveryInfo.type}" scope="request"/>
        <jsp:include page="infoField.jsp"/>
    </div>
</div>

<div class="row">
    <div class="col-lg-6">
        <c:set var="fieldName" value="doc.deliveryInfo.restaurantId" scope="request"/>
        <c:set var="fieldTitle" value="ID Ресторана" scope="request"/>
        <c:set var="fieldValue" value="${doc.deliveryInfo.restaurantId}" scope="request"/>
        <jsp:include page="infoField.jsp"/>
    </div>

    <div class="col-lg-6">
        <c:set var="fieldName" value="doc.deliveryInfo.currentPosition" scope="request"/>
        <c:set var="fieldTitle" value="Координаты" scope="request"/>
        <c:set var="fieldValue"
               value="${doc.deliveryInfo.currentPosition.longitude} ${doc.deliveryInfo.currentPosition.latitude}"
               scope="request"/>
        <jsp:include page="infoField.jsp"/>
    </div>
</div>

<div class="row">
    <div class="col-lg-6">
        <c:set var="fieldName" value="doc.deliveryInfo.deliveryCost" scope="request"/>
        <c:set var="fieldTitle" value="Стоимость доставки" scope="request"/>
        <c:set var="fieldValue" value="${doc.deliveryInfo.deliveryCost}" scope="request"/>
        <jsp:include page="infoField.jsp"/>
    </div>

    <div class="col-lg-6">
        <c:set var="fieldName" value="doc.deliveryInfo.courierId" scope="request"/>
        <c:set var="fieldTitle" value="ID Курьера" scope="request"/>
        <c:set var="fieldValue" value="${doc.deliveryInfo.courierId}" scope="request"/>
        <c:if test="${doc.deliveryInfo.courierId != null}">
            <c:set var="fieldValue" value="${doc.deliveryInfo.courierId} ${couriers.get(doc.deliveryInfo.courierId).firstName} ${couriers.get(doc.deliveryInfo.courierId).phoneNumber}" scope="request"/>
        </c:if>
        <jsp:include page="infoField.jsp"/>
    </div>
</div>

<div class="row">
    <div class="col-lg-6">
        <c:set var="fieldName" value="doc.deliveryInfo.address.city" scope="request"/>
        <c:set var="fieldTitle" value="Город" scope="request"/>
        <c:set var="fieldValue" value="${doc.deliveryInfo.address.city}" scope="request"/>
        <jsp:include page="infoField.jsp"/>
    </div>
    <div class="col-lg-6">
        <c:set var="fieldName" value="doc.deliveryInfo.address.street" scope="request"/>
        <c:set var="fieldTitle" value="Улица" scope="request"/>
        <c:set var="fieldValue" value="${doc.deliveryInfo.address.street}" scope="request"/>
        <jsp:include page="infoField.jsp"/>
    </div>
    <div class="col-lg-6">
        <c:set var="fieldName" value="doc.deliveryInfo.address.house" scope="request"/>
        <c:set var="fieldTitle" value="Дом" scope="request"/>
        <c:set var="fieldValue" value="${doc.deliveryInfo.address.house}" scope="request"/>
        <jsp:include page="infoField.jsp"/>
    </div>
    <div class="col-lg-6">
        <c:set var="fieldName" value="doc.deliveryInfo.address.porch" scope="request"/>
        <c:set var="fieldTitle" value="Подъезд" scope="request"/>
        <c:set var="fieldValue" value="${doc.deliveryInfo.address.porch}" scope="request"/>
        <jsp:include page="infoField.jsp"/>
    </div>
    <div class="col-lg-6">
        <c:set var="fieldName" value="doc.deliveryInfo.address.floor" scope="request"/>
        <c:set var="fieldTitle" value="Этаж" scope="request"/>
        <c:set var="fieldValue" value="${doc.deliveryInfo.address.floor}" scope="request"/>
        <jsp:include page="infoField.jsp"/>
    </div>
    <div class="col-lg-6">
        <c:set var="fieldName" value="doc.deliveryInfo.address.apartment" scope="request"/>
        <c:set var="fieldTitle" value="Квартира" scope="request"/>
        <c:set var="fieldValue" value="${doc.deliveryInfo.address.apartment}" scope="request"/>
        <jsp:include page="infoField.jsp"/>
    </div>
    <div class="col-lg-6">
        <c:set var="fieldName" value="doc.deliveryInfo.address.coordinates" scope="request"/>
        <c:set var="fieldTitle" value="Координаты" scope="request"/>
        <c:set var="fieldValue"
               value="${doc.deliveryInfo.address.coordinates.longitude} ${doc.deliveryInfo.address.coordinates.latitude}"
               scope="request"/>
        <jsp:include page="infoField.jsp"/>
    </div>
    <div class="col-lg-6">
        <c:set var="fieldName" value="doc.deliveryInfo.address.coordinateAccuracy" scope="request"/>
        <c:set var="fieldTitle" value="Точность координат" scope="request"/>
        <c:set var="fieldValue" value="${doc.deliveryInfo.address.coordinateAccuracy}" scope="request"/>
        <jsp:include page="infoField.jsp"/>
    </div>
</div>

