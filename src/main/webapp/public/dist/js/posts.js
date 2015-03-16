var getPosts = function() {
    $.get('/posts')
    .then(function(posts) {
        $('.posts').empty();
        posts.forEach(function(post) {
            $('.posts').append('<li>'
                + post.content
                + '<p class="post__timestamp">Posted at: '
                + new Date(post.date)
                + '</p></li>');
        });
    });
}

$(function() {
    getPosts();

    $('.new-post-form__submit').on('click', function(e) {
        e.preventDefault();
        var $postInput = $('.new-post-form input'),
            postContent = $postInput.val();

        if (postContent) {
            var postData = { content: postContent };
            $.ajax('http://localhost:8080/posts', {
                data : JSON.stringify(postData),
                contentType : 'application/json',
                type : 'POST'
            }).then(function(data) {
                console.log(data);
                getPosts();
                $postInput.val('');
            });
        }
    })
});
