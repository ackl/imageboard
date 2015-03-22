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
    findAll: function(attrs) {
        var queryString = '';
        if (attrs.options.active) {
            queryString += '?paginate=true';

            if (attrs.options.page) {
                queryString += '&page='+attrs.options.page;
            }
            if (attrs.options.perPage) {
                queryString += '&perpage='+attrs.options.perPage;
            }
        }
        console.log(queryString);

        return $.get('/api/threads'+queryString);
    },

    findOne: 'GET /api/threads/{id}',

    create:  function(attrs) {
        return $.ajax('/api/threads', {
            data : JSON.stringify(attrs),
            contentType : 'application/json',
            type : 'POST'
        });
    },

    getMetaInfo: function() {
        return $.get('/api/threads/meta');
    }
},{});


var Router = can.Control({
    'init': function(el, ev) {
        console.log(el);
    },

    displayHome: function() {
        this.element.html(can.view('threadListTemplate'));
        new ThreadForm('.new-thread-form');
        var threadsControl = new ThreadsControl('.threads');
        threadsControl.on("threadsdone", function(ev, value) {
            console.log('router heard some shit dude');
        });
        var paginateControl = new PaginateControl('.paginate-controls');
    },
    'route' : function(){
        this.displayHome();
        //this.element.html(can.view('threadListTemplate'));
        //new ThreadForm('.new-thread-form');
        //var threadsControl = new ThreadsControl('.threads');
        //var paginateControl = new PaginateControl('.paginate-controls');
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
    },

    'page/:page route': function(data) {
        this.displayHome();
        Pagination.attr('options.active', true);
        Pagination.attr('options.page', data.page);
        Pagination.attr('options.perPage', null);
    },

    'perpage/:perpage/page/:page route': function(data) {
        if (!$('.threads').length) {
            this.displayHome();
        }
        Pagination.attr('options.active', true);
        Pagination.attr('options.page', data.page);
        Pagination.attr('options.perPage', data.perpage);
        can.trigger(Pagination.attr('options'), 'change');
        can.trigger(Pagination.attr('meta'), 'change');
    }
});

Pagination = new can.Map({
    options: {
        active: true,
        page: 1,
        perPage: 5
    },
    meta: {
        pages: null
    }
});

updateAmountOfPages = can.compute(function() {
    Thread.getMetaInfo().then(function(metaInf) {
        var threadCount = metaInf.thread_count;

        var pagesCount = Math.ceil(threadCount / Pagination.attr('options.perPage'));

        if (Pagination.attr('options.page') > pagesCount) {
            Pagination.attr('options.page', pagesCount);
        }

        Pagination.attr('meta.pages', pagesCount);
    });
});

var PaginateControl = can.Control.extend({
    defaults : { view: 'paginateTemplate' } }, {

    init: function(el, opts) {
        this.options = opts;
        console.log('yoyo this is paginator');
        can.trigger(Pagination.attr('meta'), 'change');
    },

    '.paginate-controls__back click': function(el, ev) {
        if (Pagination.attr('options.page') != 1) {
            var toPage = parseInt(Pagination.attr('options.page')) - 1;
            can.route.attr({'perpage': Pagination.attr('options.perPage'), 'page': toPage});
            //Pagination.attr('options.page', parseInt(Pagination.attr('options.page'))-1);
        }
        window.scrollTo(0,document.body.scrollHeight);
    },

    '.paginate-controls__forward click': function(el, ev) {
        if (Pagination.attr('options.page') != Pagination.attr('meta.pages')) {
            //Pagination.attr('options.page', parseInt(Pagination.attr('options.page'))+1);
            var toPage = parseInt(Pagination.attr('options.page')) + 1;
            can.route.attr({'perpage': Pagination.attr('options.perPage'), 'page': toPage});
        }
        window.scrollTo(0,document.body.scrollHeight);
    },

    '{Pagination.meta} change': function(el, ev) {
        var paginateLinks = [];
        for (var i=1; i<=Pagination.meta.pages; i++) {
            paginateLinks.push({
                link: can.route.link(i, {
                        page: i,
                        perpage: Pagination.attr('options.perPage')
                    }, {}, false ),
                current: Pagination.attr('options.page') == i
            });
        }

        var disable = {
            back: Pagination.attr('options.page') == 1,
            forward: Pagination.attr('options.page') == Pagination.attr('meta.pages')
        }

        this.element.html(can.view(this.options.view, { links: paginateLinks, disable: disable } ));
    },

    'a click': function(el, ev) {
        if (el.hasClass('disabled')) {
            ev.preventDefault();
        }
    },

    '{ThreadsControl} threadsdone': function(el, ev) {
        console.log('paginate heard threads finishing');
        console.log(el, ev);
    }
});

var ThreadsControl = can.Control.extend({
    defaults : { view: 'threadTemplate', reply: 'replyTemplate' } }, {

    init: function(el, opts) {
        this.options = opts;
        this.getThreads();
    },

    '{Thread} created': 'getThreads',
    '{Pagination.options} change': function(el, ev) {
        this.getThreads();
    },

    /*
    * Retrieve all threads from server.
    * Data is stored in a can.List and pass to view.
    */
    getThreads: function() {
        var self = this;

        Thread.findAll(Pagination, function(threads) {
            self.element.empty();

            can.each(threads, function(thread) {
                self.element.append(can.view(self.options.view, thread, {

                    formatDate: function(date) {
                        return new Date(date());
                    },

                    replyLink: function() {
                        return can.route.link(
                            "<button>Reply</button>",
                            { id: thread.attr('id') },
                            {}, false );
                    }
                }));
            });

            self.element.find('.thread').each(function(i, el) {
                new ThreadControl(el);
            });

            if (Pagination.options.active) {
                console.log('paginate is on');
                updateAmountOfPages();
            }
        });

        console.log(this);
        can.trigger(this, 'threadsdone');
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


$(function() {
    new Router($('.page'));
    can.route.ready();
});
