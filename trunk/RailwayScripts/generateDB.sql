DROP TABLE Carriage CASCADE CONSTRAINTS;
DROP TABLE Carriage_freight_order_item CASCADE CONSTRAINTS;
DROP TABLE Carriage_type CASCADE CONSTRAINTS;
DROP TABLE Characteristic CASCADE CONSTRAINTS;
DROP TABLE Characteristic_type CASCADE CONSTRAINTS;
DROP TABLE City CASCADE CONSTRAINTS;
DROP TABLE Client CASCADE CONSTRAINTS;
DROP TABLE Country CASCADE CONSTRAINTS;
DROP TABLE Freight_order_item CASCADE CONSTRAINTS;
DROP TABLE Goods CASCADE CONSTRAINTS;
DROP TABLE Locomotive CASCADE CONSTRAINTS;
DROP TABLE Order_item CASCADE CONSTRAINTS;
DROP TABLE Passenger_order_item CASCADE CONSTRAINTS;
DROP TABLE Railroad CASCADE CONSTRAINTS;
DROP TABLE Rolling_stock CASCADE CONSTRAINTS;
DROP TABLE Route CASCADE CONSTRAINTS;
DROP TABLE Station CASCADE CONSTRAINTS;
DROP TABLE Train CASCADE CONSTRAINTS;
DROP TABLE Train_schedule CASCADE CONSTRAINTS;

DROP SEQUENCE "TRAINFORMATION"."SEQUENCE_CARRIAGE";
DROP SEQUENCE "TRAINFORMATION"."SEQUENCE_CARRIAGE_TYPE";
DROP SEQUENCE "TRAINFORMATION"."SEQUENCE_CHARACTERISTIC_TYPE";
DROP SEQUENCE "TRAINFORMATION"."SEQ_CITY";
DROP SEQUENCE "TRAINFORMATION"."SEQUENCE_CLIENT";
DROP SEQUENCE "TRAINFORMATION"."SEQ_COUNTRY";
DROP SEQUENCE "TRAINFORMATION"."SEQUENCE_ORDER";
DROP SEQUENCE "TRAINFORMATION"."SEQUENCE_POI";
DROP SEQUENCE "TRAINFORMATION"."SEQUENCE_ROLLING_STOCK";
DROP SEQUENCE "TRAINFORMATION"."SEQ_STATION";
DROP SEQUENCE "TRAINFORMATION"."SEQ_TRAIN_ID";
DROP SEQUENCE "TRAINFORMATION"."SEQUENCE_GOI";
DROP SEQUENCE "TRAINFORMATION"."LOCOMOTIVE_SEQUENCE";
-----------------------------------------------

CREATE TABLE Carriage 
    ( 
     carriage_id NUMBER  NOT NULL , 
     carriage_mark VARCHAR2 (50 CHAR)  NOT NULL , 
     fk_rolling_stock_id NUMBER, 
     fk_carriage_type_id NUMBER  
    ) 
;



ALTER TABLE Carriage 
    ADD CONSTRAINT Carriage_PK PRIMARY KEY ( carriage_id ) ;



CREATE TABLE Carriage_freight_order_item 
    ( 
     fk_carriage_id NUMBER  NOT NULL , 
     fk_foi_id NUMBER  NOT NULL 
    ) 
;




CREATE TABLE Carriage_type 
    ( 
     carriage_type_id NUMBER  NOT NULL , 
     carriage_type_name VARCHAR2 (50 CHAR)  NOT NULL , 
     fk_parrent_type_id NUMBER 
    ) 
;



ALTER TABLE Carriage_type 
    ADD CONSTRAINT Carriage_type_PK PRIMARY KEY ( carriage_type_id ) ;



CREATE TABLE Characteristic 
    ( 
     fk_carriage_type_id NUMBER  NOT NULL , 
     characteristic_type NUMBER , 
     value VARCHAR2 (50 CHAR) 
    ) 
;


ALTER TABLE Characteristic 
    ADD CONSTRAINT Characteristic_PK PRIMARY KEY ( fk_carriage_type_id, characteristic_type ) ;



CREATE TABLE Characteristic_type 
    ( 
     characteristic_type_id NUMBER  NOT NULL , 
     characteristic_name VARCHAR2 (50 CHAR)  NOT NULL 
    ) 
;



ALTER TABLE Characteristic_type 
    ADD CONSTRAINT Characteristic_type_PK PRIMARY KEY ( characteristic_type_id ) ;



CREATE TABLE City 
    ( 
     city_id NUMBER  NOT NULL , 
     fk_country_id NUMBER  NOT NULL , 
     city_name VARCHAR(50) 
    ) 
