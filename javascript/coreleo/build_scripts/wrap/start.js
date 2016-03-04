(function (root, factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD.
        define(['jquery', 'lodash'], factory);
    } 
 	else if (typeof module === 'object' && module.exports) {
        // Node. Does not work with strict CommonJS, but
        // only CommonJS-like environments that support module.exports,
        // like Node.
        module.exports = factory(require('jquery'), require('lodash'));
    }    
    else {
        // Browser globals
        root.coreleo = factory(root.$, root._);
    }
}(this, function ($, _) {


