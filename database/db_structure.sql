create table language_type
 (
    id                  bigint      not null,
    language_type       varchar     not null,

    CONSTRAINT language_type_primary_key_constraint PRIMARY KEY (id)
 );

create table alter_type
 (
    id                  bigint      not null,
    alter_type          varchar     not null,

    CONSTRAINT alter_type_primary_key_constraint PRIMARY KEY (id)
 );

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
    amount_of_incorrect_authentications           integer default 0,

    language_type_id                              bigint   not null, -- FOREIGN KEY

    creation_date_time                            timestamp not null,
    last_alter_date_time                          timestamp not null,
    created_by_id                                 bigint    not null, -- FOREIGN KEY
    altered_by_id                                 bigint    not null, -- FOREIGN KEY
    alter_type                                    bigint    not null, -- FOREIGN KEY
    version                                       bigint    not null,

    CONSTRAINT accounts_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT accounts_language_type_id_fk_constraint FOREIGN KEY (language_type_id) REFERENCES language_type (id),
    CONSTRAINT accounts_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT accounts_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id),
    CONSTRAINT accounts_alter_type_fk_constraint FOREIGN KEY (alter_type) REFERENCES alter_type (id),
    CONSTRAINT accounts_amount_of_incorrect_authentications_check CHECK (amount_of_incorrect_authentications >= 0)
);

create sequence account_id_seq
    START WITH 1
    INCREMENT BY 1;

create table access_levels
(
    id                   bigint    not null,
    access_level         varchar   not null,
    account_id           bigint    not null, -- FOREIGN KEY
    enabled              boolean   not null,

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           bigint    not null, -- FOREIGN KEY
    version              bigint    not null,

    CONSTRAINT access_levels_id_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT access_levels_account_id_fk_constraint FOREIGN KEY (account_id) REFERENCES accounts (id),
    CONSTRAINT access_levels_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT access_levels_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id),
    CONSTRAINT access_levels_alter_type_fk_constraint FOREIGN KEY (alter_type) REFERENCES alter_type (id)
);

create sequence access_level_id_seq
    START WITH 1
    INCREMENT BY 1;

create table administrators
(
    id bigint not null, -- FOREIGN KEY

    CONSTRAINT administrators_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT administrators_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id)
);

create table business_workers
(
    id           bigint  not null, -- FOREIGN KEY
    phone_number varchar not null,

    CONSTRAINT business_workers_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT business_workers_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id)
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
    alter_type           bigint    not null, -- FOREIGN KEY
    version              bigint    not null,

    CONSTRAINT address_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT address_alter_type_fk_constraint FOREIGN KEY (alter_type) REFERENCES alter_type (id),
    CONSTRAINT addresses_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT addresses_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create sequence address_id_seq
    START WITH 1
    INCREMENT BY 1;

create table clients
(
    id              bigint  not null, -- FOREIGN KEY
    phone_number    varchar not null,
    home_address_id bigint  not null, -- FOREIGN KEY

    CONSTRAINT clients_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT clients_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id),
    CONSTRAINT clients_home_address_id_fk_constraint FOREIGN KEY (home_address_id) REFERENCES addresses (id)
);

