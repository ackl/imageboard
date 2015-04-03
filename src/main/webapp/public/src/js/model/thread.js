var $ = require('jquery');
var can = require('canjs/amd/can');
var Post = require('./post');

Thread = Post.extend({
    findAll: function(attrs) {
        var queryString = '?replylimit='+attrs.options.replyLimit;
        if (attrs.options.active) {
            queryString += '&paginate=true';

            if (attrs.options.page) {
                queryString += '&page='+attrs.options.page;
            }
            if (attrs.options.perPage) {
                queryString += '&perpage='+attrs.options.perPage;
            }
            if (attrs.options.sortby) {
                queryString += '&sortby='+attrs.options.sortby;
            }
        }

        return $.ajax({
            url: '/api/threads'+queryString,
            cache: false,
            type: 'GET'
        });

    },

    findOne: 'GET /api/threads/{id}',

    create:  function(attrs) {
        var data = new FormData(attrs.form);
        return $.ajax({
            url: '/api/threads',
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST'
        });
    },

    getMetaInfo: function() {
        return $.get('/api/threads/meta');
    }
},{});

module.exports = Thread;
