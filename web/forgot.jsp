<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String contextPath = request.getContextPath();
    String msg = (String) request.getAttribute("msg");
    String username = (String) request.getAttribute("username");
    String email = (String) request.getAttribute("email");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>忘记密码</title>
    <link rel="stylesheet" href="<%=contextPath%>/css/style.css">
</head>
<body>

<div class="card">
    <h1 class="title">忘记密码</h1>
    <p class="subtitle">通过“用户名 + 注册邮箱”验证身份后重置密码</p>

    <% if (msg != null && !msg.isEmpty()) { %>
        <div class="msg"><%=msg%></div>
    <% } %>

    <form action="<%=contextPath%>/forgot" method="post" onsubmit="return checkForgot()">
        <div class="form-item">
            <label for="username">用户名</label>
            <input type="text" id="username" name="username" maxlength="20"
                   placeholder="请输入注册时的用户名" value="<%=username == null ? "" : username%>">
        </div>

        <div class="form-item">
            <label for="email">注册邮箱</label>
            <input type="text" id="email" name="email" maxlength="50"
                   placeholder="请输入注册时填写的邮箱" value="<%=email == null ? "" : email%>">
        </div>

        <div class="form-item">
            <label for="newPassword">新密码</label>
            <input type="password" id="newPassword" name="newPassword" maxlength="20" placeholder="不少于 6 位">
        </div>

        <div class="form-item">
            <label for="confirmPassword">确认新密码</label>
            <input type="password" id="confirmPassword" name="confirmPassword" maxlength="20"
                   placeholder="请再次输入新密码">
        </div>

        <button type="submit" class="btn">重 置 密 码</button>
    </form>

    <div class="links">
        <a href="<%=contextPath%>/login.jsp">返回登录</a>
        <a href="<%=contextPath%>/register.jsp">注册新账号</a>
    </div>
</div>

<script>
    function checkForgot() {
        var username = document.getElementById('username').value.trim();
        var email = document.getElementById('email').value.trim();
        var newPassword = document.getElementById('newPassword').value.trim();
        var confirmPassword = document.getElementById('confirmPassword').value.trim();

        if (username === '') {
            alert('请输入用户名！');
            return false;
        }
        if (email === '') {
            alert('请输入注册邮箱！');
            return false;
        }
        if (newPassword.length < 6) {
            alert('新密码长度不能少于 6 位！');
            return false;
        }
        if (newPassword !== confirmPassword) {
            alert('两次输入的新密码不一致！');
            return false;
        }
        return true;
    }
</script>

</body>
</html>
