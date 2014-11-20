CREATE DATABASE CPSC304;

CREATE TABLE cpsc304.Item (
    upc INT,
    title VARCHAR(45),
    type VARCHAR(45),
    category VARCHAR(45),
    company VARCHAR(45),
    year INT,
    price DOUBLE,
    stock INT,
    PRIMARY KEY (upc)
);

CREATE TABLE cpsc304.Customer (
    cid INT,
    password VARCHAR(30),
    name VARCHAR(30),
    address VARCHAR(45),
    phone VARCHAR(20),
    PRIMARY KEY (cid)
);
  
CREATE TABLE cpsc304.LeadSinger (
    upc INT,
    name VARCHAR(45),
    PRIMARY KEY (upc , name),
    FOREIGN KEY (upc)
        REFERENCES cpsc304.Item (upc)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);
	
CREATE TABLE cpsc304.HasSong (
    upc INT,
    title VARCHAR(45),
    PRIMARY KEY (upc),
    FOREIGN KEY (upc)
        REFERENCES cpsc304.Item (upc)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE cpsc304.`Order` (
    receiptId INT,
    date DATE,
    cid INT NOT NULL,
    `card#` VARCHAR(20) NOT NULL,
    expiryDate DATE NOT NULL,
    expectedDate DATE,
    deliveredDate DATE,
    PRIMARY KEY (receiptId),
    FOREIGN KEY (cid)
        REFERENCES cpsc304.Customer (cid)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE cpsc304.PurchaseItem (
    receiptId INT,
    upc INT,
    quantity INT,
    PRIMARY KEY (receiptId , upc),
    FOREIGN KEY (receiptId)
        REFERENCES cpsc304.`Order` (receiptId)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (upc)
        REFERENCES cpsc304.Item (upc)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE cpsc304.`Return` (
    retid INT,
    date DATE,
    receiptId INT,
    PRIMARY KEY (retid),
    FOREIGN KEY (receiptId)
        REFERENCES cpsc304.`Order` (receiptId)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE cpsc304.ReturnItem (
    retid INT,
    upc INT,
    quantity INT,
    PRIMARY KEY (retid , upc),
    FOREIGN KEY (retid)
        REFERENCES cpsc304.`Return` (retid)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (upc)
        REFERENCES cpsc304.Item (upc)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);
	