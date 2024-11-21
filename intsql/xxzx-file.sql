/*
 Navicat Premium Data Transfer

 Source Server         : CentOS_MYSQL_DB
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 192.168.47.3:3306
 Source Schema         : xxzx-file

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 21/11/2024 20:08:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xt_fjxx
-- ----------------------------
DROP TABLE IF EXISTS `xt_fjxx`;
CREATE TABLE `xt_fjxx`  (
  `bh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '编号',
  `fjmc` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `fjlj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `fjdx` decimal(11, 2) NULL DEFAULT NULL COMMENT '文件大小',
  `fjcclj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件存储路径',
  `fjhz` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件后缀',
  `fjfz` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件分组',
  `cjrbh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人编号',
  `cjsj` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `czrbh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人编号',
  `czsj` datetime NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`bh`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '附件信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xt_fjxx
-- ----------------------------
INSERT INTO `xt_fjxx` VALUES ('6ec42854e3a1612d042a1d67ffbcad37', '功能-问题趋势分析.png', NULL, 85943.00, '1551145968898936846.png', '.png', '应用图片', '1', '2022-07-24 18:03:13', '1', '2022-07-24 18:03:13');
INSERT INTO `xt_fjxx` VALUES ('71efedb33d46be5c3fbbbf1c3dae0ca8', '营销风险-录屏.mp4', NULL, 14925592.00, '1551144072498909281.mp4', '.mp4', '应用视频', '1', '2022-07-24 17:55:40', '1', '2022-07-24 17:55:40');

-- ----------------------------
-- Table structure for xt_hotword
-- ----------------------------
DROP TABLE IF EXISTS `xt_hotword`;
CREATE TABLE `xt_hotword`  (
  `hotword` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '热词',
  `scount` int(11) NULL DEFAULT NULL COMMENT '搜索次数'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xt_hotword
-- ----------------------------
INSERT INTO `xt_hotword` VALUES ('API', 5);
INSERT INTO `xt_hotword` VALUES ('统一开发', 6);
INSERT INTO `xt_hotword` VALUES ('拓邦科技', 7);
INSERT INTO `xt_hotword` VALUES ('营销', 2);
INSERT INTO `xt_hotword` VALUES ('get', 3);

SET FOREIGN_KEY_CHECKS = 1;
