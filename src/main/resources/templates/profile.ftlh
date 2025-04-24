<#import "parts/common.ftlh" as c>

<@c.page>
    <h5>${username}</h5>
<#--    ${message!}-->
    <#if message??>
        <div class="alert alert-info" role="alert">
            ${message}
        </div>
    </#if>
    <form method="post">
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label"> Email: </label>
            <div class="col-sm-6">
                <input type="email" name="email" class="form-control" placeholder="some@some.com" value="${email!''}"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary ms-3" type="submit">Save</button>
    </form>
</@c.page>