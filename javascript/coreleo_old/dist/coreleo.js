if (typeof coreleo === 'undefined') var coreleo = {};
if (typeof coreleo.util === 'undefined') coreleo.util = {};
if (typeof coreleo.ui === 'undefined') coreleo.ui = {};
if (typeof coreleo.ui.menu === 'undefined') coreleo.ui.menu = {};
if (typeof coreleo.ui.dialog === 'undefined') coreleo.ui.dialog = {};
if (typeof coreleo.ui.tabs === 'undefined') coreleo.ui.tabs = {};
if (typeof coreleo.mobile === 'undefined') coreleo.mobile = {};
if (typeof coreleo.mobile.util === 'undefined') coreleo.mobile.util = {};
if (typeof coreleo.mobile.util.selectmenu === 'undefined') coreleo.mobile.util.selectmenu = {};
if (typeof coreleo.mobile.util.table === 'undefined') coreleo.mobile.util.table = {};
if (typeof coreleo.mobile.util.input === 'undefined') coreleo.mobile.util.input = {};
if (typeof coreleo.event === 'undefined') coreleo.event = {};
if (typeof coreleo.event.poller === 'undefined') coreleo.event.poller = {};
if (typeof coreleo.console === 'undefined') coreleo.console = {};

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

$(function() {

    var TemplateCache = {
        get: function(selector) {
            if (!this.templates) {
                this.templates = {};
            }

            var template = this.templates[selector];
            if (!template) {
                template = $(selector).html();
                // precompile the template
                template = Handlebars.compile(template);
                this.templates[selector] = template;
            }
            return template;
        }
    };

    var renderTemplateWithCaching = function(selector, data) {
        var render = TemplateCache.get(selector);
        return render(data);
    };

    var renderTemplate = function(template, data) {
        var render = Handlebars.compile(template);
        return render(data);
    };

    var isMobileClient = function() {
        if (window.isMobileClient) {
            return window.isMobileClient();
        } else {
            return $.mobile ? true : false;
        }
    };


    var isSmallScreen = function() {
        if (window.isSmallScreen) {
            return window.isSmallScreen();
        }
        return false;
    };

    var timeoutButton = function(id, time) {
        if (!time) {
            time = coreleo.util.ONE_SECOND * 2;
        }
        var itemId = coreleo.util.formatId(id);
        $(itemId).prop('disabled', true);

        setTimeout(function() {
            $(itemId).prop('disabled', false);
        }, time);
    };


    var startsWith = function(str, ch) {
        return str.lastIndexOf(ch, 0) === 0;
    };


    var formatPhone = function(phone) {
        if (isEmpty(phone)) {
            return '';
        }
        return '(' + phone.substr(0, 3) + ') ' + phone.substr(3, 3) + '-' + phone.substr(6, 4);
    };


    var replaceCharAt = function(str, index, chr) {
        if (index > str.length - 1) return str;
        return str.substr(0, index) + chr + str.substr(index + 1);
    };

    var currencyFormatted = function(amount) {
        var i = parseFloat(amount);
        if (isNaN(i)) {
            i = 0.00;
        }
        var minus = '';
        if (i < 0) {
            minus = '-';
        }
        i = Math.abs(i);
        i = parseInt((i + 0.005) * 100);
        i = i / 100;
        var s = i.toString();
        if (s.indexOf('.') < 0) {
            s += '.00';
        }
        if (s.indexOf('.') === (s.length - 2)) {
            s += '0';
        }
        s = minus + s;
        return s;
    };


    var blankNull = function(text) {
        if (isEmpty(text)) {
            return '';
        } else {
            return text;
        }
    };

    /*jshint eqnull:true */
    var isEmpty = function(text) {
        return (
            text == null || text === null ||
            text === undefined ||
            typeof text === 'undefined' ||
            $.trim(text) == 'null' ||
            $.trim(text) === '' ||
            ($.isArray(text) && text.length === 0)
        );
    };

    var isNotEmpty = function(text) {
        return !isEmpty(text);
    };


    var toBoolean = function(text) {
        if (isEmpty(text)) {
            return false;
        }

        text = text.trim().toLowerCase();
        if (text === 'true' || text === 't' || text === '1' || text === 'y' || text === 'yes' || text === 1 || text === true) {
            return true;
        } else {
            return false;
        }
    };


    var isTrue = function(text) {
        return toBoolean(text) === true;
    };


    var zeroFill = function(number, size) {
        if (isEmpty(number)) {
            return '';
        }
        number = number.toString();
        while (number.length < size) number = '0' + number;
        return number;
    };


    var addParamToUrl = function(url, param, value) {
        if (typeof param === 'undefined') {
            return url;
        }

        if (typeof value === 'undefined') {
            return url;
        }

        var sep = url.indexOf('?') === -1 ? '?' : '&';
        return url + sep + encodeURIComponent(param) + '=' + encodeURIComponent(value);
    };

    var formatId = function(id) {
        id = id.trim();

        if (startsWith(id, '.')) {
            return id;
        }

        return startsWith(id, '#') ? id : '#' + id;
    };

    var enableTextField = function(id) {
        var item = $(coreleo.util.formatId(id));
        item.attr('disabled', false);
        coreleo.mobile.util.input.enableTextField(id);
    };

    var disableTextField = function(id) {
        var item = $(coreleo.util.formatId(id));
        item.attr('disabled', true);
        coreleo.mobile.util.input.disableTextField(id);
    };

    var contains = function(string, subString) {
        if (!string) {
            return false;
        }

        if (!subString) {
            return false;
        }

        return string.indexOf(subString) !== -1;
    };

    var containsIgnoreCase = function(string, subString) {
        if (!string) {
            return false;
        }

        if (!subString) {
            return false;
        }

        return contains(string.toLowerCase(), subString.toLowerCase());
    };



    var initTableSorter = function(id, options, widgets, widgetOptions, groupFormatter, groupCallback) {
        // don't bother with sorting on mobile client
        if (isMobileClient()) {
            return;
        }

        var groupFormatterWrapper = function(txt, col, table, c, wo) {
            txt === '' ? 'Empty' : txt;
            if (!groupFormatter) {
                return txt;
            }
            return groupFormatter(txt, col, table, c, wo);
        };

        var groupCallbackWrapper = function($cell, $rows, column, table) {
            if (groupCallback) {
                groupCallback($cell, $rows, column, table);
            }
        };

        var widgetOptionsObject = {
            group_collapsible: true, // make the group header clickable and collapse the rows below it.
            group_collapsed: false, // start with all groups collapsed (if true)
            group_saveGroups: false, // remember collapsed groups
            group_saveReset: '.group_reset', // element to clear saved collapsed groups
            group_count: ' ({num} items)', // if not false, the '{num}' string is replaced with the number of rows in the group
            group_formatter: groupFormatterWrapper,
            group_callback: groupCallbackWrapper,
            // event triggered on the table when the grouping widget has finished work
            group_complete: 'groupingComplete',
            filter_hideFilters: true
        };

        if (widgetOptions && $.isPlainObject(widgetOptions)) {
            $.extend(widgetOptionsObject, widgetOptions);
        }

        var widgetsArray = ['group', 'filter'];
        if (widgets && $.isArray(widgets)) {
            $.merge(widgetsArray, widgets);
        }

        var optionsObject = {
            theme: 'blue',
            widgets: widgetsArray,
            widgetOptions: widgetOptionsObject
        };

        if (options && $.isPlainObject(options)) {
            $.extend(optionsObject, options);
        }

        $(coreleo.util.formatId(id)).tablesorter(optionsObject);
    };


    var trimNewLineChar = function(string) {
        if (!string) {
            return false;
        }

        return string.replace(/(\r\n|\n|\r)/gm, '');
    };


    var trimWhiteSpaceChar = function(string) {
        if (!string) {
            return false;
        }

        return string.replace(/(\s)/gm, '');
    };


    var redirectPost = function(location, args, target) {
        if (!target) {
            target = '_self';
        }

        var form = '';
        if ($.isArray(args)) {
            $.each(args, function(index, obj) {
                form += '<input type="hidden" name="' + obj.name + '" value="' + obj.value + '">';
            });
        } else {
            $.each(args, function(key, value) {
                form += '<input type="hidden" name="' + key + '" value="' + value + '">';
            });
        }

        var dynamicForm = '<form data-ajax="false" target="' + target + '" action="' + location + '" method="POST">' + form + '</form>';
        $(dynamicForm).appendTo($(document.body)).submit();
    };


    var formatClass = function(cssClass) {
        return startsWith(cssClass, ".") ? cssClass : "." + cssClass;
    };


    var hasWhiteSpace = function(s) {
        return /\s/g.test(s);
    };


    var properCase = function(s) {
        if (!s) {
            return s;
        }
        s = s.toLowerCase();
        return s.replace(/\b[a-z]/g, function(f) {
            return f.toUpperCase();
        });
    };


    var getParameterFromUrl = function(urlString, paramName) {
        return (urlString.split('' + paramName + '=')[1] || '').split('&')[0];
    };


    var loading = function(id) {
        var div = $(coreleo.util.formatId(id));
        div.empty();
        div.html("<image src='images/ajax-loader2.gif'></image>");
    };


    var endLoading = function(id) {
        var div = $(coreleo.util.formatId(id));
        div.empty();
    };

    var toKeyValueHash = function(key, value) {
        return {
            'key': key,
            'value': value
        };
    };



    // public API
    coreleo.util.ONE_SECOND = 1000;
    coreleo.util.ONE_MINUTE = 60000;
    coreleo.util.redirectPost = redirectPost;
    coreleo.util.formatPhone = formatPhone;
    coreleo.util.blankNull = blankNull;
    coreleo.util.zeroFill = zeroFill;
    coreleo.util.currencyFormatted = currencyFormatted;
    coreleo.util.isEmpty = isEmpty;
    coreleo.util.replaceCharAt = replaceCharAt;
    coreleo.util.formatPhone = formatPhone;
    coreleo.util.isNotEmpty = isNotEmpty;
    coreleo.util.addParamToUrl = addParamToUrl;
    coreleo.util.formatId = formatId;
    coreleo.util.timeoutButton = timeoutButton;
    coreleo.util.enableTextField = enableTextField;
    coreleo.util.disableTextField = disableTextField;
    coreleo.util.initTableSorter = initTableSorter;
    coreleo.util.isMobileClient = isMobileClient;
    coreleo.util.isSmallScreen = isSmallScreen;
    coreleo.util.contains = contains;
    coreleo.util.containsIgnoreCase = containsIgnoreCase;
    coreleo.util.trimNewLineChar = trimNewLineChar;
    coreleo.util.trimWhiteSpaceChar = trimWhiteSpaceChar;
    coreleo.util.formatClass = formatClass;
    coreleo.util.startsWith = startsWith;
    coreleo.util.renderTemplate = renderTemplate;
    coreleo.util.renderTemplateWithCaching = renderTemplateWithCaching;
    coreleo.util.hasWhiteSpace = hasWhiteSpace;
    coreleo.util.properCase = properCase;
    coreleo.util.getParameterFromUrl = getParameterFromUrl;
    coreleo.util.loading = loading;
    coreleo.util.endLoading = endLoading;
    coreleo.util.toKeyValueHash = toKeyValueHash;
    coreleo.util.toBoolean = toBoolean;
    coreleo.util.isTrue = isTrue;


});

