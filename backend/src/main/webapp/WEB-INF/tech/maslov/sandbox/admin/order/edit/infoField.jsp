<%--
  Created by IntelliJ IDEA.
  User: maslov
  Date: 28.01.2020
  Time: 09:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="form-group">
    <label for="info-field-${fieldName}">${fieldTitle}</label>
    <input name="info-field-${fieldName}" class="form-control" id="info-field-${fieldName}" disabled="disabled" value="${fieldValue}"/>
</div>
