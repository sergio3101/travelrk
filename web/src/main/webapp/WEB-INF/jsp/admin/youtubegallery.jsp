<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="includes/headMeta.jsp" %>
    <title>Ролики с youtube!</title>
    <%@ include file="includes/cssBlock.jsp" %>
    <!-- PNotify -->
    <link href="../vendors/pnotify/dist/pnotify.custom.min.css" rel="stylesheet">
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>
    <style>
        .table th a i.fa {
            font-size: 18px;
            cursor: pointer;
        }

        .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th, .table > thead > tr > td, .table > thead > tr > th {
            vertical-align: middle;
        }

        #map {
            margin: 15px 0px 0px 0px;
            padding: 0;
            width: 100%;
            height: 238px;
        }
    </style>
    <script type="text/javascript">
        var myMap, myPlacemark, coords = [45.1956, 34.0539], defcoords = [45.1956, 34.0539];

        ymaps.ready(init);

        function init() {
            myMap = new ymaps.Map('map', {
                center: defcoords,
                zoom: 7,
                controls: ['zoomControl', 'typeSelector']
            });
            /*var searchControl = new ymaps.control.SearchControl({
             options: {
             float: 'left',
             floatIndex: 100,
             noPlacemark: true
             }
             });
             myMap.controls.add(searchControl);*/

            //Определяем метку и добавляем ее на карту
            myPlacemark = new ymaps.Placemark(defcoords, {}, {preset: "islands#redIcon", draggable: true});
            myMap.geoObjects.add(myPlacemark);

            //Отслеживаем событие перемещения метки
            myPlacemark.events.add("dragend", function (e) {
                coords = this.geometry.getCoordinates();
                savecoordinats();
            }, myPlacemark);

            //Отслеживаем событие щелчка по карте
            myMap.events.add('click', function (e) {
                coords = e.get('coords');
                savecoordinats();
            });

            /*//Отслеживаем событие выбора результата поиска
             searchControl.events.add("resultselect", function (e) {
             coords = searchControl.getResultsArray()[0].geometry.getCoordinates();
             savecoordinats();
             });*/

            //Ослеживаем событие изменения области просмотра карты - масштаб и центр карты
            myMap.events.add('boundschange', function (event) {
                if (event.get('newZoom') != event.get('oldZoom')) {
//                    savecoordinats();
                }
                if (event.get('newCenter') != event.get('oldCenter')) {
//                    savecoordinats();
                }
            });
        }

        //Функция для передачи полученных значений в форму
        function savecoordinats() {
            var new_lat = coords[0].toFixed(4);
            var new_lon = coords[1].toFixed(4);
            var new_coords = [new_lat, new_lon];
            myPlacemark.getOverlaySync().getData().geometry.setCoordinates(new_coords);
            document.getElementById("youtube-latitude").value = new_lat;
            document.getElementById("youtube-longitude").value = new_lon;
//            document.getElementById("mapzoom").value = myMap.getZoom();
//            var center = myMap.getCenter();
//            var new_center = [center[0].toFixed(4), center[1].toFixed(4)];
//            document.getElementById("latlongcenter").value = new_center;
        }
    </script>
</head>

