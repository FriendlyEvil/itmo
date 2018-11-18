<#import "common.ftl" as c>

<@c.page>
<div class="form-box">
    <div class="header">Enter</div>
    <div class="body">
        <form method="post" action="/news">
            <input type="hidden" name="action" value="addNews">
            <div class="field">
                <div class="name">
                    <label for="text" title="text">Text</label>
                </div>
                <div class="value">
                    <#--<input id="text" name="news" value="${text!}"/>-->
                    <textarea id="text" name="text" value="${text!}"></textarea>
                </div>
            </div>
            <div class="button-field">
                <input type="submit" value="Add news">
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
