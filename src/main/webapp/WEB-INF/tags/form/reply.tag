<%@tag description="Form for new reply" pageEncoding="UTF-8"%>

<form class="new-reply-form">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <textarea id="" class="content" placeholder="Reply content here." name="" cols="30" rows="10"></textarea>
    <!--<input type="text" class="content">-->
    <button class="new-reply-form__submit">
        <i class="fa-reply fa"></i>
    </button>
</form>
