var Thread = require('./model/thread');
var ThreadForm = require('./control/threadForm');
var ThreadControl = require('./control/threadControl');
var ThreadsControl = require('./control/threadsControl');
var PaginateControl = require('./control/paginateControl');
var $ = require('jquery');
var can = require('canjs/amd/can');
//var Pagination = require('./map/pagination').getInstance();

var Router = can.Control({
    'init': function(el, ev) {
    },

    displayHome: function() {
        this.element.addClass('threads-page');
        this.element.html(can.view('threadListTemplate'));
        new ThreadForm('.new-thread-form');
        var paginateControl = new PaginateControl('.paginate-controls');
        var threadsControl = new ThreadsControl('.threads', {paginate: $('.paginate-controls')});
    },

    'route' : function(){
        this.displayHome();
        this.element.removeClass('thread-page');
    },

    'dashboard route': function() {
        $(document.body).append("Dashboard");
    },

    'threads/:id route': function(data) {
        var self = this;
        this.element.empty();
        this.element.removeClass('threads-page');
        this.element.addClass('thread-page');
        Thread.findOne({id: data.id}).then(function(resp) {
            //self.element.html(can.view('threadTemplate', resp));
            resp.attr('threadPage', 'hello');
            console.log(resp);
            self.element.append(can.view('threadTemplate', resp, {

                formatDate: function(date) {
                    return new Date(date());
                },

                checkReplies: function(content) {
                    if (content().match(/(@\w*)/g)) {
                        var string = content().replace(/(@\w*)/g, function(match) {
                            return '<span class="hover-preview" data-post-id="'+match.substr(1)+'">'+match+'</span>';
                        });
                        return string;
                    } else {
                        return content();
                    }
                },

                replyCount: function(replyCount) {
                    if (parseInt(replyCount) > resp.replies.length) {
                        return (parseInt(replyCount) - resp.replies.length) + ' more replies hidden';
                    } else {
                        return '';
                    }
                },

                replyLink: function() {
                    return can.route.link(
                        "<button><i class='fa fa-eye'></i><span class='show-for-medium-up'>View</span></button>",
                        { id: resp.id },
                        {}, false );
                }
            }));

        self.element.find('.thread').each(function(i, el) {
            new ThreadControl(el, {body: $('body'), view: {
                quickreply: 'replyFullTemplate',
                reply: 'replyPreviewTemplate',
            }});
        });

        });
    },

    //'page/:page route': function(data) {
        //this.displayHome();
        //Pagination.attr('options.active', true);
        //Pagination.attr('options.page', data.page);
        //Pagination.attr('options.perPage', null);
        //can.trigger(Pagination.attr('options'), 'changePage');
    //},

    'perpage/:perpage/page/:page route': function(data) {
        if (!$('.threads').length) {
            this.displayHome();
        }
        Pagination.attr('options.active', true);
        Pagination.attr('options.page', data.page);
        Pagination.attr('options.perPage', data.perpage);
        can.trigger(Pagination.attr('options'), 'changePage');
        can.trigger(Pagination.attr('meta'), 'change');
    }
});

module.exports = Router;
