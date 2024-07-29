/*
 Navicat Premium Data Transfer

 Source Server         : LocalMySQL
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : screw_it_development_biz

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 29/07/2024 22:54:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog_collection
-- ----------------------------
DROP TABLE IF EXISTS `blog_collection`;
CREATE TABLE `blog_collection`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏组ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `collection_name` varchar(255) CHARACTER SET armscii8 COLLATE armscii8_bin NULL DEFAULT NULL COMMENT '收藏组名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `del_flag` int NULL DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = armscii8 COLLATE = armscii8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_collection
-- ----------------------------

-- ----------------------------
-- Table structure for blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment`  (
  `id` bigint NOT NULL COMMENT '评论ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '评论发表者ID',
  `blog_id` bigint NULL DEFAULT NULL COMMENT '评论所在博客ID',
  `comment` varchar(1024) CHARACTER SET armscii8 COLLATE armscii8_bin NULL DEFAULT NULL COMMENT '具体评论',
  `create_time` datetime NULL DEFAULT NULL COMMENT '发表时间',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父ID、归属于哪个评论，默认为0',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `del_flag` int NULL DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = armscii8 COLLATE = armscii8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_comment
-- ----------------------------

-- ----------------------------
-- Table structure for blog_history
-- ----------------------------
DROP TABLE IF EXISTS `blog_history`;
CREATE TABLE `blog_history`  (
  `id` int NOT NULL COMMENT 'id',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '消息',
  `send_user_id` int NULL DEFAULT NULL COMMENT '发送者',
  `receive_user_id` int NULL DEFAULT NULL COMMENT '接收者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `del_flag` int NULL DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_history
-- ----------------------------

-- ----------------------------
-- Table structure for blog_inbox
-- ----------------------------
DROP TABLE IF EXISTS `blog_inbox`;
CREATE TABLE `blog_inbox`  (
  `id` bigint NOT NULL COMMENT '收件箱ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `message_id` bigint NOT NULL COMMENT '消息ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_inbox
-- ----------------------------

-- ----------------------------
-- Table structure for blog_permission
-- ----------------------------
DROP TABLE IF EXISTS `blog_permission`;
CREATE TABLE `blog_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限记录ID',
  `permission_type` int NULL DEFAULT NULL COMMENT '权限类型(1-按钮,2-表单,3-数据,4-其它)',
  `permission_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限编码',
  `permission_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限名称',
  `action_id` bigint NULL DEFAULT NULL COMMENT '动作ID(按钮ID)',
  `permission_scope` int NULL DEFAULT NULL COMMENT '权限范围(1-Tenant,2-Role,3-Org,4-User,5-EnergyType,6-Product,7-Device,8-Point)',
  `permission_category` int NULL DEFAULT NULL COMMENT '权限类别(1-Read,2-Write,3-Read/Write,4-Other)',
  `menu_form_id` bigint NULL DEFAULT NULL COMMENT '自定义Form唯一ID',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT ' ' COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT ' ' COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志，0未删除，1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_permission
-- ----------------------------

-- ----------------------------
-- Table structure for blog_public_box
-- ----------------------------
DROP TABLE IF EXISTS `blog_public_box`;
CREATE TABLE `blog_public_box`  (
  `id` bigint NOT NULL COMMENT '公开箱ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `blog_id` bigint NOT NULL COMMENT '博客ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_public_box
-- ----------------------------

-- ----------------------------
-- Table structure for blog_role
-- ----------------------------
DROP TABLE IF EXISTS `blog_role`;
CREATE TABLE `blog_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT ' ' COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT ' ' COMMENT '修改人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标记，0未删除，1已删除',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '0停用 1启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_role
-- ----------------------------

-- ----------------------------
-- Table structure for blog_user
-- ----------------------------
DROP TABLE IF EXISTS `blog_user`;
CREATE TABLE `blog_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '盐值',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `lock_flag` int NULL DEFAULT 0 COMMENT '锁定标记，0未锁定，9已锁定',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记，0未删除，1已删除',
  `wx_openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信登录openId',
  `mini_openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小程序openId',
  `qq_openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'QQ openId',
  `gitee_login` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '码云标识',
  `osc_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开源中国标识',
  `gender` int NULL DEFAULT 0 COMMENT '性别 0：未知 1：男 2：女',
  `status` int NULL DEFAULT 1 COMMENT '0停用 1启用',
  `last_login` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_user
-- ----------------------------
INSERT INTO `blog_user` VALUES (1, 'fangyaohui', '123456', NULL, NULL, NULL, NULL, NULL, NULL, '2024-07-27 18:38:43', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `blog_user` VALUES (3, 'fang', '47d4bead8ac78fd470363b30bf3f4923', '232b5e5d-0147-4b90-a6a6-29244f5502f6', NULL, NULL, 'fang', NULL, '2944163240@qq.com', NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, 0, 1, '2024-07-28 12:31:24', NULL);
INSERT INTO `blog_user` VALUES (19, 'fangMyBatisTest', 'f47c1ba24e08ce33db11b0a3947bae76', 'd3adb988-3cdc-44b4-885e-4877e1756832', NULL, NULL, 'fangMyBatisTest', NULL, 'fangMyBatisTest@qq.com', '2024-07-28 15:50:44', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, 0, 1, '2024-07-28 15:50:44', NULL);

-- ----------------------------
-- Table structure for rel_collection_blog
-- ----------------------------
DROP TABLE IF EXISTS `rel_collection_blog`;
CREATE TABLE `rel_collection_blog`  (
  `user_id` bigint NOT NULL,
  `collection_id` bigint NOT NULL,
  `blog_id` bigint NOT NULL
) ENGINE = InnoDB CHARACTER SET = armscii8 COLLATE = armscii8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rel_collection_blog
-- ----------------------------

-- ----------------------------
-- Table structure for rel_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `rel_role_permission`;
CREATE TABLE `rel_role_permission`  (
  `role_id` bigint NOT NULL,
  `permission` bigint NOT NULL,
  PRIMARY KEY (`role_id`, `permission`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rel_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for rel_user_blog
-- ----------------------------
DROP TABLE IF EXISTS `rel_user_blog`;
CREATE TABLE `rel_user_blog`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `blog_id` bigint NOT NULL COMMENT '博客ID'
) ENGINE = InnoDB CHARACTER SET = armscii8 COLLATE = armscii8_bin COMMENT = '点赞关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rel_user_blog
-- ----------------------------

-- ----------------------------
-- Table structure for rel_user_role
-- ----------------------------
DROP TABLE IF EXISTS `rel_user_role`;
CREATE TABLE `rel_user_role`  (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rel_user_role
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
