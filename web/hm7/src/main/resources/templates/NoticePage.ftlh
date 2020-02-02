<#import "/spring.ftl" as spring>
<#import "./common.ftlh" as c>

<#macro error field>
    <@spring.bind field/>
    <#list spring.status.errorMessages as error>
        <div class="error">${error}</div>
        <#break/>
    </#list>
</#macro>

<@c.page>
<div class="form-box">
    <div class="header">Enter</div>
    <div class="body">
        <form method="post" action="/notices">
            <div class="field">
                <div class="name">
                    <label for="content">Text</label>
                </div>
                <div class="value">
                    <textarea id="content" name="content" value="${content!}"></textarea>
                </div>
            </div>
            <@error 'registerForm.content'/>
            <div class="button-field">
                <input type="submit" value="Create">
            </div>
            <div class="error"></div>
        </form>
    </div>
</div>
</@c.page>