$(function() {

    var pollFunction = function(theFunction, interval) {
        if (typeof theFunction !== "function") {
            return;
        }

        (function loopsiloop() {
            setTimeout(function() {

                theFunction();

                // recurse
                loopsiloop();

            }, interval);
        })();
    };


    /**
     * Similar to using setInterval, however this function will only poll again once the original call is complete.
     */
    var pollUlr = function(
        /* String */
        url,
        /* int */
        interval,
        /* function */
        successFunction,
        /* function */
        errorFunction,
        /* object or function */
        postData,
        /* string */
        dataType
    ) {
        if (!postData) {
            postData = {};
        }

        if (!dataType) {
            dataType = "json";
        }

        (function loopsiloop() {
            setTimeout(function() {
                var data = {};
                if (typeof(postData) === 'function') {
                    data = postData();
                } else {
                    data = postData;
                }

                $.ajax({
                    type: "POST",
                    url: url,
                    dataType: dataType,
                    data: data,
                    success: function(response) {
                        if (typeof successFunction === "function") {
                            successFunction(response);
                        }
                        // recurse
                        loopsiloop();
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        if (typeof errorFunction === "function") {
                            errorFunction(jqXHR, textStatus, errorThrown);
                        }
                        // recurse
                        loopsiloop();
                        console.error("poll: errorThrown=" + errorThrown + "textStatus=" + textStatus);
                    }
                });
            }, interval);
        })();
    };


    // public API
    coreleo.event.poller.pollUrl = pollUlr;
    coreleo.event.poller.pollFunction = pollFunction;


});