;



ALTER TABLE City 
    ADD CONSTRAINT City_PK PRIMARY KEY ( city_id ) ;



CREATE TABLE Client 
    ( 
     client_id NUMBER  NOT NULL , 
     name VARCHAR2 (30)  NOT NULL , 
     surname VARCHAR2 (30)  NOT NULL , 
     phone_number VARCHAR2 (11)  NOT NULL , 
     age INTEGER , 
     discount INTEGER , 
     email VARCHAR2 (50)  NOT NULL 
    ) 
;



ALTER TABLE Client 
    ADD CONSTRAINT Client_PK PRIMARY KEY ( client_id ) ;



CREATE TABLE Country 
    ( 
     country_id NUMBER  NOT NULL , 
     country_name VARCHAR(50) 
    ) 
;



ALTER TABLE Country 
    ADD CONSTRAINT Country_PK PRIMARY KEY ( country_id ) ;



CREATE TABLE Freight_order_item 
    ( 
     foi_id NUMBER  NOT NULL , 
     fk_order_id NUMBER  NOT NULL , 
     fk_goods_name VARCHAR2 (50)  NOT NULL , 
     mass INTEGER  NOT NULL , 
     origin VARCHAR2 (50)  NOT NULL , 
     delivery VARCHAR2 (50)  NOT NULL 
    ) 
;



ALTER TABLE Freight_order_item 
    ADD CONSTRAINT Feight_order_items_PK PRIMARY KEY ( foi_id ) ;



CREATE TABLE Goods 
    ( 
     goods_name VARCHAR2 (50)  NOT NULL , 
     goods_type VARCHAR2 (50)  NOT NULL 
    ) 
;



ALTER TABLE Goods 
    ADD CONSTRAINT Goods_PK PRIMARY KEY ( goods_name ) ;



CREATE TABLE Locomotive 
    ( 
     locomotive_id NUMBER  NOT NULL , 
     locomotive_type VARCHAR2 (50)  NOT NULL , 
     number_carriages NUMBER  NOT NULL , 
     railroad_type VARCHAR2 (50)  NOT NULL , 
     locomotive_mark VARCHAR2 (50) 
    ) 
;



ALTER TABLE Locomotive 
    ADD CONSTRAINT Locomotive_PK PRIMARY KEY ( locomotive_id ) ;



CREATE TABLE Order_item 
    ( 
     order_id NUMBER  NOT NULL , 
     fk_client_id NUMBER  NOT NULL , 
     order_date DATE  NOT NULL 
    ) 
;



ALTER TABLE Order_item 
    ADD CONSTRAINT Order_item_PK PRIMARY KEY ( order_id ) ;



CREATE TABLE Passenger_order_item 
    ( 
     poi_id NUMBER  NOT NULL , 
     fk_order_id NUMBER  NOT NULL , 
     fk_train_id NUMBER  NOT NULL , 
     "date" DATE  NOT NULL , 
     vagon_number NUMBER  NOT NULL , 
     place_number NUMBER  NOT NULL 
    ) 
;



ALTER TABLE Passenger_order_item 
    ADD CONSTRAINT Passenger_order_item_PK PRIMARY KEY ( poi_id ) ;



CREATE TABLE Railroad 
    ( 
     railroad_type VARCHAR2 (50)  NOT NULL , 
     fk_railroad_source_id NUMBER  NOT NULL , 
     fk_railroad_destination_id NUMBER  NOT NULL , 
     railroad_id NUMBER  NOT NULL 
    ) 
;



ALTER TABLE Railroad 
    ADD CONSTRAINT Railroad_PK PRIMARY KEY ( railroad_id ) ;



CREATE TABLE Rolling_stock 
    ( 
     rolling_stock_id NUMBER  NOT NULL , 
     fk_lokomotive_id NUMBER  NOT NULL
    ) 
;



ALTER TABLE Rolling_stock 
    ADD CONSTRAINT RollingStock_PK PRIMARY KEY ( rolling_stock_id ) ;


ALTER TABLE Rolling_stock 
    ADD CONSTRAINT Rolling_stock__UN UNIQUE ( fk_lokomotive_id ) ;




CREATE TABLE Route 
    ( 
     fk_train_id NUMBER  NOT NULL , 
     fk_arrive_station_id NUMBER  NOT NULL , 
     fk_destination_station_id NUMBER  NOT NULL , 
     arrive_time DATE , 
     travel_time DATE 
    ) 
;



ALTER TABLE Route 
    ADD CONSTRAINT Route_PK PRIMARY KEY ( fk_arrive_station_id, fk_train_id, fk_destination_station_id ) ;



