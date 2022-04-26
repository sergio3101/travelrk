<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><krpano version="1.19" title="${fn:escapeXml(pano.title)}">
    <!-- the skin -->
    <include url="/krengine/header.xml" />

    ${pano.sceneXml}
    <action name="startup" autorun="onstart">
        loadscene("scene_${pano.panoPath}");
    </action>

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
    <social_but name="panoshot_email" show="true"/>
    <social_but name="panoshot_download" show="true"/>

    <include url="/krengine/footer.xml" />
</krpano>