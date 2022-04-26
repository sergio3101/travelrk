<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="includes/headMeta.jsp" %>
    <title>Login </title>
    <%@ include file="includes/cssBlock.jsp" %>

    <!-- Animate.css -->
    <link href="../vendors/animate.css/animate.min.css" rel="stylesheet">
</head>

<body class="login">
<div>
    <a class="hiddenanchor" id="signup"></a>
    <a class="hiddenanchor" id="signin"></a>

    <div class="login_wrapper">
        <div class="animate form login_form">
            <section class="login_content">
                <form role="form" action="/admin/auth" method="POST">
                    <h1>Admin Panel</h1>
                    <c:if test="${info!=null}">
                        <jsp:include page="messages/logininfo.jsp">
                            <jsp:param name="alert" value="alert-info"/>
                            <jsp:param name="message" value="${info}"/>
                        </jsp:include>
                    </c:if>
                    <div>
                        <input type="text" class="form-control" name="username" placeholder="Имя" required="" />
                    </div>
                    <div>
                        <input type="password" class="form-control" name="password" placeholder="Пароль" required="" />
                    </div>
                    <div>
                        <button type="submit" class="btn btn-success">Вход</button>
                    </div>

                    <div class="clearfix"></div>

                    <div class="separator">
                        <div>
                            <h1><i class="fa fa-paw"></i> TravelRK</h1>
                        </div>
                    </div>
                </form>
            </section>
        </div>
    </div>
</div>
<%@ include file="includes/footerScripts.jsp" %>
<script>
    $(document).ready(function(){
        $('input[name=username]').focus();
    });
</script>
</body>
</html>