<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="includes/headMeta.jsp" %>
    <title>3D панорама - ${panorama.title}</title>
    <%@ include file="includes/cssBlock.jsp" %>
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>
    <script src="../js/pano3Dedit-ym.js"></script>
    <!-- PNotify -->
    <link href="../vendors/pnotify/dist/pnotify.custom.min.css" rel="stylesheet">
    <link href="../vendors/switchery/dist/switchery.min.css" rel="stylesheet">
    <link href="../vendors/normalize-css/normalize.css" rel="stylesheet">
    <link href="../vendors/ion.rangeSlider/css/ion.rangeSlider.css" rel="stylesheet">
    <link href="../vendors/ion.rangeSlider/css/ion.rangeSlider.skinNice.css" rel="stylesheet">
    <link href="../vendors/iCheck/skins/flat/green.css" rel="stylesheet">
    <link href="../vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
    <link href="../vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.css" rel="stylesheet">
    <link href="../build/css/custom.min.css" rel="stylesheet">
    <style>
        .table th a i.fa {
            font-size: 18px;
            cursor: pointer;
        }

        .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th, .table > thead > tr > td, .table > thead > tr > th {
            vertical-align: middle;
        }

        .control-label p {
            font-weight: normal;
            color: #525252;
        }

        div#img_content {
            width: 425px;
            position: relative;
            margin-top: 15px;
            /*margin: 100px auto 0px auto;*/
            height: 213px;
            background-color: steelblue;
        }

        div#img_content_focus {
            position: relative;
            margin-top: 15px;
            width: 425px;
            height: 213px;
            background-color: steelblue;
        }

        div#selection_line {
            position: absolute;
            z-index: 10;
            top: 0px;
            width: 1px;
            height: 213px;
            background-color: rgba(0, 0, 0, 0.6);
        }

        div#selection_angle {
            position: absolute;
            z-index: 10;
            top: -16px;
            width: 46px;
            height: 20px;
            text-align: center;
        }

        img#selection_north {
            position: absolute;
            z-index: 11;
            top: 213px;
            left: -7px;
        }

        img#selection_focus {
            position: absolute;
            z-index: 12;
            top: 106px;
            left: 212px;
        }

        .imgPanoscan {
            width: 425px;
            height: 213px;
            background-repeat-x: repeat;
            background-position-x: 0px;
        }

        .imgPanoscanFocus {
            width: 425px;
            height: 213px;
            background-repeat-x: repeat;
            background-position-x: 0px;
        }

        #map {
            margin: 15px 0px 0px 0px;
            padding: 0;
            width: 100%;
            height: 213px;
        }

        .setup-title {
            margin-top: 30px;
            font-weight: bold;
        }

        html {
            -webkit-transition: background-color 1s;
            transition: background-color 1s;
        }

        html, body {
            /* For the loading indicator to be vertically centered ensure */
            /* the html and body elements take up the full viewport */
            min-height: 100%;
        }

        html.loading {
            /* Replace #333 with the background-color of your choice */
            /* Replace loading.gif with the loading image of your choice */
            background: #333 url('/img/loading.gif') no-repeat center center fixed;
            -webkit-background-size: 80px;
            -moz-background-size: 80px;
            -o-background-size: 80px;
            background-size: 80px;

            /* Ensures that the transition only runs in one direction */
            -webkit-transition: background-color 0.6;
            transition: background-color 0.6;
        }

        body {
            -webkit-transition: opacity 1s ease-in;
            transition: opacity 1s ease-in;
        }

        html.loading body {
            /* Make the contents of the body opaque during loading */
            opacity: 0.6;

            /* Ensures that the transition only runs in one direction */
            -webkit-transition: opacity 0.6;
            transition: opacity 0.6;
        }
    </style>

</head>

