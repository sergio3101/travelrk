<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>3D тур - ${tour.name}</title>
    <meta name="description" content="${tour.description}" />
    <meta name="medium" content="mult" />
    <meta name="video_width" content="640"/>
    <meta name="video_height" content="480"/>
    <link rel="image_src" href="${tour.panoTourSrc.path}/thumbnail.jpg">
    <meta name="title" content="${tour.name}" />
    <meta property="og:title" content="${tour.name}" />
    <meta property="og:description" content="${tour.description}" />
    <meta property="og:image:type" content="image/jpeg" />
    <meta property="og:image" content="${tour.panoTourSrc.path}/thumbnail.jpg" />
    <meta name="twitter:card" content="summary" />
    <meta name="twitter:title" content="${tour.name}" />
    <meta name="twitter:description" content="${tour.description}" />
    <meta name="twitter:image" content="${tour.panoTourSrc.path}/thumbnail.jpg" />
    <meta name="viewport" content="target-densitydpi=device-dpi, width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui, viewport-fit=cover"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="default">

    <style type="text/css">
        @-ms-viewport { width: device-width; }
        @media only screen and (min-device-width: 800px) { html { overflow:hidden; } }
        * { padding: 0; margin: 0; }
        html { height: 100%; }
        body { height: 100%; overflow:hidden; }
        div#container { height: 100%; min-height: 100%; width: 100%; margin: 0 auto; }
        div#tourDIV {
            height:100%;
            position:relative;
            overflow:hidden;
        }
        div#panoDIV {
            height:100%;
            position:relative;
            overflow:hidden;
            -webkit-user-select: none;
            -khtml-user-select: none;
            -moz-user-select: none;
            -o-user-select: none;
            user-select: none;
        }


    </style>
    <!--[if !IE]><!-->
    <script type="text/javascript" src="${tour.panoTourSrc.path}/lib/jquery-2.1.1.min.js"></script>
    <!--<![endif]-->
    <!--[if lte IE 8]>
    <script type="text/javascript" src="${tour.panoTourSrc.path}/lib/jquery-1.11.1.min.js"></script>
    <![endif]-->
    <!--[if gt IE 8]>
    <script type="text/javascript" src="${tour.panoTourSrc.path}/lib/jquery-2.1.1.min.js"></script>
    <![endif]-->

