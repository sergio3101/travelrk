<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:eval expression="@viewProp['url.exclusivetour']" var="urlExclTour" />
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="includes/headMeta.jsp" %>
    <title>Эксклюзивные туры</title>
    <%@ include file="includes/cssBlock.jsp" %>
    <!-- PNotify -->
    <link href="../vendors/pnotify/dist/pnotify.custom.min.css" rel="stylesheet">
    <link href="../vendors/bootstrap-fileinput/css/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
        function clipboard(element) {
            var $temp = $("<input>");
            $("body").append($temp);
            $temp.val($(element).text()).select();
            document.execCommand("copy");
            $temp.remove();
            new PNotify({
                title: 'Сообщение',
                text: 'Ссылка сохранена в буфере',
                type: 'success'
            });
        }
        function clipboardiframe(element) {
            var $temp = $("<input>");
            $("body").append($temp);
            $temp.val("<iframe src=\""+$(element).text()+"\" id=\"tour_frame\" width=\"700\" height=\"410\" border=\"0\" frameborder=\"0\" allowfullscreen=\"true\" mozallowfullscreen=\"true\" webkitallowfullscreen=\"true\" style=\"width: 100%;max-width: 100%;\"></iframe>").select();
            document.execCommand("copy");
            $temp.remove();
            new PNotify({
                title: 'Сообщение',
                text: 'IFRAME сохранен в буфере',
                type: 'success'
            });
        }
    </script>
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
        .setup-title {
            margin-top: 30px;
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
                            <h2>Загрузка
                                <small>эксклюзивных туров</small>
                            </h2>
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
                        </div>
                    </div>
                </div>
            </div>

            <div class="clearfix"></div>

            <div class="row">
                <div class="col-md-12">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2>Эксклюзивные туры</h2>
                            <ul class="nav navbar-right panel_toolbox">
                                <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
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
                                        <th></th>
                                        <th>Размер</th>
                                        <th>Дата загрузки</th>
                                        <th style="text-align: center">Логотип</th>
                                        <th>Заголовок</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${exclusivetourList}" varStatus="vs">
                                        <tr id="${exclusivetourList[vs.index].id}">
                                            <th scope="row">
                                                <a href="${urlExclTour}/${exclusivetourList[vs.index].path}" target="_blank"><i class="fa fa-play-circle-o" title="Просмотр"></i></a>&nbsp;
                                                <a href="exclTourEdit-${exclusivetourList[vs.index].id}"><i class="fa fa-edit" title="Изменить"></i></a>&nbsp;
                                                <a data-toggle="modal" data-target=".bs-remove-exclusivetour" data-data="${exclusivetourList[vs.index].id}" title="Удалить"><i class="fa fa-remove"></i></a>
                                            </th>
                                            <td>${exclusivetourList[vs.index].size}</td>
                                            <td><fmt:formatDate value="${exclusivetourList[vs.index].dateOfDownload}" var="dateOfDownload" type="date" pattern="yyyy-MM-dd"/>${dateOfDownload}</td>
                                            <td style="padding:0px;text-align: center;"><c:if test="${not empty exclusivetourList[vs.index].logo}"><img src="${urlExclTour}/${exclusivetourList[vs.index].logo}" height="37"></c:if></td>
                                            <td>${exclusivetourList[vs.index].name}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <div class="modal fade bs-remove-exclusivetour" tabindex="-1" role="dialog"
                                     aria-hidden="true" style="display: none;">
                                    <div class="modal-dialog modal-sm">
                                        <div class="modal-content">

                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close"><span aria-hidden="true">×</span>
                                                </button>
                                                <h4 class="modal-title" id="myModalLabel2">Удалить тур</h4>
                                            </div>
                                            <div class="modal-body"></div>
                                            <div class="modal-footer">

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
<script src="../vendors/bootstrap-fileinput/js/fileinput.js" type="text/javascript"></script>
<script src="../vendors/bootstrap-fileinput/js/locales/ru.js" type="text/javascript"></script>
<script src="../vendors/bootstrap-fileinput/themes/fa/theme.js" type="text/javascript"></script>
<script src="../js/exclusivetour-act.js"></script>
</body>
</html>