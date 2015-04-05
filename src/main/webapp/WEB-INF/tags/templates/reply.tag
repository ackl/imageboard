<%@tag description="Front end quickreply template" pageEncoding="UTF-8"%>
<%@taglib prefix="form" tagdir="/WEB-INF/tags/form" %>

<script type="text/mustache" id="replyTemplateMain">
    <form:reply></form:reply>
    <button class="quick-reply__close">
        <i class="fa-times fa"></i>
    </button>
</script>

<script type="text/mustache" id="replyTemplate">
<div class="quick-reply">
    {{>replyTemplateMain}}
</div>
</script>

<script type="text/mustache" id="replyFullTemplate">
<div class="quick-reply">
    <h1>Full reply template</h1>
    {{> replyTemplateMain}}
</div>
</script>
