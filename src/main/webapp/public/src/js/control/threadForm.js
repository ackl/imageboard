var $ = require('jquery');
var can = require('canjs/amd/can');

var ThreadForm = can.Control.extend({
    init: function(el, ev) {
    },

    'submit': function(el, ev) {
        console.log('submite from control');
        ev.preventDefault();
        var threadContent = this.element.find('textarea.content').val();
        var threadImage = this.element.find('input.file').val();
        var threadSubject = this.element.find('input.subject').val();

        if (!threadSubject) {
            this.element.find('input.subject').addClass('error');
        } else {
            this.element.find('input.subject').removeClass('error');
        }

        if (!threadImage) {
            this.element.find('input.file').addClass('error');
        } else {
            this.element.find('input.file').removeClass('error');
        }

        if (!threadContent) {
            this.element.find('textarea.content').addClass('error');
        } else {
            this.element.find('textarea.content').removeClass('error');
        }
        if (threadContent && threadSubject && threadImage) {
            var data = new FormData(el[0]);
            console.log(data);

            var thread = new Thread({form: el[0]});
            thread.save();

            if (Pagination.attr('options.page') != 1) {
                can.route.attr({'page': '1'});
            }


            this.element.trigger('reset');
        }
    }
});

module.exports = ThreadForm;
