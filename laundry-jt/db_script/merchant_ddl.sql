CREATE TABLE `mt_merchant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `info` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '简介',
  `province` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '省',
  `city` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '市',
  `area` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '区',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '地址',
  `business_license_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '营业执照编号',
  `contact_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '联系人姓名',
  `contact_mobile` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '联系人手机号',
  `contact_mail` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '联系人邮箱',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `create_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '创建人',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '更新人',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_name` (`name`),
  KEY `idx_contact_name` (`contact_name`),
  KEY `idx_contact_mobile` (`contact_mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户表';