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

/** 
 * The JQuery object
 * @module $ 
 */
define('$',['require','jquery'],function(require) {
    'use strict';
    return require('jquery');
});

/** 
 * A logger utility.
 * @module log 
 */

/* eslint no-console:0 */
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

/** 
 * Generic utilities for dealing with strings, objects, etc.
 * @module util 
 */
define('util',['require','$','lodash','log'],function(require) {
    'use strict';

    var $ = require('$');
    var _ = require('lodash');
    var log = require('log');


    // Workaround for "this" being undefined when used in the util object literal
    var getThis = function() {
        return module;
    };

    var module = {

        deprecated: function() {
            log.warn('This function has been deprecated and will not be supported in future releases.  See documentation.');
        },

        isEmpty: function(obj) {
            return _.isEmpty(obj);
        },

        isNotEmpty: function(text) {
            return !getThis().isEmpty(text);
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
            if (getThis().isEmpty(phone)) {
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
         * 
         * @param {String} string the string to check
         * @return {String} A empty string if the parameter was null or undefined otherwise the parameter
         */
        blankNull: function(string) {
            if (getThis().isEmpty(string)) {
                return '';
            }
            else {
                return string;
            }
        },


        toBoolean: function(str) {
            if (getThis().isEmpty(str)) {
                return false;
            }

            str = str.trim().toLowerCase();
            if (str === 'true' || str === 't' || str === '1' || str === 'y' || str === 'yes' || str === 1 || str === true) {
                return true;
            }

            return false;
        },


        isTrue: function(str) {
            return getThis().toBoolean(str);
        },

        isFalse: function(str) {
            return !getThis().isTrue(str);
        },

        /**
         * Left pads the given string with zeros to fill the size specified
         * 
         * @param {String} string the String to pad
         * @param {Integer} size the number of zeros to pad
         * 
         * @return {String} the string with padded zeros
         * 
         */
        zeroFill: function(string, size) {
            if (getThis().isEmpty(string)) {
                return '';
            }
            return _.padStart(string, (size - string.length), '0');
        },

        isIdSelector: function(id) {
            if (getThis().isEmpty(id)) {
                return false;
            }

            return getThis().startsWith(id, '#');
        },

        isClassSelector: function(cssClass) {
            if (getThis().isEmpty(cssClass)) {
                return false;
            }
            return getThis().startsWith(cssClass, '.');
        },

        idAsSelector: function(id) {
            if (getThis().isEmpty(id)) {
                return '';
            }

            id = id.trim();
            if (getThis().isIdSelector(id) || getThis().isClassSelector(id)) {
                return id;
            }
            return '#' + id;
        },

        classAsSelector: function(cssClass) {
            if (getThis().isEmpty(cssClass)) {
                return '';
            }

            cssClass = cssClass.trim();
            if (getThis().isIdSelector(cssClass) || getThis().isClassSelector(cssClass)) {
                return cssClass;
            }
            return '.' + cssClass;
        },

        contains: function(str, subString) {
            if (getThis().isEmpty(str)) {
                return false;
            }

            if (getThis().isEmpty(subString)) {
                return false;
            }

            return str.indexOf(subString) !== -1;
        },

        containsIgnoreCase: function(str, subString) {
            if (getThis().isEmpty(str)) {
                return false;
            }

            if (getThis().isEmpty(subString)) {
                return false;
            }

            return getThis().contains(str.toLowerCase(), str.toLowerCase());
        },

        trimNewLineChar: function(str) {
            if (getThis().isEmpty(str)) {
                return '';
            }
            return str.replace(/(\r\n|\n|\r)/gm, '');
        },


        trimWhiteSpaceChar: function(str) {
            if (getThis().isEmpty(str)) {
                return '';
            }
            return str.replace(/(\s)/gm, '');
        },


        redirectAsHttpPost: function(location, args, target) {
            if (!target) {
                target = '_getThis()';
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


        /**
         * Converts the provided string to proper case
         * @param {String} str the string to convert
         * @return {String} the string in proper case
         */
        properCase: function(str) {
            if (getThis().isEmpty(str)) {
                return '';
            }
            str = str.toLowerCase();
            return str.replace(/\b[a-z]/g, function(f) {
                return f.toUpperCase();
            });
        },


        /**
         * Adds a parameter and value to an existing URL.
         * 
         * @param {String} url the URL to append to
         * @param {String} name the name of the parameter
         * @param {String} value the value of the parameter
         * @return {String} the url with the given parameter appended
         * 
         */
        addParameterToUrl: function(url, name, value) {
            if (getThis().isEmpty(name) || getThis().isEmpty(value)) {
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
        }


    };

    return _.assign({}, _, module);
});

/** 
 * A class of Constants
 * @module constants 
 */
define('constants',['require'],function(require) {
    'use strict';

    return {
        ONE_SECOND: 1000,
        ONE_MINUTE: 60000
    };

});

/**
 * This object contains functions dealing with JQuery mobile and is never exposed as a public API.
 * Instead these functions are called in the various public UI API to handle JQuery mobile elements in
 * the appropriate fashion behind the scenes. 
 * 
 */
define('ui/mobile',['require','$','util'],function(require) {
    'use strict';

    var $ = require('$');
    var util = require('util');


    // Workaround for "this" being undefined when used in the util object literal
    var getThis = function() {
        return module;
    };


    var module = {

        /*
         * Refreshes a JQuery mobile select item when options are changed.
         * 
         * @param {String} id the id of the select item
         * 
         */
        refreshSelect: function(id) {
            var item = $(util.idAsSelector(id));
            if (getThis().isMobile() && item.selectmenu) {
                item.selectmenu('refresh');
            }
        },

        /*
         * Initializes a table to be a JQuery table for mobile displays
         * 
         * @param {String} id the id of the select item
         * 
         */
        initTable: function(id) {
            var item = $(util.idAsSelector(id));
            if (getThis().isMobile() && item.table) {
                item.table();
            }
        },

        refreshTable: function(id) {
            var item = $(util.idAsSelector(id));
            if (getThis().isMobile() && item.table) {
                item.table('rebuild');
            }
        },


        enableTextField: function(id) {
            var item = $(util.idAsSelector(id));
            if (getThis().isMobile() && item.textinput) {
                item.textinput('enable');
            }
        },

        disableTextField: function(id) {
            var item = $(util.idAsSelector(id));
            if (getThis().isMobile() && item.textinput) {
                item.textinput('disable');
            }
        },

        isMobile: function() {
            return $.mobile ? true : false;
        }
    };
    return module;
});

/** 
 * Utilities for handling form and form inputs.
 * @module form 
 */
define('ui/form',['require','$','util'],function(require) {
    'use strict';

    var $ = require('$');
    var util = require('util');

    var module = {
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
         * before re-enabling it.
         * 
         */
        disable: function(id, milliseconds) {
            var item = $(util.idAsSelector(id));
            item.prop('disabled', true);

            if (util.isNotEmpty(milliseconds)) {
                setTimeout(function() {
                    module.enable(id);
                }, milliseconds);
            }
        }
    };

    return module;


});

/** 
 * Utilities for rendering handlebar templates.
 * @module template 
 */
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
                    // pre compile the template
                    template = handlebars.compile($(selector).html());
                    this.templates[selector] = template;
                }
                return template;
            }
        },

        /**
         * This function will find the template using the given selector,
         * compile it with handlebars and cache it for future use.
         * 
         * @param {String} selector - the selector/id to the template html
         * @param {Object} data - the data values to use for the template
         * @return {String} the rendered template and data string
         * 
         */
        renderTemplateWithCaching: function(selector, data) {
            var render = this.cache.get(selector);
            return render(data);
        },

        /**
         * Combines the template and data to create a rendered String
         * @param {String} templateString - the template to render
         * @param {Object} data - the data values to use for the template
         * @return {String} the rendered template and data
         */
        renderTemplate: function(templateString, data) {
            var render = handlebars.compile(templateString);
            return render(data);
        }

    };
});

