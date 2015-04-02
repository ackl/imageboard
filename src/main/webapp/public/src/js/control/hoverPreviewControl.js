var $ = require('jquery');
var can = require('canjs/amd/can');

var HoverPreviewControl = can.Control.extend({
    init: function(el, ev) {
        //console.log(el, 'hi there');
        //el.find('a.hover-preview').each(function(i) {
            //var el = $(this);
        //});
    },

    //'a.hover-preview mouseenter': function(el, ev) {
        //if (!el.data('shown')) {
            //$.get('/api/posts/'+el.data("post-id")).then(function(data) {
                //el.siblings('.profile-posts__thread-preview').find('p').html(data.content)
                //console.log(el.siblings('.profile-posts__thread-preview').find('p'))
            //});
            //el.data('shown', 'true');
        //}

        //var top = el.offset().top - 10,
            //left = el.offset().left + el.width() + 10;


        //el.siblings('.profile-posts__thread-preview')
            //.removeClass('hide')
            //.css({
                //'top': top,
                //'left': left
            //});
    //},
    //'a.hover-preview mouseleave': function(el, ev) {
        //el.siblings('.profile-posts__thread-preview').addClass('hide');
    //},
    '.hover-preview mouseenter': function(el, ev) {
        var top = (el.offset().top - 10) + 'px',
            left = el.offset().left,
            postId = el.data('post-id'),
            side;

        if (left < $(window).width()/2) {
            //on the left of screen
            left += el.width() + 10;
            console.log(left);
            left += 'px';
            right = 'auto';
            side = 'right';
        } else {
            //on the right side of screen
            console.log($(window).width(), left, el.width());
            right = $(window).width() - left - el.width() + 35;
            right += 'px';
            left = 'auto';
            side = 'left';
        }

        if (!el.data('shown')) {
            $.get('/api/posts/'+postId).then(function(data) {
                data.top = top;
                data.left = left;
                data.right = right;
                data.side = side;
                var hoverView = can.view('postHoverPreview', data);
                $('body').append(hoverView);
                //$('body').append('<div class="post-hover-preview">'+data.content+'</div>');
            });

            el.data('shown', 'true');
        } else {
            $("[data-post-preview-id='"+el.data('post-id')+"']").removeClass('hide');
            $("[data-post-preview-id='"+el.data('post-id')+"']").css({
                top: top,
                left: left
            });
        }
    },

    '.hover-preview mouseleave': function(el, ev) {
        $("[data-post-preview-id='"+el.data('post-id')+"']").addClass('hide');
    }
});

module.exports = HoverPreviewControl;
