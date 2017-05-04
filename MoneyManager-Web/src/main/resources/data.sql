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

CREATE TABLE IF NOT EXISTS mm.customer_details(
	customer_id BIGINT PRIMARY KEY,
	region VARCHAR(50) NOT NULL,
	building VARCHAR(50),
	address VARCHAR(255) NOT NULL,
	client VARCHAR(50),
	name VARCHAR(50),
	floor VARCHAR(50),
	fee numeric,
	mahal VARCHAR(20),
	telephone VARCHAR(20),
	left_travel VARCHAR(50),
	note VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS mm.customer_payment_details(
	customer_payment_id BIGINT PRIMARY KEY,
	customer_id BIGINT,
	paid char(1),
	CONSTRAINT customer_payment_details_fk FOREIGN KEY (customer_id) REFERENCES mm.customer_details (customer_id)
);

--INSERT INTO mm.user_auth_details VALUES (2860524688118663536,'admin','$2a$10$trT3.R/Nfey62eczbKEnueTcIbJXW.u1ffAo/XfyLpofwNDbEB86O','ADMIN',null,null,'ACTIVE');
--INSERT INTO mm.customer_details (customer_id,region,building,address,client,name,floor,fee,mahal,telephone,left_travel,note) VALUES (4641962035890310183,'orange','OA','الجمل زاروب الكشاف المسلم','OA1',' حسن مرعي ','1',20000,null,'01/702501',null,null);