CREATE TABLE `oauth_client_details` (
    `client_id` VARCHAR(128)CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `resource_ids` VARCHAR(256)CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `client_secret` VARCHAR(256)CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `scope` VARCHAR(256)CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `authorized_grant_types` VARCHAR(256)CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `web_server_redirect_uri` VARCHAR(256)CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `authorities` VARCHAR(256)CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `access_token_validity` INT DEFAULT NULL,
    `refresh_token_validity` INT DEFAULT NULL,
    `additional_information` VARCHAR(4096)CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `autoapprove` VARCHAR(256)CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `client_secret_str` VARCHAR(20)CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

INSERT INTO `oauth_client_details` VALUES ('app',NULL,'$2a$10$i3F515wEDiB4Gvj9ym9Prui0dasRttEUQ9ink4Wpgb4zEDCAlV8zO','all','APP_SMS,APP_ONE_KEY,APP_SOCIAL,APP_PWD,ADMIN_PWD,APP_SOCIAL,APP_ANONYMOUS,APP_QRCODE,authorization_code,password,refresh_token','http://a.qimiao.com',NULL,2592000,2592000,NULL,NULL,'app'),('h5',NULL,'$2a$10$i3F515wEDiB4Gvj9ym9Prui0dasRttEUQ9ink4Wpgb4zEDCAlV8zO','all','APP_SMS,APP_ONE_KEY,APP_SOCIAL,APP_PWD,ADMIN_PWD,APP_SOCIAL,APP_ANONYMOUS,APP_QRCODE,authorization_code,password,refresh_token',NULL,NULL,2592000,2592000,NULL,NULL,'app'),('mini',NULL,'$2a$10$i3F515wEDiB4Gvj9ym9Prui0dasRttEUQ9ink4Wpgb4zEDCAlV8zO','all','APP_SMS,APP_ONE_KEY,APP_SOCIAL,APP_PWD,ADMIN_PWD,APP_SOCIAL,APP_ANONYMOUS,APP_QRCODE,authorization_code,password,refresh_token',NULL,NULL,2592000,2592000,NULL,NULL,'app'),('web',NULL,'$2a$10$i3F515wEDiB4Gvj9ym9Prui0dasRttEUQ9ink4Wpgb4zEDCAlV8zO','all','APP_SMS,APP_ONE_KEY,APP_SOCIAL,APP_PWD,ADMIN_PWD,APP_SOCIAL,APP_ANONYMOUS,APP_QRCODE,authorization_code,password,refresh_token',NULL,NULL,2592000,2592000,NULL,NULL,'app');

