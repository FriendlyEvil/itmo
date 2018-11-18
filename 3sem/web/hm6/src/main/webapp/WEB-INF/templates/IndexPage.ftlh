<#-- @ftlvariable name="message" type="java.lang.String" -->

<#import "common.ftl" as c>

<@c.page>
    <#if message??>
        <p>${message}</p>
    </#if>
<ul class="articles no-data">
</ul>

<template class="articles_list">
    <li>
        <article>
            <div class="title">Title</div>
            <div class="information">By <b class="login"></b>, <span class="article_time"></span></div>
            <div class="news">
            <#--<p class="text"></p>-->
                <ul class="text">
                </ul>
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
                    <span class="article_time"></span>
                    <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                    <a href="#">68</a>
                </div>
            </div>
        </article>
    </li>
</template>

<template class="article_text">
    <li>
        <p class="text"></p>
    </li>
</template>
<script>
    $(function () {
        var $noData = $(".no-data");
        $noData.text("Loading...");
        $.post("", {action: "getArticles"}, function (articles) {
            var $art_list = $(".articles");
            if (articles.length > 0) {
                $art_list.empty();
                articles.forEach(function (article) {
                    var $tr = cloneTemplate($("template.articles_list"));
                    $tr.find(".information .login").text(article.login);
                    $tr.find(".title").text(article.title);
                    $tr.find(".article_time").text(article.creationTime);
                    var $text_list = $tr.find(".text");
                    article.text.split("\n").forEach(function (value) {
                        var $tr_text = cloneTemplate($("template.article_text"));
                        $tr_text.find(".text").text(value);
                        $text_list.append($tr_text);
                    });

                    $art_list.append($tr);
                });
            } else {
                $noData.text("No data");
            }
        })
    })
</script>
</@c.page>
