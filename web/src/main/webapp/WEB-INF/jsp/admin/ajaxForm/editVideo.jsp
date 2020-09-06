<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form:form method="post" modelAttribute="videoForm" id="videoForm">
    <form:hidden path="id" id="id"/>
    <form:hidden path="youtubeId" id="youtubeId"/>
    <form:label path="title">Заголовок *:</form:label>
    <form:input path="title" id="youtube-title" cssClass="form-control"/>
    <form:label path="region">Регион *:</form:label>
    <form:select path="region.id" items="${regionList}" itemValue="id" itemLabel="viewName" id="youtube-region" cssClass="form-control"/>
    <form:label path="categoryOfContent">Категория *:</form:label>
    <form:select path="categoryOfContent.id" items="${categoryList}" itemValue="id" itemLabel="viewName" id="youtube-categoryOfContent" cssClass="form-control"/>
    <form:label path="description">Краткое описание *:</form:label>
    <form:textarea path="description" id="youtube-description" cssClass="form-control"/>
    <div class="divider-dashed"></div>
    <form:label path="latitude">Широта *:</form:label>
    <form:input path="latitude" id="youtube-latitude" cssClass="form-control"/>
    <form:label path="longitude">Долгота *:</form:label>
    <form:input path="longitude" id="youtube-longitude" cssClass="form-control"/>
</form:form>