/** 
 * Utilities for handling generic UI elements.
 * @module ui 
 */
define('ui',['require','$','util','constants','ui/mobile','ui/form','template/template'],function(require) {
    'use strict';


    var $ = require('$');
    var util = require('util');
    var constants = require('constants');
    var mobile = require('ui/mobile');
    var form = require('ui/form');
    var template = require('template/template');


    var SPINNER_IMAGE_DATA_URL = 'data:image/gif;base64,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';


    return {
        renderTemplateWithCaching: function(selector, data) {
            return template.renderTemplateWithCaching(selector, data);
        },


        renderTemplate: function(templateString, data) {
            return template.renderTemplate(templateString, data);
        },

        isMobile: function() {
            return mobile.isMobile();
        },

        timeoutButton: function(id, time) {
            if (!time) {
                time = constants.ONE_SECOND * 2;
            }
            form.disable(id, time);
        },


        /**
         * Displays a loading spinner in the element which matches the provided id.  
         * By default it replaces the content of the element.
         * 
         * @param {String} id the id of the element.
         * @param {boolean} [append=false] set to true to append the spinner to the element instead
         * of replacing it's content.
         *    
         */
        startSpinner: function(id, append) {
            var el = $(util.idAsSelector(id));

            if (util.isTrue(append)) {
                el.append('<image id="coreleo-spinner-image" src="' + SPINNER_IMAGE_DATA_URL + '" />');
            }
            else {
                el.empty();
                el.html('<image id="coreleo-spinner-image" src="' + SPINNER_IMAGE_DATA_URL + '" />');
            }
        },


        stopSpinner: function(id) {
            var div = $(util.idAsSelector(id));
            $('#coreleo-spinner-image', div).remove();
        }

    };


});

