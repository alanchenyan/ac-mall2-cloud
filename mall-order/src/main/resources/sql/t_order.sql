CREATE TABLE `t_order` (
  `id` bigint NOT NULL COMMENT 'id',
  `order_no` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '订单号',
  `pay_channel` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '支付渠道(微信/支付宝)',
  `order_state` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'UN_PAY' COMMENT '订单状态',
  `order_time` datetime DEFAULT NULL COMMENT '下单时间',
  `pay_time` datetime DEFAULT NULL COMMENT '付款时间',
  `refund_time` datetime DEFAULT NULL COMMENT '退单时间',
  `member_id` bigint NOT NULL COMMENT '用户ID',
  `member_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '用户姓名',
  `idNo` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '用户手机号码',
  `product_amount` decimal(16,2) NOT NULL DEFAULT '0.00' COMMENT '商品总金额',
  `discount_amount` decimal(16,2) NOT NULL DEFAULT '0.00' COMMENT '优惠总金额',
  `pay_amount` decimal(16,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除 0未删除 1已删除',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IDX_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='订单'