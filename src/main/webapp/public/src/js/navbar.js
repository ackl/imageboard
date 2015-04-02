var $ = require('jquery');
var can = require('canjs/amd/can');
var navBarControl = require('./control/navBarControl');

var init = function() {
    console.log('yo');
    new navBarControl('nav');
}


module.exports = {init: init};
