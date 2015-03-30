var $ = require('jquery');
var can = require('canjs/amd/can');

var Post = can.Model.extend({
    findAll: 'GET /api/posts',
    findOne: 'GET /api/posts/{id}',
    create:  function(attrs){
        return $.ajax('/api/posts?_csrf='+attrs.csrf, {
            data : JSON.stringify(attrs.payload),
            contentType : 'application/json',
            type : 'POST'
        });
    },
    update:  'PUT /api/posts/{id}',
    destroy: 'DELETE /api/posts/{id}'
},{});

module.exports = Post;
