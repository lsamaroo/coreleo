/*global define */

/**
 * The main module (sometimes called main.js) which defines the public 
 * interface for the coreleo library
 */
define(function(require) {
    'use strict';

    //Return the module value.
    return {
        version: '0.0.1',
        $: require('$'),
        log: require('log'),
        constants: require('constants'),
        util: require('util'),
        ui: require('ui')

    };
});
