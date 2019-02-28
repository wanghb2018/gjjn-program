drop table auth_group_permissions;

drop table auth_user_user_permissions;

drop table auth_user_groups;

drop table auth_group;

drop table auth_permission;

drop table django_admin_log;

drop table django_content_type;

drop table django_migrations;

drop table django_session;

drop table qc_job;

drop table work_accesslog;

drop table work_danzhongduan;

ALTER TABLE `gjjn`.`auth_user` 
DROP COLUMN `is_superuser`,
DROP COLUMN `first_name`,
DROP COLUMN `last_name`;

ALTER TABLE `gjjn`.`work_jianniang_maps` 
CHANGE COLUMN `jianniang_id` `jn_id` int(11) NOT NULL AFTER `id`;

alter table `work_jnshengxing` rename `work_jianniangsx`;

alter table `work_jnsj` rename `work_jianniangsj`;

ALTER TABLE `gjjn`.`work_myjianniang` 
CHANGE COLUMN `mygongji` `gongji` int(11) NOT NULL AFTER `id`,
CHANGE COLUMN `myfangyu` `fangyu` int(11) NOT NULL AFTER `gongji`,
CHANGE COLUMN `myxueliang` `xueliang` int(11) NOT NULL AFTER `fangyu`,
CHANGE COLUMN `mysudu` `sudu` int(11) NOT NULL AFTER `xueliang`,
CHANGE COLUMN `mybaoji` `baoji` int(11) NOT NULL AFTER `sudu`,
CHANGE COLUMN `myduobi` `duobi` int(11) NOT NULL AFTER `baoji`,
CHANGE COLUMN `mystar` `star` int(11) NOT NULL AFTER `duobi`,
CHANGE COLUMN `jianniang_id` `jn_id` int(11) NOT NULL AFTER `jingyan`;

ALTER TABLE `gjjn`.`work_myjianniang` 
ADD COLUMN `name` varchar(50) NULL AFTER `role_id`,
ADD COLUMN `pinji` int(11) NULL AFTER `name`,
ADD COLUMN `touxiang` varchar(1024) NULL AFTER `pinji`,
ADD COLUMN `lihui` varchar(1024) NULL AFTER `touxiang`,
ADD COLUMN `color` varchar(50) NULL AFTER `lihui`;

update work_myjianniang m
INNER JOIN work_jianniang j on m.jn_id = j.id
set m.name = j.`name`,
m.pinji=j.pinji,
m.touxiang = j.touxiang,
m.lihui = j.lihui,
m.color = j.color;

ALTER TABLE `gjjn`.`work_suipian` 
ADD COLUMN `name` varchar(50) NULL AFTER `role_id`,
ADD COLUMN `pinji` int(11) NULL AFTER `name`,
ADD COLUMN `touxiang` varchar(1024) NULL AFTER `pinji`,
ADD COLUMN `color` varchar(50) NULL AFTER `touxiang`;

ALTER TABLE `gjjn`.`work_suipian` 
CHANGE COLUMN `jnsp_id` `jn_id` int(11) NOT NULL AFTER `num`;

update work_suipian s
INNER JOIN work_jianniang j on s.jn_id = j.id
set s.name = j.`name`,
s.pinji=j.pinji,
s.touxiang = j.touxiang,
s.color = j.color;

ALTER TABLE `gjjn`.`work_jianniang` 
MODIFY COLUMN `touxiang` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `duobi`,
MODIFY COLUMN `lihui` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `touxiang`;

ALTER TABLE `gjjn`.`work_myjianniang` 
ADD INDEX `work_myjianniang_iswar`(`iswar`) USING BTREE,
ADD INDEX `work_myjianniang_pinji`(`pinji`) USING BTREE,
ADD INDEX `work_myjianniang_level`(`level`) USING BTREE;

ALTER TABLE `gjjn`.`work_suipian` 
ADD INDEX `work_suipian_num`(`num`) USING BTREE,
ADD INDEX `work_suipian_pinji`(`pinji`) USING BTREE;