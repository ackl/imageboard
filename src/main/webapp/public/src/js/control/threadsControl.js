var $ = require('jquery');
var can = require('canjs/amd/can');

var Thread = require('../model/thread');
var ThreadControl = require('./threadControl');

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
    '{Pagination.options} change': function(el, ev) {
        this.getThreads();
    },

    '.thread replied': 'getThreads',
    '{paginate} changePage': 'setFlag',
    setFlag: function() {
        this.changePage = true;
    },
    /*
    * Retrieve all threads from server.
    * Data is stored in a can.List and pass to view.
    */
    getThreads: function() {
        var self = this;

        self.element.html("<div class='threads__loading'><img src='/public/dist/img/ring.svg' /></div>");
        self.element.toggleClass("loading");

        Thread.findAll(Pagination, function(threads) {
            self.element.toggleClass("loading");
            self.element.empty();
            can.each(threads, function(thread) {
                self.element.append(can.view(self.options.view, thread, {

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

                    replyLink: function() {
                        return can.route.link(
                            "<button><i class='fa fa-eye'></i><span class='show-for-medium-up'>View</span></button>",
                            { id: thread.attr('id') },
                            {}, false );
                    }
                }));
            });

            if (self.changePage) {
                //window.scrollTo(0,document.body.scrollHeight);
            }


            self.element.find('.thread').each(function(i, el) {
                new ThreadControl(el, {body: $('body')});
            });

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