<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <%@ include file="includes/leftSide.jsp" %>

        <%@ include file="includes/topNav.jsp" %>

        <!-- page content -->
        <div class="right_col" role="main">
            <div class="">
                <div class="page-title">
                    <div class="title_left">
                        <h3> Видео ролики с youtube</h3>
                    </div>

                    <div class="title_right">
                        <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="Search for...">
                                <span class="input-group-btn">
                        <button class="btn btn-default" type="button">Go!</button>
                    </span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="clearfix"></div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>Видео  ролики с youtube</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>
                                    <li><a class="dropdown-toggle info-number" id="newYoutube"
                                           data-toggle="dropdown" aria-expanded="true"><i class="fa fa-youtube"
                                                                                          title="Добавить ролик"></i></a>
                                        <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">
                                            <li>
                                                <div class="input-group">
                                                    <input type="text" class="form-control" id="addID" name="addID" style="width: 200px;" placeholder="Youtube code"/>
                                                    <span class="input-group-btn">
                                                        <button type="button" class="btn btn-danger" id="btnAddVideo"><i class="fa fa-youtube" title="Добавить ролик"></i> Добавить</button>
                                                    </span>
                                                </div>
                                            </li>
                                        </ul>
                                    </li>
                                    <li class="dropdown">
                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                           aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                        <ul class="dropdown-menu" role="menu">
                                            <li><a href="#">Settings 1</a>
                                            </li>
                                            <li><a href="#">Settings 2</a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="row">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th>code</th>
                                            <th>Название</th>
                                            <th>Регион</th>
                                            <th>Фильтр</th>
                                            <th>Описание</th>
                                            <th>Широта</th>
                                            <th>Долгота</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="video" items="${videoList}">
                                            <tr id="${video.youtubeId}">
                                                <th scope="row">
                                                    <a data-toggle="modal" data-target=".bs-view-youtube"
                                                       data-data="${video.youtubeId}"><i class="fa fa-eye"
                                                                                              title="Просмотр"></i></a>&nbsp;
                                                    <a data-toggle="modal" data-target=".bs-edit-youtube"
                                                       data-data="${video.youtubeId}"><i class="fa fa-edit"
                                                                                              title="Изменить"></i></a>&nbsp;
                                                    <a data-toggle="modal" data-target=".bs-remove-youtube"
                                                       data-data="${video.youtubeId}" title="Удалить"><i
                                                            class="fa fa-remove"></i></a>&nbsp;&nbsp;
                                                    <img src="../img/youtube-ico.png" width="30"
                                                         height="30">&nbsp;${video.youtubeId}
                                                </th>
                                                <td>${video.title}</td>
                                                <td>${video.region.viewName}</td>
                                                <td>${video.categoryOfContent.viewName}</td>
                                                <td>${video.description}</td>
                                                <td>${video.latitude}</td>
                                                <td>${video.longitude}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>

                                    <div class="modal fade bs-remove-youtube" tabindex="-1" role="dialog"
                                         aria-hidden="true" style="display: none;">
                                        <div class="modal-dialog modal-sm">
                                            <div class="modal-content">

                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close"><span aria-hidden="true">×</span>
                                                    </button>
                                                    <h4 class="modal-title" id="myModalLabel2">Удалить ролик</h4>
                                                </div>
                                                <div class="modal-body"></div>
                                                <div class="modal-footer">

                                                </div>

                                            </div>
                                        </div>
                                    </div>


                                    <div class="modal fade bs-edit-youtube" tabindex="-1" role="dialog"
                                         aria-hidden="true">
                                        <div class="modal-dialog modal-lg">
                                            <div class="modal-content">

                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"><span
                                                            aria-hidden="true">×</span>
                                                    </button>
                                                    <h4 class="modal-title" id="myModalLabel">Редактор роликов с
                                                        youtube</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <img id="imgYoutube" src=""
                                                                 alt="" style="width: 100%;"/>
                                                            <div id="map"></div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <form id="videoForm" data-parsley-validate="" novalidate="">

                                                                <input type="hidden" id="youtubeID" class="form-control"
                                                                       name="youtubeID" required="">

                                                                <label for="youtube-title">Заголовок *:</label>
                                                                <input type="text" id="youtube-title"
                                                                       class="form-control"
                                                                       name="title" required="">

                                                                <label for="youtube-region">Регион *:</label>
                                                                <select id="youtube-region" class="form-control"
                                                                        name="region"
                                                                        required="">
                                                                    <c:forEach items="${regionList}" var="region">
                                                                        <option value="${region.name}">${region.viewName}</option>
                                                                    </c:forEach>
                                                                </select>
                                                                <label for="youtube-categoryOfContent">Фильтр *:</label>
                                                                <select id="youtube-categoryOfContent" class="form-control"
                                                                        name="categoryOfContent"
                                                                        required="">
                                                                    <c:forEach items="${categoryList}" var="category">
                                                                    <option value="${category.name}">${category.viewName}</option>
                                                                    </c:forEach>
                                                                </select>

                                                                <label for="youtube-description">Краткое описание
                                                                    *:</label>
                                                                <textarea id="youtube-description" name="description"
                                                                          class="form-control" rows="3"
                                                                          placeholder=""></textarea>

                                                                <div class="divider-dashed"></div>

                                                                <label for="youtube-latitude">Широта *:</label>
                                                                <input type="text" id="youtube-latitude"
                                                                       class="form-control"
                                                                       name="latitude" required="">

                                                                <label for="youtube-longitude">Долгота *:</label>
                                                                <input type="text" id="youtube-longitude"
                                                                       class="form-control"
                                                                       name="longitude" required="">
                                                            </form>
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-default" data-dismiss="modal">
                                                        Закрыть
                                                    </button>
                                                    <button type="button" class="btn btn-primary" id="saveVideo">
                                                        Сохранить
                                                    </button>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal fade bs-view-youtube" tabindex="-1" role="dialog"
                                         aria-hidden="true">
                                        <div class="modal-dialog modal-lg">
                                            <div class="modal-content">

                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"><span
                                                            aria-hidden="true">×</span>
                                                    </button>
                                                    <h4 class="modal-title" id="myModalLabel1">Просмотр роликов с
                                                        youtube</h4>
                                                </div>
                                                <div class="modal-body"></div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-default" data-dismiss="modal">
                                                        Закрыть
                                                    </button>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /page content -->

        <%@ include file="includes/footerContent.jsp" %>
    </div>
