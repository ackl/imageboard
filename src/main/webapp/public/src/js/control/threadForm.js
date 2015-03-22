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

        if (!threadSubject) {
            this.element.find('input.subject').addClass('error');
        } else {
            this.element.find('input.subject').removeClass('error');
        }

        if (!threadContent) {
            this.element.find('textarea.content').addClass('error');
        } else {
            this.element.find('textarea.content').removeClass('error');
        }

        if (threadContent && threadSubject) {
            var thread = new Thread({ content: threadContent, subject: threadSubject });
            can.route.attr({'page': '1'});
            thread.save();
            this.element.find('textarea.content').val('');
            this.element.find('input.subject').val('');
        }
    }
});

module.exports = ThreadForm;
