define(function(require) {
    'use strict';

    if (!(window.console && console.log)) {
        return {
            log: function() {},
            debug: function() {},
            info: function() {},
            warn: function() {},
            error: function() {}
        };
    }
    else {
        return window.console;
    }

});
