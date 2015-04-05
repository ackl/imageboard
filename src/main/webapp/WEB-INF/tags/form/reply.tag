<%@tag description="Form for new reply" pageEncoding="UTF-8"%>

<form class="new-reply-form" method="POST" action="api/posts/form" enctype="multipart/form-data">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="hidden" name="parent_id" value="" class="parent_id"/>
    <textarea id="" class="content" placeholder="Reply content here." name="content" cols="30" rows="10"></textarea>
    <label class="file" tabindex="3"><span class="file__text">Choose image</span><input type="file" name="file"></label><br />
    or
    <input type="text" class="image_url" name="image_url" placeholder="Remote image url." tabindex="4">
    <button class="new-reply-form__submit">
        <i class="fa-reply fa"></i>
    </button>
</form>
