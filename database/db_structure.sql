create table language_types
(
    id   bigint  not null,
    name varchar not null,

    CONSTRAINT language_types_primary_key_constraint PRIMARY KEY (id)
);

create table alter_types
(
    id   bigint  not null,
    name varchar not null,

    CONSTRAINT alter_types_primary_key_constraint PRIMARY KEY (id)
);

create table accounts
(
    id                                            bigint                              not null,
    first_name                                    varchar(25)                         not null,
    second_name                                   varchar(25)                         not null,
    login                                         varchar(25)                         not null,
    email                                         varchar(64)                         not null,
    password_hash                                 varchar(64)                         not null, -- hash sha256
    confirmed                                     boolean   default false             not null,
    active                                        boolean   default false             not null,

    last_incorrect_authentication_date_time       timestamp,
    last_incorrect_authentication_logical_address varchar,
    last_correct_authentication_date_time         timestamp,
    last_correct_authentication_logical_address   varchar,
    number_of_authentication_failures             integer   default 0,

    language_type_id                              bigint    default -1                not null, -- FOREIGN KEY

    creation_date_time                            timestamp default CURRENT_TIMESTAMP not null,
    last_alter_date_time                          timestamp                           not null,
    created_by_id                                 bigint                              not null, -- FOREIGN KEY
    altered_by_id                                 bigint                              not null, -- FOREIGN KEY
    alter_type_id                                 bigint                              not null, -- FOREIGN KEY
    version                                       bigint check (version >= 0)         not null,

    CONSTRAINT accounts_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT accounts_language_type_id_fk_constraint FOREIGN KEY (language_type_id) REFERENCES language_types (id),
    CONSTRAINT accounts_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT accounts_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id),
    CONSTRAINT accounts_alter_type_id_fk_constraint FOREIGN KEY (alter_type_id) REFERENCES alter_types (id),
    CONSTRAINT accounts_number_of_authentication_failures_check CHECK (number_of_authentication_failures >= 0)
);

create sequence account_id_seq
    START WITH 1
    INCREMENT BY 1;

create table access_levels
(
    id                   bigint                              not null,
    access_level         varchar(25)                         not null,
    account_id           bigint                              not null, -- FOREIGN KEY
    enabled              boolean   default false             not null,

    creation_date_time   timestamp default CURRENT_TIMESTAMP not null,
    last_alter_date_time timestamp                           not null,
    created_by_id        bigint                              not null, -- FOREIGN KEY
    altered_by_id        bigint                              not null, -- FOREIGN KEY
    alter_type_id        bigint                              not null, -- FOREIGN KEY
    version              bigint check (version >= 0)         not null,

    CONSTRAINT access_levels_id_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT access_levels_account_id_fk_constraint FOREIGN KEY (account_id) REFERENCES accounts (id),
    CONSTRAINT access_levels_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT access_levels_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id),
    CONSTRAINT access_levels_alter_type_id_fk_constraint FOREIGN KEY (alter_type_id) REFERENCES alter_types (id)
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

