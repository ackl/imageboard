var $ = require('jquery');
var can = require('canjs/amd/can');


var NavBarControl = can.Control.extend({
    init: function(el, opts) {
        this.csrf = this.element.find('form input[name="_csrf"]').val();
        //this.stickyNav = true;
    },

    'a.colour-scheme-switch click': function(el, ev) {
        this.scheme = $('body').hasClass('light') ?
            'dark' : 'light';

        $.ajax({
            url: '/users/colour?colour='+this.scheme,
            data: {'_csrf': this.csrf},
            type: 'POST'
        }).then(function(data) {
            console.log(data);
        });

        $('body').toggleClass('light');
    },

    'a.anchor-nav-switch click': function(el, ev) {
        ev.stopPropagation();
        ev.preventDefault();
        if (this.stickyNav) {
            $('body').removeClass('f-topbar-fixed');
            $('.nav__wrap').removeClass('sticky');
            $('.nav__wrap').removeClass('fixed');
        } else {
            $('body').addClass('f-topbar-fixed');
            $('.nav__wrap').addClass('sticky');
            $('.nav__wrap').addClass('fixed');
        }

        this.stickyNav = !this.stickyNav;
    },

    triggerRender: function() {
        can.trigger(Pagination.attr('options'), 'changePage');
        can.trigger(Pagination.attr('meta'), 'change');
    },

    '.posts-per-page .item click': function(el, ev) {
        if (Pagination.attr('options.perPage') != parseInt(el.html())) {
            Pagination.attr('options.perPage', parseInt(el.html()))
            this.triggerRender();
        }
    },

    '.replies-per-thread .item click': function(el, ev) {
        if (Pagination.attr('options.replyLimit') != parseInt(el.html())) {
            Pagination.attr('options.replyLimit', parseInt(el.html()))
            this.triggerRender();
        }
    },

    '.sort-threads-by .item click': function(el, ev) {
        if (Pagination.attr('options.sortby') != el.data('param')) {
            Pagination.attr('options.sortby', el.data('param'));
            this.triggerRender();
        }
    }
});


module.exports = NavBarControl;