CREATE TABLE Station 
    ( 
     station_id NUMBER  NOT NULL , 
     fk_city_id NUMBER  NOT NULL , 
     station_name VARCHAR(50) 
    ) 
;



ALTER TABLE Station 
    ADD CONSTRAINT Station_PK PRIMARY KEY ( station_id ) ;



CREATE TABLE Train 
    ( 
     train_id NUMBER  NOT NULL , 
     fk_rolling_stock_id NUMBER  NOT NULL , 
     fk_arrive_station_id NUMBER  NOT NULL , 
     train_name VARCHAR(50) , 
     arrive_time DATE 
    ) 
;



ALTER TABLE Train 
    ADD CONSTRAINT Train_PK PRIMARY KEY ( train_id ) ;



CREATE TABLE Train_schedule 
    ( 
     fk_train_id NUMBER  NOT NULL , 
     data_arrive TIMESTAMP 
    ) 
;





ALTER TABLE Carriage_freight_order_item 
    ADD CONSTRAINT CarriageFOI_Carriage_FK FOREIGN KEY 
    ( 
     fk_carriage_id
    ) 
    REFERENCES Carriage 
    ( 
     carriage_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Carriage_freight_order_item 
    ADD CONSTRAINT CarriageFOI_FOI_FK FOREIGN KEY 
    ( 
     fk_foi_id
    ) 
    REFERENCES Freight_order_item 
    ( 
     foi_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Carriage_type 
    ADD CONSTRAINT CarriageType_CarriageType_FK FOREIGN KEY 
    ( 
     fk_parrent_type_id
    ) 
    REFERENCES Carriage_type 
    ( 
     carriage_type_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Carriage 
    ADD CONSTRAINT Carriage_CarriageType_FK FOREIGN KEY 
    ( 
     fk_carriage_type_id
    ) 
    REFERENCES Carriage_type 
    ( 
     carriage_type_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Carriage 
    ADD CONSTRAINT Carriage_RollingStack_FK FOREIGN KEY 
    ( 
     fk_rolling_stock_id
    ) 
    REFERENCES Rolling_stock 
    ( 
     rolling_stock_id
    ) 
    ON DELETE SET NULL 
;


ALTER TABLE Characteristic 
    ADD CONSTRAINT Char_CarriageType_FK FOREIGN KEY 
    ( 
     fk_carriage_type_id
    ) 
    REFERENCES Carriage_type 
    ( 
     carriage_type_id
    ) 
    ON DELETE CASCADE 
;

ALTER TABLE Characteristic 
    ADD CONSTRAINT Char_Char_type_FK FOREIGN KEY 
    ( 
     characteristic_type
    ) 
    REFERENCES Characteristic_type 
    ( 
     characteristic_type_id
    ) 
    ON DELETE CASCADE 
;






ALTER TABLE City 
    ADD CONSTRAINT City_Country_FK FOREIGN KEY 
    ( 
     fk_country_id
    ) 
    REFERENCES Country 
    ( 
     country_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Freight_order_item 
    ADD CONSTRAINT FeightOrderItems_Goods_FK FOREIGN KEY 
    ( 
     fk_goods_name
    ) 
    REFERENCES Goods 
    ( 
     goods_name
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Freight_order_item 
    ADD CONSTRAINT FeightOrderItems_order_FK FOREIGN KEY 
    ( 
     fk_order_id
    ) 
    REFERENCES Order_item 
    ( 
     order_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Passenger_order_item 
    ADD CONSTRAINT PassengerOrderItems_Train_FK FOREIGN KEY 
    ( 
     fk_train_id
    ) 
    REFERENCES Train 
    ( 
     train_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Railroad 
    ADD CONSTRAINT Railroad_Station_FK FOREIGN KEY 
    ( 
     fk_railroad_destination_id
    ) 
    REFERENCES Station 
    ( 
     station_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Railroad 
    ADD CONSTRAINT Railroad_Station_FKv1 FOREIGN KEY 
    ( 
     fk_railroad_source_id
    ) 
    REFERENCES Station 
    ( 
     station_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Rolling_stock 
    ADD CONSTRAINT RollingStack_Lokomotive_FK FOREIGN KEY 
    ( 
     fk_lokomotive_id
    ) 
    REFERENCES Locomotive 
    ( 
     locomotive_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Route 
    ADD CONSTRAINT Route_Station_FK FOREIGN KEY 
    ( 
     fk_arrive_station_id
    ) 
    REFERENCES Station 
    ( 
     station_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Route 
    ADD CONSTRAINT Route_Station_FKv2 FOREIGN KEY 
    ( 
     fk_destination_station_id
    ) 
    REFERENCES Station 
    ( 
     station_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Route 
    ADD CONSTRAINT Route_Train_FK FOREIGN KEY 
    ( 
     fk_train_id
    ) 
    REFERENCES Train 
    ( 
     train_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Station 
    ADD CONSTRAINT Station_City_FK FOREIGN KEY 
    ( 
     fk_city_id
    ) 
    REFERENCES City 
    ( 
     city_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Order_item 
    ADD CONSTRAINT TABLE_2_client_FK FOREIGN KEY 
    ( 
     fk_client_id
    ) 
    REFERENCES Client 
    ( 
     client_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Passenger_order_item 
    ADD CONSTRAINT TABLE_4_order_FK FOREIGN KEY 
    ( 
     fk_order_id
    ) 
    REFERENCES Order_item 
    ( 
     order_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Train 
    ADD CONSTRAINT "Train-ARRIVE_STATION_FK" FOREIGN KEY 
    ( 
     fk_arrive_station_id
    ) 
    REFERENCES Station 
    ( 
     station_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Train 
    ADD CONSTRAINT Train_RollingStack_FK FOREIGN KEY 
    ( 
     fk_rolling_stock_id
    ) 
    REFERENCES Rolling_stock 
    ( 
     rolling_stock_id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE Train_schedule 
    ADD CONSTRAINT Train_schedule_Train_FK FOREIGN KEY 
    ( 
     fk_train_id
    ) 
    REFERENCES Train 
    ( 
     train_id
    ) 
    ON DELETE CASCADE 
;

CREATE SEQUENCE sequence_carriage 
    NOCACHE 
    ORDER ;

CREATE OR REPLACE TRIGGER trigger_carriage 
BEFORE INSERT ON Carriage 
FOR EACH ROW 
BEGIN 
    SELECT sequence_carriage.NEXTVAL INTO :NEW.carriage_id FROM DUAL; 
END;
/

CREATE SEQUENCE sequence_carriage_type 
    NOCACHE 
    ORDER ;

CREATE OR REPLACE TRIGGER trigger_carriage_type 
BEFORE INSERT ON Carriage_type 
FOR EACH ROW 
BEGIN 
    SELECT sequence_carriage_type.NEXTVAL INTO :NEW.carriage_type_id FROM DUAL; 
END;
/

CREATE SEQUENCE sequence_characteristic_type 
    NOCACHE 
    ORDER ;

CREATE OR REPLACE TRIGGER triger_characteristic_type 
BEFORE INSERT ON Characteristic_type 
FOR EACH ROW 
BEGIN 
    SELECT sequence_characteristic_type.NEXTVAL INTO :NEW.characteristic_type_id FROM DUAL; 
END;
/

CREATE SEQUENCE seq_city 
    NOCACHE 
    ORDER ;

CREATE OR REPLACE TRIGGER trg_insert_city 
BEFORE INSERT ON City 
FOR EACH ROW 
BEGIN 
    SELECT seq_city.NEXTVAL INTO :NEW.city_id FROM DUAL; 
END;
/

CREATE SEQUENCE sequence_client 
START WITH 0 
    MINVALUE 0 
    MAXVALUE 99999 
    NOCACHE 
    ORDER ;

CREATE OR REPLACE TRIGGER client_insert 
BEFORE INSERT ON Client 
FOR EACH ROW 
BEGIN 
    SELECT sequence_client.NEXTVAL INTO :NEW.client_id FROM DUAL; 
END;
/

CREATE SEQUENCE seq_country 
    NOCACHE 
    ORDER ;

CREATE OR REPLACE TRIGGER trg_insert_country 
BEFORE INSERT ON Country 
FOR EACH ROW 
BEGIN 
    SELECT seq_country.NEXTVAL INTO :NEW.country_id FROM DUAL; 
END;
/

CREATE SEQUENCE sequence_goi 
START WITH 0 
    MINVALUE 0 
    MAXVALUE 99999 
    NOCACHE 
    ORDER ;

CREATE OR REPLACE TRIGGER goi_insert 
BEFORE INSERT ON Freight_order_item 
FOR EACH ROW 
BEGIN 
    SELECT sequence_goi.NEXTVAL INTO :NEW.foi_id FROM DUAL; 
END;
/

CREATE SEQUENCE locomotive_sequence 
    NOCACHE 
    ORDER ;

CREATE OR REPLACE TRIGGER locomotive_trigger_insert 
BEFORE INSERT ON Locomotive 
FOR EACH ROW 
BEGIN 
    SELECT locomotive_sequence.NEXTVAL INTO :NEW.locomotive_id FROM DUAL; 
END;
/

CREATE SEQUENCE sequence_order 
START WITH 0 
    MINVALUE 0 
    MAXVALUE 99999 
    NOCACHE 
    ORDER ;

CREATE OR REPLACE TRIGGER order_insert 
BEFORE INSERT ON Order_item 
FOR EACH ROW 
BEGIN 
    SELECT sequence_order.NEXTVAL INTO :NEW.order_id FROM DUAL; 
END;
/

CREATE SEQUENCE sequence_poi 
START WITH 0 
    MINVALUE 0 
    MAXVALUE 99999 
    NOCACHE 
    ORDER ;

CREATE OR REPLACE TRIGGER insert_poi 
BEFORE INSERT ON Passenger_order_item 
FOR EACH ROW 
BEGIN 
    SELECT sequence_poi.NEXTVAL INTO :NEW.poi_id FROM DUAL; 
END;
/

CREATE SEQUENCE sequence_rolling_stock 
    NOCACHE 
    ORDER ;

CREATE OR REPLACE TRIGGER trigger_rolling_stock 
BEFORE INSERT ON Rolling_stock 
FOR EACH ROW 
BEGIN 
    SELECT sequence_rolling_stock.NEXTVAL INTO :NEW.rolling_stock_id FROM DUAL; 
END;
/

CREATE SEQUENCE seq_station 
    NOCACHE 
    ORDER ;

CREATE OR REPLACE TRIGGER trg_insert_station 
BEFORE INSERT ON Station 
FOR EACH ROW 
BEGIN 
    SELECT seq_station.NEXTVAL INTO :NEW.station_id FROM DUAL; 
END;
/

CREATE SEQUENCE seq_train_id 
    NOCACHE 
    ORDER ;

CREATE OR REPLACE TRIGGER trg_insert_train 
BEFORE INSERT ON Train 
FOR EACH ROW 
BEGIN 
    SELECT seq_train_id.NEXTVAL INTO :NEW.train_id FROM DUAL; 
END;
/


CREATE OR REPLACE PROCEDURE add_carriage_to_stock(carriage NUMBER) AS 
BEGIN
  UPDATE carriage SET fk_rolling_stock_id  = 
                    (SELECT max(rolling_stock_id) FROM rolling_stock)
                     WHERE CARRIAGE_ID = carriage;
END add_carriage_to_stock;
/

CREATE OR REPLACE PROCEDURE CREATE_NEW_STOCK(loco NUMBER) AS 
BEGIN
  insert into Rolling_stock values(1, loco);  
END CREATE_NEW_STOCK;
/

CREATE OR REPLACE PROCEDURE add_station(add_station_name IN varchar2, find_city_name IN varchar2) 
IS
find_city_id NUMBER;
BEGIN
  SELECT city_id INTO find_city_id FROM city
    where find_city_name = city_name;
  
  INSERT INTO station(station_name,fk_city_id) values(add_station_name,find_city_id);
END;
/

CREATE OR REPLACE PROCEDURE add_city(add_city_name IN varchar2, find_country_name IN varchar2) 
IS
find_country_id NUMBER;
BEGIN
  SELECT country_id INTO find_country_id FROM country
    where find_country_name = country_name;
  
  INSERT INTO city(city_name,fk_country_id) values(add_city_name,find_country_id);
END;
/

CREATE OR REPLACE PROCEDURE add_country(add_country_name IN varchar2, no_need_argument IN varchar2) 
IS
BEGIN
  INSERT INTO country(country_name) values(add_country_name);
END;

CREATE OR REPLACE PROCEDURE delete_station(del_station_name IN varchar2) 
IS
BEGIN
  DELETE FROM station WHERE del_station_name = station_name;
END;
/

CREATE OR REPLACE PROCEDURE delete_city(del_city_name IN varchar2) 
IS
BEGIN
  DELETE FROM city WHERE del_city_name = city_name;
END;
/

CREATE OR REPLACE PROCEDURE delete_country(del_country_name IN varchar2) 
IS
BEGIN
  DELETE FROM country WHERE del_country_name = country_name;
END;
/
CREATE INDEX "FK_CHARACTERISTIC_TYPE" ON "CHARACTERISTIC" ("CHARACTERISTIC_TYPE");
/
CREATE INDEX "FK_ROLLING_STOCK_ID" ON "TRAINFORMATION"."CARRIAGE" ("FK_ROLLING_STOCK_ID"); 
/
CREATE INDEX "TRAINFORMATION"."STATION_NAME" ON "TRAINFORMATION"."STATION" ("FK_CITY_ID");
/
COMMIT;