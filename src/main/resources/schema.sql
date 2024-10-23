-- ----------------------------------------------
-- DDL Statements for tables
-- ----------------------------------------------

CREATE TABLE "USERS" ("ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ,
"FULL_NAME" VARCHAR(500), 
"PASSWORD" VARCHAR(500), 
"EMAIL" VARCHAR(500),
"ADDRESS" VARCHAR(100),
"PHONE" VARCHAR(250),
"RESUME" VARCHAR(500),
"DEPARTMENT" VARCHAR(500),
"ROLE" VARCHAR(500),
"ADMIN_ID" INTEGER);

-- ----------------------------------------------
-- DDL Statements for keys
-- ----------------------------------------------

-- primary/unique
ALTER TABLE "USERS" ADD CONSTRAINT "SQL120325130144011" PRIMARY KEY ("ID");