create table moderators
(
    id bigint not null, -- FOREIGN KEY

    CONSTRAINT moderators_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT moderators_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id)
);
------------------------mow module--------------------------
create table cruise_addresses
(
    id                   bigint    not null,
    street               varchar   not null,
    street_number        bigint,
    harbor_name          varchar,
    city                 varchar   not null,
    country              varchar   not null,

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           bigint    not null, -- FOREIGN KEY

    version              bigint    not null,

    CONSTRAINT cruise_addresses_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT cruise_alter_type_fk_constraint FOREIGN KEY (alter_type) REFERENCES alter_type (id),
    CONSTRAINT cruise_addresses_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT cruise_addresses_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table cruise_pictures
 (
    id                   bigint    not null,
    img_name             varchar,
    img                  bytea,                 --nullable only for implementation time.

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           bigint    not null, -- FOREIGN KEY

    version              bigint    not null,

    CONSTRAINT cruise_pictures_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT cruise_pictures_alter_type_fk_constraint FOREIGN KEY (alter_type) REFERENCES alter_type (id),
    CONSTRAINT cruise_pictures_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT cruise_pictures_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
 );

create table commercial_type
 (
    id                   bigint    not null,
    commercial_type      varchar   not null,

    CONSTRAINT commercial_type_primary_key_constraint PRIMARY KEY (id)
 );

create table companies
(
    id                  bigint      not null,
    name                varchar     not null,
    address_id          bigint      not null,  -- FOREIGN KEY
    phone_number        varchar     not null,
    nip                 bigint      not null,

    creation_date_time   timestamp  not null,
    last_alter_date_time timestamp  not null,
    created_by_id        bigint     not null, -- FOREIGN KEY
    altered_by_id        bigint     not null, -- FOREIGN KEY
    alter_type           bigint     not null, -- FOREIGN KEY

    version              bigint     not null,

    CONSTRAINT companies_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT companies_address_id_fk_constraint FOREIGN KEY (address_id) REFERENCES addresses (id),
    CONSTRAINT compamies_alter_type_fk_constraint FOREIGN KEY (alter_type) REFERENCES alter_type (id),
    CONSTRAINT companies_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT companies_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table cruises_groups
(
    id                  bigint      not null,
    company_id          bigint      not null,  -- FOREIGN KEY
    name                varchar     not null,
    number_of_seats     bigint      not null,
    price               numeric     not null,
    start_address_id    bigint      not null,  -- FOREIGN KEY
    average_rating      numeric,

    creation_date_time   timestamp  not null,
    last_alter_date_time timestamp  not null,
    created_by_id        bigint     not null, -- FOREIGN KEY
    altered_by_id        bigint     not null, -- FOREIGN KEY
    alter_type           bigint     not null, -- FOREIGN KEY

    version              bigint     not null,

    CONSTRAINT cruises_groups_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT cruises_groups_companies_fk FOREIGN KEY (company_id) REFERENCES companies (id),
    CONSTRAINT cruises_groups_start_address_id_fk_constraint FOREIGN KEY (start_address_id) REFERENCES cruise_addresses (id),
    CONSTRAINT cruises_groups_alter_type_fk_constraint FOREIGN KEY (alter_type) REFERENCES alter_type (id),
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
    cruises_groups_id   bigint      not null,  -- FOREIGN KEY
    available           boolean     not null,

    creation_date_time   timestamp  not null,
    last_alter_date_time timestamp  not null,
    created_by_id        bigint     not null, -- FOREIGN KEY
    altered_by_id        bigint     not null, -- FOREIGN KEY
    alter_type           bigint     not null, -- FOREIGN KEY

    version              bigint     not null,

    CONSTRAINT cruises_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT cruises_cruises_groups_fk FOREIGN KEY (cruises_groups_id) REFERENCES cruises_groups (id),
    CONSTRAINT cruises_alter_type_fk_constraint FOREIGN KEY (alter_type) REFERENCES alter_type (id),
    CONSTRAINT cruises_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT cruises_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table attractions
(
    id                   bigint     not null,
    name                 varchar    not null,
    description          varchar,
    price                numeric    not null,
    number_of_seats      bigint     not null,
    available            boolean    not null,
    cruise_id            bigint,              -- FOREIGN KEY

    creation_date_time   timestamp  not null,
    last_alter_date_time timestamp  not null,
    created_by_id        bigint     not null, -- FOREIGN KEY
    altered_by_id        bigint     not null, -- FOREIGN KEY
    alter_type           bigint     not null, -- FOREIGN KEY

    version              bigint     not null,

    CONSTRAINT attractions_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT attractions_cruise_id_fk_constraint FOREIGN KEY (cruise_id) REFERENCES cruises (id),
    CONSTRAINT attractions_alter_type_fk_constraint FOREIGN KEY (alter_type) REFERENCES alter_type (id),
    CONSTRAINT attractions_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT attractions_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table reservations
(
    id                   bigint    not null,
    client_id            bigint    not null, --FOREIGN KEY
    number_of_seats      bigint    not null,
    cruise_id            bigint    not null, --FOREIGN KEY

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           bigint    not null, -- FOREIGN KEY

    version              bigint    not null,

    CONSTRAINT reservations_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT reservations_client_id_fk_constraint FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT reservations_cruise_id_fk_constraint FOREIGN KEY (cruise_id) REFERENCES cruises (id),
    CONSTRAINT reservations_alter_type_fk_constraint FOREIGN KEY (alter_type) REFERENCES alter_type (id),
    CONSTRAINT reservations_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT reservations_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table ratings
(
    id                   bigint     not null,
    account_id           bigint     not null, -- FOREIGN KEY
    cruise_id            bigint     not null, -- FOREIGN KEY
    rating               numeric    not null,

    creation_date_time   timestamp  not null,
    last_alter_date_time timestamp  not null,
    created_by_id        bigint     not null, -- FOREIGN KEY
    altered_by_id        bigint     not null, -- FOREIGN KEY
    alter_type           bigint     not null, -- FOREIGN KEY

    version              bigint     not null,

    CONSTRAINT ratings_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT ratings_account_id_fk_constraint FOREIGN KEY (account_id) REFERENCES accounts (id),
    CONSTRAINT ratings_cruise_id_fk_constraint FOREIGN KEY (cruise_id) REFERENCES cruises (id),
    CONSTRAINT ratings_alter_type_fk_constraint FOREIGN KEY (alter_type) REFERENCES alter_type (id),
    CONSTRAINT ratings_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT ratings_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table comments
(
    id                  bigint      not null,
    account_id          bigint      not null, -- FOREIGN KEY
    cruise_id           bigint      not null, -- FOREIGN KEY
    comment             varchar     not null,

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           bigint   not null, -- FOREIGN KEY

    version              bigint    not null,

    CONSTRAINT comments_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT comments_account_id_fk_constraint FOREIGN KEY (account_id) REFERENCES accounts (id),
    CONSTRAINT comments_cruise_id_fk_constraint FOREIGN KEY (cruise_id) REFERENCES cruises (id),
    CONSTRAINT comments_alter_type_fk_constraint FOREIGN KEY (alter_type) REFERENCES alter_type (id),
    CONSTRAINT comments_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT comments_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table commercials
(
    id                   bigint     not null,
    commercial_type_id   bigint     not null, -- FOREIGN KEY
    cruises_group_id     bigint     not null, -- FOREIGN KEY
    start_date           timestamp  not null,
    end_date             timestamp  not null,

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           bigint    not null, -- FOREIGN KEY

    version              bigint    not null,

    CONSTRAINT commercials_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT commercials_cruises_group_id_fk_constraint FOREIGN KEY (cruises_group_id) REFERENCES cruises_groups (id),
    CONSTRAINT commercials_commercial_type_id_fk_constraint FOREIGN KEY (commercial_type_id) REFERENCES commercial_type  (id),
    CONSTRAINT commercials_alter_type_fk_constraint FOREIGN KEY (alter_type) REFERENCES alter_type (id),
    CONSTRAINT commercials_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT commercials_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create table company_workers
(
    id                   bigint     not null,
    companies_id         bigint     not null,           -- FOREIGN KEY
    business_workers_id  bigint     not null    unique, -- FOREIGN KEY

    CONSTRAINT company_workers_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT company_workers_companies_id_business_workers_id_unique UNIQUE (companies_id, business_workers_id),
    CONSTRAINT company_workers_companies_id_fk_constraint FOREIGN KEY (companies_id) REFERENCES companies (id),
    CONSTRAINT company_workers_business_workers_id_fk_constraint FOREIGN KEY (business_workers_id) REFERENCES  business_workers (id)
);

create table cruises_groups_pictures
(
    id                   bigint     not null,
    cruises_groups_id    bigint     not null, -- FOREIGN KEY
    cruise_pictures_id   bigint     not null, -- FOREIGN KEY

    CONSTRAINT cruises_groups_pictures_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT cruises_groups_pictures_groups_id_pictures_id_unique UNIQUE (cruises_groups_id, cruise_pictures_id),
    CONSTRAINT cruises_groups_pictures_cruises_groups_id_fk_constraint FOREIGN KEY (cruises_groups_id) REFERENCES cruises_groups (id),
    CONSTRAINT cruises_groups_pictures_cruise_pictures_id_fk_constraint FOREIGN KEY (cruise_pictures_id) REFERENCES  cruise_pictures (id)
);

create table reservations_attractions
(
    id                   bigint     not null,
    reservations_id      bigint     not null,--FOREIGN KEY
    attractions_id       bigint     not null,--FOREIGN KEY

    CONSTRAINT reservations_attractions_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT reservations_attractions_reservations_id_attractions_id_unique UNIQUE (reservations_id, attractions_id),
    CONSTRAINT reservations_attractions_reservations_id_fk_constraint FOREIGN KEY (reservations_id) REFERENCES reservations (id),
    CONSTRAINT reservations_attractions_attractions_id_fk_constraint FOREIGN KEY (attractions_id) REFERENCES attractions (id)
);

CREATE VIEW glassfish_auth_view AS
SELECT accounts.login, accounts.password_hash, access_levels.access_level
FROM accounts
         JOIN access_levels ON accounts.id = access_levels.account_id
WHERE accounts.confirmed
  AND accounts.active
  AND access_levels.enabled;


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
ALTER TABLE commercials
    OWNER to ssbd03admin;
ALTER TABLE cruise_addresses
    OWNER to ssbd03admin;
ALTER TABLE cruise_pictures
    OWNER to ssbd03admin;
ALTER TABLE commercial_type
    OWNER to ssbd03admin;
ALTER TABLE language_type
    OWNER to ssbd03admin;
ALTER TABLE alter_type
    OWNER to ssbd03admin;
ALTER TABLE company_workers
    OWNER to ssbd03admin;
ALTER TABLE cruises_groups_pictures
    OWNER to ssbd03admin;
ALTER TABLE reservations_attractions
    OWNER to ssbd03admin;
ALTER TABLE cruises_groups
    OWNER to ssbd03admin;
ALTER TABLE companies
    OWNER to ssbd03admin;
ALTER TABLE cruises
    OWNER to ssbd03admin;
ALTER VIEW glassfish_auth_view OWNER TO ssbd03admin;


-- Table permissions --
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
    ON SEQUENCE access_level_id_seq TO ssbd03mok;

GRANT SELECT, UPDATE
    ON SEQUENCE address_id_seq TO ssbd03mok;


-- Table permissions for MOW --
GRANT SELECT
    ON alter_type TO ssbd03mow

GRANT SELECT, INSERT, UPDATE, DELETE
    ON attractions TO ssbd03mow

GRANT SELECT, INSERT, UPDATE, DELETE
    ON comments TO ssbd03mow

GRANT SELECT
    ON commercial_type TO ssbd03mow

GRANT SELECT, INSERT, UPDATE, DELETE
    ON commercials TO ssbd03mow

GRANT SELECT, INSERT, UPDATE, DELETE
    ON companies TO ssbd03mow

GRANT SELECT, INSERT, UPDATE, DELETE
    ON company_workers TO ssbd03mow

GRANT SELECT, INSERT, DELETE
    ON cruise_addresses TO ssbd03mow

GRANT SELECT, INSERT, DELETE
    ON cruise_pictures TO ssbd03mow

GRANT SELECT, INSERT, UPDATE, DELETE
    ON cruises TO ssbd03mow

GRANT SELECT, INSERT, UPDATE, DELETE
    ON cruises_groups TO ssbd03mow

GRANT SELECT, INSERT, DELETE
    ON cruises_groups_pictures TO ssbd03mow

GRANT SELECT
    ON language_type TO ssbd03mow

GRANT SELECT, INSERT, UPDATE, DELETE
    ON ratings TO ssbd03mow

GRANT SELECT, INSERT, UPDATE, DELETE
    ON reservations TO ssbd03mow

GRANT SELECT, INSERT, UPDATE, DELETE
    ON reservations_attractions TO ssbd03mow

GRANT SELECT ON glassfish_auth_view TO ssbd03glassfish;
