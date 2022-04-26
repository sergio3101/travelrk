$(document).ready(function () {
    // PNotify.prototype.options.styling = "bootstrap3";

    $('.bs-remove-pano3d .modal-footer').on('click', '#btn-rmv-pn3d', function () {
        var id = $(this).attr('data-data');
        $.ajax({
            type: 'POST',
            data: 'id=' + id,
            url: 'ajaxRemovePano3D',
            success: function (data) {
                if (data == 'SUCCESS') {
                    $('#pano-' + id).remove();
                    $('#btn-rmv-pn3d').remove();
                    $('.bs-remove-pano3d').modal('hide');
                    new PNotify({
                        title: 'Удаление',
                        text: 'Панорама успешно удалена',
                        type: 'success'
                    });
                };
                if (data == 'ERROR') {
                    new PNotify({
                        title: 'Удаление',
                        text: 'При удалении панорамы произошла ошибка',
                        type: 'success'
                    });
                    $('#btn-rmv-pn3d').remove();
                }
                ;
            },
            error: function (jqXHR, exception) {
                var msg = '';
                if (jqXHR.status === 0) {
                    msg = 'Not connect.\n Verify Network.';
                } else if (jqXHR.status == 404) {
                    msg = 'Requested page not found. [404]';
                } else if (jqXHR.status == 500) {
                    msg = 'Internal Server Error [500].';
                } else if (exception === 'parsererror') {
                    msg = 'Requested JSON parse failed.';
                } else if (exception === 'timeout') {
                    msg = 'Time out error.';
                } else if (exception === 'abort') {
                    msg = 'Ajax request aborted.';
                } else {
                    msg = 'Uncaught Error.\n' + jqXHR.responseText;
                }
                new PNotify({
                    title: 'Удаление',
                    text: msg,
                    type: 'error'
                });
            }
        });
    });

    var scntbl = $("#tabulator-table");
    Tabulator.extendExtension("format", "formatters", {
        actionCell: function (cell, formatterParams) {
            var path = cell.getRow().getData().panoPath;
            var id = cell.getRow().getData().id;
            var delPanoBtn = $("<i class='fa fa-trash' style='cursor: pointer' title='Удалить'></i>");
            var airPano="";
            if (cell.getRow().getData().aair) {
                airPano+="<i class='fa fa-plane' style='cursor: pointer' title='Air-пано'></i>";
            }
            var aCompass="";
            if (cell.getRow().getData().acompassOn) {
                aCompass+="<i class='fa fa-compass' style='cursor: pointer' title='Компас включен'></i>";
            }
            var editPanoBtn = $("<a href='pano3Dedit-"+id+"'><i class='fa fa-edit' title='Редактировать'></i></a>");
            var viewPanoBtn = $("<a href='/pano/"+path+"' target='_blank'><i class='fa fa-eye' title='Просмотр'></i>");
            delPanoBtn.on("click", function () {
                var notic = new PNotify({
                    title: 'Подтвердите удаление',
                    text: 'Восстановить панораму будет невозможно',
                    icon: 'fa fa-question-circle',
                    hide: false,
                    type: 'success',
                    confirm: {
                        confirm: true
                    },
                    buttons: {
                        closer: true,
                        sticker: false
                    },
                    history: {
                        history: false
                    }
                });
                notic.get().on('pnotify.confirm', function() {
                    $.ajax({
                        type: 'POST',
                        data: 'id=' + id,
                        url: 'ajaxRemovePano3D',
                        success: function (data) {
                            if (data.length == 0) {
                                new PNotify({
                                    title: 'Удаление',
                                    text: 'Панорама успешно удалена',
                                    type: 'success'
                                });
                                var row = cell.getRow();
                                row.delete();
                                row.delete();
                            } else {
                                var msg = "Нельзя удалить панораму которая учавствует в туре:\n\n";
                                data.forEach(function(tour) {
                                    msg += "<a href='"+tour.url+"'>"+tour.name+"</a>\n";
                                });
                                new PNotify({
                                    title: 'Удаление',
                                    hide: false,
                                    buttons: {
                                        closer: true,
                                        sticker: false
                                    },
                                    text: msg,
                                    type: 'error'
                                });
                            }
                        },
                        error: function (jqXHR, exception) {
                            var msg = '';
                            if (jqXHR.status === 0) {
                                msg = 'Not connect.\n Verify Network.';
                            } else if (jqXHR.status == 404) {
                                msg = 'Requested page not found. [404]';
                            } else if (jqXHR.status == 500) {
                                msg = 'Internal Server Error [500].';
                            } else if (exception === 'parsererror') {
                                msg = 'Requested JSON parse failed.';
                            } else if (exception === 'timeout') {
                                msg = 'Time out error.';
                            } else if (exception === 'abort') {
                                msg = 'Ajax request aborted.';
                            } else {
                                msg = 'Uncaught Error.\n' + jqXHR.responseText;
                            }
                            new PNotify({
                                title: 'Удаление',
                                text: msg,
                                type: 'error'
                            });
                        }
                    });
                });
            });
            return delPanoBtn.append("&nbsp;").add(editPanoBtn).append("&nbsp;").add(viewPanoBtn).append("&nbsp;").add(airPano).append("&nbsp;").add(aCompass);
        },
    });
    scntbl.tabulator({
        placeholder: "Загрузка данных",
        layout: "fitColumns",
        groupBy:"region.viewName",
        groupStartOpen: false,
        groupToggleElement:"header",
        columns: [
            {title: "", field: "action", align: "left", formatter: "actionCell", width: "120"},
            {title: "Создана", field: "dateOfCreate", align: "center", sorter: "date", sorterParams:{format:"DD/MM/YYYY"}, width: "100"},
            {title: "Регион", field: "region.viewName", visible:false},
            {field: "panoPath", visible:false},
            {title: "Название", field: "title", sorter: "string"}
        ],
        // initialSort:[
        //     {column: "title", dir:"asc"},
        //     {column:"dateOfCreate", dir:"desc"}
        // ],
        // pagination:"local",
        // paginationSize:10,
        ajaxURL: "getPanoramasJson",
        locale:true,
        langs:{
            "ru-ru":{
                "columns":{
                    "name":"Имя", //replace the title of column name with the value "Name"
                },
                "ajax":{
                    "loading":"Загрузка", //ajax loader text
                    "error":"Ошибка", //ajax error text
                },
                "groups":{ //copy for the auto generated item count in group header
                    "item":"панорама", //the singluar for item
                    "items":"панорам", //the plural for items
                },
                "pagination":{
                    "first":"Первая", //text for the first page button
                    "first_title":"Первая страница", //tooltip text for the first page button
                    "last":"Последняя",
                    "last_title":"Последняя страница",
                    "prev":"Предыдущая",
                    "prev_title":"Предыдущая страница",
                    "next":"Следующая",
                    "next_title":"Следующая страница",
                },
                "headerFilters":{
                    "default":"filter column...", //default header filter placeholder text
                    "columns":{
                        "name":"filter name...", //replace default header filter text for column name
                    }
                }
            }
        }
    });

    $(".collapse-table").on("click",function(){
        var groups = scntbl.tabulator("getGroups");
        $.each(groups,function(i, group){
            group.hide();
        });
    });
    $(".recollapse-table").on("click",function(){
        var groups = scntbl.tabulator("getGroups");
        $.each(groups,function(i, group){
            group.show();
        });
    });
});