(function (root, factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD.
        define(['jquery', 'lodash', 'handlebars'], factory);
    } 
 	else if (typeof module === 'object' && module.exports) {
        // Node. Does not work with strict CommonJS, but
        // only CommonJS-like environments that support module.exports,
        // like Node.
        module.exports = factory(require('jquery'), require('lodash'), require('handlebars'));
    }    
    else {
        // Browser globals
        root.coreleo = factory(root.$, root._, root.Handlebars);
    }
}(this, function ($, _, Handlebars) {
/**
 * @license almond 0.3.1 Copyright (c) 2011-2014, The Dojo Foundation All Rights Reserved.
 * Available via the MIT or new BSD license.
 * see: http://github.com/jrburke/almond for details
 */
//Going sloppy to avoid 'use strict' string cost, but strict practices should
//be followed.
/*jslint sloppy: true */
/*global setTimeout: false */

var requirejs, require, define;
(function (undef) {
    var main, req, makeMap, handlers,
        defined = {},
        waiting = {},
        config = {},
        defining = {},
        hasOwn = Object.prototype.hasOwnProperty,
        aps = [].slice,
        jsSuffixRegExp = /\.js$/;

    function hasProp(obj, prop) {
        return hasOwn.call(obj, prop);
    }

    /**
     * Given a relative module name, like ./something, normalize it to
     * a real name that can be mapped to a path.
     * @param {String} name the relative name
     * @param {String} baseName a real name that the name arg is relative
     * to.
     * @returns {String} normalized name
     */
    function normalize(name, baseName) {
        var nameParts, nameSegment, mapValue, foundMap, lastIndex,
            foundI, foundStarMap, starI, i, j, part,
            baseParts = baseName && baseName.split("/"),
            map = config.map,
            starMap = (map && map['*']) || {};

        //Adjust any relative paths.
        if (name && name.charAt(0) === ".") {
            //If have a base name, try to normalize against it,
            //otherwise, assume it is a top-level require that will
            //be relative to baseUrl in the end.
            if (baseName) {
                name = name.split('/');
                lastIndex = name.length - 1;

                // Node .js allowance:
                if (config.nodeIdCompat && jsSuffixRegExp.test(name[lastIndex])) {
                    name[lastIndex] = name[lastIndex].replace(jsSuffixRegExp, '');
                }

                //Lop off the last part of baseParts, so that . matches the
                //"directory" and not name of the baseName's module. For instance,
                //baseName of "one/two/three", maps to "one/two/three.js", but we
                //want the directory, "one/two" for this normalization.
                name = baseParts.slice(0, baseParts.length - 1).concat(name);

                //start trimDots
                for (i = 0; i < name.length; i += 1) {
                    part = name[i];
                    if (part === ".") {
                        name.splice(i, 1);
                        i -= 1;
                    } else if (part === "..") {
                        if (i === 1 && (name[2] === '..' || name[0] === '..')) {
                            //End of the line. Keep at least one non-dot
                            //path segment at the front so it can be mapped
                            //correctly to disk. Otherwise, there is likely
                            //no path mapping for a path starting with '..'.
                            //This can still fail, but catches the most reasonable
                            //uses of ..
                            break;
                        } else if (i > 0) {
                            name.splice(i - 1, 2);
                            i -= 2;
                        }
                    }
                }
                //end trimDots

                name = name.join("/");
            } else if (name.indexOf('./') === 0) {
                // No baseName, so this is ID is resolved relative
                // to baseUrl, pull off the leading dot.
                name = name.substring(2);
            }
        }

        //Apply map config if available.
        if ((baseParts || starMap) && map) {
            nameParts = name.split('/');

            for (i = nameParts.length; i > 0; i -= 1) {
                nameSegment = nameParts.slice(0, i).join("/");

                if (baseParts) {
                    //Find the longest baseName segment match in the config.
                    //So, do joins on the biggest to smallest lengths of baseParts.
                    for (j = baseParts.length; j > 0; j -= 1) {
                        mapValue = map[baseParts.slice(0, j).join('/')];

                        //baseName segment has  config, find if it has one for
                        //this name.
                        if (mapValue) {
                            mapValue = mapValue[nameSegment];
                            if (mapValue) {
                                //Match, update name to the new value.
                                foundMap = mapValue;
                                foundI = i;
                                break;
                            }
                        }
                    }
                }

                if (foundMap) {
                    break;
                }

                //Check for a star map match, but just hold on to it,
                //if there is a shorter segment match later in a matching
                //config, then favor over this star map.
                if (!foundStarMap && starMap && starMap[nameSegment]) {
                    foundStarMap = starMap[nameSegment];
                    starI = i;
                }
            }

            if (!foundMap && foundStarMap) {
                foundMap = foundStarMap;
                foundI = starI;
            }

            if (foundMap) {
                nameParts.splice(0, foundI, foundMap);
                name = nameParts.join('/');
            }
        }

        return name;
    }

    function makeRequire(relName, forceSync) {
        return function () {
            //A version of a require function that passes a moduleName
            //value for items that may need to
            //look up paths relative to the moduleName
            var args = aps.call(arguments, 0);

            //If first arg is not require('string'), and there is only
            //one arg, it is the array form without a callback. Insert
            //a null so that the following concat is correct.
            if (typeof args[0] !== 'string' && args.length === 1) {
                args.push(null);
            }
            return req.apply(undef, args.concat([relName, forceSync]));
        };
    }

    function makeNormalize(relName) {
        return function (name) {
            return normalize(name, relName);
        };
    }

    function makeLoad(depName) {
        return function (value) {
            defined[depName] = value;
        };
    }

    function callDep(name) {
        if (hasProp(waiting, name)) {
            var args = waiting[name];
            delete waiting[name];
            defining[name] = true;
            main.apply(undef, args);
        }

        if (!hasProp(defined, name) && !hasProp(defining, name)) {
            throw new Error('No ' + name);
        }
        return defined[name];
    }

    //Turns a plugin!resource to [plugin, resource]
    //with the plugin being undefined if the name
    //did not have a plugin prefix.
    function splitPrefix(name) {
        var prefix,
            index = name ? name.indexOf('!') : -1;
        if (index > -1) {
            prefix = name.substring(0, index);
            name = name.substring(index + 1, name.length);
        }
        return [prefix, name];
    }

    /**
     * Makes a name map, normalizing the name, and using a plugin
     * for normalization if necessary. Grabs a ref to plugin
     * too, as an optimization.
     */
    makeMap = function (name, relName) {
        var plugin,
            parts = splitPrefix(name),
            prefix = parts[0];

        name = parts[1];

        if (prefix) {
            prefix = normalize(prefix, relName);
            plugin = callDep(prefix);
        }

        //Normalize according
        if (prefix) {
            if (plugin && plugin.normalize) {
                name = plugin.normalize(name, makeNormalize(relName));
            } else {
                name = normalize(name, relName);
            }
        } else {
            name = normalize(name, relName);
            parts = splitPrefix(name);
            prefix = parts[0];
            name = parts[1];
            if (prefix) {
                plugin = callDep(prefix);
            }
        }

        //Using ridiculous property names for space reasons
        return {
            f: prefix ? prefix + '!' + name : name, //fullName
            n: name,
            pr: prefix,
            p: plugin
        };
    };

    function makeConfig(name) {
        return function () {
            return (config && config.config && config.config[name]) || {};
        };
    }

    handlers = {
        require: function (name) {
            return makeRequire(name);
        },
        exports: function (name) {
            var e = defined[name];
            if (typeof e !== 'undefined') {
                return e;
            } else {
                return (defined[name] = {});
            }
        },
        module: function (name) {
            return {
                id: name,
                uri: '',
                exports: defined[name],
                config: makeConfig(name)
            };
        }
    };

    main = function (name, deps, callback, relName) {
        var cjsModule, depName, ret, map, i,
            args = [],
            callbackType = typeof callback,
            usingExports;

        //Use name if no relName
        relName = relName || name;

        //Call the callback to define the module, if necessary.
        if (callbackType === 'undefined' || callbackType === 'function') {
            //Pull out the defined dependencies and pass the ordered
            //values to the callback.
            //Default to [require, exports, module] if no deps
            deps = !deps.length && callback.length ? ['require', 'exports', 'module'] : deps;
            for (i = 0; i < deps.length; i += 1) {
                map = makeMap(deps[i], relName);
                depName = map.f;

                //Fast path CommonJS standard dependencies.
                if (depName === "require") {
                    args[i] = handlers.require(name);
                } else if (depName === "exports") {
                    //CommonJS module spec 1.1
                    args[i] = handlers.exports(name);
                    usingExports = true;
                } else if (depName === "module") {
                    //CommonJS module spec 1.1
                    cjsModule = args[i] = handlers.module(name);
                } else if (hasProp(defined, depName) ||
                           hasProp(waiting, depName) ||
                           hasProp(defining, depName)) {
                    args[i] = callDep(depName);
                } else if (map.p) {
                    map.p.load(map.n, makeRequire(relName, true), makeLoad(depName), {});
                    args[i] = defined[depName];
                } else {
                    throw new Error(name + ' missing ' + depName);
                }
            }

            ret = callback ? callback.apply(defined[name], args) : undefined;

            if (name) {
                //If setting exports via "module" is in play,
                //favor that over return value and exports. After that,
                //favor a non-undefined return value over exports use.
                if (cjsModule && cjsModule.exports !== undef &&
                        cjsModule.exports !== defined[name]) {
                    defined[name] = cjsModule.exports;
                } else if (ret !== undef || !usingExports) {
                    //Use the return value from the function.
                    defined[name] = ret;
                }
            }
        } else if (name) {
            //May just be an object definition for the module. Only
            //worry about defining if have a module name.
            defined[name] = callback;
        }
    };

    requirejs = require = req = function (deps, callback, relName, forceSync, alt) {
        if (typeof deps === "string") {
            if (handlers[deps]) {
                //callback in this case is really relName
                return handlers[deps](callback);
            }
            //Just return the module wanted. In this scenario, the
            //deps arg is the module name, and second arg (if passed)
            //is just the relName.
            //Normalize module name, if it contains . or ..
            return callDep(makeMap(deps, callback).f);
        } else if (!deps.splice) {
            //deps is a config object, not an array.
            config = deps;
            if (config.deps) {
                req(config.deps, config.callback);
            }
            if (!callback) {
                return;
            }

            if (callback.splice) {
                //callback is an array, which means it is a dependency list.
                //Adjust args if there are dependencies
                deps = callback;
                callback = relName;
                relName = null;
            } else {
                deps = undef;
            }
        }

        //Support require(['a'])
        callback = callback || function () {};

        //If relName is a function, it is an errback handler,
        //so remove it.
        if (typeof relName === 'function') {
            relName = forceSync;
            forceSync = alt;
        }

        //Simulate async callback;
        if (forceSync) {
            main(undef, deps, callback, relName);
        } else {
            //Using a non-zero value because of concern for what old browsers
            //do, and latest browsers "upgrade" to 4 if lower value is used:
            //http://www.whatwg.org/specs/web-apps/current-work/multipage/timers.html#dom-windowtimers-settimeout:
            //If want a value immediately, use require('id') instead -- something
            //that works in almond on the global level, but not guaranteed and
            //unlikely to work in other AMD implementations.
            setTimeout(function () {
                main(undef, deps, callback, relName);
            }, 4);
        }

        return req;
    };

    /**
     * Just drops the config on the floor, but returns req in case
     * the config return value is used.
     */
    req.config = function (cfg) {
        return req(cfg);
    };

    /**
     * Expose module registry for debugging and tooling
     */
    requirejs._defined = defined;

    define = function (name, deps, callback) {
        if (typeof name !== 'string') {
            throw new Error('See almond README: incorrect module build, no module name');
        }

        //This module may not have dependencies
        if (!deps.splice) {
            //deps is not an array, so probably means
            //an object literal or factory function for
            //the value. Adjust args.
            callback = deps;
            deps = [];
        }

        if (!hasProp(defined, name) && !hasProp(waiting, name)) {
            waiting[name] = [name, deps, callback];
        }
    };

    define.amd = {
        jQuery: true
    };
}());

define("../node_modules/almond/almond", function(){});

define('$',['require','jquery'],function(require) {
    'use strict';
    return require('jquery');
});

define('log',['require'],function(require) {
    'use strict';

    if (!(window.console && console.log)) {
        return {
            log: function() {},
            debug: function() {},
            info: function() {},
            warn: function() {},
            error: function() {}
        };
    }
    else {
        return window.console;
    }

});

define('constants',['require'],function(require) {
    'use strict';

    return {
        ONE_SECOND: 1000,
        ONE_MINUTE: 60000
    };

});

define('template/template',['require','$','handlebars'],function(require) {
    'use strict';

    var $ = require('$');
    var handlebars = require('handlebars');

    return {
        cache: {
            get: function(selector) {
                if (!this.templates) {
                    this.templates = {};
                }

                var template = this.templates[selector];
                if (!template) {
                    // precompile the template
                    template = handlebars.compile($(selector).html());
                    this.templates[selector] = template;
                }
                return template;
            }
        },

        /**
         * @param {string} selector - the selector/id to the template html
         * @param {object} data - the data values to use for the template
         * 
         * This function will find the template using the given selector,
         * compile it with handlebars and cache it for future use.
         * 
         * @return the rendered template and data string
         * 
         */
        renderTemplateWithCaching: function(selector, data) {
            var render = this.cache.get(selector);
            return render(data);
        },

        /**
         * @param {string} templateString - the template to render
         * @param {object} data - the data values to use for the template
         */
        renderTemplate: function(templateString, data) {
            var render = handlebars.compile(templateString);
            return render(data);
        }

    };
});

define('util',['require','$','lodash','log','template/template'],function(require) {
    'use strict';

    var $ = require('$');
    var _ = require('lodash');
    var log = require('log');
    var template = require('template/template');

    var util = {

        renderTemplateWithCaching: function(selector, data) {
            return template.renderTemplateWithCaching(selector, data);
        },


        renderTemplate: function(templateString, data) {
            return template.renderTemplate(templateString, data);
        },

        deprecated: function() {
            log.warn('This function is deprecated.  See documentation.');
        },

        isEmpty: function(obj) {
            return _.isEmpty(obj);
        },

        isNotEmpty: function(text) {
            return !this.isEmpty(text);
        },

        startsWith: function(str, ch, position) {
            if (!position) {
                position = 0;
            }
            return _.startsWith(str, ch, position);
        },

        replaceCharAt: function(str, index, chr) {
            if (index > str.length - 1) {
                return str;
            }
            return str.substr(0, index) + chr + str.substr(index + 1);
        },

        formatPhone: function(phone) {
            if (_.isEmpty(phone)) {
                return '';
            }
            return '(' + phone.substr(0, 3) + ') ' + phone.substr(3, 3) + '-' + phone.substr(6, 4);
        },


        formatCurrency: function(amount) {
            var i = parseFloat(amount);
            if (isNaN(i)) {
                i = 0.00;
            }
            var minus = '';
            if (i < 0) {
                minus = '-';
            }
            i = Math.abs(i);
            i = parseInt((i + 0.005) * 100, 10);
            i = i / 100;
            var s = i.toString();
            if (s.indexOf('.') < 0) {
                s += '.00';
            }
            if (s.indexOf('.') === (s.length - 2)) {
                s += '0';
            }
            s = minus + s;
            return s;
        },


        /**
         * If the string is null it returns a empty string otherwise returns the string
         */
        blankNull: function(string) {
            if (this.isEmpty(string)) {
                return '';
            }
            else {
                return string;
            }
        },


        toBoolean: function(str) {
            if (this.isEmpty(str)) {
                return false;
            }

            str = str.trim().toLowerCase();
            if (str === 'true' || str === 't' || str === '1' || str === 'y' || str === 'yes' || str === 1 || str === true) {
                return true;
            }

            return false;
        },


        isTrue: function(str) {
            return this.toBoolean(str) === true;
        },

        isFalse: function(str) {
            return !this.isTrue(str);
        },

        /**
         * Left pads the given string with zeros to fill the size specified
         */
        zeroFill: function(string, size) {
            if (this.isEmpty(string)) {
                return '';
            }
            return _.padStart(string, (size - string.length), '0');
        },

        isIdSelector: function(id) {
            if (this.isEmpty(id)) {
                return false;
            }

            return this.startsWith(id, '#');
        },

        isClassSelector: function(cssClass) {
            if (this.isEmpty(cssClass)) {
                return false;
            }
            return this.startsWith(cssClass, '.');
        },

        idAsSelector: function(id) {
            if (this.isEmpty(id)) {
                return '';
            }

            id = id.trim();
            if (this.isIdSelector(id) || this.isClassSelector(id)) {
                return id;
            }
            return '#' + id;
        },

        classAsSelector: function(cssClass) {
            if (this.isEmpty(cssClass)) {
                return '';
            }

            cssClass = cssClass.trim();
            if (this.isIdSelector(cssClass) || this.isClassSelector(cssClass)) {
                return cssClass;
            }
            return '.' + cssClass;
        },

        contains: function(str, subString) {
            if (this.isEmpty(str)) {
                return false;
            }

            if (this.isEmpty(subString)) {
                return false;
            }

            return str.indexOf(subString) !== -1;
        },

        containsIgnoreCase: function(str, subString) {
            if (this.isEmpty(str)) {
                return false;
            }

            if (this.isEmpty(subString)) {
                return false;
            }

            return this.contains(str.toLowerCase(), str.toLowerCase());
        },

        trimNewLineChar: function(str) {
            if (this.isEmpty(str)) {
                return '';
            }
            return str.replace(/(\r\n|\n|\r)/gm, '');
        },


        trimWhiteSpaceChar: function(str) {
            if (this.isEmpty(str)) {
                return '';
            }
            return str.replace(/(\s)/gm, '');
        },


        redirectAsHttpPost: function(location, args, target) {
            if (!target) {
                target = '_self';
            }

            var form = '';
            if ($.isArray(args)) {
                $.each(args, function(index, obj) {
                    form += '<input type="hidden" name="' + obj.name + '" value="' + obj.value + '">';
                });
            }
            else {
                $.each(args, function(key, value) {
                    form += '<input type="hidden" name="' + key + '" value="' + value + '">';
                });
            }

            var dynamicForm = '<form data-ajax="false" target="' + target + '" action="' + location + '" method="POST">' + form + '</form>';
            $(dynamicForm).appendTo($(document.body)).submit();
        },


        hasWhiteSpace: function(s) {
            return (/\s/g.test(s));
        },


        toProperCase: function(str) {
            if (this.isEmpty(str)) {
                return '';
            }
            str = str.toLowerCase();
            return str.replace(/\b[a-z]/g, function(f) {
                return f.toUpperCase();
            });
        },


        /**
         * Adds a param and value to an existing url.
         */
        addParameterToUrl: function(url, name, value) {
            if (this.isEmpty(name)) {
                return url;
            }

            if (this.isEmpty(value)) {
                return url;
            }

            var seperator = url.indexOf('?') === -1 ? '?' : '&';
            return url + seperator + encodeURIComponent(name) + '=' + encodeURIComponent(value);
        },


        getParameterFromUrl: function(urlString, name) {
            return (urlString.split('' + name + '=')[1] || '').split('&')[0];
        },


        toKeyValueHash: function(key, value) {
            return {
                'key': key,
                'value': value
            };
        },


        /** Deprecated API */


        /**
         * @deprecated
         * @alias for addParameterToUrl
         */
        addParamToUrl: function(url, name, value) {
            this.deprecated();
            return this.addParameterToUrl(url, name, value);
        },

        /**
         * @deprecated
         * @alias for idAsSelector
         */
        formatId: function(id) {
            this.deprecated();
            return this.idAsSelector(id);
        },


        /**
         * @deprecated
         * @alias for classAsSelector
         */
        formatClass: function(cssClass) {
            this.deprecated();
            return this.classAsSelector(cssClass);
        },

        /**
         * @deprecated
         * @alias for formatCurrency
         */
        currencyFormatted: function(amount) {
            this.deprecated();
            return this.formatCurrency(amount);
        },


        /**
         * @deprecated
         * @alias for redirectAsPost
         */
        redirectPost: function(location, args, target) {
            this.deprecated();
            this.redirectAsHttpPost(location, args, target);
        },

        /**
         * @deprecated
         * @alias for redirectAsPost
         */
        properCase: function(str) {
            this.deprecated();
            return this.toProperCase(str);
        }

    };

    return _.assign({}, _, util);
});

define('ui/mobile',['require','$','util'],function(require) {
    'use strict';

    var $ = require('$');
    var util = require('util');

    return {
        refreshSelect: function(id) {
            var item = $(util.idAsSelector(id));
            if ($.mobile && item.selectmenu) {
                item.selectmenu('refresh');
            }
        },

        initTable: function(id) {
            var item = $(util.idAsSelector(id));
            if ($.mobile && item.table) {
                item.table();
            }
        },

        refreshTable: function(id) {
            var item = $(util.idAsSelector(id));
            if ($.mobile && item.table) {
                item.table('rebuild');
            }
        },


        enableTextField: function(id) {
            var item = $(util.idAsSelector(id));
            if ($.mobile && item.textinput) {
                item.textinput('enable');
            }
        },

        disableTextField: function(id) {
            var item = $(util.idAsSelector(id));
            if ($.mobile && item.textinput) {
                item.textinput('disable');
            }
        },

        isMobileClient: function() {
            return $.mobile ? true : false;
        }
    };
});

define('ui/form',['require','$','util'],function(require) {
    'use strict';

    var $ = require('$');
    var util = require('util');

    return {

        /**
         * @param {string} id - the id or selector of the element to disable
         */
        enable: function(id) {
            var item = $(util.idAsSelector(id));
            item.prop('disabled', false);
        },

        /**
         * 
         * Disable the element and optionally re-enables it after a specific number of milliseconds.
         * 
         * @param {string} id - the id or selector of the element to disable
         * @param {int} [milliseconds] - an optional time in milliseconds before re-enabling it 
         * before 
         * 
         */
        disable: function(id, milliseconds) {
            var item = $(util.idAsSelector(id));
            item.prop('disabled', true);

            if (milliseconds) {
                var self = this;
                setTimeout(function() {
                    self.enable(id);
                }, milliseconds);
            }
        }
    };


});

define('ui',['require','$','ui/mobile','ui/form'],function(require) {
    'use strict';

    var $ = require('$');
    var mobile = require('ui/mobile');
    var form = require('ui/form');

    return {
        isMobileClient: function() {
            return mobile.isMobileClient();
        },


        timeoutButton: function(id, time) {
            if (!time) {
                time = constants.ONE_SECOND * 2;
            }
            form.disable(id, time);
        },

        enableTextField: function(id) {
            form.enable(id);
            mobile.enableTextField(id);
        },


        disableTextField: function(id) {
            form.disable(id);
            mobile.disableTextField(id);
        }
    };


});

/*global define */

/**
 * The main module (sometimes called main.js) which defines the public 
 * interface for the coreleo library
 */
define('main',['require','$','log','constants','util','ui'],function(require) {
    'use strict';

    //Return the module value.
    return {
        version: '0.0.1',
        $: require('$'),
        log: require('log'),
        constants: require('constants'),
        util: require('util'),
        ui: require('ui')

    };
});

    //Register in the values from the outer closure for common dependencies
    //as local almond modules
    define('jquery', function () {
        return $;
    });
    define('lodash', function () {
        return _;
    });
    define('handlebars', function () {
        return Handlebars;
    });    
    

    //Use almond's special top-level, synchronous require to trigger factory
    //functions, get the final module value, and export it as the public
    //value.
    return require('main');
}));