/** 
 * Utilities for handling JQuery dialog and mobile pop-ups.
 * @module dialog 
 */
define('ui/dialog',['require','$','ui','util'],function(require) {
    'use strict';

    var $ = require('$');
    var ui = require('ui');
    var util = require('util');

    var CONFIRM_DIALOG_TMPL = '<div data-role="popup" data-shadow="false" data-dismissible="false"';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + 'id="my-confirm-dialog" ';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + 'class="ui-dialog dialog confirm-dialog ui-corner-all" ';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + 'title="{title}">';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + '{header}<div class="icon-content {iconClass}"/><div class="ui-dialog-content text-content">{text}</div>';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + '<div class="dialog-footer">';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + '<button class="save" type="button">Ok</button>';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + '<button class="cancel" type="button">Cancel</button>';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + '</div>';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + '</div>';

    var CONFIRM_DIALOG_HEADER_TMPL = '<div class="header ui-dialog-titlebar ui-widget-header ui-corner-all" data-role="header">{title}</div>';

    var LOADING_DIALOG_TMPL = '<div data-role="popup" data-shadow="false" data-dismissible="false" ';
    LOADING_DIALOG_TMPL = LOADING_DIALOG_TMPL + ' class="ui-dialog dialog loading-dialog ui-corner-all" ';
    LOADING_DIALOG_TMPL = LOADING_DIALOG_TMPL + 'id="my-loading-dialog" title="{title}">';
    LOADING_DIALOG_TMPL = LOADING_DIALOG_TMPL + '<div class="{loadingImageClass}"></div>{text}</div>';

    var ALERT_DIALOG_TMPL = '<div data-role="popup" data-shadow="false" data-dismissible="false" title="{title}"';
    ALERT_DIALOG_TMPL = ALERT_DIALOG_TMPL + 'class="ui-dialog dialog alert-dialog ui-corner-all">{header}';
    ALERT_DIALOG_TMPL = ALERT_DIALOG_TMPL + '<div class="icon-content {iconClass}"/><div class="text-content">{text}</div></div>';

    var CLOSE_BUTTON_TMPL = '<button class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close" ';
    CLOSE_BUTTON_TMPL = CLOSE_BUTTON_TMPL + 'role="button" aria-disabled="false" title="close"><span class="ui-button-icon-primary ui-icon ui-icon-closethick">';
    CLOSE_BUTTON_TMPL = CLOSE_BUTTON_TMPL + '</span><span class="ui-button-text">close</span></button>';

    var ALERT_DIALOG_HEADER_TMPL = '<div class="header ui-dialog-titlebar ui-widget-header ui-corner-all" data-role="header"><span class="ui-dialog-title">{title}</span>{close}</div>';

    var LOADING_IMAGE = 'Loading, please wait...';

    var showConfirmPopup = function(title, text, successFunction, iconClass) {
        var template = CONFIRM_DIALOG_TMPL;
        template = template.replace('{title}', title);
        template = template.replace('{text}', text);
        var header = CONFIRM_DIALOG_HEADER_TMPL.replace('{title}', title);
        template = template.replace('{header}', header);
        template = template.replace('{iconClass}', iconClass);

        var item = $(template);
        $('.save', item).click(function(eventObject) {
            successFunction(item);
            closeDialogOrPopup('#my-confirm-dialog');
            destroyDialogOrPopup('#my-confirm-dialog');
        });

        $('.cancel', item).click(function(eventObject) {
            closeDialogOrPopup('#my-confirm-dialog');
            destroyDialogOrPopup('#my-confirm-dialog');
        });

        item.popup();
        item.popup('open');
    };


    var showConfirmDialog = function(title, text, successFunction, iconClass) {
        var template = CONFIRM_DIALOG_TMPL;
        template = template.replace('{title}', title);
        template = template.replace('{text}', text);
        template = template.replace('{header}', '');
        template = template.replace('{iconClass}', iconClass);

        var item = $(template);
        $('.save', item).click(function(eventObject) {
            successFunction(item);
            closeDialogOrPopup('#my-confirm-dialog');
            destroyDialogOrPopup('#my-confirm-dialog');
        });

        $('.cancel', item).click(function(eventObject) {
            closeDialogOrPopup('#my-confirm-dialog');
            destroyDialogOrPopup('#my-confirm-dialog');
        });

        item.dialog({
            autoOpen: false,
            resizable: true,
            modal: true
        });

        item.dialog('open');
    };


    var destroyDialogOrPopup = function(id) {
        var item = null;
        if (typeof id === 'string') {
            var itemId = util.idAsSelector(id);
            item = $(itemId);
        }
        else {
            item = id;
        }

        if (item.popup) {
            item.popup('destroy').remove();
        }
        else {
            item.dialog('destroy').remove();
        }
    };

    var closeDialogOrPopup = function(id) {
        var item = null;
        if (typeof id === 'string') {
            var itemId = util.idAsSelector(id);
            item = $(itemId);
        }
        else {
            item = id;
        }

        if (item.popup) {
            item.popup('close');
        }
        else {
            item.dialog('close');
        }
    };

    return {
        open: function(id) {
            var itemId = util.idAsSelector(id);
            if ($(itemId).panel) {
                $(itemId).panel('open');
            }
            else {
                $(itemId).dialog('open');
            }
        },

        close: function(id) {
            var itemId = util.idAsSelector(id);
            if ($(itemId).panel) {
                $(itemId).panel('close');
            }
            else {
                $(itemId).dialog('close');
            }
        },

        init: function(id, width, height) {
            if (!ui.isMobile()) {
                $(util.idAsSelector(id)).dialog({
                    autoOpen: false,
                    height: height,
                    width: width,
                    modal: true
                });
            }
        },

        confirm: function(title, text, successFunction, iconClass) {
            if (util.isEmpty(iconClass)) {
                iconClass = '';
            }

            if (ui.isMobile()) {
                showConfirmPopup(title, text, successFunction, iconClass);
            }
            else {
                showConfirmDialog(title, text, successFunction, iconClass);
            }
        },



        showLoadingDialog: function(title, text, loadingImageClass) {
            if (util.isEmpty(text)) {
                text = LOADING_IMAGE;
            }

            if (util.isEmpty(loadingImageClass)) {
                loadingImageClass = 'loading-image';
            }

            var template = LOADING_DIALOG_TMPL;
            template = template.replace('{loadingImageClass}', loadingImageClass);
            template = template.replace('{title}', title);
            template = template.replace('{text}', text);

            var item = $(template);
            if (ui.isMobile()) {
                item.popup();
                item.popup('open');
            }
            else {
                item.dialog({
                    autoOpen: false,
                    closeOnEscape: false,
                    modal: true,
                    dialogClass: 'loading-dialog-contentpane',
                    height: 70,
                    open: function(event) {
                        $('.loading-dialog-contentpane .ui-dialog-titlebar-close').hide();
                        if (!title) {
                            $('.loading-dialog-contentpane .ui-dialog-titlebar').hide();
                        }
                    }
                });
                item.dialog('open');
            }
        },


        hideLoadingDialog: function() {
            closeDialogOrPopup('#my-loading-dialog');
            destroyDialogOrPopup('#my-loading-dialog');
        },


        alert: function(title, text, iconClass) {
            if (util.isEmpty(iconClass)) {
                iconClass = '';
            }

            var template = ALERT_DIALOG_TMPL;
            template = template.replace('{title}', title);
            template = template.replace('{text}', text);
            template = template.replace('{iconClass}', iconClass);

            if (ui.isMobile()) {
                var header = ALERT_DIALOG_HEADER_TMPL.replace('{title}', title);
                header = header.replace('{close}', CLOSE_BUTTON_TMPL);
                template = template.replace('{header}', header);

                var item = $(template);

                $('.header', item).click(function(eventObject) {
                    closeDialogOrPopup(item);
                    destroyDialogOrPopup(item);
                });

                item.popup();
                item.popup('open');
            }
            else {
                template = template.replace('{header}', '');
                template = template.replace('{close}', '');
                $(template).dialog();
            }
        }

    };


});

