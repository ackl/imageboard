var $ = require('jquery');
var can = require('canjs/amd/can');

var Post = require('../model/post');

var ThreadControl = can.Control.extend({
    defaults : {
        view: {
            reply: 'replyPreviewTemplate',
            quickreply: 'replyTemplate'
        },

        sections: {
            replies: '.replies-preview',
            quickreply: '.quick-reply'
        }
    } }, {

    init: function(el, opts) {
        this.postId = el.data('post-id');
    },

    '.thread__reply click': function(el, ev) {
        can.route.attr('id', this.postId);
    },

    '.thread__reply--quick click': 'showQuickReply',
    '.quick-reply__close click': 'hideQuickReply',

    '.new-reply-form__submit click': function(el, ev) {
        ev.stopPropagation();
        ev.preventDefault();
        var threadContent = el.siblings('textarea').val(),
            csrf = el.siblings('input').val(),
            self = this;


        var reply = new Post({
            payload: {
                content: threadContent,
                parentId: this.postId
            },
            csrf: csrf
        });

        reply.save()
             .then(function(postData) {
                if (Pagination.attr('options.page') == 1) {
                    self.element.trigger('replied');
                } else {
                    can.route.attr({'page': '1'});
                }
             });

    },

    showQuickReply: function(el, ev) {
        if (!this.quickreply) {
            this.element.find('.thread__content--wrap').append(can.view(this.options.view.quickreply));
            this.toggleQuickReply();
        }
    },

    hideQuickReply: function(el, ev) {
        this.element.find('.quick-reply').remove();
        this.toggleQuickReply();
    },

    '{body} closed.fndtn.clearing': function(el, ev) {
        this.element.addClass('thumbnail');
    },

    '.th click': function(el, ev) {
        this.element.removeClass('thumbnail');
    },


    /**
     * Toggle this control's quickreply flag and disabled class of quickreply button
     */
    toggleQuickReply: function() {
        this.element.toggleClass('glow');
        this.element.find('button.thread__reply--quick').toggleClass('disabled');
        this.quickreply = !this.quickreply;
    }
});


module.exports = ThreadControl;
