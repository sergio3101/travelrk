(function () {
    var h = void 0, k = !0, l = null, m = !1;

    function o() {
        return function () {
        }
    }

    (function () {
        ktools = {
            i: function () {
                var a = "en", d = "", b = [], i = new ktools.k;
                this.setLanguage = this.Oa = function (c) {
                    a = c
                };
                this.getLanguage = this.da = function () {
                    return a
                };
                this.getAvailableLanguages = this.Z = function () {
                    return b
                };
                this.setLanguageFilePath = this.M = function (c, b) {
                    d = (c ? c : "") + a + (b ? b : "")
                };
                this.initLanguage = this.oa = function (c, f, g) {
                    a = c;
                    if (-1 == b.indexOf(a)) {
                        b.push(a);
                        f && this.M(f, g);
                        for (var c = ktools.r.I(d), f = c.getElementsByTagName("data").length, g = h, j = 0; j < f; j++) g = c.getElementsByTagName("data")[j].textContent !=
                        h ? c.getElementsByTagName("data")[j].textContent : h, g == h && c.getElementsByTagName("data")[j].innerText && (g = c.getElementsByTagName("data")[j].innerText), g == h && c.getElementsByTagName("data")[j].innerHTML && (g = c.getElementsByTagName("data")[j].innerHTML), g == h && c.getElementsByTagName("data")[j].text && (g = c.getElementsByTagName("data")[j].text), g == h && c.getElementsByTagName("data")[j].firstChild && (g = c.getElementsByTagName("data")[j].firstChild.data), i.add(c.getElementsByTagName("data")[j].getAttribute("name"),
                            g)
                    }
                };
                this.getMessage = this.ea = function (c) {
                    return i.item(a + "_" + c)
                }
            }
        };
        ktools.I18N = ktools.i;
        ktools.i.a = l;
        ktools.I18N.instance = ktools.i.a;
        ktools.i.d = function () {
            this.a == l && (this.a = new ktools.i);
            return this.a
        };
        ktools.I18N.getInstance = ktools.i.d;
        ktools.r = o();
        ktools.XML = ktools.r;
        ktools.r.I = function (a) {
            var d = l, b = l, i = m;
            try {
                d = new XMLHttpRequest, d.open("GET", a, m)
            } catch (c) {
                if ("undefined" != typeof window.ActiveXObject) {
                    b = new ActiveXObject("Microsoft.XMLDOM");
                    for (b.async = m; 4 != b.readyState;) ;
                    b.load(a);
                    i = k;
                    return b
                }
                b =
                    document.implementation.createDocument("", "", l);
                b.onload = function () {
                    return b
                };
                b.load(a);
                i = k
            }
            if (!i) return d.send(""), b = d.responseXML, i = k, b
        };
        ktools.XML.importXML = ktools.r.I;
        ktools.A = o();
        ktools.Device = ktools.A;
        ktools.A.Aa = function () {
            return "ontouchstart" in window || "ontouchstart" in document.documentElement || window.Ua && document instanceof DocumentTouch
        };
        ktools.Device.isTouch = ktools.A.Aa;
        ktools.e = o();
        ktools.DOM = ktools.e;
        ktools.e.fa = function (a) {
            for (var d = y = 0; a && !isNaN(a.offsetLeft) && !isNaN(a.offsetTop);) d +=
                a.offsetLeft - a.scrollLeft, y += a.offsetTop - a.scrollTop, a = a.offsetParent;
            return {x: d, y: y}
        };
        ktools.DOM.getOffset = ktools.e.fa;
        ktools.e.preventDefault = function (a) {
            e = a ? a : window.event;
            e.preventDefault ? e.preventDefault() : e.returnValue = m
        };
        ktools.DOM.preventDefault = ktools.e.preventDefault;
        ktools.e.Qa = function (a) {
            e = a ? a : window.event;
            e.stopPropagation ? e.stopPropagation() : e.cancelBubble = k
        };
        ktools.DOM.stopEventPropagation = ktools.e.Qa;
        ktools.g = o();
        ktools.String = ktools.g;
        ktools.g.Pa = function (a, d) {
            return a.match("^" +
                d) == d
        };
        ktools.String.startsWith = ktools.g.Pa;
        ktools.g.X = function (a) {
            kolorBrowserDetect.m() && !kolorBrowserDetect.C() && (a = toStaticHTML(a));
            var d = a, d = jQuery("<div/>").html(a).text();
            return unescape(d)
        };
        ktools.String.decode = ktools.g.X;
        ktools.g.Ra = function (a) {
            var d = document.createElement("div");
            d.innerHTML = kolorBrowserDetect.m() && !kolorBrowserDetect.C() ? toStaticHTML(a) : a;
            var b, a = d.getElementsByTagName("script");
            for (b = a.length; b--;) a[b].parentNode.removeChild(a[b]);
            a = d.getElementsByTagName("style");
            for (b =
                     a.length; b--;) a[b].parentNode.removeChild(a[b]);
            a = d.getElementsByTagName("iframe");
            for (b = a.length; b--;) a[b].parentNode.removeChild(a[b]);
            a = d.getElementsByTagName("audio");
            for (b = a.length; b--;) a[b].parentNode.removeChild(a[b]);
            a = d.getElementsByTagName("canvas");
            for (b = a.length; b--;) a[b].parentNode.removeChild(a[b]);
            a = d.getElementsByTagName("embed");
            for (b = a.length; b--;) a[b].parentNode.removeChild(a[b]);
            a = d.getElementsByTagName("form");
            for (b = a.length; b--;) a[b].parentNode.removeChild(a[b]);
            a = d.getElementsByTagName("object");
            for (b = a.length; b--;) a[b].parentNode.removeChild(a[b]);
            a = d.getElementsByTagName("video");
            for (b = a.length; b--;) a[b].parentNode.removeChild(a[b]);
            return d.innerHTML
        };
        ktools.String.stripTags = ktools.g.Ra;
        ktools.z = o();
        ktools.Code = ktools.z;
        ktools.z.eval = function (a) {
            a = a.replace(/(\r\n|\n|\r|\t)/gm, "");
            eval(a)
        };
        ktools.Code.eval = ktools.z.eval;
        ktools.B = o();
        ktools.Url = ktools.B;
        ktools.B.ga = function (a, d) {
            var d = d.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]"), b = RegExp("[\\?&]" + d + "=([^&#]*)").exec(a);
            return b == l ? "" :
                b[1]
        };
        ktools.Url.getParam = ktools.B.ga;
        ktools.o = o();
        ktools.Color = ktools.o;
        ktools.o.ma = function (a, d) {
            var b = /^#?[0-9a-f]{3,6}$/i.test(a);
            return "undefined" != typeof a && (7 == a.length || 4 == a.length) && b ? a.toUpperCase() : "undefined" != typeof a && (6 == a.length || 3 == a.length) && b ? "#" + a.toUpperCase() : "undefined" != typeof d ? d : "#FFFFFF"
        };
        ktools.Color.htmlColor = ktools.o.ma;
        ktools.o.la = function (a) {
            var d = [];
            d[0] = parseInt(a.substr(0, 2), 16);
            d[1] = parseInt(a.substr(2, 2), 16);
            d[2] = parseInt(a.substr(4, 2), 16);
            return d
        };
        ktools.Color.hex2rgb =
            ktools.o.la;
        ktools.P = function () {
            function a(a) {
                var b = a.indexOf(this.w);
                return -1 == b ? h : parseFloat(a.substring(b + this.w.length + 1))
            }

            function d(a) {
                for (var b = 0, d = a.length; b < d; b++) {
                    var j = a[b].string, q = a[b].prop;
                    this.w = a[b].versionSearch || a[b].identity;
                    if (j) {
                        if (-1 != j.indexOf(a[b].subString)) return a[b].identity
                    } else if (q) return a[b].identity
                }
            }

            this.versionSearchString = this.w = this.version = this.version = this.OS = this.f = this.browser = this.browser = "";
            var b = [{
                    string: navigator.userAgent, subString: "Edge", identity: "Explorer",
                    versionSearch: "Edge"
                }, {string: navigator.userAgent, subString: "Chrome", identity: "Chrome"}, {
                    string: navigator.userAgent,
                    subString: "OmniWeb",
                    versionSearch: "OmniWeb/",
                    identity: "OmniWeb"
                }, {
                    string: navigator.vendor,
                    subString: "Apple",
                    identity: "Safari",
                    versionSearch: "Version"
                }, {prop: window.opera, identity: "Opera", versionSearch: "Version"}, {
                    string: navigator.vendor,
                    subString: "iCab",
                    identity: "iCab"
                }, {string: navigator.vendor, subString: "KDE", identity: "Konqueror"}, {
                    string: navigator.userAgent,
                    subString: "Firefox",
                    identity: "Firefox"
                },
                    {string: navigator.vendor, subString: "Camino", identity: "Camino"}, {
                        string: navigator.userAgent,
                        subString: "Netscape",
                        identity: "Netscape"
                    }, {
                        string: navigator.userAgent,
                        subString: "MSIE",
                        identity: "Explorer",
                        versionSearch: "MSIE"
                    }, {
                        string: navigator.userAgent,
                        subString: "Trident",
                        identity: "Explorer",
                        versionSearch: "MSIE"
                    }, {
                        string: navigator.userAgent,
                        subString: "Gecko",
                        identity: "Mozilla",
                        versionSearch: "rv"
                    }, {string: navigator.userAgent, subString: "Mozilla", identity: "Netscape", versionSearch: "Mozilla"}],
                i = [{
                    string: navigator.platform,
                    subString: "Win", identity: "Windows"
                }, {string: navigator.platform, subString: "Mac", identity: "Mac"}, {
                    string: navigator.userAgent,
                    subString: "iPhone",
                    identity: "iPhone/iPod"
                }, {string: navigator.userAgent, subString: "iPad", identity: "iPad"}, {
                    string: navigator.platform,
                    subString: "Linux",
                    identity: "Linux"
                }];
            this.isIE = this.m = function () {
                return "Explorer" == this.browser
            };
            this.isVista = this.Ba = function () {
                return -1 != navigator.userAgent.indexOf("Windows NT 6.0")
            };
            this.isXP = this.Ia = function () {
                return -1 != navigator.userAgent.indexOf("Windows NT 5")
            };
            this.isWindows7 = this.Ga = function () {
                return -1 != navigator.userAgent.indexOf("Windows NT 6.1")
            };
            this.isWindows8 = this.Ha = function () {
                return -1 != navigator.userAgent.indexOf("Windows NT 6.2")
            };
            this.isWindows10 = this.Fa = function () {
                return -1 != navigator.userAgent.indexOf("Windows NT 10.")
            };
            this.isWindows = this.Ea = function () {
                return "Windows" == this.f
            };
            this.isLinux = this.wa = function () {
                return "Linux" == this.f
            };
            this.isWP8 = this.Da = function () {
                return -1 != navigator.userAgent.indexOf("Windows Phone 8")
            };
            this.isWP7 = this.Ca = function () {
                return -1 !=
                    navigator.userAgent.indexOf("Windows Phone 7")
            };
            this.isAndroid = this.pa = function () {
                return -1 != navigator.userAgent.indexOf("Android")
            };
            this.isIE9 = this.ta = function () {
                return this.m() && 9 == this.t()
            };
            this.isEdge = this.C = function () {
                return this.m() && 12 <= this.t()
            };
            this.isAtLeastIEX = this.qa = function (a) {
                return this.m() && this.t() >= a
            };
            this.isMac = this.xa = function () {
                return "Mac" == this.f || this.u()
            };
            this.isIOS = this.u = function () {
                return "iPhone/iPod" == this.f || "iPad" == this.f
            };
            this.isIOSRetina = this.ua = function () {
                return this.u() &&
                    1 < window.devicePixelRatio
            };
            this.isMobileSafari = this.ya = function () {
                return this.L() && this.u()
            };
            this.isSafari = this.L = function () {
                return "Safari" == this.browser
            };
            this.isChrome = this.ra = function () {
                return "Chrome" == this.browser
            };
            this.isFirefox = this.sa = function () {
                return "Firefox" == this.browser || "Mozilla" == this.browser
            };
            this.isOpera = this.za = function () {
                return "Opera" == this.browser
            };
            this.getInternetExplorerVersion = this.t = function () {
                var a = -1;
                if ("Microsoft Internet Explorer" == navigator.appName) {
                    var b = navigator.userAgent,
                        d = /MSIE ([0-9]{1,}[.0-9]{0,})/;
                    d.exec(b) != l && (a = parseFloat(RegExp.$1))
                } else "Netscape" == navigator.appName && (b = navigator.userAgent, d = /Trident\/.*rv:([0-9]{1,}[.0-9]{0,})/, d.exec(b) != l ? a = parseFloat(RegExp.$1) : /Windows NT 10.*Edge\/([0-9]{1,}[.0-9]{0,})/.exec(b) != l && (a = parseFloat(RegExp.$1)));
                return a
            };
            this.init = this.na = function () {
                this.browser = d(b) || "An unknown browser";
                this.version = a(navigator.userAgent) || a(navigator.appVersion) || "An unknown version";
                this.f = d(i) || "An unknown OS"
            }
        };
        ktools.BrowserDetect =
            ktools.P;
        ktools.R = function (a) {
            var d = ["webkit", "moz", "ms", "o", "khtml"], b = a || document.documentElement, i = m, c = "", f = "",
                g = {enter: l, exit: l, change: l, resize: l}, j = m;
            this.getExternal = this.b = function () {
                return g
            };
            this.setExternal = this.Na = function (a) {
                g = a
            };
            this.isFullscreen = this.D = function () {
                return "" === c ? document.fullscreen ? document.fullscreen : document.fullScreen : "webkit" == c ? document[c + "IsFullscreen"] ? document[c + "IsFullscreen"] : document[c + "IsFullScreen"] : document[c + "Fullscreen"] ? document[c + "Fullscreen"] : document[c +
                "FullScreen"]
            };
            this.getFullscreenElement = this.p = function () {
                if (i) {
                    var a = document;
                    return "" === c ? a.fullscreenElement ? a.fullscreenElement : a.fullScreenElement : a[c + "FullscreenElement"] ? a[c + "FullscreenElement"] : a[c + "FullScreenElement"]
                }
                return l
            };
            this.getFullscreenEnabled = this.ca = function () {
                if (i) return k;
                var a = document;
                return "" === c ? a.fullscreenEnabled ? a.fullscreenEnabled : a.fullScreenEnabled : "webkit" == c ? kolorBrowserDetect && "Safari" == kolorBrowserDetect.browser && 7 > kolorBrowserDetect.version ? m : a[c + "RequestFullscreen"] ?
                    a[c + "RequestFullscreen"] : a[c + "RequestFullScreen"] : a[c + "FullscreenEnabled"] ? a[c + "FullscreenEnabled"] : a[c + "FullScreenEnabled"]
            };
            this.supportsFullscreen = this.Sa = function () {
                debug && console.log("ktools.Fullscreen.supportsFullscreen");
                f = "";
                if ("undefined" != typeof document.exitFullscreen || "undefined" != typeof document.exitFullScreen || "undefined" != typeof document.cancelFullscreen || "undefined" != typeof document.cancelFullScreen) i = k, f += "fullscreenchange"; else for (var a = 0, g = d.length; a < g; a++) {
                    c = d[a];
                    if ("undefined" !=
                        typeof document[c + "CancelFullScreen"] || "undefined" != typeof document[c + "CancelFullscreen"] || "undefined" != typeof document[c + "ExitFullscreen"] || "undefined" != typeof document[c + "ExitFullScreen"]) {
                        if ("webkit" == c && kolorBrowserDetect && "Safari" == kolorBrowserDetect.browser && 7 > kolorBrowserDetect.version) {
                            i = m;
                            break
                        }
                        i = k;
                        f = "ms" == c ? f + "MSFullscreenChange" : f + c + "fullscreenchange";
                        break
                    }
                    if ("webkit" == c) {
                        var p = b;
                        if (p.webkitSupportsFullscreen) {
                            i = p.webkitSupportsFullscreen;
                            break
                        }
                    }
                }
                a = "#" + b.id + ":fullscreen { height: 100% !important; width: 100% !important; max-height: 100% !important; background-color: black; }";
                a += "#" + b.id + ":fullscreen::backdrop { background-color: black; }";
                a += "#" + b.id + ":full-screen { height: 100% !important; width: 100% !important; max-height: 100% !important; background-color: black; }";
                "" != c && b.id && ("ms" == c ? (a += "#" + b.id + ":-" + c + "-fullscreen { height: 100% !important; width: 100% !important; max-height: 100% !important; background-color: black; }", a += "#" + b.id + ":-" + c + "-fullscreen::-" + c + "-backdrop { background-color: black; }") : a += "#" + b.id + ":-" + c + "-full-screen { height: 100% !important; width: 100% !important; max-height: 100% !important; background-color: black; }");
                g = document.createElement("style");
                g.type = "text/css";
                g.styleSheet ? g.styleSheet.cssText = a : g.appendChild(document.createTextNode(a));
                document.getElementsByTagName("head")[0].appendChild(g);
                var n = this;
                document.addEventListener ? document.addEventListener(f, function () {
                    j = n.p() != l ? k : m;
                    if (n.b().change && n.b().resize) {
                        n.b().change(j);
                        n.b().resize()
                    }
                }) : f && document.attachEvent(f, function () {
                    j = n.p() != l ? k : m;
                    if (n.b().change && n.b().resize) {
                        n.b().change(j);
                        n.b().resize()
                    }
                });
                return i
            };
            this.request = this.La = function () {
                debug &&
                console.log("ktools.Fullscreen.request");
                if (i) {
                    if (!this.D() || !this.p()) if ("" === c) b.requestFullscreen ? b.requestFullscreen() : b.requestFullScreen(); else if (b[c + "RequestFullscreen"]) if ("webkit" == c) b[c + "RequestFullscreen"](Element.O); else b[c + "RequestFullscreen"](); else if ("webkit" == c) b[c + "RequestFullScreen"](Element.O); else b[c + "RequestFullScreen"]()
                } else this.b().enter && this.b().enter()
            };
            this.exit = this.Y = function () {
                debug && console.log("ktools.Fullscreen.exit");
                if (i) {
                    if (this.D() || this.p()) {
                        var a = document;
                        if ("" === c) a.exitFullscreen ? a.exitFullscreen() : a.exitFullScreen ? a.exitFullScreen() : a.cancelFullscreen ? a.cancelFullscreen() : a.cancelFullScreen(); else if (a[c + "ExitFullscreen"]) a[c + "ExitFullscreen"](); else if (a[c + "ExitFullScreen"]) a[c + "ExitFullScreen"](); else if (a[c + "CancelFullscreen"]) a[c + "CancelFullscreen"](); else a[c + "CancelFullScreen"]()
                    }
                } else this.b().exit && this.b().exit()
            }
        };
        ktools.Fullscreen = ktools.R;
        ktools.n = function () {
            var a = [];
            this.add = this.add = function (d) {
                a.push(d)
            };
            this.remove = this.remove = function (d) {
                for (var b =
                    0; b < a.length; b++) a[b] == d && a.splice(b, 1)
            };
            this.get = this.get = function (d) {
                return a[d]
            };
            this.toArray = this.toArray = function () {
                return a
            };
            this.size = this.size = function () {
                return a.length
            }
        };
        ktools.Collection = ktools.n;
        ktools.k = function () {
            this.count = this.count = 0;
            this.map = this.map = {};
            this.add = this.add = function (a, d) {
                if ("undefined" != typeof a) {
                    if (this.map[a] != h) return this.count;
                    this.map[a] = d;
                    return ++this.count
                }
            };
            this.update = this.update = function (a, d) {
                if ("undefined" != typeof a) return this.map[a] = d, this.count
            };
            this.remove =
                this.remove = function (a) {
                    if (this.map[a] != h) return delete this.map[a], --this.count
                };
            this.item = this.item = function (a) {
                return this.map[a]
            };
            this.keys = this.keys = function () {
                var a = new ktools.n, d;
                for (d in this.map) a.add(d);
                return a.toArray()
            };
            this.values = this.Ta = function () {
                var a = new ktools.n, d;
                for (d in this.map) a.add(this.map[d]);
                return a.toArray()
            }
        };
        ktools.Map = ktools.k;
        ktools.c = function () {
            if (ktools.c.caller != ktools.c.d) throw Error("This object cannot be instanciated, use getInstance.");
            var a = new ktools.k,
                d = new ktools.k;
            this.loadScript = this.h = function (b, d, c) {
                if ("undefined" == typeof a.item(b)) {
                    a.add(b, d);
                    var f = document.createElement("script");
                    f.type = "text/javascript";
                    f.readyState ? f.onreadystatechange = function () {
                        if ("loaded" == f.readyState || "complete" == f.readyState) f.onreadystatechange = l, c && c()
                    } : f.onload = function () {
                        c && c()
                    };
                    f.src = d;
                    document.getElementsByTagName("head")[0].appendChild(f);
                    debug && console.log("DynamicLoader : loadScript " + b)
                } else debug && console.log("DynamicLoader : " + b + " already loading/loaded"),
                c && c()
            };
            this.loadCss = this.q = function (a, i) {
                debug && console.log("preparing css " + a);
                if ("undefined" == typeof d.item(a)) {
                    d.add(a, i);
                    var c = document.createElement("link");
                    c.type = "text/css";
                    c.rel = "stylesheet";
                    c.href = i;
                    c.media = "screen";
                    c.title = "dynamicLoadedSheet";
                    document.getElementsByTagName("head")[0].appendChild(c);
                    debug && console.log("css " + a + " added")
                } else debug && console.log("css " + a + " already added")
            }
        };
        ktools.DynamicLoader = ktools.c;
        ktools.c.a = l;
        ktools.DynamicLoader.instance = ktools.c.a;
        ktools.c.d = function () {
            this.a ==
            l && (this.a = new ktools.c);
            return this.a
        };
        ktools.DynamicLoader.getInstance = ktools.c.d;
        ktools.T = function (a, d, b, i) {
            var c = this, f = i, g = m, j = new ktools.k;
            this.getName = this.getName = function () {
                return a
            };
            this.getUrl = this.l = function () {
                return d
            };
            this.getDependances = this.aa = function () {
                return b
            };
            this.isLoaded = this.F = function () {
                return g
            };
            this.setLoaded = this.N = function (a) {
                g = a
            };
            this.isPrimaryNode = this.K = function () {
                "undefined" == typeof f && (f = m);
                return f
            };
            this.getParentsToCall = this.H = function () {
                return j
            };
            this.addParentToCall =
                this.G = function (a) {
                    "undefined" != typeof a && a != l && j.add(a.getName(), a)
                };
            this.loadScript = this.h = function (a) {
                c.G(a);
                if (c.F()) return c.s();
                if (0 == b.length) ktools.c.d().h(c.getName(), c.l(), c.s); else for (a = 0; a < b.length; a++) b[a].h(c)
            };
            this.callback = this.s = function () {
                debug && console.log("script info : " + c.getName() + " loaded !");
                c.N(k);
                for (var a = c.H(), b = l, d = a.keys(), g = 0; g < a.count; g++) b = a.item(d[g]), ktools.c.d().h(b.getName(), b.l(), b.s)
            }
        };
        ktools.Script = ktools.T;
        ktools.Q = function (a, d) {
            var b = this;
            this.getName = this.getName =
                function () {
                    return a
                };
            this.getUrl = this.l = function () {
                return d
            };
            this.loadCss = this.q = function () {
                ktools.c.d().q(b.getName(), b.l())
            }
        };
        ktools.CssStyle = ktools.Q;
        ktools.S = function (a) {
            var d = new ktools.n, b = new ktools.n, i = l, c = m, f = l;
            this.getPluginName = this.ia = function () {
                return a
            };
            this.addScript = this.W = function (a) {
                d.add(a)
            };
            this.getScripts = this.ka = function () {
                return d
            };
            this.addCss = this.U = function (a) {
                b.add(a)
            };
            this.getCss = this.$ = function () {
                return b
            };
            this.setEditorScript = this.Ma = function (a) {
                i = a
            };
            this.getEditorScript =
                this.ba = function () {
                    return i
                };
            this.register = this.Ja = function (a) {
                f = a;
                return this
            };
            this.getRegistered = this.ja = function () {
                return f
            };
            this.isInitialized = this.va = function () {
                for (var a = 0; a < d.size(); a++) c |= d.get(a).F();
                return c
            };
            this.initialize = this.J = function () {
                for (var a = l, c = 0; c < b.size(); c++) a = b.get(c), a.q();
                a = l;
                for (c = 0; c < d.size(); c++) a = d.get(c), a.K() && a.h()
            }
        };
        ktools.KolorPlugin = ktools.S;
        ktools.j = function () {
            this.pluginList = this.v = new ktools.k;
            this.addPlugin = this.V = function (a, d, b) {
                this.v.add(a, d);
                ("undefined" ==
                    typeof b || b) && d.J()
            };
            this.removePlugin = this.Ka = function (a) {
                this.v.remove(a)
            };
            this.getPlugin = this.ha = function (a) {
                return this.v.item(a)
            }
        };
        ktools.KolorPluginList = ktools.j;
        ktools.j.a = l;
        ktools.KolorPluginList.instance = ktools.j.a;
        ktools.j.d = function () {
            this.a == l && (this.a = new ktools.j);
            return this.a
        };
        ktools.KolorPluginList.getInstance = ktools.j.d
    })();
})();