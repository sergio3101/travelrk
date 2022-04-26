$(document).ready(function () {
    var ddd = $("div");
    var isProtect = $('#protect');
    function checkI(ob) {
        var isDisable = !$(ob).is(':checked');
        if (isDisable) {
            $('.icheckbox_flat-green').addClass('disabled');
        } else {
            $('.icheckbox_flat-green').removeClass('disabled');
        }
        $('input.flat').prop("disabled", isDisable);
        $('#domain').prop("disabled", isDisable);
        $('#expire').prop("disabled", isDisable);
    }

    isProtect.on('click', function(){
        checkI(this);
    });
    isProtect.each(function(){
        checkI(this);
    });
    $("input:text[name]").each(function(){
        isDef(this.name, this.value);
    });
    $("input:checkbox[name]").each(function(){
        isDefCheck(this.name);
    });
    $("select[name]").each(function(){
        isDef(this.name, this.value);
    });
    ddd.on("change", "input:text", function () {
        isDef(this.id, this.value);
    });
    ddd.on("change", "input:checkbox", function () {
        isDefCheck(this.name);
    });
    ddd.on("change", "select", function () {
        isDef(this.id, this.value);
    });

    function isDef(id, val) {
        var label = $("label[for='" + id + "']");
        if (def[id]!=val)
            label.css('color', '#26b99a');
        else
            label.css('color', '');
    }
    function isDefCheck(id) {
        var label = $("label[for='" + id + "']");
        if (def[id] !== $('#'+id).is(':checked'))
            label.css('color', '#26b99a');
        else
            label.css('color', '');
    }
});