<%@ page import="tech.maslov.sandbox.restaurant.routes.RestaurantAdminRoutes" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="modal fade custom-width" id="modal-restaurant">
    <div class="modal-dialog" style="width: 96%">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Выбор Рестораны</h4>
            </div>

            <div class="modal-body">

                <div class="row">

                    <div class="col-lg-5">
                        <div class="input-group">
                            <input type="text" class="form-control input-sm" id="modal-restaurant-query" name="query"
                                   value="" placeholder="Поиск"/>

                            <div class="input-group-btn">
                                <button type="submit" id="modal-restaurant-search" class="btn btn-sm btn-default"><i
                                        class="entypo-search">Поиск </i>
                                </button>
                            </div>
                        </div>
                    </div>

                </div>
                <section id="modal-restaurant-parent-content"></section>

            </div>
        </div>
    </div>
</div>

<script>
    function initRestaurantModal() {
        $.get("<%= RestaurantAdminRoutes.MODAL_PARENT%>",
                {query: $('#modal-restaurant-query').val()},
                function (data) {
                    updateRestaurantContent(data);
                });
    }

    function updateRestaurantContent(data) {
        $('#modal-restaurant-parent-content').html(data);
    }

    function onClickRestaurantMTable() {
        var id = $(this).attr('data-id');
        var title = $(this).attr('data-title');
        $('#parent-restaurant-hidden').val(id);
        $('#parent-restaurant').val(title);
        $('#modal-restaurant').modal('hide');
    }

    function onClickRestaurantPaginator() {
        var q = $(this).attr('data-query');
        var n = $(this).attr('data-number');
        $.get("<%= RestaurantAdminRoutes.MODAL_PARENT%>",
                {query: q, currentPage: n},
                function (data) {
                    updateRestaurantContent(data);
                });
    }

    $(function () {
        $('#btn_parent_restaurant').click(function () {
            $('#modal-restaurant').modal('show');
            initRestaurantModal();
            return false;
        });
        $('#btn_parent_restaurant_clear').click(function () {
            $('#parent-restaurant-hidden').val('');
            $('#parent-restaurant').val('');
            return false;
        });

        $('#modal-restaurant').on('click', '.modal-restaurant-line', onClickRestaurantMTable);
        $('#modal-restaurant').on('click', '.modal-restaurant-goto', onClickRestaurantPaginator);
        $('#modal-restaurant-search').click(initRestaurantModal);

    });
</script>
