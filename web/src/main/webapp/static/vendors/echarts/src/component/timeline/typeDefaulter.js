define(function (require) {

    require('../../domain/Component').registerSubTypeDefaulter('timeline', function () {
        // Only slider now.
        return 'slider';
    });

});
