/** 
 * Utilities for handling JQuery dialog and mobile pop-ups.
 * @module dialog 
 */
define(function(require) {
    'use strict';

    var $ = require('$');
    var ui = require('ui');
    var util = require('util');

    var CONFIRM_DIALOG_TMPL = '<div data-role="popup" data-shadow="false" data-dismissible="false"';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + 'id="my-confirm-dialog" ';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + 'class="ui-dialog dialog confirm-dialog ui-corner-all" ';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + 'title="{title}">';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + '{header}<div class="icon-content {iconClass}"/><div class="ui-dialog-content text-content">{text}</div>';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + '<div class="dialog-footer">';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + '<button class="save" type="button">Ok</button>';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + '<button class="cancel" type="button">Cancel</button>';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + '</div>';
    CONFIRM_DIALOG_TMPL = CONFIRM_DIALOG_TMPL + '</div>';

    var CONFIRM_DIALOG_HEADER_TMPL = '<div class="header ui-dialog-titlebar ui-widget-header ui-corner-all" data-role="header">{title}</div>';

    var LOADING_DIALOG_TMPL = '<div data-role="popup" data-shadow="false" data-dismissible="false" ';
    LOADING_DIALOG_TMPL = LOADING_DIALOG_TMPL + ' class="ui-dialog dialog loading-dialog ui-corner-all" ';
    LOADING_DIALOG_TMPL = LOADING_DIALOG_TMPL + 'id="my-loading-dialog" title="{title}">';
    LOADING_DIALOG_TMPL = LOADING_DIALOG_TMPL + '<div class="{loadingImageClass}"></div>{text}</div>';

    var ALERT_DIALOG_TMPL = '<div data-role="popup" data-shadow="false" data-dismissible="false" title="{title}"';
    ALERT_DIALOG_TMPL = ALERT_DIALOG_TMPL + 'class="ui-dialog dialog alert-dialog ui-corner-all">{header}';
    ALERT_DIALOG_TMPL = ALERT_DIALOG_TMPL + '<div class="icon-content {iconClass}"/><div class="text-content">{text}</div></div>';

    var CLOSE_BUTTON_TMPL = '<button class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close" ';
    CLOSE_BUTTON_TMPL = CLOSE_BUTTON_TMPL + 'role="button" aria-disabled="false" title="close"><span class="ui-button-icon-primary ui-icon ui-icon-closethick">';
    CLOSE_BUTTON_TMPL = CLOSE_BUTTON_TMPL + '</span><span class="ui-button-text">close</span></button>';

    var ALERT_DIALOG_HEADER_TMPL = '<div class="header ui-dialog-titlebar ui-widget-header ui-corner-all" data-role="header"><span class="ui-dialog-title">{title}</span>{close}</div>';

    var LOADING_IMAGE = 'Loading, please wait...';

    var showConfirmPopup = function(title, text, successFunction, iconClass) {
        var template = CONFIRM_DIALOG_TMPL;
        template = template.replace('{title}', title);
        template = template.replace('{text}', text);
        var header = CONFIRM_DIALOG_HEADER_TMPL.replace('{title}', title);
        template = template.replace('{header}', header);
        template = template.replace('{iconClass}', iconClass);

        var item = $(template);
        $('.save', item).click(function(eventObject) {
            successFunction(item);
            closeDialogOrPopup('#my-confirm-dialog');
            destroyDialogOrPopup('#my-confirm-dialog');
        });

        $('.cancel', item).click(function(eventObject) {
            closeDialogOrPopup('#my-confirm-dialog');
            destroyDialogOrPopup('#my-confirm-dialog');
        });

        item.popup();
        item.popup('open');
    };


    var showConfirmDialog = function(title, text, successFunction, iconClass) {
        var template = CONFIRM_DIALOG_TMPL;
        template = template.replace('{title}', title);
        template = template.replace('{text}', text);
        template = template.replace('{header}', '');
        template = template.replace('{iconClass}', iconClass);

        var item = $(template);
        $('.save', item).click(function(eventObject) {
            successFunction(item);
            closeDialogOrPopup('#my-confirm-dialog');
            destroyDialogOrPopup('#my-confirm-dialog');
        });

        $('.cancel', item).click(function(eventObject) {
            closeDialogOrPopup('#my-confirm-dialog');
            destroyDialogOrPopup('#my-confirm-dialog');
        });

        item.dialog({
            autoOpen: false,
            resizable: true,
            modal: true
        });

        item.dialog('open');
    };


    var destroyDialogOrPopup = function(id) {
        var item = null;
        if (typeof id === 'string') {
            var itemId = util.idAsSelector(id);
            item = $(itemId);
        }
        else {
            item = id;
        }

        if (item.popup) {
            item.popup('destroy').remove();
        }
        else {
            item.dialog('destroy').remove();
        }
    };

    var closeDialogOrPopup = function(id) {
        var item = null;
        if (typeof id === 'string') {
            var itemId = util.idAsSelector(id);
            item = $(itemId);
        }
        else {
            item = id;
        }

        if (item.popup) {
            item.popup('close');
        }
        else {
            item.dialog('close');
        }
    };

    return {
        open: function(id) {
            var itemId = util.idAsSelector(id);
            if ($(itemId).panel) {
                $(itemId).panel('open');
            }
            else {
                $(itemId).dialog('open');
            }
        },

        close: function(id) {
            var itemId = util.idAsSelector(id);
            if ($(itemId).panel) {
                $(itemId).panel('close');
            }
            else {
                $(itemId).dialog('close');
            }
        },

        init: function(id, width, height) {
            if (!ui.isMobile()) {
                $(util.idAsSelector(id)).dialog({
                    autoOpen: false,
                    height: height,
                    width: width,
                    modal: true
                });
            }
        },

        confirm: function(title, text, successFunction, iconClass) {
            if (util.isEmpty(iconClass)) {
                iconClass = '';
            }

            if (ui.isMobile()) {
                showConfirmPopup(title, text, successFunction, iconClass);
            }
            else {
                showConfirmDialog(title, text, successFunction, iconClass);
            }
        },



        showLoadingDialog: function(title, text, loadingImageClass) {
            if (util.isEmpty(text)) {
                text = LOADING_IMAGE;
            }

            if (util.isEmpty(loadingImageClass)) {
                loadingImageClass = 'loading-image';
            }

            var template = LOADING_DIALOG_TMPL;
            template = template.replace('{loadingImageClass}', loadingImageClass);
            template = template.replace('{title}', title);
            template = template.replace('{text}', text);

            var item = $(template);
            if (ui.isMobile()) {
                item.popup();
                item.popup('open');
            }
            else {
                item.dialog({
                    autoOpen: false,
                    closeOnEscape: false,
                    modal: true,
                    dialogClass: 'loading-dialog-contentpane',
                    height: 70,
                    open: function(event) {
                        $('.loading-dialog-contentpane .ui-dialog-titlebar-close').hide();
                        if (!title) {
                            $('.loading-dialog-contentpane .ui-dialog-titlebar').hide();
                        }
                    }
                });
                item.dialog('open');
            }
        },


        hideLoadingDialog: function() {
            closeDialogOrPopup('#my-loading-dialog');
            destroyDialogOrPopup('#my-loading-dialog');
        },


        alert: function(title, text, iconClass) {
            if (util.isEmpty(iconClass)) {
                iconClass = '';
            }

            var template = ALERT_DIALOG_TMPL;
            template = template.replace('{title}', title);
            template = template.replace('{text}', text);
            template = template.replace('{iconClass}', iconClass);

            if (ui.isMobile()) {
                var header = ALERT_DIALOG_HEADER_TMPL.replace('{title}', title);
                header = header.replace('{close}', CLOSE_BUTTON_TMPL);
                template = template.replace('{header}', header);

                var item = $(template);

                $('.header', item).click(function(eventObject) {
                    closeDialogOrPopup(item);
                    destroyDialogOrPopup(item);
                });

                item.popup();
                item.popup('open');
            }
            else {
                template = template.replace('{header}', '');
                template = template.replace('{close}', '');
                $(template).dialog();
            }
        }

    };


});
