<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="includes/headMeta.jsp" %>
    <title>Развёртки панорам</title>
    <%@ include file="includes/cssBlock.jsp" %>
    <!-- PNotify -->
    <link href="../vendors/pnotify/dist/pnotify.css" rel="stylesheet">
    <link href="../vendors/pnotify/dist/pnotify.custom.min.css" rel="stylesheet">
    <link href="../vendors/pnotify/dist/pnotify.buttons.css" rel="stylesheet">
    <link href="../vendors/bootstrap-fileinput/css/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/tabulator/3.4.6/css/tabulator_simple.min.css" rel="stylesheet">
    <style>
        .table th a i.fa {
            font-size: 18px;
            cursor: pointer;
        }

        .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th, .table > thead > tr > td, .table > thead > tr > th {
            vertical-align: middle;
        }

        div#img_content {
            position: relative;
            margin-top: 15px;
            /*margin: 100px auto 0px auto;*/
            width: 100%;
            height: 200px;
            background-color: steelblue;
        }

        div#img_content_focus {
            position: relative;
            margin-top: 15px;
            width: 100%;
            height: 200px;
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
        }
        .tabulator .tabulator-tableHolder .tabulator-table {
            width: 100%;
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
                            <h2>Загрузка панорам</h2>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <div class="row">
                                <div class="file-loading">
                                    <input id="input-709" name="file" type="file" multiple>
                                </div>
                                <div id="kv-error-1" style="margin-top:10px;display:none"></div>
                                <div id="kv-success-1" class="alert alert-success"
                                     style="margin-top:10px;display:none"></div>
                            </div>
                            <div class="row">
                                <div class="form-inline">
                                    <select name="region" id="region" class="form-control" style="width: 200px;">
                                    <c:forEach items="${regions}" varStatus="vs" var="region">
                                        <option value="${region.name}" label="${region.viewName}">${region.viewName}</option>
                                    </c:forEach>
                                </select>
                                <input type="text" id="scanName" class="form-control" placeholder="Название"/>
                                    <input type="text" id="reScan" name="rescan" class="form-control" placeholder="Перезалить"/>
                                <select name="krpanoConfigPath" id="krpanoConfigPath" class="form-control pull-right" style="width: 250px;">
                                    <c:forEach items="${krpanoConfigList}" varStatus="vs" var="krpanoConfig">
                                    <option value="${krpanoConfig.path}" label="${krpanoConfig.name}" <c:if test="${krpanoConfig.path} eq 'defaultkrpano.config'">selected</c:if> >${krpanoConfig.name}</option>
                                    </c:forEach>
                                </select>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

            <div class="clearfix"></div>

            <div class="row">
                <div class="col-md-12">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2>Развёртки
                                <small> панорам</small>
                            </h2>
                            <ul class="nav navbar-right panel_toolbox">
                                <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
                                <li><a class="collapse-table"><i class="fa fa-minus-square"></i></a></li>
                                <li><a class="recollapse-table"><i class="fa fa-plus-square"></i></a></li>
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
                                <div id="tabulator-table"></div>

                                <div class="modal fade bs-view-panoscan" tabindex="-1" role="dialog"
                                     aria-hidden="true">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">

                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal"><span
                                                        aria-hidden="true">×</span>
                                                </button>
                                                <h4 class="modal-title" id="myModalLabel1">Просмотр исходника</h4>
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
<script src="../vendors/pnotify/dist/pnotify.js"></script>
<script src="../vendors/pnotify/dist/pnotify.custom.min.js"></script>
<script src="../vendors/pnotify/dist/pnotify.buttons.js"></script>
<script src="../vendors/pnotify/dist/pnotify.confirm.js"></script>
<script src="../vendors/pnotify/dist/pnotify.history.js"></script>
<script src="../vendors/bootstrap-fileinput/js/fileinput.js" type="text/javascript"></script>
<script src="../vendors/bootstrap-fileinput/js/locales/ru.js" type="text/javascript"></script>
<script src="../vendors/bootstrap-fileinput/themes/fa/theme.js" type="text/javascript"></script>
<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tabulator/3.4.6/js/tabulator.min.js"></script>
<script type="text/javascript" src="https://momentjs.com/downloads/moment.min.js"></script>
<script src="../js/panoscans-act.js"></script>
</body>
</html>