/** 
 * Utilities for handling JQuery tabs.
 * @module tabs 
 */
define('ui/tabs',['require','$','util'],function(require) {
    'use strict';

    var $ = require('$');
    var util = require('util');

    var tabCount = 1;

    var incrementTabCount = function() {
        tabCount++;
    };

    var decrementTabCount = function() {
        tabCount--;
    };

    // Workaround for "this" being undefined when used in the util object literal
    var getThis = function() {
        return module;
    };

    var module = /** @alias module:tabs */ {
        maxTabs: 12,

        isMaxNumTabsOpen: function() {
            return tabCount >= getThis().maxTabs;
        },

        addTab: function(tabContainerId, tabId, tabTitle, tabContent, showCloseIcon, closeTabText) {
            var tabTemplate = '<li><a id="tab-anchor-{id}" href="#{href}">{tabTitle}</a>{closeIcon}</li>';
            var closeIconTemplate = '<span tabIndex="0" class="ui-icon ui-icon-close">{closeText}</span>';

            if (showCloseIcon) {
                closeIconTemplate = closeIconTemplate.replace('{closeText}', closeTabText + ' ' + tabTitle);
                tabTemplate = tabTemplate.replace('{closeIcon}', closeIconTemplate);
            }
            else {
                tabTemplate = tabTemplate.replace('{closeIcon}', '');
            }

            var tabs = $(util.idAsSelector(tabContainerId));
            var li = $(tabTemplate.replace('{id}', tabId).replace('{href}', tabId).replace('{tabTitle}', tabTitle));
            tabs.find('.ui-tabs-nav').first().append(li);
            tabs.append('<div id="' + tabId + '"><p>' + tabContent + '</p></div>');
            getThis().refresh(tabContainerId);
            incrementTabCount();
        },

        addAjaxTab: function(tabContainerId, tabId, tabTitle, href, showCloseIcon, closeTabText) {
            var tabTemplate = '<li><a id="tab-anchor-{id}" href="{href}">{tabTitle}</a>{closeIcon}</li>';
            var closeIconTemplate = '<span tabIndex="0" class="ui-icon ui-icon-close">{closeText}</span>';

            if (showCloseIcon) {
                closeIconTemplate = closeIconTemplate.replace('{closeText}', closeTabText + ' ' + tabTitle);
                tabTemplate = tabTemplate.replace('{closeIcon}', closeIconTemplate);
            }
            else {
                tabTemplate = tabTemplate.replace('{closeIcon}', '');
            }

            var tabs = $(util.idAsSelector(tabContainerId));
            var li = $(tabTemplate.replace('{id}', tabId).replace('{href}', href).replace('{tabTitle}', tabTitle));
            tabs.find('.ui-tabs-nav').first().append(li);
            getThis().refresh(tabContainerId);
            incrementTabCount();
        },

        renameTab: function(tabContainerId, tabId, title) {
            tabContainerId = util.idAsSelector(tabContainerId);
            var tabAnchor = $(tabContainerId + ' a[id="tab-anchor-' + tabId + '"]');
            tabAnchor.html(title);
        },

        refresh: function(tabContainerId) {
            tabContainerId = util.idAsSelector(tabContainerId);
            var tabs = $(tabContainerId).tabs();
            tabs.tabs('refresh');
        },

        getTabIndexById: function(tabContainerId, tabId) {
            tabContainerId = util.idAsSelector(tabContainerId);
            var tabAnchor = $(tabContainerId + ' a[id="tab-anchor-' + tabId + '"]');
            if (tabAnchor.length === 0) {
                return -1;
            }

            return tabAnchor.parent().index();
        },

        focusTab: function(tabContainerId, tabId) {
            tabContainerId = util.idAsSelector(tabContainerId);
            var tabAnchor = $(tabContainerId + ' a[id="tab-anchor-' + tabId + '"]');
            tabAnchor.focus();
        },

        selectTab: function(tabContainerId, tabId) {
            tabContainerId = util.idAsSelector(tabContainerId);
            var tabIndex = getThis().getTabIndexById(tabContainerId, tabId);
            var tabs = $(tabContainerId).tabs();
            tabs.tabs('option', 'active', tabIndex);
        },

        addCloseFunctionToCloseIcon: function(tabContainerId) {
            tabContainerId = util.idAsSelector(tabContainerId);
            var tabs = $(tabContainerId).tabs();

            // close icon: removing the tab on click
            tabs.delegate('span.ui-icon-close', 'click', function() {
                var panelId = $(this).closest('li').remove().attr('aria-controls');
                $(util.idAsSelector(panelId)).remove();
                getThis().refresh(tabContainerId);
                decrementTabCount();
            });
        },


        closeTab: function(tabContainerId, tabId) {
            var panelId = $(tabContainerId + ' a[id="tab-anchor-' + tabId + '"]').closest('li').remove().attr('aria-controls');
            $('#' + panelId).remove();
            getThis().refresh(tabContainerId);
            decrementTabCount();
        },


        getSelectedTabIndex: function(tabContainerId) {
            tabContainerId = util.idAsSelector(tabContainerId);
            return $(tabContainerId).tabs('option', 'active');
        },

        getSelectedTabId: function(tabContainerId) {
            tabContainerId = util.idAsSelector(tabContainerId);
            var index = getThis().getSelectedTabIndex(tabContainerId);
            var id = ($(tabContainerId + ' ul>li a').eq(index).attr('href'));
            return util.startsWith(id, '#') ? id.substring(1, id.lenght) : id;
        }

    };

    return module;


});

