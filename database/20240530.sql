DROP TABLE IF EXISTS `work_notice`;
CREATE TABLE `work_notice`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `title`        varchar(64) NOT NULL,
    `content`      varchar(255) NOT NULL,
    `detail`       text NOT NULL,
    `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0),
    `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0) ON UPDATE CURRENT_TIMESTAMP (0),
    PRIMARY KEY (`id`) USING BTREE
) CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `work_notice_confirmed`;
CREATE TABLE `work_notice_confirmed`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `role_id`      int(11) NOT NULL,
    `notice_id`    int(11) NOT NULL,
    `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0),
    `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0) ON UPDATE CURRENT_TIMESTAMP (0),
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `role_notice` (`role_id`, `notice_id`) USING BTREE
);