<%@tag description="Front end thread template" pageEncoding="UTF-8"%>

<script type="text/mustache" id="threadTemplate">
    <li class="thread thumbnail" data-post-id="{{id}}">
        <div class="columns small-12">
            <h3>
                {{subject}}
                <div class="thread__reply-buttons">
                        {{{replyLink}}}
                        <button class="thread__reply--quick">
                            <i class="fa fa-reply"></i>
                            <span class=""> Reply </span>
                        </button>
                </div>
            </h3>
            <div class="thread__meta-info" data-post-id="{{id}}">
                <span class="thread__timestamp">ID: {{id}} | </span>
                <span class="thread__timestamp">Time: {{formatDate date}}</span>
            </div>
        </div>
        <div class="columns small-12 thread__content--wrap">
            <div class="thread__content">
                <p>{{content}}</p>
            </div>
        </div>

        {{#if image_url}}
        <div class="thread__image columns small-12 medium-4">
            <ul class="clearing-thumbs" data-clearing>
                <li><a class="th" href="{{image_url}}"><img src="{{image_url}}"></a></li>
            </ul>
        </div>
            {{#if replies.length}}
            <div class="replies-preview columns small-12 medium-8">
                {{#replies}}
                    <div class="replies-preview__reply">
                        {{>replyPreviewTemplate}}
                    </div>
                {{/replies}}
            </div>
            {{/if}}
        {{/if}}

        {{^if image_url}}
            {{#if replies.length}}
            <div class="replies-preview columns small-12 medium-9">
                {{#replies}}
                    <div class="replies-preview__reply">
                        {{>replyPreviewTemplate}}
                    </div>
                {{/replies}}
            </div>
            {{/if}}
        {{/if}}
        <div class="avatar">
            <a class="has-tip tip-top" href="/users/profile/{{user.username}}"
                data-tooltip aria-haspopup="true"
                data-options="disable_for_touch:true"
                title="{{user.username}}">
                <img src="{{user.image_url}}" alt="">
            </a>
        </div>

        <%-- Dev helper --%>
        <!--<div class="thread__info">-->
            <!--<p class="thread__info--id">postID: {{id}}</p>-->
            <!--<p class="thread__info--id">userID: {{user_id}}</p>-->
        <!--</div>-->

    </li>
</script>

<script type="text/mustache" id="replyPreviewTemplate">
    <div class="thread__meta-info" data-post-id="{{id}}">
        <span class="reply__timestamp">ID: {{id}} | </span>
        <span class="reply__timestamp">Posted at: {{formatDate date}}</span>
    </div>

    <p class="reply__content"> {{{checkReplies content}}} </p>
    <div data-tooltip aria-haspopup="true"
        class="has-tip avatar tip-top"
        data-options="disable_for_touch:true"
        title="{{user.username}}">
        <a href="/users/profile/{{user.username}}">
            <img src="{{user.image_url}}" alt="">
        </a>
    </div>
</script>
