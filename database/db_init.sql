-- language_types start
insert into language_types(id, language_type_value)
values (-1, 'pl');

insert into language_types(id, language_type_value)
values (-2, 'en');
-- language_types end
--
--
--
-- alter_types start
insert into alter_types(id, alter_type_name)
values (-1, 'UPDATE');

insert into alter_types(id, alter_type_name)
values (-2, 'CREATE');

insert into alter_types(id, alter_type_name)
values (-3, 'DELETE');
-- alter_types end--
--
--
--
-- entity_details start
insert into entity_details (id, version, creation_datetime, last_alter_datetime, alter_type_id)
values (-1, 0, now(), now(), -1);

insert into entity_details (id, version, creation_datetime, last_alter_datetime, alter_type_id)
values (-2, 0, now(), now(), -1);

insert into entity_details (id, version, creation_datetime, last_alter_datetime, alter_type_id)
values (-3, 0, now(), now(), -1);

insert into entity_details (id, version, creation_datetime, last_alter_datetime, alter_type_id)
values (-4, 0, now(), now(), -1);

insert into entity_details (id, version, creation_datetime, last_alter_datetime, alter_type_id)
values (-5, 0, now(), now(), -1);

insert into entity_details (id, version, creation_datetime, last_alter_datetime, alter_type_id)
values (-6, 0, now(), now(), -1);

insert into entity_details (id, version, creation_datetime, last_alter_datetime, alter_type_id)
values (-7, 0, now(), now(), -1);

insert into entity_details (id, version, creation_datetime, last_alter_datetime, alter_type_id)
values (-8, 0, now(), now(), -1);

insert into entity_details (id, version, creation_datetime, last_alter_datetime, alter_type_id)
values (-9, 0, now(), now(), -1);

insert into entity_details (id, version, creation_datetime, last_alter_datetime, alter_type_id)
values (-10, 0, now(), now(), -1);

insert into entity_details (id, version, creation_datetime, last_alter_datetime, alter_type_id)
values (-11, 0, now(), now(), -1);
-- entity_details end
--
--
--
-- accounts start ||| password is 12345678
insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active,
                     last_incorrect_authentication_datetime, last_incorrect_authentication_logical_address,
                     last_correct_authentication_datetime, last_correct_authentication_logical_address,
                     entity_details_id, language_type_id)
values (-1, 'Richard', 'Branson', 'rbranson', 'rbranson@gmail.com',
        '2634c3097f98e36865f0c572009c4ffd73316bc8b88ccfe8d196af35f46e2394',
        true, true, null, null, null, null, -1, -1);

insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active,
                     last_incorrect_authentication_datetime, last_incorrect_authentication_logical_address,
                     last_correct_authentication_datetime, last_correct_authentication_logical_address,
                     entity_details_id, language_type_id)
values (-2, 'Elon', 'Musk', 'emusk', 'emusk@gmail.com',
        '2634c3097f98e36865f0c572009c4ffd73316bc8b88ccfe8d196af35f46e2394',
        true, true, null, null, null, null, -2, -1);

insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active,
                     last_incorrect_authentication_datetime, last_incorrect_authentication_logical_address,
                     last_correct_authentication_datetime, last_correct_authentication_logical_address,
                     entity_details_id, language_type_id)
values (-3, 'Jeff', 'Bezos', 'jbezos', 'jbezos@gmail.com',
        '2634c3097f98e36865f0c572009c4ffd73316bc8b88ccfe8d196af35f46e2394',
        true, true, null, null, null, null, -3, -1);


insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active,
                     last_incorrect_authentication_datetime, last_incorrect_authentication_logical_address,
                     last_correct_authentication_datetime, last_correct_authentication_logical_address,
                     entity_details_id, language_type_id)
values (-4, 'Mark', 'Zuckerberg', 'mzuckerberg', 'mzuckerberg@gmail.com',
        '2634c3097f98e36865f0c572009c4ffd73316bc8b88ccfe8d196af35f46e2394',
        true, true, null, null, null, null, -4, -1);
-- accounts end
--
--
--
-- access_levels start
insert into access_levels(id, access_level, account_id, active)
values (-1, 'administrator', -1, true);

insert into access_levels(id, access_level, account_id, active)
values (-2, 'client', -1, true);

insert into access_levels(id, access_level, account_id, active)
values (-3, 'client', -2, true);

insert into access_levels(id, access_level, account_id, active)
values (-4, 'business_worker', -3, true);

insert into access_levels(id, access_level, account_id, active)
values (-5, 'moderator', -4, false);
-- access_levels end
--
--
--
-- administrators start
insert into administrators (id, entity_details_id)
values (-1, -5);
-- administrators end
--
--
--
-- business_workers start
insert into business_workers (id, entity_details_id, phone_number)
values (-4, -6, '0987654321');
-- business_workers end
--
--
--
-- addresses start
insert into addresses(id, house_number, street, post_code, city, country, entity_details_id)
values (-1, 1, 'street 1', '123', 'London', 'United Kingdom', -7);

insert into addresses(id, house_number, street, post_code, city, country, entity_details_id)
values (-2, 1, 'street 2', '321', 'Manchester', 'United Kingdom', -8);
-- addresses end
--
--
--
-- clients start
insert into clients(id, entity_details_id, phone_number, home_address_id)
values (-2, -9, '123456789', -1);

insert into clients(id, entity_details_id, phone_number, home_address_id)
values (-3, -10, '123456789', -2);
-- clients end
--
--
--
-- moderators start
insert into moderators(id, entity_details_id)
values (-5, -11)
-- moderators end