$(function() {
    var confirmDialogTmpl = "<div data-role='popup' data-shadow='false' data-dismissible='false'";
    confirmDialogTmpl = confirmDialogTmpl + "id='my-confirm-dialog' ";
    confirmDialogTmpl = confirmDialogTmpl + "class='ui-dialog dialog confirm-dialog ui-corner-all' ";
    confirmDialogTmpl = confirmDialogTmpl + "title='{title}'>";
    confirmDialogTmpl = confirmDialogTmpl + "{header}<div class='icon-content {iconClass}'/><div class='ui-dialog-content text-content'>{text}</div>";
    confirmDialogTmpl = confirmDialogTmpl + "<div class='dialog-footer'>";
    confirmDialogTmpl = confirmDialogTmpl + "<button class='save' type='button'>Ok</button>";
    confirmDialogTmpl = confirmDialogTmpl + "<button class='cancel' type='button'>Cancel</button>";
    confirmDialogTmpl = confirmDialogTmpl + "</div>";
    confirmDialogTmpl = confirmDialogTmpl + "</div>";

    var alertDialogTmpl = "<div data-role='popup' data-shadow='false' data-dismissible='false' title='{title}'";
    alertDialogTmpl = alertDialogTmpl + "class='ui-dialog dialog alert-dialog ui-corner-all'>{header}";
    alertDialogTmpl = alertDialogTmpl + "<div class='icon-content {iconClass}'/><div class='text-content'>{text}</div></div>";


    var closeButtonTmpl = "<button class='ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close' role='button' aria-disabled='false' title='close'><span class='ui-button-icon-primary ui-icon ui-icon-closethick'></span><span class='ui-button-text'>close</span></button>";


    var open = function(id) {
        var itemId = coreleo.util.formatId(id);

        if ($(itemId).panel) {
            $(itemId).panel("open");
        } else {
            $(itemId).dialog("open");
        }
    };

    var close = function(id) {
        var itemId = coreleo.util.formatId(id);

        if ($(itemId).panel) {
            $(itemId).panel("close");
        } else {
            $(itemId).dialog("close");
        }
    };

    var init = function(id, width, height) {
        if (!coreleo.util.isMobileClient()) {
            $(coreleo.util.formatId(id)).dialog({
                autoOpen: false,
                height: height,
                width: width,
                modal: true
            });
        }
    };

    var confirm = function(title, text, successFunction, iconClass) {
        if (!iconClass) {
            iconClass = "";
        }

        if (coreleo.util.isMobileClient()) {
            showConfirmPopup(title, text, successFunction, iconClass);
        } else {
            showConfirmDialog(title, text, successFunction, iconClass);
        }
    };

    var showConfirmPopup = function(title, text, successFunction, iconClass) {
        var template = confirmDialogTmpl;
        template = template.replace("{title}", title);
        template = template.replace("{text}", text);
        var header = "<div class='header ui-dialog-titlebar ui-widget-header ui-corner-all' data-role='header'>{title}</div>".replace("{title}", title);
        template = template.replace("{header}", header);
        template = template.replace("{iconClass}", iconClass);

        var item = $(template);
        $('.save', item).click(function(eventObject) {
            successFunction(item);
            closeDialogOrPopup("#my-confirm-dialog");
            destroyDialogOrPopup("#my-confirm-dialog");
        });

        $('.cancel', item).click(function(eventObject) {
            closeDialogOrPopup("#my-confirm-dialog");
            destroyDialogOrPopup("#my-confirm-dialog");
        });

        item.popup();
        item.popup("open");
    };


    var showConfirmDialog = function(title, text, successFunction, iconClass) {
        var template = confirmDialogTmpl;
        template = template.replace("{title}", title);
        template = template.replace("{text}", text);
        template = template.replace("{header}", "");
        template = template.replace("{iconClass}", iconClass);

        var item = $(template);
        $('.save', item).click(function(eventObject) {
            successFunction(item);
            closeDialogOrPopup("#my-confirm-dialog");
            destroyDialogOrPopup("#my-confirm-dialog");
        });

        $('.cancel', item).click(function(eventObject) {
            closeDialogOrPopup("#my-confirm-dialog");
            destroyDialogOrPopup("#my-confirm-dialog");
        });

        item.dialog({
            autoOpen: false,
            resizable: true,
            modal: true
        });

        item.dialog("open");
    };


    var destroyDialogOrPopup = function(id) {
        var item = null;
        if (typeof id === 'string') {
            var itemId = coreleo.util.formatId(id);
            item = $(itemId);
        } else {
            item = id;
        }

        if (item.popup) {
            item.popup("destroy").remove();
        } else {
            item.dialog('destroy').remove();
        }
    };

    var closeDialogOrPopup = function(id) {
        var item = null;
        if (typeof id === 'string') {
            var itemId = coreleo.util.formatId(id);
            item = $(itemId);
        } else {
            item = id;
        }

        if (item.popup) {
            item.popup("close");
        } else {
            item.dialog("close");
        }
    };

    var showLoadingDialog = function(title, text, loadingImageClass) {
        if (!text) {
            text = "Loading, please wait...";
        }

        if (!loadingImageClass) {
            loadingImageClass = "loading-image";
        }

        var template = "<div data-role='popup' data-shadow='false' data-dismissible='false' ";
        template = template + " class='ui-dialog dialog loading-dialog ui-corner-all' ";
        template = template + "id='my-loading-dialog' title='{title}'>";
        template = template + "<div class='" + loadingImageClass + "'></div>{text}</div>";

        template = template.replace("{title}", title);
        template = template.replace("{text}", text);

        var item = $(template);
        if (coreleo.util.isMobileClient()) {
            item.popup();
            item.popup("open");
        } else {
            item.dialog({
                autoOpen: false,
                closeOnEscape: false,
                modal: true,
                dialogClass: "loading-dialog-contentpane",
                height: 70,
                open: function(event, ui) {
                    $('.loading-dialog-contentpane .ui-dialog-titlebar-close').hide();
                    if (!title) {
                        $('.loading-dialog-contentpane .ui-dialog-titlebar').hide();
                    }
                }
            });
            item.dialog("open");
        }
    };


    var hideLoadingDialog = function() {
        closeDialogOrPopup("#my-loading-dialog");
        destroyDialogOrPopup("#my-loading-dialog");
    };


    var alert = function(title, text, iconClass) {
        if (!iconClass) {
            iconClass = "";
        }

        var template = alertDialogTmpl;
        template = template.replace("{title}", title);
        template = template.replace("{text}", text);
        template = template.replace("{iconClass}", iconClass);

        if (coreleo.util.isMobileClient()) {
            var header = "<div class='header ui-dialog-titlebar ui-widget-header ui-corner-all' data-role='header'><span class='ui-dialog-title'>{title}</span>{close}</div>".replace("{title}", title);
            header = header.replace("{close}", closeButtonTmpl);
            template = template.replace("{header}", header);

            var item = $(template);

            $('.header', item).click(function(eventObject) {
                closeDialogOrPopup(item);
                destroyDialogOrPopup(item);
            });

            item.popup();
            item.popup("open");
        } else {
            template = template.replace("{header}", "");
            template = template.replace("{close}", "");
            $(template).dialog();
        }
    };


    // public API
    coreleo.ui.dialog.open = open;
    coreleo.ui.dialog.close = close;
    coreleo.ui.dialog.init = init;
    coreleo.ui.dialog.create = init;
    coreleo.ui.dialog.confirm = confirm;
    coreleo.ui.dialog.showLoadingDialog = showLoadingDialog;
    coreleo.ui.dialog.hideLoadingDialog = hideLoadingDialog;
    coreleo.ui.dialog.alert = alert;

});

