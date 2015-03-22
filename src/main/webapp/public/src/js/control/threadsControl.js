var $ = require('jquery');
var can = require('canjs/amd/can');

var Thread = require('../model/thread');
var ThreadControl = require('./threadControl');
//var Pagination = require('../map/pagination').getInstance();

var ThreadsControl = can.Control.extend({
    defaults : { view: 'threadTemplate', reply: 'replyTemplate' } }, {

    init: function(el, opts) {
        this.options = opts;
        this.getThreads();
    },

    '{Thread} created': 'getThreads',
    '{Pagination.options} change': function(el, ev) {
        this.getThreads();
    },

    /*
    * Retrieve all threads from server.
    * Data is stored in a can.List and pass to view.
    */
    getThreads: function() {
        var self = this;

        Thread.findAll(Pagination, function(threads) {
            self.element.empty();

            can.each(threads, function(thread) {
                console.log(thread);
                self.element.append(can.view(self.options.view, thread, {

                    formatDate: function(date) {
                        return new Date(date());
                    },

                    replyLink: function() {
                        return can.route.link(
                            "<button>Reply</button>",
                            { id: thread.attr('id') },
                            {}, false );
                    }
                }));
            });

            self.element.find('.thread').each(function(i, el) {
                new ThreadControl(el);
            });

            if (Pagination.options.active) {
                self.updateAmountOfPages();
            }
        });

        can.trigger(this, 'threadsdone');
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
