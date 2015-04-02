<%@tag description="Post hover preview template" pageEncoding="UTF-8"%>
<%@taglib prefix="form" tagdir="/WEB-INF/tags/form" %>

<script type="text/mustache" id="postHoverPreview">
<div class="post-hover-preview {{side}}"
    data-post-preview-id="{{id}}"
    style="top: {{top}}; left: {{left}}; right: {{right}}">
    <div class="preview-inline-wrap">
        {{content}}
    </div>
</div>
</script>

