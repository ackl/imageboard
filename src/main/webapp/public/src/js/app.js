var Router = require('./router');
var $ = require('jquery');
var can = require('canjs/amd/can');
//var Pagination = require('./map/pagination');

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
});
