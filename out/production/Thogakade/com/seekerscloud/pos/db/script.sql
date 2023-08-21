SHOW DATABASES;
DROP DATABASE IF EXISTS Thogakade;
CREATE DATABASE IF NOT EXISTS Thogakade;
SHOW DATABASES;
USE Thogakade;
#===============
DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
    id VARCHAR(45),
    name VARCHAR(45),
    address TEXT,
    salary DOUBLE,
    CONSTRAINT PRIMARY KEY (id)
);
#==============================
DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
    code VARCHAR(45),
    description VARCHAR(250),
    unitPrice DOUBLE,
    qtyOnHand INT,
    CONSTRAINT PRIMARY KEY (code)
);
#==============================
DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order`(
    oId  VARCHAR(45),
    date DATE,
    cost DOUBLE,
    id  VARCHAR(15),
    CONSTRAINT PRIMARY KEY (oId),
    CONSTRAINT FOREIGN KEY (id) REFERENCES Customer (id)
        ON UPDATE CASCADE ON DELETE CASCADE
);
SHOW TABLES;
DESC `Order`;
#=====================================
DROP TABLE IF EXISTS `Order Details`;
CREATE TABLE IF NOT EXISTS `Order Details`(
    code VARCHAR(45),
    oId VARCHAR(45),
    unitPrice DOUBLE,
    qty INT,
    cost DOUBLE,
    CONSTRAINT PRIMARY KEY (oId,code),
    CONSTRAINT FOREIGN KEY (oId) REFERENCES `Order` (oId)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (code) REFERENCES `Item` (code)
        ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESC `Order Details`;