define(function(require) {
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
