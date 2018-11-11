CREATE TABLE ShoppingList (
	Id	INTEGER PRIMARY KEY AUTOINCREMENT,
	TransactionId int,
	UserId int,
	DishId int,
	DishName varchar(50),
	DishImage int,
	IngredientId int,
	IngredientName varchar(50),
	Amount varchar(50),
	IngredientUnit varchar(50),
	PricePerUnit float,
	Status int
);
