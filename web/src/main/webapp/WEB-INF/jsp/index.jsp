<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
<%@ include file="includes/headMeta.jsp" %>
    <title>Главная</title>
<%@ include file="includes/headStyle.jsp" %>
</head>
<body>
<%@ include file="includes/topMenu.jsp" %>
<section>
    <div class="mozaik-map">
        <div class="mozaik-content">
            <img class="img-responsive btn-block" src="img/mozaik1.png" usemap="#Map">
            <map name="Map" id="Map">
                <area alt="" title="" href="#" shape="poly"
                      coords="33,350,43,319,160,238,302,169,352,156,376,135,373,109,348,64,351,44,365,33,385,40,413,66,427,77,470,89,495,112,530,107,553,116,590,164,616,179,654,181,697,215,751,250,765,288,761,322,774,356,833,373,885,391,949,364,1003,334,1041,342,1105,302,1195,312,1200,332,1174,377,1171,402,1174,424,1154,456,1121,469,1092,466,1034,478,996,461,937,430,892,451,838,513,817,528,785,562,751,564,650,593,602,621,575,669,480,754,454,765,393,765,355,731,326,717,316,685,329,676,338,646,336,599,357,549,345,483,324,448,294,423,256,425,232,427,185,391,135,357,106,348,60,367,44,367"/>
            </map>
        </div>
    </div>
</section>
<%@ include file="includes/footerScripts.jsp" %>
</body>
</html>