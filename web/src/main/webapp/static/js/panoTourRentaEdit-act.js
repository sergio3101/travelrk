window.ParsleyValidator.setLocale('ru');
$(document).ready(function () {
    $('#rentaExpired').daterangepicker({
        format: 'YYYY-MM-DD',
        singleDatePicker: true,
        singleClasses: "picker_3",
        orientation: 'top auto',
        drops: 'down',
        autoUpdateInput: true,
        useCurrent: true,
        locale: {
            cancelLabel: 'Clear',
            applyLabel: 'Apply',
            format: 'YYYY-MM-DD',
            orientation: 'top auto',
            daysOfWeek: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
            monthNames: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
            firstDay: 1
        }
    });
});