$(function() {

    var isMenuItemDisabled = function(selector) {
        return false;
    };


    // public API
    coreleo.ui.menu.isMenuItemDisabled = isMenuItemDisabled;

});

$(function() {

    var refreshSelect = function(id) {
        var item = $(coreleo.util.formatId(id));
        if ($.mobile && item.selectmenu) {
            item.selectmenu('refresh');
        }
    };

    var initTable = function(id) {
        var item = $(coreleo.util.formatId(id));
        if ($.mobile && item.table) {
            item.table();
        }
    };

    var refreshTable = function(id) {
        var item = $(coreleo.util.formatId(id));
        if ($.mobile && item.table) {
            item.table("rebuild");
        }
    };

    var enableTextField = function(id) {
        var item = $(coreleo.util.formatId(id));
        if ($.mobile && item.textinput) {
            item.textinput("enable");
        }
    };

    var disableTextField = function(id) {
        var item = $(coreleo.util.formatId(id));
        if ($.mobile && item.textinput) {
            item.textinput("disable");
        }
    };

    // public API
    coreleo.mobile.util.selectmenu.refresh = refreshSelect;
    coreleo.mobile.util.table.init = initTable;
    coreleo.mobile.util.table.refreshTable = refreshTable;
    coreleo.mobile.util.input.enableTextField = enableTextField;
    coreleo.mobile.util.input.disableTextField = disableTextField;

});

