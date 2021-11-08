create database ssbd03;
use ssbd03;

create user 'ssbd03admin' identified by 'adminpasswd';
create user 'ssbd03glassfish' identified by 'glassfishpasswd';
create user 'ssbd03mok' identified by 'mokpasswd';
create user 'ssbd03mow' identified by 'mowpasswd';


GRANT ALL PRIVILEGES ON ssbd03.* TO 'ssbd03admin';
GRANT ALL PRIVILEGES ON ssbd03.* TO 'ssbd03glassfish';
GRANT ALL PRIVILEGES ON ssbd03.* TO 'ssbd03mok';
GRANT ALL PRIVILEGES ON ssbd03.* TO 'ssbd03mow';

create table access_level_id_seq
(
    next_val bigint null
);

create table account_id_seq
(
    next_val bigint null
);

create table address_id_seq
(
    next_val bigint null
);

create table alter_types
(
    id   bigint       not null
        primary key,
    name varchar(255) not null,
    constraint UK_h4qxgv3iep1rugqvd4uwbuvka
        unique (name)
);

create table attractions_id_seq
(
    next_val bigint null
);

create table companies_id_seq
(
    next_val bigint null
);

create table cruise_addresses_id_seq
(
    next_val bigint null
);

create table cruise_pictures_id_seq
(
    next_val bigint null
);

create table cruises_groups_id_seq
(
    next_val bigint null
);

create table cruises_id_seq
(
    next_val bigint null
);

create table hibernate_sequence
(
    next_val bigint null
);

create table language_types
(
    id   bigint       not null
        primary key,
    name varchar(255) not null,
    constraint UK_bl0bjeh16npdadtyhf0dcse
        unique (name)
);

create table accounts
(
    id                                            bigint       not null
        primary key,
    creation_date_time                            datetime(6)  not null,
    last_alter_date_time                          datetime(6)  not null,
    version                                       bigint       not null,
    active                                        bit          not null,
    confirmed                                     bit          not null,
    dark_mode                                     bit          not null,
    email                                         varchar(255) not null,
    first_name                                    varchar(64)  not null,
    last_correct_authentication_date_time         datetime(6)  null,
    last_correct_authentication_logical_address   varchar(255) null,
    last_incorrect_authentication_date_time       datetime(6)  null,
    last_incorrect_authentication_logical_address varchar(255) null,
    login                                         varchar(255) not null,
    number_of_authentication_failures             int          null,
    password_hash                                 varchar(255) not null,
    second_name                                   varchar(64)  not null,
    alter_type_id                                 bigint       not null,
    altered_by_id                                 bigint       not null,
    created_by_id                                 bigint       not null,
    language_type_id                              bigint       not null,
    constraint UK_cc2c9baeppipgy2rjeccwcqs0
        unique (login),
    constraint UK_n7ihswpy07ci568w34q0oi8he
        unique (email),
    constraint FK3ibj79ofx679xphlcdxew3er5
        foreign key (created_by_id) references accounts (id),
    constraint FK7xdylueapo3558cgup41ilgvj
        foreign key (altered_by_id) references accounts (id),
    constraint FKfyg9h1f2i3krh0xsiwyasquou
        foreign key (language_type_id) references language_types (id),
    constraint FKm92nbpwavuajxmsjli38hfkot
        foreign key (alter_type_id) references alter_types (id)
);

create table access_levels
(
    access_level         varchar(31) not null,
    id                   bigint      not null
        primary key,
    creation_date_time   datetime(6) not null,
    last_alter_date_time datetime(6) not null,
    version              bigint      not null,
    enabled              bit         not null,
    alter_type_id        bigint      not null,
    altered_by_id        bigint      not null,
    created_by_id        bigint      not null,
    account_id           bigint      not null,
    constraint FK4t6qg0obxat6ffcfqrtpuerkt
        foreign key (altered_by_id) references accounts (id),
    constraint FK5rd7qdgfbq3m9ith8lx3vobf1
        foreign key (alter_type_id) references alter_types (id),
    constraint FKjoorx6s2dnm8ba6v9kplk3fl6
        foreign key (created_by_id) references accounts (id),
    constraint FKmmcqqo8rkbf9ew6oas1oqk2a2
        foreign key (account_id) references accounts (id)
);

