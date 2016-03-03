define(function(require) {
    'use strict';

    var $ = require('./$');
    var util = require('../util');

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
