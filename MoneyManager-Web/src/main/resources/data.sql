CREATE SCHEMA IF NOT EXISTS mm;

CREATE TABLE IF NOT EXISTS mm.user_auth_details(
	user_id BIGINT PRIMARY KEY,
	username VARCHAR(20) NOT NULL,
	password VARCHAR(60) NOT NULL,
	role VARCHAR(20) NOT NULL,
	mail VARCHAR(255),
	phone_number VARCHAR(13),
	user_status VARCHAR(20) NOT NULL,
	UNIQUE (username)
);

INSERT INTO mm.user_auth_details VALUES (2860524688118663536,'admin','$2a$11$aINNKEQ/DMB9jKB/vD0ewui6ajj/8rkntk1EKOwhJU4eMw81NEJTq','ADMIN',null,null,'ACTIVE');