<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="row g-3">
        <div class="col-md-5 mb-3">
            <form method="get" action="/main" class="row row-cols-lg-auto g-2 align-items-center">
                <div class="col-12">
                    <input type="text" name="filter" class="form-control" placeholder="Search by tag" value=${filter!}>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Search</button>
                </div>
            </form>
        </div>
    </div>

<#--    <a class="btn btn-primary my-3" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">-->
<#--        Add new message-->
<#--    </a>-->
    <button class="btn btn-primary my-3" type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
        Add new message
    </button>

    <div class="collapse <#if message??>show</#if>" id="collapseExample">
        <div class="card card-body my-3">
            <form method="post" enctype="multipart/form-data">
                <div class="mb-3">
                    <input type="text" name="text" class="form-control ${(textError??)?string('is-invalid', '')}" value="<#if message??>${message.text}</#if>" placeholder="Enter message">
                    <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>
                <div class="mb-3">
                    <input type="text" value="<#if message??>${message.tag}</#if>" name="tag" class="form-control ${(tagError??)?string('is-invalid', '')}" placeholder="Enter tag">
                    <#if tagError??>
                        <div class="invalid-feedback">
                            ${tagError}
                        </div>
                    </#if>
                </div>
                <div class="mb-3">
                    <label for="formFile" class="form-label">Choose picture</label>
                    <input type="file" name="file" class="form-control" id="formFile">
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="mb-3">
                    <button type="submit" class="btn btn-primary">Add</button>
                </div>
            </form>
        </div>
    </div>

    <div class="row row-cols-1 row-cols-md-3 g-4">
        <#list messages as message>
            <div class="col">
                <div class="card h-100">
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img-top">
                    </#if>
                    <div class="m-2">
                        <span>${message.text}</span>
                        <i>${message.tag}</i>
                    </div>
                    <div class="card-footer">
                        ${message.authorName}
                    </div>
                </div>
            </div>
        <#else>
            <div>No message</div>
        </#list>
    </div>
</@c.page>