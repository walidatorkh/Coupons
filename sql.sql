

Create table Company(ID bigint NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), COMP_NAME varchar(25), PASSWORD varchar (15), EMAIL varchar(25), PRIMARY KEY (EMAIL) );
 
Create table Customer(ID bigint NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), CUST_NAME varchar(25), PASSWORD varchar (15),EMAIL varchar(25), PRIMARY KEY (EMAIL) );

Create table Coupon (ID bigint NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), TITLE varchar(25),START_DATE DATE , END_DATE DATE, AMOUNT INTEGER, TYPE VARCHAR (20), MESSAGE varchar(30),PRICE DOUBLE, IMAGE varchar(30) , PRIMARY KEY (TITLE) );

Create table Customer_Coupon (CUST_ID BIGINT, COUPON_ID BIGINT , PRIMARY KEY (CUST_ID , COUPON_ID));

Create table Company_Coupon (COMPANY_ID BIGINT, COUPON_ID BIGINT , PRIMARY KEY (COMPANY_ID , COUPON_ID));



 INSERT INTO COMPANY (comp_name , password,email) values ('Test Company 3', 'Password 3','email2@coupon.com');
  INSERT INTO coupon (TITLE,START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE) values ('TITLE','2017-01-01', '2018-01-01', 30, 'RESTAURANTS', 'message', 1.1, 'IMAGE');
 INSERT INTO Company_Coupon (COMPANY_ID , COUPON_ID) values (1,1);

exec CURRENT_DATE;
 
 ALTER TABLE customer ADD EMAIL  varchar(25);
 

 
select * from COMPANY;
select * from customer;
select * from Coupon;
select * from Customer_Coupon;
select * from Company_Coupon;

select ID, TITLE,START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE from COUPON where Type='RESTAURANTS';

select count (comp_name) AS numOfNames from COMPANY where comp_name =  'Test Company 2';

select id,comp_name,password,email from COMPANY where comp_name = 'Test Company 1';

select COUPON_ID from Customer_Coupon where Cust_ID = 2;

DELETE FROM COMPANY;
DELETE FROM customer;
DELETE FROM coupon;
DELETE FROM Company_Coupon;
DELETE FROM Customer_Coupon;

DELETE FROM COMPANY WHERE id=1; 
DELETE FROM Company_Coupon WHERE COMPANY_ID=0; 


--DROP TABLE COMPANY;
--DROP TABLE customer;
--DROP TABLE coupon;


ALTER TABLE COMPANY DROP PRIMARY KEY  ;
ALTER TABLE customer DROP PRIMARY KEY  ;


ALTER TABLE COMPANY add PRIMARY KEY(email)  ;
ALTER TABLE customer add PRIMARY KEY(email) ;





