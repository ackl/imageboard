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
    },

    '{ThreadsControl} threadsdone': function(el, ev) {
    }
});

module.exports = PaginateControl;