create table addresses
(
    id                   bigint                                                       not null,
    house_number         bigint check ((house_number >= 0) AND (house_number <= 999)) not null,
    street               varchar(25)                                                  not null,
    postal_code          varchar(6)                                                   not null,
    city                 varchar(25)                                                  not null,
    country              varchar(25)                                                  not null,

    creation_date_time   timestamp default CURRENT_TIMESTAMP                          not null,
    last_alter_date_time timestamp                                                    not null,
    created_by_id        bigint                                                       not null, -- FOREIGN KEY
    altered_by_id        bigint                                                       not null, -- FOREIGN KEY
    alter_type_id        bigint                                                       not null, -- FOREIGN KEY
    version              bigint check (version >= 0)                                  not null,

    CONSTRAINT address_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT address_alter_type_id_fk_constraint FOREIGN KEY (alter_type_id) REFERENCES alter_types (id),
    CONSTRAINT addresses_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT addresses_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create sequence address_id_seq
    START WITH 1
    INCREMENT BY 1;

create table clients
(
    id              bigint      not null, -- FOREIGN KEY
    phone_number    varchar(12) not null,
    home_address_id bigint      not null, -- FOREIGN KEY

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

create table companies
(
    id                   bigint                               not null,
    name                 varchar(25)                          not null,
    address_id           bigint                               not null, -- FOREIGN KEY
    phone_number         varchar(12)                          not null,
    nip                  numeric(10) check (nip >= 999999999) not null,

    creation_date_time   timestamp default CURRENT_TIMESTAMP  not null,
    last_alter_date_time timestamp                            not null,
    created_by_id        bigint                               not null, -- FOREIGN KEY
    altered_by_id        bigint                               not null, -- FOREIGN KEY
    alter_type_id        bigint                               not null, -- FOREIGN KEY
    version              bigint check (version >= 0)          not null,

    CONSTRAINT companies_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT companies_address_id_fk_constraint FOREIGN KEY (address_id) REFERENCES addresses (id),
    CONSTRAINT companies_alter_type_id_fk_constraint FOREIGN KEY (alter_type_id) REFERENCES alter_types (id),
    CONSTRAINT companies_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT companies_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id),
    CONSTRAINT companies_name_unique_constraint UNIQUE (name)

);

create sequence companies_id_seq
    START WITH 1
    INCREMENT BY 1;

create table business_workers
(
    id                           bigint      not null, -- FOREIGN KEY
    phone_number                 varchar(12) not null,
    company_id                   bigint      not null,
    confirmed_by_business_worker bool        not null,

    CONSTRAINT business_workers_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT business_workers_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id),
    CONSTRAINT business_workers_company_id_fk_constraint FOREIGN KEY (company_id) REFERENCES companies (id)
);



