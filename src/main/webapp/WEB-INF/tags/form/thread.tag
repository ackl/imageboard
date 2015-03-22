<%@tag description="Form for new thread" pageEncoding="UTF-8"%>

<form class="new-thread-form">
    <div class="columns small-12 medium-10">
        <input type="text" class="subject" placeholder="Thread subject here.">
        <!--<input type="text" class="content" placeholder="Thread content here.">-->
        <textarea id="new-thread-form__content" class="content" name="" cols="30" rows="10" placeholder="Thread content here."></textarea>
    </div>
    <div class="columns small-12 medium-2">
        <button class="new-thread-form__submit">Submit Thread</button>
    </div>
</form>
