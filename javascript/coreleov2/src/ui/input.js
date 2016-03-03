define(function(require) {
    'use strict';

    var $ = require('./$');
    var util = require('../util');

    return {
        enableTextField: function(id) {
            var item = $(util.idAsSelector(id));
            item.attr('disabled', false);
        },

        disableTextField: function(id) {
            var item = $(util.idAsSelector(id));
            item.attr('disabled', true);
        }
    };
});
