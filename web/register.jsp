<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String contextPath = request.getContextPath();
    String msg = (String) request.getAttribute("msg");
    String username = (String) request.getAttribute("username");
    String email = (String) request.getAttribute("email");
    String phone = (String) request.getAttribute("phone");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户注册</title>
    <link rel="stylesheet" href="<%=contextPath%>/css/style.css">
</head>
<body>

<div class="card card-wide">
    <h1 class="title">用户注册</h1>
    <p class="subtitle">创建账号，加入我们</p>

    <% if (msg != null && !msg.isEmpty()) { %>
        <div class="msg"><%=msg%></div>
    <% } %>

    <form action="<%=contextPath%>/register" method="post" onsubmit="return checkRegister()">
        <div class="form-item">
            <label for="username">用户名</label>
            <input type="text" id="username" name="username" maxlength="20"
                   placeholder="3 ~ 20 个字符" value="<%=username == null ? "" : username%>">
        </div>

        <div class="form-item">
            <label for="password">密码</label>
            <input type="password" id="password" name="password" maxlength="20" placeholder="不少于 6 位">
        </div>

        <div class="form-item">
            <label for="confirmPassword">确认密码</label>
            <input type="password" id="confirmPassword" name="confirmPassword" maxlength="20"
                   placeholder="请再次输入密码">
        </div>

        <div class="form-item">
            <label for="email">邮箱</label>
            <input type="text" id="email" name="email" maxlength="50"
                   placeholder="例如：abc@example.com" value="<%=email == null ? "" : email%>">
        </div>

        <div class="form-item">
            <label for="phone">手机号（选填）</label>
            <input type="text" id="phone" name="phone" maxlength="11"
                   placeholder="请输入 11 位手机号" value="<%=phone == null ? "" : phone%>">
        </div>

        <button type="submit" class="btn">注 册</button>
    </form>

    <div class="links">
        <a href="<%=contextPath%>/login.jsp">已有账号？返回登录</a>
    </div>
</div>

<script>
    function checkRegister() {
        var username = document.getElementById('username').value.trim();
        var password = document.getElementById('password').value.trim();
        var confirmPassword = document.getElementById('confirmPassword').value.trim();
        var email = document.getElementById('email').value.trim();
        var phone = document.getElementById('phone').value.trim();

        if (username.length < 3 || username.length > 20) {
            alert('用户名长度必须为 3 ~ 20 个字符！');
            return false;
        }
        if (password.length < 6) {
            alert('密码长度不能少于 6 位！');
            return false;
        }
        if (password !== confirmPassword) {
            alert('两次输入的密码不一致！');
            return false;
        }
        if (!/^[\w.+-]+@[\w-]+(\.[\w-]+)+$/.test(email)) {
            alert('邮箱格式不正确！');
            return false;
        }
        if (phone !== '' && !/^1[3-9]\d{9}$/.test(phone)) {
            alert('手机号格式不正确！');
            return false;
        }
        return true;
    }
</script>

</body>
</html>
