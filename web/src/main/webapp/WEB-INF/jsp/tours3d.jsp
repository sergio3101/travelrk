<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <%@ include file="includes/headMeta.jsp" %>
    <title>3D Туры</title>
    <%@ include file="includes/headStyle.jsp" %>
    <link rel="stylesheet" href="css/jquery.fancybox.min.css"/>
</head>
<body>
<%@ include file="includes/topMenu.jsp" %>
<div class="container">
    <div id="portfoliolist">
        <c:forEach var="tour" items="${tourList}">
            <div class="portfolio" data-cat="all">
                <div class="portfolio-wrapper">
                    <a href="/rentatours/${tour.path}/" target="_blank"><div class="icon-bg"></div></a>
                    <img src="/pano/${tour.defaultPano}/thumb.jpg" alt=""/>
                    <span class="glyphicon glyphicon glyphicon-film icon-video" aria-hidden="true"></span>
                    <div class="label">
                        <div class="label-text">
                                ${tour.name}
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