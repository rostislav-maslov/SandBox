<%@ page import="tech.maslov.sandbox.client.routes.ClientAdminRoutes" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="modal fade custom-width" id="modal-client">
    <div class="modal-dialog" style="width: 96%">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Выбор Клиент</h4>
            </div>

            <div class="modal-body">

                <div class="row">

                    <div class="col-lg-5">
                        <div class="input-group">
                            <input type="text" class="form-control input-sm" id="modal-client-query" name="query"
                                   value="" placeholder="Поиск"/>

                            <div class="input-group-btn">
                                <button type="submit" id="modal-client-search" class="btn btn-sm btn-default"><i
                                        class="entypo-search">Поиск </i>
                                </button>
                            </div>
                        </div>
                    </div>

                </div>
                <section id="modal-client-parent-content"></section>

            </div>
        </div>
    </div>
</div>

<script>
    function initClientModal() {
        $.get("<%= ClientAdminRoutes.MODAL_PARENT%>",
                {query: $('#modal-client-query').val()},
                function (data) {
                    updateClientContent(data);
                });
    }

    function updateClientContent(data) {
        $('#modal-client-parent-content').html(data);
    }

    function onClickClientMTable() {
        var id = $(this).attr('data-id');
        var title = $(this).attr('data-title');
        $('#parent-client-hidden').val(id);
        $('#parent-client').val(title);
        $('#modal-client').modal('hide');
    }

    function onClickClientPaginator() {
        var q = $(this).attr('data-query');
        var n = $(this).attr('data-number');
        $.get("<%= ClientAdminRoutes.MODAL_PARENT%>",
                {query: q, currentPage: n},
                function (data) {
                    updateClientContent(data);
                });
    }

    $(function () {
        $('#btn_parent_client').click(function () {
            $('#modal-client').modal('show');
            initClientModal();
            return false;
        });
        $('#btn_parent_client_clear').click(function () {
            $('#parent-client-hidden').val('');
            $('#parent-client').val('');
            return false;
        });

        $('#modal-client').on('click', '.modal-client-line', onClickClientMTable);
        $('#modal-client').on('click', '.modal-client-goto', onClickClientPaginator);
        $('#modal-client-search').click(initClientModal);

    });
</script>
