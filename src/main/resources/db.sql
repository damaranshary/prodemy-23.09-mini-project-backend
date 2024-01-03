CREATE DATABASE PRODEMY_MINI_PROJECT;

DROP TABLE IF EXISTS T_CATEGORY;
CREATE TABLE T_CATEGORY
(
    ID   INT(11)      NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(100) NOT NULL,
    PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS T_PRODUCT;
CREATE TABLE T_PRODUCT
(
    ID          INT(11)      NOT NULL AUTO_INCREMENT,
    TITLE       VARCHAR(100) NOT NULL,
    PRICE       BIGINT       NOT NULL,
    IMAGE       VARCHAR(255) NOT NULL,
    CATEGORY_ID INT(11)      NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY FK_CATEGORY_PRODUCT (CATEGORY_ID) REFERENCES T_CATEGORY (ID)
);

INSERT INTO T_CATEGORY (ID, NAME) VALUES (1, 'Makanan');
INSERT INTO T_CATEGORY (ID, NAME) VALUES (2, 'Minuman');
INSERT INTO T_CATEGORY (ID, NAME) VALUES (3, 'Snack');

INSERT INTO T_PRODUCT (title, image, price, category_id) VALUES ('Aqual Botol 1L', 'http://localhost:8080/image_1', 12000, 2);
INSERT INTO T_PRODUCT (title, image, price, category_id) VALUES ('Frestea 450Ml', 'http://localhost:8080/image_2', 4500, 2);
INSERT INTO T_PRODUCT (title, image, price, category_id) VALUES ('Teh Pucuk Harum 400Ml', 'http://localhost:8080/image_3', 4000, 2);

INSERT INTO T_PRODUCT (title, image, price, category_id) VALUES ('Indomie Goreng', 'http://localhost:8080/image_4', 6000, 1);
INSERT INTO T_PRODUCT (title, image, price, category_id) VALUES ('Indomie Rebus', 'http://localhost:8080/image_4', 5500, 1);
INSERT INTO T_PRODUCT (title, image, price, category_id) VALUES ('Indomie Goreng Telor', 'http://localhost:8080/image_4', 10000, 1);
INSERT INTO T_PRODUCT (title, image, price, category_id) VALUES ('Indomie Rebus Telor', 'http://localhost:8080/image_4', 9500, 1);


