<#import "parts/common.ftlh" as c>

<@c.page>
    <h2 class="my-4">List of users</h2>

    <table class="table table-striped table-bordered align-middle">
        <thead class="table-light">
        <tr>
            <th style="width: 40%;">Name</th>
            <th style="width: 40%;">Role</th>
            <th style="width: 20%;"></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td>
                    <a href="/user/${user.id}" class="btn btn-sm btn-outline-primary">Edit</a>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>
