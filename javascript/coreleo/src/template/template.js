/** 
 * Utilities for rendering handlebar templates.
 * @module template 
 */
define(function(require) {
    'use strict';

    var $ = require('$');
    var handlebars = require('handlebars');

    return {
        cache: {
            get: function(selector) {
                if (!this.templates) {
                    this.templates = {};
                }

                var template = this.templates[selector];
                if (!template) {
                    // pre compile the template
                    template = handlebars.compile($(selector).html());
                    this.templates[selector] = template;
                }
                return template;
            }
        },

        /**
         * This function will find the template using the given selector,
         * compile it with handlebars and cache it for future use.
         * 
         * @param {String} selector - the selector/id to the template html
         * @param {Object} data - the data values to use for the template
         * @return {String} the rendered template and data string
         * 
         */
        renderTemplateWithCaching: function(selector, data) {
            var render = this.cache.get(selector);
            return render(data);
        },

        /**
         * Combines the template and data to create a rendered String
         * @param {String} templateString - the template to render
         * @param {Object} data - the data values to use for the template
         * @return {String} the rendered template and data
         */
        renderTemplate: function(templateString, data) {
            var render = handlebars.compile(templateString);
            return render(data);
        }

    };
});
