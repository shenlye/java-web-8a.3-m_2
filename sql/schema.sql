-- 创建数据库
CREATE DATABASE IF NOT EXISTS javaweb_homework DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE javaweb_homework;

-- 创建用户表
CREATE TABLE IF NOT EXISTS t_user (
    id          INT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    username    VARCHAR(20)  NOT NULL COMMENT '用户名',
    password    VARCHAR(32)  NOT NULL COMMENT '密码（MD5 密文）',
    email       VARCHAR(50)  NOT NULL COMMENT '邮箱',
    phone       VARCHAR(11)  DEFAULT NULL COMMENT '手机号',
    create_time TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '用户表';

-- 插入一条测试数据：用户名 admin，密码 123456（MD5 后的值）
INSERT INTO t_user (username, password, email, phone)
VALUES ('admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin@example.com', '13800138000');

-- 查询验证
SELECT * FROM t_user;
