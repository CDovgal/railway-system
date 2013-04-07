--client
insert into Client (name, surname, phone_number, email) values('Sasha','Dushin','0631111111','san4ikpro@gmail.com');
insert into Client (name, surname, phone_number, email) values('Sasha','Ignatenko','0938573045','alex.legend@gmail.com');
insert into Client (name, surname, phone_number, email) values('Kostya','Dovgal','0952222222','k.dovgal@gmail.com');
/
--order_item
insert into order_item ( fk_client_id, order_date) values(0, to_date('12-11-2012', 'dd-mm-yyyy'));
insert into order_item ( fk_client_id, order_date) values(1, to_date('12-11-2012', 'dd-mm-yyyy'));
insert into order_item ( fk_client_id, order_date) values(1, to_date('13-11-2012', 'dd-mm-yyyy'));
insert into order_item ( fk_client_id, order_date) values(2, to_date('12-11-2012', 'dd-mm-yyyy'));
/
--goods
insert into goods values('Vodka','Box');
insert into goods values('Beer','Box');
insert into goods values('Pizza','Box');
insert into goods values('Oil','Liquid');
insert into goods values('Water','Liquid');
/
--locomotive
insert into locomotive (locomotive_id, locomotive_type, number_carriages, railroad_type, locomotive_mark) values(1, 'D',10, 'All', 'USSR1');
insert into locomotive (locomotive_id, locomotive_type, number_carriages, railroad_type, locomotive_mark) values(2, 'E',15, 'Electro', 'USSR1');
insert into locomotive (locomotive_id, locomotive_type, number_carriages, railroad_type, locomotive_mark) values(3, 'T',20, 'All', 'USSR2');
insert into locomotive (locomotive_id, locomotive_type, number_carriages, railroad_type, locomotive_mark) values(4, 'E',15, 'Electro', 'USSR1');
insert into locomotive (locomotive_id, locomotive_type, number_carriages, railroad_type, locomotive_mark) values(5, 'D',10, 'All', 'USSR1');
insert into locomotive (locomotive_id, locomotive_type, number_carriages, railroad_type, locomotive_mark) values(6, 'T',20, 'All', 'USSR2');
/
--roling_stock
insert into Rolling_stock values(1, 1);
insert into Rolling_stock values(2, 2);
insert into Rolling_stock values(3, 3);
insert into Rolling_stock values(4, 4);
insert into Rolling_stock values(5, 5);
/
-- Country
insert into COUNTRY(COUNTRY_NAME) values('Ukraine');
insert into COUNTRY(COUNTRY_NAME) values('Russia');
insert into COUNTRY(COUNTRY_NAME) values('Poland');
/
-- Cities
insert into CITY(FK_COUNTRY_ID, CITY_NAME) values(1,'Kyiv');
insert into CITY(FK_COUNTRY_ID, CITY_NAME) values(1,'Poltava');
insert into CITY(FK_COUNTRY_ID, CITY_NAME) values(1,'Chernihiv');
insert into CITY(FK_COUNTRY_ID, CITY_NAME) values(1,'DNDZ');
insert into CITY(FK_COUNTRY_ID, CITY_NAME) values(1,'Lviv');

insert into CITY(FK_COUNTRY_ID, CITY_NAME) values(2,'Moscow');
insert into CITY(FK_COUNTRY_ID, CITY_NAME) values(3,'Saint-Petersbourg');

