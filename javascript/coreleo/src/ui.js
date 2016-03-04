define(function(require) {
    'use strict';

    var $ = require('$');
    var constants = require('constants');
    var mobile = require('ui/mobile');
    var input = require('ui/input');

    return {
        isMobileClient: function() {
            return mobile.isMobileClient();
        },


        timeoutButton: function(id, time) {
            if (!time) {
                time = constants.ONE_SECOND * 2;
            }
            var itemId = this.idAsSelector(id);
            $(itemId).prop('disabled', true);

            setTimeout(function() {
                $(itemId).prop('disabled', false);
            }, time);
        },



        enableTextField: function(id) {
            input.enableTextField(id);
            mobile.enableTextField(id);
        },


        disableTextField: function(id) {
            input.disableTextField(id);
            mobile.disableTextField(id);
        }
    };


});
