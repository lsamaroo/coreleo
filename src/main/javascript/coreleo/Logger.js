$(function() {
    // Create a fake logger if the logger does not exist.
    if (!(window.console && console.log)) {
        console = {
            log: function() {},
            debug: function() {},
            info: function() {},
            warn: function() {},
            error: function() {}
        };
    }

    coreleo.console = console;
});
