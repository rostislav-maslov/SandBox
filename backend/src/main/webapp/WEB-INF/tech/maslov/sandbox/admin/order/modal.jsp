<%@ page import="tech.maslov.sandbox.order.routes.OrderAdminRoutes" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="modal fade custom-width" id="modal-order">
    <div class="modal-dialog" style="width: 96%">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Выбор Заказ</h4>
            </div>

            <div class="modal-body">

                <div class="row">

                    <div class="col-lg-5">
                        <div class="input-group">
                            <input type="text" class="form-control input-sm" id="modal-order-query" name="query"
                                   value="" placeholder="Поиск"/>

                            <div class="input-group-btn">
                                <button type="submit" id="modal-order-search" class="btn btn-sm btn-default"><i
                                        class="entypo-search">Поиск </i>
                                </button>
                            </div>
                        </div>
                    </div>

                </div>
                <section id="modal-order-parent-content"></section>

            </div>
        </div>
    </div>
</div>

<script>
    function initOrderModal() {
        $.get("<%= OrderAdminRoutes.MODAL_PARENT%>",
                {query: $('#modal-order-query').val()},
                function (data) {
                    updateOrderContent(data);
                });
    }

    function updateOrderContent(data) {
        $('#modal-order-parent-content').html(data);
    }

    function onClickOrderMTable() {
        var id = $(this).attr('data-id');
        var title = $(this).attr('data-title');
        $('#parent-order-hidden').val(id);
        $('#parent-order').val(title);
        $('#modal-order').modal('hide');
    }

    function onClickOrderPaginator() {
        var q = $(this).attr('data-query');
        var n = $(this).attr('data-number');
        $.get("<%= OrderAdminRoutes.MODAL_PARENT%>",
                {query: q, currentPage: n},
                function (data) {
                    updateOrderContent(data);
                });
    }

    $(function () {
        $('#btn_parent_order').click(function () {
            $('#modal-order').modal('show');
            initOrderModal();
            return false;
        });
        $('#btn_parent_order_clear').click(function () {
            $('#parent-order-hidden').val('');
            $('#parent-order').val('');
            return false;
        });

        $('#modal-order').on('click', '.modal-order-line', onClickOrderMTable);
        $('#modal-order').on('click', '.modal-order-goto', onClickOrderPaginator);
        $('#modal-order-search').click(initOrderModal);

    });
</script>
