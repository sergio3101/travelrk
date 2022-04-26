$(document).ready(function () {
    var srcgResult = $("#tabulator-table");
    srcgResult.tabulator({
        placeholder: "Нет данных",
        layout: "fitColumns",
        selectable:true,
        columns: [
            {title: "Компания", field: "companyName", sorter: "string"},
            {title: "Адрес", field: "address", sorter: "string"},
            {title: "Телефон", field: "phone"},
            {title: "Сайт", field: "site", cellClick:function(e, cell){
                    window.open(cell.getRow().getData().site);
                }}
        ],
        initialSort:[
            {column: "companyName", dir:"asc"}
        ],
        // pagination:"local",
        // paginationSize:10,
        // ajaxURL: "getCustomersJson",
        locale:true,
        ajaxContentType:"json",
        ajaxConfig:"POST",
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
    document.getElementById("srch-btn").addEventListener("click", function(){
        $("#tabulator-table").tabulator("setData", "getSearchCustomersJson",
            {query:$("#qeury-text").val(), isSiteExist:$("#isSiteExist").is(":checked")});
    });
    document.getElementById("save-custommers-btn").addEventListener("click", function(){
        var selected = $("#tabulator-table").tabulator("getSelectedData");
        var region = $("#region").val();
        var rentaTourId = $("#rentatour").val();
        var selectModel = {region: region, rentaTourId: rentaTourId, selected: selected};
        if (selected.length !== 0) {
            $.ajax({
                type: 'POST',
                data: JSON.stringify(selectModel),
                dataType: 'json',
                contentType: 'application/json',
                // url: 'setSearchCustomersJson', // создаем арендный тур с заказчиком
                url: 'setPanoTourCustomerJson', // создаем арендный Пано-тур с заказчиком
                success: function (data) {
                    if (data.status == 'SUCCESS') {
                        new PNotify({
                            title: 'Сохранение',
                            text: 'Карточки заказчиков успешно сохранились',
                            type: 'success'
                        });
                    }
                    if (data.status == 'ERROR') {
                        new PNotify({
                            title: 'Сохранение',
                            text: 'Произошла ошибка при сохранении заказчиков',
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
                        title: 'Ошибка сохранения',
                        text: msg,
                        type: 'error'
                    });
                }
            });
        }
    });
});