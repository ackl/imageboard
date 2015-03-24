var Router = require('./router');
$ = require('jquery');
var can = require('canjs/amd/can');
//var Pagination = require('./map/pagination');

//TODO: REMOVE FROM GLOBAL SCOPE
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

$(function() {
    //new Pagination();
    new Router($('.page'));
    can.route.ready();

    /*$('.new-thread-form').submit(function(e) {*/
        /*e.preventDefault();*/
        /*var data = new FormData(this);*/
        /*console.log(data);*/
        /*$.ajax({*/
            /*url: 'api/threads/uploadFile',*/
            /*data: data,*/
            /*cache: false,*/
            /*contentType: false,*/
            /*processData: false,*/
            /*type: 'POST',*/
            /*success: function(data){*/
                /*console.log(data);*/
            /*}*/
        /*});*/
    /*});*/
});
