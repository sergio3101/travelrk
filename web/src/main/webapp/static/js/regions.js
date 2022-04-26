$(document).ready(function () {
    $('.bs-remove-regions').on('show.bs.modal', function (e) {
        var $invoker = $(e.relatedTarget);
        $('.bs-remove-regions .modal-body').html('<h4>Вы уверены?</h4><p>Регион невозможно будет восстановить.</p>')
        $('.bs-remove-regions .modal-footer').html('<button type="button" class="btn btn-danger" id="btn-rmv-rgn" data-data="' + $($invoker).attr('data-data') + '">Удалить</button>')
    });
    $('.bs-remove-regions .modal-footer').on('click', '#btn-rmv-rgn', function () {
        var id = $(this).attr('data-data');
        $.ajax({
            type: 'POST',
            data: 'id=' + id,
            url: 'ajaxRemoveRegion',
            success: function (data) {
                if (data == 'SUCCESS') {
                    $('#' + id).remove();
                    $('#btn-rmv-rgn').remove();
                    $('.bs-remove-regions').modal('hide');
                    new PNotify({
                        title: 'Удаление',
                        text: 'Регион успешно удален',
                        type: 'success'
                    });

                }
                ;
                if (data == 'ERROR') {
                    new PNotify({
                        title: 'Удаление',
                        text: 'При удалении региона произошла ошибка',
                        type: 'error'
                    });
                    $('#btn-rmv-rgn').remove();
                }
                ;
                if (data == 'CONSTRAIGHTS') {
                    new PNotify({
                        title: 'Удаление',
                        text: 'Нельзя удалить используемый регион',
                        type: 'error'
                    });
                    $('#btn-rmv-rgn').remove();
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