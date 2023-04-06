CREATE TABLE `t_product` (
  `id` bigint NOT NULL COMMENT 'id',
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '产品名称',
  `category` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '产品分类',
  `brand` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '产品品牌',
  `stock` bigint DEFAULT '0' COMMENT '库存',
  `sell_num` bigint DEFAULT '0' COMMENT '销量',
  `cost_price` decimal(16,2) NOT NULL DEFAULT '0.00' COMMENT '进价',
  `sell_price` decimal(16,2) NOT NULL DEFAULT '0.00' COMMENT '售价',
  `discount_price` decimal(16,2) NOT NULL DEFAULT '0.00' COMMENT '折扣价',
  `publish_state` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'UN_PUBLISHED' COMMENT '发布状态',
  `image_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图片地址',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '产品详情描述',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除 0未删除 1已删除',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='产品'