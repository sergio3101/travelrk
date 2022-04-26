$(document).ready(function () {
    $('.bs-remove-krpanoconfig').on('show.bs.modal', function (e) {
        var $invoker = $(e.relatedTarget);
        $('.bs-remove-krpanoconfig .modal-body').html('<h4>Вы уверены?</h4><p>Конфигурацию невозможно будет восстановить.</p>')
        $('.bs-remove-krpanoconfig .modal-footer').html('<button type="button" class="btn btn-danger" id="btn-rmv-krpanocfg" data-data="' + $($invoker).attr('data-data') + '">Удалить</button>')
    });
    $('.bs-remove-krpanoconfig .modal-footer').on('click', '#btn-rmv-krpanocfg', function () {
        var id = $(this).attr('data-data');
        $.ajax({
            type: 'POST',
            data: 'id=' + id,
            url: 'ajaxRemoveKrpanoconfig',
            success: function (data) {
                if (data == 'SUCCESS') {
                    $('#' + id).remove();
                    $('#btn-rmv-krpanocfg').remove();
                    $('.bs-remove-krpanoconfig').modal('hide');
                    new PNotify({
                        title: 'Удаление',
                        text: 'Конфигурация успешно удалена',
                        type: 'success'
                    });

                }
                ;
                if (data == 'ERROR') {
                    new PNotify({
                        title: 'Удаление',
                        text: 'При удалении конфигурации произошла ошибка',
                        type: 'error'
                    });
                    $('#btn-rmv-krpanocfg').remove();
                }
                ;
            },
            error: function (jqXHR, exception) {
                var msg = '';
                if (jqXHR.status === 0) {
                    msg = 'Not connect.\n Verify Network.';
                } else if (jqXHR.status == 404) {
                    msg = 'Requested page not found. [404]';
                } else if (jqXHR.status == 500) {
                    msg = 'Internal Server Error [500].';
                } else if (exception === 'parsererror') {
                    msg = 'Requested JSON parse failed.';
                } else if (exception === 'timeout') {
                    msg = 'Time out error.';
                } else if (exception === 'abort') {
                    msg = 'Ajax request aborted.';
                } else {
                    msg = 'Uncaught Error.\n' + jqXHR.responseText;
                }
                new PNotify({
                    title: 'Удаление',
                    text: msg,
                    type: 'error'
                });
            }
        });
    });
});