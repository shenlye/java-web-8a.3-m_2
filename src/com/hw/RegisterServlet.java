package com.hw;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注册 Servlet：处理注册页面提交的表单
 * 访问地址：/register
 */
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = trim(request.getParameter("username"));
        String password = trim(request.getParameter("password"));
        String confirmPassword = trim(request.getParameter("confirmPassword"));
        String email = trim(request.getParameter("email"));
        String phone = trim(request.getParameter("phone"));

        // 出错时回填，避免用户重新输入
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);

        // 1. 非空校验
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
            fail(request, response, "用户名、密码、确认密码、邮箱均不能为空！");
            return;
        }

        // 2. 格式校验
        if (username.length() < 3 || username.length() > 20) {
            fail(request, response, "用户名长度必须为 3 ~ 20 个字符！");
            return;
        }
        if (password.length() < 6) {
            fail(request, response, "密码长度不能少于 6 位！");
            return;
        }
        if (!password.equals(confirmPassword)) {
            fail(request, response, "两次输入的密码不一致！");
            return;
        }
        if (!email.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            fail(request, response, "邮箱格式不正确！");
            return;
        }
        if (!phone.isEmpty() && !phone.matches("^1[3-9]\\d{9}$")) {
            fail(request, response, "手机号格式不正确！");
            return;
        }

        try {
            // 3. 用户名查重
            if (userDao.findByUsername(username) != null) {
                fail(request, response, "用户名【" + username + "】已被注册，请更换！");
                return;
            }

            // 4. 新增用户（密码 MD5 加密后入库）
            User user = new User(username, MD5Util.md5(password), email, phone);
            if (!userDao.insert(user)) {
                fail(request, response, "注册失败，请稍后重试！");
                return;
            }

            // 5. 注册成功，跳回登录页并提示
            request.setAttribute("msg", "注册成功，请使用新账号登录！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);

        } catch (Exception e) {
            fail(request, response, "系统异常：" + e.getMessage());
        }
    }

    private void fail(HttpServletRequest request, HttpServletResponse response, String msg)
            throws ServletException, IOException {
        request.setAttribute("msg", msg);
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
