<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form:errors path="*" cssClass="alert alert-warning" element="div" />
<form:hidden path="id"/>


<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="clientInfo">Гость</label>
            <form:input path="clientInfo" cssClass="form-control" id="clientInfo"/>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="basket">Товары/корзина</label>
            <form:input path="basket" cssClass="form-control" id="basket"/>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="deliveryInfo">Доставка</label>
            <form:input path="deliveryInfo" cssClass="form-control" id="deliveryInfo"/>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="paymentType">Оплата</label>
            <form:input path="paymentType" cssClass="form-control" id="paymentType"/>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="orderInfo">Информация о заказе</label>
            <form:input path="orderInfo" cssClass="form-control" id="orderInfo"/>
        </div>
    </div>
</div>

