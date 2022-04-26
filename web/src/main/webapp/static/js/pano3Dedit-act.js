window.ParsleyValidator.setLocale('ru');
$(document).ready(function () {

    var selectline = document.getElementById('selection_line');
    var container = document.getElementById('img_content');
    var container_focus = document.getElementById('img_content_focus');
    var northico = document.getElementById('selection_north');
    var targetico = document.getElementById('selection_focus');
    var selectionangle = document.getElementById('selection_angle');
    var pos = 0;
    var focus_posx = 0;
    var focus_posy = 0;

    var imgPath = $("form#panorama input#panoPath").val();
    var hLookAt = $("form#panorama input#hLookAt").val();
    var vLookAt = $("form#panorama input#vLookAt").val();
    var north = $("form#panorama input#north").val();
    var lat = $('form#panorama input#latitude').val();
    var lng = $('form#panorama input#longitude').val();
    var crds = [];
    $('a[href^="#position"]').on('click', function () {
        if (lat != "" && lng != "") {
            crds = [lat, lng];
            myPlacemark.geometry.setCoordinates(crds);
            myMap.setCenter(crds);
            myMap.setZoom(7);
        } else {
            myPlacemark.geometry.setCoordinates(defcoords);
            myMap.setCenter(defcoords);
            myMap.setZoom(7);
        }
    });
    var npx = north / 180 * 212;
    targetico.style.left = degToPxX(hLookAt) - 25 + 'px';
    targetico.style.top = degToPxY(vLookAt) - 25 + 'px';
    northico.style.left = degToPxX(-north) - 7 + 'px';
    var imgPano = $('.imgPanoscan');
    var imgPanoFocus = $('.imgPanoscanFocus');
    imgPano.css('background-image', 'url(/pano/' + imgPath + '/thumb.jpg)');
    imgPanoFocus.css('background-image', 'url(/pano/' + imgPath + '/thumb.jpg)');
    imgPanoFocus.css('background-position-x', npx + 'px');

    function pxToDegX(px) {
        var deg = px / 212 * 180 - 180;
        return -deg.toFixed(2);
    }

    function degToPxX(deg) {
        var px = deg / 180 * 212 + 212;
        return px.toFixed(0);
    }

    function pxToDegY(px) {
        var deg = px / 106 * 90 - 90;
        return deg.toFixed(2);
    }

    function degToPxY(deg) {
        var px = deg / 90 * 106 + 106;
        return px.toFixed(0);
    }

    $(container).on('mousemove', function (e) {
        var offset = $("#img_content").offset();
        var offsetLeft = 0;
        offsetLeft = offset.left;
        pos = e.pageX - offsetLeft.toFixed(0);
        $(selectionangle).html(pxToDegX(pos) + '&deg;');
        selectionangle.style.left = pos - 23 + 'px';
        selectline.style.left = pos + 'px';
    });
    $(container_focus).on('mousemove', function (e) {
        var offset = $("#img_content_focus").offset();
        var offsetLeft = 0;
        var offsetTop = 0;
        offsetLeft = offset.left;
        offsetTop = offset.top;
        focus_posx = e.pageX - offsetLeft.toFixed(0);
        focus_posy = e.pageY - offsetTop.toFixed(0);
    });
    $(container).on('click', function () {
        northico.style.left = pos - 7 + 'px';
        var north = pxToDegX(pos);
        var npx = north / 180 * 212;
        $("#north").val(north);
        $(".imgPanoscanFocus").css('background-position-x', npx + 'px');
    });
    $(container_focus).on('click', function () {
        targetico.style.left = focus_posx - 25 + 'px';
        targetico.style.top = focus_posy - 25 + 'px';
        $("#hLookAt").val(-pxToDegX(focus_posx));
        $("#vLookAt").val(pxToDegY(focus_posy));
    });
    $(document).on('focusout', '#north', function () {
        var update_north = degToPxX($("#north").val());
        northico.style.left = update_north - 7 + 'px';
        // console.log(degToPxX($("#north").val()));
    });
    $(document).on('focusout', '#hLookAt', function () {
        var hor = degToPxX($("#hLookAt").val());
        targetico.style.left = hor - 25 + 'px';
    });
    $(document).on('focusout', '#vLookAt', function () {
        var vert = degToPxY($("#vLookAt").val());
        targetico.style.top = vert - 25 + 'px';
    });

    $('#btn-genpano').on('click', function () {
        $('#panoForm').parsley().validate();
        if ($('#panoForm').parsley().isValid()) {
            var panoPath = $("#panoscan").val();
            $('#panoPath').val(panoPath.slice(0, -4));
            var panoform = $('#panoForm').serialize();
            $.ajax({
                type: 'POST',
                data: panoform,
                url: 'ajaxGenPano3D',
                success: function (data) {
                    if (data === 'SUCCESS') {
                        new PNotify({
                            title: 'Генерация',
                            text: 'Панорама успешно сгенерирована',
                            type: 'success'
                        });
                    }
                    if (data === 'ERROR') new PNotify({
                        title: 'Генерация',
                        text: 'Произошла ошибка при генераци панорамы!',
                        type: 'error'
                    });
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
                        title: 'Ошибка генерации',
                        text: msg,
                        type: 'error'
                    });
                }
            });
        }
    });

    $('#btnRegen').on('click', function () {
        $("html").addClass('loading');
        $.ajax({
            type: 'POST',
            data: 'panoPath=' + $("#panoPath").val() + '&krpanoConfigPath='+$("#krpanoConfigPath option:selected").val(),
            url: 'ajaxReGenPano3D',
            success: function (data) {
                if (data !== null) {
                    $("html").removeClass('loading');
                    new PNotify({
                        title: 'Регенерация',
                        text: 'Панорама успешно регенерирована',
                        type: 'success'
                    });
                } else {
                    new PNotify({
                        title: 'Регенерация',
                        text: 'Произошла ошибка при регенераци панорамы!',
                        type: 'error'
                    });
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
                    title: 'Ошибка генерации',
                    text: msg,
                    type: 'error'
                });
            }
        });
    });
});
