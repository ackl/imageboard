var $ = require('jquery');
var can = require('canjs/amd/can');
var ProfileImageForm = require('./control/profileImageForm');
var HoverPreviewControl = require('./control/hoverPreviewControl');

var init = function() {
    new ProfileImageForm('form.edit-profile-form');
    new HoverPreviewControl('.profile-posts');
}


module.exports = {init: init};
