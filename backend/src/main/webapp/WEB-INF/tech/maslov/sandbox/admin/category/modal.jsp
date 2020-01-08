<%@ page import="tech.maslov.sandbox.category.routes.CategoryAdminRoutes" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="modal fade custom-width" id="modal-category">
    <div class="modal-dialog" style="width: 96%">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Выбор Категории</h4>
            </div>

            <div class="modal-body">

                <div class="row">

                    <div class="col-lg-5">
                        <div class="input-group">
                            <input type="text" class="form-control input-sm" id="modal-category-query" name="query"
                                   value="" placeholder="Поиск"/>

                            <div class="input-group-btn">
                                <button type="submit" id="modal-category-search" class="btn btn-sm btn-default"><i
                                        class="entypo-search">Поиск </i>
                                </button>
                            </div>
                        </div>
                    </div>

                </div>
                <section id="modal-category-parent-content"></section>

            </div>
        </div>
    </div>
</div>

<script>
    function initCategoryModal() {
        $.get("<%= CategoryAdminRoutes.MODAL_PARENT%>",
                {query: $('#modal-category-query').val()},
                function (data) {
                    updateCategoryContent(data);
                });
    }

    function updateCategoryContent(data) {
        $('#modal-category-parent-content').html(data);
    }

    function onClickCategoryMTable() {
        var id = $(this).attr('data-id');
        var title = $(this).attr('data-title');
        $('#parent-category-hidden').val(id);
        $('#parent-category').val(title);
        $('#modal-category').modal('hide');
    }

    function onClickCategoryPaginator() {
        var q = $(this).attr('data-query');
        var n = $(this).attr('data-number');
        $.get("<%= CategoryAdminRoutes.MODAL_PARENT%>",
                {query: q, currentPage: n},
                function (data) {
                    updateCategoryContent(data);
                });
    }

    $(function () {
        $('#btn_parent_category').click(function () {
            $('#modal-category').modal('show');
            initCategoryModal();
            return false;
        });
        $('#btn_parent_category_clear').click(function () {
            $('#parent-category-hidden').val('');
            $('#parent-category').val('');
            return false;
        });

        $('#modal-category').on('click', '.modal-category-line', onClickCategoryMTable);
        $('#modal-category').on('click', '.modal-category-goto', onClickCategoryPaginator);
        $('#modal-category-search').click(initCategoryModal);

    });
</script>
