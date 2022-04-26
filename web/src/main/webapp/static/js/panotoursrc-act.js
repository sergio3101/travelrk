$(document).ready(function () {
    $('.bs-remove-panotoursrc').on('show.bs.modal', function (e) {
        var $invoker = $(e.relatedTarget);
        $('.bs-remove-panotoursrc .modal-body').html('<h4>Вы уверены?</h4><p>Тур невозможно будет восстановить.</p>')
        $('.bs-remove-panotoursrc .modal-footer').html('<button type="button" class="btn btn-danger" id="btn-rmv-dmo" data-data="' + $($invoker).attr('data-data') + '">Удалить</button>')
    });

    $('.bs-remove-panotoursrc .modal-footer').on('click', '#btn-rmv-dmo', function () {
        var id = $(this).attr('data-data');
        $.ajax({
            type: 'POST',
            data: 'id=' + id,
            url: 'ajaxRemovePanoTourSrc',
            success: function (data) {
                if (data == 'SUCCESS') {
                    $('#' + id).remove();
                    $('#btn-rmv-dmo').remove();
                    $('.bs-remove-panotoursrc').modal('hide');
                    new PNotify({
                        title: 'Удаление',
                        text: 'Тур успешно удален',
                        type: 'success'
                    });
                }
                if (data == 'ERROR') {
                    new PNotify({
                        title: 'Удаление',
                        text: 'При удалении тура произошла ошибка',
                        type: 'success'
                    });
                    $('#btn-rmv-dmo').remove();
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
    document.getElementById("save-panotoursrc-btn").addEventListener("click", function(){
        var panotoururl = $("#panotoururl").val();
        window.location.href = "/admin/panotoursrcedit-0?panotoururl=" + panotoururl;
    });
});