/** 
 * Utilities for handling JQuery mobile and select2 select.
 * @module select 
 */
define('ui/select',['require','ui/mobile'],function(require) {
    'use strict';

    var mobile = require('ui/mobile');

    return {
        /**
         * Refreshes the select drop down after items have been added and removed.
         * For mobile select items it assumes jquery mobile is being used.
         * 
         * @param {String} id the id of the select item
         * 
         */
        refresh: function(id) {
            mobile.refreshSelect();
        }
    };


});

/** 
 * Utilities for handling text inputs.
 * @module text 
 */
define('ui/text',['require','ui/form','ui/mobile'],function(require) {
    'use strict';

    var form = require('ui/form');
    var mobile = require('ui/mobile');

    return {

        /**
         * Enables a text input.
         * @param {String} id the id of the text input 
         */
        enable: function(id) {
            form.enable(id);
            mobile.enableTextField(id);
        },

        /**
         * Disables a text input.
         * @param {String} id the id of the text input 
         */
        disable: function(id) {
            form.disable(id);
            mobile.disableTextField(id);
        }
    };


});

/** 
 * Utilities for handling JQuery UI and mobile tables.
 * @module table 
 */
define('ui/table',['require','$','util','ui/mobile','ui'],function(require) {
    'use strict';

    var $ = require('$');
    var util = require('util');
    var mobile = require('ui/mobile');
    var ui = require('ui');

    return {

        /**
         * 
         * Initializes a table.  This call creates a JQuery table in mobile environment.
         * In non mobile browsers it does nothing and can be used without any side effects.
         * 
         * @param {String} id the id of the table
         * 
         */
        init: function(id) {
            mobile.initTable(id);
        },

        /**
         * 
         * Refreshes the table.  This call is only needed for
         * refreshing a JQuery table in mobile environment.
         * In non mobile browsers it does nothing and can be used without any side effects.
         * 
         * @param {String} id the id of the table
         * 
         */
        refresh: function(id) {
            mobile.refreshTable(id);
        },


        /**
         * Adds the table sorter feature to the table.  Requires the table sorter plug-in.
         * @param {String} id of the table
         * @param {Object} [options] an options object to pass to the table sorter plug-in
         * @param {Array} [widgets] a list of widget names to pass in.  By default the 'group' and 'filter' widget is always included
         * @param {Object} [widgetOptions] options for the widgets
         * @param {Function} [groupFormatter] a function to tell the table sorter how to display the group header title
         * @param {Function} [groupCallback] a function 
         *  
         */
        /*eslint max-params: 0 */
        initTableSorter: function(id, options, widgets, widgetOptions, groupFormatter, groupCallback) {
            if (!$.tablesorter || ui.isMobile()) {
                return;
            }

            // wrap the passed in group formatter
            var groupFormatterWrapper = function(txt, col, table, c, wo) {
                txt = (util.isEmpty(txt) ? 'Empty' : txt);
                if (!groupFormatter) {
                    return txt;
                }
                return groupFormatter(txt, col, table, c, wo);
            };

            // Since we have the groupCallback as optional, we need to always pass in one
            // so create a wrapper does nothing if one was not provided
            var groupCallbackWrapper = function($cell, $rows, column, table) {
                if (groupCallback) {
                    groupCallback($cell, $rows, column, table);
                }
            };

            /*eslint camelcase: 0 */
            var widgetOptionsObject = {
                group_collapsible: true, // make the group header clickable and collapse the rows below it.
                group_collapsed: false, // start with all groups collapsed (if true)
                group_saveGroups: false, // remember collapsed groups
                group_saveReset: '.group_reset', // element to clear saved collapsed groups
                group_count: ' ({num} items)', // if not false, the '{num}' string is replaced with the number of rows in the group
                group_formatter: groupFormatterWrapper,
                group_callback: groupCallbackWrapper,
                // event triggered on the table when the grouping widget has finished work
                group_complete: 'groupingComplete',
                filter_hideFilters: true
            };

            if (widgetOptions && $.isPlainObject(widgetOptions)) {
                $.extend(widgetOptionsObject, widgetOptions);
            }

            var widgetsArray = ['group', 'filter'];
            if (widgets && $.isArray(widgets)) {
                $.merge(widgetsArray, widgets);
            }

            var optionsObject = {
                theme: 'blue',
                widgets: widgetsArray,
                widgetOptions: widgetOptionsObject
            };

            if (options && $.isPlainObject(options)) {
                $.extend(optionsObject, options);
            }

            $(util.idAsSelector(id)).tablesorter(optionsObject);
        }

    };


});

