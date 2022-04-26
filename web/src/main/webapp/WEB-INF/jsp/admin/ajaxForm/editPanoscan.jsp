<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form:form method="post" modelAttribute="panoscanForm" id="panoscanForm">
    <form:hidden path="id" id="id"/>
    <div class="col-md-6">
        <form:label path="path">Файл *:</form:label>
        <form:input path="path" cssClass="form-control" readonly="true"/>
    </div>
    <div class="col-md-6">
        <form:label path="name">Заголовок *:</form:label>
        <form:input path="name" cssClass="form-control"/>
    </div>
    <div class="col-md-12">
        <div class="divider-dashed"></div>
    </div>
    <div class="col-md-6">
        Гео-координаты:
        <div class="col-md-6">
            <form:input path="latitude" id="panoscan-latitude" cssClass="form-control"/>
        </div>
        <div class="col-md-6">
            <form:input path="longitude" id="panoscan-longitude" cssClass="form-control"/>
        </div>
    </div>
    <div class="col-md-6">
        Начальная фокусировка:
        <div class="col-md-6">
            <form:input path="hLookAt" id="hLookAt" cssClass="form-control"/>
        </div>
        <div class="col-md-6">
            <form:input path="vLookAt" id="vLookAt" cssClass="form-control"/>
        </div>
    </div>
    <div class="col-md-12">
        <div class="divider-dashed"></div>
    </div>
    <div class="col-md-12">
        Угол севера:
        <form:input path="north" id="north" cssClass="form-control"/>
    </div>
</form:form>