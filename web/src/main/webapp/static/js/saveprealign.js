function savePreAlign(value,name) {
    $.ajax({
        type: 'POST',
        data: 'name=' + name+'&prealign='+value,
        url: '/admin/ajaxSavePreAlign',
        success: function (data) {
            if (data == 'OK') {
                alert("Данные prealign сохранены!");
            } else {
                alert("Данные prealign НЕ сохранились!");
            }
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
            alert(msg);
        }
    });
}
