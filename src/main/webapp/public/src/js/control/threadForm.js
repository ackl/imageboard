var $ = require('jquery');
var can = require('canjs/amd/can');

var ThreadForm = can.Control.extend({
    init: function(el, ev) {
    },

    /**
     * Submit a new thread.
     */
    'button click': function(el, ev) {
        ev.stopPropagation();
        ev.preventDefault();

        var threadContent = this.element.find('textarea.content').val();
        var threadSubject = this.element.find('input.subject').val();

        if (threadContent) {
            var thread = new Thread({ content: threadContent, subject: threadSubject });
            thread.save();
            this.element.find('textarea.content').val('');
            this.element.find('input.subject').val('');
        }
    }
});

module.exports = ThreadForm;
