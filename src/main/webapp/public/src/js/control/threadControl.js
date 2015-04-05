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
        this.threadPage = $('.page').hasClass('thread-page');
    },

    '.thread__reply click': function(el, ev) {
        can.route.attr('id', this.postId);
    },

    '.thread__reply--quick click': 'showQuickReply',
    '.quick-reply__close click': 'hideQuickReply',

    //'.new-reply-form__submit click': function(el, ev) {
        //ev.stopPropagation();
        //ev.preventDefault();

        //var threadContent = el.siblings('textarea').val(),
            //csrf = el.siblings('input').val(),
            //self = this;


        //var reply = new Post({
            //payload: {
                //content: threadContent,
                //parentId: this.postId
            //},
            //csrf: csrf
        //});

        //reply.save()
             //.then(function(postData) {
                //if (Pagination.attr('options.page') == 1) {
                    //self.element.trigger('replied');
                //} else {
                    //can.route.attr({'page': '1'});
                //}
             //});

    //},

    'submit': function(el, ev) {
        ev.preventDefault();
        var self = this;
        this.element.find('input.parent_id').val(this.postId);
        var threadContent = this.element.find('textarea.content').val();
        var threadImageUrl = this.element.find('input.image_url').val();

        if (!threadContent) {
            this.element.find('textarea.content').addClass('error');
        } else {
            this.element.find('textarea.content').removeClass('error');
        }

        if (threadContent && (this.filename || threadImageUrl)) {
            var data = new FormData(el[0]);

            //var thread = new Thread({form: el[0]});
            var reply = new Post({form: this.element.find('form')[0]});
            //reply.save();
            reply.save()
                .then(function(postData) {
                    if (Pagination.attr('options.page') == 1) {
                        self.element.trigger('replied');
                    } else {
                        can.route.attr({'page': '1'});
                    }
                });
            this.element.trigger('reset');
            this.element.find('.file__text').text('Choose image');
        }
    },

    '.file input change': function(el, ev) {
        var filename = el.val().replace(/^.*\\/, "");
        this.filename = filename;
        el.siblings().text(filename);
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
        this.element.find('textarea').val('@' + el.data('post-id') + ' ');
        this.element.find('textarea').focus();
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