$(function() {

    var MAX_TABS = 12;
    var tabCount = 1;
    var maxTabs = MAX_TABS;


    var isMaxNumTabsOpen = function() {
        return tabCount >= maxTabs;
    };

    var incrementTabCount = function() {
        tabCount++;
    };

    var decrementTabCount = function() {
        tabCount--;
    };


    var addTab = function(tabContainerId, tabId, tabTitle, tabContent, showCloseIcon, closeTabText) {
        var tabTemplate = "<li><a id='tab-anchor-{id}' href='#{href}'>{tabTitle}</a>{closeIcon}</li>";
        var closeIconTemplate = "<span tabIndex='0' class='ui-icon ui-icon-close'>{closeText}</span>";

        if (showCloseIcon) {
            closeIconTemplate = closeIconTemplate.replace("{closeText}", closeTabText + " " + tabTitle);
            tabTemplate = tabTemplate.replace("{closeIcon}", closeIconTemplate);
        } else {
            tabTemplate = tabTemplate.replace("{closeIcon}", "");
        }

        var tabs = $(coreleo.util.formatId(tabContainerId));
        var li = $(tabTemplate.replace("{id}", tabId).replace("{href}", tabId).replace("{tabTitle}", tabTitle));
        tabs.find(".ui-tabs-nav").first().append(li);
        tabs.append("<div id='" + tabId + "'><p>" + tabContent + "</p></div>");
        tabs.tabs("refresh");
    };

    var addAjaxTab = function(tabContainerId, tabId, tabTitle, href, showCloseIcon, closeTabText) {
        var tabTemplate = "<li><a id='tab-anchor-{id}' href='{href}'>{tabTitle}</a>{closeIcon}</li>";
        var closeIconTemplate = "<span tabIndex='0' class='ui-icon ui-icon-close'>{closeText}</span>";

        if (showCloseIcon) {
            closeIconTemplate = closeIconTemplate.replace("{closeText}", closeTabText + ' ' + tabTitle);
            tabTemplate = tabTemplate.replace("{closeIcon}", closeIconTemplate);
        } else {
            tabTemplate = tabTemplate.replace("{closeIcon}", "");
        }

        var tabs = $(coreleo.util.formatId(tabContainerId));
        var li = $(tabTemplate.replace("{id}", tabId).replace("{href}", href).replace("{tabTitle}", tabTitle));
        tabs.find(".ui-tabs-nav").first().append(li);
        tabs.tabs("refresh");
    };

    var renameTab = function(tabContainerId, tabId, title) {
        tabContainerId = coreleo.util.formatId(tabContainerId);
        var tabAnchor = $(tabContainerId + ' a[id="tab-anchor-' + tabId + '"]');
        tabAnchor.html(title);
    };

    var refresh = function(tabContainerId) {
        tabContainerId = coreleo.util.formatId(tabContainerId);
        var tabs = $(tabContainerId).tabs();
        tabs.tabs("refresh");
    };

    var getTabIndexById = function(tabContainerId, tabId) {
        tabContainerId = coreleo.util.formatId(tabContainerId);
        var tabAnchor = $(tabContainerId + ' a[id="tab-anchor-' + tabId + '"]');
        if (tabAnchor.length === 0) {
            return -1;
        }

        return tabAnchor.parent().index();
    };

    var focusTab = function(tabContainerId, tabId) {
        tabContainerId = coreleo.util.formatId(tabContainerId);
        var tabAnchor = $(tabContainerId + ' a[id="tab-anchor-' + tabId + '"]');
        tabAnchor.focus();
    };

    var selectTab = function(tabContainerId, tabId) {
        tabContainerId = coreleo.util.formatId(tabContainerId);
        var tabIndex = getTabIndexById(tabContainerId, tabId);
        var tabs = $(tabContainerId).tabs();
        tabs.tabs("option", "active", tabIndex);
    };

    var addCloseFunctionToCloseIcon = function(tabContainerId) {
        tabContainerId = coreleo.util.formatId(tabContainerId);
        var tabs = $(tabContainerId).tabs();

        // close icon: removing the tab on click
        tabs.delegate("span.ui-icon-close", "click", function() {
            var panelId = $(this).closest("li").remove().attr("aria-controls");
            $("#" + panelId).remove();
            coreleo.ui.tabs.refresh(tabContainerId);
            coreleo.ui.tabs.decrementTabCount();
        });
    };


    var closeTab = function(tabContainerId, tabId) {
        var panelId = $(tabContainerId + ' a[id="tab-anchor-' + tabId + '"]').closest("li").remove().attr("aria-controls");
        $("#" + panelId).remove();
        coreleo.ui.tabs.refresh(tabContainerId);
        coreleo.ui.tabs.decrementTabCount();
    };


    var getSelectedTabIndex = function(tabContainerId) {
        tabContainerId = coreleo.util.formatId(tabContainerId);
        return $(tabContainerId).tabs('option', 'active');
    };

    var getSelectedTabId = function(tabContainerId) {
        tabContainerId = coreleo.util.formatId(tabContainerId);
        var index = getSelectedTabIndex(tabContainerId);
        var id = ($(tabContainerId + " ul>li a").eq(index).attr('href'));
        return coreleo.util.startsWith(id, "#") ? id.substring(1, id.lenght) : id;
    };



    // Public API
    coreleo.ui.tabs.focusTab = focusTab;
    coreleo.ui.tabs.addTab = addTab;
    coreleo.ui.tabs.addAjaxTab = addAjaxTab;
    coreleo.ui.tabs.isMaxNumTabsOpen = isMaxNumTabsOpen;
    coreleo.ui.tabs.incrementTabCount = incrementTabCount;
    coreleo.ui.tabs.decrementTabCount = decrementTabCount;
    coreleo.ui.tabs.renameTab = renameTab;
    coreleo.ui.tabs.getTabIndexById = getTabIndexById;
    coreleo.ui.tabs.refresh = refresh;
    coreleo.ui.tabs.addCloseFunctionToCloseIcon = addCloseFunctionToCloseIcon;
    coreleo.ui.tabs.closeTab = closeTab;
    coreleo.ui.tabs.getSelectedTabIndex = getSelectedTabIndex;
    coreleo.ui.tabs.getSelectedTabId = getSelectedTabId;
    coreleo.ui.tabs.selectTab = selectTab;

});

$(function() {
    var idleTime = 0;
    var started = false;
    var idleInterval = null;


    var startIdleTimer = function(doSomethingOnIdle) {
        if (started) {
            console.log("Idle timer already started.");
            return false;
        }

        started = true;

        //Increment the idle time counter every minute.
        idleInterval = setInterval(function() {
            idleTime++;
            doSomethingOnIdle(idleTime * coreleo.util.ONE_MINUTE);
        }, coreleo.util.ONE_MINUTE);

        //Zero the idle timer on mouse movement.
        $(document).mousemove(function(e) {
            idleTime = 0;
        });
        $(document).keypress(function(e) {
            idleTime = 0;
        });

        return true;
    };


    var stopIdleTimer = function() {
        if (idleInterval !== null) {
            clearInterval(idleInterval);
            started = false;
            return true;
        }

        return false;
    };


    // public API
    coreleo.event.startIdleTimer = startIdleTimer;
    coreleo.event.stopIdleTimer = stopIdleTimer;
});
