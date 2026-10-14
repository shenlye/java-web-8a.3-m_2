<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.hw.User" %>
<%
    String contextPath = request.getContextPath();
    // 未登录则拦截，跳回登录页
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(contextPath + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录成功</title>
    <link rel="stylesheet" href="<%=contextPath%>/css/style.css">
</head>
<body>

<div class="card card-wide">
    <h1 class="title">登录成功</h1>
    <p class="subtitle">欢迎回来，<%=user.getUsername()%></p>

    <div class="info">
        用户名：<span><%=user.getUsername()%></span><br>
        邮箱：<span><%=user.getEmail()%></span><br>
        手机号：<span><%=user.getPhone() == null || user.getPhone().isEmpty() ? "未填写" : user.getPhone()%></span><br>
        注册时间：<span><%=user.getCreateTime() == null ? "" : user.getCreateTime()%></span>
    </div>

    <div class="links">
        <a href="<%=contextPath%>/logout">退出登录</a>
    </div>
</div>

</body>
</html>
