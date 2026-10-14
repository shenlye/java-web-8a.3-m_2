package com.hw;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录 Servlet：处理登录页面提交的表单
 * 访问地址：/login
 */
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = trim(request.getParameter("username"));
        String password = trim(request.getParameter("password"));

        request.setAttribute("username", username);

        // 1. 非空校验
        if (username.isEmpty() || password.isEmpty()) {
            fail(request, response, "用户名和密码不能为空！");
            return;
        }

        try {
            // 2. 根据用户名查询用户
            User user = userDao.findByUsername(username);
            if (user == null) {
                fail(request, response, "用户不存在，请先注册！");
                return;
            }

            // 3. 校验密码（把明文加密后再与库中的密文比较）
            if (!user.getPassword().equals(MD5Util.md5(password))) {
                fail(request, response, "密码错误，请重新输入！");
                return;
            }

            // 4. 登录成功：把用户放入 session，跳转到欢迎页
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/welcome.jsp");

        } catch (Exception e) {
            fail(request, response, "系统异常：" + e.getMessage());
        }
    }

    private void fail(HttpServletRequest request, HttpServletResponse response, String msg)
            throws ServletException, IOException {
        request.setAttribute("msg", msg);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
