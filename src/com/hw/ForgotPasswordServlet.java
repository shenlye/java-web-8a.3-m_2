package com.hw;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 忘记密码 Servlet：校验“用户名 + 邮箱”后重置密码
 * 访问地址：/forgot
 */
public class ForgotPasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/forgot.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = trim(request.getParameter("username"));
        String email = trim(request.getParameter("email"));
        String newPassword = trim(request.getParameter("newPassword"));
        String confirmPassword = trim(request.getParameter("confirmPassword"));

        request.setAttribute("username", username);
        request.setAttribute("email", email);

        // 1. 非空校验
        if (username.isEmpty() || email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            fail(request, response, "用户名、邮箱、新密码、确认密码均不能为空！");
            return;
        }

        // 2. 新密码校验
        if (newPassword.length() < 6) {
            fail(request, response, "新密码长度不能少于 6 位！");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            fail(request, response, "两次输入的新密码不一致！");
            return;
        }

        try {
            // 3. 校验身份：用户名与邮箱必须匹配
            if (userDao.findByUsernameAndEmail(username, email) == null) {
                fail(request, response, "用户名与邮箱不匹配，无法重置密码！");
                return;
            }

            // 4. 重置密码（MD5 加密后入库）
            if (!userDao.updatePassword(username, MD5Util.md5(newPassword))) {
                fail(request, response, "密码重置失败，请稍后重试！");
                return;
            }

            // 5. 重置成功，跳回登录页并提示
            request.setAttribute("msg", "密码重置成功，请使用新密码登录！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);

        } catch (Exception e) {
            fail(request, response, "系统异常：" + e.getMessage());
        }
    }

    private void fail(HttpServletRequest request, HttpServletResponse response, String msg)
            throws ServletException, IOException {
        request.setAttribute("msg", msg);
        request.getRequestDispatcher("/forgot.jsp").forward(request, response);
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
