<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <%@ include file="../includes/headMeta.jsp" %>
    <title>Ошибка!</title>
    <%@ include file="../includes/headStyle.jsp" %>
</head>
<body>
<%@ include file="../includes/topMenu.jsp" %>
<div class="error-header"><h1>${errorMsg}</h1></div>
<%@ include file="../includes/footerScripts.jsp" %>
</body>
</html>