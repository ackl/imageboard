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

var Thread = Post.extend({
    findAll: 'GET /api/posts/threads',
},{});


var ThreadsControl = can.Control.extend({
    //defaults are merged into the options arg provided to the constructor
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
            self.element.html(can.view(self.options.view, {threads: threads}, {
                formatDate: function(date) { return new Date(date()); }
            }));


            /*new ThreadControl('.thread');*/
            $('.thread').each(function(i, el) {
                new ThreadControl(el);
            });
        });
    }
});

var ThreadControl = can.Control.extend({
    defaults : { reply: 'replyTemplate' } }, {
    init: function(el, opts) {
        console.log(el.find('button'));
    },

    '.thread__reply--quick click': 'showQuickReply',
    '.quick-reply__close click': 'hideQuickReply',
    '.new-reply-form__submit click': function(el, ev) {
        ev.stopPropagation();
        ev.preventDefault();
        var threadContent = el.siblings().first().val(),
            parentId = this.element.data('post-id');

        var reply = new Post({ content: threadContent, parentId: parentId });
        reply.save();
    },

    showQuickReply: function(el, ev) {
        if (!this.quickreply) {
            this.element.append(can.view(this.options.reply));
            this.toggleQuickReply();
        }
    },

    hideQuickReply: function(el, ev) {
        el.parent().remove();
        this.toggleQuickReply();
    },

    toggleQuickReply: function() {
        this.element.find('button.thread__reply--quick').toggleClass('disabled');
        this.quickreply = !this.quickreply;
    }
});

var ThreadForm = can.Control.extend({
    init: function(el, ev) {
    },

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
    new ThreadForm('.new-thread-form');
    new ThreadsControl('.threads');
});
