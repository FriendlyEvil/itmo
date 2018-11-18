<#-- @ftlvariable name="talks" type="ru.itmo.webmail.model.domain.Talk[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.webmail.model.domain.User" -->
<#import "common.ftl" as c>

<@c.page>

<div class="form-box">
    <div class="datatable">
        <div class="header">Talk</div>
        <div class="body">
            <form method="post" action="/talk">
                <input type="hidden" name="action" value="saveTalk">
                <div class="field">
                    <div class="name">
                        <label for="toUser">Login/Email</label>
                    </div>
                    <div class="value">
                        <input id="toUser" name="toUser"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text" title="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text"></textarea>
                    </div>
                </div>
                <div class="button-field">
                    <input type="submit" value="Enter">
                </div>
            <#if error??>
            <div class="error">
                ${error}
            </div>
            </#if>
            </form>
        </div>
    </div>
</div>

<div class="datatable">
    <div class="caption">Talks</div>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>To/From</th>
            <th>Text</th>
            <th>Time</th>
        </tr>
        </thead>
        <tbody>
        <#list talks as talk>
        <tr>
            <td>
                ${talk_index + 1}
            </td>
            <td>
            <#if user.id == talk.sourceUserId>
                To <b>${talk.targetLogin}</b>
            <#else>
                From <b>${talk.sourceLogin}</b>
            </#if>
            <td>
                ${talk.text}
            </td>
            <td>
                ${talk.creationTime}
            </td>
        </tr>
        </#list>
    </table>
</div>
</@c.page>
