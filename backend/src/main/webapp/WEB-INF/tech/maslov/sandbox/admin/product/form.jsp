<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form:errors path="*" cssClass="alert alert-warning" element="div"/>
<form:hidden path="id"/>


<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="title">Заголовок</label>
            <form:input path="title" cssClass="form-control" id="title"/>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="description">Описание</label>
            <form:textarea path="description" cssClass="form-control" id="description"/>
        </div>
    </div>
</div>


<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="picId">Картинка</label>
            <form:hidden path="picId" cssClass="form-control" id="picId"/>
            <input name="picFile" type="file" class="form-control" id="picId"/>

            <c:if test="${not empty doc.picId }">
                <img src="/pics/${doc.picId}" style="max-width: 300px; max-height: 300px"/>
            </c:if>

        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-6">
        <div class="form-group">
            <label for="categoryId">Категория товара</label>
            <form:select path="categoryId" cssClass="form-control" id="categoryId">
                <c:forEach items="${categories.values()}" var="category">
                    <form:option value="${category.id}">${category.title}</form:option>
                </c:forEach>
            </form:select>
        </div>
    </div>
    <div class="col-lg-6">
        <div class="form-group">
            <label for="price">Цена</label>
            <form:input path="price" cssClass="form-control" id="price"/>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="form-group">
            <label for="available">Активный товар</label>
            <form:select path="available" cssClass="form-control" id="available">
                <form:option value="${false}">Заблокирован</form:option>
                <form:option value="${true}">Активный</form:option>
            </form:select>
        </div>
    </div>
</div>


