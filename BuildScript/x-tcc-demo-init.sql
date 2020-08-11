# sponsor

DROP DATABASE IF EXISTS `x_sponsor`;
CREATE DATABASE `x_sponsor`;
USE `x_sponsor`;

DROP TABLE IF EXISTS `x_tcc_transaction`;
CREATE TABLE `x_tcc_transaction` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `tcc_tx_id` CHAR(36) NOT NULL COMMENT 'tcc事务id',
    `phase` VARCHAR(10) NOT NULL COMMENT '事务阶段:try confirm cancel',
    `type` CHAR(6) NOT NULL COMMENT '事务类型：主事务，分支事务',
    `compensation_event` VARCHAR(128) NOT NULL COMMENT '补偿动作事件',
    `compensation_info` VARCHAR(4096) NOT NULL COMMENT '补偿可能使用的数据',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_time` DATETIME NOT NULL COMMENT '更新时间',
    `retry_times` BIGINT DEFAULT 0,
    UNIQUE KEY `uk_tcc-tx-id` (`tcc_tx_id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

#for shedlock
DROP TABLE IF EXISTS `shedlock`;
CREATE TABLE shedlock (
    `name` VARCHAR(64) NOT NULL,
    `lock_until` TIMESTAMP(3) NOT NULL,
    `locked_at` TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `locked_by` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`name`)
);

# order-mgr

DROP DATABASE IF EXISTS `x_order_mgr`;
CREATE DATABASE `x_order_mgr`;
USE `x_order_mgr`;
DROP TABLE IF EXISTS `x_order`;
CREATE TABLE `x_order` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `price_amount` DECIMAL(21,4) NOT NULL COMMENT '待支付金额',
    `paid_amount` DECIMAL(21,4) NOT NULL COMMENT '已经支付金额'
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
INSERT INTO `x_order` VALUES (1, 5000.00, 0.00);

DROP TABLE IF EXISTS `x_tcc_transaction`;
CREATE TABLE `x_tcc_transaction` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `tcc_tx_id` CHAR(36) NOT NULL COMMENT 'tcc事务id',
    `phase` VARCHAR(10) NOT NULL COMMENT '事务阶段:try confirm cancel',
    `type` CHAR(6) NOT NULL COMMENT '事务类型：主事务，分支事务',
    `compensation_event` VARCHAR(128) NOT NULL COMMENT '补偿动作事件',
    `compensation_info` VARCHAR(4096) NOT NULL COMMENT '补偿可能使用的数据',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_time` DATETIME NOT NULL COMMENT '更新时间',
    `retry_times` BIGINT DEFAULT 0,
    UNIQUE KEY `uk_tcc-tx-id` (`tcc_tx_id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

# account-mgr

DROP DATABASE IF EXISTS `x_account_mgr`;
CREATE DATABASE `x_account_mgr`;
USE `x_account_mgr`;

DROP TABLE IF EXISTS `x_account`;
CREATE TABLE `x_account` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `total_amount` DECIMAL(21,4) NOT NULL COMMENT '账户总金额',
    `update_time` DATETIME NOT NULL COMMENT '更新时间'
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
INSERT INTO `x_account` VALUES (1, 5000, now());

DROP TABLE IF EXISTS `x_tcc_transaction`;
CREATE TABLE `x_tcc_transaction` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `tcc_tx_id` CHAR(36) NOT NULL COMMENT 'tcc事务id',
    `phase` VARCHAR(10) NOT NULL COMMENT '事务阶段:try confirm cancel',
    `type` CHAR(6) NOT NULL COMMENT '事务类型：主事务，分支事务',
    `compensation_event` VARCHAR(128) NOT NULL COMMENT '补偿动作事件',
    `compensation_info` VARCHAR(4096) NOT NULL COMMENT '补偿可能使用的数据',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_time` DATETIME NOT NULL COMMENT '更新时间',
    `retry_times` BIGINT DEFAULT 0,
    UNIQUE KEY `uk_tcc-tx-id` (`tcc_tx_id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
