<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <%@ include file="../includes/headMeta.jsp" %>
    <title>3D тур</title>
    <%@ include file="../includes/headStyle.jsp" %>
    <style>
        @-ms-viewport {
            width: device-width;
        }

        @media only screen and (min-device-width: 800px) {
            html {
                overflow: hidden;
            }
        }

        html {
            height: 100%;
        }

        body {
            height: 100%;
            overflow: hidden;
            margin: 0;
            padding: 0;
            font-family: Arial, Helvetica, sans-serif;
            font-size: 16px;
            color: #FFFFFF;
            background-color: #000000;
        }
        .pano-container {
            height:700px;
        }
    </style>
</head>
<body>
<script src="/krengine/pano.js"></script>
<%@ include file="../includes/topMenu.jsp" %>
    <div class="pano-container">
        <div id="pano" style="width:100%;height:100%;">
            <noscript>
                <table style="width:100%;height:100%;">
                    <tr style="vertical-align:middle;">
                        <td>
                            <div style="text-align:center;">ERROR:<br/><br/>Javascript not activated<br/><br/></div>
                        </td>
                    </tr>
                </table>
            </noscript>
            <script>
                embedpano({
                    swf: "/krengine/pano.swf",
                    xml: "xml/pano.xml",
                    target: "pano",
                    html5: "auto",
                    mobilescale: 1.0,
                    passQueryParameters: true
                });
            </script>
        </div>
    </div>
<%@ include file="../includes/footerScripts.jsp" %>
</body>
</html>