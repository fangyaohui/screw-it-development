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

 Date: 09/08/2024 13:26:32
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
-- Table structure for blog_headline
-- ----------------------------
DROP TABLE IF EXISTS `blog_headline`;
CREATE TABLE `blog_headline`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '头条博客ID',
  `blog_id` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '博客ID',
  `start_time` datetime NULL DEFAULT NULL COMMENT '头条博客开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '头条博客结束时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志',
  `status` int NULL DEFAULT 1 COMMENT '状态',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_headline
-- ----------------------------
INSERT INTO `blog_headline` VALUES (1, 'kxiTHJEBVE6-k_TXtt67', '2024-08-02 17:45:21', '2024-08-15 17:45:29', 0, 1, NULL);
INSERT INTO `blog_headline` VALUES (2, 'lBiTHJEBVE6-k_TXtt7B', '2024-08-01 17:47:01', '2029-08-04 17:47:21', 0, 1, NULL);
INSERT INTO `blog_headline` VALUES (3, 'lRiTHJEBVE6-k_TXtt7G', '2024-08-01 17:47:07', '2029-08-04 17:47:31', 0, 1, NULL);
INSERT INTO `blog_headline` VALUES (4, 'mBiTHJEBVE6-k_TXtt7_', '2024-08-01 17:47:11', '2024-08-22 17:47:36', 0, 1, NULL);
INSERT INTO `blog_headline` VALUES (5, 'mhiTHJEBVE6-k_TXuN5Y', '2024-08-01 17:47:15', '2029-08-31 17:47:42', 0, 1, NULL);

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
-- Table structure for blog_image_upload
-- ----------------------------
DROP TABLE IF EXISTS `blog_image_upload`;
CREATE TABLE `blog_image_upload`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '图片上传ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `source_path` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '源图片地址',
  `target_path` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标图片地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `status` int NULL DEFAULT 1 COMMENT '状态',
  `img_size` bigint NULL DEFAULT NULL COMMENT '上传图片大小',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除状态',
  `description` varchar(255) CHARACTER SET armscii8 COLLATE armscii8_bin NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 170 CHARACTER SET = armscii8 COLLATE = armscii8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_image_upload
