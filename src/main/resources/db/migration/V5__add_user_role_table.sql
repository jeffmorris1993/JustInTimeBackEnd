CREATE TABLE ${db.prefix}.ROLE
(
    ID UUID DEFAULT uuid_generate_v4 () NOT NULL,
    name VARCHAR NOT NULL,
    PRIMARY KEY (ID)
);
