CREATE TABLE `t_member` (
  `id` bigint NOT NULL COMMENT 'id',
  `member_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '用户姓名',
  `idNo` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '用户手机号码',
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'UNKNOW' COMMENT '用户性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除 0未删除 1已删除',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IDX_mobile` (`idNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户'