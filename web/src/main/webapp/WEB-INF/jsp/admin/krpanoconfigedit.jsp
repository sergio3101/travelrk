<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="includes/headMeta.jsp" %>
    <title>KrPano конфигурации</title>
    <%@ include file="includes/cssBlock.jsp" %>
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
                            <h2>KrPano конфигурация</h2>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <form:form method="post" commandName="krpanoConfig" id="krpanoConfig" cssClass="form-horizontal form-label-left input_mask" data-parsley-validate="true">
                                <form:hidden path="id" id="id"/>
                                <div class="col-xs-2">
                                    <!-- required for floating -->
                                    <!-- Nav tabs -->
                                    <ul class="nav nav-tabs tabs-left">
                                        <li class="active"><a href="#main" data-toggle="tab">Основные настройки</a>
                                        </li>
                                        <li><a href="#source" data-toggle="tab">Исходник</a>
                                        </li>
                                        <li><a href="#converting" data-toggle="tab">Конвертирование</a>
                                        </li>
                                        <li><a href="#multiresolution" data-toggle="tab">Мультиразрешение</a>
                                        </li>
                                        <li><a href="#previewsetting" data-toggle="tab">Превью</a>
                                        </li>
                                        <li><a href="#qualitysetting" data-toggle="tab">Качество</a>
                                        </li>
                                        <li><a href="#securitysetting" data-toggle="tab">Безопасность</a>
                                        </li>
                                    </ul>
                                </div>

                                <div class="col-xs-10">
                                    <!-- Tab panes -->
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="main">
                                            <p class="lead">Основные настройки</p>
                                            <div class="form-group">
                                                <label class="control-label col-md-3" for="path">Имя файла</label>
                                                <div class="col-md-7">
                                                    <div class="input-group input-group-sm">
                                                        <label class="input-group-addon" for="path">path</label>
                                                        <form:input path="path" id="path" cssClass="form-control field"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-3" for="name">Имя конфигурации</label>
                                                <div class="col-md-7">
                                                    <div class="input-group input-group-sm">
                                                        <label class="input-group-addon" for="name">name</label>
                                                        <form:input path="name" id="name" cssClass="form-control field"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="parsegps" cssClass="control-label col-md-3">Получать EXIF GPS информацию из исходников:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="parsegps" id="parsegps" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="flash" cssClass="control-label col-md-3">Поддержка Flash:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="flash" id="flash" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="html5" cssClass="control-label col-md-3">Поддержка HTML5:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="html5" id="html5" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="ignorelayers" cssClass="control-label col-md-3">Игнорировать слои в PSD/PSB:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="ignorelayers" id="ignorelayers" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="sortinput" cssClass="control-label col-md-3">Сортировать входные файлы изображений по имени:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="sortinput" id="sortinput" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-3" for="autolevel">Получать из EXIF или XMP ориентацию/нивелирование:</label>
                                                <div class="col-md-7">
                                                    <div class="input-group input-group-sm">
                                                        <label class="input-group-addon" for="autolevel">autolevel</label>
                                                        <form:select path="autolevel" id="autolevel" cssClass="form-control field" data-parsley-required="true">
                                                            <form:options items="${autoLevels}"/>
                                                        </form:select>
                                                        <span class="input-group-addon">действие</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="stereosupport" cssClass="control-label col-md-3">Поддержка стерео:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="stereosupport" id="stereosupport" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="mobileVersion" cssClass="control-label col-md-3">Генерировать мобильную версию:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="mobileVersion" id="mobileVersion" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="makethumb" cssClass="control-label col-md-3">Генерировать thumbnail:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="makethumb" id="makethumb" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="thumbsize" cssClass="control-label col-md-3">Размер thumbnail:<p>thumbsize</p></form:label>
                                                <div class="col-md-7">
                                                    <form:input path="thumbsize" id="thumbsize" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-3" for="tilepath">Структура папок изображений</label>
                                                <div class="col-md-7">
                                                    <div class="input-group input-group-sm">
                                                        <label class="input-group-addon" for="tilepath">tilepath</label>
                                                        <form:input path="tilepath" id="tilepath" cssClass="form-control field"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="source">
                                            <p class="lead">Параметры исходника развертки</p>
                                            <div class="form-group">
                                                <label class="control-label col-md-3" for="panotype">Тип панорамы:</label>
                                                <div class="col-md-7">
                                                    <div class="input-group input-group-sm">
                                                        <label class="input-group-addon" for="panotype">panotype</label>
                                                        <form:select path="panotype" id="panotype" cssClass="form-control field" data-parsley-required="true">
                                                            <form:options items="${panoTypes}"/>
                                                        </form:select>
                                                        <span class="input-group-addon">тип</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="hfov" cssClass="control-label col-md-3">Горизонтальная область видимости:<p>hfov</p></form:label>
                                                <div class="col-md-7">
                                                    <form:input path="hfov" id="hfov" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="vfov" cssClass="control-label col-md-3">Вертикальная область видимости:<p>vfov</p></form:label>
                                                <div class="col-md-7">
                                                    <form:input path="vfov" id="vfov" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="voffset" cssClass="control-label col-md-3">Вертикальное смещение:<p>voffset</p></form:label>
                                                <div class="col-md-7">
                                                    <form:input path="voffset" id="voffset" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="converting">
                                            <p class="lead">Параметры конвертирования (сферы/цилиндра в куб)</p>
                                            <div class="form-group">
                                                <form:label path="converttocube" cssClass="control-label col-md-3">Конвертировать в куб:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="converttocube" id="converttocube" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-3" for="converttocubeformat">Временный формат конвертирования:</label>
                                                <div class="col-md-7">
                                                    <div class="input-group input-group-sm">
                                                        <label class="input-group-addon" for="converttocubeformat">converttocubeformat</label>
                                                        <form:select path="converttocubeformat" id="converttocubeformat" cssClass="form-control field" data-parsley-required="true">
                                                            <form:options items="${cubeFormats}"/>
                                                        </form:select>
                                                        <span class="input-group-addon">формат</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="converttocubelimit360x" cssClass="control-label col-md-3">Область видимости при конвертировании:<p>converttocubelimit360x</p></form:label>
                                                <div class="col-md-7">
                                                    <form:input path="converttocubelimit360x" id="converttocubelimit360x" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="multiresolution">
                                            <p class="lead">Параметры мультиразрешения</p>
                                            <div class="form-group">
                                                <form:label path="multires" cssClass="control-label col-md-3">Генерировать мультиразрешение:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="multires" id="multires" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="tilesize" cssClass="control-label col-md-3">Размер мультиразрешения:<p>tilesize</p></form:label>
                                                <div class="col-md-7">
                                                    <form:input path="tilesize" id="tilesize" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="levels" cssClass="control-label col-md-3">Число уровней генерации:<p>levels</p></form:label>
                                                <div class="col-md-7">
                                                    <form:input path="levels" id="levels" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="levelstep" cssClass="control-label col-md-3">Шаг уровня генерации:<p>levelstep</p></form:label>
                                                <div class="col-md-7">
                                                    <form:input path="levelstep" id="levelstep" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="minsize" cssClass="control-label col-md-3">Минимальный размер (высота):<p>minsize</p></form:label>
                                                <div class="col-md-7">
                                                    <form:input path="minsize" id="minsize" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="maxsize" cssClass="control-label col-md-3">Максимальный размер (высота):<p>maxsize</p></form:label>
                                                <div class="col-md-7">
                                                    <form:input path="maxsize" id="maxsize" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="maxcubesize" cssClass="control-label col-md-3">Максимальный размер куба (ширина/высота):<p>maxcubesize</p></form:label>
                                                <div class="col-md-7">
                                                    <form:input path="maxcubesize" id="maxcubesize" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="previewsetting">
                                            <p class="lead">Параметры Параметры превью</p>
                                            <div class="form-group">
                                                <form:label path="preview" cssClass="control-label col-md-3">Генерировать превью:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="preview" id="preview" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="graypreview" cssClass="control-label col-md-3">Ч/Б превью:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="graypreview" id="graypreview" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="cspreview" cssClass="control-label col-md-3">Генерировать кубическое превью:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="cspreview" id="cspreview" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="previewsmooth" cssClass="control-label col-md-3">Размытие превью:<p>previewsmooth</p></form:label>
                                                <div class="col-md-7">
                                                    <form:input path="previewsmooth" id="previewsmooth" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="previewspsize" cssClass="control-label col-md-3">Размер (ширина) сферической превью:<p>previewspsize</p></form:label>
                                                <div class="col-md-7">
                                                    <form:input path="previewspsize" id="previewspsize" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="previewcssize" cssClass="control-label col-md-3">Размер (ширина) кубической превью:<p>previewcssize</p></form:label>
                                                <div class="col-md-7">
                                                    <form:input path="previewcssize" id="previewcssize" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-3" for="previewpath">Путь и имя файла превью:</label>
                                                <div class="col-md-7">
                                                    <div class="input-group input-group-sm">
                                                        <label class="input-group-addon" for="previewpath">previewpath</label>
                                                        <form:input path="previewpath" id="previewpath" cssClass="form-control field"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="qualitysetting">
                                            <p class="lead">Параметры качества изображений</p>
                                            <div class="form-group">
                                                <form:label path="jpegquality" cssClass="control-label col-md-3">Jpeg качество сжатия:<p>jpegquality</p></form:label>
                                                <div class="col-md-7">
                                                    <form:input path="jpegquality" id="jpegquality" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-3" for="jpegsubsamp">Jpeg <a href="https://en.wikipedia.org/wiki/Chroma_subsampling" target="_blank">Chroma subsampling</a>:</label>
                                                <div class="col-md-7">
                                                    <div class="input-group input-group-sm">
                                                        <label class="input-group-addon" for="jpegsubsamp">jpegsubsamp</label>
                                                        <form:select path="jpegsubsamp" id="jpegsubsamp" cssClass="form-control field" data-parsley-required="true">
                                                            <form:options items="${jpegSubSamps}"/>
                                                        </form:select>
                                                        <span class="input-group-addon">метод</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="jpegoptimize" cssClass="control-label col-md-3">Jpeg оптимизация:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="jpegoptimize" id="jpegoptimize" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="jpegprogressive" cssClass="control-label col-md-3">Прогрессивное Jpeg кодирование:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="jpegprogressive" id="jpegprogressive" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-3" for="filter">Фильтр генерации низкого разрешения:</label>
                                                <div class="col-md-7">
                                                    <div class="input-group input-group-sm">
                                                        <label class="input-group-addon" for="filter">filter</label>
                                                        <form:select path="filter" id="filter" cssClass="form-control field" data-parsley-required="true">
                                                            <form:options items="${imageFilters}"/>
                                                        </form:select>
                                                        <span class="input-group-addon">фильтр</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="securitysetting">
                                            <p class="lead">Параметры безопасности</p>
                                            <div class="form-group">
                                                <form:label path="protect" cssClass="control-label col-md-3">Включить защиту:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="" style="padding-left: 10px;">
                                                        <label>
                                                            <form:checkbox path="protect" id="protect" cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="noep" cssClass="control-label col-md-3">Отключить внешние параметры:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="checkbox">
                                                        <label>
                                                            <form:checkbox path="noep" id="noep" cssClass="flat field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="nojs" cssClass="control-label col-md-3">Отключить JS интерфейс:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="checkbox">
                                                        <label>
                                                            <form:checkbox path="nojs" id="nojs" cssClass="flat field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="nolu" cssClass="control-label col-md-3">Отключить offline использование:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="checkbox">
                                                        <label>
                                                            <form:checkbox path="nolu" id="nolu" cssClass="flat field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="noex" cssClass="control-label col-md-3">Отключить загрузку компонентов с внешних доменов:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="checkbox">
                                                        <label>
                                                            <form:checkbox path="noex" id="noex" cssClass="flat field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="pxml" cssClass="control-label col-md-3">Загружать только шифрованые XML:</form:label>
                                                <div class="col-md-9 col-sm-9 col-xs-12">
                                                    <div class="checkbox">
                                                        <label>
                                                            <form:checkbox path="pxml" id="pxml" cssClass="flat field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="domain" cssClass="control-label col-md-3">Ограничить просмотр доменом:</form:label>
                                                <div class="col-md-7">
                                                    <div class="input-group input-group-sm" style="padding-left: 10px;">
                                                        <label class="input-group-addon" for="domain">domain</label>
                                                        <form:input path="domain" id="domain" cssClass="form-control field" placeholder="domain.com"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="expire" cssClass="control-label col-md-3">Установить дату отключения панорамы:</form:label>
                                                <div class="col-md-7">
                                                    <div class="input-group input-group-sm">
                                                        <div class="col-md-11 xdisplay_inputx form-group has-feedback">
                                                            <form:input path="expire" id="expire" cssClass="form-control has-feedback-left field" aria-describedby="inputSuccess2Status3" placeholder="Укажите дату"/>
                                                            <span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
                                                            <span id="inputSuccess2Status3" class="sr-only">(success)</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>

                                <div class="ln_solid"></div>
                                <div class="form-group">
                                    <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                                        <button type="submit" class="btn btn-success">Сохранить</button>
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
<script>
    var def = ${def};
</script>
<script src="../js/krpanoedit.js"></script>
</body>
</html>