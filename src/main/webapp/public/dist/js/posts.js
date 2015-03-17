var Post = can.Model.extend({
  findAll: 'GET /posts',
  findOne: 'GET /posts/{id}',
  //create:  'POST /posts',
  create:  function(attrs){
    return $.ajax('/posts', {
                data : JSON.stringify(attrs),
                contentType : 'application/json',
                type : 'POST'
            });
  },
  update:  'PUT /posts/{id}',
  destroy: 'DELETE /posts/{id}'
},{});


var getPosts = function() {
    Post.findAll({}, function(posts) {
        $('.posts').html(can.view('postTemplate', {posts: posts}, {
            formatDate: function(date) { return new Date(date()); }
        }));
    });
}

Post.bind('created', function( ev, todo ) {
    console.log(ev);
    getPosts();
});

$(function() {
    getPosts();

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

    new PostForm('.new-post-form');

    //$('.new-post-form__submit').on('click', function(e) {
    //})
});
