CREATE TABLE ${db.prefix}.USER
(
    ID UUID DEFAULT uuid_generate_v4 () NOT NULL,
    FIRST_NAME VARCHAR NULL,
    LAST_NAME VARCHAR NULL,
    EMAIL VARCHAR NOT NULL,
    PASSWORD VARCHAR NOT NULL,
    CREATED_AT TIMESTAMP NULL,
    UPDATED_AT TIMESTAMP NULL,
    PRIMARY KEY (ID),
    UNIQUE (EMAIL)
);