<%@tag description="Form for new thread" pageEncoding="UTF-8"%>

<form class="new-thread-form" method="POST" action="api/threads/uploadFile" enctype="multipart/form-data">
    <div class="row">
        <div class="columns medium-9">
            Thread subject: <input type="text" class="subject" name="subject" placeholder="Thread subject here." tabindex="1">
            Thread content: <textarea id="new-thread-form__content" class="content" name="content" cols="30" rows="10" placeholder="Thread content here." tabindex="2"></textarea>
        </div>
        <div class="columns medium-3">
            File to upload: <input type="file" name="file" tabindex="3" class="file"><br />
            <button class="new-thread-form__submit" tabindex="5">Submit Thread</button>
        </div>
    </div>
</form>
