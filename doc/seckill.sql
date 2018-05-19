/*
SQLyog Ultimate v9.62 
MySQL - 5.7.17-log : Database - rainbow-seckill
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rainbow-seckill` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `rainbow-seckill`;

/*Table structure for table `t_goods` */

DROP TABLE IF EXISTS `t_goods`;

CREATE TABLE `t_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods_name` varchar(128) COLLATE utf8_unicode_ci NOT NULL COMMENT '商品名称',
  `goods_title` varchar(128) COLLATE utf8_unicode_ci NOT NULL COMMENT '商品标题',
  `goods_img` varchar(512) COLLATE utf8_unicode_ci NOT NULL COMMENT '商品图片',
  `goods_detail` varchar(1024) COLLATE utf8_unicode_ci NOT NULL COMMENT '商品详情',
  `goods_price` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `goods_stock` int(11) NOT NULL DEFAULT '0' COMMENT '商品库存',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_goods` */

/*Table structure for table `t_order` */

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `goods_id` int(11) NOT NULL COMMENT '商品ID',
  `delivery_addr_id` int(11) DEFAULT NULL COMMENT '发货地址ID',
  `goods_name` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品名称',
  `goods_count` int(11) DEFAULT '0' COMMENT '商品数量',
  `goods_price` decimal(18,2) DEFAULT '0.00' COMMENT '商品价格',
  `order_channel` int(11) DEFAULT NULL COMMENT '订单渠道',
  `status` int(11) DEFAULT '0' COMMENT '状态',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `pay_date` timestamp NULL DEFAULT NULL COMMENT '付款时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_order` */

/*Table structure for table `t_seckill_goods` */

DROP TABLE IF EXISTS `t_seckill_goods`;

CREATE TABLE `t_seckill_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods_id` int(11) NOT NULL COMMENT '商品ID',
  `stock_count` int(11) NOT NULL DEFAULT '0' COMMENT '购买数量',
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_seckill_goods` */

/*Table structure for table `t_seckill_order` */

DROP TABLE IF EXISTS `t_seckill_order`;

CREATE TABLE `t_seckill_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `goods_id` int(11) NOT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_seckill_order` */

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nickname` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '昵称',
  `PASSWORD` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码',
  `salt` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '盐值',
  `head` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '头像',
  `register_date` timestamp NULL DEFAULT NULL COMMENT '注册时间',
  `last_login_date` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `login_count` int(11) DEFAULT NULL COMMENT '登录次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