create index accounts_alter_type_id_index
    on accounts (alter_type_id);

create index accounts_altered_by_id_index
    on accounts (altered_by_id);

create index accounts_created_by_id_index
    on accounts (created_by_id);

create index accounts_language_type_id_index
    on accounts (language_type_id);

create table addresses
(
    id                   bigint       not null
        primary key,
    creation_date_time   datetime(6)  not null,
    last_alter_date_time datetime(6)  not null,
    version              bigint       not null,
    city                 varchar(64)  null,
    country              varchar(64)  null,
    house_number         varchar(255) null,
    postal_code          varchar(20)  null,
    street               varchar(64)  null,
    alter_type_id        bigint       not null,
    altered_by_id        bigint       not null,
    created_by_id        bigint       not null,
    constraint FK34nawl4s4r78r4qg05dsc3wtk
        foreign key (altered_by_id) references accounts (id),
    constraint FKf94qxd2cdck75pydk78t8j1ul
        foreign key (created_by_id) references accounts (id),
    constraint FKtc8wh3mkfwu51dl0g6u41k5yo
        foreign key (alter_type_id) references alter_types (id)
);

create table administrators
(
    id bigint not null
        primary key,
    constraint FKa4ulmbsfw23p55nsgylx6wq5g
        foreign key (id) references access_levels (id)
);

create table clients
(
    phone_number    varchar(15) not null,
    id              bigint      not null
        primary key,
    home_address_id bigint      not null,
    constraint FK1vnppaewx21gstq7p97nrub9n
        foreign key (home_address_id) references addresses (id),
    constraint FKk4o4of3bc35b1d7bgwpvx45cr
        foreign key (id) references access_levels (id)
);

create table companies
(
    id                   bigint      not null
        primary key,
    creation_date_time   datetime(6) not null,
    last_alter_date_time datetime(6) not null,
    version              bigint      not null,
    nip                  bigint      not null,
    name                 varchar(64) not null,
    phone_number         varchar(15) not null,
    alter_type_id        bigint      not null,
    altered_by_id        bigint      not null,
    created_by_id        bigint      not null,
    address_id           bigint      not null,
    constraint UK_50ygfritln653mnfhxucoy8up
        unique (name),
    constraint UK_orn15lg0w4uod7ltljcjgd0le
        unique (nip),
    constraint FK8w70yf6urddd0ky7ev90okenf
        foreign key (address_id) references addresses (id),
    constraint FKds1kvnhdj71ohbsmfg5man36x
        foreign key (altered_by_id) references accounts (id),
    constraint FKe3rgg81vccs0vs1y3bq240jp1
        foreign key (created_by_id) references accounts (id),
    constraint FKp2j79g37wkq53f9fbm66eiadm
        foreign key (alter_type_id) references alter_types (id)
);

create table business_workers
(
    confirmed    bit         not null,
    phone_number varchar(15) not null,
    id           bigint      not null
        primary key,
    company_id   bigint      not null,
    constraint FK2ivkr8pivgktf35ehn6j8tc7k
        foreign key (id) references access_levels (id),
    constraint FKdjdxac2xdknx5a12jslbm1bge
        foreign key (company_id) references companies (id)
);

create table cruise_addresses
(
    id                   bigint       not null
        primary key,
    creation_date_time   datetime(6)  not null,
    last_alter_date_time datetime(6)  not null,
    version              bigint       not null,
    city_name            varchar(64)  null,
    country_name         varchar(64)  null,
    harbor_name          varchar(255) null,
    street               varchar(64)  null,
    street_number        varchar(255) null,
    alter_type_id        bigint       not null,
    altered_by_id        bigint       not null,
    created_by_id        bigint       not null,
    constraint FK4u6ao6jhpgaxx6fmlx2a0h20g
        foreign key (altered_by_id) references accounts (id),
    constraint FKdyq8j0vn8masrij00crpq0vm7
        foreign key (created_by_id) references accounts (id),
    constraint FKlplxl1ubtnqqc9sl28oun1908
        foreign key (alter_type_id) references alter_types (id)
);

