<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div class="fw-bold fs-3 mb-3">Add new user</div>
    ${message!}
    <@l.login "/registration" true/>
</@c.page>