/*
 Navicat Premium Data Transfer

 Source Server         : localconnect
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : design_electric

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 09/10/2022 22:50:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `account_number` int NOT NULL,
  `account_password` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `worker_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`account_number`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (101, '101password', 'Tom', 101);
INSERT INTO `account` VALUES (102, '102password', 'Jerry', 102);

-- ----------------------------
-- Table structure for checkwork
-- ----------------------------
DROP TABLE IF EXISTS `checkwork`;
CREATE TABLE `checkwork`  (
  `worker_id` int NOT NULL,
  `on_date` datetime NOT NULL,
  `off_date` datetime NOT NULL,
  `work_attitude` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `worker_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`worker_id`, `on_date`, `off_date`, `work_attitude`, `worker_name`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of checkwork
-- ----------------------------
INSERT INTO `checkwork` VALUES (101, '2021-01-01 09:00:00', '2021-01-01 16:00:00', '1', 'Tom');
INSERT INTO `checkwork` VALUES (102, '2021-12-05 09:31:43', '2021-12-05 09:31:45', '2', 'Jerry');

-- ----------------------------
-- Table structure for dangerousrecord
-- ----------------------------
DROP TABLE IF EXISTS `dangerousrecord`;
CREATE TABLE `dangerousrecord`  (
  `danger_number` int NOT NULL,
  `action_time` datetime NULL DEFAULT NULL,
  `danger_grade` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `danger_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `video_info` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`danger_number`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dangerousrecord
-- ----------------------------
INSERT INTO `dangerousrecord` VALUES (1, '2021-12-05 09:22:27', '1', 'Conflict', NULL);

-- ----------------------------
-- Table structure for detected_device_picture
-- ----------------------------
DROP TABLE IF EXISTS `detected_device_picture`;
CREATE TABLE `detected_device_picture`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `device_picture_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `detected_picture_url` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of detected_device_picture
-- ----------------------------

-- ----------------------------
-- Table structure for device_picture
-- ----------------------------
DROP TABLE IF EXISTS `device_picture`;
CREATE TABLE `device_picture`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `picture_url` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of device_picture
-- ----------------------------

-- ----------------------------
-- Table structure for devices
-- ----------------------------
DROP TABLE IF EXISTS `devices`;
CREATE TABLE `devices`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `identifier` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号',
  `picture` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `brand` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '品牌',
  `make_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产日期',
  `use_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预计使用时间',
  `his_location` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在地区',
  `person_in_charge` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `price` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '价值',
  `qrcode` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二维码',
  `state` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态 1-正常 2-故障 3-报废 4-风险',
  `types` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '种类',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of devices
-- ----------------------------
INSERT INTO `devices` VALUES ('0c8b98ee4e424d35922fab043e4bea63', '5', '0c8b98ee4e424d35922fab043e4bea63.jpg', '222', '222', '2021-05-04', '22', '2', '66195b8728e235ff79c3d8b48005a603', '222', '0c8b98ee4e424d35922fab043e4bea63.jpg', '1', '00007');
INSERT INTO `devices` VALUES ('104718bdeaa641f3ab5c3f3460b2c760', '123', NULL, 'test', 'sdf', '2021-05-06 00:00:00', '12', 'c', '66195b8728e235ff79c3d8b48005a603', '12223', '104718bdeaa641f3ab5c3f3460b2c760.jpg', '1', '12223');
INSERT INTO `devices` VALUES ('2028ba4b56c549b0a03bc3afbb07b099', '456', '2028ba4b56c549b0a03bc3afbb07b099.jpg', 'test2', 'sdf', '2021-05-06 00:00:00', '12', 'a', '66195b8728e235ff79c3d8b48005a603', '12223', '2028ba4b56c549b0a03bc3afbb07b099.jpg', '1', '12223');
INSERT INTO `devices` VALUES ('21774d278c1a43519050b96d9d2bc2d9', '00001', '21774d278c1a43519050b96d9d2bc2d9.jpg', 'test1', '111', '2021-04-19', '111', 'c区', '66195b8728e235ff79c3d8b48005a603', '20000', '21774d278c1a43519050b96d9d2bc2d9.jpg', '2', '类型一');
INSERT INTO `devices` VALUES ('35564c56e16f43fbafd5ce3b30448dae', '00003', '35564c56e16f43fbafd5ce3b30448dae.jpg', '050505', '55', '2021-04-04', '50', 'l', 'd85f6d84ac4af4aff61619694e70089e', '200', '35564c56e16f43fbafd5ce3b30448dae.jpg', '1', 'jjjj');
INSERT INTO `devices` VALUES ('5888d304857440119de31c6473d1d557', '123', '5888d304857440119de31c6473d1d557.jpg', 'test', 'sdf', '2021-05-06 00:00:00', '12', 'c', '66195b8728e235ff79c3d8b48005a603', '12223', '5888d304857440119de31c6473d1d557.jpg', '1', '12223');
INSERT INTO `devices` VALUES ('7a74614e36344804a6f6f14d65116285', '00006', '7a74614e36344804a6f6f14d65116285.jpg', '5', '5', '2021-04-20', '5', '5', '66195b8728e235ff79c3d8b48005a603', '5000', '7a74614e36344804a6f6f14d65116285.jpg', '4', '1');
INSERT INTO `devices` VALUES ('8e0c48197c5d44dbb53549ade87585d5', '3432', '8e0c48197c5d44dbb53549ade87585d5.jpg', '32', '123', '2021-05-18', '232', '321', '66195b8728e235ff79c3d8b48005a603', '333', '8e0c48197c5d44dbb53549ade87585d5.jpg', '1', 'www');
INSERT INTO `devices` VALUES ('9cdc512aebfe4fe8a6eb38e5196cdcf3', '123', '9cdc512aebfe4fe8a6eb38e5196cdcf3.jpg', 'test', 'sdf', '2021-05-06 00:00:00', '12', 'c', '66195b8728e235ff79c3d8b48005a603', '12223', '9cdc512aebfe4fe8a6eb38e5196cdcf3.jpg', '1', '12223');
INSERT INTO `devices` VALUES ('bb907bb057f9459bba2b4b33c63ddeab', '000055', 'bb907bb057f9459bba2b4b33c63ddeab.jpg', '电表甲', 'aaa', '2021-05-11', '50', 'c区域', 'd85f6d84ac4af4aff61619694e70089e', '500', 'bb907bb057f9459bba2b4b33c63ddeab.jpg', '1', '电表');
INSERT INTO `devices` VALUES ('ee9b5fc0c7424f52b58fc7980c465df4', '00008', 'ee9b5fc0c7424f52b58fc7980c465df4.jpg', 'ttt', 'u', '2021-05-11', '2', 'm', '66195b8728e235ff79c3d8b48005a603', '500', 'ee9b5fc0c7424f52b58fc7980c465df4.jpg', '4', '1');

-- ----------------------------
-- Table structure for devices_change
-- ----------------------------
DROP TABLE IF EXISTS `devices_change`;
CREATE TABLE `devices_change`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `old_manager` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原所有人',
  `change_device` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备id',
  `old_price` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原设备价值',
  `old_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原设备区域',
  `old_state` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原设备状态',
  `new_manager` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现所有人',
  `new_price` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现设备价值',
  `new_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现设备区域',
  `new_state` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现设备状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of devices_change
-- ----------------------------
INSERT INTO `devices_change` VALUES ('0a263909310ddc120c956ddf126db204', '66195b8728e235ff79c3d8b48005a603', 'd0fe956f719c4c4a9738f19919faeacf', '2000', 'd', '2', '66195b8728e235ff79c3d8b48005a603', '2000', 'd', '1');
INSERT INTO `devices_change` VALUES ('378080cc447d961214e25917afc572bc', NULL, 'cedef9db5c42422485c618698fbd8411', '12223', '12223dfs ', '1', 'null', '12223', '12223dfs ', '1');
INSERT INTO `devices_change` VALUES ('3e2eedb09f917c64411ea32aacb012ba', 'null', 'cedef9db5c42422485c618698fbd8411', '12223', '12223dfs ', '1', 'null', '12223', '12223dfs ', '1');
INSERT INTO `devices_change` VALUES ('74866b290d63ea2c9302c2959d41a53f', '66195b8728e235ff79c3d8b48005a603', '9cdc512aebfe4fe8a6eb38e5196cdcf3', '12223', 'c', '1', '66195b8728e235ff79c3d8b48005a603', '12223', 'c', '1');
INSERT INTO `devices_change` VALUES ('87f16bf83ebe61352a9f49118b1711f6', 'e7b4088f2d79ec763f0467b12f1d575d', '8e0c48197c5d44dbb53549ade87585d5', '333', '321', '1', 'e7f9d01d18a2f76924ca21af8e0ea726', '333', '321', '1');
INSERT INTO `devices_change` VALUES ('9a6535e3e21cfd3ebf87eb7bfb1d2438', '66195b8728e235ff79c3d8b48005a603', 'd0fe956f719c4c4a9738f19919faeacf', '200044', 'd44', '1', '66195b8728e235ff79c3d8b48005a603', '200044', 'd44', '1');
INSERT INTO `devices_change` VALUES ('a28e47ca780ae2002ee957fe289e903c', '66195b8728e235ff79c3d8b48005a603', 'd0fe956f719c4c4a9738f19919faeacf', '2000', 'd', '1', '66195b8728e235ff79c3d8b48005a603', '2000', 'd', '1');
INSERT INTO `devices_change` VALUES ('a6bf56ed226260a352f9468c0154a362', '66195b8728e235ff79c3d8b48005a603', 'd0fe956f719c4c4a9738f19919faeacf', '2000', 'd', '1', '66195b8728e235ff79c3d8b48005a603', '200044', 'd44', '1');
INSERT INTO `devices_change` VALUES ('a7ab0a98d8b81d037eb77b0f95f9223f', 'e7f9d01d18a2f76924ca21af8e0ea726', '8e0c48197c5d44dbb53549ade87585d5', '333', '321', '1', '66195b8728e235ff79c3d8b48005a603', '333', '321', '1');
INSERT INTO `devices_change` VALUES ('b91f1cbe3ae143af4020b32352a571d9', 'e7f9d01d18a2f76924ca21af8e0ea726', '2028ba4b56c549b0a03bc3afbb07b099', '12223', 'a', '1', '66195b8728e235ff79c3d8b48005a603', '12223', 'a', '1');
INSERT INTO `devices_change` VALUES ('bbb3c9c106847843d118a18a33ec2181', '66195b8728e235ff79c3d8b48005a603', '5888d304857440119de31c6473d1d557', '12223', 'c', '1', '66195b8728e235ff79c3d8b48005a603', '12223', 'c', '1');
INSERT INTO `devices_change` VALUES ('c3e5540d96a8d81d0c9cf90a74d5a3cd', 'e7f9d01d18a2f76924ca21af8e0ea726', '273aef52d98543de893a2787367daf68', '12223', 'a', '1', '66195b8728e235ff79c3d8b48005a603', '12223', 'a', '1');
INSERT INTO `devices_change` VALUES ('e6f16eb771dc554f416405ba7d3d71c9', 'ef75a5f3628c3ecf45559308d20f6b44', '74b8880064da4894bb67ca74f03b2d47', 'vvvvvvvvvvvvvvvvvvvv', 'vvvvvvvvvvvvvvv', '1', '1daab21af08b755d88e7c1d4e2a08ec1', 'vvvvvvvvvvvvvvvvvvvv', 'vvvvvvvvvvvvvvv', '1');
INSERT INTO `devices_change` VALUES ('e889b4d57ffda689854e9f53548d9ad8', 'ef75a5f3628c3ecf45559308d20f6b44', '74b8880064da4894bb67ca74f03b2d47', 'vvvvvvvvvvvvvvvvvvvv', 'vvvvvvvvvvvvvvv', '1', '1daab21af08b755d88e7c1d4e2a08ec0', 'vvvvvvvvvvvvvvvvvvvv', 'vvvvvvvvvvvvvvv', '1');

-- ----------------------------
-- Table structure for dutyinfo
-- ----------------------------
DROP TABLE IF EXISTS `dutyinfo`;
CREATE TABLE `dutyinfo`  (
  `duty_id` int NOT NULL,
  `duty_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`duty_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dutyinfo
-- ----------------------------
INSERT INTO `dutyinfo` VALUES (1, 'duty');

-- ----------------------------
-- Table structure for inspect
-- ----------------------------
DROP TABLE IF EXISTS `inspect`;
CREATE TABLE `inspect`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `deviceid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备id',
  `managerid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查人员id',
  `inspect_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '检查时间',
  `qualified` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合格率',
  `fault` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '故障率',
  `scrap` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报废率',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `inspect_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查地点',
  `state` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态：1合格2故障3报废4有风险',
  `picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `x` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `y` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of inspect
-- ----------------------------
INSERT INTO `inspect` VALUES ('0fecf149e68141a68c7ea98006acab8b', 'd0fe956f719c4c4a9738f19919faeacf', '66195b8728e235ff79c3d8b48005a603', '2021-04-18 23:08:28', NULL, NULL, NULL, '123456789', NULL, '1', '0fecf149e68141a68c7ea98006acab8b.png', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('2704e2384e6844a68eb23bdabe85b61f', '21774d278c1a43519050b96d9d2bc2d9', '66195b8728e235ff79c3d8b48005a603', '2021-05-26 10:37:20', NULL, NULL, NULL, 'dtee', NULL, '4', '', '', '');
INSERT INTO `inspect` VALUES ('405fa06989d44ae0918ecc7b88d9ba19', '7a74614e36344804a6f6f14d65116285', '66195b8728e235ff79c3d8b48005a603', '2021-04-19 10:41:51', NULL, NULL, NULL, '15646546546', NULL, '3', '405fa06989d44ae0918ecc7b88d9ba19.jpg', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('455e90b086c94910aa1dc683505b1d91', '21774d278c1a43519050b96d9d2bc2d9', '66195b8728e235ff79c3d8b48005a603', '2021-05-26 10:41:29', NULL, NULL, NULL, '666666666666666666666', NULL, '2', '455e90b086c94910aa1dc683505b1d91.png', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('59d2f55d749d4815b283a0958b40c3ff', '21774d278c1a43519050b96d9d2bc2d9', 'd85f6d84ac4af4aff61619694e70089e', '2021-04-18 22:58:47', NULL, NULL, NULL, 'zxxzxcxzvzvxzvxzvxz', NULL, '4', '59d2f55d749d4815b283a0958b40c3ff.png', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('62280bc7157e4dd2a3fe2a7cbf8009bb', '20df7dc2fd6940b9a104183cc0a9c21e', 'd85f6d84ac4af4aff61619694e70089e', '2021-04-18 22:36:10', NULL, NULL, NULL, '232332', NULL, '3', '62280bc7157e4dd2a3fe2a7cbf8009bb.png', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('7043a3ed05d2465d80b12bb74af9633d', '7a74614e36344804a6f6f14d65116285', '66195b8728e235ff79c3d8b48005a603', '2021-04-19 10:42:03', NULL, NULL, NULL, '', NULL, '1', '', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('7cb4f878c2774526815d92445d34cdc0', '21774d278c1a43519050b96d9d2bc2d9', '66195b8728e235ff79c3d8b48005a603', '2021-05-08 07:46:32', NULL, NULL, NULL, '坏了', NULL, '3', '7cb4f878c2774526815d92445d34cdc0.png', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('7d9a6a5172cf4686ad8f43e281d2d75d', '35564c56e16f43fbafd5ce3b30448dae', 'd85f6d84ac4af4aff61619694e70089e', '2021-05-03 10:53:13', NULL, NULL, NULL, '', NULL, '1', '', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('9ad2143cd540444b9012721e90d1590b', '7a74614e36344804a6f6f14d65116285', '66195b8728e235ff79c3d8b48005a603', '2021-05-08 08:45:15', NULL, NULL, NULL, 'ceshi', NULL, '4', '9ad2143cd540444b9012721e90d1590b.png', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('b82e7002db4a4ead9f2a0ea529d02294', '21774d278c1a43519050b96d9d2bc2d9', '66195b8728e235ff79c3d8b48005a603', '2021-05-25 22:12:15', NULL, NULL, NULL, '66666+6', NULL, '1', 'b82e7002db4a4ead9f2a0ea529d02294.png', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('bc41983d43f544b0b46c6844ba1e5f42', '7a74614e36344804a6f6f14d65116285', '66195b8728e235ff79c3d8b48005a603', '2021-05-06 20:22:10', NULL, NULL, NULL, '', NULL, '2', 'bc41983d43f544b0b46c6844ba1e5f42.png', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('d0dc353777a54feeb0fd198fba347c91', 'a7deb5c05ae34c538bac884c4ae3ecb9', 'd85f6d84ac4af4aff61619694e70089e', '2021-04-17 23:56:17', NULL, NULL, NULL, '234', NULL, '2', '', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('d25edaac25714082b1cbde74e930e5fe', '21774d278c1a43519050b96d9d2bc2d9', '66195b8728e235ff79c3d8b48005a603', '2021-05-25 22:11:34', NULL, NULL, NULL, 'esgtdrgrsg', NULL, '2', '', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('d38321727dd14e71ac96859702d95669', 'd0fe956f719c4c4a9738f19919faeacf', '66195b8728e235ff79c3d8b48005a603', '2021-04-18 23:10:00', NULL, NULL, NULL, '46+644654646', NULL, '2', 'd38321727dd14e71ac96859702d95669.jpg', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('d3aa6b2796a7402199bb60c5b70f3b5f', '7a74614e36344804a6f6f14d65116285', '66195b8728e235ff79c3d8b48005a603', '2021-04-19 10:45:15', NULL, NULL, NULL, 'sadsadasdasda', NULL, '3', '', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('d678260abbce45cca65ade861a198218', '21774d278c1a43519050b96d9d2bc2d9', 'd85f6d84ac4af4aff61619694e70089e', '2021-04-18 22:58:49', NULL, NULL, NULL, 'zxxzxcxzvzvxzvxzvxz', NULL, '4', 'd678260abbce45cca65ade861a198218.png', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('d8ff2aa845ce4a78b61204cd5e3277dc', '21774d278c1a43519050b96d9d2bc2d9', '66195b8728e235ff79c3d8b48005a603', '2021-05-26 09:00:07', NULL, NULL, NULL, 'test', NULL, '4', 'd8ff2aa845ce4a78b61204cd5e3277dc.png', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('e8143251760948a7a945a71c666c26ab', 'ee9b5fc0c7424f52b58fc7980c465df4', '66195b8728e235ff79c3d8b48005a603', '2021-05-29 14:35:38', NULL, NULL, NULL, 'cehsi', NULL, '4', 'e8143251760948a7a945a71c666c26ab.png', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('e8ebe1b9c8f94afbb82740511eabfcc5', 'a7deb5c05ae34c538bac884c4ae3ecb9', 'd85f6d84ac4af4aff61619694e70089e', '2021-04-18 22:33:39', NULL, NULL, NULL, '123', NULL, '1', 'e8ebe1b9c8f94afbb82740511eabfcc5.png', '39.916295', '116.410344');
INSERT INTO `inspect` VALUES ('f6c9028873ec4e84ac634bdadc538101', '35564c56e16f43fbafd5ce3b30448dae', '66195b8728e235ff79c3d8b48005a603', '2021-05-29 13:59:24', NULL, NULL, NULL, '66666678\\57\\5\r\n\r\n\r\n457\\5\r\n\r\n\r\n5\\75\\75', NULL, '1', 'f6c9028873ec4e84ac634bdadc538101.png', '39.916295', '116.410344');

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager`  (
  `id` varchar(155) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键（管理员信息表）',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '姓名',
  `age` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '年龄',
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '性别',
  `tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '联系电话',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `head` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色 1-超级管理员 2-管理员',
  `email` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('66195b8728e235ff79c3d8b48005a603', 'yjh', 'wfsy1234', 'Jianhui Yang', '', 'female', '17860971012', '2021-04-18 22:49:42', '', NULL, NULL);
INSERT INTO `manager` VALUES ('8eed0f3eaf50eed9485b04b471999d30', 'stephencurry', '123456', 'Yansheng Fu', '20', 'male', '13894437805', '2021-05-02 18:12:18', NULL, NULL, NULL);
INSERT INTO `manager` VALUES ('cea237ba3d9f79544c7635a7ef455e2a', '11', '11', '11', '', 'male', '00', '2021-12-26 20:54:29', '', NULL, NULL);
INSERT INTO `manager` VALUES ('d85f6d84ac4af4aff61619694e70089e', 'ceshi', 'wzy789.', 'Ziwen Wang', '', 'female', '13036354877', '2021-03-12 02:11:31', '', NULL, NULL);
INSERT INTO `manager` VALUES ('d9bebf20218e48f050093c5566df3501', 'similar', 'Fy1209.1128', 'Yue Fang', '', 'male', '15886046568', '2021-12-14 16:17:38', '', NULL, NULL);

-- ----------------------------
-- Table structure for problem_to_solve
-- ----------------------------
DROP TABLE IF EXISTS `problem_to_solve`;
CREATE TABLE `problem_to_solve`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `problem_outline` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `problem_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `other_information` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `problem_classification` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `problem_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deadline` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `presenter` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `preson_in_charge` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of problem_to_solve
-- ----------------------------

-- ----------------------------
-- Table structure for qrcode
-- ----------------------------
DROP TABLE IF EXISTS `qrcode`;
CREATE TABLE `qrcode`  (
  `id` int NOT NULL,
  `date` datetime NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrcode
-- ----------------------------

-- ----------------------------
-- Table structure for vipinfo
-- ----------------------------
DROP TABLE IF EXISTS `vipinfo`;
CREATE TABLE `vipinfo`  (
  `card_number` int NOT NULL,
  `face_info` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `vip_address` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `vip_age` int NULL DEFAULT NULL,
  `vip_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `vip_tel` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`card_number`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of vipinfo
-- ----------------------------
INSERT INTO `vipinfo` VALUES (101, '1', '1', 1, 'Tom', '18111424568');
INSERT INTO `vipinfo` VALUES (102, '2', '2', 2, 'Jerry', '19646463369');

-- ----------------------------
-- Table structure for viprecord
-- ----------------------------
DROP TABLE IF EXISTS `viprecord`;
CREATE TABLE `viprecord`  (
  `date` datetime NOT NULL,
  `card_number` int NOT NULL,
  PRIMARY KEY (`date`, `card_number`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of viprecord
-- ----------------------------
INSERT INTO `viprecord` VALUES ('2021-09-05 09:30:32', 102);
INSERT INTO `viprecord` VALUES ('2021-12-05 09:30:26', 101);

-- ----------------------------
-- Table structure for workerduty
-- ----------------------------
DROP TABLE IF EXISTS `workerduty`;
CREATE TABLE `workerduty`  (
  `worker_id` int NOT NULL,
  `duty_id` int NOT NULL,
  `duty_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `worker_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`worker_id`, `duty_id`, `duty_name`, `worker_name`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of workerduty
-- ----------------------------
INSERT INTO `workerduty` VALUES (1, 1, 'check', 'Tom');

-- ----------------------------
-- Table structure for workerinfo
-- ----------------------------
DROP TABLE IF EXISTS `workerinfo`;
CREATE TABLE `workerinfo`  (
  `worker_id` int NOT NULL,
  `face_info` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `worker_address` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `worker_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `worker_sex` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `worker_tel` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`worker_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of workerinfo
-- ----------------------------
INSERT INTO `workerinfo` VALUES (1, '1', 'Shangdong Province', 'Tom', 'man', '18956475611');
INSERT INTO `workerinfo` VALUES (2, '2', 'Shangdong Province', 'Jerry', 'man', '19645689955');

SET FOREIGN_KEY_CHECKS = 1;
