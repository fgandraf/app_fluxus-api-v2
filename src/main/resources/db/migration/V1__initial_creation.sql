-----------------| USER |-----------------

CREATE SEQUENCE SEQ_USER
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE TBL_USER (
    USER_ID NUMBER(19,0) NOT NULL,
    PROFESSIONAL_ID NUMBER(19,0),
    TECHNICIAN_RESPONSIBLE NUMBER(1,0) DEFAULT 0 CHECK (TECHNICIAN_RESPONSIBLE BETWEEN 0 AND 1) NOT NULL,
    LEGAL_RESPONSIBLE NUMBER(1,0) DEFAULT 0 CHECK (LEGAL_RESPONSIBLE BETWEEN 0 AND 1) NOT NULL,
    ACTIVE NUMBER(1,0) DEFAULT 0 CHECK (ACTIVE BETWEEN 0 AND 1) NOT NULL,
    EMAIL VARCHAR2(255 CHAR) NOT NULL,
    PASSWORD VARCHAR2(255 CHAR) NOT NULL,
    ROLE VARCHAR2(10 CHAR) NOT NULL,

    PRIMARY KEY (USER_ID)
);

INSERT INTO
    TBL_USER
        (USER_ID, TECHNICIAN_RESPONSIBLE, LEGAL_RESPONSIBLE, ACTIVE, EMAIL, PASSWORD, ROLE)
    VALUES
        (1, 1, 1, 1, 'admin@admin.com', '$2a$10$WavPC5NSWR9UZRfU/EDjp.0VnK/y0xjATRTOGz.A.7ymKB4eH3/Je', 'ADMIN');
        -- Password '1234abcd' after encrypt

-----------------| BANK BRANCH |-----------------

CREATE SEQUENCE SEQ_BANK_BRANCH
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE TBL_BANK_BRANCH (
    BANK_BRANCH_ID NUMBER(19,0) NOT NULL,
    NAME VARCHAR2(100 CHAR) NOT NULL,
    ADDRESS VARCHAR2(255 CHAR),
    COMPLEMENT VARCHAR2(255 CHAR),
    DISTRICT VARCHAR2(100 CHAR),
    CITY VARCHAR2(100 CHAR),
    ZIP VARCHAR2(8 CHAR),
    STATE VARCHAR2(100 CHAR),
    CONTACT_NAME VARCHAR2(100 CHAR),
    PHONE1 VARCHAR2(11 CHAR),
    PHONE2 VARCHAR2(11 char),
    EMAIL VARCHAR2(100 CHAR),

    PRIMARY KEY (BANK_BRANCH_ID)
);

-----------------| PROFESSIONAL |-----------------

CREATE SEQUENCE SEQ_PROFESSIONAL
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE TBL_PROFESSIONAL (
    PROFESSIONAL_ID NUMBER(19,0) NOT NULL,
    TAG VARCHAR2(5 CHAR),
    NAME VARCHAR2(100 CHAR) NOT NULL,
    CPF VARCHAR2(11 CHAR),
    BIRTHDAY TIMESTAMP(6),
    PROFESSION VARCHAR2(50 CHAR) NOT NULL,
    PERMIT_NUMBER VARCHAR2(50 CHAR) NOT NULL,
    ASSOCIATION VARCHAR2(100 CHAR) NOT NULL,
    PHONE1 VARCHAR2(11 CHAR),
    PHONE2 VARCHAR2(11 CHAR),
    EMAIL VARCHAR2(100 CHAR),

    PRIMARY KEY (PROFESSIONAL_ID)
);

ALTER TABLE TBL_USER
    ADD CONSTRAINT FK_USER_PROFESSIONAL_ID
        FOREIGN KEY (PROFESSIONAL_ID)
            REFERENCES TBL_PROFESSIONAL;

-----------------| SERVICE |-----------------

CREATE SEQUENCE SEQ_SERVICE
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE TBL_SERVICE (
    SERVICE_ID NUMBER(19,0) NOT NULL,
    TAG VARCHAR2(10 CHAR),
    DESCRIPTION VARCHAR2(255 CHAR) NOT NULL,
    SERVICE_AMOUNT NUMBER(6,2) NOT NULL,
    MILEAGE_ALLOWANCE NUMBER(6,2) NOT NULL,

    PRIMARY KEY (SERVICE_ID)
);




-----------------| INVOICE |-----------------

-- CREATE SEQUENCE SEQ_INVOICE
--     START WITH 1
--     INCREMENT BY 1
--     NOCACHE
--     NOCYCLE;
--
-- CREATE TABLE TBL_INVOICE (
--     INVOICE_ID NUMBER(19,0) NOT NULL,
--     DESCRIPTION VARCHAR2(100 CHAR) NOT NULL,
--     ISSUE_DATE TIMESTAMP(6) NOT NULL,
--     SUBTOTAL_SERVICE NUMBER(6,2) NOT NULL,
--     SUBTOTAL_MILEAGE NUMBER(6,2) NOT NULL,
--     TOTAL NUMBER(6,2) NOT NULL,
--
--     PRIMARY KEY (INVOICE_ID)
-- );




-----------------| PROFILE |-----------------

