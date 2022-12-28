CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE ${db.prefix}.CUSTOMER
(
    CUSTOMER_ID UUID DEFAULT uuid_generate_v4 () NOT NULL,
    FIRST_NAME VARCHAR NULL,
    LAST_NAME VARCHAR NULL,
    EMAIL VARCHAR NULL,
    PHONE VARCHAR NULL,
    STREET_ADDRESS VARCHAR NULL,
    CITY VARCHAR NULL,
    ZIP VARCHAR NULL,
    CREATED_AT TIMESTAMP NULL,
    UPDATED_AT TIMESTAMP NULL,
    PRIMARY KEY (CUSTOMER_ID)
);

CREATE TABLE ${db.prefix}.SERVICE_TYPE
(
    SERVICE_TYPE_ID SMALLSERIAL,
    SERVICE_TYPE VARCHAR NOT NULL,
    PRIMARY KEY (SERVICE_TYPE_ID)
);

CREATE TABLE ${db.prefix}.ADD_ONS_TYPE
(
    ADD_ON_TYPE_ID VARCHAR,
    ADD_ON_TYPE VARCHAR NOT NULL,
    PRIMARY KEY (ADD_ON_TYPE_ID)
);

CREATE TABLE ${db.prefix}.VEHICLE
(
    VEHICLE_ID UUID DEFAULT uuid_generate_v4 () NOT NULL,
    CUSTOMER_ID UUID NOT NULL,
    SERVICE_TYPE_ID INT NOT NULL,
    MAKE VARCHAR NULL,
    MODEL VARCHAR NULL,
    YEAR INT NULL,
    CREATED_AT TIMESTAMP NULL,
    UPDATED_AT TIMESTAMP NULL,
    PRIMARY KEY (VEHICLE_ID),
    CONSTRAINT fk_customer FOREIGN KEY (CUSTOMER_ID) REFERENCES ${db.prefix}.CUSTOMER(CUSTOMER_ID),
    CONSTRAINT fk_service_type FOREIGN KEY (SERVICE_TYPE_ID) REFERENCES ${db.prefix}.SERVICE_TYPE(SERVICE_TYPE_ID)
);

CREATE TABLE ${db.prefix}.ADD_ONS
(
    VEHICLE_ADD_ON_ID UUID DEFAULT uuid_generate_v4 () NOT NULL,
    VEHICLE_ID UUID NOT NULL,
    ADD_ON_ID VARCHAR NOT NULL,
    CREATED_AT TIMESTAMP NULL,
    UPDATED_AT TIMESTAMP NULL,
    PRIMARY KEY (VEHICLE_ADD_ON_ID),
    CONSTRAINT fk_vehicle FOREIGN KEY (VEHICLE_ID) REFERENCES ${db.prefix}.VEHICLE(VEHICLE_ID),
    CONSTRAINT fk_add_on FOREIGN KEY (ADD_ON_ID) REFERENCES ${db.prefix}.ADD_ONS_TYPE(ADD_ON_TYPE_ID)
);

CREATE TABLE ${db.prefix}.INSPECTION_VALUE_TYPES
(
    INSPECTION_VALUE_TYPE_ID VARCHAR,
    INSPECTION_VALUE_TYPE_VALUE VARCHAR NOT NULL,
    PRIMARY KEY (INSPECTION_VALUE_TYPE_ID)
);

CREATE TABLE ${db.prefix}.VEHICLE_INSPECTION
(
    VEHICLE_INSPECTION_ID UUID DEFAULT uuid_generate_v4 () NOT NULL,
    VEHICLE_ID UUID NOT NULL,
    EXTERNAL_INSPECTION_VALUE_TYPE_ID VARCHAR NULL,
    INTERNAL_INSPECTION_VALUE_TYPE_ID VARCHAR NULL,
    CREATED_AT TIMESTAMP NULL,
    UPDATED_AT TIMESTAMP NULL,
    PRIMARY KEY (VEHICLE_INSPECTION_ID),
    CONSTRAINT fk_vehicle FOREIGN KEY (VEHICLE_ID) REFERENCES ${db.prefix}.VEHICLE(VEHICLE_ID),
    CONSTRAINT fk_external_inspection_value_type FOREIGN KEY (EXTERNAL_INSPECTION_VALUE_TYPE_ID)
        REFERENCES ${db.prefix}.INSPECTION_VALUE_TYPES(INSPECTION_VALUE_TYPE_ID),
    CONSTRAINT fk_internal_inspection_value_type FOREIGN KEY (INTERNAL_INSPECTION_VALUE_TYPE_ID)
        REFERENCES ${db.prefix}.INSPECTION_VALUE_TYPES(INSPECTION_VALUE_TYPE_ID)
);

