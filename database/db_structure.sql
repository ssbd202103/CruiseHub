create table language_types
(
    id                  bigint   not null,
    language_type_value varchar not null unique,
    CONSTRAINT language_versions_primary_key_constraint PRIMARY KEY (id)
);

create table alter_types
(
    id              bigint   not null,
    alter_type_name varchar not null,
    CONSTRAINT alter_type_primary_key_constraint PRIMARY KEY (id)
);

create table entity_details
(
    id                  bigint     not null,
    version             bigint     not null,
    creation_datetime   timestamp not null,
--     created_by_id       bigint     not null, -- FOREIGN KEY
    last_alter_datetime timestamp not null,
--     altered_by_id       bigint     not null, -- FOREIGN KEY
    alter_type_id       bigint     not null, -- FOREIGN KEY

    CONSTRAINT entity_details_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT entity_details_alter_type_id_fk_constraint FOREIGN KEY (alter_type_id) REFERENCES alter_types (id)

);

create table accounts
(
    id                                            bigint   not null,

    first_name                                    varchar,
    second_name                                   varchar,

    login                                         varchar not null,
    email                                         varchar not null,
    password_hash                                 varchar not null, -- hash sha256

    confirmed                                     boolean not null,
    active                                        boolean not null,

    last_incorrect_authentication_datetime        timestamp,
    last_incorrect_authentication_logical_address varchar,


    last_correct_authentication_datetime          timestamp,
    last_correct_authentication_logical_address   varchar,

    entity_details_id                             bigint   not null, -- FOREIGN KEY
    language_type_id                              bigint   not null, -- FOREIGN KEY

    CONSTRAINT accounts_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT accounts_entity_details_id_fk_constraint FOREIGN KEY (entity_details_id) REFERENCES entity_details (id),
    CONSTRAINT accounts_language_type_id_fk_constraint FOREIGN KEY (language_type_id) REFERENCES language_types (id)
);

create table access_levels
(
    id           bigint   not null,
    access_level varchar not null,
    account_id   bigint   not null,
    active       boolean not null,

    CONSTRAINT access_levels_id_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT access_levels_account_id_fk_constraint FOREIGN KEY (account_id) REFERENCES accounts (id)
);

create table administrators
(
    id                bigint not null, -- should be foreign for access_level
    entity_details_id bigint not null, -- FOREIGN KEY

    CONSTRAINT administrators_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT administrators_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id),
    CONSTRAINT administrators_entity_details_id_fk_constraint FOREIGN KEY (entity_details_id) REFERENCES entity_details (id)
);

create table business_workers
(
    id                bigint not null,
    entity_details_id bigint not null, -- FOREIGN KEY
    phone_number      varchar,

    CONSTRAINT business_workers_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT business_workers_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id),
    CONSTRAINT business_workers_entity_details_id_fk_constraint FOREIGN KEY (entity_details_id) REFERENCES entity_details (id)
);

create table addresses
(
    id                bigint   not null,
    house_number      bigint   not null,
    street            varchar not null,
    post_code         varchar not null,
    city              varchar not null,
    country           varchar not null,
    entity_details_id bigint   not null, -- FOREIGN KEY

    CONSTRAINT address_entity_details_id_fk_constraint FOREIGN KEY (entity_details_id) REFERENCES entity_details (id),
    CONSTRAINT address_primary_key_constraint PRIMARY KEY (id)
);

create table clients
(
    id                bigint not null,
    entity_details_id bigint not null, -- FOREIGN KEY
    phone_number      varchar,
    home_address_id   bigint,          -- FOREIGN KEY

    CONSTRAINT clients_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT clients_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id),
    CONSTRAINT clients_entity_details_id_fk_constraint FOREIGN KEY (entity_details_id) REFERENCES entity_details (id),
    CONSTRAINT clients_home_address_id_fk_constraint FOREIGN KEY (home_address_id) REFERENCES addresses (id)
);

create table moderators
(
    id                bigint not null,
    entity_details_id bigint not null, -- FOREIGN KEY
    CONSTRAINT moderators_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT moderators_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id),
    CONSTRAINT moderators_entity_details_id_fk_constraint FOREIGN KEY (entity_details_id) REFERENCES entity_details (id)
);


-- drop table moderators,clients, addresses, business_workers, administrators, access_levels, accounts, entity_details,alter_types, language_types;


CREATE VIEW glassfish_auth_view AS
SELECT accounts.login, accounts.password_hash, access_levels.access_level
FROM accounts
         JOIN access_levels ON accounts.id = access_levels.account_id
WHERE accounts.confirmed
  AND accounts.active
  AND access_levels.active = true;

select * from glassfish_auth_view;

-- Table owner --
ALTER TABLE language_types OWNER TO ssbd03admin;
ALTER TABLE alter_types OWNER TO ssbd03admin;
ALTER TABLE entity_details OWNER TO ssbd03admin;
ALTER TABLE accounts OWNER TO ssbd03admin;
ALTER TABLE access_levels OWNER TO ssbd03admin;
ALTER TABLE administrators OWNER TO ssbd03admin;
ALTER TABLE business_workers OWNER TO ssbd03admin;
ALTER TABLE addresses OWNER TO ssbd03admin;
ALTER TABLE clients OWNER TO ssbd03admin;
ALTER TABLE moderators OWNER TO ssbd03admin;
ALTER VIEW glassfish_auth_view OWNER TO ssbd03admin;

-- Table permissions --
GRANT SELECT ON glassfish_auth_view TO ssbd03glassfish;
