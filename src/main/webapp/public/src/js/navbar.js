var $ = require('jquery');
var can = require('canjs/amd/can');
var navBarControl = require('./control/navBarControl');

var init = function() {
    new navBarControl('nav');
}


module.exports = {init: init};
