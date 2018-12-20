DROP DATABASE IF EXISTS springdb ;

CREATE DATABASE springdb ;

USE springdb ;

CREATE TABLE product (
	id INT AUTO_INCREMENT PRIMARY KEY,
	`name` VARCHAR(50) NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	store INT NOT NULL
) ;

INSERT INTO product (`id`,`name`,`price`,`store`) VALUES (DEFAULT,'辣条',4.70,100);
INSERT INTO product (`id`,`name`,`price`,`store`) VALUES (DEFAULT,'润唇膏',37.99,200);
INSERT INTO product (`id`,`name`,`price`,`store`) VALUES (DEFAULT,'娃哈哈',4.50,300);
INSERT INTO product (`id`,`name`,`price`,`store`) VALUES (DEFAULT,'红牛',5.50,400);
INSERT INTO product (`id`,`name`,`price`,`store`) VALUES (DEFAULT,'雪碧',3.00,500);
INSERT INTO product (`id`,`name`,`price`,`store`) VALUES (DEFAULT,'可口可乐',2.50,600);

CREATE TABLE productorder(
	id INT AUTO_INCREMENT PRIMARY KEY,
	productId INT NOT NULL,
	tradeNo VARCHAR(50) NOT NULL,
	createTime DATETIME DEFAULT NOW() NOT NULL,
	userId INT NOT NULL
);

INSERT INTO productorder VALUES (DEFAULT,1,UUID(),DEFAULT,1);
INSERT INTO productorder VALUES (DEFAULT,2,UUID(),DEFAULT,2);
INSERT INTO productorder VALUES (DEFAULT,3,UUID(),DEFAULT,1);
INSERT INTO productorder VALUES (DEFAULT,2,UUID(),DEFAULT,1);
INSERT INTO productorder VALUES (DEFAULT,1,UUID(),DEFAULT,1);

SELECT * FROM product;
SELECT * FROM productorder;