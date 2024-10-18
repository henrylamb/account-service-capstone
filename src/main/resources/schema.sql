-- ----------------------------------------------
-- DDL Statements for tables
-- ----------------------------------------------

CREATE TABLE "USERS" ("ID" INTEGER NOT NULL AUTO_INCREMENT ,
"FULL_NAME" VARCHAR(50) DEFAULT NULL, 
"PASSWORD" VARCHAR(500), 
"EMAIL" VARCHAR(50) DEFAULT NULL,
"ADDRESS" VARCHAR(100) DEFAULT NULL,
"PHONE" VARCHAR(25) DEFAULT NULL,
"RESUME" TEXT,
"DEPARTMENT" TEXT,
"ROLE" VARCHAR(50),
"ADMIN_ID" INT DEFAULT NULL);

-- ----------------------------------------------
-- DDL Statements for keys
-- ----------------------------------------------

-- primary/unique
ALTER TABLE "USERS" ADD CONSTRAINT "SQL120325130144011" PRIMARY KEY ("ID");