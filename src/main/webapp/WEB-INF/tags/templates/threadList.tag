<%@tag description="Front end thread template" pageEncoding="UTF-8"%>
<%@taglib prefix="form" tagdir="/WEB-INF/tags/form" %>

<script type="text/mustache" id="threadListTemplate">
<div class="row thread-list-wrap">
    <div class="columns small-12 medium-9">
        <form:thread></form:thread>
    </div>
    <div class="columns small-12 medium-3">
        <div class="thread-list__info"></div>
    </div>
    <div class="columns small-12">
        <ul id="thread-list" class="threads"></ul>
    </div>
    <div class="paginate-controls columns small-12"></div>
</div>
</script>
