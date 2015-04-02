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
    }
});


module.exports = NavBarControl;
