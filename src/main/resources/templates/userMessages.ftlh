<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="container py-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>${userChannel.username}</h2>

            <#if !isCurrentUser>
                <#if isSubscriber>
                    <a href="/user/unsubscribe/${userChannel.id}" class="btn btn-outline-danger">
                        Unsubscribe
                    </a>
                <#else>
                    <a href="/user/subscribe/${userChannel.id}" class="btn btn-outline-primary">
                        Subscribe
                    </a>
                </#if>
            </#if>
        </div>

        <div class="row text-center mb-4">
            <div class="col-6">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h6 class="card-title">Subscriptions</h6>
                        <a href="/user/subscriptions/${userChannel.id}/list" class="stretched-link text-decoration-none">
                            <h4 class="mb-0">${subscriptionsCount}</h4>
                        </a>
                    </div>
                </div>
            </div>

            <div class="col-6">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h6 class="card-title">Subscribers</h6>
                        <a href="/user/subscribers/${userChannel.id}/list" class="stretched-link text-decoration-none">
                            <h4 class="mb-0">${subscribersCount}</h4>
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <#if isCurrentUser>
            <#include "parts/messageEdit.ftlh"/>
        </#if>

        <#include "parts/messageList.ftlh"/>
    </div>
</@c.page>
