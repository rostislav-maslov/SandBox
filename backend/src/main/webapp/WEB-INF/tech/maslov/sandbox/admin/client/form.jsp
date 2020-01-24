<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form:errors path="*" cssClass="alert alert-warning" element="div" />
<form:hidden path="id"/>


<div class="row">
    <div class="col-lg-4">
        <div class="form-group">
            <label for="firstName">Имя</label>
            <form:input path="firstName" cssClass="form-control" id="firstName"/>
        </div>
    </div>

    <div class="col-lg-4">
        <div class="form-group">
            <label for="lastName">Фамилия</label>
            <form:input path="lastName" cssClass="form-control" id="lastName"/>
        </div>
    </div>

    <div class="col-lg-4">
        <div class="form-group">
            <label for="phoneNumber">Телефон</label>
            <form:input path="phoneNumber" cssClass="form-control" id="phoneNumber"/>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="userId">UserDoc ID</label>
            <form:input path="userId" cssClass="form-control" id="userId"/>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="bDate">Дата рождения</label>
            <form:input path="bDate" cssClass="form-control" id="bDate"/>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="sex">Пол</label>
            <form:input path="sex" cssClass="form-control" id="sex"/>
        </div>
    </div>
</div>

