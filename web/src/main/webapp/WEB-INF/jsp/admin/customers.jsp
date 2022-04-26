<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="includes/headMeta.jsp" %>
    <title>Список заказчиков</title>
    <%@ include file="includes/cssBlock.jsp" %>
    <!-- PNotify -->
    <link href="../vendors/pnotify/dist/pnotify.custom.min.css" rel="stylesheet">
    <link href="../vendors/bootstrap-fileinput/css/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
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
            <div class="row">
                <div class="col-md-12">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2>Список  заказчиков</h2>
                            <ul class="nav navbar-right panel_toolbox">
                                <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                </li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                       aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="customerEdit-0">Новый заказчик</a></li>
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
<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tabulator/3.4.6/js/tabulator.min.js"></script>
<script type="text/javascript" src="https://momentjs.com/downloads/moment.min.js"></script>
<script src="../js/customers.js" type="text/javascript"></script>
</body>
</html>