</div>
<%@ include file="includes/footerScripts.jsp" %>
<!-- PNotify -->
<script src="../vendors/pnotify/dist/pnotify.custom.min.js"></script>
<script>
    $(document).ready(function () {
//        PNotify.prototype.options.styling = "bootstrap3";

        $('#newYoutube').on('click', function () {
            $('input[name=addID]').focus();
        });
        $('#btnAddVideo').on('click', function (e) {

            var idForAdd = $("#addID").val();
            $.ajax({
                type: 'POST',
                data: 'idForAdd=' + idForAdd,
                url: 'ajaxAddVideo',
                success: function (result) {
                    switch (result) {
                        case 'SUCCESS':
                            window.location = "/admin/youtubegallery";
                            break;
                        case 'DUBLICAT':
                            new PNotify({
                                title: 'Дубликат',
                                text: 'Данный ролик уже загружен!',
                                type: 'error'
                            });
                            break;
                        case 'ERROR':
                            new PNotify({
                                title: 'Сохранение',
                                text: 'Произошла ошибка при сохранении изменений!',
                                type: 'error'
                            });
                            break;
                        case 'YTBERROR':
                            new PNotify({
                                title: 'Ошибка загрузки с Youtube',
                                text: 'Ролика с таким ID не существует!',
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
                        title: 'Ошибка',
                        text: msg,
                        type: 'error'
                    });
                }
            });
        });
        $('.bs-view-youtube').on('show.bs.modal', function (e) {
            var $invoker = $(e.relatedTarget);
            $('.bs-view-youtube .modal-body').html('<iframe width="100%" height="488" src="https://www.youtube.com/embed/' + $($invoker).attr('data-data') + '?autoplay=1" frameborder="0" allowfullscreen></iframe>')
        });
        $('.bs-view-youtube').on('hidden.bs.modal', function () {
            $('.bs-view-youtube .modal-body iframe').remove();
        });

        $('#saveVideo').on('click', function () {
            var video = $('#videoForm').serialize();
            var idytb = $('#youtubeId').val();
            var title = $('#youtube-title').val();
            var categoryOfContent = $('#youtube-categoryOfContent option:selected').text();
            var region = $('#youtube-region option:selected').text();
            var description = $('#youtube-description').val();
            var latitude = $('#youtube-latitude').val();
            var longitude = $('#youtube-longitude').val();
            $.ajax({
                type: 'POST',
                data: video,
                url: 'ajaxSaveVideo',
                success: function (data) {
                    switch (data) {
                        case 'SUCCESS':
                            $('#' + idytb + " td:nth-child(2)").html(title);
                            $('#' + idytb + " td:nth-child(3)").html(region);
                            $('#' + idytb + " td:nth-child(4)").html(categoryOfContent);
                            $('#' + idytb + " td:nth-child(5)").html(description);
                            $('#' + idytb + " td:nth-child(6)").html(latitude);
                            $('#' + idytb + " td:nth-child(7)").html(longitude);
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
                        case 'YTBERROR':
                            new PNotify({
                                title: 'Ошибка загрузки с Youtube',
                                text: 'Ролика с таким ID не существует!',
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
        $('.bs-edit-youtube').on('show.bs.modal', function (e) {
            var $invoker = $(e.relatedTarget);
            var idForEdit = $($invoker).attr('data-data');
            $.ajax({
                type: 'POST',
                data: 'idForEdit=' + idForEdit,
//                url: 'ajaxGetVideoById',
                url: 'ajaxForm/ajaxGetVideoFormById',
                success: function (videoForm) {
                    $("#videoForm").html(videoForm);
                    $('#imgYoutube').attr('src', '/images/youtubetumbs/' + idForEdit + '.jpg');
                    var lat=$('#youtube-latitude').val();
                    var lng=$('#youtube-longitude').val();
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
/*                    $('#youtubeID').val(video.youtubeID);
                    $('#youtube-title').val(video.title);
                    $('#youtube-region').val(video.region.name).select();
                    $('#youtube-categoryOfContent').val(video.categoryOfContent.name).select();
                    $('#youtube-description').val(video.description);
                    $('#youtube-latitude').val(video.latitude);
                    $('#youtube-longitude').val(video.longitude);*/
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
        $('.bs-remove-youtube').on('show.bs.modal', function (e) {
            var $invoker = $(e.relatedTarget);
            $('.bs-remove-youtube .modal-body').html('<h4>Вы уверены?</h4><p>Ролик невозможно будет восстановить.</p>')
            $('.bs-remove-youtube .modal-footer').html('<button type="button" class="btn btn-danger" id="btn-rmv-ytb" data-data="' + $($invoker).attr('data-data') + '">Удалить</button>')
        });
        $('.bs-remove-youtube .modal-footer').on('click', '#btn-rmv-ytb', function () {
            var idytb = $(this).attr('data-data');
            $.ajax({
                type: 'POST',
                data: 'idytb=' + idytb,
                url: 'ajaxRemove',
                success: function (data) {
                    if (data == 'SUCCESS') {
                        $('#' + idytb).remove();
                        $('#btn-rmv-ytb').remove();
                        $('.bs-remove-youtube').modal('hide');
                        new PNotify({
                            title: 'Удаление',
                            text: 'Ролик успешно удален',
                            type: 'success'
                        });

                    };
                    if (data == 'ERROR') {
                        new PNotify({
                            title: 'Удаление',
                            text: 'При удалении ролика произошла ошибка',
                            type: 'success'
                        });
                        $('#btn-rmv-ytb').remove();
                    };
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
</script>
</body>
</html>