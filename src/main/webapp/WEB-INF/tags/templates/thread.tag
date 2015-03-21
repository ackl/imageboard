<%@tag description="Front end thread template" pageEncoding="UTF-8"%>

<script type="text/mustache" id="threadsTemplate">
    {{#threads}}
        {{>threadTemplate}}
    {{/threads}}
</script>

<script type="text/mustache" id="threadTemplate">
    <li class="thread" data-post-id="{{id}}">
        <h2>{{subject}}</h2>
        <p>{{content}}</p>
        <p class="thread__timestamp">Posted at: {{formatDate date}}</p>
        <div class="thread__info">
            <p class="thread__info--id">postID: {{id}}</p>
            <p class="thread__info--parent-id">parentId: {{parentId}}</p>
        </div>

        <div class="thread__reply-buttons">
                {{{replyLink}}}
                <button class="thread__reply--quick">Quick reply</button>
                <button class="thread__reply--expand">Expand replies</button>
        </div>
        <div class="replies-preview">
            {{#replies}}
                {{>replyPreviewTemplate}}
            {{/replies}}
        </div>
    </li>
</script>

<script type="text/mustache" id="replyPreviewTemplate">
    <p class="replies-preview__reply">{{{content}}}</p>
</script>
