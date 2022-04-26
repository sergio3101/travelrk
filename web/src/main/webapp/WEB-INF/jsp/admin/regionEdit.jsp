<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="includes/headMeta.jsp" %>
    <title>Регион - ${region.viewName}</title>
    <%@ include file="includes/cssBlock.jsp" %>
    <!-- PNotify -->
    <link href="../vendors/pnotify/dist/pnotify.custom.min.css" rel="stylesheet">
    <link href="../vendors/switchery/dist/switchery.min.css" rel="stylesheet">
    <link href="../vendors/normalize-css/normalize.css" rel="stylesheet">
    <link href="../vendors/ion.rangeSlider/css/ion.rangeSlider.css" rel="stylesheet">
    <link href="../vendors/ion.rangeSlider/css/ion.rangeSlider.skinNice.css" rel="stylesheet">
    <link href="../vendors/iCheck/skins/flat/green.css" rel="stylesheet">
    <link href="../vendors/bootstrap-fileinput/css/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
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
            height: 100%;
        }

        .setup-title {
            margin-top: 30px;
            font-weight: bold;
        }
        .kv-avatar .krajee-default.file-preview-frame,.kv-avatar .krajee-default.file-preview-frame:hover {
            margin: 0;
            padding: 0;
            border: none;
            box-shadow: none;
            text-align: center;
        }
        .kv-avatar {
            display: inline-block;
        }
        .kv-avatar .file-input {
            display: table-cell;
            width: 213px;
        }
        .kv-reqd {
            color: red;
            font-family: monospace;
            font-weight: normal;
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
                            <h2>Регион - <small>${region.viewName}</small></h2>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <form:form method="post" commandName="region" id="region" cssClass="form-horizontal form-label-left input_mask" data-parsley-validate="true" enctype="multipart/form-data">
                                <form:hidden path="id" id="id"/>
                                <form:hidden path="name" id="name"/>
                                <form:hidden path="region_id" id="region_id"/>
                                <div class="col-md-10">
                                    <div class="form-group">
                                        <form:label path="viewName"
                                                    class="control-label col-md-2">Название региона:</form:label>
                                        <div class="col-md-10">
                                            <div class="input-group input-group-sm col-md-10">
                                                <form:input path="viewName" cssClass="form-control field col-md-10"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <form:label path="description" class="control-label col-md-2">Описание:</form:label>
                                        <div class="col-md-10">
                                            <div class="input-group input-group-sm col-md-10">
                                                <form:input path="description" cssClass="form-control field col-md-10"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="ln_solid"></div>
                                <div class="form-group">
                                    <div class="col-md-2">
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
<script src="../vendors/bootstrap-fileinput/js/locales/ru.js" type="text/javascript"></script>
<script src="../vendors/bootstrap-fileinput/themes/fa/theme.js" type="text/javascript"></script>
</body>
</html>