function add_to_facebook(name) {
    var pagelink =window.location.protocol+'//'+window.location.host+window.location.pathname;
    var linkto = 'http://www.facebook.com/sharer.php?u='+pagelink+'?startscene='+name;
    win = window.open(linkto,'','toolbar=0,status=0,width=626,height=436');
}
function add_to_twitter(name,title) {
    var pagelink =window.location.protocol+'//'+window.location.host+window.location.pathname;
    var linkto = 'https://twitter.com/share?text='+title+'&url='+pagelink+'?startscene='+name;
    win = window.open(linkto,'','toolbar=0,status=0,width=626,height=436');
}
function add_to_vkontakte(name) {
    var pagelink =window.location.protocol+'//'+window.location.host+window.location.pathname;
    var linkto = 'http://vkontakte.ru/share.php?url='+pagelink+'?startscene='+name;
    win = window.open(linkto,'','toolbar=0,status=0,width=626,height=436');
}
function add_to_ok(name) {
    var pagelink =window.location.protocol+'//'+window.location.host+window.location.pathname;
    var linkto = 'https://www.ok.ru/dk?st.cmd=addShare&st.s=1&st._surl='+pagelink+'?startscene='+name;
    win = window.open(linkto,'','toolbar=0,status=0,width=626,height=436');
}
function add_to_skype(name) {
    var pagelink =window.location.protocol+'//'+window.location.host+window.location.pathname;
    var linkto = 'http://web.skype.com/share?url='+encodeURI(pagelink+'?startscene='+name);
    win = window.open(linkto,'','toolbar=0,status=0,width=626,height=436');
}
function add_to_moimir(name) {
    var pagelink =window.location.protocol+'//'+window.location.host+window.location.pathname;
    var linkto = 'https://connect.mail.ru/share?url='+pagelink+'?startscene='+name;
    win = window.open(linkto,'','toolbar=0,status=0,width=626,height=436');
}
function add_to_google(name) {
    var pagelink =window.location.protocol+'//'+window.location.host+window.location.pathname;
    var linkto = 'https://plus.google.com/share?url='+pagelink+'?startscene='+name;
    win = window.open(linkto,'','toolbar=0,status=0,width=626,height=436');
}
