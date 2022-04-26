$(document).ready(function () {
    var delAction = function(e, cell) {
        var id = cell.getRow().getData().id;
        var notic = new PNotify({
            title: 'Подтвердите удаление',
            text: 'Восстановить тур будет невозможно',
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
                url: 'ajaxRemovePanoTourRenta',
                success: function (data) {
                    if (data == 'SUCCESS') {
                        new PNotify({
                            title: 'Удаление',
                            text: 'Тур успешно удален',
                            type: 'success'
                        });
                        cell.getRow().delete();
                        cell.getRow().delete();
                    }
                    if (data == 'ERROR') {
                        new PNotify({
                            title: 'Удаление',
                            text: 'При удалении тура произошла ошибка',
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
    };
    var viewAction = function(e, cell) {
        var path = cell.getRow().getData().path;
        window.open('/panotour/'+path+'/');
    };
    var delFormatter = function(cell, formatterParams, onRendered){ //plain text value
        return "<i class='fa fa-trash' style='cursor: pointer' title='Удалить'></i>";
    };
    var viewFormatter = function(cell, formatterParams, onRendered){ //plain text value
        return "<i class='fa fa-play-circle-o' style='cursor: pointer' title='Просмотр'></i>";
    };
    var linkEdit = function(e, cell){
        window.location = 'panoTourRentaEdit-'+cell.getRow().getData().id;
    };
    var colorProgress = function (value) {
        var color;
        if (value > 0 && value <= 10) color="#33FF00";
        if (value > 10 && value <= 20) color="#66FF00";
        if (value > 20 && value <= 30) color="#99FF00";
        if (value > 30 && value <= 40) color="#CCFF00";
        if (value > 40 && value <= 50) color="#FFFF00";
        if (value > 50 && value <= 60) color="#FFCC00";
        if (value > 60 && value <= 70) color="#FF9900";
        if (value > 70 && value <= 80) color="#FF6600";
        if (value > 80 && value <= 90) color="#FF3300";
        if (value > 90 && value < 100) color="#FF0000";
        if (value == 100) color="#6FA6E3";
        return color;
    };
    var scntbl = new Tabulator("#panotourrenta-table", {
        placeholder: "Нет данных",
        layout: "fitColumns",
        groupBy:"customerInfo.region.viewName",
        groupStartOpen: false,
        groupToggleElement:"header",
        columns: [
            {title: "", field: "actionDel", align: "left", formatter: delFormatter, cellClick:delAction, width: "20"},
            {title: "", field: "actionView", align: "left", formatter: viewFormatter, cellClick:viewAction, width: "20"},
            {title: "Название", field: "name", sorter: "string", cellClick:linkEdit},
            {title:"Прогресс", field:"dayProgress", formatter:"progress", cellClick:linkEdit, formatterParams:{
                    min:0,
                    max:100,
                    color:colorProgress,
                    legendColor:"#000000",
                    legendAlign:"center"}, width: "100"},
            {title: "Оплачено", field: "sum", cellClick:linkEdit, formatter:"money", formatterParams:{
                    decimal:",",
                    thousand:".",
                    symbol:"£",
                    symbolAfter:"p",
                    precision:true,
                }, width: "100"},
            {title: "Месяцев", field: "monthCount", cellClick:linkEdit, width: "100"},
            {title: "Просмотров", field: "counter", cellClick:linkEdit, width: "100"},
            {title:"Платеж в будущем", field:"isFuturePayment", formatter:"tickCross", formatterParams:{
                    allowEmpty:true,
                    allowTruthy:true,
                    tickElement:"<i class='fa fa-check'></i>",
                    crossElement:"<i class='fa fa-times'></i>",
                }, cellClick:linkEdit},
            {title: "Создан", field: "dateOfCreate", align: "center", sorter: "date",
                sorterParams:{format:"DD/MM/YYYY"}, width: "100",
                cellClick:linkEdit},
            {title: "Срок аренды", field: "rentaExpired", align: "center", sorter: "date",
                sorterParams:{format:"DD/MM/YYYY"}, width: "100",
                cellClick:linkEdit},
            {title: "Создал", field: "user.fio", cellClick:linkEdit},
            {title: "Домен", field: "domain", cellClick:linkEdit},
            {title: "Регион", field: "customerInfo.region.viewName", width: "100%", visible:false, sorter:"string"}
        ],
        initialSort:[
            {column: "companyName", dir:"desc"},
            {column: "customerInfo.region.viewName", dir:"asc"}

        ],
        // pagination:"local",
        // paginationSize:10,
        ajaxURL: "getPanoTourRentaJson",
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
                "groups":{
                    "item":"тур",
                    "items":"туров",
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
