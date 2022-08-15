/*
 Navicat Premium Data Transfer

 Source Server         : 本机mysql
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : yed

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 03/08/2018 18:29:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `real_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `portrait` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_time` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `admin_username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1714770640800407552', 'admin', 'w', 'w', '112', '12', '12', '2018-08-01 12:30:30');
INSERT INTO `admin` VALUES ('1714771431074390016', 'l', 'l', 'l2', '156', '121@173.com', 'l', '2018-08-01 14:53:30');
INSERT INTO `admin` VALUES ('1715086406796009472', 'abc', '', 'abc', '1212', 'abc', '123', '123123');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `admin_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联admin id',
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联role id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('1', '1714770640800407552', '1717049481728188416');

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `money` int(16) NULL DEFAULT NULL COMMENT '面额',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态,0为使用,1已使用',
  `admin_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'adminId',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '优惠券' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon
-- ----------------------------
INSERT INTO `coupon` VALUES ('1211', 10, '1', '1025');

-- ----------------------------
-- Table structure for role_perm
-- ----------------------------
DROP TABLE IF EXISTS `role_perm`;
CREATE TABLE `role_perm`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'role表id',
  `perm_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'perm表id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_perm
-- ----------------------------
INSERT INTO `role_perm` VALUES ('1', '1717049481728188416', '1720239405478535168');
INSERT INTO `role_perm` VALUES ('2', '1717049481728188416', '1720244318921121792');
INSERT INTO `role_perm` VALUES ('3', '1717049481728188416', '1720245487152226304');
INSERT INTO `role_perm` VALUES ('4', '1717049481728188416', '1720245830749609984');

-- ----------------------------
-- Table structure for standard
-- ----------------------------
DROP TABLE IF EXISTS `standard`;
CREATE TABLE `standard`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '校名',
  `age` int(8) NULL DEFAULT NULL COMMENT '校龄',
  `area` decimal(12, 2) NULL DEFAULT NULL COMMENT '面积',
  `descr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `top` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否高级学校',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of standard
-- ----------------------------
INSERT INTO `standard` VALUES ('1654906080958701568', '第一中学', 10, 250.65, '很好', '2018-07-12 10:55:06', '1');
INSERT INTO `standard` VALUES ('1654911063120764928', '第一中学', 10, 250.65, '很好', '2018-07-12 10:57:31', '0');
INSERT INTO `standard` VALUES ('1657680080076103680', '第3中学', 10, 250.65, '很好', '2018-07-13 09:20:40', '1');
INSERT INTO `standard` VALUES ('1658349132901605376', '第8中学', 11, 220.65, '很好', '2018-07-13 14:45:12', '1');
INSERT INTO `standard` VALUES ('1658349682657419264', '第2中学', 8, 120.65, '很好', '2018-07-13 14:45:28', '1');
INSERT INTO `standard` VALUES ('1658350266772971520', '第9中学', 2, 100.65, '一般', '2018-07-13 14:45:45', '0');
INSERT INTO `standard` VALUES ('1658355729971372032', '第5中学', 5, 130.65, '一般', '2018-07-13 14:48:24', '0');
INSERT INTO `standard` VALUES ('1658356211007709184', '第11中学', 11, 110.65, '一般', '2018-07-13 14:48:38', '0');
INSERT INTO `standard` VALUES ('1658357104360906752', '第3中学', 29, 50.65, '很好', '2018-07-13 14:49:04', '1');
INSERT INTO `standard` VALUES ('1667269470737752064', '福州附中', 12, 12.00, '好', '2018-07-16 14:52:08', '1');
INSERT INTO `standard` VALUES ('1667379284461576192', '马尾私立学校', 5, 520.00, '厉害', '2018-07-16 15:45:24', '1');
INSERT INTO `standard` VALUES ('1667383338910703616', '马尾鳌峰中学', 12, 30.00, '高级中学12', '2018-07-16 15:47:22', '0');
INSERT INTO `standard` VALUES ('1687958190763630592', 'string', 0, 0.00, 'string', '2018-07-23 14:07:29', '1');

-- ----------------------------
-- Table structure for sys_perm
-- ----------------------------
DROP TABLE IF EXISTS `sys_perm`;
CREATE TABLE `sys_perm`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `identify` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '唯一标识符',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `parent_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父id',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '0菜单,1按钮',
  `routeName` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由名称,可为空',
  `url_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '除掉host外的绝对路径',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_perm_index`(`identify`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_perm
-- ----------------------------
INSERT INTO `sys_perm` VALUES ('1720239405478535168', '系统用户', 'adminMgr', 'fa fa-jw', NULL, 0, '', '');
INSERT INTO `sys_perm` VALUES ('1720244318921121792', '管理员管理', 'admin', 'fa fa-jw', '1720239405478535168', 0, 'admin', '');
INSERT INTO `sys_perm` VALUES ('1720245487152226304', '角色管理', 'sysRole', '', '1720239405478535168', 0, 'sysRole', '');
INSERT INTO `sys_perm` VALUES ('1720245830749609984', '权限管理', 'sysPerm', '', '1720239405478535168', 0, 'sysPerm', '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `create_time` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1717049481728188416', '处长1', '2018-07-01 12:30:00');
INSERT INTO `sys_role` VALUES ('1717050340721647616', '科长', '2018-06-01 05:00:00');

SET FOREIGN_KEY_CHECKS = 1;
