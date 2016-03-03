define(function(require) {
    'use strict';

    var $ = require('./$');
    var _ = require('lodash');
    var handlebars = require('handlebars');


    var util = {
        HandlebarsTemplateCache: {
            get: function(selector) {
                if (!this.templates) {
                    this.templates = {};
                }

                var template = this.templates[selector];
                if (!template) {
                    template = $(selector).html();
                    // precompile the template
                    template = handlebars.compile(template);
                    this.templates[selector] = template;
                }
                return template;
            }
        },

        renderTemplateWithCaching: function(selector, data) {
            var render = this.HandlebarsTemplateCache.get(selector);
            return render(data);
        },

        renderTemplate: function(template, data) {
            var render = handlebars.compile(template);
            return render(data);
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

        formatPhone: function(phone) {
            if (_.isEmpty(phone)) {
                return '';
            }
            return '(' + phone.substr(0, 3) + ') ' + phone.substr(3, 3) + '-' + phone.substr(6, 4);
        },


        replaceCharAt: function(str, index, chr) {
            if (index > str.length - 1) {
                return str;
            }
            return str.substr(0, index) + chr + str.substr(index + 1);
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
         * @deprecated
         * @alias for formatCurrency
         */
        currencyFormatted: this.formatCurrency,


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
         * Pads the given string with zeros to fill the size specified
         */
        zeroFill: function(string, size) {
            if (this.isEmpty(string)) {
                return '';
            }
            return _.padStart(string, (size - string.length), '0');
        },

        /**
         * Adds a param and value to an existing url.
         */
        addParamToUrl: function(url, param, value) {
            if (this.isEmpty(param)) {
                return url;
            }

            if (this.isEmpty(value)) {
                return url;
            }

            var seperator = url.indexOf('?') === -1 ? '?' : '&';
            return url + seperator + encodeURIComponent(param) + '=' + encodeURIComponent(value);
        },

        idAsSelector: function(id) {
            if (this.isEmpty(id)) {
                return '';
            }

            id = id.trim();
            if (this.startsWith(id, '.')) {
                return id;
            }
            return this.startsWith(id, '#') ? id : '#' + id;
        },


        /**
         * @deprecated
         * @alias for idAsSelector
         */
        formatId: this.idAsSelector,

        classAsSelector: function(cssClass) {
            return this.startsWith(cssClass, '.') ? cssClass : '.' + cssClass;
        },

        /**
         * @deprecated
         * @alias for classAsSelector
         */
        formatClass: this.classAsSelector,

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


        redirectAsPost: function(location, args, target) {
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


        /**
         * @deprecated
         * @alias for redirectAsPost
         */
        redirectPost: this.redirectAsPost,


        hasWhiteSpace: function(s) {
            return (/\s/g.test(s));
        },


        properCase: function(str) {
            if (this.isEmpty(str)) {
                return '';
            }
            str = str.toLowerCase();
            return str.replace(/\b[a-z]/g, function(f) {
                return f.toUpperCase();
            });
        },


        getParameterFromUrl: function(urlString, paramName) {
            return (urlString.split('' + paramName + '=')[1] || '').split('&')[0];
        },


        toKeyValueHash: function(key, value) {
            return {
                'key': key,
                'value': value
            };
        }
    };

    return _.assign({}, _, util);
});
