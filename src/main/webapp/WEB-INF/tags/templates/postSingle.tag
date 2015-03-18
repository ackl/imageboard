<%@tag description="Front end post template" pageEncoding="UTF-8"%>

<script type="text/mustache" id="postSingleTemplate">
        <li>
            {{post.content}}
            <p class="post__timestamp">Posted at: {{formatDate post.date}}</p>
            <div class="post__info">
                <p class="post__info--id">postID: {{id}}</p>
                <p class="post__info--parent-id">parentId: {{post.parentId}}</p>
            </div>

            <div class="thread__reply-buttons">
                {{#post.thread}}
                    <button class="thread__reply">Reply to thread</button>
                {{/post.thread}}
                    <button class="thread__reply--quick">Quick reply</button>
                    <button class="thread__reply--expand">Expand replies</button>
            </div>
        </li>
</script>

