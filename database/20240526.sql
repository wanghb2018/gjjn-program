ALTER TABLE `work_suipian`
DROP COLUMN `name`,
DROP COLUMN `pinji`,
DROP COLUMN `touxiang`,
DROP COLUMN `color`,
DROP COLUMN `spnum`,
DROP INDEX `work_suipian_jnsp_id_bed2ecf4_fk_work_jianniang_id`,
DROP INDEX `pinji`;


ALTER TABLE `work_myjianniang`
DROP INDEX `work_myjianniang_jianniang_id_62b219ec_fk_work_jianniang_id`;