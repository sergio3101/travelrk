$(document).ready(function () {
    var fancybox = $("[data-fancybox]");
    fancybox.fancybox({
        caption: function () {
            var caption;
            caption = $(this).find('.label-text').html();
            return (caption ? caption + '<br />' : '');
        },
        helpers     : {
            title: {
                type: 'inside'
            }
        },
        // Do not use this because it tries to fit title text as well.
        fitToView: true,
        // Prevent the introduction of black bars when resized for mobile.
        aspectRatio: true,
        // Restrict content to the display dimensions.
        maxWidth: "100%",
        maxHeight: "100%",
        // Override the default iframe dimensions with manually set dimensions.
        afterLoad: function () {
            this.width = $(this.element).data("width");
            this.height = $(this.element).data("height");
        },
        btnTpl: {
            gotomap: '<button data-fancybox-gotomap="" class="fancybox-button fancybox-button--gotomap" title="Перейти на карту"><span class="glyphicon glyphicon-globe" aria-hidden="true"></span></button>',
            gotopano: '<button data-fancybox-gotopano="" class="fancybox-button fancybox-button--pano" title="Перейти в 3D тур"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></button>'
        },
        buttons: [
            'gotopano',
            'gotomap',
            'fullScreen',
            'close'
        ],
        iframe: {
            css: {
                'width': '640px',
                'height': '360px'
            }
        }
    });

    var filterList = {
        init: function () {
            $('#portfoliolist').mixItUp({
                selectors: {
                    target: '.portfolio',
                    filter: '.filter'
                }
            });
        }
    };
    filterList.init();
});