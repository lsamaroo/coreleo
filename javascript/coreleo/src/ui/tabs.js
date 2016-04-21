/** 
 * Utilities for handling JQuery tabs.
 * @module tabs 
 */
define(function(require) {
    'use strict';

    var $ = require('$');
    var util = require('util');

    var tabCount = 1;

    var incrementTabCount = function() {
        tabCount++;
    };

    var decrementTabCount = function() {
        tabCount--;
    };

    // Workaround for "this" being undefined when used in the util object literal
    var getThis = function() {
        return module;
    };

    var module = /** @alias module:tabs */ {
        maxTabs: 12,

        isMaxNumTabsOpen: function() {
            return tabCount >= getThis().maxTabs;
        },

        addTab: function(tabContainerId, tabId, tabTitle, tabContent, showCloseIcon, closeTabText) {
            var tabTemplate = '<li><a id="tab-anchor-{id}" href="#{href}">{tabTitle}</a>{closeIcon}</li>';
            var closeIconTemplate = '<span tabIndex="0" class="ui-icon ui-icon-close">{closeText}</span>';

            if (showCloseIcon) {
                closeIconTemplate = closeIconTemplate.replace('{closeText}', closeTabText + ' ' + tabTitle);
                tabTemplate = tabTemplate.replace('{closeIcon}', closeIconTemplate);
            }
            else {
                tabTemplate = tabTemplate.replace('{closeIcon}', '');
            }

            var tabs = $(util.idAsSelector(tabContainerId));
            var li = $(tabTemplate.replace('{id}', tabId).replace('{href}', tabId).replace('{tabTitle}', tabTitle));
            tabs.find('.ui-tabs-nav').first().append(li);
            tabs.append('<div id="' + tabId + '"><p>' + tabContent + '</p></div>');
            getThis().refresh(tabContainerId);
            incrementTabCount();
        },

        addAjaxTab: function(tabContainerId, tabId, tabTitle, href, showCloseIcon, closeTabText) {
            var tabTemplate = '<li><a id="tab-anchor-{id}" href="{href}">{tabTitle}</a>{closeIcon}</li>';
            var closeIconTemplate = '<span tabIndex="0" class="ui-icon ui-icon-close">{closeText}</span>';

            if (showCloseIcon) {
                closeIconTemplate = closeIconTemplate.replace('{closeText}', closeTabText + ' ' + tabTitle);
                tabTemplate = tabTemplate.replace('{closeIcon}', closeIconTemplate);
            }
            else {
                tabTemplate = tabTemplate.replace('{closeIcon}', '');
            }

            var tabs = $(util.idAsSelector(tabContainerId));
            var li = $(tabTemplate.replace('{id}', tabId).replace('{href}', href).replace('{tabTitle}', tabTitle));
            tabs.find('.ui-tabs-nav').first().append(li);
            getThis().refresh(tabContainerId);
            incrementTabCount();
        },

        renameTab: function(tabContainerId, tabId, title) {
            tabContainerId = util.idAsSelector(tabContainerId);
            var tabAnchor = $(tabContainerId + ' a[id="tab-anchor-' + tabId + '"]');
            tabAnchor.html(title);
        },

        refresh: function(tabContainerId) {
            tabContainerId = util.idAsSelector(tabContainerId);
            var tabs = $(tabContainerId).tabs();
            tabs.tabs('refresh');
        },

        getTabIndexById: function(tabContainerId, tabId) {
            tabContainerId = util.idAsSelector(tabContainerId);
            var tabAnchor = $(tabContainerId + ' a[id="tab-anchor-' + tabId + '"]');
            if (tabAnchor.length === 0) {
                return -1;
            }

            return tabAnchor.parent().index();
        },

        focusTab: function(tabContainerId, tabId) {
            tabContainerId = util.idAsSelector(tabContainerId);
            var tabAnchor = $(tabContainerId + ' a[id="tab-anchor-' + tabId + '"]');
            tabAnchor.focus();
        },

        selectTab: function(tabContainerId, tabId) {
            tabContainerId = util.idAsSelector(tabContainerId);
            var tabIndex = getThis().getTabIndexById(tabContainerId, tabId);
            var tabs = $(tabContainerId).tabs();
            tabs.tabs('option', 'active', tabIndex);
        },

        addCloseFunctionToCloseIcon: function(tabContainerId) {
            tabContainerId = util.idAsSelector(tabContainerId);
            var tabs = $(tabContainerId).tabs();

            // close icon: removing the tab on click
            tabs.delegate('span.ui-icon-close', 'click', function() {
                var panelId = $(this).closest('li').remove().attr('aria-controls');
                $(util.idAsSelector(panelId)).remove();
                getThis().refresh(tabContainerId);
                decrementTabCount();
            });
        },


        closeTab: function(tabContainerId, tabId) {
            var panelId = $(tabContainerId + ' a[id="tab-anchor-' + tabId + '"]').closest('li').remove().attr('aria-controls');
            $('#' + panelId).remove();
            getThis().refresh(tabContainerId);
            decrementTabCount();
        },


        getSelectedTabIndex: function(tabContainerId) {
            tabContainerId = util.idAsSelector(tabContainerId);
            return $(tabContainerId).tabs('option', 'active');
        },

        getSelectedTabId: function(tabContainerId) {
            tabContainerId = util.idAsSelector(tabContainerId);
            var index = getThis().getSelectedTabIndex(tabContainerId);
            var id = ($(tabContainerId + ' ul>li a').eq(index).attr('href'));
            return util.startsWith(id, '#') ? id.substring(1, id.lenght) : id;
        }

    };

    return module;


});