<%--    <link type="text/css" href="${tour.panoTourSrc.path}/lib/jquery-ui-1.11.1/jquery-ui.min.css" rel="stylesheet" />--%>
<%--    <script type="text/javascript" src="${tour.panoTourSrc.path}/lib/jquery-ui-1.11.1/jquery-ui.min.js"></script>--%>
<%--    <script type="text/javascript" src="${tour.panoTourSrc.path}/lib/jquery.ui.touch-punch.min.js"></script>--%>
<%--    <script type="text/javascript" src="${tour.panoTourSrc.path}/lib/Kolor/KolorTools.min.js"></script>--%>
<%--    <script type="text/javascript" src="${tour.panoTourSrc.path}/graphics/KolorBootstrap.js"></script>--%>



    <style type="text/css">
        div#panoDIV.cursorMoveMode {
            cursor: move;
            cursor: url(${tour.panoTourSrc.path}/graphics/cursors_move_html5.cur), move;
        }
        div#panoDIV.cursorDragMode {
            cursor: grab;
            cursor: -moz-grab;
            cursor: -webkit-grab;
            cursor: url(${tour.panoTourSrc.path}/graphics/cursors_drag_html5.cur), default;
        }
    </style>

    <script type="text/javascript">

        function webglAvailable() {
            try {
                var canvas = document.createElement("canvas");
                return !!window.WebGLRenderingContext && (canvas.getContext("webgl") || canvas.getContext("experimental-webgl"));
            } catch(e) {
                return false;
            }
        }
        function getWmodeValue() {
            var webglTest = webglAvailable();
            if(webglTest){
                return 'direct';
            }
            return 'opaque';
        }

        function readDeviceOrientation() {
            // window.innerHeight is not supported by IE
            var winH = window.innerHeight ? window.innerHeight : jQuery(window).height();
            var winW = window.innerWidth ? window.innerWidth : jQuery(window).width();
            //force height for iframe usage
            if(!winH || winH == 0){
                winH = '100%';
            }
            // set the height of the document
            jQuery('html').css('height', winH);
            // scroll to top
            window.scrollTo(0,0);
        }
        jQuery( document ).ready(function() {
            if (/(iphone|ipod|ipad|android|iemobile|webos|fennec|blackberry|kindle|series60|playbook|opera\smini|opera\smobi|opera\stablet|symbianos|palmsource|palmos|blazer|windows\sce|windows\sphone|wp7|bolt|doris|dorothy|gobrowser|iris|maemo|minimo|netfront|semc-browser|skyfire|teashark|teleca|uzardweb|avantgo|docomo|kddi|ddipocket|polaris|eudoraweb|opwv|plink|plucker|pie|xiino|benq|playbook|bb|cricket|dell|bb10|nintendo|up.browser|playstation|tear|mib|obigo|midp|mobile|tablet)/.test(navigator.userAgent.toLowerCase())) {
                if(/iphone/.test(navigator.userAgent.toLowerCase()) && window.self === window.top){
                    jQuery('body').css('height', '100.18%');
                }
                // add event listener on resize event (for orientation change)
                if (window.addEventListener) {
                    window.addEventListener("load", readDeviceOrientation);
                    window.addEventListener("resize", readDeviceOrientation);
                    window.addEventListener("orientationchange", readDeviceOrientation);
                }
                //initial execution
                setTimeout(function(){readDeviceOrientation();},10);
            }
        });


        function accessWebVr(curScene, curTime){

            unloadPlayer();

            // eventUnloadPlugins();

            loadPlayer(true, curScene, curTime);
        }
        function accessStdVr(curScene, curTime){

            unloadPlayer();

            // resetValuesForPlugins();

            loadPlayer(false, curScene, curTime);
        }
        function loadPlayer(isWebVr, curScene, curTime) {
            if (isWebVr) {
                embedpano({
                    id:"krpanoSWFObject"
                    ,xml:"${tour.panoTourSrc.path}/${nameTour}_vr.xml"
                    ,target:"panoDIV"
                    ,passQueryParameters:true
                    ,bgcolor:"#000000"
                    ,html5:"only+webgl"
                    ,focus: false
                    ,vars:{skipintro:true,norotation:true,startscene:curScene,starttime:curTime }
                });
            } else {

                var isBot = /bot|googlebot|crawler|spider|robot|crawling/i.test(navigator.userAgent);
                embedpano({
                    id:"krpanoSWFObject"

                    ,swf:"${tour.panoTourSrc.path}/${nameTour}.swf"
                    ,xml:"${firstXmlPath}"
                    ,target:"panoDIV"
                    ,passQueryParameters:true
                    ,bgcolor:"#000000"
                    ,focus: false
                    ,html5:isBot ? "always" : "prefer"
                    ,initvars:{UNIQURL:"https://travelrk.ru/panotour/${tour.path}", COUNTER:"Просмотров: ${tour.counter}"}
                    ,vars:{startscene:curScene,starttime:curTime,UNIQURL:"https://travelrk.ru/panotour/${tour.path}"}

                    ,localfallback:"flash"

                    ,wmode: getWmodeValue()


                });
            }
            //apply focus on the visit if not embedded into an iframe
            if(top.location === self.location){
                kpanotour.Focus.applyFocus();
            }
        }
        function unloadPlayer(){
            if(jQuery('#krpanoSWFObject')){
                removepano('krpanoSWFObject');
            }

        }
        var currentPanotourPlayer = null;
        function getCurrentTourPlayer() {
            if (currentPanotourPlayer == null) {
                currentPanotourPlayer = document.getElementById('krpanoSWFObject');
            }
            return currentPanotourPlayer;
        }
        function isVRModeRequested() {
            var querystr = window.location.search.substring(1);
            var params = querystr.split('&');
            for (var i=0; i<params.length; i++){
                if (params[i].toLowerCase() == "vr"){
                    return true;
                }
            }
            return false;
        }
    </script>
    <c:if test="${showAdv}">
    <!-- Yandex.RTB -->
    <script>window.yaContextCb=window.yaContextCb||[]</script>
    <script src="https://yandex.ru/ads/system/context.js" async></script>
    </c:if>
</head>
<body>
<c:if test="${showAdv}">
    <!-- Yandex.RTB R-A-1260303-4 -->
    <div id="yandex_rtb_R-A-1260303-4"></div>
    <script>window.yaContextCb.push(()=>{
        Ya.Context.AdvManager.render({
            renderTo: 'yandex_rtb_R-A-1260303-4',
            blockId: 'R-A-1260303-4'
        })
    })</script>
