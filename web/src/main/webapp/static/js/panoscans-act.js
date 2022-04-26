$(document).ready(function () {
    // PNotify.prototype.options.styling = "bootstrap3";
    $("[data-toggle='tooltip']").tooltip();
    var bsView = $('.bs-view-panoscan');
    var regionVal="";
    bsView.on('show.bs.modal', function (e) {
        var $invoker = $(e.relatedTarget);
        $('.bs-view-panoscan .modal-body').html('<img width="100%" height="488" src="/images/panoscans/' + $($invoker).attr('data-data') + '">');
    });
    bsView.on('hidden.bs.modal', function () {
        $('.bs-view-panoscan .modal-body img').remove();
    });

    var scntbl = $("#tabulator-table");
    Tabulator.extendExtension("format", "formatters", {
        pathToTombImg: function (cell, formatterParams) {
            var path = cell.getRow().getData().path;
            return "<img src='/images/panoscans/" + cell.getValue().slice(0, -4) + "_tumb.jpg' height='30' data-toggle='modal' data-target='.bs-view-panoscan' data-data='" + cell.getValue().slice(0, -4) + "_bt.jpg'/>";
        },
        actionCell: function (cell, formatterParams) {
            var path = cell.getRow().getData().path;
            var panoBtn="";
            if(cell.getRow().getData().panoramas!=null) {
                $.each(cell.getRow().getData().panoramas, function( index, value ) {
                    panoBtn+="<a href='/admin/pano3Dedit-"+value.id+"'><i class='fa fa-codepen' style='cursor: pointer' title='"+value.name+"'></i></a>&nbsp;";
                });
            } else {
                panoBtn = $("<i class='fa fa-codepen' style='color: #ddd' title='Панорамы отсутствуют'></i>");
            }
            var delBtn = $("<i class='fa fa-trash' style='cursor: pointer' title='Удалить'></i>");
            var genBtn = $("<i class='fa fa-magic' style='cursor: pointer' title='Генерация панорамы'></i>");
            var reScanBtn = $("<i class='fa fa-upload' style='cursor: pointer' title='Перезакачать'></i>");
            var dwnldBtn = $("<a href='/images/panoscans/"+path+"' download='"+path+"'><i class='fa fa-download' title='Скачать развертку'></i></a>");
            delBtn.on("click", function () {
                var notic = new PNotify({
                    title: 'Подтвердите удаление',
                    text: 'Восстановить развертку будет невозможно',
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
                        data: 'path=' + path,
                        url: 'ajaxRemovePanoscan',
                        success: function (data) {
                            if (data == 'SUCCESS') {
                                new PNotify({
                                    title: 'Удаление',
                                    text: 'Развёртка успешно удалена',
                                    type: 'success'
                                });
                                var row = cell.getRow()
                                row.delete();
                                row.delete();
                            }
                            if (data == 'ERROR') {
                                new PNotify({
                                    title: 'Удаление',
                                    text: 'При удалении развертки произошла ошибка',
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
                notic.get().on('pnotify.cancel', function() {

                });

            });
            genBtn.on("click", function (event) {
                $("html").addClass('loading');
                $.ajax({
                    type: 'POST',
                    data: 'path=' + path+'&krpanoConfigPath='+$("#krpanoConfigPath option:selected").val(),
                    url: 'ajaxGenPanorama',
                    success: function (data) {
                        var panoPath=path.slice(0, -4);
                        if (data !== null) {
                            if ( event.ctrlKey ) {
                                window.location.replace('pano3Dedit-'+data.id);
                            } else {
                                $("html").removeClass('loading');
                                new PNotify({
                                    title: 'Генерация',
                                    text: 'Панорама '+data.title+' успешно сгенерирована',
                                    type: 'success'
                                });
                            }

                        } else {
                            new PNotify({
                                title: 'Генерация',
                                text: 'При генерации панорамы произошел сбой',
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
                            title: 'Генерация',
                            text: msg,
                            type: 'error'
                        });
                    }
                });
            });
            reScanBtn.on("click", function () {
                $("#reScan").val(path);
            });
            return delBtn.add(genBtn).add(reScanBtn).append("&nbsp;").add(dwnldBtn).append("&nbsp;").add(panoBtn);
        },
    });
    scntbl.tabulator({
        placeholder: "Загрузка данных",
        layout: "fitColumns",
        groupBy:"region.viewName",
        groupStartOpen: false,
        groupToggleElement:"header",
        columns: [
            {title: "", field: "action", align: "left", formatter: "actionCell", width: "140"},
            {title: "Фото", field: "path", align: "center", formatter: "pathToTombImg", width: "80", sorter: "string"},
            {title: "Загружен", field: "dateOfDownload", align: "center", sorter: "date", sorterParams:{format:"DD/MM/YYYY"}, width: "100"},
            {title: "Размер", field: "size", align: "center", sorter: "number", width: "100"},
            {title: "Регион", field: "region.viewName", width: "100%", visible:false},
            {title: "Название", field: "name", sorter: "string"},
            {title: "Файл", field: "path", sorter: "string"},
        ],
        initialSort:[
            {column: "path", dir:"asc"},
            {column:"dateOfDownload", dir:"desc"},
        ],
        // pagination:"local",
        // paginationSize:10,
        ajaxURL: "getScansJson",
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
                    "item":"развертка", //the singluar for item
                    "items":"разверток", //the plural for items
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
        },
    });

    $(".collapse-table").on("click",function(){
        var groups = scntbl.tabulator("getGroups");
        $.each(groups,function(i, group){
            group.hide();
        })
    });
    $(".recollapse-table").on("click",function(){
        var groups = scntbl.tabulator("getGroups");
        $.each(groups,function(i, group){
            group.show();
        })
    });

    $("#input-709").fileinput({
        language: "ru",
        theme: "fa",
        uploadUrl: "uploadpanoscan",
        uploadAsync: true,
        showPreview: false,
        allowedFileExtensions: ['jpg'],
        maxFileSize: 100000,
        maxFileCount: 1,
        autoReplace: true,
        elErrorContainer: '#kv-error-1',
        uploadExtraData: function(previewId, index) {
            return {
                region: $("#region option:selected").val(),
                name: $("#scanName").val(),
                rescan: $("#reScan").val()
            }
        }
    }).on('filebatchpreupload', function (event, data, id, index) {
    }).on('fileuploaded', function (event, data, id, index) {
        var scanObj = data.response;
        if ($("#reScan").val().length>0) {
            scntbl.tabulator("updateData",[scanObj], true);
        } else {
            scntbl.tabulator("addData",scanObj, true);
        }

        new PNotify({
            title: 'Сохранение',
            text: "Развертка успешно загружена!",
            type: 'success'
        });
    });
    document.getElementById("upload-dir-btn").addEventListener("click", function(){
        var region = { region: $("#region").val() };
        $.ajax({
            type: 'POST',
            data: JSON.stringify(region),
            dataType: 'json',
            contentType: 'application/json',
            url: 'updateFromUploadDir',
            success: function (data) {
                if (data.status == 'SUCCESS') {
                    new PNotify({
                        title: 'Сохранение',
                        text: 'Развертки из каталога upload успешно сохранились',
                        type: 'success'
                    });
                }
                if (data.status == 'EMPTY') {
                    new PNotify({
                        title: 'Сохранение',
                        text: 'Каталог upload пустой',
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
    });
});