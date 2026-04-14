-- 创建数据库
CREATE DATABASE IF NOT EXISTS homeservice DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE homeservice;

-- 用户表
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    phone VARCHAR(20) NOT NULL UNIQUE COMMENT '手机号',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar VARCHAR(255) COMMENT '头像URL',
    role TINYINT NOT NULL DEFAULT 0 COMMENT '角色：0-普通用户 1-服务人员 2-管理员',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB COMMENT='用户表';

-- 服务类目表
DROP TABLE IF EXISTS service_category;
CREATE TABLE service_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(50) NOT NULL COMMENT '类目名称',
    description VARCHAR(500) COMMENT '类目描述',
    price DECIMAL(10,2) NOT NULL COMMENT '参考价格（元/次）',
    icon VARCHAR(255) COMMENT '类目图标URL',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-下架 1-上架',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB COMMENT='服务类目表';

-- 服务人员表
DROP TABLE IF EXISTS worker;
CREATE TABLE worker (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT NOT NULL UNIQUE COMMENT '关联用户ID',
    real_name VARCHAR(50) NOT NULL COMMENT '真实姓名',
    gender TINYINT COMMENT '性别：0-女 1-男',
    age INT COMMENT '年龄',
    phone VARCHAR(20) COMMENT '联系电话',
    id_card VARCHAR(20) COMMENT '身份证号',
    skills VARCHAR(500) COMMENT '技能标签（JSON数组）',
    experience INT DEFAULT 0 COMMENT '从业年限',
    level TINYINT DEFAULT 1 COMMENT '等级：1-初级 2-中级 3-高级 4-金牌',
    rating DECIMAL(2,1) DEFAULT 5.0 COMMENT '综合评分',
    work_status TINYINT DEFAULT 1 COMMENT '工作状态：0-休息 1-空闲 2-忙碌',
    introduction TEXT COMMENT '个人简介',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '审核状态：0-待审核 1-已通过 2-已拒绝',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES sys_user(id)
) ENGINE=InnoDB COMMENT='服务人员表';

-- 订单表
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    order_no VARCHAR(32) NOT NULL UNIQUE COMMENT '订单编号',
    user_id BIGINT NOT NULL COMMENT '下单用户ID',
    worker_id BIGINT COMMENT '服务人员ID',
    category_id BIGINT NOT NULL COMMENT '服务类目ID',
    category_name VARCHAR(50) NOT NULL COMMENT '服务类目名称',
    appointment_time DATETIME NOT NULL COMMENT '预约服务时间',
    contact_name VARCHAR(50) NOT NULL COMMENT '联系人姓名',
    contact_phone VARCHAR(20) NOT NULL COMMENT '联系人电话',
    address VARCHAR(255) NOT NULL COMMENT '服务地址',
    remark VARCHAR(500) COMMENT '订单备注',
    total_price DECIMAL(10,2) NOT NULL COMMENT '订单金额',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '订单状态：0-待派单 1-已派单 2-已接单 3-进行中 4-待评价 5-已完成 6-已取消 7-已拒绝',
    cancel_reason VARCHAR(255) COMMENT '取消原因',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    finish_time DATETIME COMMENT '完成时间',
    FOREIGN KEY (user_id) REFERENCES sys_user(id),
    FOREIGN KEY (worker_id) REFERENCES worker(id),
    FOREIGN KEY (category_id) REFERENCES service_category(id)
) ENGINE=InnoDB COMMENT='订单表';

-- 评价表
DROP TABLE IF EXISTS review;
CREATE TABLE review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    order_id BIGINT NOT NULL UNIQUE COMMENT '关联订单ID',
    user_id BIGINT NOT NULL COMMENT '评价用户ID',
    worker_id BIGINT NOT NULL COMMENT '被评价服务人员ID',
    rating TINYINT NOT NULL COMMENT '评分（1-5）',
    content VARCHAR(500) COMMENT '评价内容',
    images VARCHAR(1000) COMMENT '评价图片（JSON数组）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (user_id) REFERENCES sys_user(id),
    FOREIGN KEY (worker_id) REFERENCES worker(id)
) ENGINE=InnoDB COMMENT='评价表';

-- 地址表
DROP TABLE IF EXISTS address;
CREATE TABLE address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '所属用户ID',
    contact_name VARCHAR(50) NOT NULL COMMENT '联系人姓名',
    contact_phone VARCHAR(20) NOT NULL COMMENT '联系人电话',
    province VARCHAR(50) COMMENT '省',
    city VARCHAR(50) COMMENT '市',
    district VARCHAR(50) COMMENT '区',
    detail_address VARCHAR(255) NOT NULL COMMENT '详细地址',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认地址：0-否 1-是',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES sys_user(id)
) ENGINE=InnoDB COMMENT='地址表';

-- 初始化管理员账号（密码: admin123，BCrypt加密）
INSERT INTO sys_user (username, password, phone, nickname, role, status)
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '13800000000', '系统管理员', 2, 1);

-- 初始化服务类目数据
INSERT INTO service_category (name, description, price, sort_order) VALUES
('日常保洁', '家庭日常清洁打扫，包括地面、桌面、厨房、卫生间等', 120.00, 1),
('深度保洁', '全面深度清洁，包括家电清洗、窗帘清洗、油烟机清洗等', 280.00, 2),
('月嫂服务', '专业产妇及新生儿护理服务', 8800.00, 3),
('保姆服务', '家庭日常照料，包括做饭、洗衣、打扫等', 3500.00, 4),
('家电清洗', '空调、洗衣机、冰箱、热水器等家电专业清洗', 150.00, 5),
('搬家服务', '家庭搬家，包括打包、搬运、拆装等', 500.00, 6),
('管道疏通', '厨房下水道、卫生间管道疏通', 200.00, 7),
('除甲醛', '新房装修后甲醛检测与治理', 800.00, 8);

-- 创建索引
CREATE INDEX idx_user_phone ON sys_user(phone);
CREATE INDEX idx_user_role ON sys_user(role);
CREATE INDEX idx_order_user ON orders(user_id);
CREATE INDEX idx_order_worker ON orders(worker_id);
CREATE INDEX idx_order_status ON orders(status);
CREATE INDEX idx_order_time ON orders(create_time);
CREATE INDEX idx_review_worker ON review(worker_id);