insert into CITY(FK_COUNTRY_ID, CITY_NAME) values(3,'Warsaw');
insert into CITY(FK_COUNTRY_ID, CITY_NAME) values(3,'Lublin');
/
-- Station
insert into STATION(FK_CITY_ID, STATION_NAME) values(1,'Kyiv Passenger');
insert into STATION(FK_CITY_ID, STATION_NAME) values(2,'Poltava Kyivska');
insert into STATION(FK_CITY_ID, STATION_NAME) values(2,'Poltava Pivdenna');
insert into STATION(FK_CITY_ID, STATION_NAME) values(3,'Chernihiv');
insert into STATION(FK_CITY_ID, STATION_NAME) values(4,'Dndz');
insert into STATION(FK_CITY_ID, STATION_NAME) values(5,'Lviv Holovna');
/
--train
insert into train(fk_rolling_stock_id,fk_departure_station_id,fk_arrival_station_id) values(1,1,6);
insert into train(fk_rolling_stock_id,fk_departure_station_id,fk_arrival_station_id) values(2,1,4);
insert into train(fk_rolling_stock_id,fk_departure_station_id,fk_arrival_station_id) values(3,1,4);
insert into train(fk_rolling_stock_id,fk_departure_station_id,fk_arrival_station_id) values(4,2,3);
insert into train(fk_rolling_stock_id,fk_departure_station_id,fk_arrival_station_id) values(5,2,1);
/
--Route
insert into route(fk_train_id,fk_departure_station_id,fk_arrival_station_id,departure_time,travel_time) values(1,1,2,to_timestamp('12:00','HH24:MI'),interval '5:50' hour to minute);
insert into route(fk_train_id,fk_departure_station_id,fk_arrival_station_id,departure_time,travel_time) values(1,2,6,to_timestamp('18:00','HH24:MI'),interval '3:32' hour to minute);
--insert into route(fk_train_id,fk_departure_station_id,fk_arrival_station_id) values(2,2,3);
--insert into route(fk_train_id,fk_departure_station_id,fk_arrival_station_id) values(3,2,1);
--insert into route(fk_train_id,fk_departure_station_id,fk_arrival_station_id) values(1,2,3);
/
--train_schedule
insert into train_schedule(fk_train_id) values(1);
insert into train_schedule(fk_train_id) values(2);
insert into train_schedule(fk_train_id) values(3);
insert into train_schedule(fk_train_id) values(4);
/
--railroad
insert into railroad(railroad_id, fk_railroad_source_id, fk_railroad_destination_id, railroad_type) values(1, 1, 2, 'Electro');
insert into railroad(railroad_id, fk_railroad_source_id, fk_railroad_destination_id, railroad_type) values(2, 2, 3, 'Electro');
insert into railroad(railroad_id, fk_railroad_source_id, fk_railroad_destination_id, railroad_type) values(3, 3, 4, 'All');
insert into railroad(railroad_id, fk_railroad_source_id, fk_railroad_destination_id, railroad_type) values(4, 4, 1, 'All');
insert into railroad(railroad_id, fk_railroad_source_id, fk_railroad_destination_id, railroad_type) values(5, 4, 3, 'All');
/
--freight order items
insert into freight_order_item values(1, 1, 'Vodka', 3000, 'Dndz', 'Kyev');
insert into freight_order_item values(1, 2, 'Beer', 3000, 'Chernihiv', 'Kyev');
insert into freight_order_item values(1, 3, 'Oil', 3000, 'Poltava', 'Kyev');
insert into freight_order_item values(1, 1, 'Oil', 3000, 'Dndz', 'Kyev');
insert into freight_order_item values(1, 2, 'Water', 3000, 'Chernihiv', 'Dndz');

