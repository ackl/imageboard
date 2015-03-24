<%@tag description="Front end thread template" pageEncoding="UTF-8"%>

<script type="text/mustache" id="threadTemplate">
    <li class="thread" data-post-id="{{id}}">
        <div class="columns small-12">
            <h3>{{subject}}</h3>
            <div class="thread__meta-info">
                <span class="thread__timestamp">ID: {{id}} | </span>
                <span class="thread__timestamp">Time: {{formatDate date}}</span>
            </div>
        </div>
        <div class="columns small-12">
            <p>{{content}}</p>
        </div>

        {{#if image_url}}
        <div class="thread__image columns small-12 medium-2">
                <img src="{{image_url}}" alt="">
        </div>
        <div class="replies-preview columns small-12 medium-10">
            {{#replies}}
                <div class="replies-preview__reply">
                    {{>replyPreviewTemplate}}
                </div>
            {{/replies}}
        </div>
        {{/if}}

        {{^if image_url}}
        <div class="replies-preview columns small-12 medium-9">
            {{#replies}}
                <div class="replies-preview__reply">
                    {{>replyPreviewTemplate}}
                </div>
            {{/replies}}
        </div>
        {{/if}}

        <div class="thread__reply-buttons">
                {{{replyLink}}}
                <button class="thread__reply--quick">
                    <i class="fa fa-reply"></i>
                    <span class=""> Reply </span>
                </button>
        </div>

        <%-- Dev helper --%>
        <div class="thread__info">
            <p class="thread__info--id">postID: {{id}}</p>
        </div>

    </li>
</script>

<script type="text/mustache" id="replyPreviewTemplate">
    <p class="reply__timestamp">Posted at: {{formatDate date}}</p>
    <p class="reply__content">{{{content}}}</p>
    <div class="post__info">
        <p class="post__info--id">postID: {{id}}</p>
    </div>
</script>
