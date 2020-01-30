<%@ page import="tech.maslov.sandbox.notification.routes.NotificationAdminRoutes" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="modal fade custom-width" id="modal-notification">
    <div class="modal-dialog" style="width: 96%">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Выбор Уведомление</h4>
            </div>

            <div class="modal-body">

                <div class="row">

                    <div class="col-lg-5">
                        <div class="input-group">
                            <input type="text" class="form-control input-sm" id="modal-notification-query" name="query"
                                   value="" placeholder="Поиск"/>

                            <div class="input-group-btn">
                                <button type="submit" id="modal-notification-search" class="btn btn-sm btn-default"><i
                                        class="entypo-search">Поиск </i>
                                </button>
                            </div>
                        </div>
                    </div>

                </div>
                <section id="modal-notification-parent-content"></section>

            </div>
        </div>
    </div>
</div>

<script>
    function initNotificationModal() {
        $.get("<%= NotificationAdminRoutes.MODAL_PARENT%>",
                {query: $('#modal-notification-query').val()},
                function (data) {
                    updateNotificationContent(data);
                });
    }

    function updateNotificationContent(data) {
        $('#modal-notification-parent-content').html(data);
    }

    function onClickNotificationMTable() {
        var id = $(this).attr('data-id');
        var title = $(this).attr('data-title');
        $('#parent-notification-hidden').val(id);
        $('#parent-notification').val(title);
        $('#modal-notification').modal('hide');
    }

    function onClickNotificationPaginator() {
        var q = $(this).attr('data-query');
        var n = $(this).attr('data-number');
        $.get("<%= NotificationAdminRoutes.MODAL_PARENT%>",
                {query: q, currentPage: n},
                function (data) {
                    updateNotificationContent(data);
                });
    }

    $(function () {
        $('#btn_parent_notification').click(function () {
            $('#modal-notification').modal('show');
            initNotificationModal();
            return false;
        });
        $('#btn_parent_notification_clear').click(function () {
            $('#parent-notification-hidden').val('');
            $('#parent-notification').val('');
            return false;
        });

        $('#modal-notification').on('click', '.modal-notification-line', onClickNotificationMTable);
        $('#modal-notification').on('click', '.modal-notification-goto', onClickNotificationPaginator);
        $('#modal-notification-search').click(initNotificationModal);

    });
</script>
