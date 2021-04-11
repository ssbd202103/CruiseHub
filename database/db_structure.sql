create table accounts
(
    id                                            bigint    not null,
    first_name                                    varchar,
    second_name                                   varchar,
    login                                         varchar   not null,
    email                                         varchar   not null,
    password_hash                                 varchar   not null, -- hash sha256
    confirmed                                     boolean   not null,
    active                                        boolean   not null,
    last_incorrect_authentication_date_time       timestamp,
    last_incorrect_authentication_logical_address varchar,

    last_correct_authentication_date_time         timestamp,
    last_correct_authentication_logical_address   varchar,

    language_type                                 varchar   not null, -- FOREIGN KEY

    creation_date_time                            timestamp not null,
    last_alter_date_time                          timestamp not null,
    created_by_id                                 bigint    not null, -- FOREIGN KEY
    altered_by_id                                 bigint    not null, -- FOREIGN KEY
    alter_type                                    varchar   not null,

    version                                       bigint    not null,

    CONSTRAINT accounts_primary_key_constraint PRIMARY KEY (id),

    CONSTRAINT accounts_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT accounts_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create sequence account_id_seq
    START WITH 1
    INCREMENT BY 1;

create table access_levels
(
    id           bigint  not null,
    access_level varchar not null,
    account_id   bigint  not null,
    active       boolean not null,

    CONSTRAINT access_levels_id_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT access_levels_account_id_fk_constraint FOREIGN KEY (account_id) REFERENCES accounts (id)
);

create sequence accesslevel_id_seq
    START WITH 1
    INCREMENT BY 1;

create table administrators
(
    id                   bigint    not null, -- should be foreign for access_level

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null,

    version              bigint    not null,

    CONSTRAINT administrators_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT administrators_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id),

    CONSTRAINT administrators_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT administrators_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table business_workers
(
    id                   bigint    not null,
    phone_number         varchar,

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null,

    version              bigint    not null,

    CONSTRAINT business_workers_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT business_workers_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id),

    CONSTRAINT business_workers_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT business_workers_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table addresses
(
    id                   bigint    not null,
    house_number         bigint    not null,
    street               varchar   not null,
    postal_code          varchar   not null,
    city                 varchar   not null,
    country              varchar   not null,

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null,

    version              bigint    not null,

    CONSTRAINT address_primary_key_constraint PRIMARY KEY (id),

    CONSTRAINT addresses_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT addresses_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create sequence address_id_seq
    START WITH 1
    INCREMENT BY 1;

create table clients
(
    id                   bigint    not null,
    phone_number         varchar,
    home_address_id      bigint,             -- FOREIGN KEY

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null,

    version              bigint    not null,

    CONSTRAINT clients_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT clients_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id),
    CONSTRAINT clients_home_address_id_fk_constraint FOREIGN KEY (home_address_id) REFERENCES addresses (id),

    CONSTRAINT clients_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT clients_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table moderators
(
    id                   bigint    not null,

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null,

    version              bigint    not null,

    CONSTRAINT moderators_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT moderators_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id),

    CONSTRAINT moderators_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT moderators_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);
------------------------mow modul--------------------------
create table companies
(
    id                  bigint      not null,
    name                varchar     not null,
    address             varchar     not null,
    phone_number        varchar     not null,
    nip                 bigint      not null,
    owner               varchar     not null,
    worker_id           bigint,                -- FOREIGN KEY

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null,

    version              bigint    not null,

    CONSTRAINT companies_id_pk_constraint PRIMARY KEY (id),

    CONSTRAINT companies_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT companies_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table attractions
(
    id                   bigint     not null,
    name                 varchar    not null,
    description          varchar,
    price                numeric,
    number_of_seats      bigint     not null,

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null,

    version              bigint    not null,

    CONSTRAINT attractions_id_pk_constraint PRIMARY KEY (id),

    CONSTRAINT attractions_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT attractions_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table cruises_groups
(
    id                  bigint      not null,
    company_id          bigint      not null,  -- FOREIGN KEY
    name                varchar     not null,
    number_of_seats     bigint      not null,
    price               numeric     not null,

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null,

    version              bigint    not null,

	CONSTRAINT cruises_groups_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT cruises_groups_companies_fk FOREIGN KEY (company_id) REFERENCES companies (id),

    CONSTRAINT cruises_groups_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT cruises_groups_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table cruises
(
    id                  bigint      not null,
    start_date          timestamp   not null,
    end_date            timestamp,
    active              boolean     not null,
    description         varchar,
    start_place         varchar,
    cruises_groups_id   bigint      not null,  -- FOREIGN KEY
    attractions         bigint,                -- FOREIGN KEY

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null,

    version              bigint    not null,

	CONSTRAINT cruises_id_pk_constraint PRIMARY KEY (id),
	CONSTRAINT cruises_attractions_fk FOREIGN KEY (attractions) REFERENCES attractions (id),
    CONSTRAINT cruises_cruises_groups_fk FOREIGN KEY (cruises_groups_id) REFERENCES cruises_groups (id),

    CONSTRAINT cruises_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT cruises_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table reservations
(
    id                   bigint    not null,
    client_id            bigint    not null, --FOREIGN KEY
    number_of_seats      bigint    not null,
    attractions_id       bigint    not null, --FOREIGN KEY
    cruise_id            bigint    not null, --FOREIGN KEY

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null,

    version              bigint    not null,

    CONSTRAINT reservations_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT reservations_client_id_fk_constraint FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT reservations_cruise_id_fk_constraint FOREIGN KEY (cruise_id) REFERENCES cruises (id),
    CONSTRAINT reservations_attractions_id_fk_constraint FOREIGN KEY (attractions_id) REFERENCES attractions (id),

    CONSTRAINT reservations_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT reservations_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table ratings
(
    id                  bigint      not null,
    account_id          bigint      not null,
    cruise_id           bigint      not null,
    rating              bigint      not null,

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null,

    version              bigint    not null,

    CONSTRAINT ratings_primary_key_constraint PRIMARY KEY (id),

    CONSTRAINT ratings_account_id_fk_constraint FOREIGN KEY (account_id) REFERENCES accounts (id),
    CONSTRAINT ratings_cruise_id_fk_constraint FOREIGN KEY (cruise_id) REFERENCES cruises (id),

    CONSTRAINT ratings_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT ratings_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table comments
(
    id                  bigint      not null,
    account_id          bigint      not null,
    cruise_id           bigint      not null,
    comment             varchar     not null,

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null,

    version              bigint    not null,

    CONSTRAINT comments_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT comments_account_id_fk_constraint FOREIGN KEY (account_id) REFERENCES accounts (id),
    CONSTRAINT comments_cruise_id_fk_constraint FOREIGN KEY (cruise_id) REFERENCES cruises (id),

    CONSTRAINT comments_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT comments_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);


CREATE TABLE positioning
(
    id                   bigint NOT NULL,
    type                 boolean NOT NULL,
    cruises_group_id     bigint NOT NULL,
    end_date             timestamp NOT NULL,

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null,

    version              bigint    not null,

    CONSTRAINT positioning_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT positioning_cruises_group_id_fk_constraint FOREIGN KEY (cruises_group_id) REFERENCES cruises_groups (id),

    CONSTRAINT positioning_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT positioning_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);


CREATE VIEW glassfish_auth_view AS
SELECT accounts.login, accounts.password_hash, access_levels.access_level
FROM accounts
         JOIN access_levels ON accounts.id = access_levels.account_id
WHERE accounts.confirmed
  AND accounts.active
  AND access_levels.active = true;


-- Table owner --
ALTER TABLE accounts
    OWNER TO ssbd03admin;
ALTER TABLE access_levels
    OWNER TO ssbd03admin;
ALTER TABLE administrators
    OWNER TO ssbd03admin;
ALTER TABLE business_workers
    OWNER TO ssbd03admin;
ALTER TABLE addresses
    OWNER TO ssbd03admin;
ALTER TABLE clients
    OWNER TO ssbd03admin;
ALTER TABLE moderators
    OWNER TO ssbd03admin;
AlTER TABLE reservations
    OWNER TO ssbd03admin;
AlTER TABLE attractions
    OWNER TO ssbd03admin;
ALTER TABLE ratings
    OWNER to ssbd03admin;
ALTER TABLE comments
    OWNER to ssbd03admin;
ALTER TABLE positionings
    OWNER to ssbd03admin;
ALTER VIEW glassfish_auth_view OWNER TO ssbd03admin;


-- Table permissions --
ALTER TABLE cruises_groups
    OWNER to ssbd03admin;
ALTER TABLE companies
    OWNER to ssbd03admin;
ALTER TABLE cruises
    OWNER to ssbd03admin;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON access_levels TO ssbd03mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON accounts TO ssbd03mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON addresses TO ssbd03mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON administrators TO ssbd03mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON business_workers TO ssbd03mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON clients TO ssbd03mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON moderators TO ssbd03mok;

GRANT SELECT, UPDATE
    ON SEQUENCE account_id_seq TO ssbd03mok;

GRANT SELECT, UPDATE
    ON SEQUENCE accesslevel_id_seq TO ssbd03mok;

GRANT SELECT, UPDATE
    ON SEQUENCE address_id_seq TO ssbd03mok;

GRANT SELECT ON glassfish_auth_view TO ssbd03glassfish;