</c:if>
<div id="container">

    <div id="tourDIV">
        <div id="panoDIV">
            <noscript>

                <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" width="100%" height="100%" id="${tour.path}/${nameTour}">
                    <param name="movie" value="${tour.panoTourSrc.path}/${nameTour}.swf"/>
                    <param name="allowFullScreen" value="true"/>
                    <!--[if !IE]>-->
                    <object type="application/x-shockwave-flash" data="${tour.path}/${nameTour}.swf" width="100%" height="100%">
                        <param name="movie" value="${tour.panoTourSrc.path}/${nameTour}.swf"/>
                        <param name="allowFullScreen" value="true"/>
                        <!--<![endif]-->
                        <a href="http://www.adobe.com/go/getflash">
                            <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player to visualize the Virtual Tour : Утёс (Virtual tour generated by Panotour)"/>
                        </a>
                        <!--[if !IE]>-->
                    </object>
                    <!--<![endif]-->
                </object>

            </noscript>
        </div>








        <!-- no search option activated -->




















        <script type="text/javascript" src="${tour.panoTourSrc.path}/${nameTour}.js"></script>
        <script type="text/javascript">
            if (isVRModeRequested()){
                accessWebVr();
            }else{
                accessStdVr();
            }
        </script>
        <c:if test="${showAdv}">
            <script src="https://unpkg.com/@vkontakte/vk-bridge/dist/browser.min.js"></script>

            <script>
                // Sends event to client
                vkBridge.send('VKWebAppInit');
            </script>
            <script>
                // Sends event to client
                vkBridge.send("VKWebAppShowNativeAds", {ad_format:"preloader"});
            </script>
            <script>
                // Sends event to client
                vkBridge.send("VKWebAppAddToFavorites");
            </script>

            <script type="text/javascript" src="//vk.com/js/api/xd_connection.js?2" charset="utf-8"></script>
            <script type="text/javascript" src="//ad.mail.ru/static/admanhtml/rbadman-html5.min.js" charset="utf-8"></script>
            <script type="text/javascript" src="//vk.com/js/api/adman_init.js" charset="utf-8"></script>
            <script>
                window.addEventListener('load', function() {

                    var user_id = null;   // user's id
                    var app_id = 7923602;  // your app's id

                    admanInit({
                        user_id: user_id,
                        app_id: 7923602,
                        type: 'preloader'         // 'preloader' or 'rewarded' (default - 'preloader')
                        // params: {preview: 1}   // to verify the correct operation of advertising
                    }, onAdsReady, onNoAds);

                    function onAdsReady(adman) {
                        adman.onStarted(function () {});
                        adman.onCompleted(function() {});
                        adman.onSkipped(function() {});
                        adman.onClicked(function() {});
                        adman.start('preroll');
                    };
                    function onNoAds() {};
                });
            </script>


            <script type="text/javascript" src="//vk.com/js/api/xd_connection.js?2" charset="utf-8"></script>
            <script type="text/javascript" src="//ad.mail.ru/static/admanhtml/rbadman-html5.min.js" charset="utf-8"></script>
            <script type="text/javascript" src="//vk.com/js/api/adman_init.js" charset="utf-8"></script>
            <script>
                window.addEventListener('load', function() {

                    var user_id = null;   // user's id
                    var app_id = 7923579;  // your app's id

                    admanInit({
                        user_id: user_id,
                        app_id: 7923579,
                        mobile: true,
                        type: 'rewarded' 			// 'preloader' or 'rewarded' (default - 'preloader')
                        // params: {preview: 1}   // to verify the correct operation of advertising
                    }, onAdsReady, onNoAds);

                    function onAdsReady(adman) {
                        adman.onStarted(function () {});
                        adman.onCompleted(function() {});
                        adman.onSkipped(function() {});
                        adman.onClicked(function() {});
                        adman.start('preroll');
                    };
                    function onNoAds() {};
                });
            </script>
            <div id="vk_ads_134084"></div>
            <script type="text/javascript">
                setTimeout(function() {
                    var adsParams = {"ad_unit_id":134084,"ad_unit_hash":"52339143592aca2eb4e9b3b4380d6967"};
                    function vkAdsInit() {
                        VK.Widgets.Ads('vk_ads_134084', {}, adsParams);
                    }
                    if (window.VK && VK.Widgets) {
                        vkAdsInit();
                    } else {
                        if (!window.vkAsyncInitCallbacks) window.vkAsyncInitCallbacks = [];
                        vkAsyncInitCallbacks.push(vkAdsInit);
                        var protocol = ((location.protocol === 'https:') ? 'https:' : 'http:');
                        var adsElem = document.getElementById('vk_ads_134084');
                        var scriptElem = document.createElement('script');
                        scriptElem.type = 'text/javascript';
                        scriptElem.async = true;
                        scriptElem.src = protocol + '//vk.com/js/api/openapi.js?169';
                        adsElem.parentNode.insertBefore(scriptElem, adsElem.nextSibling);
                    }
                }, 0);
            </script>
        </c:if>
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