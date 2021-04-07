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

    alter_type                                    varchar   not null, -- FOREIGN KEY

    CONSTRAINT accounts_primary_key_constraint PRIMARY KEY (id),

    CONSTRAINT accounts_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT accounts_altered_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id)
);

create table access_levels
(
    id           bigint  not null,
    access_level varchar not null,
    account_id   bigint  not null,
    active       boolean not null,

    CONSTRAINT access_levels_id_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT access_levels_account_id_fk_constraint FOREIGN KEY (account_id) REFERENCES accounts (id)
);



create table administrators
(
    id                   bigint    not null, -- should be foreign for access_level

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null, -- FOREIGN KEY

    CONSTRAINT administrators_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT administrators_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id),

    CONSTRAINT administrators_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT administrators_altered_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id)
);

create table business_workers
(
    id                   bigint    not null,
    phone_number         varchar,

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null, -- FOREIGN KEY

    CONSTRAINT business_workers_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT business_workers_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id),

    CONSTRAINT business_workers_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT business_workers_altered_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id)
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
    alter_type           varchar   not null, -- FOREIGN KEY

    CONSTRAINT address_primary_key_constraint PRIMARY KEY (id),

    CONSTRAINT addresses_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT addresses_altered_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id)
);

create table clients
(
    id                   bigint    not null,
    phone_number         varchar,
    home_address_id      bigint,             -- FOREIGN KEY

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null, -- FOREIGN KEY

    CONSTRAINT clients_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT clients_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id),
    CONSTRAINT clients_home_address_id_fk_constraint FOREIGN KEY (home_address_id) REFERENCES addresses (id),

    CONSTRAINT clients_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT clients_altered_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id)
);

create table moderators
(
    id                   bigint    not null,

    creation_date_time   timestamp not null,
    last_alter_date_time timestamp not null,
    created_by_id        bigint    not null, -- FOREIGN KEY
    altered_by_id        bigint    not null, -- FOREIGN KEY
    alter_type           varchar   not null, -- FOREIGN KEY

    CONSTRAINT moderators_id_pk_constraint PRIMARY KEY (id),
    CONSTRAINT moderators_id_fk_constraint FOREIGN KEY (id) REFERENCES access_levels (id),

    CONSTRAINT moderators_created_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id),
    CONSTRAINT moderators_altered_by_id_fk_constraint FOREIGN KEY (created_by_id) REFERENCES accounts (id)
);


CREATE TABLE ratings
(
    id bigint NOT NULL,
    account_id bigint NOT NULL,
    cruise_id bigint NOT NULL,
    rating bigint NOT NULL,

    CONSTRAINT ratings_primary_key_constraint PRIMARY KEY (id),

    CONSTRAINT ratings_account_id_fk_constraint FOREIGN KEY (account_id) REFERENCES accounts (id),
    CONSTRAINT ratings_cruise_id_fk_constraint FOREIGN KEY (cruise_id) REFERENCES cruises (id),
);


CREATE TABLE comments
(
    id bigint NOT NULL,
    account_id bigint NOT NULL,
    cruise_id bigint NOT NULL,
    comment character varying NOT NULL,

    CONSTRAINT comments_primary_key_constraint PRIMARY KEY (id),
    CONSTRAINT comments_account_id_fk_constraint FOREIGN KEY (account_id) REFERENCES accounts (id),
    CONSTRAINT comments_cruise_id_fk_constraint FOREIGN KEY (cruise_id) REFERENCES cruises (id)
);


CREATE TABLE positionings
(
    id bigint NOT NULL,
    type boolean NOT NULL,
    cruises_group_id bigint NOT NULL,
    end_date timestamp NOT NULL,

    CONSTRAINT positionings_primary_key_constraint PRIMARY KEY (id),

    CONSTRAINT positionings_cruises_group_id_fk_constraint FOREIGN KEY (cruises_group_id) REFERENCES cruises_groups (id)
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
ALTER TABLE ratings
    OWNER to ssbd03admin;
ALTER TABLE comments
    OWNER to ssbd03admin;
ALTER TABLE positionings
    OWNER to ssbd03admin;
ALTER VIEW glassfish_auth_view OWNER TO ssbd03admin;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON access_levels TO ssbd00mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON accounts TO ssbd00mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON addresses TO ssbd00mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON administrators TO ssbd00mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON business_workers TO ssbd00mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON clients TO ssbd00mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON moderators TO ssbd00mok;

-- Table permissions --
GRANT SELECT ON glassfish_auth_view TO ssbd03glassfish;
