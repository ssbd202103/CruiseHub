-- accounts start ||| password is 12345678
insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active,
                     last_incorrect_authentication_date_time, last_incorrect_authentication_logical_address,
                     last_correct_authentication_date_time, last_correct_authentication_logical_address,
                     language_type,
                     creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-1, 'Richard', 'Branson', 'rbranson', 'rbranson@gmail.com',
        '2634c3097f98e36865f0c572009c4ffd73316bc8b88ccfe8d196af35f46e2394',
        true, true, null, null, null, null, 'pl', now(), now(), 'INSERT', -1, -1, 0);

insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active,
                     last_incorrect_authentication_date_time, last_incorrect_authentication_logical_address,
                     last_correct_authentication_date_time, last_correct_authentication_logical_address,
                     language_type,
                     creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-2, 'Elon', 'Musk', 'emusk', 'emusk@gmail.com',
        '2634c3097f98e36865f0c572009c4ffd73316bc8b88ccfe8d196af35f46e2394',
        true, true, null, null, null, null, 'pl', now(), now(), 'INSERT', -2, -2, 0);

insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active,
                     last_incorrect_authentication_date_time, last_incorrect_authentication_logical_address,
                     last_correct_authentication_date_time, last_correct_authentication_logical_address,
                     language_type,
                     creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-3, 'Jeff', 'Bezos', 'jbezos', 'jbezos@gmail.com',
        '2634c3097f98e36865f0c572009c4ffd73316bc8b88ccfe8d196af35f46e2394',
        true, true, null, null, null, null, 'pl', now(), now(), 'INSERT', -3, -3, 0);

insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active,
                     last_incorrect_authentication_date_time, last_incorrect_authentication_logical_address,
                     last_correct_authentication_date_time, last_correct_authentication_logical_address,
                     language_type,
                     creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-4, 'Mark', 'Zuckerberg', 'mzuckerberg', 'mzuckerberg@gmail.com',
        '2634c3097f98e36865f0c572009c4ffd73316bc8b88ccfe8d196af35f46e2394',
        true, true, null, null, null, null, 'pl', now(), now(), 'INSERT', -4, -4, 0);
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
insert into administrators (id, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-1, now(), now(), 'INSERT', -1, -1, 0);
-- administrators end
--
--
--
-- business_workers start
insert into business_workers (id, phone_number, creation_date_time, last_alter_date_time, alter_type,
                              created_by_id, altered_by_id, version)
values (-4, '0987654321', now(), now(), 'INSERT', -3, -3, 0);
-- business_workers end
--
--
--
-- addresses start
insert into addresses(id, house_number, street, postal_code, city, country,
                      creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-1, 1, 'street 1', '123', 'London', 'United Kingdom', now(), now(), 'INSERT', -2, -2, 0);

insert into addresses(id, house_number, street, postal_code, city, country,
                      creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-2, 1, 'street 2', '321', 'Manchester', 'United Kingdom', now(), now(), 'INSERT', -3, -3, 0);
-- addresses end
--
--
--
-- clients start
insert into clients(id, phone_number, home_address_id,
                    creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-2, '123456789', -1, now(), now(), 'INSERT', -2, -2, 0);

insert into clients(id, phone_number, home_address_id, creation_date_time, last_alter_date_time, alter_type,
                    created_by_id, altered_by_id, version)
values (-3, '123456789', -2, now(), now(), 'INSERT', -3, -3, 0);
-- clients end
--
--
--
-- moderators start
insert into moderators(id, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                       altered_by_id, version)
values (-5, now(), now(), 'INSERT', -4, -4, 0);
-- moderators end
--
--
--companies start
insert into companies(id, name, address, phone_number, NIP, owner, worker_id)
values (-1,'FirmaJez','Zakopane','777876542',232332323220, 'Jezy Musial', -4);
--companies end
--
--
--
--cruises_groups start
insert into cruises_groups(id, company_id, name, number_of_seats, price)
values (-1,-1,'Przygoda morska', 24, 52);
--cruises_groups end
--
--
--
--cruises start
insert into cruises(id, start_date, end_date, active, description, start_place, cruises_groups_id, attractions)
values (-1,now(),now(),true,'opis','miejsce poczatku', -1, -1)
--cruises end
--
--
--
--atractions start
insert into atractions(id,name,description,price,number_of_seats)
values (-1,'atrakcja','opis atrakcji',123,20);
--atractions end
--
--
--
--reservation start
insert into reservations(id,client_id,number_of_seats,atraction_id,cruise_id)
values (-1,-2,2,-1,-1);
--reservation end
--
--
--
--ratings start
insert into ratings(id, account_id, cruise_id, rating)
values (-1, -2, -1, 3);

insert into ratings(id, account_id, cruise_id, rating)
values (-2, -3, -1, 5);
--ratings end
--
--
--
--comments start
insert into comments (id, account_id, cruise_id, comment)
values (-1, -2, -1, "Not bad. Lorem ipsum dolor sit amet is weird");

insert into comments (id, account_id, cruise_id, comment)
values (-2, -3, -1, "It's one of the beatufiul travel in my life");
--comments end
--
--
--
--positionings start
insert into positionings (id, type, cruises_group_id, end_date)
values (-1, true, -1, DATEADD(day, 30, now()));
--positionings end