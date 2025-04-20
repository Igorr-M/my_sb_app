<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <#if RequestParameters.error??>
        <div class="alert alert-danger" role="alert">
            ${RequestParameters.error}
        </div>
    </#if>
    <#if message??>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>
<#--    ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}-->
    <#if RequestParameters.logout??>
        <div class="alert alert-info" role="alert">
            You have been logged out.
        </div>
    </#if>
<#--    ${message!}-->
    <@l.login "/login" false/>
</@c.page>