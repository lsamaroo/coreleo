/*global define */

/**
 * The main module (sometimes called main.js) which defines the public 
 * interface for the coreleo library
 */
define(function(require) {
    'use strict';

    var $ = require('$');
    var _ = require('lodash');
    var handlebars = require('handlebars');

    var util = require('util'),
        ui = require('ui');

    //Return the module value.
    return {
        version: '0.0.1',
        util: util,
        ui: ui
    };
});
