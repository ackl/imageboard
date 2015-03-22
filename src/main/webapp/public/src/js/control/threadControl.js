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
        var threadContent = el.siblings().first().val(),
            self = this;

        var reply = new Post({ content: threadContent, parentId: this.postId });
        reply.save()
             .then(function(postData) {
                self.element.find('.replies-preview').append(can.view(self.options.view.reply, postData));
                self.hideQuickReply();
             });
    },

    showQuickReply: function(el, ev) {
        if (!this.quickreply) {
            this.element.append(can.view(this.options.view.quickreply));
            this.toggleQuickReply();
        }
    },

    hideQuickReply: function(el, ev) {
        this.element.find('.quick-reply').remove();
        this.toggleQuickReply();
    },

    /**
     * Toggle this control's quickreply flag and disabled class of quickreply button
     */
    toggleQuickReply: function() {
        this.element.find('button.thread__reply--quick').toggleClass('disabled');
        this.quickreply = !this.quickreply;
    }
});


module.exports = ThreadControl;
