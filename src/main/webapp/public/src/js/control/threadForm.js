var $ = require('jquery');
var can = require('canjs/amd/can');

var ThreadForm = can.Control.extend({
    init: function(el, ev) {
        this.localImageSource = true;
    },

    'submit': function(el, ev) {
        ev.preventDefault();
        var threadContent = this.element.find('textarea.content').val();
        var threadSubject = this.element.find('input.subject').val();
        var threadImageUrl = this.element.find('input.image_url').val();

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
        if (!this.filename && !threadImageUrl) {
            this.element.find('.file').addClass('error');
        } else {
            this.element.find('.file').removeClass('error');
        }

        if (threadContent && threadSubject && (this.filename || threadImageUrl)) {
            var data = new FormData(el[0]);

            var thread = new Thread({form: el[0]});
            thread.save();

            if (Pagination.attr('options.page') != 1) {
                can.route.attr({'page': '1'});
            }


            this.element.trigger('reset');
            this.element.find('.file__text').text('Choose image');
        }
    },

    '.flipper__switch click': function(el, ev) {
        var $fileInput = this.element.find('.file input');
        var $imageUrlInput = this.element.find('input.image_url');

        ev.preventDefault();
        ev.stopPropagation();
        el.parent().toggleClass('flip');

        $fileInput.replaceWith($fileInput.val('').clone(true));
        this.element.find('.file__text').text('Choose image');
        $imageUrlInput.val('');
        this.filename = null;

        var tooltipTitle = this.localImageSource ?
            el.data('link-text') : el.data('upload-text');

        $('span[data-selector="flipper__switch"]').html(tooltipTitle);
        this.localImageSource = !this.localImageSource;
    },

    '.file input change': function(el, ev) {
        var filename = el.val().replace(/^.*\\/, "");
        if (filename) {
            this.filename = filename;
            el.siblings('.file__text').text(filename);
        }
    }
});

module.exports = ThreadForm;
