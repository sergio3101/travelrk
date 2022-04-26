window.ParsleyValidator.setLocale('ru');
function setNorth(delta) {
    $('input#deltaNorth').val(delta.toFixed(2));
}
$(document).ready(function () {
    $('#hsForRenta').multiSelect({
        keepOrder: true,
        selectableHeader: "Панорамы",
        selectionHeader: "в аренде"

    });

    $('.bs-edit-scena').on('show.bs.modal', function (e) {
        var $invoker = $(e.relatedTarget);
        var name = $($invoker).attr('data-name');
        var tourId = $($invoker).attr('data-tourid');
        $.ajax({
            type: 'POST',
            data: 'name=' + name+'&'+'tourid=' + tourId,
            url: 'ajaxForm/ajaxGetScenaFormByName',
            success: function (scenaForm) {
                $("#scenaForm").html(scenaForm);
                $('input#height').ionRangeSlider({
                    type: "single",
                    min: 0,
                    max: 1000,
                    postfix: 'M',
                    grid: true,
                    force_edges: true
                });
                removepano("currentScena");
                embedpano({swf:"/krengine/pano.swf", xml:"getSceneXml.xml?scenaId="+$('#scenaForm input#id').val(), target:"scenaNorth"});
                var lat=$('#latitude').val();
                var lng=$('#longitude').val();
                var crds=[];
                if (lat!=""&&lng!="") {
                    crds = [lat, lng];
                    myPlacemark.geometry.setCoordinates(crds);
                    myMap.setCenter(crds);
                    myMap.setZoom(7);
                } else {
                    myPlacemark.geometry.setCoordinates(defcoords);
                    myMap.setCenter(defcoords);
                    myMap.setZoom(7);
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
                new PNotify({
                    title: 'Ошибка получения данных',
                    text: msg,
                    type: 'error'
                });
            }
        });
    });
    $('#saveScena').on('click', function () {
        var latitude = $('#latitude').val();
        var longitude = $('#longitude').val();
        var north = $('#north').val();
        var deltaNorth = $('#deltaNorth').val();
        if (north=='') north = 0;
        if (deltaNorth=='') deltaNorth = 0;
        var newNorth = north - deltaNorth;
        $('#north').val(newNorth.toFixed(2));
        var scena = $('#scenaForm').serialize();
        $.ajax({
            type: 'POST',
            data: scena,
            url: 'ajaxSaveScena',
            success: function (data) {
                switch (data) {
                    case 'SUCCESS':
                        $('.bs-edit-scena').modal('hide');
                        new PNotify({
                            title: 'Сохранение',
                            text: 'Изменения внесены успешно!',
                            type: 'success'
                        });
                        var lat=latitude;
                        var lng = longitude;
                        var crds;
                        if (lat!=""&&lng!="") {
                            crds = [lat, lng];
                            myPlacemark.geometry.setCoordinates(crds);
                            myMap.setCenter(crds);
                            myMap.setZoom(7);
                        } else {
                            myPlacemark.geometry.setCoordinates(defcoords);
                            myMap.setCenter(defcoords);
                            myMap.setZoom(7);
                        }
                        break;
                    case 'ERROR':
                        new PNotify({
                            title: 'Сохранение',
                            text: 'Произошла ошибка при сохранении изменений!',
                            type: 'error'
                        });
                        break;
                    default:
                        new PNotify({
                            title: 'Ответ от сервера',
                            text: 'Получен пустой ответ от сервера!',
                            type: 'error'
                        })
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
                new PNotify({
                    title: 'Ошибка соединения',
                    text: msg,
                    type: 'error'
                });
            }
        });
    });
});
