define(function(require) {
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
