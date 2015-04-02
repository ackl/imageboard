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

    '.thread__meta-info click': function(el, ev) {
        console.log('clicked metinf');
        this.element.find('.thread__reply--quick').click();
        this.element.find('textarea').val('@'+el.data('post-id'));
        this.element.find('textarea').focus();
    },

    'span.hover-preview mouseenter': function(el, ev) {
        var top = el.offset().top,
            left = el.offset().left + el.width() + 10,
            postId = el.data('post-id');

        if (!el.data('shown')) {
            $.get('/api/posts/'+postId).then(function(data) {
                data.top = top;
                data.left = left;
                var hoverView = can.view('postHoverPreview', data);
                $('body').append(hoverView);
                //$('body').append('<div class="post-hover-preview">'+data.content+'</div>');
            });

            el.data('shown', 'true');
        } else {
            $("[data-post-preview-id='"+el.data('post-id')+"']").removeClass('hide');
            $("[data-post-preview-id='"+el.data('post-id')+"']").css({
                top: top,
                left: left
            });
        }
    },

    'span.hover-preview mouseleave': function(el, ev) {
        $("[data-post-preview-id='"+el.data('post-id')+"']").addClass('hide');
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
