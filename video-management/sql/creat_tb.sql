USE `video`;
CREATE TABLE IF NOT EXISTS `video_base_info`
(
    `id`             BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `cover_url`      VARCHAR(128)     NOT NULL COMMENT '封面',
    `title`          VARCHAR(255)     NOT NULL COMMENT '头像地址，URL',
    `main_partition` BIGINT UNSIGNED  NOT NULL COMMENT '主分区',
    `sub_partition`  BIGINT UNSIGNED  NOT NULL COMMENT '子分区',
    `tags`           VARCHAR(1024)    NOT NULL COMMENT '标签',
    `profile`        VARCHAR(256)     NOT NULL COMMENT '简介',
    `author_id`      BIGINT UNSIGNED  NOT NULL COMMENT '作者id',
    `create_time`    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    `update_time`    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_delete`      TINYINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除（0-未删除 1-已删除）',
    KEY `partition_index` (`main_partition`, `sub_partition`) USING BTREE,
    KEY `author_index` (`author_id`) USING BTREE
) comment '视频基本信息表' collate = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `video_media_info`
(
    `id`            BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `video_id`      BIGINT UNSIGNED  NOT NULL COMMENT '视频id',
    `path`          VARCHAR(128)     NOT NULL COMMENT '存储路径',
    `file_name`     VARCHAR(64)      NOT NULL COMMENT '文件名',
    `type`          VARCHAR(8)       NOT NULL COMMENT '文件类型',
    `bucket`        VARCHAR(32)      NOT NULL COMMENT '存储桶',
    `video_size`    VARCHAR(16) COMMENT '视频大小',
    `video_clarity` TINYINT comment '视频清晰度 1-360P流畅 2-480P清晰 3-720P高清 4-1080P高清 5-1080P高码率',
    `create_time`   DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    `update_time`   DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_delete`     TINYINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除（0-未删除 1-已删除）',
    KEY `video_id_index` (`video_id`) USING BTREE,
    KEY `create_time_index` (`create_time`) USING BTREE,
    KEY `file_name_index` (`file_name`) USING BTREE
) comment '视频媒体资源信息表' collate = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `video_review_info`
(
    `id`              BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `video_id`        BIGINT UNSIGNED  NOT NULL COMMENT '视频id',
    `review_status`   TINYINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '审核状态 0-审核中 1-审核结束',
    `review_result`   TINYINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '审核结果 0-未知 1-通过 2-未通过',
    `review_comments` VARCHAR(1024)    NOT NULL COMMENT '审核意见',
    `create_time`     DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    `update_time`     DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_delete`       TINYINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除（0-未删除 1-已删除）',
    KEY `video_id_index` (`video_id`) USING BTREE,
    KEY `review_status_index` (`review_status`) USING BTREE,
    KEY `review_result_index` (`review_result`) USING BTREE
) comment '视频审核表' collate = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `partition_info`
(
    `id`             BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `partition_name` VARCHAR(64)      NOT NULL COMMENT '分区名称',
    `description`    VARCHAR(256)     NULL COMMENT '分区描述',
    `parent_id`      BIGINT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '该分区所属主分区',
    `create_time`    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_delete`      TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除（0-未删除 1-已删除）',
    KEY `partition_name_index` (`partition_name`) USING BTREE,
    KEY `parent_id_index` (`parent_id`) USING BTREE
) comment '视频分区表' collate = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `video_interaction`
(
    `id`       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `video_id` BIGINT UNSIGNED NOT NULL COMMENT '视频id',
    `like`     BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '点赞数',
    `coin`     BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '投币数',
    `collect`  BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '收藏数',
    `share`    BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '转发数',
    `play`     BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '播放数',
    KEY `video_id_index` (`video_id`) USING BTREE
) comment '视频互动表' collate = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `interaction_active`
(
    `id`        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `video_id`  BIGINT UNSIGNED NOT NULL COMMENT '视频id',
    `user_id`   BIGINT UNSIGNED NOT NULL COMMENT '用户id',
    `is_like`   TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否点赞',
    `is_coin`   TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否投币',
    `is_collect` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否收藏',
    KEY `video_user_active_index` (`user_id`, `video_id`) USING BTREE
) comment '用户互动状态表' collate = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `dm`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `video_id`    BIGINT UNSIGNED NOT NULL COMMENT '视频id',
    `user_id`     BIGINT UNSIGNED NOT NULL COMMENT '用户id',
    `content`     VARCHAR(128)    NOT NULL COMMENT '弹幕内容',
    `color`       VARCHAR(16)     NOT NULL DEFAULT '#FFFFFF' COMMENT '弹幕颜色',
    `fontSize`    INT UNSIGNED    NOT NULL DEFAULT 25 COMMENT '字体大小',
    `stamp`       INT UNSIGNED    NOT NULL COMMENT '弹幕在视频中出现时间 ms',
    `create_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    KEY `video_index` (`video_id`) USING BTREE
) comment '视频弹幕表' collate = utf8mb4_unicode_ci;

INSERT INTO `dm` (`video_id`, `user_id`, `content`, `color`, `fontSize`, `stamp`)
SELECT
    10 AS `video_id`,
    FLOOR(RAND() * 1000) + 1 AS `user_id`,
    CONCAT('这是弹幕内容，用户', FLOOR(RAND() * 1000) + 1) AS `content`,
    CONCAT('#', LPAD(HEX(FLOOR(RAND() * 16777215)), 6, '0')) AS `color`,
    CASE WHEN RAND() > 0.5 THEN 18 ELSE 25 END AS `fontSize`,
    FLOOR(RAND() * 230000) AS `stamp`
FROM
    dm
LIMIT 1000;