CREATE TABLE ${db.prefix}.BOOKING
(
    BOOKING_NUMBER UUID DEFAULT uuid_generate_v4 () NOT NULL,
    CUSTOMER_ID UUID NULL,
    VEHICLE_ID UUID NULL,
    DATE_OF_SERVICE TIMESTAMP NULL,
    IS_SUBMITTED BOOLEAN NULL,
    BASE_COST NUMERIC(6,2) NULL,
    TIP NUMERIC(6,2) NULL,
    TOTAL_COST NUMERIC(6,2) NULL,
    NOTES VARCHAR NULL,
    SERVICE_PROVIDERS VARCHAR NULL,
    CREATED_AT TIMESTAMP NULL,
    UPDATED_AT TIMESTAMP NULL,
    PRIMARY KEY (BOOKING_NUMBER),
    CONSTRAINT fk_customer FOREIGN KEY (CUSTOMER_ID) REFERENCES ${db.prefix}.CUSTOMER(CUSTOMER_ID),
    CONSTRAINT fk_vehicle FOREIGN KEY (VEHICLE_ID) REFERENCES ${db.prefix}.VEHICLE(VEHICLE_ID)
);

INSERT INTO ${db.prefix}.SERVICE_TYPE (SERVICE_TYPE)
VALUES ('NA'), ('J.I.T WASH & WAX (EXTERIOR ONLY)'), ('J.I.T BASIC PACKAGE (INTERIOR/EXTERIOR)'),
       ('J.I.T PREMIUM PACKAGE (INTERIOR/EXTERIOR)'), ('J.I.T PLATINUM PACKAGE (INTERIOR/EXTERIOR)'),
       ('J.I.T INTERIOR DETAIL PACKAGE'), ('J.I.T PET PACKAGE (INTERIOR ONLY)'),
       ('J.I.T LIGHT SWIRL REMOVER + WAX PACKAGE (EXTERIOR)'), ('J.I.T Fleet Service'),
       ('J.I.T WASH & WAX (Speciality Paint)');

INSERT INTO ${db.prefix}.ADD_ONS_TYPE (ADD_ON_TYPE_ID, ADD_ON_TYPE)
VALUES ('WA', 'Wax Application (wash included)'), ('VD', 'Vinyl Dressing'), ('LC', 'Leather Conditioning'),
       ('FSC', 'Fabric Steam Cleaning'), ('UVPA', 'UV Protectant Application'), ('FMS', 'Fabric and Mat Shampooing'),
       ('PHR', 'Pet Hair Removal'), ('CBT', 'Clay Bar Treatment (wash included)'), ('DV', 'Deep Vacuuming'),
       ('GT','Glass Treatment'), ('TDA', 'Tire Dressing Application'), ('OED', 'Odor Elimination/Deodorizer (2hr Process)'),
       ('SR', 'Stain Removal'), ('HR', 'Headlight Restoration'), ('HWSR', 'Hard Water Spot Removal'),
       ('TMR', 'Trim & Molding Restoration'), ('CS', 'COVID-19 Sanitation'), ('CCS', 'Ceramic Coating Service');

INSERT INTO ${db.prefix}.INSPECTION_VALUE_TYPES (INSPECTION_VALUE_TYPE_ID, INSPECTION_VALUE_TYPE_VALUE)
VALUES ('S', 'Swirls'), ('WS', 'Water Spots'), ('OX', 'Oxidation'), ('RC', 'Rock Chip'), ('H', 'Holograms'),
       ('DS', 'Deep Scratch'), ('BD', 'Bird Dropping Etching'), ('SC', 'Surface Contaminants'), ('PT', 'Paint Transfer'),
       ('CF', 'Clearcoat Failure'), ('CH', 'Cloudy Headlights'), ('GS', 'Glass Scratch or Chip'), ('D', 'Dents'),
       ('WD', 'Wheel Damage'), ('LT', 'Loose Trim'), ('TS', 'Tree Sap'), ('RT', 'Road Tar'), ('RU', 'Rust');
