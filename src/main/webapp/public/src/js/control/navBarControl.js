var $ = require('jquery');
var can = require('canjs/amd/can');


var NavBarControl = can.Control.extend({
    init: function(el, opts) {
        this.csrf = this.element.find('form input[name="_csrf"]').val();
        var settings = Pagination.attr('options');

        console.log(el.find('.posts-per-page .' + settings.attr('perPage')));
        el.find('.posts-per-page .' + settings.attr('perPage')).addClass('disabled');
        el.find('.replies-per-thread .' + settings.attr('replyLimit')).addClass('disabled');
        //this.stickyNav = true;
    },

    '{Pagination.options} changePage': function(el, ev) {
        var settings = Pagination.attr('options');
        this.element.find('.settings-switch .item').removeClass('disabled');
        this.element.find('.posts-per-page .' + settings.attr('perPage')).addClass('disabled');
        this.element.find('.replies-per-thread .' + settings.attr('replyLimit')).addClass('disabled');
        if (settings.attr('sortby') == 'popularity') {
            this.element.find('.popularity').addClass('disabled');
        } else {
            this.element.find('.lastactive').addClass('disabled');
        }
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
