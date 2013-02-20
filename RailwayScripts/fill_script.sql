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
insert into CITY(FK_COUNTRY_ID, CITY_NAME) values(3,'Lublin);
/
-- Station
insert into STATION(FK_CITY_ID, STATION_NAME) values(1,'Kyiv Passenger');
insert into STATION(FK_CITY_ID, STATION_NAME) values(2,'Poltava Kyivska');
insert into STATION(FK_CITY_ID, STATION_NAME) values(2,'Poltava Pivdenna');
insert into STATION(FK_CITY_ID, STATION_NAME) values(3,'Chernihiv');
insert into STATION(FK_CITY_ID, STATION_NAME) values(4,'DNDZ');
insert into STATION(FK_CITY_ID, STATION_NAME) values(5,'Lviv Holovna');
/