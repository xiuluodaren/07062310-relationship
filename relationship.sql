/*
 Navicat Premium Data Transfer

 Source Server         : aliyunRDS
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : rm-2ze9c6q06e8q31530ho.mysql.rds.aliyuncs.com:3306
 Source Schema         : relationship

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 09/07/2019 08:51:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for active
-- ----------------------------
DROP TABLE IF EXISTS `active`;
CREATE TABLE `active`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详情',
  `startTime` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `endTime` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `initiator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发起方',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总结',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `participant` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参与者列表,逗号分隔',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '活动表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of active
-- ----------------------------
INSERT INTO `active` VALUES (1, '活动1', '这是一个活动1', '2019-07-07 19:53:41', '2019-07-07 20:53:44', '修罗大人', '北斗星域', '挺成功', '2019-07-07 19:54:21', NULL);
INSERT INTO `active` VALUES (3, '2', '2		', '2019-07-08 10:42:58', '2019-07-08 10:42:58', '2', '2', '2', '2019-07-08 10:43:07', NULL);
INSERT INTO `active` VALUES (4, '4', '4	', '2019-07-08 11:16:28', '2019-07-08 11:16:28', '4', '4', '4', '2019-07-08 11:16:36', NULL);
INSERT INTO `active` VALUES (6, '2', 'fasdaffasfasfasf	', '2019-07-09 12:00:00', '2019-07-10 17:28:00', '2', '2', '2	2', '2019-07-08 15:49:41', '22222');

-- ----------------------------
-- Table structure for cooperation
-- ----------------------------
DROP TABLE IF EXISTS `cooperation`;
CREATE TABLE `cooperation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `cooperationerId` int(11) NULL DEFAULT NULL COMMENT '合作人',
  `cooperationerName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '合作人姓名',
  `startTime` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `endTime` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '合作详情',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '合作状态  0进行中 1已结束',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '合作表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cooperation
-- ----------------------------
INSERT INTO `cooperation` VALUES (1, '1', 1, '1', '2019-07-08 12:47:19', '2019-07-08 12:47:22', '1', '1');
INSERT INTO `cooperation` VALUES (2, '2', NULL, '2', '2019-07-08 13:40:54', '2019-07-08 13:40:54', '2', '1');
INSERT INTO `cooperation` VALUES (3, '1111', NULL, '101010', '2019-07-08 15:49:45', '2019-07-08 15:49:46', '1', '0');

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `mobilePhone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `QQ` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'QQ',
  `wx` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'wx',
  `socialCircleId` int(11) NULL DEFAULT NULL COMMENT '社交圈id',
  `socialCircleName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '社交圈名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '联系人表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES (1, '联系人1', '男', '1312345678', '1448564807@qq.com', '12345678', 2, '社交圈2');
INSERT INTO `friend` VALUES (2, '联系人2', '1', '1', '1', '1', 1, '社交圈1');
INSERT INTO `friend` VALUES (3, '2', '2', '2', '2', '2', 1, '社交圈1');

-- ----------------------------
-- Table structure for gift
-- ----------------------------
DROP TABLE IF EXISTS `gift`;
CREATE TABLE `gift`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '礼物名称',
  `recvOrSend` int(1) NULL DEFAULT NULL COMMENT '收送方式 0收 1送',
  `liking` int(1) NULL DEFAULT NULL COMMENT '喜欢程度 0一般 1喜欢 2很喜欢',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `cause` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '赠送原因',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '礼物表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gift
-- ----------------------------
INSERT INTO `gift` VALUES (12, '1111', 0, 2, 1111.00, '111', '2019-07-08 15:22:30', '111');
INSERT INTO `gift` VALUES (13, '10101', 0, 2, 101010.00, '10101010', '2019-07-08 15:49:57', '1010101');

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `userName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `type` int(11) NULL DEFAULT NULL COMMENT '用户类型 0管理员 1客户',
  `loginTime` datetime(0) NULL DEFAULT NULL COMMENT '登陆时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登陆日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_log
-- ----------------------------
INSERT INTO `login_log` VALUES (20, 1, 'admin', 0, '2019-07-07 23:15:11');
INSERT INTO `login_log` VALUES (21, 1, 'admin', 0, '2019-07-07 23:16:50');
INSERT INTO `login_log` VALUES (22, 1, 'admin', 0, '2019-07-07 23:20:58');
INSERT INTO `login_log` VALUES (23, 1, 'admin', 0, '2019-07-07 23:21:59');
INSERT INTO `login_log` VALUES (24, 1, 'admin', 0, '2019-07-07 23:24:09');
INSERT INTO `login_log` VALUES (25, 1, 'admin', 0, '2019-07-07 23:25:54');
INSERT INTO `login_log` VALUES (26, 1, 'admin', 0, '2019-07-07 23:27:29');
INSERT INTO `login_log` VALUES (27, 1, 'admin', 0, '2019-07-07 23:30:38');
INSERT INTO `login_log` VALUES (28, 1, 'admin', 0, '2019-07-07 23:34:41');
INSERT INTO `login_log` VALUES (29, 1, 'admin', 0, '2019-07-07 23:44:23');
INSERT INTO `login_log` VALUES (30, 1, 'admin', 0, '2019-07-08 08:51:18');
INSERT INTO `login_log` VALUES (31, 1, 'admin', 0, '2019-07-08 08:56:28');
INSERT INTO `login_log` VALUES (32, 1, 'admin', 0, '2019-07-08 10:42:47');
INSERT INTO `login_log` VALUES (33, 1, 'admin', 0, '2019-07-08 11:01:05');
INSERT INTO `login_log` VALUES (34, 1, 'admin', 0, '2019-07-08 11:13:04');
INSERT INTO `login_log` VALUES (35, 1, 'admin', 0, '2019-07-08 11:13:55');
INSERT INTO `login_log` VALUES (36, 1, 'admin', 0, '2019-07-08 11:14:54');
INSERT INTO `login_log` VALUES (37, 1, 'admin', 0, '2019-07-08 11:16:17');
INSERT INTO `login_log` VALUES (38, 1, 'admin', 0, '2019-07-08 12:01:05');
INSERT INTO `login_log` VALUES (39, 1, 'admin', 0, '2019-07-08 12:02:55');
INSERT INTO `login_log` VALUES (40, 1, 'admin', 0, '2019-07-08 13:21:59');
INSERT INTO `login_log` VALUES (41, 1, 'admin', 0, '2019-07-08 13:23:04');
INSERT INTO `login_log` VALUES (42, 1, 'admin', 0, '2019-07-08 13:24:22');
INSERT INTO `login_log` VALUES (43, 1, 'admin', 0, '2019-07-08 13:27:43');
INSERT INTO `login_log` VALUES (44, 1, 'admin', 0, '2019-07-08 13:28:20');
INSERT INTO `login_log` VALUES (45, 1, 'admin', 0, '2019-07-08 13:28:44');
INSERT INTO `login_log` VALUES (46, 1, 'admin', 0, '2019-07-08 13:32:56');
INSERT INTO `login_log` VALUES (47, 1, 'admin', 0, '2019-07-08 13:35:06');
INSERT INTO `login_log` VALUES (48, 1, 'admin', 0, '2019-07-08 13:39:11');
INSERT INTO `login_log` VALUES (49, 1, 'admin', 0, '2019-07-08 13:40:37');
INSERT INTO `login_log` VALUES (50, 1, 'admin', 0, '2019-07-08 13:56:47');
INSERT INTO `login_log` VALUES (51, 1, 'admin', 0, '2019-07-08 15:15:49');
INSERT INTO `login_log` VALUES (52, 1, 'admin', 0, '2019-07-08 15:16:25');
INSERT INTO `login_log` VALUES (53, 1, 'admin', 0, '2019-07-08 15:22:01');
INSERT INTO `login_log` VALUES (54, 1, 'admin', 0, '2019-07-08 15:42:02');
INSERT INTO `login_log` VALUES (55, 1, 'admin', 0, '2019-07-08 15:47:48');
INSERT INTO `login_log` VALUES (56, 1, 'admin', 0, '2019-07-08 16:05:29');
INSERT INTO `login_log` VALUES (57, 1, 'admin', 0, '2019-07-08 16:48:45');
INSERT INTO `login_log` VALUES (58, 1, 'admin', 0, '2019-07-08 20:21:05');
INSERT INTO `login_log` VALUES (59, 1, 'admin', 0, '2019-07-08 21:07:31');
INSERT INTO `login_log` VALUES (60, 1, 'admin', 0, '2019-07-08 21:32:14');
INSERT INTO `login_log` VALUES (61, 1, 'admin', 0, '2019-07-08 22:04:16');
INSERT INTO `login_log` VALUES (62, 1, 'admin', 0, '2019-07-08 22:32:56');
INSERT INTO `login_log` VALUES (63, 1, 'admin', 0, '2019-07-08 22:44:07');
INSERT INTO `login_log` VALUES (64, 1, 'admin', 0, '2019-07-08 23:18:52');
INSERT INTO `login_log` VALUES (65, 1, 'admin', 0, '2019-07-08 23:19:35');
INSERT INTO `login_log` VALUES (66, 1, 'admin', 0, '2019-07-09 08:51:12');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menuName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `paneName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '面板名称 需与代码对应',
  `type` int(11) NULL DEFAULT NULL COMMENT '菜单级别 0-管理员 1用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '社交圈管理', 1, 'SocialCirclePane', 0);
INSERT INTO `menu` VALUES (2, '联系人管理', 2, 'FriendPane', 0);
INSERT INTO `menu` VALUES (3, '活动管理', 3, 'ActivePane', 0);
INSERT INTO `menu` VALUES (4, '合作管理', 4, 'CooperationPane', 0);
INSERT INTO `menu` VALUES (5, '礼物管理', 5, 'GiftPane', 0);

-- ----------------------------
-- Table structure for social_circle
-- ----------------------------
DROP TABLE IF EXISTS `social_circle`;
CREATE TABLE `social_circle`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `principal` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `mobilePhone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `QQ` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'QQ',
  `wx` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '社交圈表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of social_circle
-- ----------------------------
INSERT INTO `social_circle` VALUES (1, '1', '社交圈1', '1', '1', '1', '1');
INSERT INTO `social_circle` VALUES (2, '2', '社交圈2', '2', '2', '2', '2');
INSERT INTO `social_circle` VALUES (4, '3', '1', '1', '1', '1', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL COMMENT '用户类型 0管理员 1客户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', 0);

SET FOREIGN_KEY_CHECKS = 1;
