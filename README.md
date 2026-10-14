# JavaWeb 登录 / 注册 / 忘记密码

> ⚠️ 本项目为 JavaWeb 课程作业演示代码，仅用于学习交流，未做生产级安全加固（例如密码仍使用 MD5 存储），请勿直接用于线上环境。

## 技术栈

- JSP + Servlet + JavaBean + JDBC + MySQL（`javax.servlet` 4.0）
- 容器：Tomcat 8 / 9（Tomcat 10+ 使用 `jakarta.servlet` 包名，无法运行本项目）
- 前端：原生 HTML / CSS / JavaScript + JSP

## 项目结构

```
JavaWeb登录注册作业/
├── src/com/hw/                    后端 Java 源码
│   ├── User.java                  用户实体（JavaBean）
│   ├── DBUtil.java                数据库连接工具（读取 db.properties）
│   ├── UserDao.java               用户数据访问层（JDBC + PreparedStatement）
│   ├── MD5Util.java               密码 MD5 加密工具
│   ├── LoginServlet.java          /login
│   ├── RegisterServlet.java       /register
│   ├── ForgotPasswordServlet.java /forgot
│   └── LogoutServlet.java         /logout
├── src/db.properties              数据库连接配置（需改成自己的 MySQL 账号密码）
├── web/                           页面资源
│   ├── login.jsp / register.jsp / forgot.jsp / welcome.jsp
│   ├── css/style.css
│   └── WEB-INF/web.xml            Servlet 映射与编码配置
├── sql/schema.sql                 建库建表脚本 + 测试账号
├── lib/                           依赖 jar（servlet-api、mysql 驱动）
└── build.ps1                      编译脚本（javac）
```

## 页面与 Servlet 对应关系

| 页面 | Servlet | 处理逻辑 |
| --- | --- | --- |
| [login.jsp](web/login.jsp) | [LoginServlet.java](src/com/hw/LoginServlet.java) | 按用户名查库 → MD5 比对密码 → 成功写入 session 并跳转 welcome.jsp；失败回显错误 |
| [register.jsp](web/register.jsp) | [RegisterServlet.java](src/com/hw/RegisterServlet.java) | 非空与格式校验（用户名 3~20 位、密码 ≥6 位、邮箱/手机号正则）→ 用户名查重 → 插入数据 |
| [forgot.jsp](web/forgot.jsp) | [ForgotPasswordServlet.java](src/com/hw/ForgotPasswordServlet.java) | 用「用户名 + 注册邮箱」校验身份 → 更新密码 |

前后端双重校验：页面 JS 先校验一遍，Servlet 里再校验一遍；密码统一 MD5 后入库，不存明文。

## 运行方式

1. 在 MySQL 中执行 `sql/schema.sql`：建库 `javaweb_homework`、建表 `t_user`，并插入测试账号 `admin / 123456`
2. 修改 `src/db.properties` 中的 `jdbc.username`、`jdbc.password`
3. 编译：PowerShell 中执行 `.\build.ps1`（或用 IDEA 直接部署）
4. 将 `web` 目录部署到 Tomcat（8 / 9），访问 `/login.jsp`

## 已完成的验证

本地用 Tomcat 9 + H2（MySQL 兼容模式）实际部署，并跑了 11 项 HTTP 用例，全部通过：

- **注册**：注册成功 / 用户名重复 / 两次密码不一致 / 密码过短 → 均按预期提示
- **登录**：密码错误、用户不存在、空值 → 全部拦截；密码正确 → 302 跳转 welcome.jsp 且 session 正常
- **忘记密码**：邮箱不匹配 → 拒绝；重置成功 → 新密码可登录、旧密码失效

## 其他说明

- 密码使用 MD5 仅为课程惯例，实际项目应使用 BCrypt 等加盐哈希算法。