/** 
 * Utilities for polling a function and URLs.
 * @module poller 
 */
define('poller',['require','$','log'],function(require) {
    'use strict';

    var $ = require('$');
    var log = require('log');

    return {

        /**
         * Executes a function at a given interval
         * 
         * @param {integer} interval - the interval in milliseconds
         * @param {Function} theFunction - the function to call
         * 
         */
        pollFunction: function(interval, theFunction) {
            if (!$.isFunction(theFunction)) {
                return;
            }
            (function loopsiloop() {
                setTimeout(function() {
                    theFunction();
                    // recurse
                    loopsiloop();
                }, interval);
            }());
        },

        /**
         * Sends a request to the provided URL on a set interval.
         * 
         * @param {Object}  options a list of options required for this function.
         * @param {integer} options.interval - the interval in milliseconds.
         * @param {String}  options.url - A string containing the URL to which the request is sent.
         * @param {Object}  options.data - Either a function to be called to get data, a plain object or string to 
         * send to the server with the request
         * @param {Function} options.success - A callback function which is executed if the request succeeds.
         * @param {Function} options.error - A callback function which is executed if the request fails.
         * @param {String} options.dataType - he type of data expected from the server. Default: json
         */
        pollUrl: function(options) {
            var interval = options.interval,
                url = options.url,
                data = options.data,
                success = options.success,
                error = options.error,
                dataType = options.dataType;

            if (!data) {
                data = {};
            }

            if (!dataType) {
                dataType = 'json';
            }
            (function loopsiloop() {
                setTimeout(function() {
                    var postData = {};
                    if ($.isFunction(data)) {
                        postData = data();
                    }
                    else {
                        postData = data;
                    }

                    $.ajax({
                        type: 'POST',
                        url: url,
                        dataType: dataType,
                        data: postData,
                        success: function(response) {
                            if ($.isFunction(success)) {
                                success(response);
                            }
                            // recurse
                            loopsiloop();
                        },
                        error: function(jqXHR, textStatus, errorThrown) {
                            if ($.isFunction(error)) {
                                error(jqXHR, textStatus, errorThrown);
                            }
                            // recurse
                            loopsiloop();
                            log.error('poll: errorThrown=' + errorThrown + 'textStatus=' + textStatus);
                        }
                    });
                }, interval);
            }());
        }
    };


});

/*global define */

/**
 * The main module (sometimes called main.js) which defines the public 
 * interface for the coreleo library
 */
define('coreleo',['require','ui','ui/dialog','ui/form','ui/tabs','ui/select','ui/text','ui/table','$','constants','log','poller','util'],function(require) {
    'use strict';

    var ui = require('ui');
    ui.dialog = require('ui/dialog');
    ui.form = require('ui/form');
    ui.tabs = require('ui/tabs');
    ui.select = require('ui/select');
    ui.text = require('ui/text');
    ui.table = require('ui/table');

    //Return the module value.
    var coreleo = {
        version: '0.0.1',
        $: require('$'),
        constants: require('constants'),
        log: require('log'),
        poller: require('poller'),
        ui: ui,
        util: require('util')
    };

    return coreleo;
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
    return require('coreleo');
}));
