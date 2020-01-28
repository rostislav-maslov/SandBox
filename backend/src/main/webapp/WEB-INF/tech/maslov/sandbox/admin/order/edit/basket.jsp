<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12">
        <table class="table table-bordered" id="table-order-basket">
            <thead>
            <tr>
                <th>Название товара</th>
                <th>Цена товара</th>
                <th>Количество</th>
                <th>Сумма</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${doc.basket.products}" var="product">
                <tr>
                    <td>${product.productDoc.title}</td>
                    <td>${product.productDoc.price}</td>
                    <td>${product.count}</td>
                    <td>${product.totalPrice}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <h4>Всего к оплате${doc.paymentInfo.total}</h4>
    </div>
</div>