<%@ page contentType="text/xml;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><krpano>
    <!-- the skin -->
    <include url="/krengine/skin/tour.xml" />

    <skin_settings layout_width="100%"
                   layout_maxwidth=""
                   controlbar_width="100%"
                   controlbar_height.normal="40"
                   controlbar_height.mobile="38"
                   controlbar_offset.normal="20"
                   controlbar_offset.mobile="0"
                   controlbar_offset_closed="-2"
                   controlbar_overlap="0"
                   design_skin_images="vtourskin.png"
                   design_bgcolor="0x000000"
                   design_bgalpha="0.5"
                   design_bgborder="1,0 0xFFFFFF 1"
                   design_bgroundedge.no-ios="0"
                   design_bgroundedge.ios="1"
                   design_bgshadow="0 0 20 0x000000 1.0"
                   design_thumbborder_bgborder="4 0xFFFFFF 1.0"
                   design_thumbborder_padding="2"
                   design_thumbborder_bgroundedge="3"
                   design_text_css="color:#FFFFFF; font-family:Arial; font-weight:bold;"
                   design_text_shadow="1"
    />

    <!-- view settings -->
    <view
            hlookat="${pano.panoScan.hLookAt}"
            vlookat="${pano.panoScan.vLookAt}"
            fov="30"
            fovmin="0"
            fovmax="60"
            limitview="auto"
            maxpixelzoom="2"
    />

    <!-- vignetting image -->
    <plugin name="mask" url="/krengine/img/vignetting.png" width="100%" height="100%" enabled="false" zorder="0" alpha="0" onloaded="tween(alpha,1);" />

    <c:if test="${pano.compassOn}">
    <!-- enable compass -->
    <include url="/krengine/plugins/compass.xml" />
    </c:if>

    <!-- enable autorotation -->
    <autorotate enabled="true" waittime="3.0" accel="0.5" speed="3" horizon="0" tofov="100" />

    <style name="hotspot_sm" url="/krengine/skin/hotspot_sm.png"/>

    <preview url="/tiles/${pano.panoPath}/preview.jpg" />
    <plugin name="gyro" url="/krengine/plugins/gyro2.js" enabled="true" devices="html5" camroll="true" friction="0.5" velastic="0" keep="true"/>
<%--    <image type="CUBE" prealign="0|0.0|0" multires="true" tilesize="1024">
        <level tiledimagewidth="4608" tiledimageheight="4608">
            <cube url="/tiles/dgangul/%s/l5/%v/l5_%s_%v_%h.jpg" />
        </level>
        <level tiledimagewidth="2304" tiledimageheight="2304">
            <cube url="/tiles/dgangul/%s/l4/%v/l4_%s_%v_%h.jpg" />
        </level>
        <level tiledimagewidth="1152" tiledimageheight="1152">
            <cube url="/tiles/dgangul/%s/l3/%v/l3_%s_%v_%h.jpg" />
        </level>
        <level tiledimagewidth="512" tiledimageheight="512">
            <cube url="/tiles/dgangul/%s/l2/%v/l2_%s_%v_%h.jpg" />
        </level>
        <level tiledimagewidth="256" tiledimageheight="256">
            <cube url="/tiles/dgangul/%s/l1/%v/l1_%s_%v_%h.jpg" />
        </level>

        <mobile>
            <cube url="/tiles/dgangul/mobile/mobile_%s.jpg" />
        </mobile>

        <mobile devices="iPad+Retina">
            <cube url="/tiles/dgangul/ipad3/ipad3_%s.jpg" />
        </mobile>
    </image>--%>
    <%--<image type="CUBE" multires="true" prealign="0|${pano.panoScan.north}|0" tilesize="512">
        <level tiledimagewidth="4736" tiledimageheight="4736">
            <cube url="/tiles/${pano.panoPath}/%s/l5/%0v/l5_%s_%0v_%0h.jpg" />
        </level>
        <level tiledimagewidth="1792" tiledimageheight="1792">
            <cube url="/tiles/${pano.panoPath}/%s/l4/%0v/l4_%s_%0v_%0h.jpg" />
        </level>
        <level tiledimagewidth="1024" tiledimageheight="1024">
            <cube url="/tiles/${pano.panoPath}/%s/l3/%0v/l3_%s_%0v_%0h.jpg" />
        </level>
        <level tiledimagewidth="512" tiledimageheight="512">
            <cube url="/tiles/${pano.panoPath}/%s/l2/%0v/l2_%s_%0v_%0h.jpg" />
        </level>
        <level tiledimagewidth="256" tiledimageheight="256">
            <cube url="/tiles/${pano.panoPath}/%s/l1/%0v/l1_%s_%0v_%0h.jpg" />
        </level>

        <mobile>
            <cube url="/tiles/${pano.panoPath}/mobile/mobile_%s.jpg" />
        </mobile>

        <mobile devices="iPad+Retina">
            <cube url="/tiles/${pano.panoPath}/ipad3/ipad3_%s.jpg" />
        </mobile>
    </image>--%>
    <image type="CUBE" multires="true" prealign="0|${pano.panoScan.north}|0" tilesize="1024">
        <level tiledimagewidth="3584" tiledimageheight="3584">
            <cube url="/tiles/${pano.panoPath}/%s/l5/%v/l5_%s_%v_%h.jpg" />
        </level>
        <level tiledimagewidth="2048" tiledimageheight="2048">
            <cube url="/tiles/${pano.panoPath}/%s/l4/%v/l4_%s_%v_%h.jpg" />
        </level>
        <level tiledimagewidth="1024" tiledimageheight="1024">
            <cube url="/tiles/${pano.panoPath}/%s/l3/%v/l3_%s_%v_%h.jpg" />
        </level>
        <level tiledimagewidth="512" tiledimageheight="512">
            <cube url="/tiles/${pano.panoPath}/%s/l2/%v/l2_%s_%v_%h.jpg" />
        </level>
        <level tiledimagewidth="256" tiledimageheight="256">
            <cube url="/tiles/${pano.panoPath}/%s/l1/%v/l1_%s_%v_%h.jpg" />
        </level>

        <mobile>
            <cube url="/tiles/${pano.panoPath}/mobile/mobile_%s.jpg" />
        </mobile>

        <mobile devices="iPad+Retina">
            <cube url="/tiles/${pano.panoPath}/ipad3/ipad3_%s.jpg" />
        </mobile>
    </image>


    <c:forEach items="${hotspots}" var="hotspot">
    <hotspot name="${hotspot.getPanorama().getPanoPath()}_hotspot" ath="${hotspot.getAhv()}" atv="15" style="hotspot_sm" onclick="js(click_hotspot('${hotspot.getPanorama().getPanoPath()}'));"/>
    </c:forEach>
</krpano>