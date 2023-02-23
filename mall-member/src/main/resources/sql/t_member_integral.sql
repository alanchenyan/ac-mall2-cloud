CREATE TABLE `t_member_integral` (
  `id` bigint NOT NULL COMMENT 'id',
  `member_id` bigint NOT NULL COMMENT '用户ID',
  `total_integral` bigint DEFAULT '0' COMMENT '用户总积分',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除 0未删除 1已删除',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_member_id` (`member_id`),
  KEY `IDX_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户积分'