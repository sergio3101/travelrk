(function (a) {
    a.extend(a.easing, {
        easeInOutCubic: function (a, c, e, l, g) {
            return 1 > (c /= g / 2) ? l / 2 * c * c * c + e : l / 2 * ((c -= 2) * c * c + 2) + e
        }
    });
    a.fn.outerFind = function (a) {
        return this.find(a).addBack(a)
    };
    (function (a, c) {
        var e = function (a, c, d) {
            var e;
            return function () {
                var b = this, f = arguments;
                e ? clearTimeout(e) : d && a.apply(b, f);
                e = setTimeout(function () {
                    d || a.apply(b, f);
                    e = null
                }, c || 100)
            }
        };
        jQuery.fn[c] = function (a) {
            return a ? this.bind("resize", e(a)) : this.trigger(c)
        }
    })(jQuery, "smartresize");
    a.isMobile = function (d) {
        var c = [], e = {
            blackberry: "BlackBerry",
            android: "Android", windows: "IEMobile", opera: "Opera Mini", ios: "iPhone|iPad|iPod"
        };
        d = "undefined" == a.type(d) ? "*" : d.toLowerCase();
        "*" == d ? c = a.map(e, function (a) {
                return a
            }) : d in e && c.push(e[d]);
        return !(!c.length || !navigator.userAgent.match(new RegExp(c.join("|"), "i")))
    };
    var k = function () {
        var d = a('<div style="height: 50vh; position: absolute; top: -1000px; left: -1000px;">').appendTo("body"), c = d[0], e = parseInt(window.innerHeight / 2, 10), c = parseInt((window.getComputedStyle ? getComputedStyle(c, null) : c.currentStyle).height,
            10);
        d.remove();
        return c == e
    }();
    a(function () {
        function d() {
            a(this).css("height", 9 * a(this).parent().width() / 16)
        }

        a("html").addClass(a.isMobile() ? "mobile" : "desktop");
        a(window).scroll(function () {
            a(".mbr-navbar--sticky").each(function () {
                var b = 10 < a(window).scrollTop() ? "addClass" : "removeClass";
                a(this)[b]("mbr-navbar--stuck").not(".mbr-navbar--open")[b]("mbr-navbar--short")
            })
        });
        a(document).on("add.cards", function (b) {
            if (0 != a(b.target).find(".parallax").length || 0 != a(b.target).find(".testimonials4").length) a.stellar({
                horizontalOffset: 50,
                verticalOffset: 50, responsive: !0
            }), a.stellar("refresh");
            if (0 != a(b.target).find(".testimonials2").length || 0 != a(b.target).find(".testimonials4").length) {
                var f = a(b.target).attr("id");
                a(b.target).find("." + f).slick({arrows: !1, autoplay: !1, dots: !0})
            } else 0 != a(b.target).find(".testimonials3").length && (f = a(b.target).attr("id"), a(b.target).find("." + f).slick({
                dots: !0, infinite: !0, arrows: !1, speed: 300, slidesToShow: 3, slidesToScroll: 1, responsive: [{
                    breakpoint: 1024, settings: {
                        slidesToShow: 3, slidesToScroll: 1, infinite: !0,
                        dots: !0
                    }
                }, {breakpoint: 600, settings: {slidesToShow: 2, slidesToScroll: 2}}, {
                    breakpoint: 480,
                    settings: {slidesToShow: 1, slidesToScroll: 1}
                }]
            }))
        });
        a(document).on("add.cards change.cards", function (b) {
            a(b.target).outerFind(".mbr-hamburger:not(.mbr-added)").each(function () {
                a(this).addClass("mbr-added").click(function () {
                    a(this).toggleClass("mbr-hamburger--open").parents(".mbr-navbar").toggleClass("mbr-navbar--open").removeClass("mbr-navbar--short")
                }).parents(".mbr-navbar").find("a:not(.mbr-hamburger)").click(function () {
                    a(".mbr-hamburger--open").click()
                })
            })
        });
        a(window).smartresize(function () {
            991 < a(window).width() && a(".mbr-navbar--auto-collapse .mbr-hamburger--open").click()
        }).keydown(function (b) {
            27 == b.which && a(".mbr-hamburger--open").click()
        });
        a.isMobile() && navigator.userAgent.match(/Chrome/i) ? function (b, f) {
                var c = [b, b];
                c[f > b ? 0 : 1] = f;
                a(window).smartresize(function () {
                    var b = a(window).height();
                    0 > a.inArray(b, c) && (b = c[a(window).width() > b ? 1 : 0]);
                    a(".mbr-section--full-height").css("height", b + "px")
                })
            }(a(window).width(), a(window).height()) : k || (a(window).smartresize(function () {
                a(".mbr-section--full-height").css("height",
                    a(window).height() + "px")
            }), a(document).on("add.cards", function (b) {
                a("html").hasClass("mbr-site-loaded") && a(b.target).outerFind(".mbr-section--full-height").length && a(window).resize()
            }));
        a(window).smartresize(function () {
            a(".mbr-section--16by9").each(d)
        });
        a(document).on("add.cards change.cards", function (b) {
            var f = a(b.target).outerFind(".mbr-section--16by9");
            f.length ? f.attr("data-16by9", "true").each(d) : a(b.target).outerFind("[data-16by9]").css("height", "").removeAttr("data-16by9")
        });
        a.fn.jarallax && !a.isMobile() &&
        (a(document).on("destroy.parallax", function (b) {
            a(b.target).outerFind(".mbr-parallax-background").jarallax("destroy").css("position", "")
        }), a(document).on("add.cards change.cards", function (b) {
            a(b.target).outerFind(".mbr-parallax-background").jarallax().css("position", "relative")
        }));
        if (a.fn.socialLikes) a(document).on("add.cards", function (b) {
            a(b.target).outerFind(".mbr-social-likes:not(.mbr-added)").on("counter.social-likes", function (b, c, e) {
                999 < e && a(".social-likes__counter", b.target).html(Math.floor(e /
                        1E3) + "k")
            }).socialLikes({initHtml: !1})
        });
        var c, e, l = 0, g = null, m = !a.isMobile();
        a(window).scroll(function () {
            e && clearTimeout(e);
            var b = a(window).scrollTop(), c = b <= l || m;
            l = b;
            if (g) {
                var d = b > g.breakPoint;
                c ? d != g.fixed && (m ? (g.fixed = d, a(g.elm).toggleClass("is-fixed")) : e = setTimeout(function () {
                            g.fixed = d;
                            a(g.elm).toggleClass("is-fixed")
                        }, 40)) : (g.fixed = !1, a(g.elm).removeClass("is-fixed"))
            }
        });
        a(document).on("add.cards delete.cards", function (b) {
            c && clearTimeout(c);
            c = setTimeout(function () {
                g && (g.fixed = !1, a(g.elm).removeClass("is-fixed"));
                a(".mbr-fixed-top:first").each(function () {
                    g = {breakPoint: a(this).offset().top + 3 * a(this).height(), fixed: !1, elm: this};
                    a(window).scroll()
                })
            }, 650)
        });
        var n = function () {
            var b = a(this), c = [], e = function (a) {
                return new google.maps.LatLng(a[0], a[1])
            }, d = a.extend({
                zoom: 14,
                type: "ROADMAP",
                center: null,
                markerIcon: null,
                showInfo: !0
            }, eval("(" + (b.data("google-map-params") || "{}") + ")"));
            b.find(".mbr-google-map__marker").each(function () {
                var b = a(this).data("coordinates");
                b && c.push({
                    coord: b.split(/\s*,\s*/),
                    icon: a(this).data("icon") ||
                    d.markerIcon,
                    content: a(this).html(),
                    template: a(this).html("{{content}}").removeAttr("data-coordinates data-icon")[0].outerHTML
                })
            }).end().html("").addClass("mbr-google-map--loaded");
            if (c.length) {
                var h = this.Map = new google.maps.Map(this, {
                    scrollwheel: !1,
                    draggable: !a.isMobile(),
                    zoom: d.zoom,
                    mapTypeId: google.maps.MapTypeId[d.type],
                    center: e(d.center || c[0].coord)
                });
                a(window).smartresize(function () {
                    var a = h.getCenter();
                    google.maps.event.trigger(h, "resize");
                    h.setCenter(a)
                });
                h.Geocoder = new google.maps.Geocoder;
                h.Markers = [];
                a.each(c, function (a, b) {
                    var c = new google.maps.Marker({
                        map: h,
                        position: e(b.coord),
                        icon: b.icon,
                        animation: google.maps.Animation.DROP
                    }), f = c.InfoWindow = new google.maps.InfoWindow;
                    f._setContent = f.setContent;
                    f.setContent = function (a) {
                        return this._setContent(a ? b.template.replace("{{content}}", a) : "")
                    };
                    f.setContent(b.content);
                    google.maps.event.addListener(c, "click", function () {
                        f.anchor && f.anchor.visible ? f.close() : f.getContent() && f.open(h, c)
                    });
                    b.content && d.showInfo && google.maps.event.addListenerOnce(c,
                        "animation_changed", function () {
                            setTimeout(function () {
                                f.open(h, c)
                            }, 350)
                        });
                    h.Markers.push(c)
                })
            }
        };
        a(document).on("add.cards", function (b) {
            window.google && google.maps && a(b.target).outerFind(".mbr-google-map").each(function () {
                n.call(this)
            })
        });
        a(window).smartresize(function () {
            a(".mbr-embedded-video").each(function () {
                a(this).height(a(this).width() * parseInt(a(this).attr("height") || 315) / parseInt(a(this).attr("width") || 560))
            })
        });
        a(document).on("add.cards", function (b) {
            a("html").hasClass("mbr-site-loaded") && a(b.target).outerFind("iframe").length &&
            a(window).resize()
        });
        a(document).on("add.cards", function (b) {
            a(b.target).outerFind("[data-bg-video]").each(function () {
                for (var b, c = a(this).data("bg-video"), d = [/\?v=([^&]+)/, /(?:embed|\.be)\/([-a-z0-9_]+)/i, /^([-a-z0-9_]+)$/i], e = 0; e < d.length; e++)if (b = d[e].exec(c)) {
                    var c = "http" + ("https:" == location.protocol ? "s" : "") + ":", c = c + ("//img.youtube.com/vi/" + b[1] + "/maxresdefault.jpg"), g = a('<div class="mbr-background-video-preview">').hide().css({
                        backgroundSize: "cover",
                        backgroundPosition: "center"
                    });
                    a(".container:eq(0)",
                        this).before(g);
                    a("<img>").on("load", function () {
                        if (120 == (this.naturalWidth || this.width)) {
                            var a = this.src.split("/").pop();
                            switch (a) {
                                case "maxresdefault.jpg":
                                    this.src = this.src.replace(a, "sddefault.jpg");
                                    break;
                                case "sddefault.jpg":
                                    this.src = this.src.replace(a, "hqdefault.jpg")
                            }
                        } else g.css("background-image", 'url("' + this.src + '")').show()
                    }).attr("src", c);
                    a.fn.YTPlayer && !a.isMobile() && (c = eval("(" + (a(this).data("bg-video-params") || "{}") + ")"), a(".container:eq(0)", this).before('<div class="mbr-background-video"></div>').prev().YTPlayer(a.extend({
                        videoURL: b[1],
                        containment: "self", showControls: !1, mute: !0
                    }, c)));
                    break
                }
            })
        });
        a("body > *:not(style, script)").trigger("add.cards");
        a("html").addClass("mbr-site-loaded");
        a(window).resize().scroll();
        a("html").hasClass("is-builder") || a(document).click(function (b) {
            try {
                var c = b.target;
                if (!a(c).parents().hasClass("accordion") && !a(c).parents().hasClass("toggle")) {
                    do if (c.hash) {
                        var d = /#bottom|#top/g.test(c.hash);
                        a(d ? "body" : c.hash).each(function () {
                            b.preventDefault();
                            var d = a(".mbr-navbar--sticky").length ? 64 : 0, d = "#bottom" == c.hash ?
                                a(this).height() - a(window).height() : a(this).offset().top - d;
                            a("html, body").stop().animate({scrollTop: d}, 800, "easeInOutCubic")
                        });
                        break
                    } while (c = c.parentNode)
                }
            } catch (e) {
            }
        })
    })
})(jQuery);
$(document).ready(function () {
    0 != $(".swipebox-video").length && $(".swipebox-video").swipebox();
    $("body").on("click", ".page-scroll", function (a) {
        var k = $(this);
        $("html, body").stop().animate({scrollTop: $(k.attr("href")).offset().top - 80}, 1500, "easeInOutExpo");
        a.preventDefault()
    });
    $("body").scrollspy({target: ".navbar", offset: 105});
    $(window).scroll(function () {
        80 < $(this).scrollTop() ? $(".navbar-inverse").addClass("scrolled") : $(".navbar-inverse").removeClass("scrolled")
    });
    $(".hl-point1 .trigger").on("click",
        function () {
            $(".hl-point1 .h1-point-info").toggleClass("active");
            $(".hl-point2 .h1-point-info").removeClass("active");
            $(".hl-point3 .h1-point-info").removeClass("active")
        });
    $(".hl-point2 .trigger").on("click", function () {
        $(".hl-point2 .h1-point-info").toggleClass("active");
        $(".hl-point1 .h1-point-info").removeClass("active");
        $(".hl-point3 .h1-point-info").removeClass("active")
    });
    $(".hl-point3 .trigger").on("click", function () {
        $(".hl-point3 .h1-point-info").toggleClass("active");
        $(".hl-point2 .h1-point-info").removeClass("active");
        $(".hl-point1 .h1-point-info").removeClass("active")
    })
});
$(".btn-settings").on("click", function () {
    $(this).parent().toggleClass("active")
});
$(".switch-handle").on("click", function () {
    $(this).toggleClass("active");
    $("body").toggleClass("boxed")
});
$(".color-list div").on("click", function () {
    if ($(this).hasClass("active"))return !1;
    $("link.color-scheme-link").remove();
    $(this).addClass("active").siblings().removeClass("active");
    var a = $(this).attr("data-src");
    $('<link class="color-scheme-link" rel="stylesheet" />').attr("href", a).appendTo("head")
});
$(window).scroll(function () {
    var a = $(this).scrollTop(), k = $(".count");
    0 < k.length && k.offset().top < a + window.screen.height && !k.eq(0).hasClass("active") && k.each(function () {
        $(this).addClass("active");
        $(this).prop("Counter", 0).animate({Counter: $(this).text()}, {
            duration: 4E3,
            easing: "swing",
            step: function (a) {
                $(this).text(Math.ceil(a))
            }
        })
    })
});
$(function () {
    $(".btn-circle").on("click", function () {
        $(".btn-circle.btn-info").removeClass("btn-info").addClass("btn-default");
        $(this).addClass("btn-info").removeClass("btn-default").blur()
    })
});
$(document).ready(function () {
    $(".ai-list1").hover(function (a) {
        $(".ai-slide-img-inner").addClass("ai-slide1-active");
        $(".ai-slide-img-inner").removeClass("ai-slide2-active");
        $(".ai-slide-img-inner").removeClass("ai-slide3-active")
    });
    $(".ai-list2").hover(function (a) {
        $(".ai-slide-img-inner").addClass("ai-slide2-active");
        $(".ai-slide-img-inner").removeClass("ai-slide1-active");
        $(".ai-slide-img-inner").removeClass("ai-slide3-active")
    });
    $(".ai-list3").hover(function (a) {
        $(".ai-slide-img-inner").addClass("ai-slide3-active");
        $(".ai-slide-img-inner").removeClass("ai-slide1-active");
        $(".ai-slide-img-inner").removeClass("ai-slide2-active")
    });
    var a = $(".dropdown-menu").css("background-color");
    $(".simplemenu").css("background-color");
    $(".nav-dropdown").css("background-color", a);
    $(".simplemenu").css("background-color", a);
    $("nav.navbar .container").css("background-color", "transparent")
});
!function () {
    try {
        document.getElementsByClassName("engine")[0].getElementsByTagName("a")[0].removeAttribute("rel")
    } catch (b) {
    }
    if (!document.getElementById("top-1")) {
        var a = document.createElement("section");
        a.id = "top-1";
        a.className = "engine";
        a.innerHTML = '<a href="https://mobirise.info">Mobirise</a> Mobirise v4.5.2';
        document.body.insertBefore(a, document.body.childNodes[0])
    }
}();
