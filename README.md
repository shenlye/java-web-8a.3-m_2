\## 项目结构



```

JavaWeb登录注册作业/

├── src/com/hw/            User.java / DBUtil.java / UserDao.java / MD5Util.java

│                          LoginServlet.java / RegisterServlet.java / ForgotPasswordServlet.java / LogoutServlet.java

├── src/db.properties      数据库连接配置（改成你自己的 MySQL 账号密码）

├── web/                   login.jsp / register.jsp / forgot.jsp / welcome.jsp + css/style.css

├── web/WEB-INF/web.xml    servlet 映射（/login、/register、/forgot、/logout）

├── sql/schema.sql         建库建表 + 测试账号 admin/123456

├── lib/                   javax.servlet-api-4.0.1.jar、mysql-connector-j-8.0.33.jar

└── build.ps1              本机没有 Maven，用 javac 编译的脚本

```



技术栈就是老一套：\*\*JSP + Servlet + JavaBean + JDBC + MySQL\*\*，`javax.servlet` 4.0，部署在 Tomcat 8/9（不能用 Tomcat 10，那是 jakarta 包名）。



\## 三组页面 / Servlet 的功能



| 页面 | Servlet | 处理逻辑 |

|---|---|---|

| \[login.jsp](file:///C:/Users/sheng/Desktop/JavaWeb登录注册作业/web/login.jsp) | \[LoginServlet.java](file:///C:/Users/sheng/Desktop/JavaWeb登录注册作业/src/com/hw/LoginServlet.java) | 按用户名查库 → MD5 比对密码 → 成功存 session 跳 welcome.jsp，失败回显错误 |

| \[register.jsp](file:///C:/Users/sheng/Desktop/JavaWeb登录注册作业/web/register.jsp) | \[RegisterServlet.java](file:///C:/Users/sheng/Desktop/JavaWeb登录注册作业/src/com/hw/RegisterServlet.java) | 非空 + 格式校验（用户名 3\~20、密码 ≥6、邮箱/手机号正则）→ 用户名查重 → 插入 |

| \[forgot.jsp](file:///C:/Users/sheng/Desktop/JavaWeb登录注册作业/web/forgot.jsp) | \[ForgotPasswordServlet.java](file:///C:/Users/sheng/Desktop/JavaWeb登录注册作业/src/com/hw/ForgotPasswordServlet.java) | 用「用户名 + 注册邮箱」校验身份 → 更新密码 |



前后端双重校验（页面里带 JS，Servlet 里再校一遍），密码统一 MD5 后入库，不存明文。



\## 实测验证结果



我下载了 Tomcat 9 + 用嵌入式 H2（MySQL 兼容模式）建表，真实部署后跑了 11 项 HTTP 用例，\*\*全部 PASS\*\*：



\- 注册成功 / 用户名查重 / 两次密码不一致 / 密码长度不足 → 均按预期提示

\- 登录：密码错误、用户不存在、空值 → 全部拦截；密码正确 → 302 跳 welcome.jsp，session 正常

\- 忘记密码：邮箱不匹配 → 拒绝；重置成功 → 用新密码能登录、旧密码失效



验证完已把数据库配置还原成 MySQL，测试用的 H2 驱动没有留在项目里。



\## 你这边怎么跑起来



1\. MySQL 里执行 \[schema.sql](file:///C:/Users/sheng/Desktop/JavaWeb登录注册作业/sql/schema.sql)

2\. 改 \[db.properties](file:///C:/Users/sheng/Desktop/JavaWeb登录注册作业/src/db.properties) 里的 `jdbc.username` / `jdbc.password`

3\. PowerShell 跑 `.\\build.ps1` 编译（或直接丢进 IDEA 配 Tomcat 运行）

4\. Tomcat 部署整个 `web` 目录，访问 `/login.jsp`



两个提醒：一是 `lib` 里的 jar 是 `javax.servlet` 包，Tomcat 10+ 跑不了；二是 MD5 只是按课程惯例用的，真实项目该换 BCrypt。

