# sponsor

DROP DATABASE IF EXISTS `X_SPONSOR`;
CREATE DATABASE `X_SPONSOR`;
USE `X_SPONSOR`;

DROP TABLE IF EXISTS `X_TCC_TRANSACTION`;
CREATE TABLE `X_TCC_TRANSACTION` (
    `ID` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `TCC_TX_ID` CHAR(64) NOT NULL COMMENT 'tcc事务id',
    `PHASE` VARCHAR(10) NOT NULL COMMENT '事务阶段:try confirm cancel',
    `TYPE` CHAR(6) NOT NULL COMMENT '事务类型：主事务，分支事务',
    `COMPENSATION_EVENT` VARCHAR(128) NOT NULL COMMENT '补偿动作事件',
    `COMPENSATION_INFO` VARCHAR(4096) NOT NULL COMMENT '补偿可能使用的数据',
    `CREATE_TIME` DATETIME NOT NULL COMMENT '创建时间',
    `UPDATE_TIME` DATETIME NOT NULL COMMENT '更新时间',
    `RETRY_TIMES` BIGINT DEFAULT 0
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

# order-mgr

DROP DATABASE IF EXISTS `X_ORDER_MGR`;
CREATE DATABASE `X_ORDER_MGR`;
USE `X_ORDER_MGR`;
DROP TABLE IF EXISTS `X_ORDER`;
CREATE TABLE `X_ORDER` (
    `ID` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `PRICE_AMOUNT` DECIMAL(21,4) NOT NULL COMMENT '待支付金额',
    `PAID_AMOUNT` DECIMAL(21,4) NOT NULL COMMENT '已经支付金额'
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
INSERT INTO `X_ORDER` VALUES (1, 100.00, 0.00);

DROP TABLE IF EXISTS `X_TCC_TRANSACTION`;
CREATE TABLE `X_TCC_TRANSACTION` (
    `ID` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `TCC_TX_ID` CHAR(64) NOT NULL COMMENT 'tcc事务id',
    `PHASE` VARCHAR(10) NOT NULL COMMENT '事务阶段:try confirm cancel',
    `TYPE` CHAR(6) NOT NULL COMMENT '事务类型：主事务，分支事务',
    `COMPENSATION_EVENT` VARCHAR(128) NOT NULL COMMENT '补偿动作事件',
    `COMPENSATION_INFO` VARCHAR(4096) NOT NULL COMMENT '补偿可能使用的数据',
    `CREATE_TIME` DATETIME NOT NULL COMMENT '创建时间',
    `UPDATE_TIME` DATETIME NOT NULL COMMENT '更新时间',
    `RETRY_TIMES` BIGINT DEFAULT 0
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

# account-mgr

DROP DATABASE IF EXISTS `X_ACCOUNT_MGR`;
CREATE DATABASE `X_ACCOUNT_MGR`;
USE `X_ACCOUNT_MGR`;

DROP TABLE IF EXISTS `X_ACCOUNT`;
CREATE TABLE `X_ACCOUNT` (
    `ID` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `TOTAL_AMOUNT` DECIMAL(21,4) NOT NULL COMMENT '账户总金额',
    `UPDATE_TIME` DATETIME NOT NULL COMMENT '更新时间'
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
INSERT INTO `X_ACCOUNT` VALUES (1, 500, now());

DROP TABLE IF EXISTS `X_TCC_TRANSACTION`;
CREATE TABLE `X_TCC_TRANSACTION` (
    `ID` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `TCC_TX_ID` CHAR(64) NOT NULL COMMENT 'tcc事务id',
    `PHASE` VARCHAR(10) NOT NULL COMMENT '事务阶段:try confirm cancel',
    `TYPE` CHAR(6) NOT NULL COMMENT '事务类型：主事务，分支事务',
    `COMPENSATION_EVENT` VARCHAR(128) NOT NULL COMMENT '补偿动作事件',
    `COMPENSATION_INFO` VARCHAR(4096) NOT NULL COMMENT '补偿可能使用的数据',
    `CREATE_TIME` DATETIME NOT NULL COMMENT '创建时间',
    `UPDATE_TIME` DATETIME NOT NULL COMMENT '更新时间',
    `RETRY_TIMES` BIGINT DEFAULT 0
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;