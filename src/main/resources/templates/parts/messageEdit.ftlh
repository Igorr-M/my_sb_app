<#assign formAction = "/main">
<#if userChannel??>
    <#if isEditMode?? && isEditMode>
        <#assign formAction = "/user-messages/${userChannel.id}">
    <#else>
        <#assign formAction = "/user-messages/${userChannel.id}/add">
    </#if>
</#if>

<#if !isEditMode?? || !isEditMode>
    <button class="btn btn-primary my-3" type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
        Add new message
    </button>
</#if>

<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div class="card card-body my-3">
        <form method="post" enctype="multipart/form-data" action="${formAction}">
            <div class="mb-3">
                <input type="text" name="text" class="form-control ${(textError??)?string('is-invalid', '')}" value="<#if message??>${message.text}</#if>" placeholder="Enter message" />
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="mb-3">
                <input type="text" value="<#if message??>${message.tag}</#if>" name="tag" class="form-control ${(tagError??)?string('is-invalid', '')}" placeholder="Enter tag" />
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <div class="mb-3">
                <label for="formFile" class="form-label">Choose picture</label>
                <input type="file" name="file" class="form-control" id="formFile" />
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <#if message??>
                <input type="hidden" name="id" value="${message.id}"/>
            </#if>
            <div class="mb-3">
                <#if isEditMode?? && isEditMode>
                    <button type="submit" class="btn btn-warning">Edit message</button>
                <#else>
                    <button type="submit" class="btn btn-primary">Add message</button>
                </#if>
            </div>
        </form>
    </div>
</div>