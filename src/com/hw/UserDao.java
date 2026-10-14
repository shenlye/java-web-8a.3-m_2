package com.hw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据访问层（DAO）
 * 使用 JDBC + PreparedStatement 操作 t_user 表
 */
public class UserDao {

    /**
     * 根据用户名查询用户（用于登录、注册查重）
     */
    public User findByUsername(String username) throws Exception {
        String sql = "SELECT id, username, password, email, phone, create_time FROM t_user WHERE username = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return toUser(rs);
            }
            return null;
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
    }

    /**
     * 根据用户名 + 邮箱查询用户（用于忘记密码时校验身份）
     */
    public User findByUsernameAndEmail(String username, String email) throws Exception {
        String sql = "SELECT id, username, password, email, phone, create_time FROM t_user "
                + "WHERE username = ? AND email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return toUser(rs);
            }
            return null;
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
    }

    /**
     * 新增用户（注册）
     */
    public boolean insert(User user) throws Exception {
        String sql = "INSERT INTO t_user (username, password, email, phone) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            return stmt.executeUpdate() > 0;
        } finally {
            DBUtil.close(null, stmt, conn);
        }
    }

    /**
     * 根据用户名修改密码（忘记密码重置）
     */
    public boolean updatePassword(String username, String newPassword) throws Exception {
        String sql = "UPDATE t_user SET password = ? WHERE username = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } finally {
            DBUtil.close(null, stmt, conn);
        }
    }

    /**
     * 查询全部用户（可选的学生演示方法）
     */
    public List<User> findAll() throws Exception {
        String sql = "SELECT id, username, password, email, phone, create_time FROM t_user ORDER BY id DESC";
        List<User> list = new ArrayList<User>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(toUser(rs));
            }
            return list;
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
    }

    /**
     * 把结果集当前行封装成 User 对象
     */
    private User toUser(ResultSet rs) throws Exception {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setCreateTime(String.valueOf(rs.getTimestamp("create_time")));
        return user;
    }
}
