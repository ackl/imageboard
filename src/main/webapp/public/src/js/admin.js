var $ = require('jquery');
var can = require('canjs/amd/can');

var thingy = function() {
    console.log("saying someting");
}


module.exports = {init: thingy};
