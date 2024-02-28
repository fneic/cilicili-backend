CREATE TABLE IF NOT EXISTS `user`
(
    `id`          BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `nick_name`   VARCHAR(16)      NULL COMMENT '姓名',
    `account`     VARCHAR(64)      NOT NULL COMMENT '账号',
    `password`    VARCHAR(64)      NULL COMMENT '密码，非必须',
    `avatar`      VARCHAR(255)     NULL COMMENT '头像地址，URL',
    `phone`       VARCHAR(16)      NOT NULL COMMENT '电话号',
    `email`       VARCHAR(64)      NULL COMMENT '邮箱',
    `profile`     VARCHAR(1024)    NULL COMMENT '个人签名',
    `birth`       DATETIME         NULL COMMENT '生日',
    `openid`      VARCHAR(64)      NULL COMMENT '微信登录id',
    `gender`      TINYINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '性别 0-未知 1-男 2-女',
    `role`        TINYINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '用户角色 0-普通用户 1-系统管理员',
    `status`      TINYINT UNSIGNED NOT NULL DEFAULT '1' COMMENT '0-禁用 1-正常',
    `create_time` DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_delete`   TINYINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否注销账户（0-未删除 1-已删除（注销））',
    UNIQUE KEY `user_account_unique` (`account`) USING BTREE,
    UNIQUE KEY `user_nickName_unique` (`nick_name`) USING BTREE,
    UNIQUE KEY `user_phone_unique` (`phone`) USING BTREE,
    UNIQUE KEY `user_openid_unique` (`openid`) USING BTREE
) comment '用户表' collate = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `account_info`
(
    `id`        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `coins`     INT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '硬币数',
    `follower`  INT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '粉丝数',
    `following` INT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '关注数',
    `likes`     INT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '获赞数',
    `plays`     INT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '播放数'
) comment '账户信息表' collate = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `exp`
(
    `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `user_id`       BIGINT UNSIGNED NOT NULL COMMENT '用户id',
    `current_level` INT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '当前等级',
    `current_exp`   INT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '当前经验值',
    `next_exp`      INT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '下一级所需经验',
    KEY `user_index` (`user_id`) USING BTREE
) comment '用户等级经验表' collate = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `level`
(
    `id`    INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `level` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '等级',
    `exp`   INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '经验值',
    KEY `level_index` (`level`) USING BTREE
) comment '等级表' collate = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `equity`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `name`        VARCHAR(128)    NOT NULL DEFAULT 0 COMMENT '权益名',
    `description` VARCHAR(1024)            DEFAULT NULL COMMENT '权益描述',
    `level`       INT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '所需等级',
    KEY `level_index` (`level`) USING BTREE
) comment '权益等级表' collate = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `coins_record`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `user_id`     BIGINT UNSIGNED NOT NULL COMMENT '用户id',
    `amount`      INT             NOT NULL COMMENT '变化数量',
    `reason`      VARCHAR(1024)   NOT NULL COMMENT '理由',
    `create_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    KEY `user_index` (`user_id`) USING BTREE
) comment '硬币消费记录表' collate = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `relation`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `follower`    BIGINT UNSIGNED NOT NULL COMMENT '关注者id',
    `following`   BIGINT UNSIGNED NOT NULL COMMENT '粉丝id',
    `create_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    KEY `follower_index` (`follower`) USING BTREE,
    KEY `following_index` (`following`) USING BTREE
) comment '用户关系表' collate = utf8mb4_unicode_ci;