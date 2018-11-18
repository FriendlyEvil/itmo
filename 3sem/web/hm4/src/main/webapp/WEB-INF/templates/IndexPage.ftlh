<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="newss" type="ru.itmo.webmail.model.domain.Pair[]" -->

<#import "common.ftl" as c>

<@c.page>
    <#if message??>
        <p>${message}</p>
    </#if>

<#--<h1>Welcome to the index page</h1>-->

    <#if newss??>
        <#list newss as n>
    <article>
        <div class="title">Title</div>
        <div class="information">By ${n.login}, 2 days ago, translation</div>
        <div class="news">
        <#list n.news.text as text>
            <p>${text}</p>
        </#list>
        </div>
        <ul class="attachment">
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
        </ul>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                2 days ago
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </article>
        </#list>
    </#if>

</@c.page>
