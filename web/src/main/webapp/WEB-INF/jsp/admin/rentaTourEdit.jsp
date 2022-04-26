<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="includes/headMeta.jsp" %>
    <title>Тур - ${rentaTour.name}</title>
    <%@ include file="includes/cssBlock.jsp" %>
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
                            <h2>Арендный тур -
                                <small>${rentaTour.name}</small>
                            </h2>
                            <div class="clearfix"></div>
                            <form:errors path="rentaTour"/>
                        </div>
                        <div class="x_content">
                            <form:form method="post" commandName="rentaTour" id="rentaTour"
                                       cssClass="form-horizontal form-label-left input_mask"
                                       data-parsley-validate="true">
                                <form:hidden path="id" id="id"/>
                                <form:hidden path="dateOfCreate" id="dateOfCreate"/>
                                <form:hidden path="path" id="path"/>
                                <form:hidden path="defaultPano" id="defaultPano"/>
                                <form:hidden path="user.id"/>
                                <div class="col-xs-2">
                                    <!-- required for floating -->
                                    <!-- Nav tabs -->
                                    <ul class="nav nav-tabs tabs-left">
                                        <li class="active"><a href="#main" data-toggle="tab">Основные настройки</a></li>
                                        <li><a href="#paymenttab" data-toggle="tab">Оплата</a></li>
                                        <li><a href="#rentatab" data-toggle="tab">Аренда панорам</a></li>
                                        <li><a href="#iframecode" data-toggle="tab">IFRAME код</a></li>
                                    </ul>
                                    <a href="/admin/rentatour" class="btn btn-info pull-right"
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
                                                <div class="col-md-10"><a href="https://travelrk.ru/rentatours/${rentaTour.path}/"
                                                                          target="_blank">https://travelrk.ru/rentatours/${rentaTour.path}</a>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-2"><strong>Дата создания тура:</strong></div>
                                                <div class="col-md-10">${rentaTour.dateOfCreate}</div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-2"><strong>Тур создал:</strong></div>
                                                <div class="col-md-10">${rentaTour.user.fio}</div>
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
                                            <div class="form-group">
                                                <label class="control-label col-md-2"
                                                       for="description">Заказчик *:</label>
                                                <div class="col-md-10">
                                                    <div class="input-group input-group-sm col-md-10">
                                                        <form:select path="customerInfo.id" items="${customers}"
                                                                     itemValue="id" itemLabel="companyName"
                                                                     cssClass="form-control field col-md-10">
                                                        </form:select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="tab-pane" id="paymenttab">
                                            <p class="lead">Оплата</p>
                                            <div class="form-group">
                                                <label class="control-label col-md-2"
                                                       for="description">Сумма аренды тура:</label>
                                                <div class="col-md-10">
                                                    <div class="input-group input-group-sm col-md-10">
                                                        <form:input path="sum" id="sum"
                                                                    cssClass="form-control field col-md-10"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-2"
                                                       for="description">Количество месяцев:</label>
                                                <div class="col-md-10">
                                                    <div class="input-group input-group-sm col-md-10">
                                                        <form:input path="monthCount" id="monthCount"
                                                                    cssClass="form-control field col-md-10"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-2"
                                                       for="description">В счет будущих платежей:</label>
                                                <div class="col-md-10">
                                                    <div class="input-group input-group-sm col-md-10">
                                                        <form:checkbox path="isFuturePayment" id="isFuturePayment"
                                                                       cssClass="js-switch field col-md-10"/>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div class="tab-pane" id="rentatab">
                                            <p class="lead">Аренда панорам</p>
                                            <div class="form-group">
                                                <form:label path="rentaExpired" cssClass="control-label col-md-2">Дата истечения срока аренды:</form:label>
                                                <div class="col-md-7">
                                                    <div class="input-group input-group-sm">
                                                        <div class="col-md-10 xdisplay_inputx form-group has-feedback">
                                                            <form:input path="rentaExpired" id="rentaExpired" cssClass="form-control has-feedback-left field" aria-describedby="inputSuccess2Status3" placeholder="Укажите дату"/>
                                                            <span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
                                                            <span id="inputSuccess2Status3" class="sr-only">(success)</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <form:label path="hsForRenta"
                                                            cssClass="control-label col-md-2">Панорамы для аренды:</form:label>
                                                <div class="col-md-10">
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

                                            <div class="form-group">
                                                <form:label path="domain" cssClass="control-label col-md-2">Домен:</form:label>
                                                <div class="col-md-6">
                                                    <form:input path="domain" id="domain" cssClass="form-control field"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="iframecode">
                                            <p class="lead">IFRAME код</p>
                                            <div class="form-group">
                                                <div class="col-md-12">
                                                    <section class="language-markup">
                                                        <pre class="language-html pre-wrap"><code><c:set var = "iframe" value = '<iframe src="https://travelrk.ru/rentatours/${rentaTour.path}/" id="tour_frame" width="700" height="410" border="0" frameborder="0" allowfullscreen="true" mozallowfullscreen="true" webkitallowfullscreen="true" style="width: 100%;max-width: 100%;"></iframe>'/>${fn:escapeXml(iframe)}</code></pre>
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
<script src="../js/rentaTourEdit-act.js"></script>
</body>
</html>