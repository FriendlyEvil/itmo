<#import "./common.ftlh" as c>
<#import "/spring.ftl" as spring>

<#macro error field>
    <@spring.bind field/>
    <#list spring.status.errorMessages as error>
        <div class="error">${error}</div>
        <#break/>
    </#list>
</#macro>

<@c.page>
    <#if (notice_data)??>

        <div class="form-box">
            <div class="header">New Comment</div>
            <div class="body">
                <form method="post" action="/notice/comment/${notice_data.id}">
                    <div class="field">
                        <div class="name">
                            <label for="text">Text</label>
                        </div>
                        <div class="value">
                            <textarea id="text" rows="2" name="text"></textarea>
                        </div>
                        <#--<@error 'comment'/>-->
                    </div>

                    <div class="button-field">
                        <input type="submit" value="Add">
                    </div>
                </form>
            </div>
        </div>

        <div class="notice">
            <div class="header">
                <p>By <b>${notice_data.user.login}</b> at ${notice_data.creationTime}</p>
                    <#list notice_data.tags as tag>
                            <span class = "tag">&nbsp;<b>${tag.name}</b>&nbsp;</span>
                    </#list>
            </div>
            <p class="text">
                ${notice_data.text}
            </p>
                <h3>Comments:<br></h3>
                <#list notice_data.comments as comment>
                <div class="comment">
                    <div class="comment_header"><b>${comment.user.login}</b>
                        (${comment.creationTime!})
                    </div>
                    <div class="comment_text">${comment.text}</div>
                </div>
                </#list>


            <#else>
                <p>Notice Not Found</p>
            </#if>
        </div>
</@c.page>
