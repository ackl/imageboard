var $ = require('jquery');
var can = require('canjs/amd/can');
var ProfileImageForm = require('./control/profileImageForm');

var createForm = function() {
    new ProfileImageForm('form.edit-profile-form');
    console.log("saying someting");
}


module.exports = {init: createForm};