create table cruise_pictures
(
    id                   bigint       not null
        primary key,
    creation_date_time   datetime(6)  not null,
    last_alter_date_time datetime(6)  not null,
    version              bigint       not null,
    img                  varchar(255) null,
    img_name             varchar(255) null,
    alter_type_id        bigint       not null,
    altered_by_id        bigint       not null,
    created_by_id        bigint       not null,
    constraint FKc4pmtt6yy4gj4o1xtju3926vu
        foreign key (created_by_id) references accounts (id),
    constraint FKe6jetminus37cek0nldqumfrt
        foreign key (alter_type_id) references alter_types (id),
    constraint FKmhrv1bujb4a1xp1pu2bns5c7w
        foreign key (altered_by_id) references accounts (id)
);

create table cruises_groups
(
    id                   bigint       not null
        primary key,
    creation_date_time   datetime(6)  not null,
    last_alter_date_time datetime(6)  not null,
    version              bigint       not null,
    active               bit          not null,
    average_rating       double       null,
    description          varchar(255) null,
    name                 varchar(255) null,
    number_of_seats      bigint       null,
    price                double       null,
    uuid                 binary(255)  not null,
    alter_type_id        bigint       not null,
    altered_by_id        bigint       not null,
    created_by_id        bigint       not null,
    start_address_id     bigint       not null,
    company_id           bigint       not null,
    constraint UK_km110mavo8mv17crd2my6scsl
        unique (uuid),
    constraint FK4p7l7tjghg5392gd3h3xa7myl
        foreign key (altered_by_id) references accounts (id),
    constraint FK7eayl3gdmnpsfa6bijt9oun26
        foreign key (alter_type_id) references alter_types (id),
    constraint FK7fcq8txhrcj9n4rbbn3uvmtm6
        foreign key (company_id) references companies (id),
    constraint FK9ni0qa2heko00bhps3halbgiq
        foreign key (start_address_id) references cruise_addresses (id),
    constraint FKjbavbou3098y0iannr6csg8t1
        foreign key (created_by_id) references accounts (id)
);

create table cruises
(
    id                   bigint      not null
        primary key,
    creation_date_time   datetime(6) not null,
    last_alter_date_time datetime(6) not null,
    version              bigint      not null,
    active               bit         null,
    end_date             datetime(6) not null,
    published            bit         null,
    start_date           datetime(6) not null,
    uuid                 binary(255) not null,
    alter_type_id        bigint      not null,
    altered_by_id        bigint      not null,
    created_by_id        bigint      not null,
    cruises_group_id     bigint      null,
    constraint UK_c005e6dwop4ag5nwnco2ftuhu
        unique (uuid),
    constraint FK9r3m0jw60dpj84p5imlxrjfa6
        foreign key (altered_by_id) references accounts (id),
    constraint FKg8a8ekuvkuhgkfroja0p5fuja
        foreign key (cruises_group_id) references cruises_groups (id),
    constraint FKgixfusnynpio8jbhr5fee9h0i
        foreign key (created_by_id) references accounts (id),
    constraint FKlyufw2uc2tmkspj9yg3tspv8h
        foreign key (alter_type_id) references alter_types (id)
);

create table attractions
(
    id                   bigint       not null
        primary key,
    creation_date_time   datetime(6)  not null,
    last_alter_date_time datetime(6)  not null,
    version              bigint       not null,
    description          varchar(255) null,
    has_free_spots       bit          null,
    name                 varchar(255) null,
    number_of_seats      bigint       null,
    price                double       null,
    uuid                 binary(255)  not null,
    alter_type_id        bigint       not null,
    altered_by_id        bigint       not null,
    created_by_id        bigint       not null,
    cruise_id            bigint       not null,
    constraint UK_3xwc79almvjelk26cla1cx355
        unique (uuid),
    constraint FK3nig2pblhg3ow9av72o7ww2uh
        foreign key (created_by_id) references accounts (id),
    constraint FKo0vv0xfhmcneggmkb4taagton
        foreign key (altered_by_id) references accounts (id),
    constraint FKqravlyll53k618uyhode97kll
        foreign key (cruise_id) references cruises (id),
    constraint FKqs1sp5l1p67b2l1qb57vpi3s4
        foreign key (alter_type_id) references alter_types (id)
);

