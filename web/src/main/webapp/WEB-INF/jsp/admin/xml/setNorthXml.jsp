<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<krpano  bgcolor="#ffffff">
    <action name="draghotspot">
        spheretoscreen(ath, atv, hotspotcenterx, hotspotcentery, calc(mouse.stagex LT stagewidth/2 ? 'l' : 'r'));
        sub(drag_adjustx, mouse.stagex, hotspotcenterx);
        sub(drag_adjusty, mouse.stagey, hotspotcentery);
        asyncloop(pressed,
        sub(dx, mouse.stagex, drag_adjustx);
        sub(dy, mouse.stagey, drag_adjusty);
        set(attv,0);
        screentosphere(dx, dy, ath, attv);
        js(setNorth(get(ath)));
        print_hotspot_pos();
        );
    </action>

    <action name="print_hotspot_pos"><![CDATA[
        copy(print_ath, ath);
        copy(print_atv, atv);
        roundval(print_ath, 3);
        roundval(print_atv, 3);
        calc(plugin[hotspot_pos_info].html, '&lt;hotspot name="' + name + '"[br]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...[br]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ath="' + print_ath + '"[br]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;atv="' + print_atv + '"[br]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...[br]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/&gt;');
        ]]></action>

${scenaXml}

    <action name="startup" autorun="onstart">
        loadscene("${scena.name}");
    </action>
</krpano>