-- CREATE SEQUENCE SEQ_PROFILE
--     START WITH 1
--     INCREMENT BY 1
--     NOCACHE
--     NOCYCLE;
--
-- CREATE TABLE TBL_PROFILE (
--     PROFILE_ID NUMBER(19,0) NOT NULL,
--     CNPJ VARCHAR2(14 CHAR),
--     TRADING_NAME VARCHAR2(100 CHAR),
--     COMPANY_NAME VARCHAR2(255 CHAR),
--     STATE_ID VARCHAR2(50 CHAR),
--     CITY_ID VARCHAR2(50 CHAR),
--     ADDRESS VARCHAR2(255 CHAR),
--     COMPLEMENT VARCHAR2(255 CHAR),
--     DISTRICT VARCHAR2(100 CHAR),
--     CITY VARCHAR2(100 CHAR),
--     ZIP VARCHAR2(8 CHAR),
--     STATE VARCHAR2(100 CHAR),
--     ESTABLISHMENT_DATE TIMESTAMP(6),
--     PHONE1 VARCHAR2(11 CHAR),
--     PHONE2 VARCHAR2(11 CHAR),
--     EMAIL VARCHAR2(100 CHAR),
--     BANK_ACCOUNT_NAME VARCHAR2(100 CHAR),
--     BANK_ACCOUNT_TYPE VARCHAR2(10 CHAR),
--     BANK_ACCOUNT_BRANCH NUMBER(5,0),
--     BANK_ACCOUNT_DIGIT NUMBER(2),
--     BANK_ACCOUNT_NUMBER NUMBER(20,0),
--     CONTRACTOR_NAME VARCHAR2(100 CHAR),
--     CONTRACT_NOTICE VARCHAR2(100 CHAR),
--     CONTRACT_NUMBER VARCHAR2(100 CHAR),
--     CONTRACT_ESTABLISHED TIMESTAMP(6),
--     CONTRACT_START TIMESTAMP(6),
--     CONTRACT_END TIMESTAMP(6),
--
--     PRIMARY KEY (PROFILE_ID)
-- );




-----------------| SERVICE ORDER |-----------------

-- CREATE SEQUENCE SEQ_SERVICE_ORDER
--     START WITH 1
--     INCREMENT BY 1
--     NOCACHE
--     NOCYCLE;
--
--
-- CREATE TABLE TBL_SERVICE_ORDER (
--     SERVICE_ORDER_ID NUMBER(19,0) NOT NULL,
--     REFERENCE_CODE VARCHAR2(27 CHAR) NOT NULL,
--     BRANCH_ID NUMBER(19,0) NOT NULL,
--     ORDER_DATE TIMESTAMP(6) NOT NULL,
--     DEADLINE TIMESTAMP(6) NOT NULL,
--     PROFESSIONAL_ID NUMBER(19,0) NOT NULL,
--     SERVICE_ID NUMBER(19,0) NOT NULL,
--     SERVICE_AMOUNT NUMBER(6,2) NOT NULL,
--     MILEAGE_ALLOWANCE NUMBER(6,2) NOT NULL,
--     SIOPI NUMBER(1,0) DEFAULT 0 CHECK (SIOPI BETWEEN 0 AND 1) NOT NULL,
--     CUSTOMER_NAME VARCHAR2(100 CHAR),
--     CITY VARCHAR2(100 CHAR),
--     CONTACT_NAME VARCHAR2(100 CHAR),
--     CONTACT_PHONE VARCHAR2(11 CHAR),
--     COORDINATES VARCHAR2(50 CHAR),
--     STATUS VARCHAR2(10 CHAR) NOT NULL,
--     PENDING_DATE TIMESTAMP(6),
--     SURVEY_DATE TIMESTAMP(6),
--     DONE_DATE TIMESTAMP(6),
--     INVOICED NUMBER(1,0) DEFAULT 0 CHECK (INVOICED BETWEEN 0 AND 1) NOT NULL,
--     INVOICE_ID NUMBER(19,0),
--
--     PRIMARY KEY (SERVICE_ORDER_ID)
-- );




-----------------| FK CONSTRAINTS |-----------------

-- ALTER TABLE TBL_SERVICE_ORDER
--     ADD CONSTRAINT FK_SERVICE_ORDER_BRANCH_ID
--         FOREIGN KEY (BRANCH_ID)
--             REFERENCES TBL_BANK_BRANCH;
--
-- ALTER TABLE TBL_SERVICE_ORDER
--     ADD CONSTRAINT FK_SERVICE_ORDER_PROFESSIONAL_ID
--         FOREIGN KEY (PROFESSIONAL_ID)
--             REFERENCES TBL_PROFESSIONAL;
--
-- ALTER TABLE TBL_SERVICE_ORDER
--     ADD CONSTRAINT FK_SERVICE_ORDER_SERVICE_ID
--         FOREIGN KEY (SERVICE_ID)
--             REFERENCES TBL_SERVICE;
--
-- ALTER TABLE TBL_SERVICE_ORDER
--     ADD CONSTRAINT FK_SERVICE_ORDER_INVOICE_ID
--         FOREIGN KEY (INVOICE_ID)
--             REFERENCES TBL_INVOICE;

