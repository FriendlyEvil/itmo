<#import "./common.ftlh" as c>

<@c.page>
    <#if (user_data)??>
        <p>Id = <b>${user_data.id}</b></p>
        <p>Login = <b>${user_data.login}</b></p>
        <p>Creation time = <b>${user_data.creationTime}</b></p>
    <#else>
        <p>User Not Found</p>
    </#if>
</@c.page>
