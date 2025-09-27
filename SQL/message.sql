/*
 Navicat Premium Dump SQL

 Source Server         : nas
 Source Server Type    : MySQL
 Source Server Version : 80042 (8.0.42)
 Source Host           : 192.168.68.198:3306
 Source Schema         : say_hi

 Target Server Type    : MySQL
 Target Server Version : 80042 (8.0.42)
 File Encoding         : 65001

 Date: 28/09/2025 00:42:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `message_id` bigint NOT NULL COMMENT '唯一消息ID',
  `seq_id` bigint NOT NULL COMMENT '会话内消息序号，严格单调递增',
  `chat_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会话ID',
  `sender_id` bigint NOT NULL COMMENT '发送者ID',
  `content_type` int NOT NULL COMMENT '消息类型',
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息内容',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否被删除:0-未被删除; 1-已被删除',
  `create_time` bigint NOT NULL COMMENT '创建时间:毫秒时间戳',
  `update_time` bigint NOT NULL COMMENT '更新时间:毫秒时间戳',
  PRIMARY KEY (`message_id`),
  KEY `idx_chat_id` (`chat_id`,`seq_id`,`message_id`,`sender_id`,`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息表';

SET FOREIGN_KEY_CHECKS = 1;
