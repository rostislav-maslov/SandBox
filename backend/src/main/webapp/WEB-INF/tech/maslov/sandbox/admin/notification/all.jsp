<%@ page import="tech.maslov.sandbox.notification.routes.NotificationAdminRoutes" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/com/ub/core/admin/main/components/pageHeader.jsp"/>

<div class="row">
    <form action="<%= NotificationAdminRoutes.ALL%>" method="GET">
        <div class="col-lg-5">
            <div class="input-group">
                <input type="text" class="form-control input-sm" id="query" name="query"
                       value="${search.query}" placeholder="Поиск"/>
                <div class="input-group-btn">
                    <button type="submit" class="btn btn-sm btn-default"><i class="entypo-search">Поиск </i></button>
                </div>
            </div>
        </div>
    </form>
</div>

<div class="widget widget-blue">
    <div class="widget-title">
        <h3><i class="fa fa-table" aria-hidden="true"></i> Результаты поиска: </h3>
    </div>
    <p>Всего - ${search.all}</p>
    <div class="widget-content">
        <div class="table-responsive">
            <table class="table table-bordered" id="table-1">
                <thead>
                <tr>

                    <th>Client ID</th><th>Title</th><th>Description</th><th>Order ID</th><th>Read Status</th>
                    <th>Дата создания</th>
                    <th>Дата изменения</th>

                    <th>Действия</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${search.result}" var="doc">
                    <tr>
                        <td>${doc.clientId}</td><td>${doc.title}</td><td>${doc.description}</td><td>${doc.orderId}</td><td>${doc.readStatus}</td>
                        <td><fmt:formatDate dateStyle="short" timeStyle="short" value="${doc.createdAt}"/></td>
                        <td><fmt:formatDate dateStyle="short" timeStyle="short" value="${doc.updateAt}"/></td>

                        <td>
                            <c:url value="<%= NotificationAdminRoutes.EDIT%>" var="editUrl">
                                <c:param name="id" value="${doc.id}"/>
                            </c:url>
                            <a href="${editUrl}" class="btn btn-xs btn-default">
                                <i class="fa fa-pencil" aria-hidden="true"></i> Редактировать
                            </a>
                            <c:url value="<%= NotificationAdminRoutes.DELETE%>" var="urlDelete">
                                <c:param name="id" value="${doc.id}"/>
                            </c:url>
                            <a href="${urlDelete}" type="submit" class="btn btn-xs btn-danger">
                                <i class="fa fa-times-circle" aria-hidden="true"></i> Удалить
                            </a>

                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12 text-center">
        <ul class="pagination pagination-sm">
            <c:url value="<%= NotificationAdminRoutes.ALL%>" var="urlPrev">
                <c:param name="query" value="${search.query}"/>
                <c:param name="currentPage" value="${search.prevNum()}"/>
            </c:url>
            <li><a href="${urlPrev}"><i class="entypo-left-open-mini"></i></a></li>

            <c:forEach items="${search.paginator()}" var="page">
                <c:url value="<%= NotificationAdminRoutes.ALL%>" var="urlPage">
                    <c:param name="query" value="${search.query}"/>
                    <c:param name="currentPage" value="${page}"/>
                </c:url>
                <li class="<c:if test="${search.currentPage eq page}">active</c:if>">
                    <a href="${urlPage}">${page + 1}</a>
                </li>
            </c:forEach>
            <c:url value="<%= NotificationAdminRoutes.ALL%>" var="urlNext">
                <c:param name="query" value="${search.query}"/>
                <c:param name="currentPage" value="${search.nextNum()}"/>
            </c:url>
            <li><a href="${urlNext}"><i class="entypo-right-open-mini"></i></a></li>
        </ul>
    </div>
</div>
