CREATE TABLE Transaction (
	Id	INTEGER PRIMARY KEY AUTOINCREMENT,
	UserId int,
	TypeOfTransaction int,
	OrderTime varchar(50),
    Address varchar(50),
    Phone varchar(50),
	Status int
);
