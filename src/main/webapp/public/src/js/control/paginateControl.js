var $ = require('jquery');
var can = require('canjs/amd/can');

//var Pagination = require('../map/pagination').getInstance();
var ThreadsControl = require('./threadsControl');

var PaginateControl = can.Control.extend({
    defaults : { view: 'paginateTemplate' } }, {

    init: function(el, opts) {
        this.options = opts;
        can.trigger(Pagination.attr('meta'), 'change');
    },

    '.paginate-controls__back click': function(el, ev) {
        if (Pagination.attr('options.page') != 1) {
            this.element.trigger('changePage');
            var toPage = parseInt(Pagination.attr('options.page')) - 1;
            can.route.attr({'perpage': Pagination.attr('options.perPage'), 'page': toPage});
            //Pagination.attr('options.page', parseInt(Pagination.attr('options.page'))-1);
        }
    },

    '.paginate-controls__forward click': function(el, ev) {
        if (Pagination.attr('options.page') != Pagination.attr('meta.pages')) {
            this.element.trigger('changePage');
            //Pagination.attr('options.page', parseInt(Pagination.attr('options.page'))+1);
            var toPage = parseInt(Pagination.attr('options.page')) + 1;
            can.route.attr({'perpage': Pagination.attr('options.perPage'), 'page': toPage});
            this.changedPage = true;
        }
    },

    '{Pagination.meta} change': function(el, ev) {
        var paginateLinks = [];
        for (var i=1; i<=Pagination.meta.pages; i++) {
            paginateLinks.push({
                link: can.route.link(i, {
                        page: i,
                        perpage: Pagination.attr('options.perPage')
                    }, {}, false ),
                current: Pagination.attr('options.page') == i
            });
        }

        var disable = {
            back: Pagination.attr('options.page') == 1,
            forward: Pagination.attr('options.page') == Pagination.attr('meta.pages')
        }

        this.element.html(can.view(this.options.view, { links: paginateLinks, disable: disable } ));
    },

    'a click': function(el, ev) {
        if (el.hasClass('disabled')) {
            ev.preventDefault();
        }
    }
});

module.exports = PaginateControl;
