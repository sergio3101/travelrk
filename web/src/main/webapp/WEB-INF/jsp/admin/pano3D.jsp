<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="includes/headMeta.jsp" %>
    <title>3D панорамы</title>
    <%@ include file="includes/cssBlock.jsp" %>
    <link href="../vendors/pnotify/dist/pnotify.css" rel="stylesheet">
    <link href="../vendors/pnotify/dist/pnotify.custom.min.css" rel="stylesheet">
    <link href="../vendors/pnotify/dist/pnotify.buttons.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/tabulator/3.4.6/css/tabulator_simple.min.css" rel="stylesheet">
    <style>
        .table th a i.fa {
            font-size: 18px;
            cursor: pointer;
        }

        .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th, .table > thead > tr > td, .table > thead > tr > th {
            vertical-align: middle;
        }

        .tabulator .tabulator-tableHolder .tabulator-table {
            width: 100%;
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
            <div class="">
                <div class="row">
                    <div class="col-md-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>3D панорамы</h2>
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
                                    <%--<c:if test="${not empty panoramalist}">
                                        <table class="table">
                                            <thead>
                                            <tr>
                                                <th></th>
                                                <th>Название</th>
                                                <th>Регион</th>
                                                <th>Фильтр</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${panoramalist}" varStatus="vs">
                                                <tr id="pano-${panoramalist[vs.index].id}">
                                                    <th scope="row">
                                                        <a href="/pano/${panoramalist[vs.index].panoPath}" target="_blank"><i class="fa fa-play-circle-o" title="Просмотр"></i></a>&nbsp;
                                                        <a href="pano3Dedit-${panoramalist[vs.index].id}"><i class="fa fa-edit" title="Изменить"></i></a>&nbsp;
                                                        <a data-toggle="modal" data-target=".bs-remove-pano3d" data-data="${panoramalist[vs.index].id}" title="Удалить">
                                                            <i class="fa fa-remove"></i>
                                                        </a>&nbsp;&nbsp;
                                                        <img src="../img/3d-icon.png" width="30" height="28">&nbsp;${panoramalist[vs.index].panoPath}
                                                    </th>
                                                    <td>${panoramalist[vs.index].title}</td>
                                                    <td>${panoramalist[vs.index].region.viewName}</td>
                                                    <td>${panoramalist[vs.index].categoryOfContent.viewName}</td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:if>--%>

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
<script src="../vendors/pnotify/dist/pnotify.js"></script>
<script src="../vendors/pnotify/dist/pnotify.custom.min.js"></script>
<script src="../vendors/pnotify/dist/pnotify.buttons.js"></script>
<script src="../vendors/pnotify/dist/pnotify.confirm.js"></script>
<script src="../vendors/pnotify/dist/pnotify.history.js"></script>
<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tabulator/3.4.6/js/tabulator.min.js"></script>
<script type="text/javascript" src="https://momentjs.com/downloads/moment.min.js"></script>
<script src="../js/pano3d-act.js"></script>
</body>
</html>