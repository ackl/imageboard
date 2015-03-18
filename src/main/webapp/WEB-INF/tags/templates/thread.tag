<%@tag description="Front end thread template" pageEncoding="UTF-8"%>

<script type="text/mustache" id="threadTemplate">
    {{#threads}}
        <li class="thread">
            {{content}}
            <p class="thread__timestamp">Posted at: {{formatDate date}}</p>
            <div class="thread__info">
                <p class="thread__info--id">postID: {{id}}</p>
                <p class="thread__info--parent-id">parentId: {{parentId}}</p>
            </div>

            <div class="thread__reply-buttons">
                {{#thread}}
                    <button class="thread__reply">Reply to thread</button>
                {{/thread}}
                    <button class="thread__reply--quick">Quick reply</button>
                    <button class="thread__reply--expand">Expand replies</button>
            </div>
        </li>
    {{/threads}}
</script>

