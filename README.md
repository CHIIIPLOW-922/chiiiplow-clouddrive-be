# chiiiplow-clouddrive-be
clouddrive-new
Table Design
```mysql
-- user
CREATE TABLE IF NOT EXISTS `user` (
    `id` bigint NOT NULL COMMENT 'user id',
    `username` VARCHAR(20) NOT NULL COMMENT 'username',
    `nickname` VARCHAR(20) DEFAULT NULL COMMENT 'nickname',
    `avatar_path` VARCHAR(255) DEFAULT NULL COMMENT 'avatar path',
    `email` VARCHAR(255) DEFAULT NULL COMMENT 'email',
    `password` VARCHAR(64) DEFAULT NULL COMMENT 'password',
    `salt` VARCHAR(50) DEFAULT NULL COMMENT 'salt',
    `register_time` DATETIME DEFAULT NULL COMMENT 'register time',
    `modify_time` DATETIME DEFAULT NULL COMMENT 'modify time',
    `used_space` bigint DEFAULT '0' COMMENT 'used space',
    `total_space` bigint DEFAULT NULL COMMENT 'total space',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_email` (`email`),
    UNIQUE KEY `uni_user_name` (`username`),
    KEY `idx_nick_name` (`nickname`)
) ENGINE=InnoDB COMMENT='user';

-- file
CREATE TABLE IF NOT EXISTS `file` (
    `id` bigint NOT NULL COMMENT 'file id',
    `user_id` bigint NOT NULL COMMENT 'user id',
    `parent_id` bigint DEFAULT 0 COMMENT 'parent id',
    `upload_id` VARCHAR(255) DEFAULT NULL COMMENT 'upload id',
    `is_folder` TINYINT(1) DEFAULT 0 COMMENT 'folder flag',
    `md5` VARCHAR(255) DEFAULT NULL COMMENT 'md5',
    `file_name` VARCHAR(255) DEFAULT NULL COMMENT 'file name',
    `file_size` bigint DEFAULT 0 COMMENT 'file size',
    `file_type` TINYINT(1) DEFAULT NULL COMMENT 'file type',
    `file_path` VARCHAR(255) DEFAULT NULL COMMENT 'file path',
    `created_time` DATETIME DEFAULT NULL COMMENT 'created time',
    `modify_time` DATETIME DEFAULT NULL COMMENT 'modify time',
    `recycling_time` DATETIME DEFAULT NULL COMMENT 'recycling time',
    `chunk_size` bigint DEFAULT NULL COMMENT 'chunk size',
    `chunk_num` int DEFAULT NULL COMMENT 'chunk number',
    `delete_status` TINYINT(1) DEFAULT 0 COMMENT 'normal: 0, deleted: 1, recycling: 2',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_parent_id` (`parent_id`)
) ENGINE=INNODB COMMENT='file';
```
