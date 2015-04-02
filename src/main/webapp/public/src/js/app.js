$ = require('jquery');
jQuery = require('jquery');

var Router = require('./router'),
    can = require('canjs/amd/can');

    require('foundation/js/foundation');
    require('foundation/js/foundation/foundation.topbar.js');
    require('foundation/js/foundation/foundation.clearing.js');
    require('foundation/js/foundation/foundation.tooltip.js');


//TODO: REMOVE FROM GLOBAL SCOPE
Pagination = new can.Map({
    options: {
        active: true,
        page: 1,
        perPage: 3
    },
    meta: {
        pages: null
    }
});

$(function() {
    var pageName = $('.page-meta-info').data("page-name");
        console.log(pageName);
    if (pageName === "index") {
        console.log("on index page");
        $(document).foundation();
        new Router($('.page'));
        can.route.ready();
    } else if (pageName === "admin") {
        var admin = require('./admin');
        admin.init();
    } else if (pageName === "profile") {
        console.log('on profile page');
        var profile = require('./profile');
        profile.init();
    }


    $('.top-bar .left a').click(function() {
        $('body').toggleClass('light');
        console.log('switching colour schemes');
    });

});
