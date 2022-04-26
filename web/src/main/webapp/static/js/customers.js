$(document).ready(function () {
    var scntbl = $("#tabulator-table");
    Tabulator.extendExtension("format", "formatters", {
        actionCell: function (cell, formatterParams) {
            var id = cell.getRow().getData().id;
            var yaid = cell.getRow().getData().yaid;
            var yaImg = "";
            if (yaid !== null) {
                yaImg="<img src='/images/ya-icon.png' style='height: 14px; width: 14px;'/>";
            }
            var delCustomerBtn = $("<i class='fa fa-trash' style='cursor: pointer' title='Удалить'></i>");
            delCustomerBtn.on("click", function () {
                var notic = new PNotify({
                    title: 'Подтвердите удаление',
                    text: 'Восстановить закачзика будет невозможно',
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
                        url: 'ajaxRemoveCustomerInfo',
                        success: function (data) {
                            if (data == 'SUCCESS') {
                                new PNotify({
                                    title: 'Удаление',
                                    text: 'Заказчик успешно удален',
                                    type: 'success'
                                });
                                cell.getRow().delete();
                                cell.getRow().delete();
                            }
                            if (data == 'ERROR') {
                                new PNotify({
                                    title: 'Удаление',
                                    text: 'При удалении заказчика произошла ошибка',
                                    type: 'error'
                                });
                            }
                            if (data == 'CONSTRAIGHTS') {
                                new PNotify({
                                    title: 'Удаление',
                                    text: 'Нельзя удалить заказчика с турами',
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
            return delCustomerBtn.append("&nbsp;").add(yaImg);
        }
    });
    scntbl.tabulator({
        placeholder: "Нет данных",
        layout: "fitColumns",
        groupBy:"region.viewName",
        groupStartOpen: false,
        groupToggleElement:"header",
        columns: [
            {title: "", field: "action", align: "left", formatter: "actionCell", width: "40"},
            {title: "Компания", field: "companyName", sorter: "string", cellClick:function(e, cell){
                    window.location = 'customerEdit-'+cell.getRow().getData().id;
                }},
            {title: "Телефон", field: "phone", cellClick:function(e, cell){
                    window.location = 'customerEdit-'+cell.getRow().getData().id;
                }},
            {title: "E-mail", field: "email", cellClick:function(e, cell){
                    window.location = 'customerEdit-'+cell.getRow().getData().id;
                }},
            {title: "Сайт", field: "site", cellClick:function(e, cell){
                window.open(cell.getRow().getData().site);
                }},
            {title: "Регион", field: "region.viewName", width: "100%", visible:false, sorter:"string"},
            {field: "yaid", width: "100%", visible:false, sorter:"string"}
        ],
        initialSort:[
            {column: "companyName", dir:"desc"},
            {column: "region.viewName", dir:"asc"}

        ],
        // pagination:"local",
        // paginationSize:10,
        ajaxURL: "getCustomersJson",
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
                    "item":"заказчик", //the singluar for item
                    "items":"заказчиков", //the plural for items
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