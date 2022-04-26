define(function (require) {

    require('../../domain/Component').registerSubTypeDefaulter('dataZoom', function (option) {
        // Default 'slider' when no type specified.
        return 'slider';
    });

});