create table cruise_addresses
(
    id                   bigint                              not null,
    street               varchar(25)                         not null,
    street_number        bigint check ((street_number >= 0) AND (street_number <= 999)),
    harbor_name          varchar(25)                         not null,
    city_name            varchar(25)                         not null,
    country_name         varchar(25)                         not null,

    creation_date_time   timestamp default CURRENT_TIMESTAMP not null,
    last_alter_date_time timestamp                           not null,
    created_by_id        bigint                              not null, -- FOREIGN KEY
    altered_by_id        bigint                              not null, -- FOREIGN KEY
    alter_type_id        bigint                              not null, -- FOREIGN KEY
    version              bigint check (version >= 0)         not null,

    CONSTRAINT cruise_addresses_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT cruise_alter_type_id_fk_constraint FOREIGN KEY (alter_type_id) REFERENCES alter_types (id),
    CONSTRAINT cruise_addresses_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT cruise_addresses_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create sequence cruise_addresses_id_seq
    START WITH 1
    INCREMENT BY 1;

create table cruise_pictures
(
    id                   bigint                              not null,
    img_name             varchar(25),
    img                  bytea,                                        --nullable only for implementation time.

    creation_date_time   timestamp default CURRENT_TIMESTAMP not null,
    last_alter_date_time timestamp                           not null,
    created_by_id        bigint                              not null, -- FOREIGN KEY
    altered_by_id        bigint                              not null, -- FOREIGN KEY
    alter_type_id        bigint                              not null, -- FOREIGN KEY
    version              bigint check (version >= 0)         not null,

    CONSTRAINT cruise_pictures_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT cruise_pictures_alter_type_id_fk_constraint FOREIGN KEY (alter_type_id) REFERENCES alter_types (id),
    CONSTRAINT cruise_pictures_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT cruise_pictures_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create sequence cruise_pictures_id_seq
    START WITH 1
    INCREMENT BY 1;

create table commercial_types
(
    id   bigint      not null,
    name varchar(25) not null,

    CONSTRAINT commercial_types_primary_key_constraint PRIMARY KEY (id)
);

create table cruises_groups
(
    id                   bigint                                                              not null,
    company_id           bigint                                                              not null, -- FOREIGN KEY
    name                 varchar(64)                                                         not null,
    number_of_seats      bigint check ((number_of_seats >= 0) AND (number_of_seats <= 5200)) not null,
    price                numeric(8, 2) check ((price >= (0)::numeric))                       not null,
    start_address_id     bigint                                                              not null, -- FOREIGN KEY
    average_rating       numeric(2, 1) check ((average_rating >= (0)::numeric) AND (average_rating <= (5)::numeric)),

    creation_date_time   timestamp default CURRENT_TIMESTAMP                                 not null,
    last_alter_date_time timestamp                                                           not null,
    created_by_id        bigint                                                              not null, -- FOREIGN KEY
    altered_by_id        bigint                                                              not null, -- FOREIGN KEY
    alter_type_id        bigint                                                              not null, -- FOREIGN KEY
    version              bigint check (version >= 0)                                         not null,

    CONSTRAINT cruises_groups_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT cruises_groups_companies_fk FOREIGN KEY (company_id) REFERENCES companies (id),
    CONSTRAINT cruises_groups_start_address_id_fk_constraint FOREIGN KEY (start_address_id) REFERENCES cruise_addresses (id),
    CONSTRAINT cruises_groups_alter_type_id_fk_constraint FOREIGN KEY (alter_type_id) REFERENCES alter_types (id),
    CONSTRAINT cruises_groups_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT cruises_groups_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create sequence cruises_groups_id_seq
    START WITH 1
    INCREMENT BY 1;

create table cruises
(
    id                   bigint                              not null,
    start_date           timestamp                           not null,
    end_date             timestamp,
    active               boolean   default false             not null,
    description          varchar                             not null,
    cruises_group_id     bigint                              not null, -- FOREIGN KEY
    available            boolean   default false             not null,

    creation_date_time   timestamp default CURRENT_TIMESTAMP not null,
    last_alter_date_time timestamp                           not null,
    created_by_id        bigint                              not null, -- FOREIGN KEY
    altered_by_id        bigint                              not null, -- FOREIGN KEY
    alter_type_id        bigint                              not null, -- FOREIGN KEY

    version              bigint check (version >= 0)         not null,

    CONSTRAINT cruises_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT cruises_cruises_group_id_fk FOREIGN KEY (cruises_group_id) REFERENCES cruises_groups (id),
    CONSTRAINT cruises_alter_type_id_fk_constraint FOREIGN KEY (alter_type_id) REFERENCES alter_types (id),
    CONSTRAINT cruises_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT cruises_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create sequence cruises_id_seq
    START WITH 1
    INCREMENT BY 1;

create table attractions
(
    id                   bigint                              not null,
    name                 varchar(25)                         not null,
    description          varchar                             not null,
    price                numeric(8, 2)                       not null,
    number_of_seats      bigint check (number_of_seats >= 0) not null,
    available            boolean   default false             not null,
    cruise_id            bigint,                                       -- FOREIGN KEY

    creation_date_time   timestamp default CURRENT_TIMESTAMP not null,
    last_alter_date_time timestamp                           not null,
    created_by_id        bigint                              not null, -- FOREIGN KEY
    altered_by_id        bigint                              not null, -- FOREIGN KEY
    alter_type_id        bigint                              not null, -- FOREIGN KEY
    version              bigint check (version >= 0)         not null,

    CONSTRAINT attractions_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT attractions_cruise_id_fk_constraint FOREIGN KEY (cruise_id) REFERENCES cruises (id),
    CONSTRAINT attractions_alter_type_id_fk_constraint FOREIGN KEY (alter_type_id) REFERENCES alter_types (id),
    CONSTRAINT attractions_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT attractions_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create sequence attractions_id_seq
    START WITH 1
    INCREMENT BY 1;

create table reservations
(
    id                   bigint                              not null,
    client_id            bigint                              not null, --FOREIGN KEY
    number_of_seats      bigint check (number_of_seats >= 0) not null,
    cruise_id            bigint                              not null, --FOREIGN KEY

    creation_date_time   timestamp default CURRENT_TIMESTAMP not null,
    last_alter_date_time timestamp                           not null,
    created_by_id        bigint                              not null, -- FOREIGN KEY
    altered_by_id        bigint                              not null, -- FOREIGN KEY
    alter_type_id        bigint                              not null, -- FOREIGN KEY
    version              bigint check (version >= 0)         not null,

    CONSTRAINT reservations_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT reservations_client_id_fk_constraint FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT reservations_cruise_id_fk_constraint FOREIGN KEY (cruise_id) REFERENCES cruises (id),
    CONSTRAINT reservations_alter_type_id_fk_constraint FOREIGN KEY (alter_type_id) REFERENCES alter_types (id),
    CONSTRAINT reservations_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT reservations_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create sequence reservations_id_seq
    START WITH 1
    INCREMENT BY 1;

create table ratings
(
    id                   bigint                                                                      not null,
    account_id           bigint                                                                      not null, -- FOREIGN KEY
    cruise_id            bigint                                                                      not null, -- FOREIGN KEY
    rating               numeric(2, 1) check ((rating >= (0)::numeric) AND (rating <= (5)::numeric)) not null,

    creation_date_time   timestamp default CURRENT_TIMESTAMP                                         not null,
    last_alter_date_time timestamp                                                                   not null,
    created_by_id        bigint                                                                      not null, -- FOREIGN KEY
    altered_by_id        bigint                                                                      not null, -- FOREIGN KEY
    alter_type_id        bigint                                                                      not null, -- FOREIGN KEY
    version              bigint check (version >= 0)                                                 not null,

    CONSTRAINT ratings_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT ratings_account_id_fk_constraint FOREIGN KEY (account_id) REFERENCES accounts (id),
    CONSTRAINT ratings_cruise_id_fk_constraint FOREIGN KEY (cruise_id) REFERENCES cruises (id),
    CONSTRAINT ratings_alter_type_id_fk_constraint FOREIGN KEY (alter_type_id) REFERENCES alter_types (id),
    CONSTRAINT ratings_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT ratings_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create sequence ratings_id_seq
    START WITH 1
    INCREMENT BY 1;

create table comments
(
    id                   bigint                              not null,
    account_id           bigint                              not null, -- FOREIGN KEY
    cruise_id            bigint                              not null, -- FOREIGN KEY
    comment              varchar(2500)                       not null,

    creation_date_time   timestamp default CURRENT_TIMESTAMP not null,
    last_alter_date_time timestamp                           not null,
    created_by_id        bigint                              not null, -- FOREIGN KEY
    altered_by_id        bigint                              not null, -- FOREIGN KEY
    alter_type_id        bigint                              not null, -- FOREIGN KEY
    version              bigint check (version >= 0)         not null,

    CONSTRAINT comments_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT comments_account_id_fk_constraint FOREIGN KEY (account_id) REFERENCES accounts (id),
    CONSTRAINT comments_cruise_id_fk_constraint FOREIGN KEY (cruise_id) REFERENCES cruises (id),
    CONSTRAINT comments_alter_type_id_fk_constraint FOREIGN KEY (alter_type_id) REFERENCES alter_types (id),
    CONSTRAINT comments_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT comments_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create sequence comments_id_seq
    START WITH 1
    INCREMENT BY 1;

create table commercials
(
    id                   bigint                              not null,
    commercial_type_id   bigint                              not null, -- FOREIGN KEY
    cruises_group_id     bigint                              not null, -- FOREIGN KEY
    start_date           timestamp default CURRENT_TIMESTAMP not null,
    end_date             timestamp                           not null,

    creation_date_time   timestamp default CURRENT_TIMESTAMP not null,
    last_alter_date_time timestamp                           not null,
    created_by_id        bigint                              not null, -- FOREIGN KEY
    altered_by_id        bigint                              not null, -- FOREIGN KEY
    alter_type_id        bigint                              not null, -- FOREIGN KEY
    version              bigint check (version >= 0)         not null,

    CONSTRAINT commercials_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT commercials_cruises_group_id_fk_constraint FOREIGN KEY (cruises_group_id) REFERENCES cruises_groups (id),
    CONSTRAINT commercials_commercial_type_id_fk_constraint FOREIGN KEY (commercial_type_id) REFERENCES commercial_types (id),
    CONSTRAINT commercials_alter_type_id_fk_constraint FOREIGN KEY (alter_type_id) REFERENCES alter_types (id),
    CONSTRAINT commercials_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT commercials_altered_by_id_fk_constraint FOREIGN KEY (altered_by_id) REFERENCES accounts (id)
);

create sequence commercials_id_seq
    START WITH 1
    INCREMENT BY 1;


create table cruises_group_pictures
(
    cruises_group_id  bigint not null, -- FOREIGN KEY
    cruise_picture_id bigint,          -- FOREIGN KEY no not null in this moment

    CONSTRAINT cruises_group_pictures_primary_key_constraint PRIMARY KEY (cruises_group_id, cruise_picture_id),
    CONSTRAINT cruises_group_pictures_groups_id_pictures_id_unique UNIQUE (cruises_group_id, cruise_picture_id),
    CONSTRAINT cruises_group_pictures_cruises_group_id_fk_constraint FOREIGN KEY (cruises_group_id) REFERENCES cruises_groups (id),
    CONSTRAINT cruises_group_pictures_cruise_picture_id_fk_constraint FOREIGN KEY (cruise_picture_id) REFERENCES cruise_pictures (id)
);

create table reservation_attractions
(
    reservation_id bigint not null,--FOREIGN KEY
    attraction_id  bigint not null,--FOREIGN KEY

    CONSTRAINT reservation_attractions_primary_key_constraint PRIMARY KEY (reservation_id, attraction_id),
    CONSTRAINT reservation_attractions_reservation_id_attraction_id_unique UNIQUE (reservation_id, attraction_id),
    CONSTRAINT reservation_attractions_reservation_id_fk_constraint FOREIGN KEY (reservation_id) REFERENCES reservations (id),
    CONSTRAINT reservation_attractions_attraction_id_fk_constraint FOREIGN KEY (attraction_id) REFERENCES attractions (id)
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
ALTER TABLE commercial_types
    OWNER to ssbd03admin;
ALTER TABLE language_types
    OWNER to ssbd03admin;
ALTER TABLE alter_types
    OWNER to ssbd03admin;
ALTER TABLE cruises_group_pictures
    OWNER to ssbd03admin;
ALTER TABLE reservation_attractions
    OWNER to ssbd03admin;
ALTER TABLE cruises_groups
    OWNER to ssbd03admin;
ALTER TABLE companies
    OWNER to ssbd03admin;
ALTER TABLE cruises
    OWNER to ssbd03admin;
ALTER TABLE account_id_seq
    OWNER to ssbd03admin;
ALTER TABLE access_level_id_seq
    OWNER to ssbd03admin;
ALTER TABLE address_id_seq
    OWNER to ssbd03admin;
ALTER TABLE cruise_addresses_id_seq
    OWNER to ssbd03admin;
ALTER TABLE cruise_pictures_id_seq
    OWNER to ssbd03admin;
ALTER TABLE companies_id_seq
    OWNER to ssbd03admin;
ALTER TABLE cruises_groups_id_seq
    OWNER to ssbd03admin;
ALTER TABLE cruises_id_seq
    OWNER to ssbd03admin;
ALTER TABLE attractions_id_seq
    OWNER to ssbd03admin;
ALTER TABLE reservations_id_seq
    OWNER to ssbd03admin;
ALTER TABLE ratings_id_seq
    OWNER to ssbd03admin;
ALTER TABLE comments_id_seq
    OWNER to ssbd03admin;
ALTER TABLE commercials_id_seq
    OWNER to ssbd03admin;

ALTER
    VIEW glassfish_auth_view OWNER TO ssbd03admin;

--Table sequence--
ALTER TABLE accounts
    ALTER COLUMN id
        SET DEFAULT nextval('account_id_seq');

ALTER TABLE access_levels
    ALTER COLUMN id
        SET DEFAULT nextval('access_level_id_seq');

ALTER TABLE addresses
    ALTER COLUMN id
        SET DEFAULT nextval('address_id_seq');

AlTER TABLE reservations
    ALTER COLUMN id
        SET DEFAULT nextval('reservations_id_seq');

AlTER TABLE attractions
    ALTER COLUMN id
        SET DEFAULT nextval('attractions_id_seq');

ALTER TABLE ratings
    ALTER COLUMN id
        SET DEFAULT nextval('ratings_id_seq');

ALTER TABLE comments
    ALTER COLUMN id
        SET DEFAULT nextval('comments_id_seq');

ALTER TABLE commercials
    ALTER COLUMN id
        SET DEFAULT nextval('commercials_id_seq');

ALTER TABLE cruise_addresses
    ALTER COLUMN id
        SET DEFAULT nextval('cruise_addresses_id_seq');

ALTER TABLE cruise_pictures
    ALTER COLUMN id
        SET DEFAULT nextval('cruise_pictures_id_seq');

ALTER TABLE cruises_groups
    ALTER COLUMN id
        SET DEFAULT nextval('cruises_groups_id_seq');

ALTER TABLE companies
    ALTER COLUMN id
        SET DEFAULT nextval('companies_id_seq');

ALTER TABLE cruises
    ALTER COLUMN id
        SET DEFAULT nextval('cruises_id_seq');

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

GRANT SELECT
    ON companies TO ssbd03mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON clients TO ssbd03mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON moderators TO ssbd03mok;

GRANT SELECT
    ON alter_types TO ssbd03mok;

GRANT SELECT, UPDATE
    ON SEQUENCE account_id_seq TO ssbd03mok;

GRANT SELECT, UPDATE
    ON SEQUENCE access_level_id_seq TO ssbd03mok;

GRANT SELECT, UPDATE
    ON SEQUENCE address_id_seq TO ssbd03mok;

-- Table permissions for MOW --
GRANT SELECT
    ON access_levels TO ssbd03mow;

GRANT SELECT
    ON accounts TO ssbd03mow;

GRANT SELECT
    ON alter_types TO ssbd03mow;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON attractions TO ssbd03mow;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON comments TO ssbd03mow;

GRANT SELECT
    ON commercial_types TO ssbd03mow;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON commercials TO ssbd03mow;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON companies TO ssbd03mow;

GRANT SELECT, INSERT, DELETE
    ON cruise_addresses TO ssbd03mow;

GRANT SELECT, INSERT, DELETE
    ON cruise_pictures TO ssbd03mow;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON cruises TO ssbd03mow;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON cruises_groups TO ssbd03mow;

GRANT SELECT, INSERT, DELETE
    ON cruises_group_pictures TO ssbd03mow;

GRANT SELECT
    ON language_types TO ssbd03mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON ratings TO ssbd03mow;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON reservations TO ssbd03mow;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON reservation_attractions TO ssbd03mow;

GRANT SELECT, UPDATE
    ON SEQUENCE cruise_addresses_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON SEQUENCE cruise_pictures_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON SEQUENCE companies_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON SEQUENCE cruises_groups_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON SEQUENCE cruises_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON SEQUENCE attractions_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON SEQUENCE reservations_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON SEQUENCE ratings_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON SEQUENCE comments_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON SEQUENCE commercials_id_seq TO ssbd03mow;

GRANT SELECT ON glassfish_auth_view TO ssbd03glassfish;

-- INDEXES --
CREATE INDEX accounts_language_type_id_index
    ON accounts USING btree
        (language_type_id ASC NULLS LAST);
CREATE INDEX accounts_created_by_id_index
    ON accounts USING btree
        (created_by_id ASC NULLS LAST);
CREATE INDEX accounts_altered_by_id_index
    ON accounts USING btree
        (altered_by_id ASC NULLS LAST);
CREATE INDEX accounts_alter_type_id_index
    ON accounts USING btree
        (alter_type_id ASC NULLS LAST);

CREATE INDEX access_levels_account_id_index
    ON access_levels USING btree
        (account_id ASC NULLS LAST);
CREATE INDEX access_levels_created_by_id_index
    ON access_levels USING btree
        (created_by_id ASC NULLS LAST);
CREATE INDEX access_levels_altered_by_id_index
    ON access_levels USING btree
        (altered_by_id ASC NULLS LAST);
CREATE INDEX access_levels_alter_type_id_index
    ON access_levels USING btree
        (alter_type_id ASC NULLS LAST);

CREATE INDEX administrators_id_index
    ON administrators USING btree
        (id ASC NULLS LAST);

CREATE INDEX business_workers_id_index
    ON business_workers USING btree
        (id ASC NULLS LAST);

CREATE INDEX business_workers_company_id_index
    ON business_workers USING btree
        (company_id ASC NULLS LAST);

CREATE INDEX addresses_created_by_id_index
    ON addresses USING btree
        (created_by_id ASC NULLS LAST);
CREATE INDEX addresses_altered_by_id_index
    ON addresses USING btree
        (altered_by_id ASC NULLS LAST);
CREATE INDEX addresses_alter_type_id_index
    ON addresses USING btree
        (alter_type_id ASC NULLS LAST);

CREATE INDEX clients_id_index
    ON clients USING btree
        (id ASC NULLS LAST);
CREATE INDEX clients_home_address_id_index
    ON clients USING btree
        (home_address_id ASC NULLS LAST);

CREATE INDEX moderators_id_index
    ON moderators USING btree
        (id ASC NULLS LAST);

CREATE INDEX cruise_addresses_created_by_id_index
    ON cruise_addresses USING btree
        (created_by_id ASC NULLS LAST);
CREATE INDEX cruise_addresses_altered_by_id_index
    ON cruise_addresses USING btree
        (altered_by_id ASC NULLS LAST);
CREATE INDEX cruise_addresses_alter_type_id_index
    ON cruise_addresses USING btree
        (alter_type_id ASC NULLS LAST);

CREATE INDEX cruise_pictures_created_by_id_index
    ON cruise_pictures USING btree
        (created_by_id ASC NULLS LAST);
CREATE INDEX cruise_pictures_altered_by_id_index
    ON cruise_pictures USING btree
        (altered_by_id ASC NULLS LAST);
CREATE INDEX cruise_pictures_alter_type_id_index
    ON cruise_pictures USING btree
        (alter_type_id ASC NULLS LAST);

CREATE INDEX companies_address_id_index
    ON companies USING btree
        (address_id ASC NULLS LAST);
CREATE INDEX companies_created_by_id_index
    ON companies USING btree
        (created_by_id ASC NULLS LAST);
CREATE INDEX companies_altered_by_id_index
    ON companies USING btree
        (altered_by_id ASC NULLS LAST);
CREATE INDEX companies_alter_type_id_index
    ON companies USING btree
        (alter_type_id ASC NULLS LAST);

CREATE INDEX cruises_groups_company_id_index
    ON cruises_groups USING btree
        (company_id ASC NULLS LAST);
CREATE INDEX cruises_groups_start_address_id_index
    ON cruises_groups USING btree
        (start_address_id ASC NULLS LAST);
CREATE INDEX cruises_groups_created_by_id_index
    ON cruises_groups USING btree
        (created_by_id ASC NULLS LAST);
CREATE INDEX cruises_groups_altered_by_id_index
    ON cruises_groups USING btree
        (altered_by_id ASC NULLS LAST);
CREATE INDEX cruises_groups_alter_type_id_index
    ON cruises_groups USING btree
        (alter_type_id ASC NULLS LAST);

CREATE INDEX cruises_cruises_groups_id_index
    ON cruises USING btree
        (cruises_group_id ASC NULLS LAST);
CREATE INDEX cruises_created_by_id_index
    ON cruises USING btree
        (created_by_id ASC NULLS LAST);
CREATE INDEX cruises_altered_by_id_index
    ON cruises USING btree
        (altered_by_id ASC NULLS LAST);
CREATE INDEX cruises_alter_type_id_index
    ON cruises USING btree
        (alter_type_id ASC NULLS LAST);

CREATE INDEX attractions_cruise_id_index
    ON attractions USING btree
        (cruise_id ASC NULLS LAST);
CREATE INDEX attractions_created_by_id_index
    ON attractions USING btree
        (created_by_id ASC NULLS LAST);
CREATE INDEX attractions_altered_by_id_index
    ON attractions USING btree
        (altered_by_id ASC NULLS LAST);
CREATE INDEX attractions_alter_type_id_index
    ON attractions USING btree
        (alter_type_id ASC NULLS LAST);

CREATE INDEX reservations_client_id_index
    ON reservations USING btree
        (client_id ASC NULLS LAST);
CREATE INDEX reservations_cruise_id_index
    ON reservations USING btree
        (cruise_id ASC NULLS LAST);
CREATE INDEX reservations_created_by_id_index
    ON reservations USING btree
        (created_by_id ASC NULLS LAST);
CREATE INDEX reservations_altered_by_id_index
    ON reservations USING btree
        (altered_by_id ASC NULLS LAST);
CREATE INDEX reservations_alter_type_id_index
    ON reservations USING btree
        (alter_type_id ASC NULLS LAST);

CREATE INDEX ratings_account_id_index
    ON ratings USING btree
        (account_id ASC NULLS LAST);
CREATE INDEX ratings_cruise_id_index
    ON ratings USING btree
        (cruise_id ASC NULLS LAST);
CREATE INDEX ratings_created_by_id_index
    ON ratings USING btree
        (created_by_id ASC NULLS LAST);
CREATE INDEX ratings_altered_by_id_index
    ON ratings USING btree
        (altered_by_id ASC NULLS LAST);
CREATE INDEX ratings_alter_type_id_index
    ON ratings USING btree
        (alter_type_id ASC NULLS LAST);

CREATE INDEX comments_account_id_index
    ON comments USING btree
        (account_id ASC NULLS LAST);
CREATE INDEX comments_cruise_id_index
    ON comments USING btree
        (cruise_id ASC NULLS LAST);
CREATE INDEX comments_created_by_id_index
    ON comments USING btree
        (created_by_id ASC NULLS LAST);
CREATE INDEX comments_altered_by_id_index
    ON comments USING btree
        (altered_by_id ASC NULLS LAST);
CREATE INDEX comments_alter_type_id_index
    ON comments USING btree
        (alter_type_id ASC NULLS LAST);
CREATE INDEX commercials_commercial_type_id_index
    ON commercials USING btree
        (commercial_type_id ASC NULLS LAST);
CREATE INDEX commercials_cruises_group_id_index
    ON commercials USING btree
        (cruises_group_id ASC NULLS LAST);
CREATE INDEX commercials_created_by_id_index
    ON commercials USING btree
        (created_by_id ASC NULLS LAST);
CREATE INDEX commercials_altered_by_id_index
    ON commercials USING btree
        (altered_by_id ASC NULLS LAST);
CREATE INDEX commercials_alter_type_id_index
    ON commercials USING btree
        (alter_type_id ASC NULLS LAST);

CREATE INDEX cruises_group_pictures_cruises_group_id_index
    ON cruises_group_pictures USING btree
        (cruises_group_id ASC NULLS LAST);
CREATE INDEX cruises_group_pictures_cruise_picture_id_index
    ON cruises_group_pictures USING btree
        (cruise_picture_id ASC NULLS LAST);

CREATE INDEX reservation_attractions_reservation_id_index
    ON reservation_attractions USING btree
        (reservation_id ASC NULLS LAST);
CREATE INDEX reservation_attractions_attraction_id_index
    ON reservation_attractions USING btree
        (attraction_id ASC NULLS LAST);