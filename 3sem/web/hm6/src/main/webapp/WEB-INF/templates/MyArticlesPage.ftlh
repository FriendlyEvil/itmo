<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="articles" type="ru.itmo.webmail.model.domain.Article[]" -->

<#import "common.ftl" as c>

<@c.page>
<div class="datatable">
    <div class="caption">Talks</div>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Title</th>
            <th>Text</th>
            <th>Hidden</th>
        </tr>
        </thead>
        <tbody>
        <#list articles as article>
        <tr>
            <td>
                ${article_index + 1}
            </td>
            <td>
                ${article.title}
            <td>
                ${article.text}
            </td>

            <td class="hide" onclick="f_hide(event)">
                <#--<form>-->
                    <div class="button-field" >
                        <button class="text_value" onclick="f_hide(event)">
                        <#if article.hidden == true>
                            Show
                        <#else>
                            Hide
                        </#if>
                        </button>
                        <p style="display: none" class="id">${article.id}</p>
                    </div>
                <#--</form>-->
            </td>


        </tr>
        </#list>
    </table>
</div>

<script>
    var f_hide = function (event) {
        var el = $(event.target).closest(".button-field");
        var id = (el.find(".id")).text();
        var text_value = $(el.find(".text_value"));

        $.post("", {action: "changeHidden", id: id});
        if (text_value.text().indexOf("Hide") !== -1) {
            text_value.text("Show");
        } else {
            text_value.text("Hide");
        }
        return false;
    };

</script>
</@c.page>
