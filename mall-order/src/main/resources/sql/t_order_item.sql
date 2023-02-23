CREATE TABLE `t_order_item` (
  `id` bigint NOT NULL COMMENT 'id',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '产品名称',
  `buy_num` bigint DEFAULT '0' COMMENT '购买数量',
  `buy_price` decimal(16,2) NOT NULL DEFAULT '0.00' COMMENT '购买价',
  `image_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图片地址',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除 0未删除 1已删除',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IDX_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='订单项'