/**
 * This object contains functions dealing with JQuery mobile and is never exposed as a public API.
 * Instead these functions are called in the various public UI API to handle JQuery mobile elements in
 * the appropriate fashion behind the scenes. 
 * 
 */
define(function(require) {
    'use strict';

    var $ = require('$');
    var util = require('util');


    return {

        /*
         * Refreshes a JQuery mobile select item when options are changed.
         * 
         * @param {String} id the id of the select item
         * 
         */
        refreshSelect: function(id) {
            var item = $(util.idAsSelector(id));
            if (this.isMobile() && item.selectmenu) {
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
            if (this.isMobile() && item.table) {
                item.table();
            }
        },

        refreshTable: function(id) {
            var item = $(util.idAsSelector(id));
            if (this.isMobile() && item.table) {
                item.table('rebuild');
            }
        },


        enableTextField: function(id) {
            var item = $(util.idAsSelector(id));
            if (this.isMobile() && item.textinput) {
                item.textinput('enable');
            }
        },

        disableTextField: function(id) {
            var item = $(util.idAsSelector(id));
            if (this.isMobile() && item.textinput) {
                item.textinput('disable');
            }
        },

        isMobile: function() {
            return $.mobile ? true : false;
        }
    };
});
