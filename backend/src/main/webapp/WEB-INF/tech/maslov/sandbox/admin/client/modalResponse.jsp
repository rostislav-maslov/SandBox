<%@ page import="tech.maslov.sandbox.client.routes.ClientAdminRoutes" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
    .curcor-pointer:hover {
        cursor: pointer;
        background-color: whitesmoke;
    }
</style>
<div class="row" style="margin-top: 10px">
    <div class="col-lg-12">
        <table class="table table-bordered" id="table-1">
            <thead>
            <tr>
                <th>Заголовок</th><th>Заголовок</th><th>UserDoc ID</th><th>Дата рождения</th><th>Пол</th>
                <th>Дата создания</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${search.result}" var="doc">

                <tr class="curcor-pointer modal-client-line" data-id="${doc.id}" data-title="${doc.title}">
                    <td>${doc.firstName}</td><td>${doc.lastName}</td><td>${doc.userId}</td><td>${doc.bDate}</td><td>${doc.sex}</td>
                    <td><fmt:formatDate dateStyle="short" timeStyle="short" value="${doc.createdAt}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div class="row">
    <div class="col-lg-12 text-center">
        <ul class="pagination pagination-sm">

            <li>
                <a href="#" class="modal-client-goto" data-query="${search.query}"
                   data-number="${search.prevNum()}">
                    <i class="entypo-left-open-mini"></i></a>
            </li>
            <c:forEach items="${search.paginator()}" var="page">
                <li class="<c:if test="${search.currentPage eq page}">active</c:if>">
                    <a href="#" class="modal-client-goto" data-query="${search.query}"
                       data-number="${page}">${page + 1}</a>
                </li>
            </c:forEach>
            <li>
                <a href="#" class="modal-client-goto" data-query="${search.query}"
                   data-number="${search.nextNum()}"><i class="entypo-right-open-mini"></i></a>
            </li>
        </ul>
    </div>
</div>