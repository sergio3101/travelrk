var krpanoplugin=function(){function x(a){return"boolean"==typeof a?a:0<="yesontrue1".indexOf(String(a).toLowerCase())}function M(a){return"rgb("+(a>>16&255)+","+(a>>8&255)+","+(a&255)+")"}function Y(){null!=y&&(window[y]=null,delete window[y],y=null);f&&google&&google.maps&&f.trace(0,"Google Maps API Version: "+google.maps.version);var a=window._krpano_gmap_loadedcallbacks_;if(a){window._krpano_gmap_loadedcallbacks_=null;delete window._krpano_gmap_loadedcallbacks_;var d=a.length,b;if(0<d&&99>d)for(b=
0;b<d;b++)setTimeout(a[b],10+5*b);a=null}f&&e&&(k=document.createElement("div"),k.style.position="absolute",k.style.width="100%",k.style.height="100%",p.android&&p.firefox&&(k.style.opacity=.99),p.ios&&(k.addEventListener(f.browser.events.gesturestart,function(a){a.preventDefault();a.stopPropagation()},!1),k.addEventListener(f.browser.events.touchstart,function(a){a.preventDefault();a.stopPropagation()},!1)),e.sprite.a₀