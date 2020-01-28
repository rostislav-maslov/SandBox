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
                <div class="panel-title"><h4>Заказ - № ${doc.number}</h4></div>
                <div class="panel-options">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#tab-client-info" data-toggle="tab" aria-expanded="true">
                                Информация о клиенте
                            </a>
                        </li>
                        <li class="">
                            <a href="#tab-basket" data-toggle="tab" aria-expanded="false">
                                Товары
                            </a>
                        </li>
                        <li class="">
                            <a href="#tab-delivery" data-toggle="tab" aria-expanded="false">
                                Доставка
                            </a>
                        </li>
                        <li class="">
                            <a href="#tab-info" data-toggle="tab" aria-expanded="false">
                                Информация о заказе
                            </a>
                        </li>
                        <li class="">
                            <a href="#tab-payment" data-toggle="tab" aria-expanded="false">
                                Оплата
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="panel-body">
                <div class="tab-content">
                    <div class="tab-pane active" id="tab-client-info">
                        <jsp:include page="edit/client.jsp"/>
                    </div>
                    <div class="tab-pane" id="tab-basket">
                        <jsp:include page="edit/basket.jsp"/>
                    </div>
                    <div class="tab-pane" id="tab-delivery">
                        <jsp:include page="edit/delivery.jsp"/>
                    </div>
                    <div class="tab-pane" id="tab-info">
                        <jsp:include page="edit/order.jsp"/>
                    </div>
                    <div class="tab-pane" id="tab-payment">
                        <jsp:include page="edit/payment.jsp"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
