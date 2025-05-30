-- 创建数据库
CREATE DATABASE IF NOT EXISTS petshome DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE petshome;

-- 用户表
CREATE TABLE IF NOT EXISTS `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `gender` tinyint(1) DEFAULT '0' COMMENT '性别：0-未知，1-男，2-女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(64) DEFAULT NULL COMMENT '最后登录IP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 宠物表
CREATE TABLE IF NOT EXISTS `t_pet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '宠物ID',
  `name` varchar(64) NOT NULL COMMENT '宠物名称',
  `type` tinyint(1) NOT NULL COMMENT '宠物类型：1-猫，2-狗，3-兔子，4-仓鼠，5-其他',
  `breed` varchar(64) DEFAULT NULL COMMENT '品种',
  `gender` tinyint(1) DEFAULT '0' COMMENT '性别：0-未知，1-公，2-母',
  `age` int(11) DEFAULT NULL COMMENT '年龄（月）',
  `weight` decimal(5,2) DEFAULT NULL COMMENT '体重（kg）',
  `sterilized` tinyint(1) DEFAULT '0' COMMENT '是否绝育：0-否，1-是',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `user_id` bigint(20) NOT NULL COMMENT '所属用户ID',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态：0-正常，1-生病，2-死亡',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='宠物表';

-- 轮播图表
CREATE TABLE IF NOT EXISTS `t_banner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '轮播图ID',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `image_url` varchar(255) NOT NULL COMMENT '图片URL',
  `link_url` varchar(255) DEFAULT NULL COMMENT '链接URL',
  `sort` int(11) DEFAULT '0' COMMENT '排序值，越小越靠前',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图表';

-- 商品分类表
CREATE TABLE IF NOT EXISTS `t_product_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(64) NOT NULL COMMENT '分类名称',
  `sort` int(11) DEFAULT '0' COMMENT '排序值，越小越靠前',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- 商品表
CREATE TABLE IF NOT EXISTS `t_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `category_id` bigint(20) NOT NULL COMMENT '分类ID',
  `brand` varchar(64) DEFAULT NULL COMMENT '品牌',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `original_price` decimal(10,2) DEFAULT NULL COMMENT '原价',
  `stock` int(11) DEFAULT '0' COMMENT '库存',
  `sales` int(11) DEFAULT '0' COMMENT '销量',
  `main_image` varchar(255) DEFAULT NULL COMMENT '主图',
  `album` text COMMENT '相册，多个图片用逗号分隔',
  `description` text COMMENT '商品描述',
  `detail` text COMMENT '商品详情',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态：0-下架，1-上架',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 购物车表
CREATE TABLE IF NOT EXISTS `t_cart_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '购物车项ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- 插入测试数据
INSERT INTO `t_user` (`username`, `password`, `nickname`, `mobile`, `email`, `avatar`, `gender`, `status`, `create_time`, `update_time`)
VALUES ('admin', '$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG', '管理员', '13800138000', 'admin@petshome.com', 'https://petshome.oss-cn-beijing.aliyuncs.com/avatar/admin.jpg', 1, 1, NOW(), NOW());

INSERT INTO `t_banner` (`title`, `image_url`, `link_url`, `sort`, `status`, `create_time`, `update_time`)
VALUES
('萌宠之家欢迎您', 'https://petshome.oss-cn-beijing.aliyuncs.com/banner/banner1.jpg', '/home', 1, 1, NOW(), NOW()),
('宠物用品大促销', 'https://petshome.oss-cn-beijing.aliyuncs.com/banner/banner2.jpg', '/product/list', 2, 1, NOW(), NOW()),
('宠物健康服务', 'https://petshome.oss-cn-beijing.aliyuncs.com/banner/banner3.jpg', '/service', 3, 1, NOW(), NOW());

INSERT INTO `t_product_category` (`name`, `sort`, `icon`, `status`, `create_time`, `update_time`)
VALUES
('猫咪用品', 1, 'https://petshome.oss-cn-beijing.aliyuncs.com/category/cat.png', 1, NOW(), NOW()),
('狗狗用品', 2, 'https://petshome.oss-cn-beijing.aliyuncs.com/category/dog.png', 1, NOW(), NOW()),
('小宠用品', 3, 'https://petshome.oss-cn-beijing.aliyuncs.com/category/small.png', 1, NOW(), NOW()),
('猫粮', 4, 'https://petshome.oss-cn-beijing.aliyuncs.com/category/cat-food.png', 1, NOW(), NOW()),
('猫砂', 5, 'https://petshome.oss-cn-beijing.aliyuncs.com/category/cat-litter.png', 1, NOW(), NOW()),
('猫玩具', 6, 'https://petshome.oss-cn-beijing.aliyuncs.com/category/cat-toy.png', 1, NOW(), NOW()),
('狗粮', 7, 'https://petshome.oss-cn-beijing.aliyuncs.com/category/dog-food.png', 1, NOW(), NOW()),
('狗窝', 8, 'https://petshome.oss-cn-beijing.aliyuncs.com/category/dog-house.png', 1, NOW(), NOW()),
('狗玩具', 9, 'https://petshome.oss-cn-beijing.aliyuncs.com/category/dog-toy.png', 1, NOW(), NOW());

-- 插入测试商品数据
INSERT INTO `t_product` (`name`, `category_id`, `brand`, `price`, `original_price`, `stock`, `sales`, `main_image`, `description`, `status`, `create_time`, `update_time`)
VALUES
('高级猫粮', 4, '皇家', 199.00, 259.00, 100, 50, 'https://petshome.oss-cn-beijing.aliyuncs.com/product/cat-food.jpg', '优质猫粮，营养均衡', 1, NOW(), NOW()),
('猫砂盆', 5, '贝适', 89.00, 129.00, 50, 30, 'https://petshome.oss-cn-beijing.aliyuncs.com/product/cat-litter-box.jpg', '防溅猫砂盆，清洁方便', 1, NOW(), NOW()),
('猫咪玩具套装', 6, '萌宠', 59.00, 79.00, 200, 100, 'https://petshome.oss-cn-beijing.aliyuncs.com/product/cat-toys.jpg', '多种玩具组合，让猫咪爱不释手', 1, NOW(), NOW()),
('狗粮', 7, '宝路', 159.00, 199.00, 80, 40, 'https://petshome.oss-cn-beijing.aliyuncs.com/product/dog-food.jpg', '营养丰富，适合各种犬种', 1, NOW(), NOW()),
('狗窝', 8, '舒适', 129.00, 169.00, 30, 20, 'https://petshome.oss-cn-beijing.aliyuncs.com/product/dog-house.jpg', '舒适保暖，四季适用', 1, NOW(), NOW()),
('狗狗玩具球', 9, '萌宠', 39.00, 49.00, 150, 80, 'https://petshome.oss-cn-beijing.aliyuncs.com/product/dog-toy.jpg', '耐咬耐磨，互动玩具', 1, NOW(), NOW());

-- 管理员密码为：123456
