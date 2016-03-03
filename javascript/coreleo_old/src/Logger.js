$(function() {
    // Create a fake logger if the logger does not exist.
    if (!(window.console && console.log)) {
        var myconsole = {
            log: function() {},
            debug: function() {},
            info: function() {},
            warn: function() {},
            error: function() {}
        };
        coreleo.console = myconsole;
    } else {
        coreleo.console = console;
    }


});
