<%@ page import="tech.maslov.sandbox.product.routes.ProductAdminRoutes" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="modal fade custom-width" id="modal-product">
    <div class="modal-dialog" style="width: 96%">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Выбор Товары</h4>
            </div>

            <div class="modal-body">

                <div class="row">

                    <div class="col-lg-5">
                        <div class="input-group">
                            <input type="text" class="form-control input-sm" id="modal-product-query" name="query"
                                   value="" placeholder="Поиск"/>

                            <div class="input-group-btn">
                                <button type="submit" id="modal-product-search" class="btn btn-sm btn-default"><i
                                        class="entypo-search">Поиск </i>
                                </button>
                            </div>
                        </div>
                    </div>

                </div>
                <section id="modal-product-parent-content"></section>

            </div>
        </div>
    </div>
</div>

<script>
    function initProductModal() {
        $.get("<%= ProductAdminRoutes.MODAL_PARENT%>",
                {query: $('#modal-product-query').val()},
                function (data) {
                    updateProductContent(data);
                });
    }

    function updateProductContent(data) {
        $('#modal-product-parent-content').html(data);
    }

    function onClickProductMTable() {
        var id = $(this).attr('data-id');
        var title = $(this).attr('data-title');
        $('#parent-product-hidden').val(id);
        $('#parent-product').val(title);
        $('#modal-product').modal('hide');
    }

    function onClickProductPaginator() {
        var q = $(this).attr('data-query');
        var n = $(this).attr('data-number');
        $.get("<%= ProductAdminRoutes.MODAL_PARENT%>",
                {query: q, currentPage: n},
                function (data) {
                    updateProductContent(data);
                });
    }

    $(function () {
        $('#btn_parent_product').click(function () {
            $('#modal-product').modal('show');
            initProductModal();
            return false;
        });
        $('#btn_parent_product_clear').click(function () {
            $('#parent-product-hidden').val('');
            $('#parent-product').val('');
            return false;
        });

        $('#modal-product').on('click', '.modal-product-line', onClickProductMTable);
        $('#modal-product').on('click', '.modal-product-goto', onClickProductPaginator);
        $('#modal-product-search').click(initProductModal);

    });
</script>
