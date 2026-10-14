<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String contextPath = request.getContextPath();
    String msg = (String) request.getAttribute("msg");
    String username = (String) request.getAttribute("username");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户登录</title>
    <link rel="stylesheet" href="<%=contextPath%>/css/style.css">
</head>
<body>

<div class="card">
    <h1 class="title">用户登录</h1>
    <p class="subtitle">欢迎回来，请登录您的账号</p>

    <%-- 显示后端返回的提示信息 --%>
    <% if (msg != null && !msg.isEmpty()) { %>
        <div class="<%=msg.contains("成功") ? "msg msg-ok" : "msg"%>"><%=msg%></div>
    <% } %>

    <form action="<%=contextPath%>/login" method="post" onsubmit="return checkLogin()">
        <div class="form-item">
            <label for="username">用户名</label>
            <input type="text" id="username" name="username" maxlength="20"
                   placeholder="请输入用户名" value="<%=username == null ? "" : username%>">
        </div>

        <div class="form-item">
            <label for="password">密码</label>
            <input type="password" id="password" name="password" maxlength="20" placeholder="请输入密码">
        </div>

        <button type="submit" class="btn">登 录</button>
    </form>

    <div class="links">
        <a href="<%=contextPath%>/register.jsp">还没有账号？立即注册</a>
        <a href="<%=contextPath%>/forgot.jsp">忘记密码？</a>
    </div>
</div>

<script>
    function checkLogin() {
        var username = document.getElementById('username').value.trim();
        var password = document.getElementById('password').value.trim();

        if (username === '') {
            alert('请输入用户名！');
            return false;
        }
        if (password === '') {
            alert('请输入密码！');
            return false;
        }
        return true;
    }
</script>

</body>
</html>
