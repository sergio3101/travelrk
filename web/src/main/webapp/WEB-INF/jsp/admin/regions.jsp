<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="includes/headMeta.jsp" %>
    <title>Справочник регионов</title>
    <%@ include file="includes/cssBlock.jsp" %>
    <!-- PNotify -->
    <link href="../vendors/pnotify/dist/pnotify.custom.min.css" rel="stylesheet">
    <link href="../vendors/bootstrap-fileinput/css/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
    <style>
        .table th a i.fa {
            font-size: 18px;
            cursor: pointer;
        }

        .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th, .table > thead > tr > td, .table > thead > tr > th {
            vertical-align: middle;
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
                            <h2>Список регионов</h2>
                            <ul class="nav navbar-right panel_toolbox">
                                <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                </li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                       aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="regionEdit-0">Новый регион</a></li>
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
                                        <th>Регион</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${regionList}" varStatus="vs">
                                        <tr id="${regionList[vs.index].id}">
                                            <th scope="row">
                                                <a href="regionEdit-${regionList[vs.index].id}">
                                                    <i class="fa fa-edit" title="Изменить"></i>
                                                </a>&nbsp;
                                                <a data-toggle="modal" data-target=".bs-remove-regions"
                                                   data-data="${regionList[vs.index].id}" title="Удалить"><i
                                                        class="fa fa-remove"></i></a>&nbsp;&nbsp;
                                                <i class="fa fa-info" style="font-size: 18px;"></i>
                                            </th>
                                            <td>${regionList[vs.index].viewName}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <div class="modal fade bs-remove-regions" tabindex="-1" role="dialog"
                                     aria-hidden="true" style="display: none;">
                                    <div class="modal-dialog modal-sm">
                                        <div class="modal-content">

                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close"><span aria-hidden="true">×</span>
                                                </button>
                                                <h4 class="modal-title" id="myModalLabel2">Удалить регион</h4>
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
<script src="../js/regions.js" type="text/javascript"></script>
</body>
</html>