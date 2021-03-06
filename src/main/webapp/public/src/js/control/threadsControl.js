var $ = require('jquery');
var can = require('canjs/amd/can');

var Thread = require('../model/thread');
var ThreadControl = require('./threadControl');
var HoverPreviewControl = require('./hoverPreviewControl');

require('foundation/js/foundation');
require('foundation/js/foundation/foundation.clearing.js');
//var Pagination = require('../map/pagination').getInstance();

var ThreadsControl = can.Control.extend({
    defaults : { view: 'threadTemplate', reply: 'replyTemplate' } }, {

    init: function(el, opts) {
        this.options = opts;
        this.getThreads();
    },

    '{Thread} created': 'getThreads',
    '{Pagination.options} changePage': function(el, ev) {
        console.log('pagination option changed');
        this.getThreads();
    },

    '.thread replied': 'getThreads',
    '{paginate} changePage': 'setFlag',
    setFlag: function() {
        this.changePage = true;
    },

    getAllThreads: function(attrs) {
        $('.tooltip').remove();
        $('.post-hover-preview').remove();
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
    /*
    * Retrieve all threads from server.
    * Data is stored in a can.List and pass to view.
    */
    getThreads: function() {
        var self = this;

        self.element.html("<div class='threads__loading'><img src='/public/dist/img/ring.svg' /></div>");
        self.element.toggleClass("loading");

        var Thread = require('../model/thread');
        //Thread.findAll(Pagination, function(threads) {
        self.getAllThreads(Pagination).then(function(threads) {
            threads.forEach(function(i) {
            });
            self.element.toggleClass("loading");
            self.element.empty();
            console.log(threads)
            can.each(threads, function(thread) {
                //console.log(thread)
                self.element.append(can.view(self.options.view, thread, {

                    formatDate: function(date) {
                        return new Date(date);
                    },

                    checkReplies: function(content) {
                        if (content.match(/(@\w*)/g)) {
                            var string = content.replace(/(@\w*)/g, function(match) {
                                return '<span class="hover-preview" data-post-id="'+match.substr(1)+'">'+match+'</span>';
                            });
                            return string;
                        } else {
                            return content;
                        }
                    },

                    replyCount: function(replyCount) {
                        if (parseInt(replyCount) > thread.replies.length) {
                            return (parseInt(replyCount) - thread.replies.length) + ' more replies hidden';
                        } else {
                            return '';
                        }
                    },

                    replyLink: function() {
                        return can.route.link(
                            "<button><i class='fa fa-eye'></i><span class='show-for-medium-up'>View</span></button>",
                            { id: thread.id },
                            {}, false );
                    }
                }));
                new ThreadControl(self.element.find('li.thread[data-post-id="' + thread.id + '"]'), {body: $('body'), thread: thread});
            });

            if (self.changePage) {
                //window.scrollTo(0,document.body.scrollHeight);
            }


            //self.element.find('.thread').each(function(i, el) {
                //new ThreadControl(el, {body: $('body')});
            //});

            new HoverPreviewControl(self.element);

            if (Pagination.options.active) {
                self.updateAmountOfPages();
            }

            self.element.trigger('threadsdone');
            self.element.foundation();
        });
    },

    updateAmountOfPages: can.compute(function() {
        Thread.getMetaInfo().then(function(metaInf) {
            var threadCount = metaInf.thread_count;

            var pagesCount = Math.ceil(threadCount / Pagination.attr('options.perPage'));

            if (Pagination.attr('options.page') > pagesCount) {
                Pagination.attr('options.page', pagesCount);
            }

            Pagination.attr('meta.pages', pagesCount);
        });
    })
});

module.exports = ThreadsControl;
