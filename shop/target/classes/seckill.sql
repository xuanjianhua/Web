--创建数据库
create database seckill;
--使用数据库
use seckill;
--创建库存秒杀表
CREATE TABLE `seckill`(
	`seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
	`name` varchar(120) NOT NULL COMMENT '商品名称',
	`number` int NOT NULL COMMENT'库存数量',
	`start_time` TIMESTAMP NOT NULL COMMENT'秒杀开始时间',
	`end_time` TIMESTAMP NOT NULL COMMENT'秒杀结束时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'创建时间',
	PRIMARY KEY (seckill_id),
	KEY idx_start_time(start_time),
	KEY idx_end_time(end_time),
	KEY idx_create_time(create_time)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';
--初始化数据
insert into 
	seckill(name,number,start_time,end_time)
values
	('1000元秒杀iPhone6','100','2016-08-19 00:00:00','2016-08-20 00:00:00'),
	('500元秒杀iPad2','200','2016-08-19 00:00:00','2016-08-20 00:00:00'),
	('300元秒杀小米4','300','2016-08-19 00:00:00','2016-08-20 00:00:00'),
	('200元秒杀红米note','400','2016-08-19 00:00:00','2016-08-20 00:00:00');
	
--秒杀成功登记表
--用户登录认证相关信息
create table `success_killed`(
`seckill_id` bigint NOT NULL COMMENT'秒杀商品id',
`user_phone` bigint NOT NULL COMMENT'用户手机号',
`state` tinyint NOT NULL DEFAULT -1 COMMENT'状态标示：-1：无效    0：成功    1：已付款    2：已发货',
`create_time` TIMESTAMP NOT NULL COMMENT'创建时间',
PRIMARY KEY (seckill_id,user_phone),
KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀成功库存表';
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	