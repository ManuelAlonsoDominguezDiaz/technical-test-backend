DROP TABLE IF EXISTS WALLET;

CREATE TABLE WALLET (
    id INT AUTO_INCREMENT PRIMARY KEY,
    uuid uuid default random_uuid() NOT NULL UNIQUE,
    amount BIGINT NOT NULL
);

Insert into WALLET (uuid, amount) VALUES ('5b1280a3-f2a8-436b-8e0e-99e46278e441', 25);
Insert into WALLET (uuid, amount) VALUES ('6e9444a5-e52d-484b-a4a7-198665f46cc2', 10);
Insert into WALLET (uuid, amount) VALUES ('1078c292-1884-40d6-9f33-7c10700343eb', 35);