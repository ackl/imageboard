<%@tag description="Front end quickreply template" pageEncoding="UTF-8"%>

<script type="text/mustache" id="paginateTemplate">
<div class="pagination-centered">
    <ul class="pagination">
    <li class="paginate-controls__back fa fa-arrow-left {{#if disable.back}}unavailable{{/if}}"></li>
        {{#links}}
            <li {{#if current}}class="current"{{/if}}>{{{link}}}</li>
        {{/links}}
    <li class="paginate-controls__forward fa fa-arrow-right {{#if disable.forward}}unavailable{{/if}}"></li>
    </ul>
</div>
</script>

