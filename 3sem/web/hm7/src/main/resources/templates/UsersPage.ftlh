<#import "./common.ftlh" as c>

<@c.page>
<div class="datatable">
    <div class="caption">Users</div>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Login</th>
            <th>Created At</th>
            <th>Button</th>
        </tr>
        </thead>
        <tbody>
    <#list users as user>
    <tr>
        <td>${user.id}</td>
        <td><a href="/user/${user.id}">${user.login}</a></td>
        <td>${user.creationTime}</td>
        <#--<td><a href="/users/change/${user.id}">-->
            <#--<#if user.disabled>-->
                <#--Enable-->
            <#--<#else>-->
                <#--Disable-->
            <#--</#if>-->
        <#--</a></td>-->
        <td>
            <form method="post" action="/users/change/${user.id}">
                <div class="button-field">
                    <#if user.disabled>
                        <input type="submit" value="Enable">
                    <#else>
                        <input type="submit" value="Disable">
                    </#if>
                </div>
            </form>
        </td>
    </tr>
    </#list>
        </tbody>
    </table>
</div>
</@c.page>
