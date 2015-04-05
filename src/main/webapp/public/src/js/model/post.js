var $ = require('jquery');
var can = require('canjs/amd/can');

var Post = can.Model.extend({
    findAll: 'GET /api/posts',
    findOne: 'GET /api/posts/{id}',
    //create:  function(attrs){
        //return $.ajax('/api/posts?_csrf='+attrs.csrf, {
            //data : JSON.stringify(attrs.payload),
            //contentType : 'application/json',
            //type : 'POST'
        //});
    //},
    create:  function(attrs) {
        console.log('creatin gpsotçA;');
        var data = new FormData(attrs.form);
        console.log(attrs.form);

        return $.ajax({
            url: '/api/posts',
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST'
        });
    },
    update:  'PUT /api/posts/{id}',
    destroy: 'DELETE /api/posts/{id}'
},{});

module.exports = Post;
