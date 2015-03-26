var Thread = require('./model/thread');
var ThreadForm = require('./control/threadForm');
var ThreadsControl = require('./control/threadsControl');
var PaginateControl = require('./control/paginateControl');
var $ = require('jquery');
var can = require('canjs/amd/can');
//var Pagination = require('./map/pagination').getInstance();

var Router = can.Control({
    'init': function(el, ev) {
    },

    displayHome: function() {
        this.element.html(can.view('threadListTemplate'));
        new ThreadForm('.new-thread-form');
        var paginateControl = new PaginateControl('.paginate-controls');
        var threadsControl = new ThreadsControl('.threads', {paginate: $('.paginate-controls')});
        //$('.threads').on("threadsdone", function(e) {
            //console.log('threads finished loading');
            //$('body,html').animate({
                //scrollTop: 400
            //}, 500);
        //});
        //threadsControl.on("threadsdone", function(ev, value) {
        //});
    },

    //'threadsdone': function(el, ev) {
        //console.log('hi');
        //$('body,html').animate({
            //scrollTop: 400
        //}, 500);
    //},

    'route' : function(){
        this.displayHome();
    },

    'dashboard route': function() {
        $(document.body).append("Dashboard");
    },

    'threads/:id route': function(data) {
        var self = this;
        this.element.empty();
        this.element.append('<h1>yo</h1>');
        Thread.findOne({id: data.id}).then(function(resp) {
            self.element.html(can.view('threadTemplate', resp));

        })
    },

    'page/:page route': function(data) {
        this.displayHome();
        Pagination.attr('options.active', true);
        Pagination.attr('options.page', data.page);
        Pagination.attr('options.perPage', null);
    },

    'perpage/:perpage/page/:page route': function(data) {
        if (!$('.threads').length) {
            this.displayHome();
        }
        Pagination.attr('options.active', true);
        Pagination.attr('options.page', data.page);
        Pagination.attr('options.perPage', data.perpage);
        can.trigger(Pagination.attr('options'), 'change');
        can.trigger(Pagination.attr('meta'), 'change');
    }
});

module.exports = Router;
