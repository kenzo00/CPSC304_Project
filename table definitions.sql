CREATE TABLE cpsc304.Item (
    upc INT,
    title VARCHAR(45),
    type VARCHAR(5),
    category VARCHAR(20),
    company VARCHAR(30),
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
    phone CHAR(9),
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
    title VARCHAR(30),
    PRIMARY KEY (upc),
    FOREIGN KEY (upc)
        REFERENCES cpsc304.Item (upc)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE cpsc304.`Order` (
    receiptId INT,
    date DATE,
    cid INT NOT NULL,
    `card#` INT NOT NULL,
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
	