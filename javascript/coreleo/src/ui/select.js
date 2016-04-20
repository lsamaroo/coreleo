/** 
 * Utilities for handling JQuery mobile and select2 select.
 * @module select 
 */
define(function(require) {
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