create table cruises_group_pictures
(
    cruises_group_id  bigint not null,
    cruise_picture_id bigint not null,
    constraint FK5bfbnt0pv7d4thtxae9rgyoyb
        foreign key (cruises_group_id) references cruises_groups (id),
    constraint FKp1du74igefjcxyq5ni41aubo4
        foreign key (cruise_picture_id) references cruise_pictures (id)
);

create table moderators
(
    id bigint not null
        primary key,
    constraint FKec3ocdsw9bxy72snsui63i7fx
        foreign key (id) references access_levels (id)
);

create table ratings
(
    id                   bigint      not null
        primary key,
    creation_date_time   datetime(6) not null,
    last_alter_date_time datetime(6) not null,
    version              bigint      not null,
    rating               double      null,
    uuid                 binary(255) not null,
    alter_type_id        bigint      not null,
    altered_by_id        bigint      not null,
    created_by_id        bigint      not null,
    account_id           bigint      not null,
    cruise_group_id      bigint      not null,
    constraint UK_mmyum5upxsqk9htvhe0b6qpha
        unique (uuid),
    constraint FK16dlp35b75qcuy988cxcphy7t
        foreign key (alter_type_id) references alter_types (id),
    constraint FK71mqcse13gx0j55mo0rwqkolb
        foreign key (account_id) references accounts (id),
    constraint FKfjkwc7ufqfbthrxsn211nvgew
        foreign key (created_by_id) references accounts (id),
    constraint FKh4e9gx3tqy0896j5guedy2nmr
        foreign key (altered_by_id) references accounts (id),
    constraint FKpi3p22up9sedyov66hi2r0oji
        foreign key (cruise_group_id) references cruises_groups (id)
);

create table ratings_id_seq
(
    next_val bigint null
);

create table reservations
(
    id                   bigint      not null
        primary key,
    creation_date_time   datetime(6) not null,
    last_alter_date_time datetime(6) not null,
    version              bigint      not null,
    number_of_seats      bigint      null,
    price                double      null,
    uuid                 binary(255) not null,
    alter_type_id        bigint      not null,
    altered_by_id        bigint      not null,
    created_by_id        bigint      not null,
    client_id            bigint      not null,
    cruise_id            bigint      not null,
    constraint UK_6dlj3s139kj63kvhcd099x8xk
        unique (uuid),
    constraint FK1cij6euqjepr6mhdcwr9jg3pu
        foreign key (created_by_id) references accounts (id),
    constraint FK2gjhui5v08c5cl1vl1rcxijw3
        foreign key (alter_type_id) references alter_types (id),
    constraint FK6lekctbt4u88agg0b7cjsj6lf
        foreign key (client_id) references clients (id),
    constraint FKiqhukyao7wqh48mcfjuf352sl
        foreign key (altered_by_id) references accounts (id),
    constraint FKjnfs9nmt56u660060mvtm2i2e
        foreign key (cruise_id) references cruises (id)
);

create table reservation_attractions
(
    reservation_id bigint not null,
    attraction_id  bigint not null,
    constraint FKf8jp01p7pb9uuabe304xst4fq
        foreign key (attraction_id) references attractions (id),
    constraint FKta6ba33igbn6kgvx3xd49b914
        foreign key (reservation_id) references reservations (id)
);

create table reservations_id_seq
(
    next_val bigint null
);

create table used_codes
(
    id                 bigint       not null
        primary key,
    code               varchar(255) not null,
    creation_date_time datetime(6)  not null,
    used               bit          not null,
    account_id         bigint       not null,
    constraint UK_qeipafi6jiafu4xvbp4tjsldt
        unique (code),
    constraint FKmjjp6lus9f7mxt6t1rc4l15et
        foreign key (account_id) references accounts (id)
);

