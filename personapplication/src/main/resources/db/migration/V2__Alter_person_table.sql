ALTER TABLE `personapplication`.`person` 
ADD COLUMN `password` VARCHAR(500) NULL AFTER `pno`,
CHANGE COLUMN `email` `email` VARCHAR(45) NOT NULL ,
CHANGE COLUMN `pno` `pno` VARCHAR(45) NOT NULL ;