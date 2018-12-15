CREATE SCHEMA `laozi_server` DEFAULT CHARACTER SET utf8mb4 ;

CREATE TABLE `laozi_server`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(64) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `is_admin` TINYINT NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT now(),
  `updated_at` DATETIME NOT NULL DEFAULT now(),
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC) VISIBLE);

CREATE TABLE `tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  `updated_by` int(11) NOT NULL,
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `fk_tag_user_idx` (`created_by`),
  KEY `fk_tag_user_updated_by_idx` (`updated_by`),
  CONSTRAINT `fk_tag_user_created_by` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_tag_user_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `document` (
  `document_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1024) NOT NULL,
  `path` VARCHAR(2056) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  `updated_by` int(11) NOT NULL,
  PRIMARY KEY (`document_id`),
  KEY `fk_tag_user_created_by_idx` (`created_by`),
  KEY `fk_tag_user_updated_by_idx` (`updated_by`),
  CONSTRAINT `fk_document_user_created_by` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_document_user_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `laozi_server`.`document_tag` (
    `document_tag_id` INT(11) NOT NULL AUTO_INCREMENT,
  `document_id` INT NOT NULL,
  `tag_id` INT NOT NULL,
  PRIMARY KEY (`document_tag_id`),
  UNIQUE INDEX `unique_document_id_tag_id` (`document_id` ASC, `tag_id` ASC) VISIBLE
);
