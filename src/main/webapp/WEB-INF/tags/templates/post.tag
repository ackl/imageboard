<%@tag description="Front end post template" pageEncoding="UTF-8"%>

<script type="text/mustache" id="postTemplate">
    {{#posts}}
        <li class="thread">
            {{content}}
            <p class="post__timestamp">Posted at: {{formatDate date}}</p>
            <div class="post__info">
                <p class="post__info--id">postID: {{id}}</p>
                <p class="post__info--parent-id">parentId: {{parentId}}</p>
            </div>

            <div class="thread__reply-buttons">
                {{#thread}}
                    <button class="thread__reply">Reply to thread</button>
                {{/thread}}
                    <button class="thread__reply--quick">Quick reply</button>
                    <button class="thread__reply--expand">Expand replies</button>
            </div>
        </li>
    {{/posts}}
</script>

