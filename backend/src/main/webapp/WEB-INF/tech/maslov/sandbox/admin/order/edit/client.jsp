<%--
  Created by IntelliJ IDEA.
  User: maslov
  Date: 28.01.2020
  Time: 06:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-lg-6">
        <div class="form-group">
            <label for="clientInfo.phoneNumber">Телефон</label>
            <input name="clientInfo.phoneNumber" class="form-control" id="clientInfo.phoneNumber" disabled="disabled" value="${doc.clientInfo.phoneNumber}"/>
        </div>
    </div>
    <div class="col-lg-6">
        <div class="form-group">
            <label for="clientInfo.firstName">Имя гостя</label>
            <input name="clientInfo.firstName" class="form-control" id="clientInfo.firstName" disabled="disabled" value="${doc.clientInfo.firstName}"/>
        </div>
    </div>
</div>
