<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Оишибка</title>
    <style>
        .center {
            color: #b72c2c;
            width: 340px;
            padding: 10px; /* Поля вокруг текста */
            height: 74px; /* высота лемента в пикселах */
            position:relative;
            margin-top:50%;
            margin-bottom: 50%;
            top:-37px; /* 74/2=37 */
            margin-left:auto;
            margin-right:auto;
            background: #fff; /* Цвет фона */
        }
    </style>
</head>
<body style="overflow: hidden;">
<div class="center">${message}</div>
</body>
</html>
