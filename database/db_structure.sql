create table accounts
(
    id                                            float   not null,

    first_name                                    varchar,
    second_name                                   varchar,

    login                                         varchar not null,
    email                                         varchar not null,
    password_hash                                 varchar not null, -- hash sha256

    confirmed                                     boolean not null,
    active                                        boolean not null,

    last_incorrect_authentication_datetime        timestamp,
    last_incorrect_authentication_logical_address varchar,
    language_type                                 float   not null,


    last_correct_authentication_datetime          timestamp,
    last_correct_authentication_logical_address   varchar,

    entity_details_id                             float   not null, -- foreign key

    CONSTRAINT accounts_primary_key_constraint PRIMARY KEY (id)
);



create table language_versions
(
    id                     float   not null,
    language_version_value varchar not null,
    CONSTRAINT language_versions_primary_key_constraint PRIMARY KEY (id)
);


create table entity_details
(
    id                  float     not null,
    version             float     not null,
    creation_datetime   timestamp not null,
    created_by_id       float     not null, -- foreign key
    last_alter_datetime timestamp not null,
    altered_by_id       float     not null, -- foreign key
    alter_type_id       float     not null  -- foreign key
);

create table alter_types
(
    id               float primary key not null,
    alter_type_value varchar           not null,
    CONSTRAINT alter_types_primary_key_constraint PRIMARY KEY (id)
);



create table access_levels
(
    id           float   not null,
    access_level varchar not null,
    account_id   float   not null,

    CONSTRAINT access_levels_id_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT access_levels_account_id_foreign_key_constraint PRIMARY KEY (account_id)
);

create table administrators
(
    id                  float     not null, -- should be foreign for access_level
    last_alter_datetime timestamp not null,
    entity_details_id   float     not null, -- foreign key
);

create table business_workers
(
    id                float not null,
    entity_details_id float not null, -- foreign key
    phone_number      varchar
);

create table clients
(
    id                float not null,
    entity_details_id float not null, -- foreign key
    phone_number      varchar,
    home_address_id   float           -- foreign key

);


create table address
(
    id           float   not null,
    house_number float   not null,
    street       varchar not null,
    post_code    varchar not null,
    city         varchar not null,
    country      varchar not null,
    CONSTRAINT address_primary_key_constraint PRIMARY KEY (id)
);

create table moderators
(
    id                float not null,
    entity_details_id float not null, -- foreign key

);
