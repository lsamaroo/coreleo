define(function(require) {
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
