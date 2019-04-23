DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Account;
DROP TABLE IF EXISTS Transaction;

CREATE TABLE User (
Id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
Username VARCHAR(50) NOT NULL,
FirstName VARCHAR(50) NOT NULL,
LastName VARCHAR(50) NOT NULL
);

CREATE TABLE Account (
Id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
Balance DOUBLE NOT NULL,
OwnerID LONG NOT NULL,
Currency VARCHAR(50) NOT NULL
);

CREATE TABLE Transaction (
Id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
Type VARCHAR(50) NOT NULL,
FromAccount LONG,
ToAccount LONG,
Amount DOUBLE NOT NULL,
Currency VARCHAR(50) NOT NULL
);

INSERT INTO User (Username, FirstName, LastName) VALUES ('emusk', 'Elon', 'Musk');
INSERT INTO User (Username, FirstName, LastName) VALUES ('bgates', 'Bill', 'Gates');

INSERT INTO Account (Balance, OwnerID, Currency) VALUES (1000, 1, 'GBP');
INSERT INTO Account (Balance, OwnerID, Currency) VALUES (1000, 1, 'EUR');
INSERT INTO Account (Balance, OwnerID, Currency) VALUES (1000, 2, 'GBP');