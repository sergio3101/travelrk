<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>${panorama.region.viewName} - ${panorama.title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta http-equiv="x-ua-compatible" content="IE=edge" />
    <link rel="image_src" href="${snapshoturl}">
    <meta name="title" content="${panorama.region.viewName} - ${panorama.title}" />
    <meta property="og:title" content="${panorama.region.viewName} - ${panorama.title}" />
    <meta property="og:type" content="article"/>
    <%--<meta property="og:url" content="https://travelrk.ru/pano/${panorama.panoPath}/" />--%>
    <meta property="og:image" content="${snapshoturl}" />
    <meta property="og:image:url" content="${snapshoturl}" />
    <meta property="og:image:type" content="image/jpeg" />
    <meta property="og:description" content="${panorama.description}" />
    <meta property="og:site_name" content="TRAVELRK.RU" />
    <meta name="twitter:card" content="summary" />
    <meta name="twitter:title" content="${panorama.region.viewName} - ${panorama.title}" />
    <meta name="twitter:description" content="${panorama.description}" />
    <meta name="twitter:url" content="https://travelrk.ru" />
    <meta name="twitter:image" content="${snapshoturl}" />
    <style>
        @-ms-viewport { width:device-width; }
        @media only screen and (min-device-width:800px) { html { overflow:hidden; } }
        html { height:100%; }
        body { height:100%; overflow:hidden; margin:0; padding:0; font-family:Arial, Helvetica, sans-serif; font-size:16px; color:#FFFFFF; background-color:#000000; }
    </style>
</head>
<body>

<script src="/krengine/player.js"></script>
<script src="/vendors/jquery/dist/jquery.min.js"></script>
<script src="/js/saveprealign.js"></script>
<div id="pano" style="width:100%;height:100%;">
    <noscript><table style="width:100%;height:100%;"><tr style="vertical-align:middle;"><td><div style="text-align:center;">ERROR:<br/><br/>Javascript not activated<br/><br/></div></td></tr></table></noscript>
    <script>
        embedpano({swf:"/krengine/player.swf", xml:"panorama.xml", target:"pano", html5:"auto", mobilescale:1.0, passQueryParameters:true});
    </script>
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