<%@ page import="tech.maslov.sandbox.courier.routes.CourierAdminRoutes" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="modal fade custom-width" id="modal-courier">
    <div class="modal-dialog" style="width: 96%">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Выбор Курьер</h4>
            </div>

            <div class="modal-body">

                <div class="row">

                    <div class="col-lg-5">
                        <div class="input-group">
                            <input type="text" class="form-control input-sm" id="modal-courier-query" name="query"
                                   value="" placeholder="Поиск"/>

                            <div class="input-group-btn">
                                <button type="submit" id="modal-courier-search" class="btn btn-sm btn-default"><i
                                        class="entypo-search">Поиск </i>
                                </button>
                            </div>
                        </div>
                    </div>

                </div>
                <section id="modal-courier-parent-content"></section>

            </div>
        </div>
    </div>
</div>

<script>
    function initCourierModal() {
        $.get("<%= CourierAdminRoutes.MODAL_PARENT%>",
                {query: $('#modal-courier-query').val()},
                function (data) {
                    updateCourierContent(data);
                });
    }

    function updateCourierContent(data) {
        $('#modal-courier-parent-content').html(data);
    }

    function onClickCourierMTable() {
        var id = $(this).attr('data-id');
        var title = $(this).attr('data-title');
        $('#parent-courier-hidden').val(id);
        $('#parent-courier').val(title);
        $('#modal-courier').modal('hide');
    }

    function onClickCourierPaginator() {
        var q = $(this).attr('data-query');
        var n = $(this).attr('data-number');
        $.get("<%= CourierAdminRoutes.MODAL_PARENT%>",
                {query: q, currentPage: n},
                function (data) {
                    updateCourierContent(data);
                });
    }

    $(function () {
        $('#btn_parent_courier').click(function () {
            $('#modal-courier').modal('show');
            initCourierModal();
            return false;
        });
        $('#btn_parent_courier_clear').click(function () {
            $('#parent-courier-hidden').val('');
            $('#parent-courier').val('');
            return false;
        });

        $('#modal-courier').on('click', '.modal-courier-line', onClickCourierMTable);
        $('#modal-courier').on('click', '.modal-courier-goto', onClickCourierPaginator);
        $('#modal-courier-search').click(initCourierModal);

    });
</script>
