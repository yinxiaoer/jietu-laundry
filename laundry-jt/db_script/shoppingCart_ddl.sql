CREATE TABLE `sc_shopping_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '购物车id',
  `goods_id` int(11) NOT NULL COMMENT '商品类型Id',
  `merchant_id` bigint(20) NOT NULL COMMENT '商户id',
  `customer_id` bigint(20) NOT NULL COMMENT '客户id',
  `number` int(11) NOT NULL COMMENT '数量件数',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_customer_id` (`customer_id`) USING BTREE,
  KEY `idx_merchant_id` (`merchant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车表';