ALTER TABLE `work_myjianniang`
DROP COLUMN `name`,
DROP COLUMN `pinji`,
DROP COLUMN `touxiang`,
DROP COLUMN `lihui`,
DROP COLUMN `color`,
MODIFY COLUMN `level` int(11) NOT NULL DEFAULT 1 AFTER `star`,
MODIFY COLUMN `jingyan` int(11) NOT NULL DEFAULT 0 AFTER `zdl`;