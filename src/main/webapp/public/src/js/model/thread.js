var $ = require('jquery');
var can = require('canjs/amd/can');
var Post = require('./post');

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

module.exports = Thread;
