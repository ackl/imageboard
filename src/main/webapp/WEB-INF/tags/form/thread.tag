<%@tag description="Form for new thread" pageEncoding="UTF-8"%>

<form class="new-thread-form" method="POST" action="api/threads/uploadFile" enctype="multipart/form-data">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <div class="row">
        <div class="columns medium-9">
            Thread subject: <input type="text" class="subject" name="subject" placeholder="Thread subject here." tabindex="1">

            Thread content: <textarea id="new-thread-form__content" class="content" name="content" cols="30" rows="10" placeholder="Thread content here." tabindex="2"></textarea>
        </div>
        <div class="columns medium-3">
        <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
            <div class="flipper">
                <div class="front">
                    <!-- front content -->
                    <label class="file" tabindex="3">
                        <span class="file__text">Choose image</span>
                        <input type="file" name="file">
                    </label>
                    <br />
                </div>
                <div class="back">
                    <label class="image_url__label">
                        <input type="text" class="image_url" name="image_url" placeholder="Remote image url." tabindex="4">
                    </label>
                    <!-- back content -->
                </div>
            </div>

            <button
                id="flipper__switch"
                class="flipper__switch has-tip tip-top"
                data-tooltip aria-haspopup="true"
                title="Switch to image url upload"
                data-upload-text="Switch to image url upload"
                data-link-text="Switch to local image upload">

                <i class="fa"></i>
            </button>

        </div>


            <button class="new-thread-form__submit" type="submit" tabindex="5">Submit Thread</button>
        </div>
    </div>
</form>

