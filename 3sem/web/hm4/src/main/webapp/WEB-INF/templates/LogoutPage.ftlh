<#import "common.ftl" as c>

<@c.page>
<div class="form-box">
    <div class="header">Logout</div>
    <div class="body">
        <form method="post" action="/logout">
            <input type="hidden" name="action" value="logout">
            <div class="button-field">
                <input type="submit" value="Logout">
            </div>
            <#if error??>
            <div class="error">
                ${error}
            </div>
            </#if>
        </form>
    </div>
</div>
</@c.page>
