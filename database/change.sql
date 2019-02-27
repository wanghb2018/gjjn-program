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