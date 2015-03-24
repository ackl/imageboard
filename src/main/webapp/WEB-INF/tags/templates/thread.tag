<%@tag description="Front end thread template" pageEncoding="UTF-8"%>

<script type="text/mustache" id="threadsTemplate">
    {{#threads}}
        {{>threadTemplate}}
    {{/threads}}
</script>

<script type="text/mustache" id="threadTemplate">
    <li class="thread row" data-post-id="{{id}}">
        <div class="columns small-12">
            <h3>{{subject}}</h3>
        </div>
        <div class="columns small-12">
            <p>{{content}}</p>
            <p class="thread__timestamp">Posted at: {{formatDate date}}</p>
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
        <div class="replies-preview columns small-12">
            {{#replies}}
                <div class="replies-preview__reply">
                    {{>replyPreviewTemplate}}
                </div>
            {{/replies}}
        </div>
        {{/if}}

        <div class="thread__reply-buttons">
                {{{replyLink}}}
                <button class="thread__reply--quick">Quick reply</button>
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
</script>
