<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>3D тур - ${tour.name}</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta http-equiv="x-ua-compatible" content="IE=edge" />
    <link rel="image_src" href="${snapshoturl}">
    <meta name="title" content="${tour.name}" />
    <meta property="og:title" content="${tour.name}" />
    <meta property="og:type" content="article"/>
    <%--<meta property="og:url" content="https://travelrk.ru/rentatours/${tour.path}/" />--%>
    <meta property="og:image" content="${snapshoturl}" />
    <meta property="og:image:url" content="${snapshoturl}" />
    <meta property="og:image:type" content="image/jpeg" />
    <meta property="og:description" content="${tour.description}" />
    <meta property="og:site_name" content="TRAVELRK.RU" />
    <meta name="twitter:card" content="summary" />
    <meta name="twitter:title" content="${tour.name}" />
    <meta name="twitter:description" content="${tour.description}" />
    <meta name="twitter:url" content="https://travelrk.ru/rentatours/${tour.path}/" />
    <meta name="twitter:image" content="${snapshoturl}" />
    <%@ include file="includes/headStyle.jsp" %>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
    <style type="text/css">
        @-ms-viewport {
            width: device-width;
        }

        @media only screen and (min-device-width: 800px) {
            html {
                overflow: hidden;
            }
        }

        * {
            padding: 0;
            margin: 0;
        }

        html {
            height: 100%;
        }

        body {
            height: 100%;
            overflow: hidden;
        }

        div#container {
            height: 100%;
            min-height: 100%;
            width: 100%;
            margin: 0 auto;
        }

        div#tourDIV {
            height: 100%;
            position: relative;
            overflow: hidden;
        }

        div#panoDIV {
            height: 100%;
            position: relative;
            overflow: hidden;
            -webkit-user-select: none;
            -khtml-user-select: none;
            -moz-user-select: none;
            -o-user-select: none;
            user-select: none;
        }
    </style>
    <style>
        @import "/krengine/plugins/modal_win/js/iframe.css";
    </style>
    <link rel="stylesheet" href="/krengine/plugins/modal_win/js/iframe.css" />
    <script src="/krengine/plugins/modal_win/js/mootools.js"></script>
    <script src="/krengine/plugins/modal_win/js/ClassicFrame.js"></script>
    <script src="/krengine/plugins/modal_win/js/ClassicBarry.js"></script>
    <script src="/krengine/plugins/panoshot/jquery.min.js"></script>
</head>
<body>
<script src="/krengine/player.js"></script>
<div id="container">
    <div id="tourDIV">
        <div id="panoDIV">
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
                    swf: "/krengine/player.swf",
                    xml: "index.xml",
                    target: "panoDIV",
                    html5:"only+webgl",
                    webglsettings:{preserveDrawingBuffer:true},
                    mobilescale: 1.0,
                    passQueryParameters: true
                });
            </script>
        </div>
    </div>
</div>
<!-- Yandex.Metrika counter -->
<script type="text/javascript" >
    (function (d, w, c) {
        (w[c] = w[c] || []).push(function() {
            try {
                w.yaCounter46547013 = new Ya.Metrika({
                    id:46547013,
                    clickmap:true,
                    trackLinks:true,
                    accurateTrackBounce:true,
                    webvisor:true
                });
            } catch(e) { }
        });
        var n = d.getElementsByTagName("script")[0],
            s = d.createElement("script"),
            f = function () { n.parentNode.insertBefore(s, n); };
        s.type = "text/javascript";
        s.async = true;
        s.src = "https://mc.yandex.ru/metrika/watch.js";

        if (w.opera == "[object Opera]") {
            d.addEventListener("DOMContentLoaded", f, false);
        } else { f(); }
    })(document, window, "yandex_metrika_callbacks");
</script>
<noscript><div><img src="https://mc.yandex.ru/watch/46547013" style="position:absolute; left:-9999px;" alt="" /></div></noscript>
<!-- /Yandex.Metrika counter -->
</body>
</html>