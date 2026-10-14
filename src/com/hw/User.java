package com.hw;

import java.io.Serializable;

/**
 * 用户实体类（JavaBean）
 * 对应数据库表 t_user
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 id */
    private Integer id;
    /** 用户名 */
    private String username;
    /** 密码（MD5 加密后存储） */
    private String password;
    /** 邮箱 */
    private String email;
    /** 手机号 */
    private String phone;
    /** 注册时间 */
    private String createTime;

    public User() {
    }

    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', email='" + email
                + "', phone='" + phone + "', createTime='" + createTime + "'}";
    }
}
