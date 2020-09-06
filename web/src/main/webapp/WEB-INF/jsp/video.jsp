<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <%@ include file="includes/headMeta.jsp" %>
    <title>Видео</title>
    <%@ include file="includes/headStyle.jsp" %>
    <link rel="stylesheet" href="css/jquery.fancybox.min.css"/>
</head>
<body>
<%@ include file="includes/topMenu.jsp" %>
<div class="container">
    <ul id="filters" class="clearfix">
        <c:forEach var="category" items="${categoryList}">
        <li id="${category.icoPath}"><span class="filter" data-filter=".${category.name}">${category.viewName}</span></li>
        </c:forEach>
        <li id="gps_gr"><span class="filter active" data-filter=".natural, .pansionats">Всё вместе</span></li>
    </ul>
    <div id="portfoliolist">
        <c:forEach var="video" items="${videoList}">
        <div class="portfolio ${video.categoryOfContent.name}" data-cat="${video.categoryOfContent.name}">
            <div class="portfolio-wrapper" data-fancybox
                 href="https://www.youtube.com/watch?v=${video.youtubeId}">
                <div class="icon-bg"></div>
                <img src="/images/youtubetumbs/${video.youtubeId}.jpg" alt=""/>
                <span class="glyphicon glyphicon glyphicon-film icon-video" aria-hidden="true"></span>
                <div class="label">
                    <div class="label-text">
                    ${video.title}
                        <span class="text-category">${video.region.viewName}</span>
                    </div>
                    <div class="label-bg"></div>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>
</div><!-- container -->
<%@ include file="includes/footerScripts.jsp" %>
<script src="js/jquery.fancybox.min.js"></script>
<script type="text/javascript" src="js/jquery.mixitup.min.js"></script>
<script type="text/javascript" src="js/videoFancy.js"></script>
</body>
</html>