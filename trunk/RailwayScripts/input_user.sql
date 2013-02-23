-----------------------------------------Delete users------------------------------------------
DROP SEQUENCE USERS_SEQUANCE;
DROP TABLE USERS;
-----------------------------------------Users Table-------------------------------------------
CREATE TABLE Users
(
  user_id        varchar2(10)  NOT NULL ,
  user_name      varchar2(50)  NOT NULL ,
  user_password  varchar2(50)  NOT NULL ,
  user_status varchar2(50) CHECK ( user_status  IN ('TrainFormation', 'ScheduleFormation', 'OrderFormation', 'Admin')) 
);

ALTER TABLE Users ADD CONSTRAINT PK_Users PRIMARY KEY (user_id);

CREATE SEQUENCE users_sequance
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE
    ORDER;
INSERT INTO Users(user_id, user_name, user_password, user_status)
VALUES(users_sequance.nextval, 'Drop', 'Drop', 'TrainFormation');
INSERT INTO Users(user_id, user_name, user_password, user_status)
VALUES(users_sequance.nextval, 'KayF', 'KayF', 'ScheduleFormation');
INSERT INTO Users(user_id, user_name, user_password, user_status)
VALUES(users_sequance.nextval, 'Pandochka', 'Pandochka', 'OrderFormation');
INSERT INTO Users(user_id, user_name, user_password, user_status)
VALUES(users_sequance.nextval, 'Admin', 'Admin', 'Admin');
COMMIT;
-----------------------------------------------------------------------------------------------------------