<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <%@ include file="includes/leftSide.jsp" %>

        <%@ include file="includes/topNav.jsp" %>

        <!-- page content -->
        <div class="right_col" role="main">
            <div class="row">
                <div class="col-md-12">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2>3D панорама -
                                <small>${panorama.panoPath}</small>
                            </h2>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <form:form method="post" commandName="panorama" id="panorama"
                                       cssClass="form-horizontal form-label-left input_mask"
                                       data-parsley-validate="true">
                                <form:hidden path="id"/>
                                <form:hidden path="panoPath"/>
                                <form:hidden path="info"/>
                                <form:hidden path="va"/>
                                <form:hidden path="vc"/>
                                <form:hidden path="sceneXml"/>
                                <form:hidden path="panoScan"/>
                                <form:hidden path="panoramaScan.id"/>
                                <div class="col-xs-2">
                                    <!-- required for floating -->
                                    <!-- Nav tabs -->
                                    <ul class="nav nav-tabs tabs-left">
                                        <li class="active"><a href="#main" data-toggle="tab">Основные настройки</a>
                                        </li>
                                        <li><a href="#position" data-toggle="tab">Позиционирование</a>
                                        </li>
                                        <li><a href="#plugins" data-toggle="tab">Плагины</a>
                                        </li>
                                    </ul>
                                    <a href="/admin/pano3D" class="btn btn-error pull-right" style="margin-top: 5px;"><i
                                            class="fa fa-close"></i></a>
                                    <button type="submit" class="btn btn-success pull-right" style="margin-top: 5px;"><i
                                            class="fa fa-save"></i></button>
                                    <button type="button" id="btnRegen" class="btn btn-info pull-right"
                                            style="margin-top: 5px;"><i class="fa fa-refresh"></i></button>
                                    <div class="row">
                                        <select name="krpanoConfigPath" id="krpanoConfigPath"
                                                class="form-control pull-right" style="width: 250px;">
                                            <c:forEach items="${krpanoConfigList}" varStatus="vs" var="krpanoConfig">
                                                <option value="${krpanoConfig.path}" label="${krpanoConfig.name}"
                                                        <c:if test="${krpanoConfig.path} eq 'defaultkrpano.config'">selected</c:if> >${krpanoConfig.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="col-xs-10">
                                    <!-- Tab panes -->
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="main">
                                            <p class="lead">Инфо</p>
                                            <div class="row">
                                                <div class="col-md-2"><strong>Дата генерации:</strong></div>
                                                <div class="col-md-10">${panorama.dateOfCreate}</div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-2"><strong>Путь к панораме:</strong></div>
                                                <div class="col-md-10"><a href="/pano/${panorama.panoPath}"
                                                                          target="_blank">${panorama.panoPath}</a></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-2"><strong>Слои:</strong></div>
                                                <div class="col-md-10">${panorama.info}</div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-2"><strong>Развертка:</strong></div>
                                                <div class="col-md-10">${panorama.panoScan}</div>
                                            </div>
                                            <div class="col-md-12">
                                                <div class="divider-dashed"></div>
                                            </div>
                                            <p class="lead">Основные настройки</p>
                                            <div class="form-group">
                                                <label class="control-label col-md-2" for="title">Заголовок:</label>
                                                <div class="col-md-10">
                                                    <div class="input-group input-group-sm col-md-10">
                                                        <form:input path="title" id="title"
                                                                    cssClass="form-control field col-md-10"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-2"
                                                       for="description">Описание:</label>
                                                <div class="col-md-10">
                                                    <div class="input-group input-group-sm col-md-10">
                                                        <form:input path="description" id="description"
                                                                    cssClass="form-control field col-md-10"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-2" for="text">Текст:</label>
                                                <div class="col-md-10">
                                                    <div class="input-group input-group-sm col-md-10">
                                                        <form:input path="text" id="text"
                                                                    cssClass="form-control field col-md-10"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="region"
                                                            cssClass="control-label col-md-2">Регион *:</form:label>
                                                <div class="col-md-10">
                                                    <div class="input-group input-group-sm col-md-10">
                                                        <form:select path="region.id" items="${regionList}"
                                                                     itemValue="id" itemLabel="viewName"
                                                                     cssClass="form-control field col-md-10"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="categoryOfContent"
                                                            cssClass="control-label col-md-2">Категория *:</form:label>
                                                <div class="col-md-10">
                                                    <div class="input-group input-group-sm col-md-10">
                                                        <form:select path="categoryOfContent.id" items="${categoryList}"
                                                                     itemValue="id" itemLabel="viewName"
                                                                     cssClass="form-control field col-md-10"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="position">
                                            <p class="lead">Позиционирование</p>
                                            <div class="form-group">
                                                <form:label path="aCompassOn"
                                                            cssClass="control-label col-md-12">Air-пано:<p>aAir</p></form:label>
                                                <div class="col-md-12">
                                                    <form:checkbox path="aAir" id="aAir"
                                                                   cssClass="js-switch field"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="height"
                                                            cssClass="control-label col-md-12">Высота панорамы:<p>height</p></form:label>
                                                <div class="col-md-12">
                                                    <form:input path="height" id="height"
                                                                cssClass="form-control field col-md-12"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="fov"
                                                            cssClass="control-label col-md-12">Вертикальная область видимости:<p>fov</p></form:label>
                                                <div class="col-md-12">
                                                    <form:input path="fov" id="fov"
                                                                cssClass="form-control field col-md-12"/>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12" style="width:425px;">
                                                    <div class="setup-title">Текущие параметры</div>
                                                    <div style="margin-top: 15px">
                                                        <div class="col-md-6">
                                                            Гео-координаты:
                                                            <div class="col-md-6">
                                                                <form:input path="latitude" id="latitude"
                                                                            cssClass="form-control"/>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <form:input path="longitude" id="longitude"
                                                                            cssClass="form-control"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            Начальная фокусировка:
                                                            <div class="col-md-6">
                                                                <form:input path="hLookAt" id="hLookAt"
                                                                            cssClass="form-control"/>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <form:input path="vLookAt" id="vLookAt"
                                                                            cssClass="form-control"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-12">
                                                            <div class="divider-dashed"></div>
                                                        </div>
                                                        <div class="col-md-12">
                                                            Угол севера:
                                                            <form:input path="north" id="north"
                                                                        cssClass="form-control"/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="setup-title">Настройка угла севера</div>
                                                    <div id="img_content">
                                                        <div class="imgPanoscan"></div>
                                                        <img id="selection_north" src="../img/north-ico1.png">
                                                        <div id="selection_angle"></div>
                                                        <div id="selection_line"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="setup-title">Настройка точки начальной фокусировки
                                                    </div>
                                                    <div id="img_content_focus">
                                                        <div class="imgPanoscanFocus"></div>
                                                        <img id="selection_focus" src="../img/target-icon.png">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12" style="width:600px;">
                                                    <div class="setup-title">Настройка гео-координат</div>
                                                    <div id="map"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="plugins">
                                            <p class="lead">Плагины</p>
                                            <div class="form-group">
                                                <form:label path="aCompassOn"
                                                            cssClass="control-label col-md-3">Компас:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="aCompassOn" id="aCompassOn"
                                                                           cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                            </form:form>
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
<script src="../vendors/switchery/dist/switchery.min.js"></script>
<script src="../vendors/parsleyjs/dist/parsley.min.js"></script>
<script src="../vendors/jquery-knob/dist/jquery.knob.min.js"></script>
<script src="../vendors/ion.rangeSlider/js/ion.rangeSlider.min.js"></script>
<script src="../vendors/parsleyjs/dist/i18n/ru.js"></script>
<script src="../vendors/iCheck/icheck.js"></script>
<script src="../vendors/moment/min/moment.min.js"></script>
<script src="../vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
<script src="../vendors/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
<script src="../js/pano3Dedit-act.js"></script>
</body>
</html>