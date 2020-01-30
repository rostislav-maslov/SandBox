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
            <label for="clientId">Client ID</label>
            <form:input path="clientId" cssClass="form-control" id="clientId"/>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="title">Title</label>
            <form:input path="title" cssClass="form-control" id="title"/>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="description">Description</label>
            <form:input path="description" cssClass="form-control" id="description"/>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="orderId">Order ID</label>
            <form:input path="orderId" cssClass="form-control" id="orderId"/>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="readStatus">Read Status</label>
            <form:input path="readStatus" cssClass="form-control" id="readStatus"/>
        </div>
    </div>
</div>

