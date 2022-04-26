<%@ page contentType="text/xml;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><krpano version="1.19" title="${tour.name}">


    <!-- offer alternative android browsers -->
    <events name="androidalternatives" keep="true" devices="html5.and.androidstock.and.no-webgl"
            onxmlcomplete="openurl('/build/alternative.html', _self);"
    />

    <include url="/krengine/skin/vtourskin.xml?2" />

    <!-- customize skin settings: maps, gyro, thumbnails, tooltips, layout, design, ... -->
    <skin_settings maps="true"
                   maps_type="google"
                   maps_api_key="AIzaSyDzmyWi9I5b1vqJHD4F1IbK8qQZFqWDPGE"
                   maps_zoombuttons="false"
                   gyro="true"
                   webvr="true"
                   webvr_gyro_keeplookingdirection="false"
                   littleplanetintro="false"
                   title="true"
                   thumbs="true"
                   thumbs_width="120" thumbs_height="80" thumbs_padding="10" thumbs_crop=""
                   thumbs_opened="false"
                   thumbs_text="false"
                   thumbs_dragging="true"
                   thumbs_onhoverscrolling="false"
                   thumbs_scrollbuttons="false"
                   thumbs_scrollindicator="false"
                   thumbs_loop="false"
                   tooltips_buttons="true"
                   tooltips_thumbs="true"
                   tooltips_hotspots="true"
                   tooltips_mapspots="true"
                   deeplinking="false"
                   loadscene_flags="MERGE"
                   loadscene_blend="OPENBLEND(0.5, 0.0, 0.75, 0.05, linear)"
                   loadscene_blend_prev="SLIDEBLEND(0.5, 180, 0.75, linear)"
                   loadscene_blend_next="SLIDEBLEND(0.5,   0, 0.75, linear)"
                   loadingtext="Загрузка..."
                   layout_width="100%"
                   layout_maxwidth.normal="900"
                   layout_maxwidth.mobile=""
                   controlbar_width.normal="-44"
                   controlbar_width.mobile="100%"
                   controlbar_height.normal="38"
                   controlbar_height.mobile="34"
                   controlbar_offset.normal="22"
                   controlbar_offset.mobile="0"
                   controlbar_offset_closed="-40"
                   controlbar_overlap.normal="7"
                   controlbar_overlap.mobile="2"
                   design_skin_images="vtourskin.png"
                   design_bgcolor="0x000000"
                   design_bgalpha="0.5"
                   design_bgborder="0 0xFFFFFF 1.0"
                   design_bgroundedge.normal="9"
                   design_bgroundedge.mobile="1"
                   design_bgshadow="0 0 9 0xFFFFFF 0.5"
                   design_thumbborder_bgborder="4 0xFFFFFF 1.0"
                   design_thumbborder_padding="2"
                   design_thumbborder_bgroundedge="5"
                   design_text_css="color:#FFFFFF; font-family:Arial; font-weight:bold;"
                   design_text_shadow="1"
    />

    <!--
        For an alternative skin design either change the <skin_settings> values
        from above or optionally include one of the predefined designs from below.
        Either by removing the 'xml-if-check' from the particular <include> element
        or by adding e.g. initvar:{design:'flat_light'} to the embedpano() call in
        the html file:
    -->
    <include url="/krengine/skin/vtourskin_design_flat_light.xml"/>
    <%--<include url="/krengine/skin/vtourskin_design_flat.xml"        />--%>
    <%--<include url="/krengine/skin/vtourskin_design_glass.xml"       if="design === 'glass'"       />--%>
    <%--<include url="/krengine/skin/vtourskin_design_flat_light.xml"  if="design === 'flat_light'"  />--%>
    <%--<include url="/krengine/skin/vtourskin_design_ultra_light.xml" if="design === 'ultra_light'" />--%>
    <%--<include url="/krengine/skin/vtourskin_design_117.xml"         if="design === '117'"         />--%>
    <include url="/krengine/skin/travelrk_skin_renta.xml"/>
    <%--<include url="/krengine/plugins/garrows/garrows.xml" />--%>
    <!-- startup action - here the first pano/scene will be loaded -->
    <action name="startup" autorun="onstart">
        <%--if(startscene === null, copy(startscene,scene[0].name));--%>
        <%--copy(startscene,"scene_${tour.defaultPano}");--%>
            if(sceneview === null,
                set(startscene,scene_${tour.defaultPano});
            ,
                txtsplit(get(sceneview),',',startscene);
            );

            loadscene(get(startscene), null, MERGE);
    </action>

    <!-- add keyboard zooming -->
    <control keycodesin="16" keycodesout="17" zoomtocursor="true" zoomoutcursor="true"/>

    <view fovmax="90" maxpixelzoom="1.2" limitview="fullrange" />

    <%--<c:forEach items="${panoList}" varStatus="v">
        ${panoList[v.index].sceneXml}
    </c:forEach>--%>
    ${rentaTourXml}
    <action name="mainloadscene">
        looktohotspot(%4,smooth(60,40,20));
        loadscene(%1,null,MERGE,OPENBLEND(1.0, -0.5, 0.3, 0.8, linear));
        lookat(%2,%3);
    </action>
    <%--<plugin name="editor" url="/krengine/plugins/editor.swf" keep="true" />--%>
    <include url="/krengine/plugins/fps.xml"/>
    <include url="/krengine/plugins/modal_win/modal_window.xml" keep="true"/>
    <!--SNAPSHOT PLUGIN-->
    <include url="/krengine/plugins/panoshot/panoshoot.xml"/>

    <panoshot
            show_snapshot_onstart="false"
            maxsize="1200"
            jpeg_quality="0.7"
            watermark="true"
            snapshot_name="360_Snapshot"
            show_ratio="true"
            ratio_icon_position_align="right"
            viewfinder_ratio_width="16"
            viewfinder_ratio_height="9"
            viewfinder_padding="150"
            viewfinder_glass="true"
            viewfinder_bg_color="0x000000"
            viewfinder_bg_alpha="0.7"
            viewfinder_border="3 0xffffff 1"
            snapshot_icon_y="100"
            email_subject="Check this out !"
            email_line1="I took this picture !"
            email_line2="Take yours >"
            show_msg="true"
            tutorial_msg="Переместитесь в панораме, чтобы [br]сделать снимок и поделиться им."
            share_msg="[b]Замечательно ![/b][br]Теперь вы можете поделиться им в социальных сетях![br]&#8744;"
            share_txt="Поделиться"
            mobile_button_align="bottom"
            mobile_button_edge=""
            mobile_button_x=""
            mobile_button_y="50"
            plugin_path="/krengine/plugins/panoshot/"
            sound_path="/krengine/plugins/panoshot/"
            external_php_server="false"
            external_php_server_path=""
    />

    <social_but name="panoshot_facebook" show="true"/>
    <social_but name="panoshot_twitter" show="false"/>
    <social_but name="panoshot_googleplus" show="false"/>
    <social_but name="panoshot_linkedin" show="false"/>
    <social_but name="panoshot_vk" show="true"/>
    <social_but name="panoshot_pininterest" show="false"/>
    <social_but name="panoshot_tumblr" show="false"/>
    <social_but name="panoshot_weibo" show="false"/>
    <social_but name="panoshot_email" show="false"/>
    <social_but name="panoshot_download" show="true"/>
</krpano>
