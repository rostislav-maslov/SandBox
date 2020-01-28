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
        <c:set var="fieldName" value="doc.paymentInfo.type" scope="request"/>
        <c:set var="fieldTitle" value="Тип оплаты" scope="request"/>
        <c:set var="fieldValue" value="${doc.paymentInfo.type}" scope="request"/>
        <jsp:include page="infoField.jsp"/>
    </div>

    <div class="col-lg-6">
        <c:set var="fieldName" value="doc.paymentInfo.changeWith" scope="request"/>
        <c:set var="fieldTitle" value="Сдача" scope="request"/>
        <c:set var="fieldValue" value="${doc.paymentInfo.changeWith}" scope="request"/>
        <jsp:include page="infoField.jsp"/>
    </div>
</div>