--passanger_order_item
insert into passenger_order_item values(1, 1, 1, to_date('12-11-2012', 'dd-mm-yyyy'), 5, 13);
insert into passenger_order_item values(2, 2, 2, to_date('12-11-2012', 'dd-mm-yyyy'), 5, 4);
insert into passenger_order_item values(3, 1, 3, to_date('12-11-2012', 'dd-mm-yyyy'), 5, 10);
insert into passenger_order_item values(4, 2, 2, to_date('12-11-2012', 'dd-mm-yyyy'), 5, 11);
insert into passenger_order_item values(5, 3, 4, to_date('12-11-2012', 'dd-mm-yyyy'), 5, 12);
----------
--characteristic_type
INSERT INTO characteristic_type(characteristic_type_id, characteristic_name) VALUES(1, 'Carrying capacity');
INSERT INTO characteristic_type(characteristic_type_id, characteristic_name) VALUES(2, 'Seating capacity');
INSERT INTO characteristic_type(characteristic_type_id, characteristic_name) VALUES(3, 'Weight');
-----------------------------
--carriages_type
INSERT INTO carriage_type(carriage_type_id, carriage_type_name, fk_parrent_type_id) VALUES(1,'Passenger', null);
INSERT INTO carriage_type(carriage_type_id, carriage_type_name, fk_parrent_type_id) VALUES(2,'Freight', null);
INSERT INTO carriage_type(carriage_type_id, carriage_type_name, fk_parrent_type_id) VALUES(3,'Compartment',1);
INSERT INTO carriage_type(carriage_type_id, carriage_type_name, fk_parrent_type_id) VALUES(4,'Couchette',1);
INSERT INTO carriage_type(carriage_type_id, carriage_type_name, fk_parrent_type_id) VALUES(5,'Boxcar',2);
INSERT INTO carriage_type(carriage_type_id, carriage_type_name, fk_parrent_type_id) VALUES(6,'Tankcar',2);
INSERT INTO carriage_type(carriage_type_id, carriage_type_name, fk_parrent_type_id) VALUES(7,'Dumpcar',2);
------------------------------
--characteristic
INSERT INTO characteristic(fk_carriage_type_id, characteristic_type, value) VALUES(3, 2, 40);
INSERT INTO characteristic(fk_carriage_type_id, characteristic_type, value) VALUES(3, 3, 57000);
INSERT INTO characteristic(fk_carriage_type_id, characteristic_type, value) VALUES(4, 2, 30);
INSERT INTO characteristic(fk_carriage_type_id, characteristic_type, value) VALUES(4, 3, 56000);
INSERT INTO characteristic(fk_carriage_type_id, characteristic_type, value) VALUES(5, 1, 68000);
INSERT INTO characteristic(fk_carriage_type_id, characteristic_type, value) VALUES(5, 3, 23000);
INSERT INTO characteristic(fk_carriage_type_id, characteristic_type, value) VALUES(6, 1, 65000);
INSERT INTO characteristic(fk_carriage_type_id, characteristic_type, value) VALUES(6, 3, 23000);
INSERT INTO characteristic(fk_carriage_type_id, characteristic_type, value) VALUES(7, 1, 70000);
INSERT INTO characteristic(fk_carriage_type_id, characteristic_type, value) VALUES(7, 3, 22000);
-------------------------------
--carriages
INSERT INTO carriage(carriage_id, carriage_mark, fk_carriage_type_id, fk_rolling_stock_id) VALUES(1, 'AVRORA', 3, null);
INSERT INTO carriage(carriage_id, carriage_mark, fk_carriage_type_id, fk_rolling_stock_id) VALUES(2, 'HYUNDAI', 4, null);
INSERT INTO carriage(carriage_id, carriage_mark, fk_carriage_type_id, fk_rolling_stock_id) VALUES(3, 'HYUNDAI', 4, 1);
INSERT INTO carriage(carriage_id, carriage_mark, fk_carriage_type_id, fk_rolling_stock_id) VALUES(4, 'HYUNDAI', 4, 1);
INSERT INTO carriage(carriage_id, carriage_mark, fk_carriage_type_id, fk_rolling_stock_id) VALUES(5, 'EVROTRAIN', 5, 2);
INSERT INTO carriage(carriage_id, carriage_mark, fk_carriage_type_id, fk_rolling_stock_id) VALUES(6, 'EVROTRAIN', 6, 2);
INSERT INTO carriage(carriage_id, carriage_mark, fk_carriage_type_id, fk_rolling_stock_id) VALUES(7, 'EVROTRAIN', 7, 1);
-----------------------------------------
--CARRIAGE_FREIGHT_ORDER_ITEM
INSERT INTO carriage_freight_order_item VALUES (5,0);
INSERT INTO carriage_freight_order_item VALUES (5,1);
INSERT INTO carriage_freight_order_item VALUES (7,2);
INSERT INTO carriage_freight_order_item VALUES (7,3);
INSERT INTO carriage_freight_order_item VALUES (5,4);
-----------------------------------------------
COMMIT;