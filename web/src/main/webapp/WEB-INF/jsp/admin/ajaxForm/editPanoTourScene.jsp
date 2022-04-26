<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form:form method="post" modelAttribute="scenaForm" id="scenaForm">
    <form:hidden path="id"/>
    <form:hidden path="dir"/>
    <form:hidden path="name"/>
    <h4>${scenaForm.title}</h4>
    <form:hidden path="title"/>
    <div class="form-group">
        <form:label path="showCustomerInfo" cssClass="control-label col-md-3">Показывать заказчика:</form:label>
        <div class="col-md-9 col-sm-9 col-xs-12">
            <div class="">
                <label>
                    <form:checkbox path="showCustomerInfo" id="showCustomerInfo" cssClass="js-switch field"/>
                </label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <form:label path="height" cssClass="control-label col-md-12">Высота сцены:<p>height</p></form:label>
        <div class="col-md-12">
            <form:input path="height" id="height" cssClass="form-control field col-md-12"/>
        </div>
    </div>
    <div class="form-group">
        <form:label path="north" cssClass="control-label col-md-12">Установка севера:<p>north</p></form:label>
        <div class="col-md-12">
            <div id="scenaNorth"></div>
            <form:hidden path="north"/>
        </div>
    </div>
    <div class="form-group">
        <form:label path="latitude" cssClass="control-label col-md-12">Установка GPS координат:<p>lat,lon</p></form:label>
        <div class="col-md-12">
            <form:hidden path="latitude"/>
            <form:hidden path="longitude"/>
        </div>
    </div>
    <form:hidden path="panoTourSrc.id"/>
</form:form>