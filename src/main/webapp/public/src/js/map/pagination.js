var $ = require('jquery');
var can = require('canjs/amd/can');

//var _instance = null;

var Pagination = can.Map({
    options: {
        active: true,
        page: 1,
        perPage: 5
    },
    meta: {
        pages: null
    }
});

//module.exports = Pagination;

module.exports = {
    _instance: null,
    getInstance: function () {
        if (!this._instance) {
            this._instance = new Pagination();
        }
        return this._instance;
    }
}
