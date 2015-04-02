var $ = require('jquery');
var can = require('canjs/amd/can');

var HoverPreviewControl = can.Control.extend({
    init: function(el, ev) {
        //console.log(el, 'hi there');
        //el.find('a.hover-preview').each(function(i) {
            //var el = $(this);
        //});
    },

    'a.hover-preview mouseenter': function(el, ev) {
        if (!el.data('shown')) {
            $.get('/api/posts/'+el.data("post-id")).then(function(data) {
                el.siblings('.profile-posts__thread-preview').find('p').html(data.content)
                console.log(el.siblings('.profile-posts__thread-preview').find('p'))
            });
            el.data('shown', 'true');
        }

        var top = el.offset().top,
            left = el.offset().left + el.width() + 10;


        el.siblings('.profile-posts__thread-preview')
            .removeClass('hide')
            .css({
                'top': top,
                'left': left
            });
    },
    'a.hover-preview mouseleave': function(el, ev) {
        //el.siblings('.profile-posts__thread-preview').addClass('hide');
    }
});

module.exports = HoverPreviewControl;
