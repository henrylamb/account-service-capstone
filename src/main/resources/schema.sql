-- ----------------------------------------------
-- DDL Statements for tables
-- ----------------------------------------------

CREATE TABLE IF NOT EXISTS USERS (
                       ID INTEGER NOT NULL AUTO_INCREMENT,
                       FULL_NAME VARCHAR(500),
                       PASSWORD VARCHAR(500),
                       EMAIL VARCHAR(500),
                       ADDRESS VARCHAR(100),
                       PHONE VARCHAR(250),
                       RESUME LONGTEXT,
                       DEPARTMENT VARCHAR(500),
                       ROLE VARCHAR(500),
                       ADMIN_ID INTEGER,
                       PRIMARY KEY (ID)
);