create table used_codes_id_seq
(
    next_val bigint null
);

create table used_tokens
(
    id                 bigint      not null
        primary key,
    creation_date_time datetime(6) not null,
    token              text        not null,
    used               bit         not null,
    account_id         bigint      not null,
    constraint FK3gxjppixy8ktf0hjfxa8t6j6k
        foreign key (account_id) references accounts (id)
);

create table used_tokens_id_seq
(
    next_val bigint null
);

GRANT SELECT, INSERT, UPDATE, DELETE
    ON used_tokens TO ssbd03mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON used_codes TO ssbd03mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON access_levels TO ssbd03mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON accounts TO ssbd03mok;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON addresses TO ssbd03mok;

GRANT SELECT, INSERT
    ON addresses TO ssbd03mow;

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
    ON account_id_seq TO ssbd03mok;

GRANT SELECT, UPDATE
    ON access_level_id_seq TO ssbd03mok;

GRANT SELECT, UPDATE
    ON address_id_seq TO ssbd03mok;

GRANT SELECT, UPDATE
    ON address_id_seq TO ssbd03mow;

-- Table permissions for MOW --
GRANT SELECT
    ON accounts TO ssbd03mow;

GRANT SELECT
    ON access_levels TO ssbd03mow;

GRANT SELECT
    ON administrators TO ssbd03mow;

GRANT SELECT
    ON moderators TO ssbd03mow;

GRANT SELECT
    ON business_workers TO ssbd03mow;

GRANT SELECT
    ON clients TO ssbd03mow;



GRANT SELECT
    ON clients TO ssbd03mow;



GRANT SELECT
    ON alter_types TO ssbd03mow;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON attractions TO ssbd03mow;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON companies TO ssbd03mow;

GRANT SELECT, INSERT, DELETE, UPDATE
    ON cruise_addresses TO ssbd03mow;

GRANT SELECT, INSERT, DELETE, UPDATE
    ON cruise_pictures TO ssbd03mow;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON cruises TO ssbd03mow;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON cruises_groups TO ssbd03mow;

GRANT SELECT, INSERT, DELETE, UPDATE
    ON cruises_group_pictures TO ssbd03mow;

GRANT SELECT
    ON language_types TO ssbd03mok;

GRANT SELECT
    ON language_types TO ssbd03mow;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON ratings TO ssbd03mow;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON reservations TO ssbd03mow;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON reservation_attractions TO ssbd03mow;

GRANT SELECT, UPDATE
    ON used_tokens_id_seq TO ssbd03mok;

GRANT SELECT, UPDATE
    ON used_codes_id_seq TO ssbd03mok;

GRANT SELECT, UPDATE
    ON cruise_addresses_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON cruise_pictures_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON companies_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON cruises_groups_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON cruises_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON attractions_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON reservations_id_seq TO ssbd03mow;

GRANT SELECT, UPDATE
    ON ratings_id_seq TO ssbd03mow;



create definer = root@`%` view glassfish_auth_view as
select `ssbd03`.`accounts`.`login`             AS `login`,
`ssbd03`.`accounts`.`password_hash`     AS `password_hash`,
`ssbd03`.`access_levels`.`access_level` AS `access_level`
from ((`ssbd03`.`accounts` join `ssbd03`.`access_levels` on ((`ssbd03`.`accounts`.`id` = `ssbd03`.`access_levels`.`account_id`)))
left join `ssbd03`.`business_workers` `bw` on ((`ssbd03`.`access_levels`.`id` = `bw`.`id`)))
where ((0 <> `ssbd03`.`accounts`.`confirmed`) and (0 <> `ssbd03`.`accounts`.`active`) and
(0 <> `ssbd03`.`access_levels`.`enabled`) and ((0 <> `bw`.`confirmed`) or (`bw`.`confirmed` is null)));

GRANT SELECT ON glassfish_auth_view TO ssbd03glassfish;
