ALTER TABLE user_info
ADD COLUMN name VARCHAR(20) DEFAULT NULL COMMENT '姓名',
ADD COLUMN birthday VARCHAR(50) DEFAULT NULL COMMENT '出生年月日',
ADD COLUMN gender tinyint DEFAULT 1 COMMENT '0-女性，1-男性';

CREATE TABLE `user_questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(100) NOT NULL,
  `answer` varchar(100) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;