/*
 Navicat Premium Data Transfer

 Source Server         : CentOS_MYSQL_DB
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 192.168.47.3:3306
 Source Schema         : xxzx-oauth

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 21/11/2024 20:07:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `bh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `client_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用标识',
  `client_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '应用名称',
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源限定串(逗号分割)',
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用密钥(bcyt) 加密',
  `client_secret_str` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用密钥(明文)',
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '范围',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '5种oauth授权方式(authorization_code,password,refresh_token,client_credentials)',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回调地址 ',
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限',
  `access_token_validity` int(11) NULL DEFAULT NULL COMMENT 'access_token有效期',
  `refresh_token_validity` int(11) NULL DEFAULT NULL COMMENT 'refresh_token有效期',
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '{}' COMMENT '{}',
  `autoapprove` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'true' COMMENT '是否自动授权 是-true',
  `cjrbh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人编号',
  `cjsj` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `czrbh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人编号',
  `czsj` datetime NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`bh`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('1', 'webApp', 'pc端', '', '$2a$10$.62TGUmPdpRT93MD6wLzdudX1jHp6SYns3.jCr9Qy6aymBiYO3gzC', 'webApp', 'all', 'authorization_code,password,refresh_token,client_credentials,implicit,password_code,openId,mobile_password', '', '', 3600, 28800, '{}', 'true', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for oauth_login_log
-- ----------------------------
DROP TABLE IF EXISTS `oauth_login_log`;
CREATE TABLE `oauth_login_log`  (
  `bh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `czybh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作员编号',
  `czydlzh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作员登录账号',
  `czymc` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员名称',
  `dllx` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登入登出',
  `ipdz` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `dlsj` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录时间',
  PRIMARY KEY (`bh`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '登录日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_login_log
-- ----------------------------
INSERT INTO `oauth_login_log` VALUES ('01f9bdc9231ab8ddc313c0db2075ad88', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-19 09:19:25.937');
INSERT INTO `oauth_login_log` VALUES ('02a0d685c835e3768f7098d5a6e87271', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-21 08:56:06.44');
INSERT INTO `oauth_login_log` VALUES ('0665d17d93ed493d3ca6462f2880c81b', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-21 09:03:12.967');
INSERT INTO `oauth_login_log` VALUES ('067e266c0577184a490cf8878c4fed4e', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-20 18:25:12.734');
INSERT INTO `oauth_login_log` VALUES ('0785ee17309fe48d5bfa1431c1c12b28', '01bf2fe69b756d264c9d05c1e6714467', 'gmgds-gly', NULL, '登出', '127.0.0.1', '2024-11-21 15:17:49.511');
INSERT INTO `oauth_login_log` VALUES ('07dce181f7a0af47d9485e375f771372', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-20 08:58:29.152');
INSERT INTO `oauth_login_log` VALUES ('086f4eec20519ca38c6cac964580ac53', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-19 09:21:55.455');
INSERT INTO `oauth_login_log` VALUES ('08e46f0b660ee9bf7daa9df25b4e2cef', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-20 18:39:37.213');
INSERT INTO `oauth_login_log` VALUES ('0af412d67179a05f9a501cbe6ee0559f', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-21 16:14:56.985');
INSERT INTO `oauth_login_log` VALUES ('0e63e2c8d2860828d4af97c11303313d', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-19 15:59:39.824');
INSERT INTO `oauth_login_log` VALUES ('0ecabea5f5d08aa9236500a4b5f6b52f', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-20 16:55:45.591');
INSERT INTO `oauth_login_log` VALUES ('0f8d9dad8277a2f4833cc4741ca77b95', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-20 18:13:29.779');
INSERT INTO `oauth_login_log` VALUES ('10294ccd9ed7e4b578ec2cf737eeca48', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-20 14:50:46.805');
INSERT INTO `oauth_login_log` VALUES ('103dd76bdd8efbebfd89fad464f42d2d', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-18 19:03:56.174');
INSERT INTO `oauth_login_log` VALUES ('1049b2c67acc814894f80d54f6e8fd70', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-19 15:59:29.612');
INSERT INTO `oauth_login_log` VALUES ('1395e054fb38d22b45e7a6f371f85090', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-21 15:09:35.337');
INSERT INTO `oauth_login_log` VALUES ('13eff2843608ed2f615832956af067d7', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-19 14:52:10.663');
INSERT INTO `oauth_login_log` VALUES ('1562ea84b8760db90da70c57cf7ff3f3', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-20 09:18:19.787');
INSERT INTO `oauth_login_log` VALUES ('16a667f2c3f2b62b1add059565218456', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-19 16:21:52.844');
INSERT INTO `oauth_login_log` VALUES ('1d57f8a7829ca4576c7435c102073aba', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-21 15:32:56.772');
INSERT INTO `oauth_login_log` VALUES ('1d7888bd663c9605286d94e78757b025', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-19 09:50:47.197');
INSERT INTO `oauth_login_log` VALUES ('1f264764860a27e37e4539509814fc19', '01bf2fe69b756d264c9d05c1e6714467', 'gmgds-gly', NULL, '登入', '192.168.208.1', '2024-11-21 15:13:58.923');
INSERT INTO `oauth_login_log` VALUES ('200ea57509e3ad9ef1d8d50c894896a2', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-21 18:09:47.963');
INSERT INTO `oauth_login_log` VALUES ('25a53421c7b021571099704935886529', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-20 18:40:14.158');
INSERT INTO `oauth_login_log` VALUES ('2696d74f88f41e39bcca25296b9895ad', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-21 18:19:19.393');
INSERT INTO `oauth_login_log` VALUES ('2d2c356e5e10291bb42ac1e07d2589db', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-20 11:53:49.788');
INSERT INTO `oauth_login_log` VALUES ('2e0cd5d01f27ce8a8e6178e8072e1d3b', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-20 18:39:47.056');
INSERT INTO `oauth_login_log` VALUES ('346aaf67decb404d0e5b2f8c707057eb', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-19 16:22:05.217');
INSERT INTO `oauth_login_log` VALUES ('362ca8dc986c2ee32fb10d076635d54d', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-19 10:19:20.644');
INSERT INTO `oauth_login_log` VALUES ('37738416dad667ebec5708e747bf18b6', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-19 08:56:13.486');
INSERT INTO `oauth_login_log` VALUES ('37746c8994ed1d8c1ad4a6611eab8606', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-21 17:28:46.004');
INSERT INTO `oauth_login_log` VALUES ('41495ed283ba8be7cfa92813f6724a60', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-18 19:02:26.257');
INSERT INTO `oauth_login_log` VALUES ('4387dcb03c96aba088f0f182aa4263e8', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-20 14:39:51.743');
INSERT INTO `oauth_login_log` VALUES ('4848a0e5b84d9f4c247111dfa2100cff', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-18 18:51:54.525');
INSERT INTO `oauth_login_log` VALUES ('49a3d0c14faf603e63f12a1c12c9b5cd', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-19 09:32:21.429');
INSERT INTO `oauth_login_log` VALUES ('4a098ec5f23338b97b7ccb6d4f192cd8', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-19 09:32:29.782');
INSERT INTO `oauth_login_log` VALUES ('4e999cbbf4e3979461a5ca54dac66fb5', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-20 18:25:20.973');
INSERT INTO `oauth_login_log` VALUES ('4eca1d5c216b4b5b7b018ca4e95f8238', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-21 17:59:44.684');
INSERT INTO `oauth_login_log` VALUES ('50c956f4d1775a28eab0f4e13137b876', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-19 08:43:23.338');
INSERT INTO `oauth_login_log` VALUES ('5248aa98e6e450657fbf0215201f48dd', '3b0ab197adf19e9bb92ba3b8e0d7c0e6', 'mtgds-gly', NULL, '登入', '192.168.208.1', '2024-11-21 16:15:44.339');
INSERT INTO `oauth_login_log` VALUES ('599b713a3dbd190688a7cd0a901c3068', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-19 09:51:38.85');
INSERT INTO `oauth_login_log` VALUES ('5ae1e17e5eb29c6b71ba010257ea79b5', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-21 17:58:47.577');
INSERT INTO `oauth_login_log` VALUES ('5c8c64ab620c1a112ad16a541be8c07e', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-21 17:15:47.63');
INSERT INTO `oauth_login_log` VALUES ('6111a14d0e20f13c98b225759c29ae89', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-20 16:52:25.544');
INSERT INTO `oauth_login_log` VALUES ('6461b3886178b21617a8fa599ee19a34', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-19 14:44:44.482');
INSERT INTO `oauth_login_log` VALUES ('6884d6c250d6e74eb6b3550f68738c68', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-20 14:39:35.874');
INSERT INTO `oauth_login_log` VALUES ('6897c589b44e94fc1b54925bca91c60d', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-20 14:39:18.735');
INSERT INTO `oauth_login_log` VALUES ('6916985ce6ef6c38eaa78a82330ab3dc', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-20 16:44:15.36');
INSERT INTO `oauth_login_log` VALUES ('6b7f5795910edd7147e08733bfef3faf', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-21 15:08:57.326');
INSERT INTO `oauth_login_log` VALUES ('6b9d24674577152e9ab7cdc480c92ec8', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-19 14:38:14.932');
INSERT INTO `oauth_login_log` VALUES ('6c08aefdc7af6f171d9766c81d006fc0', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-19 14:47:38.305');
INSERT INTO `oauth_login_log` VALUES ('6cbcccd31ebc0ed03c09bb159ec34c7e', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-18 19:04:44.861');
INSERT INTO `oauth_login_log` VALUES ('6eab69b515f0e637ecc23128dab5a84a', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-20 16:46:25.822');
INSERT INTO `oauth_login_log` VALUES ('6fe92651e0502fac1c168639f5a89228', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-20 17:41:02.668');
INSERT INTO `oauth_login_log` VALUES ('702b38d2175d076fb5f14bf5b79a2cb6', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-20 16:44:03.448');
INSERT INTO `oauth_login_log` VALUES ('70a57d891b810ab3177306ad6800ef60', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-20 18:27:08.059');
INSERT INTO `oauth_login_log` VALUES ('72c8776cb5f521399d2f347aa59b36c6', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-20 11:54:14.172');
INSERT INTO `oauth_login_log` VALUES ('73591768a2ed96cbe482bc2254ce9341', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-19 15:23:19.019');
INSERT INTO `oauth_login_log` VALUES ('7396491c84a23b5910ff08beca35a3fc', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-21 15:06:51.759');
INSERT INTO `oauth_login_log` VALUES ('788f02d7f03a0608a360c6589eb98793', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-20 18:27:23.165');
INSERT INTO `oauth_login_log` VALUES ('7a3280919997549a57ba69a19f26680b', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-18 19:11:08.644');
INSERT INTO `oauth_login_log` VALUES ('7a66c9ce3d0944ec27238de5b0241ba2', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-19 14:49:48.439');
INSERT INTO `oauth_login_log` VALUES ('7afbc9dbbb75cbb4d574fafdaf871b14', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-19 17:57:01.79');
INSERT INTO `oauth_login_log` VALUES ('7c567db8480fe584c967cb4aef25ed9c', 'd4f713dbd91a5925227400bb5a9d92cc', 'khcqsj-gly', NULL, '登出', '127.0.0.1', '2024-11-21 09:39:43.092');
INSERT INTO `oauth_login_log` VALUES ('802fdb9146ea67530811bcf44f4c1e3d', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-21 18:08:48.894');
INSERT INTO `oauth_login_log` VALUES ('82796aa5a9bd761cf20b06465ae09661', 'd4f713dbd91a5925227400bb5a9d92cc', 'khcqsj-gly', NULL, '登入', '192.168.208.1', '2024-11-21 08:58:58.319');
INSERT INTO `oauth_login_log` VALUES ('82b7ac1f8e6de88ba31ca217170dea9e', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-21 08:47:49.526');
INSERT INTO `oauth_login_log` VALUES ('869a85c3603839c2075872b6589345d3', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-21 18:17:59.564');
INSERT INTO `oauth_login_log` VALUES ('86bc624d17429a83310b7d86c6b81fe9', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-19 11:35:36.395');
INSERT INTO `oauth_login_log` VALUES ('88bc42deea3b6543b80d53e992f4cf43', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-21 15:13:20.389');
INSERT INTO `oauth_login_log` VALUES ('896d8588d448844d73b2ef9cd57afc49', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-20 16:46:14.472');
INSERT INTO `oauth_login_log` VALUES ('8a4d97efc27b221dc34e1ca66b0b2cd3', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-19 17:35:22.269');
INSERT INTO `oauth_login_log` VALUES ('8ac3010bfd12142cb8f6c67126d9791b', '2417d1aa5f170326c4c649c8f4ed177c', 'dhgds-gly', NULL, '登出', '127.0.0.1', '2024-11-21 17:10:37.401');
INSERT INTO `oauth_login_log` VALUES ('8e5b905692f642ac56c132a9739eb5e0', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-19 09:21:42.563');
INSERT INTO `oauth_login_log` VALUES ('8fabaf48a05f98976ef70c58bb44d4d3', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-19 14:46:48.995');
INSERT INTO `oauth_login_log` VALUES ('93751e7b62194f0e7b7ee22a5adea549', 'c2e5e510d726181fc5820e80903ecb1b', 'khjsj-gly', NULL, '登入', '192.168.208.1', '2024-11-21 09:45:41.816');
INSERT INTO `oauth_login_log` VALUES ('94c95f48b5097456b3a324ea506fe535', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-19 09:49:06.314');
INSERT INTO `oauth_login_log` VALUES ('97675720dce5103b217efbf3cbde8e2c', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-19 08:47:23.804');
INSERT INTO `oauth_login_log` VALUES ('97daaea3a88f989fee810b0c8a60601e', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-19 17:35:48.312');
INSERT INTO `oauth_login_log` VALUES ('9c4e7f62b6faa6ede5a0447cb887d29d', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-20 17:08:46.459');
INSERT INTO `oauth_login_log` VALUES ('9e5e61788cbcae403af80ea294ea165a', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-19 16:21:17.34');
INSERT INTO `oauth_login_log` VALUES ('a611c4715b48492ff6bd0b4cfb8cba34', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-21 18:19:07.268');
INSERT INTO `oauth_login_log` VALUES ('a760805a11bf72c45bfe33220ef0726c', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-18 19:10:17.454');
INSERT INTO `oauth_login_log` VALUES ('a9f3f4a39cac58a12d5ddb5dc433422c', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-21 18:03:50.303');
INSERT INTO `oauth_login_log` VALUES ('aa169c513d8b84ddbc1e844cd238a6d3', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-20 17:08:37.425');
INSERT INTO `oauth_login_log` VALUES ('ab988af5ebd576cd5d952b2f021afb68', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-21 08:54:35.03');
INSERT INTO `oauth_login_log` VALUES ('ac9853074c148469be5abba690be80d5', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-20 09:18:29.187');
INSERT INTO `oauth_login_log` VALUES ('aebd7ca35cf6baa294040eeb8de4b869', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-20 18:23:51.931');
INSERT INTO `oauth_login_log` VALUES ('b08d5eab4e76034beda15b044618f947', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-20 16:13:32.268');
INSERT INTO `oauth_login_log` VALUES ('b1435e5c57449c9457bec7ed1f3b2190', '2417d1aa5f170326c4c649c8f4ed177c', 'dhgds-gly', NULL, '登入', '192.168.208.1', '2024-11-21 17:09:37.431');
INSERT INTO `oauth_login_log` VALUES ('c2b72c874ba6d56d826f15b6242f59a3', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-19 08:47:33.689');
INSERT INTO `oauth_login_log` VALUES ('c5274eadd13808e703091533c6dfa11e', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-19 09:19:18.166');
INSERT INTO `oauth_login_log` VALUES ('c9c4a6a178a5b8c70ebc29f6e4eff4d6', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-19 08:56:32.242');
INSERT INTO `oauth_login_log` VALUES ('d0159affcdf4f5afc714e354959deef8', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-21 16:27:58.379');
INSERT INTO `oauth_login_log` VALUES ('d2139b608d9f8656906ac71f74a3a6ca', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-21 09:03:19.515');
INSERT INTO `oauth_login_log` VALUES ('d37875e4cb044560c3368ff3a67858cf', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-19 16:36:13.392');
INSERT INTO `oauth_login_log` VALUES ('d7e65cbfe3e08b7957c10e7f7d8e7742', 'c2e5e510d726181fc5820e80903ecb1b', 'khjsj-gly', NULL, '登入', '192.168.208.1', '2024-11-21 11:05:42.515');
INSERT INTO `oauth_login_log` VALUES ('d84cd21fe70c2dae931aab760f00bc7e', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-20 18:29:00.924');
INSERT INTO `oauth_login_log` VALUES ('d96b123f9c3a04abdbbc665879ba0cb1', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-18 19:03:50.515');
INSERT INTO `oauth_login_log` VALUES ('db564e557c5ee75c4a7eadac5787fdfe', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-20 18:28:51.385');
INSERT INTO `oauth_login_log` VALUES ('dc1191c26494383c35fbb336527c3d33', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-20 11:52:02.85');
INSERT INTO `oauth_login_log` VALUES ('dc9ca38a4dedd569e0ddfb722dfda28c', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-19 08:48:06.296');
INSERT INTO `oauth_login_log` VALUES ('e05fac6ae3c3d5c5e6f73728273ef795', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-20 14:46:51.126');
INSERT INTO `oauth_login_log` VALUES ('e3c0a7aafebdd23d0cab53d64f27eb2a', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-21 10:09:13.075');
INSERT INTO `oauth_login_log` VALUES ('e62ffe7b95137ba0b92386a6aca1d552', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-20 09:10:33.505');
INSERT INTO `oauth_login_log` VALUES ('e68e38860826a13552b62282d0dcb06a', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-19 15:23:26.866');
INSERT INTO `oauth_login_log` VALUES ('e72d2cf6b1cc29d0993204b24e8dff69', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-20 18:23:36.851');
INSERT INTO `oauth_login_log` VALUES ('e7e62ded396200a9b7448614f8d44f65', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-19 16:36:00.834');
INSERT INTO `oauth_login_log` VALUES ('e87c3339d2f3f2713e28048ff9ca0a7f', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-19 10:18:44.552');
INSERT INTO `oauth_login_log` VALUES ('e8a68125566830f23b5aecf8496bb044', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-19 08:47:40.713');
INSERT INTO `oauth_login_log` VALUES ('e9ef552ba23128edb23a3ab1e49c3352', '3b0ab197adf19e9bb92ba3b8e0d7c0e6', 'mtgds-gly', NULL, '登出', '127.0.0.1', '2024-11-21 16:17:17.048');
INSERT INTO `oauth_login_log` VALUES ('eabba9653f02d53aedf6d782dabeaf39', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-20 18:08:39.408');
INSERT INTO `oauth_login_log` VALUES ('eb02b3d2e523b41b27d095db4dd51407', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-19 16:21:25.612');
INSERT INTO `oauth_login_log` VALUES ('f0fd51b32527a12f011a37271f9d3b37', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-20 11:53:49.788');
INSERT INTO `oauth_login_log` VALUES ('f1d69d74f28932ea549133007a98c806', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-20 09:10:49.707');
INSERT INTO `oauth_login_log` VALUES ('f1ef04d4f5d8cd92c3a56d1aae3aaa23', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-19 14:44:13.669');
INSERT INTO `oauth_login_log` VALUES ('f2ab1dae01d73719ec09db5853ac0e33', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-19 14:49:53.998');
INSERT INTO `oauth_login_log` VALUES ('f3c6230a8259e64c5b21b83423d88a2b', '1', 'ycydjcgjgly', NULL, '登入', '192.168.208.1', '2024-11-21 19:36:17.656');
INSERT INTO `oauth_login_log` VALUES ('f65f818b4c31475b240306c55b4b465b', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登出', '127.0.0.1', '2024-11-18 19:10:00.603');
INSERT INTO `oauth_login_log` VALUES ('f92430e7e68b9c0fb1ec20c273fb9d92', '1', 'ycydjcgjgly', NULL, '登出', '127.0.0.1', '2024-11-19 09:47:42.665');
INSERT INTO `oauth_login_log` VALUES ('ffa627b4cc834758b41fbc1833dd45a8', '44c4515a38406b06fdd9e49571e8863d', 'cjgly-cjgly', NULL, '登入', '192.168.208.1', '2024-11-18 19:12:18.522');

SET FOREIGN_KEY_CHECKS = 1;
