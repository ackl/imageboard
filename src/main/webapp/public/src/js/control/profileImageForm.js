var $ = require('jquery');
var can = require('canjs/amd/can');

var ProfileImageForm = can.Control.extend({
    init: function(el, ev) {
        console.log(el, 'hi there');
    },

    'submit': function(el, ev) {
        ev.preventDefault();
        var data = new FormData(el[0]);
        $.ajax({
            url: '/users/'+location.href.split("/")[location.href.split("/").length-1],
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST'
        });
    }
});

module.exports = ProfileImageForm;
