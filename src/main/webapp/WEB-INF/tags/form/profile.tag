<%@tag description="Form for profile changes" pageEncoding="UTF-8"%>

<form class="edit-profile-form" method="POST" action="/users/profile/" enctype="multipart/form-data">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <div class="row">
        <label class="file">
            <span class="file__text">Choose profile image</span>
            <input type="file" name="file" tabindex="3">
        </label>
        <button class="new-thread-form__submit" tabindex="5">Submit Thread</button>
    </div>
</form>
