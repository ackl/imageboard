var $ = require('jquery');
var can = require('canjs/amd/can');

var ThreadForm = can.Control.extend({
    init: function(el, ev) {
    },

    'submit': function(el, ev) {
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
        if (!this.filename) {
            this.element.find('.file').addClass('error');
        } else {
            this.element.find('.file').removeClass('error');
        }

        if (threadContent && threadSubject && this.filename) {
            var data = new FormData(el[0]);

            var thread = new Thread({form: el[0]});
            thread.save();

            if (Pagination.attr('options.page') != 1) {
                can.route.attr({'page': '1'});
            }


            this.element.trigger('reset');
        }
    },

    '.file input change': function(el, ev) {
        var filename = el.val().replace(/^.*\\/, "");
        this.filename = filename;
        el.parent().text(filename);
    }
});

module.exports = ThreadForm;