-- ----------------------------
INSERT INTO `blog_image_upload` VALUES (4, 3, '屏幕截图 2024-03-26 234554.png', '/blog_image_upload_folder_path/20240802/b267648a-9751-4a87-a08c-e1a1b396e4ce_1722588959761.png', '2024-08-02 16:56:00', '2024-08-02 16:56:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (5, 3, '1719365994818-1.png', '/blog_image_upload_folder_path/20240802/fe0e735d-8bd9-45d3-ba84-ff66b5711c74_1722591362180.png', '2024-08-02 17:36:02', '2024-08-02 17:36:02', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (6, 3, '3b78d4eea3e6e69f41057045e2f46df6.png', '/blog_image_upload_folder_path/20240802/f15c24c7-2944-46fb-b36d-5da89504aae9_1722595088266.png', '2024-08-02 18:38:08', '2024-08-02 18:38:08', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (7, 3, '4e6c7dffe711522820b6eba940af165a.png', '/blog_image_upload_folder_path/20240802/972244c5-1d99-485b-8358-9e54f99ef07c_1722595815742.png', '2024-08-02 18:50:16', '2024-08-02 18:50:16', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (8, 3, '86cb0928868ffdabde739caafd7d2673.png', '/blog_image_upload_folder_path/20240803/db2458ad-8a39-4ee7-98a7-00339b3c4819_1722661732726.png', '2024-08-03 13:08:53', '2024-08-03 13:08:53', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (9, 3, '90c02c2cc6ba9a7f79dc778228bd2e5d.png', '/blog_image_upload_folder_path/20240803/8f98fb51-b0e7-429e-b0bf-4c0a9eed6fb1_1722661732726.png', '2024-08-03 13:08:53', '2024-08-03 13:08:53', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (10, 3, '024f60031abbe2d83e7d956438a48a32.png', '/blog_image_upload_folder_path/20240803/4c1d1d63-cc71-4d68-9891-9c945eb98614_1722661732726.png', '2024-08-03 13:08:53', '2024-08-03 13:08:53', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (11, 3, '56b2c10c7fb8489eb4ae0b564e51f4d5.png', '/blog_image_upload_folder_path/20240803/06322dea-c6cf-42ab-9a5d-137ff31e8472_1722661732726.png', '2024-08-03 13:08:53', '2024-08-03 13:08:53', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (12, 3, '166f780ec26bd0b95f3abe9c90efb7ab.png', '/blog_image_upload_folder_path/20240803/e77c5b9f-2f8d-4d67-a204-ac170751abe3_1722661732953.png', '2024-08-03 13:08:53', '2024-08-03 13:08:53', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (13, 3, '422e86f78a5f75a47bdbba9be6ddd7fe.png', '/blog_image_upload_folder_path/20240803/409a8ede-907d-4ced-b5e2-733429506442_1722661732953.png', '2024-08-03 13:08:53', '2024-08-03 13:08:53', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (14, 3, '640-1719553840276-3.png', '/blog_image_upload_folder_path/20240803/b2e6241a-687f-4388-9ab2-b065f1fec7d2_1722661733115.png', '2024-08-03 13:08:53', '2024-08-03 13:08:53', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (15, 3, '232d386b8a7d40e2c16f6182152b7f85.jpeg', '/blog_image_upload_folder_path/20240803/0e83c2d2-acb7-4712-9e6c-826a0491cfb8_1722661733115.jpeg', '2024-08-03 13:08:53', '2024-08-03 13:08:53', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (16, 3, '931aa968170bcf665b1c15dbf7b32f52.png', '/blog_image_upload_folder_path/20240803/ee76dff0-7a41-4618-b49c-f7b3643b3494_1722661733316.png', '2024-08-03 13:08:53', '2024-08-03 13:08:53', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (17, 3, '693275-20180308155026753-937067475.png', '/blog_image_upload_folder_path/20240803/944279aa-940d-406f-a9eb-071dd6cbb8c5_1722661733331.png', '2024-08-03 13:08:53', '2024-08-03 13:08:53', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (18, 3, '242041cfb7de39a52bd45d0e805f0326.png', '/blog_image_upload_folder_path/20240803/747f7c83-d80f-49de-94a7-31a24f56c993_1722661733424.png', '2024-08-03 13:08:53', '2024-08-03 13:08:53', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (19, 3, '2062669-20200614002855017-521361250.png', '/blog_image_upload_folder_path/20240803/457e5660-d93c-45c5-8131-613cff70045c_1722661733424.png', '2024-08-03 13:08:53', '2024-08-03 13:08:53', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (20, 3, '48598556701cf365544ef535b38da542.png', '/blog_image_upload_folder_path/20240803/e9eba9f1-fce2-41d1-a323-7db13b018ffb_1722661733508.png', '2024-08-03 13:08:54', '2024-08-03 13:08:54', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (21, 3, '1718787077625-1.png', '/blog_image_upload_folder_path/20240803/99b27d08-e157-49a4-8b97-1c521bc8be28_1722661733678.png', '2024-08-03 13:08:54', '2024-08-03 13:08:54', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (22, 3, '1718787081116-4.png', '/blog_image_upload_folder_path/20240803/4550412d-fbd5-4b18-9985-f8f9454b6b8d_1722661733693.png', '2024-08-03 13:08:54', '2024-08-03 13:08:54', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (23, 3, '1718787096848-10.png', '/blog_image_upload_folder_path/20240803/5c39cbfb-6911-4183-9f53-e3a53ba3e8d8_1722661733802.png', '2024-08-03 13:08:54', '2024-08-03 13:08:54', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (24, 3, '1718787101354-13.png', '/blog_image_upload_folder_path/20240803/46c00d80-4609-4fee-887c-39b85164f61a_1722661733802.png', '2024-08-03 13:08:54', '2024-08-03 13:08:54', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (25, 3, '1718787105306-19.png', '/blog_image_upload_folder_path/20240803/ae60aaf1-6c2c-4217-b7e5-c97886e78d37_1722661733818.png', '2024-08-03 13:08:54', '2024-08-03 13:08:54', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (26, 3, '1718787109255-22.png', '/blog_image_upload_folder_path/20240803/6a993d0e-048a-41b5-ac7b-cc4b0980d0d9_1722661733904.png', '2024-08-03 13:08:54', '2024-08-03 13:08:54', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (27, 3, '1718787096848-10-1718791199678-3.png', '/blog_image_upload_folder_path/20240803/12adeac8-6489-4b0b-8ff7-e1618e63047e_1722661734043.png', '2024-08-03 13:08:54', '2024-08-03 13:08:54', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (28, 3, '1718787092000-7.png', '/blog_image_upload_folder_path/20240803/878f4615-62b6-40a0-a777-e1a88547d815_1722661734058.png', '2024-08-03 13:08:54', '2024-08-03 13:08:54', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (29, 3, '1718787103291-16.png', '/blog_image_upload_folder_path/20240803/e273dc98-d404-4dca-b0fd-8f236b310684_1722661734176.png', '2024-08-03 13:08:54', '2024-08-03 13:08:54', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (30, 3, '1718787112579-25.png', '/blog_image_upload_folder_path/20240803/d1d919a1-3e6a-407d-b1cd-cb27cb1999af_1722661734176.png', '2024-08-03 13:08:54', '2024-08-03 13:08:54', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (31, 3, '1718787116395-28.png', '/blog_image_upload_folder_path/20240803/dbd8ffab-4035-4c8f-a2ad-2aeed17cd405_1722661734192.png', '2024-08-03 13:08:54', '2024-08-03 13:08:54', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (32, 3, '1718787123421-34.png', '/blog_image_upload_folder_path/20240803/98a8fbb2-6af9-477a-85cc-3f772e8b46fb_1722661734283.png', '2024-08-03 13:08:54', '2024-08-03 13:08:54', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (33, 3, '1718787118947-31.png', '/blog_image_upload_folder_path/20240803/ffb08dd9-8c8c-45ab-8836-1d569159ce3c_1722661734422.png', '2024-08-03 13:08:54', '2024-08-03 13:08:54', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (34, 3, '1718787129598-40.png', '/blog_image_upload_folder_path/20240803/b60cc81b-2632-4e70-90ee-fc61db5594ab_1722661734432.png', '2024-08-03 13:08:54', '2024-08-03 13:08:54', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (35, 3, '1718787133455-46.png', '/blog_image_upload_folder_path/20240803/334d517c-87d5-4031-8a81-4ca76042a747_1722661734554.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (36, 3, '1718787131896-43.png', '/blog_image_upload_folder_path/20240803/b5246801-06e6-467c-b184-85be17253d89_1722661734554.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (37, 3, '1718787126701-37.png', '/blog_image_upload_folder_path/20240803/51f4baa5-3268-41e8-b0d4-5450e8f11492_1722661734554.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (38, 3, '1718787373064-49.png', '/blog_image_upload_folder_path/20240803/dc08e6f6-8034-4841-b595-9f5fcdc21071_1722661734648.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (39, 3, '1718787375960-52.png', '/blog_image_upload_folder_path/20240803/d96214fd-e429-4f8b-9602-3ab84bd75548_1722661734785.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (40, 3, '1718787380608-55.png', '/blog_image_upload_folder_path/20240803/542e51b7-110c-4200-9fc3-272c4b37f1c0_1722661734800.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (41, 3, '1718787397752-67.png', '/blog_image_upload_folder_path/20240803/d1c96357-b955-424e-b44a-72e9618a2216_1722661734914.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (42, 3, '1718787382586-58.png', '/blog_image_upload_folder_path/20240803/3972394d-3874-43e5-ab2f-11d21b9c9736_1722661734914.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (43, 3, '1718787385630-61.png', '/blog_image_upload_folder_path/20240803/b67a5686-3305-4d73-b3af-fdb54824a507_1722661734945.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (44, 3, '1718787388329-64.png', '/blog_image_upload_folder_path/20240803/347c4cf3-3590-4216-a9ae-2b688e7bf240_1722661735038.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (45, 3, '1718787402213-70.png', '/blog_image_upload_folder_path/20240803/4b85e06a-e4fd-487a-ac90-f60d4ba7a7cd_1722661735125.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (46, 3, '1718787405233-73.png', '/blog_image_upload_folder_path/20240803/6c1c4464-1327-43d3-81b8-856865c7458a_1722661735156.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (47, 3, '1718787414665-79.png', '/blog_image_upload_folder_path/20240803/0da16c7e-7ac6-4f66-8384-b734397db457_1722661735260.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (48, 3, '1718787422481-85.png', '/blog_image_upload_folder_path/20240803/2485358a-f02f-45bf-84be-1e574df5eb1e_1722661735292.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (49, 3, '1718787411863-76.png', '/blog_image_upload_folder_path/20240803/42fb81b5-59bb-465c-92e9-26337980cbda_1722661735292.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (50, 3, '1718787428230-88.png', '/blog_image_upload_folder_path/20240803/0ea3e3c3-04fd-410e-b222-5c879b54d34d_1722661735393.png', '2024-08-03 13:08:55', '2024-08-03 13:08:55', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (51, 3, '1718787430426-91.png', '/blog_image_upload_folder_path/20240803/542fb21a-4d47-4efb-877d-55e3dea9e225_1722661735510.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (52, 3, '1718787418207-82.png', '/blog_image_upload_folder_path/20240803/861ffd1c-5978-43d1-a359-c16d5ac349d3_1722661735510.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (53, 3, '1719555484321-9.png', '/blog_image_upload_folder_path/20240803/a9f4fba8-f1de-4fe1-8ec6-8f8ec1dd37bc_1722661735633.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (54, 3, '1719279838565-1.jpeg', '/blog_image_upload_folder_path/20240803/07cb671f-a8e7-47c7-a280-b76e2753e126_1722661735648.jpeg', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (55, 3, '1719555497858-15.png', '/blog_image_upload_folder_path/20240803/21433986-b39d-473c-9493-d67258925f65_1722661735742.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (56, 3, '1719555484321-10.png', '/blog_image_upload_folder_path/20240803/c5ee80ec-9af2-437f-b8f3-0d2052d6ff12_1722661735864.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (57, 3, '1719555507535-18.png', '/blog_image_upload_folder_path/20240803/455400d3-cc8d-4f77-a4c0-8bfbb91915e5_1722661735864.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (58, 3, '1719561942011-38.png', '/blog_image_upload_folder_path/20240803/27c3d8c3-2fcb-488f-b8ab-36d40f79bdc4_1722661735972.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (59, 3, '1719561942011-36.png', '/blog_image_upload_folder_path/20240803/2d2d502a-0281-4c3d-bee9-f811bf020dc5_1722661736002.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (60, 3, '1719561942011-35.png', '/blog_image_upload_folder_path/20240803/7b7d8f02-a105-41fe-8243-8724946f1b1a_1722661736017.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (61, 3, '1719561942011-37.png', '/blog_image_upload_folder_path/20240803/003360b2-ef75-42cb-a705-c9da96829a58_1722661736108.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (62, 3, '1719561942011-39.png', '/blog_image_upload_folder_path/20240803/7ffbd712-7036-48c3-979a-fda3c0722d12_1722661736217.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (63, 3, '1719561942011-40.png', '/blog_image_upload_folder_path/20240803/370e21ec-f70d-4fb0-99bd-c67b6b6139bb_1722661736216.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (64, 3, '1719561942011-42.png', '/blog_image_upload_folder_path/20240803/b4e2c900-7023-40d0-87fd-9601a69e5c64_1722661736339.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (65, 3, '1720072781953-1.png', '/blog_image_upload_folder_path/20240803/2dabad56-947c-48a8-8db7-dc24bc96deaf_1722661736354.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (66, 3, '1719561942011-41.png', '/blog_image_upload_folder_path/20240803/66348e0d-eba8-42d3-84cb-0a54bd2c5994_1722661736369.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (67, 3, '1720072781954-2.png', '/blog_image_upload_folder_path/20240803/4a2f78b1-8b61-44d1-8791-a36699402ef8_1722661736462.png', '2024-08-03 13:08:56', '2024-08-03 13:08:56', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (68, 3, '1720072781954-3.png', '/blog_image_upload_folder_path/20240803/326339ab-70d1-4e4d-a92a-d2cef973f16d_1722661736569.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (69, 3, '1720422462367-1.png', '/blog_image_upload_folder_path/20240803/0b5713c3-8f9e-41de-9732-b611ca0f6f25_1722661736569.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (70, 3, '1720072781954-4.png', '/blog_image_upload_folder_path/20240803/a8d47a23-a333-4bcd-8f43-ba6fbc89c89b_1722661736708.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (71, 3, '1720422462367-2.png', '/blog_image_upload_folder_path/20240803/89f0c991-22c8-4e10-9570-d8c36094f592_1722661736724.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (72, 3, '1720422462367-3.png', '/blog_image_upload_folder_path/20240803/9086e315-69bf-4952-b845-3a6a9a6bc8fa_1722661736756.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (73, 3, '1720422462368-4.png', '/blog_image_upload_folder_path/20240803/91986d9e-dd3d-49b8-bb53-99a75444a6aa_1722661736849.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (74, 3, '1722331012373-3.png', '/blog_image_upload_folder_path/20240803/18ecedd9-67a1-48ae-8ecf-519d76ad9492_1722661736925.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (75, 3, '1721115327527-4.png', '/blog_image_upload_folder_path/20240803/cb6f9382-1d9d-4c79-8bac-0e20ca416bb6_1722661736925.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (76, 3, '1722331012373-5.png', '/blog_image_upload_folder_path/20240803/eb363334-85f4-4bbf-b39f-ef471920c514_1722661737061.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (77, 3, '1721115327529-5.png', '/blog_image_upload_folder_path/20240803/6f8af38a-408e-47d2-80a3-be0a845912a3_1722661737077.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (78, 3, '1721115327527-3.png', '/blog_image_upload_folder_path/20240803/2fd829d2-51a8-4266-ad5f-610902af7aed_1722661737108.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (79, 3, '1722331012373-4.png', '/blog_image_upload_folder_path/20240803/728fab6c-336a-4272-8cd0-38247dbc36c3_1722661737216.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (80, 3, '20180317120704396.png', '/blog_image_upload_folder_path/20240803/31527c5d-62f3-41b4-947a-1ab47b6ef52e_1722661737277.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (81, 3, '20180317121852115.png', '/blog_image_upload_folder_path/20240803/98d79dfd-4e28-4a36-84da-09f32d62693f_1722661737277.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (82, 3, '20180317145204964.png', '/blog_image_upload_folder_path/20240803/26dbcce8-971c-44fa-93ec-d765bd137f43_1722661737422.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (83, 3, '20200502100225431.png', '/blog_image_upload_folder_path/20240803/0582b247-e84d-417f-afee-374a9291aa68_1722661737454.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (84, 3, 'a56b20fc5f171779d37f55b107af46a0.png', '/blog_image_upload_folder_path/20240803/4391afae-bb3f-4186-9ff8-0fe921a7d015_1722661737457.png', '2024-08-03 13:08:57', '2024-08-03 13:08:57', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (85, 3, 'b5c1853d77fa25eb47a2f8a627f724d8.png', '/blog_image_upload_folder_path/20240803/70519e10-ff43-48c3-bc13-ad348d76e755_1722661737643.png', '2024-08-03 13:08:58', '2024-08-03 13:08:58', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (86, 3, 'e1fe5368f8754f34a980319ce5333d06.png', '/blog_image_upload_folder_path/20240803/6962d16e-4563-441b-9fd6-75dc22951c95_1722661737643.png', '2024-08-03 13:08:58', '2024-08-03 13:08:58', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (87, 3, 'b5e8d9ce207c4be69b93ddfecd66e1b7.png', '/blog_image_upload_folder_path/20240803/3d8c83a6-3eeb-467e-80e9-a85584262b9f_1722661737643.png', '2024-08-03 13:08:58', '2024-08-03 13:08:58', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (88, 3, 'cc73d86b98664547af4100ad6f2797d9.png', '/blog_image_upload_folder_path/20240803/81104480-304d-4cf0-9562-57ac04343e95_1722661737781.png', '2024-08-03 13:08:58', '2024-08-03 13:08:58', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (89, 3, 'e8db81bf08ecab6ab4a7ff417b60f76b.png', '/blog_image_upload_folder_path/20240803/b41f8ac8-bc09-4008-bdc6-47506f078758_1722661737826.png', '2024-08-03 13:08:58', '2024-08-03 13:08:58', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (90, 3, 'e39e17effc9f47a7a006639ffa937f04.png', '/blog_image_upload_folder_path/20240803/3fc32871-eeae-42ec-a4c6-04e60dd50b81_1722661737826.png', '2024-08-03 13:08:58', '2024-08-03 13:08:58', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (91, 3, 'image-20240619174124702.png', '/blog_image_upload_folder_path/20240803/a888d0ec-54f5-4b36-ac7f-758db5556e3d_1722661738012.png', '2024-08-03 13:08:58', '2024-08-03 13:08:58', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (92, 3, 'e623c35b43c0bf2517c892f1c0dd1ea9.png', '/blog_image_upload_folder_path/20240803/a20ff082-096e-4f4e-b9b4-366f04dc00e5_1722661738012.png', '2024-08-03 13:08:58', '2024-08-03 13:08:58', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (93, 3, 'image-20240619174844242.png', '/blog_image_upload_folder_path/20240803/fbbc48ec-2e3c-4bbf-958a-05fa4c2a4f60_1722661738058.png', '2024-08-03 13:08:58', '2024-08-03 13:08:58', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (94, 3, 'image-20240625174231388.png', '/blog_image_upload_folder_path/20240803/21b8d8ca-e8b1-40c9-a62e-2de7052dd917_1722661738136.png', '2024-08-03 13:08:58', '2024-08-03 13:08:58', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (95, 3, 'image-20240625174405304.png', '/blog_image_upload_folder_path/20240803/6d82f968-2f60-4976-9381-032e39313540_1722661738198.png', '2024-08-03 13:08:58', '2024-08-03 13:08:58', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (96, 3, 'image-20240627161303822.png', '/blog_image_upload_folder_path/20240803/ee9a0dd7-f775-4dfd-b943-e3bc7f1bddbc_1722661738214.png', '2024-08-03 13:08:58', '2024-08-03 13:08:58', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (97, 3, 'image-20240627152606796.png', '/blog_image_upload_folder_path/20240803/a4c27ca0-a891-41a0-a008-04096bb42d39_1722661738369.png', '2024-08-03 13:08:58', '2024-08-03 13:08:58', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (98, 3, 'image-20240627152627594.png', '/blog_image_upload_folder_path/20240803/390ea08a-2c87-4a5d-ba00-d16f5c6d9aab_1722661738369.png', '2024-08-03 13:08:58', '2024-08-03 13:08:58', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (99, 3, 'image-20240627152800403.png', '/blog_image_upload_folder_path/20240803/4e7db249-c968-4299-922d-f59f7fceb7eb_1722661738409.png', '2024-08-03 13:08:58', '2024-08-03 13:08:58', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (100, 3, 'image-20240628101604274.png', '/blog_image_upload_folder_path/20240803/3ad4a0a6-4db5-433a-80a3-183d90b4a0d0_1722661738502.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (101, 3, 'image-20240628102024025.png', '/blog_image_upload_folder_path/20240803/b4dd661b-6d7d-4900-a4e9-bebcf8d48759_1722661738579.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (102, 3, 'image-20240628102212226.png', '/blog_image_upload_folder_path/20240803/ee0450c9-42ee-4153-9b55-48a69c805b2c_1722661738579.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (103, 3, 'image-20240628144221050.png', '/blog_image_upload_folder_path/20240803/bb61719d-e3f9-4346-a1c1-fe645ebd408d_1722661738748.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (104, 3, 'image-20240628094306969.png', '/blog_image_upload_folder_path/20240803/f4bfbb48-927b-4812-b6c6-f84ab7d9aaef_1722661738748.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (105, 3, 'image-20240628144221050-1719556943710-23.png', '/blog_image_upload_folder_path/20240803/2d2e006e-83c9-40ed-880e-f8f4dfd20767_1722661738795.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (106, 3, 'image-20240628144307052.png', '/blog_image_upload_folder_path/20240803/47756370-a1ca-403f-bc8d-99b2f1625cf7_1722661738873.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (107, 3, 'image-20240628175555716.png', '/blog_image_upload_folder_path/20240803/5daf3b34-39fb-4f53-b2a7-468799871406_1722661738960.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (108, 3, 'image-20240628144332666.png', '/blog_image_upload_folder_path/20240803/ee27a19b-3cf1-4e5d-a513-7976a9a4bd26_1722661738975.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (109, 3, 'image-20240628175022622.png', '/blog_image_upload_folder_path/20240803/20452324-a5e4-450c-ada9-618d94828e02_1722661739115.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (110, 3, 'image-20240701100340538.png', '/blog_image_upload_folder_path/20240803/676eae4b-1a3b-458e-9cc5-fe79f8e6d7c9_1722661739115.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (111, 3, 'image-20240701100518078.png', '/blog_image_upload_folder_path/20240803/14e64362-3c11-4868-95ea-2ee6c7fc8404_1722661739162.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (112, 3, 'image-20240701100256718.png', '/blog_image_upload_folder_path/20240803/22b48259-ca62-4e4f-880a-73f659175cd6_1722661739239.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (113, 3, 'image-20240701114024663.png', '/blog_image_upload_folder_path/20240803/9f911613-0c6c-4c83-b1ad-90cfc95a1deb_1722661739318.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (114, 3, 'image-20240701100559862.png', '/blog_image_upload_folder_path/20240803/159ce765-5909-40dc-baae-956bd3503765_1722661739333.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (115, 3, 'image-20240701113950870.png', '/blog_image_upload_folder_path/20240803/a50ad86e-a501-403e-85d1-23f88c10f61d_1722661739473.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (116, 3, 'image-20240702102316697.png', '/blog_image_upload_folder_path/20240803/e9cae526-b3f3-4308-a4c2-9d11236f61e8_1722661739473.png', '2024-08-03 13:08:59', '2024-08-03 13:08:59', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (117, 3, 'image-20240716100924229.png', '/blog_image_upload_folder_path/20240803/ef795a1a-8ca7-4c5e-b803-1524b822ab02_1722661739565.png', '2024-08-03 13:09:00', '2024-08-03 13:09:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (118, 3, 'image-20240716100748373.png', '/blog_image_upload_folder_path/20240803/ed35fae1-9d32-4846-bcba-a7512b085172_1722661739643.png', '2024-08-03 13:09:00', '2024-08-03 13:09:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (119, 3, 'image-20240701182611384.png', '/blog_image_upload_folder_path/20240803/5f9bdb3d-4f1d-479c-b2ad-5212ea13cd96_1722661739674.png', '2024-08-03 13:09:00', '2024-08-03 13:09:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (120, 3, 'image-20240716104246574.png', '/blog_image_upload_folder_path/20240803/a68cbfcc-8a91-4e99-9a77-ed4b2956e57a_1722661739737.png', '2024-08-03 13:09:00', '2024-08-03 13:09:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (121, 3, 'image-20240716174450151.png', '/blog_image_upload_folder_path/20240803/58609720-8312-41d6-8206-949bb111d707_1722661739859.png', '2024-08-03 13:09:00', '2024-08-03 13:09:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (122, 3, 'image-20240716114845583.png', '/blog_image_upload_folder_path/20240803/7cf23cc2-5dd4-443d-b75b-f643b474a961_1722661739859.png', '2024-08-03 13:09:00', '2024-08-03 13:09:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (123, 3, 'image-20240716174539000.png', '/blog_image_upload_folder_path/20240803/66ec71ad-7944-46d0-9bc9-b23c9369d795_1722661739952.png', '2024-08-03 13:09:00', '2024-08-03 13:09:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (124, 3, 'image-20240716174606561.png', '/blog_image_upload_folder_path/20240803/8ec7c5c3-1b33-4449-a8bb-3178efbc29d6_1722661740029.png', '2024-08-03 13:09:00', '2024-08-03 13:09:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (125, 3, 'image-20240716174629442.png', '/blog_image_upload_folder_path/20240803/df26f279-48e9-4ca5-a6ba-74f5d6f66ef8_1722661740045.png', '2024-08-03 13:09:00', '2024-08-03 13:09:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (126, 3, 'image-20240716174651105.png', '/blog_image_upload_folder_path/20240803/394c6b4f-ab74-4f2f-9f77-d9300f1aae91_1722661740122.png', '2024-08-03 13:09:00', '2024-08-03 13:09:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (127, 3, 'image-20240718103823906.png', '/blog_image_upload_folder_path/20240803/8c2853b2-babf-401d-9b5a-ee98ef2360ec_1722661740215.png', '2024-08-03 13:09:00', '2024-08-03 13:09:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (128, 3, 'image-20240717103901348.png', '/blog_image_upload_folder_path/20240803/f61be23c-c865-43c5-b585-cd9a9375f3f2_1722661740292.png', '2024-08-03 13:09:00', '2024-08-03 13:09:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (129, 3, 'image-20240718112834137.png', '/blog_image_upload_folder_path/20240803/c17f3c86-79b7-4dba-b49d-fdf8f882edba_1722661740338.png', '2024-08-03 13:09:00', '2024-08-03 13:09:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (130, 3, 'image-20240723164207399.png', '/blog_image_upload_folder_path/20240803/3a935960-eb3e-463d-aae1-78e2d2289257_1722661740399.png', '2024-08-03 13:09:00', '2024-08-03 13:09:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (131, 3, 'image-20240726115403176.png', '/blog_image_upload_folder_path/20240803/8f93080e-fb80-465a-95a2-38a092720f4a_1722661740430.png', '2024-08-03 13:09:00', '2024-08-03 13:09:00', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (132, 3, 'image-20240717103808321.png', '/blog_image_upload_folder_path/20240803/588e6ed5-1c7a-4c3c-a033-cdda78f57a60_1722661740556.png', '2024-08-03 13:09:01', '2024-08-03 13:09:01', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (133, 3, 'image-20240730195428515.png', '/blog_image_upload_folder_path/20240803/cdc0a75d-45b1-4a84-914a-f69f3cd91d92_1722661740819.png', '2024-08-03 13:09:01', '2024-08-03 13:09:01', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (134, 3, 'image-20240803101845892.png', '/blog_image_upload_folder_path/20240803/3e4aa02a-f731-40c8-bbca-8af7f848feea_1722661740943.png', '2024-08-03 13:09:01', '2024-08-03 13:09:01', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (135, 3, 'img.png', '/blog_image_upload_folder_path/20240803/015056ac-0aa0-4f59-8501-5af5cf2a6729_1722661741083.png', '2024-08-03 13:09:01', '2024-08-03 13:09:01', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (136, 3, 'watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAc2hlbuWtkOaAoQ==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center.png', '/blog_image_upload_folder_path/20240803/4f05a121-c978-45fd-b20f-48a0799dbc6f_1722661741369.png', '2024-08-03 13:09:01', '2024-08-03 13:09:01', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (137, 3, 'watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDIwNzQwMw==,size_16,color_FFFFFF,t_70.png', '/blog_image_upload_folder_path/20240803/b79241f6-0cf8-4c2d-a0d1-6dbd99b2f7cb_1722661741449.png', '2024-08-03 13:09:01', '2024-08-03 13:09:01', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (138, 3, 'watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDIwNzQwMw==,size_16,color_FFFFFF,t_70-1719558016766-29.png', '/blog_image_upload_folder_path/20240803/9b13c1c5-63b7-4702-ac2b-1d9976c5470c_1722661741495.png', '2024-08-03 13:09:02', '2024-08-03 13:09:02', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (139, 3, 'watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDIwNzQwMw==,size_16,color_FFFFFF,t_70-1719559071424-32.png', '/blog_image_upload_folder_path/20240803/81f0023d-a949-4870-a516-76eb42c56cef_1722661741571.png', '2024-08-03 13:09:02', '2024-08-03 13:09:02', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (140, 3, 'watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ2MTAxODY5,size_16,color_FFFFFF,t_70.png', '/blog_image_upload_folder_path/20240803/dcacb98f-cd10-4391-8908-2f15f0daefe6_1722661741647.png', '2024-08-03 13:09:02', '2024-08-03 13:09:02', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (141, 3, 'watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ2MTAxODY5,size_16,color_FFFFFF,t_70-1719815317624-7.png', '/blog_image_upload_folder_path/20240803/84918dcd-3266-4af0-a914-9da2dc4e2dc8_1722661741740.png', '2024-08-03 13:09:02', '2024-08-03 13:09:02', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (142, 3, 'wps1.jpg', '/blog_image_upload_folder_path/20240803/2a1a7e74-9c46-4733-996e-b4fa1e77883a_1722661741814.jpg', '2024-08-03 13:09:02', '2024-08-03 13:09:02', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (143, 3, 'wps2.jpg', '/blog_image_upload_folder_path/20240803/c7a7a933-b251-4b24-8fa3-2d0d1e24f101_1722661741860.jpg', '2024-08-03 13:09:02', '2024-08-03 13:09:02', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (144, 3, 'wps3.jpg', '/blog_image_upload_folder_path/20240803/5ea4be9c-507b-4be2-a570-55db48a9af5b_1722661741937.jpg', '2024-08-03 13:09:02', '2024-08-03 13:09:02', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (145, 3, 'wps4.jpg', '/blog_image_upload_folder_path/20240803/de5135e0-0ef9-4cac-b12c-4c41144e7f85_1722661742015.jpg', '2024-08-03 13:09:02', '2024-08-03 13:09:02', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (146, 3, 'wps5.jpg', '/blog_image_upload_folder_path/20240803/a074d8f5-0c93-476a-93f6-a332ee7c6aaa_1722661742108.jpg', '2024-08-03 13:09:02', '2024-08-03 13:09:02', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (147, 3, 'wps7.jpg', '/blog_image_upload_folder_path/20240803/faf9a362-9ec6-4fb6-930b-68077ba129a5_1722661742170.jpg', '2024-08-03 13:09:02', '2024-08-03 13:09:02', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (148, 3, 'wps8.jpg', '/blog_image_upload_folder_path/20240803/de3112c8-3438-41f9-92a8-03867f8287de_1722661742217.jpg', '2024-08-03 13:09:02', '2024-08-03 13:09:02', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (149, 3, 'wps6.jpg', '/blog_image_upload_folder_path/20240803/82e1ecdf-3d31-4d2b-a69c-04193794b330_1722661742280.jpg', '2024-08-03 13:09:02', '2024-08-03 13:09:02', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (150, 3, 'wps9.jpg', '/blog_image_upload_folder_path/20240803/c6fc7c35-b1fa-447c-a23b-0310acbf931d_1722661742372.jpg', '2024-08-03 13:09:02', '2024-08-03 13:09:02', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (151, 3, 'wps10.jpg', '/blog_image_upload_folder_path/20240803/a7a92691-f2c8-4b1b-892a-832c8b74313b_1722661742449.jpg', '2024-08-03 13:09:02', '2024-08-03 13:09:02', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (152, 3, 'wps11.jpg', '/blog_image_upload_folder_path/20240803/ba6e6ccb-505f-467d-8d97-081f83f41ed3_1722661742542.jpg', '2024-08-03 13:09:03', '2024-08-03 13:09:03', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (153, 3, 'wps12.jpg', '/blog_image_upload_folder_path/20240803/a0a73bad-98c9-4d70-b5ab-f16de91157bb_1722661742573.jpg', '2024-08-03 13:09:03', '2024-08-03 13:09:03', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (154, 3, 'wps13.jpg', '/blog_image_upload_folder_path/20240803/b84bfdce-bd1c-4314-91b2-49d796cac0e9_1722661742637.jpg', '2024-08-03 13:09:03', '2024-08-03 13:09:03', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (155, 3, 'wps14.jpg', '/blog_image_upload_folder_path/20240803/cbb6e0eb-5bd7-4a15-9aeb-c56d4408d90e_1722661742728.jpg', '2024-08-03 13:09:03', '2024-08-03 13:09:03', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (156, 3, 'wps15.jpg', '/blog_image_upload_folder_path/20240803/e2245016-9964-420d-a7c0-c3e322ec7f2d_1722661742806.jpg', '2024-08-03 13:09:03', '2024-08-03 13:09:03', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (157, 3, 'wps16.jpg', '/blog_image_upload_folder_path/20240803/0dcaa3ab-d163-49c0-a326-422b059a3793_1722661742898.jpg', '2024-08-03 13:09:03', '2024-08-03 13:09:03', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (158, 3, 'wps17.jpg', '/blog_image_upload_folder_path/20240803/5ca58a28-2671-4e00-8fd9-460715dde3dd_1722661742944.jpg', '2024-08-03 13:09:03', '2024-08-03 13:09:03', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (159, 3, 'wps18.jpg', '/blog_image_upload_folder_path/20240803/75b6f7ec-4266-4fbd-b7e8-0a58f0eb83f3_1722661742990.jpg', '2024-08-03 13:09:03', '2024-08-03 13:09:03', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (160, 3, 'wps20.jpg', '/blog_image_upload_folder_path/20240803/6467bb4c-ab80-457e-88f2-87eb015f3c93_1722661743074.jpg', '2024-08-03 13:09:03', '2024-08-03 13:09:03', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (161, 3, 'wps23.jpg', '/blog_image_upload_folder_path/20240803/923dbf70-247b-4304-b8a0-351dc37ff00b_1722661743166.jpg', '2024-08-03 13:09:03', '2024-08-03 13:09:03', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (162, 3, 'wps19.jpg', '/blog_image_upload_folder_path/20240803/4aca29c6-6c13-4149-971b-478ffa9e49dd_1722661743260.jpg', '2024-08-03 13:09:03', '2024-08-03 13:09:03', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (163, 3, 'wps21.jpg', '/blog_image_upload_folder_path/20240803/5aeca554-82c0-4b7c-a074-f44f6e7d2eab_1722661743292.jpg', '2024-08-03 13:09:03', '2024-08-03 13:09:03', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (164, 3, 'wps22.jpg', '/blog_image_upload_folder_path/20240803/d81dad9b-a526-4d79-9039-2a215f29708f_1722661743320.jpg', '2024-08-03 13:09:03', '2024-08-03 13:09:03', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (165, 3, 'wps25.jpg', '/blog_image_upload_folder_path/20240803/897c0e46-afe9-4953-a4b9-7cf8c31f8174_1722661743429.jpg', '2024-08-03 13:09:03', '2024-08-03 13:09:03', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (166, 3, 'wps24.jpg', '/blog_image_upload_folder_path/20240803/244107dc-658f-45f8-aec3-c83f68304eb6_1722661743522.jpg', '2024-08-03 13:09:04', '2024-08-03 13:09:04', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (167, 3, 'wps27.jpg', '/blog_image_upload_folder_path/20240803/990e4f4a-b32a-4251-af70-d0de40697d45_1722661743630.jpg', '2024-08-03 13:09:04', '2024-08-03 13:09:04', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (168, 3, 'wps26.jpg', '/blog_image_upload_folder_path/20240803/1d7a0b91-a822-47b5-bdff-fa3d2553d150_1722661743630.jpg', '2024-08-03 13:09:04', '2024-08-03 13:09:04', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (169, 3, 'wps28.jpg', '/blog_image_upload_folder_path/20240803/046b0343-5d15-47a3-b803-357ba2302f77_1722661743661.jpg', '2024-08-03 13:09:04', '2024-08-03 13:09:04', 1, 0, 0, NULL);
INSERT INTO `blog_image_upload` VALUES (170, 3, '屏幕截图 2024-03-12 150427.png', '/blog_image_upload_folder_path/20240804/e052e926-e1e3-412f-a1bf-77068dc75e24_1722776351099.png', '2024-08-04 21:00:27', '2024-08-04 21:00:27', 1, 0, 0, NULL);

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
  `permission_scope` int NULL DEFAULT NULL COMMENT '权限范围(1-博客资源，2-视频资源)',
  `permission_category` int NULL DEFAULT NULL COMMENT '权限类别(1-Read,2-Write,3-Read/Write,4-Other)',
  `menu_form_id` bigint NULL DEFAULT NULL COMMENT '自定义Form唯一ID',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT ' ' COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT ' ' COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志，0未删除，1已删除',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_permission
-- ----------------------------
INSERT INTO `blog_permission` VALUES (1, 3, 'common_blog', 'common_blog', 1, 1, 1, 1, ' fang', '2024-08-05 13:31:04', ' fang', '2024-08-05 13:30:58', 0, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_role
-- ----------------------------
INSERT INTO `blog_role` VALUES (1, '普通用户', 'common_user', '基本用户，每个注册的用户默认都为该角色', ' fang', ' fang', '2024-08-05 11:55:13', '2024-08-05 11:55:15', '0', '1');

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
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
  `permission_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rel_role_permission
-- ----------------------------
INSERT INTO `rel_role_permission` VALUES (1, 1);

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
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rel_user_role
-- ----------------------------
INSERT INTO `rel_user_role` VALUES (3, 1, '2024-08-05 11:55:48', '3046-08-05 11:55:52');

SET FOREIGN_KEY_CHECKS = 1;
