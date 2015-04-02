$ = require('jquery');
jQuery = require('jquery');

var Router = require('./router'),
    can = require('canjs/amd/can'),
    navbar = require('./navbar');

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
    navbar.init();

    var pageName = $('.page-meta-info').data("page-name");
    $(document).foundation();

    if (pageName === "index") {

        new Router($('.page'));
        can.route.ready();

    } else if (pageName === "admin") {

        var admin = require('./admin');
        admin.init();

    } else if (pageName === "profile") {

        var profile = require('./profile');
        profile.init();

    }
});
