<#-- @ftlvariable name="users" type="ru.itmo.webmail.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.webmail.model.domain.User" -->
<#import "common.ftl" as c>

<@c.page>
<div class="datatable users">
    <div class="caption">Users</div>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Login</th>
            <th>Rating</th>
            <#if user.admin>
                <th>Admin</th>
            </#if>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="3" class="no-data">No data</td>
        </tr>
        </tbody>
    </table>
</div>

<template class="users-tr">
    <tr>
        <td class="id">{{#}}</td>
        <td class="login">{{login}}</td>
        <td class="rating"></td>
    <#if user.admin>
        <td>
            <div class="button-field">
                <button class="text_value" onclick="change_admin(event)">
                </button>
                <p style="display: none" class="id">{{user_id}}</p>
            </div>
        </td>
    </#if>
    </tr>
</template>
<script>
    var change_admin = function (event) {
        var el = $(event.target).closest(".button-field");
        var id = (el.find(".id")).text();
        var text_value = $(el.find(".text_value"));

        $.post("", {action: "changeAdmin", id: id}, function (success) {
            if (success) {
                if (text_value.text().indexOf("enable") !== -1) {
                    text_value.text("disable");
                } else {
                    text_value.text("enable");
                }
            }
        });

        return false;
    };


    $(function () {
        var $noData = $(".users .no-data");
        $noData.text("Loading...");
        $.post("", {action: "find"}, function (users) {
            var $tbody = $(".users tbody");
            if (users.length > 0) {
                var logins = [];
                $tbody.empty();
                users.forEach(function (user) {
                    var $tr = cloneTemplate($("template.users-tr"));
                    $tr.find(".id").text(user.id);
                    $tr.find(".login").text(user.login);
                    $tr.find(".text_value").text(!user.admin ? "enable" : "disable");
                    $tr.find(".id").text(user.id);
                    $tr.find("tr").attr("id", "user_" + user.login.toLowerCase());
                    logins.push(user.login);
                    $tbody.append($tr);
                });
                $.ajax({
                    url: "http://codeforces.com/api/user.info",
                    jsonp: "jsonp",
                    dataType: "jsonp",
                    data: {
                        handles: logins.join(";")
                    },
                    success: function (response) {
                        if (response["status"] === "OK") {
                            response["result"].forEach(function (user) {
                                $("#user_" + user.handle.toLowerCase()).find(".rating").text(user["rating"]);
                            })
                        }
                    }
                })
            } else {
                $noData.text("No data");
            }
        })
    })
</script>
</@c.page>
