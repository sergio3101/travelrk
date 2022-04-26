ymaps.ready(init);
var myMap,myPlacemark,coords = [45.195666, 34.053999], defcoords = [45.195666, 34.053999];
function init() {
    myMap = new ymaps.Map('map', {
        center: defcoords,
        width: 600,
        height: 400,
        zoom: 7,
        type: 'yandex#hybrid',
        controls: ['zoomControl', 'typeSelector']
    });

    /*var searchControl = new ymaps.control.SearchControl({
     options: {
     float: 'left',
     floatIndex: 100,
     noPlacemark: true
     }
     });
     myMap.controls.add(searchControl);*/

    //Определяем метку и добавляем ее на карту
    myPlacemark = new ymaps.Placemark(defcoords, {}, {preset: "islands#redIcon", draggable: true});
    myMap.geoObjects.add(myPlacemark);

    //Отслеживаем событие перемещения метки
    myPlacemark.events.add("dragend", function (e) {
        coords = this.geometry.getCoordinates();
        savecoordinats();
    }, myPlacemark);

    //Отслеживаем событие щелчка по карте
    myMap.events.add('click', function (e) {
        coords = e.get('coords');
        savecoordinats();
    });

    /*//Отслеживаем событие выбора результата поиска
     searchControl.events.add("resultselect", function (e) {
     coords = searchControl.getResultsArray()[0].geometry.getCoordinates();
     savecoordinats();
     });*/

    //Ослеживаем событие изменения области просмотра карты - масштаб и центр карты
    myMap.events.add('boundschange', function (event) {
        if (event.get('newZoom') != event.get('oldZoom')) {
//                    savecoordinats();
        }
        if (event.get('newCenter') != event.get('oldCenter')) {
//                    savecoordinats();
        }
    });

    var lat = $('form#customerInfoForm input#officeLat').val();
    var lng = $('form#customerInfoForm input#officeLng').val();
    var crds = [];
    if (lat != "" && lng != "") {
        crds = [lat, lng];
        myPlacemark.geometry.setCoordinates(crds);
        myMap.setCenter(crds);
        myMap.setZoom(7);
    } else {
        myPlacemark.geometry.setCoordinates(defcoords);
        myMap.setCenter(defcoords);
        myMap.setZoom(7);
    }
}

//Функция для передачи полученных значений в форму
function savecoordinats() {
    var new_lat = coords[0].toFixed(6);
    var new_lon = coords[1].toFixed(6);
    var new_coords = [new_lat, new_lon];
    myPlacemark.getOverlaySync().getData().geometry.setCoordinates(new_coords);
    document.getElementById("officeLat").value = new_lat;
    document.getElementById("officeLng").value = new_lon;
//            document.getElementById("mapzoom").value = myMap.getZoom();
//            var center = myMap.getCenter();
//            var new_center = [center[0].toFixed(4), center[1].toFixed(4)];
//            document.getElementById("latlongcenter").value = new_center;
}