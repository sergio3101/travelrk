$(document).ready(function () {
    $('.bs-remove-exclusivetour').on('show.bs.modal', function (e) {
        var $invoker = $(e.relatedTarget);
        $('.bs-remove-exclusivetour .modal-body').html('<h4>Вы уверены?</h4><p>Тур невозможно будет восстановить.</p>')
        $('.bs-remove-exclusivetour .modal-footer').html('<button type="button" class="btn btn-danger" id="btn-rmv-dmo" data-data="' + $($invoker).attr('data-data') + '">Удалить</button>')
    });

    $('.bs-remove-exclusivetour .modal-footer').on('click', '#btn-rmv-dmo', function () {
        var id = $(this).attr('data-data');
        $.ajax({
            type: 'POST',
            data: 'id=' + id,
            url: 'ajaxRemoveexclusivetour',
            success: function (data) {
                if (data == 'SUCCESS') {
                    $('#' + id).remove();
                    $('#btn-rmv-dmo').remove();
                    $('.bs-remove-exclusivetour').modal('hide');
                    new PNotify({
                        title: 'Удаление',
                        text: 'Тур успешно удалена',
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
    $("#input-709").fileinput({
        language: "ru",
        theme: "fa",
        uploadUrl: "uploadexclusivetour",
        uploadAsync: true,
        showPreview: false,
        allowedFileExtensions: ['zip'],
        maxFileSize : 2000000,
        maxFileCount: 1,
        autoReplace: true,
        elErrorContainer: '#kv-error-1'
    }).on('filebatchpreupload', function (event, data, id, index) {
    }).on('fileuploaded', function (event, data, id, index) {
        var msg = data.response;
        if (msg.status==='UPDATED') {
            $('a[href^="'+msg.msgObj.path+'"]').closest('tr').remove();
            var newexclusivetour = "<tr id='"+msg.msgObj.id+"'><th scope='row'>"+
                "<a href='"+msg.msgObj.path+"' target='_blank'><i class='fa fa-play-circle-o' title='Просмотр'></i></a>&nbsp;&nbsp;"+
                "<a href='exclTourEdit-"+msg.msgObj.id+"'><i class='fa fa-edit' title='Изменить'></i></a>&nbsp;"+
                "<a data-toggle='modal' data-target='.bs-remove-exclusivetour' data-data='"+msg.msgObj.id+"' title='Удалить'><i class='fa fa-remove'></i></a>"+
                "</th><td>"+msg.msgObj.size+"</td><td>"+msg.msgObj.dateOfDownload+"</td><td style=\"padding:0px;text-align: center;\"><img src=\""+msg.msgObj.logo+"\" height=\"37\"></td><td>"+msg.msgObj.name+"</td></tr>";
            $(".table tbody").append(newexclusivetour);
            new PNotify({
                title: 'Сохранение',
                text: msg.statusMsg,
                type: 'success'
            });
        }
        if (msg.status==='SUCCESS') {
            var newexclusivetour = "<tr id='"+msg.msgObj.id+"'><th scope='row'>"+
                "<a href='"+msg.msgObj.path+"' target='_blank'><i class='fa fa-play-circle-o' title='Просмотр'></i></a>&nbsp;&nbsp;"+
                "<a href='exclTourEdit-"+msg.msgObj.id+"'><i class='fa fa-edit' title='Изменить'></i></a>&nbsp;"+
                "<a data-toggle='modal' data-target='.bs-remove-exclusivetour' data-data='"+msg.msgObj.id+"' title='Удалить'><i class='fa fa-remove'></i></a>"+
                "</th><td>"+msg.msgObj.size+"</td><td>"+msg.msgObj.dateOfDownload+"</td><td style=\"padding:0px;text-align: center;\"><img src=\""+msg.msgObj.logo+"\" height=\"37\"></td><td>"+msg.msgObj.name+"</td></tr>";
            $(".table tbody").append(newexclusivetour);
            new PNotify({
                title: 'Сохранение',
                text: msg.statusMsg,
                type: 'success'
            });
        }
        if (msg.status==='ERROR') {
            new PNotify({
                title: 'Сохранение',
                text: msg.statusMsg,
                type: 'error'
            });
        }
    });
});
