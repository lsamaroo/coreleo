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
                    // precompile the template
                    template = handlebars.compile($(selector).html());
                    this.templates[selector] = template;
                }
                return template;
            }
        },

        /**
         * @param {string} selector - the selector/id to the template html
         * @param {object} data - the data values to use for the template
         * 
         * This function will find the template using the given selector,
         * compile it with handlebars and cache it for future use.
         * 
         * @return the rendered template and data string
         * 
         */
        renderTemplateWithCaching: function(selector, data) {
            var render = this.cache.get(selector);
            return render(data);
        },

        /**
         * @param {string} templateString - the template to render
         * @param {object} data - the data values to use for the template
         */
        renderTemplate: function(templateString, data) {
            var render = handlebars.compile(templateString);
            return render(data);
        }

    };
});
