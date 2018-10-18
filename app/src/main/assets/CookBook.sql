BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `CookBook` (
	`Id`	INTEGER,
	`DishId`	INTEGER,
	`DishName`	INTEGER,
	PRIMARY KEY(`Id`)
);
INSERT INTO `CookBook` VALUES (1,1,'Black Bean Bowl');
INSERT INTO `CookBook` VALUES (2,2,'Chicken Avocado Burritos');
COMMIT;
