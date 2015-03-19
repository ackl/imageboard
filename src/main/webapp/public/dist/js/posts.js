var Post = can.Model.extend({
    findAll: 'GET /api/posts',
    findOne: 'GET /api/posts/{id}',
    create:  function(attrs){
        return $.ajax('/api/posts', {
            data : JSON.stringify(attrs),
            contentType : 'application/json',
            type : 'POST'
        });
    },
    update:  'PUT /api/posts/{id}',
    destroy: 'DELETE /api/posts/{id}'
},{});

Thread = Post.extend({
    findAll: 'GET /api/threads',
    findOne: 'GET /api/threads/{id}'
},{});


var Router = can.Control({
    'init': function(el, ev) {
        console.log(el);
    },
    'route' : function(){
        console.log('root');
        this.element.html(can.view('threadListTemplate'));
        new ThreadForm('.new-thread-form');
        var threadsControl = new ThreadsControl('.threads');
    },
    'dashboard route': function() {
        $(document.body).append("Dashboard");
    },

    'threads/:id route': function(data) {
        console.log('thread with id');
        var self = this;
        this.element.empty();
        this.element.append('<h1>yo</h1>');
        Thread.findOne({id: data.id}).then(function(resp) {
            console.log(resp);
            self.element.html(can.view('threadTemplate', resp));

        })
    }
});






var ThreadsControl = can.Control.extend({
    defaults : { view: 'threadTemplate', reply: 'replyTemplate' } }, {

    init: function(el, opts) {
        this.options = opts;
        this.getThreads();
    },

    '{Thread} created': 'getThreads',

    /*
    * Retrieve all threads from server.
    * Data is stored in a can.List and pass to view.
    */
    getThreads: function() {
        var self = this;
        Thread.findAll({}, function(threads) {
            console.log('threads: ', threads);
            can.each(threads, function(thread) {
                /*self.element.append(can.)*/
                self.element.append(can.view(self.options.view, thread, {
                    formatDate: function(date) { return new Date(date()); },
                    replyLink: function() {
                        return can.route.link( "<button>Reply</button>", { id: thread.attr('id')}, {}, false );
                    }
                }));
            });
            /*self.element.html(can.view(self.options.view, {threads: threads}, {*/
                /*formatDate: function(date) { return new Date(date()); },*/
                /*replyLink: function(postId) {*/
                    /*console.log(postId);*/
                    /*var link = can.route.link( "<button>Reply</button>", { id: postId}, {}, false );*/
                    /*console.log(link);*/
                    /*return link;*/
                /*}*/
            /*}));*/


            /*new ThreadControl('.thread');*/

            // Instantiate a ThreadControl for each thread on the page.
            /*$('.thread').each(function(i, el) {*/
                /*new ThreadControl(el);*/
            /*});*/
        });
    }
});

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


var ThreadForm = can.Control.extend({
    init: function(el, ev) {
    },

    /**
     * Submit a new thread.
     */
    'button click': function(el, ev) {
        ev.stopPropagation();
        ev.preventDefault();

        var threadContent = this.element.find('input.content').val();
        var threadSubject = this.element.find('input.subject').val();

        if (threadContent) {
            var thread = new Thread({ content: threadContent, subject: threadSubject });
            thread.save();
            this.element.find('input.content').val('');
            this.element.find('input.subject').val('');
        }
    }
});


$(function() {
    new Router($('.page'));
    can.route.ready();
});
