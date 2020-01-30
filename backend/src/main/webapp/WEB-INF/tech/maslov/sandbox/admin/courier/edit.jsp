<%@ page import="tech.maslov.sandbox.courier.routes.CourierAdminRoutes" %>
<%@ page import="tech.maslov.sandbox.order.routes.OrderAdminRoutes" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="/WEB-INF/com/ub/core/admin/main/components/pageHeader.jsp"/>

<div class="row">
    <div class="col-md-12">
        <div class="panel minimal minimal-gray">
            <div class="panel-heading">
                <div class="panel-title"><h4>Курьер - ${doc.phoneNumber}</h4></div>
                <div class="panel-options">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#tab-info" data-toggle="tab" aria-expanded="true">
                                Информация о курьере
                            </a>
                        </li>
                        <li class="">
                            <a href="#tab-orders" data-toggle="tab" aria-expanded="false">
                                Заказы
                            </a>
                        </li>

                    </ul>
                </div>
            </div>
            <div class="panel-body">
                <div class="tab-content">
                    <div class="tab-pane active" id="tab-info">
                        <form:form action="<%= OrderAdminRoutes.EDIT%>" modelAttribute="doc" method="POST">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <label for="firstName">Имя</label>
                                        <form:input path="firstName" cssClass="form-control" id="firstName"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <label for="lastName">Фамилия</label>
                                        <form:input path="lastName" cssClass="form-control" id="lastName"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <label for="userId">User ID</label>
                                        <form:input path="userId" cssClass="form-control" id="userId"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <label for="phoneNumber">Номер Телефона</label>
                                        <form:input path="phoneNumber" cssClass="form-control" id="phoneNumber"/>
                                    </div>
                                </div>
                            </div>
                        </form:form>
                    </div>
                    <div class="tab-pane" id="tab-orders">
                        <div class="row">
                            <div class="col-md-12">
                                <table class="table table-bordered" id="table-order-basket">
                                    <thead>
                                    <tr>
                                        <th>Номер заказа</th>
                                        <th>Тип доставки</th>
                                        <th>Статус заказа</th>
                                        <th>Гость</th>
                                        <th>Сумма</th>
                                        <th>Дата создания</th>

                                        <th>Действия</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <c:forEach items="${orders}" var="order">
                                        <tr>
                                            <td>${order.number}</td>
                                            <td>${order.deliveryInfo.type}</td>
                                            <td>${order.deliveryInfo.status}</td>
                                            <td>${order.clientInfo.phoneNumber}</td>
                                            <td>${order.paymentInfo.total}</td>
                                            <td><fmt:formatDate dateStyle="short" timeStyle="short"
                                                                value="${order.createdAt}"/></td>

                                            <td>
                                                <c:url value="<%= OrderAdminRoutes.EDIT%>" var="editUrl">
                                                    <c:param name="id" value="${order.id}"/>
                                                </c:url>
                                                <a href="${editUrl}" class="btn btn-xs btn-default" target="_blank">
                                                    <i class="fa fa-pencil" aria-hidden="true"></i> Редактировать
                                                </a>

                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>