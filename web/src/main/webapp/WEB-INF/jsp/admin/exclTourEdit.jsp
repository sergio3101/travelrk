<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:eval expression="@viewProp['url.exclusivetour']" var="urlExclTour" />
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="includes/headMeta.jsp" %>
    <title>Тур - ${exclusiveTour.name}</title>
    <%@ include file="includes/cssBlock.jsp" %>
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>
    <script src="../js/pano3Dedit-ym.js"></script>
    <script src="/krengine/pano.js"></script>
    <!-- PNotify -->
    <link href="../vendors/pnotify/dist/pnotify.custom.min.css" rel="stylesheet">
    <link href="../vendors/switchery/dist/switchery.min.css" rel="stylesheet">
    <link href="../vendors/normalize-css/normalize.css" rel="stylesheet">
    <link href="../vendors/ion.rangeSlider/css/ion.rangeSlider.css" rel="stylesheet">
    <link href="../vendors/ion.rangeSlider/css/ion.rangeSlider.skinNice.css" rel="stylesheet">
    <link href="../vendors/iCheck/skins/flat/green.css" rel="stylesheet">
    <link href="../vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
    <link href="../vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.css" rel="stylesheet">
    <link href="../vendors/lou-multi-select/css/multi-select.css" rel="stylesheet">
    <link href="../build/css/custom.min.css" rel="stylesheet">
    <link href="../vendors/prism/prism.css" rel="stylesheet">
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

        #map {
            margin: 0px 0px 0px 10px;
            border: 1px solid #99a4ac;
            padding: 5px;
            width: 848px;
            height: 400px;
        }

        #scenaForm label {
            margin-top: 10px;
        }

        #scenaNorth {
            width: 100%;
            height: 400px;
            border: 1px solid #99a4ac;
            padding: 5px;
        }

        .codescroll {
            height: 600px;
            overflow-y: auto;
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
                            <h2>Эксклюзивный тур -
                                <small>${exclusiveTour.name}</small>
                            </h2>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <form:form method="post" commandName="exclusiveTour" id="exclusiveTour"
                                       cssClass="form-horizontal form-label-left input_mask"
                                       data-parsley-validate="true">
                                <form:hidden path="id" id="id"/>
                                <div class="col-xs-2">
                                    <!-- required for floating -->
                                    <!-- Nav tabs -->
                                    <ul class="nav nav-tabs tabs-left">
                                        <li class="active"><a href="#main" data-toggle="tab">Основные настройки</a></li>
                                        <li><a href="#rentatab" data-toggle="tab">Аренда панорам</a></li>
                                        <li><a href="#tourscens" data-toggle="tab">Сцены тура</a></li>
                                        <li><a href="#krpanoxml" data-toggle="tab">XML код</a></li>
                                    </ul>
                                    <a href="/admin/exclusivetour" class="btn btn-info pull-right"
                                       style="margin-top: 5px;">Закрыть</a>
                                    <button type="submit" class="btn btn-success pull-right" style="margin-top: 5px;">
                                        Сохранить
                                    </button>
                                </div>

                                <div class="col-xs-10">
                                    <!-- Tab panes -->
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="main">
                                            <p class="lead">Инфо</p>
                                            <div class="row">
                                                <div class="col-md-2"><strong>Путь к туру:</strong></div>
                                                <div class="col-md-10"><a href="${urlExclTour}/${exclusiveTour.path}"
                                                                          target="_blank">${exclusiveTour.path}</a>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-2"><strong>Размер тура:</strong></div>
                                                <div class="col-md-10">${exclusiveTour.size}</div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-2"><strong>Дата загрузки тура:</strong></div>
                                                <div class="col-md-10">${exclusiveTour.dateOfDownload}</div>
                                            </div>
                                            <div class="col-md-12">
                                                <div class="divider-dashed"></div>
                                            </div>
                                            <p class="lead">Основные настройки</p>
                                            <div class="form-group">
                                                <label class="control-label col-md-2" for="name">Заголовок:</label>
                                                <div class="col-md-10">
                                                    <div class="input-group input-group-sm col-md-10">
                                                        <form:input path="name" id="name"
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
                                        </div>
                                        <div class="tab-pane" id="rentatab">
                                            <p class="lead">Аренда панорам</p>
                                            <div class="form-group">
                                                <form:label path="aRenta"
                                                            cssClass="control-label col-md-2">Аренда:</form:label>
                                                <div class="col-md-10">
                                                    <div class="">
                                                        <label>
                                                            <form:checkbox path="aRenta" id="aRenta"
                                                                           cssClass="js-switch field"/>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <form:label path="hsForRenta"
                                                            cssClass="control-label col-md-2">Панорамы для аренды:</form:label>
                                            </div>
                                            <div class="form-group">
                                                <div class="col-md-12">
                                                    <form:select multiple="true" path="hsForRenta">
                                                        <c:forEach var="group" items="${groupsOfPano}">
                                                            <optgroup label='${group.key}'>
                                                                <form:options items="${group.value}"
                                                                              itemValue="panoPath" itemLabel="title"/>
                                                            </optgroup>
                                                        </c:forEach>
                                                    </form:select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="tourscens">
                                            <p class="lead">Сцены тура</p>
                                            <c:forEach var="scena" items="${exclusiveTour.scenes}">
                                                <div class="col-md-55">
                                                    <div class="thumbnail">
                                                        <div class="image view view-first">
                                                            <img style="width: 100%; display: block;"
                                                                 src="${urlExclTour}/${fn:replace(exclusiveTour.path, '.html', 'data')}/${scena.dir}/thumbnail_bt.jpg"
                                                                 alt="image"/>
                                                            <div class="mask">
                                                                <p>${fn:substring(scena.title, 0, 26)}...</p>
                                                                <div class="tools tools-bottom">
                                                                    <a href="${urlExclTour}/${exclusiveTour.path}?scn=${scena.name}"
                                                                       target="_blank"><i class="fa fa-link"></i></a>
                                                                    <a data-toggle="modal" data-target=".bs-edit-scena"
                                                                       data-name="${scena.name}"
                                                                       data-tourid="${exclusiveTour.id}" href=""><i
                                                                            class="fa fa-pencil"
                                                                            title="Редактировать"></i></a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="caption">
                                                            <p>${scena.title}</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <div class="tab-pane" id="krpanoxml">
                                            <p class="lead">XML код тура</p>
                                            <div class="form-group">
                                                <div class="col-md-12">
                                                    <section class="language-markup language-actionscript">
                                                        <pre class="codescroll">
                                                            <code class="language-markup language-actionscript"><c:out value="${exclusiveTour.krpanoXml}" escapeXml="true"/></code>
                                                        </pre>
                                                    </section>
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
        <div class="modal fade bs-edit-scena" tabindex="-1" role="dialog"
             aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">

                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title" id="myModalLabel">${exclusiveTour.name}</h4>
                    </div>
                    <div class="modal-body">
                        <form id="scenaForm" data-parsley-validate="" novalidate="">
                            <input type="hidden" id="id"/>
                            <input type="hidden" id="dir"/>
                            <input type="hidden" id="name"/>
                            <input type="hidden" id="title"/>
                            <div id="scenaNorth"></div>
                            <input type="hidden" id="height"/>
                            <input type="hidden" id="latitude"/>
                            <input type="hidden" id="longitude"/>
                            <input type="hidden" id="north"/>
                            <input type="hidden" id="exclusiveTour.id"/>
                        </form>
                        <input type="hidden" id="deltaNorth"/>
                        <div id="map"></div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            Закрыть
                        </button>
                        <button type="button" class="btn btn-primary" id="saveScena">
                            Сохранить
                        </button>
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
<script src="../vendors/lou-multi-select/js/jquery.multi-select.js"></script>
<script src="../vendors/prism/prism.js"></script>
<script src="../js/exclTourEdit-act.js"></script>
</body>
</html>