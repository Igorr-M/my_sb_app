<#include "security.ftlh">
<#import "pager.ftlh" as p>

<@p.pager url page />

<div class="row row-cols-1 row-cols-md-3 g-4" id="message-list">
    <#list page.content as message>
        <div class="col" data-id="${message.id}">
            <div class="card h-100 shadow-sm border-0 rounded-3">
                <#if message.filename??>
                    <img src="/img/${message.filename}" class="card-img-top" style="object-fit: cover; height: 200px;" />
                <#else>
                    <div class="card-img-top bg-secondary d-flex align-items-center justify-content-center text-white" style="height: 200px;">
                        <span>No image</span>
                    </div>
                </#if>
                <div class="m-2" style="font-size: 0.9rem;">
                    <span>${message.text}</span><br />
                    <i class="text-muted">#${message.tag}</i>
                </div>
                <div class="card-footer d-flex justify-content-between align-items-center">
                    <a href="/user-messages/${message.author.id}" class="text-decoration-none">
                        ${message.authorName}
                    </a>
                    <#if message.author.id == currentUserId>
                        <a class="btn btn-sm btn-outline-primary" href="/user-messages/${message.author.id}?message=${message.id}">
                            Edit
                        </a>
                    </#if>
                </div>
            </div>
        </div>
    <#else>
        <div>No message</div>
    </#list>
</div>

<@p.pager url page />