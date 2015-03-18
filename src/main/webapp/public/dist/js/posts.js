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


var getPosts = function() {
    Post.findAll({}, function(posts) {
        $('.posts').html(can.view('postTemplate', {posts: posts}, {
            formatDate: function(date) { return new Date(date()); }
        }));
    });
}



var PostsControl = can.Control.extend({
    //defaults are merged into the options arg provided to the constructor
    defaults : { view: 'postTemplate', reply: 'replyTemplate' } }, {
    init: function(el, opts) {
        this.options = opts;
        this.getPosts();
    },

    '{Post} created': 'getPosts',

    /*'.thread__reply--quick click': function(el, ev) {*/
        /*if (!el.hasClass('disabled')) {*/
            /*el.toggleClass('disabled');*/
            /*el.parent().parent().append(can.view(this.options.reply));*/
        /*}*/

        /*console.log('clicked quick reply button');*/
    /*},*/

    /**
    * Toggles disabled state of the quick reply button.
    */
    /*toggleQuickReply: function(el) {*/
        /*el.toggleClass('disabled');*/
    /*},*/

    /*
    * Retrieve all threads from server.
    * Data is stored in a can.List and pass to view.
    */
    getPosts: function() {
        var self = this;
        Post.findAll({}, function(posts) {
            self.element.html(can.view(self.options.view, {posts: posts}, {
                formatDate: function(date) { return new Date(date()); }
            }));


            /*new PostControl('.thread');*/
            $('.thread').each(function(i, el) {
                new PostControl(el);
            });
        });
    }
});

var PostControl = can.Control.extend({
    defaults : { reply: 'replyTemplate' } }, {
    init: function(el, opts) {
        console.log(el.find('button'));
    },

    '.thread__reply--quick click': 'showQuickReply',
    '.quick-reply__close click': 'hideQuickReply',
    '.new-reply-form__submit click': function(el, ev) {
        ev.stopPropagation();
        ev.preventDefault();
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

/*Post.bind('created', function( ev, todo ) {*/
    /*console.log(ev);*/
    /*getPosts();*/
/*});*/

var PostForm = can.Control.extend({
    init: function(el, ev) {
    },

    'button click': function(el, ev) {
        ev.stopPropagation();
        ev.preventDefault();

        var postContent = this.element.find('input.content').val();

        if (postContent) {
            var post = new Post({ content: postContent });
            post.save();
            this.element.find('input.content').val('');
        }
    }
});


$(function() {
    /*getPosts();*/
    new PostForm('.new-post-form');
    new PostsControl('.posts');
});
