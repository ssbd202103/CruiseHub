--language_types start
--language_types 1
insert into language_types(id, name)
values (-1, 'PL');
--language_types 2
insert into language_types(id, name)
values (-2, 'EN');
--language_types end

--alter_types start
--alter_types 1
insert into alter_types(id, name)
values (-1, 'UPDATE');
--alter_types 2
insert into alter_types(id, name)
values (-2, 'INSERT');
--alter_types 3
insert into alter_types(id, name)
values (-3, 'DELETE');
--alter_types end

-- accounts start ||| password is abcABC123*
insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active, dark_mode,
                     last_incorrect_authentication_date_time, last_incorrect_authentication_logical_address,
                     last_correct_authentication_date_time, last_correct_authentication_logical_address,
                     language_type_id,
                     creation_date_time, last_alter_date_time, alter_type_id, created_by_id, altered_by_id, version)
values (-1, 'Richard', 'Branson', 'rbranson', 'rbranson@gmail.com',
        '70417c6b38327eddd1f5ee5633d8b90ab06398ebb4461c8ebcdc5b22bf7a6578',
        true, true, false, null, null, null, null, -1, now(), now(), -2, -1, -1, 0);

insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active, dark_mode,
                     last_incorrect_authentication_date_time, last_incorrect_authentication_logical_address,
                     last_correct_authentication_date_time, last_correct_authentication_logical_address,
                     language_type_id,
                     creation_date_time, last_alter_date_time, alter_type_id, created_by_id, altered_by_id, version)
values (-2, 'Elon', 'Musk', 'emusk', 'emusk@gmail.com',
        '70417c6b38327eddd1f5ee5633d8b90ab06398ebb4461c8ebcdc5b22bf7a6578',
        true, true, false, null, null, null, null, -1, now(), now(), -2, -2, -2, 0);

insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active, dark_mode,
                     last_incorrect_authentication_date_time, last_incorrect_authentication_logical_address,
                     last_correct_authentication_date_time, last_correct_authentication_logical_address,
                     language_type_id,
                     creation_date_time, last_alter_date_time, alter_type_id, created_by_id, altered_by_id, version)
values (-3, 'Jeff', 'Bezos', 'jbezos', 'jbezos@gmail.com',
        '70417c6b38327eddd1f5ee5633d8b90ab06398ebb4461c8ebcdc5b22bf7a6578',
        true, true, false, null, null, null, null, -1, now(), now(), -2, -3, -3, 0);

insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active, dark_mode,
                     last_incorrect_authentication_date_time, last_incorrect_authentication_logical_address,
                     last_correct_authentication_date_time, last_correct_authentication_logical_address,
                     language_type_id,
                     creation_date_time, last_alter_date_time, alter_type_id, created_by_id, altered_by_id, version)
values (-4, 'Mark', 'Zuckerberg', 'mzuckerberg', 'mzuckerberg@gmail.com',
        '70417c6b38327eddd1f5ee5633d8b90ab06398ebb4461c8ebcdc5b22bf7a6578',
        true, true, false, null, null, null, null, -1, now(), now(), -2, -4, -4, 0);
-- accounts end
--
--
--
-- access_levels start
insert into access_levels (id, access_level, account_id, enabled, creation_date_time, last_alter_date_time,
                           created_by_id, altered_by_id, alter_type_id, version)
values (-1, 'Administrator', -1, true, now(), now(), -1, -1, -2, 0);

insert into access_levels (id, access_level, account_id, enabled, creation_date_time, last_alter_date_time,
                           created_by_id, altered_by_id, alter_type_id, version)
values (-2, 'Client', -1, true, now(), now(), -2, -2, -2, 0);

insert into access_levels (id, access_level, account_id, enabled, creation_date_time, last_alter_date_time,
                           created_by_id, altered_by_id, alter_type_id, version)
values (-3, 'Client', -2, true, now(), now(), -3, -3, -2, 0);

insert into access_levels (id, access_level, account_id, enabled, creation_date_time, last_alter_date_time,
                           created_by_id, altered_by_id, alter_type_id, version)
values (-4, 'BusinessWorker', -3, true, now(), now(), -3, -3, -2, 0);

insert into access_levels (id, access_level, account_id, enabled, creation_date_time, last_alter_date_time,
                           created_by_id, altered_by_id, alter_type_id, version)
values (-5, 'Moderator', -4, true, now(), now(), -4, -4, -2, 0);
-- access_levels end
--
--
--
-- administrators start
insert into administrators (id)
values (-1);
-- administrators end
--
--
--
-- addresses start
insert into addresses(id, house_number, street, postal_code, city, country,
                      creation_date_time, last_alter_date_time, alter_type_id, created_by_id, altered_by_id, version)
values (-1, '1', 'street', '30-300', 'London', 'United Kingdom', now(), now(), -2, -2, -2, 0);

insert into addresses(id, house_number, street, postal_code, city, country,
                      creation_date_time, last_alter_date_time, alter_type_id, created_by_id, altered_by_id, version)
values (-2, '1', 'street', '30-300', 'Manchester', 'United Kingdom', now(), now(), -2, -3, -3, 0);
-- addresses end
--
--
--
--companies start
insert into companies(id, name, address_id, phone_number, nip, creation_date_time, last_alter_date_time, alter_type_id,
                      created_by_id,
                      altered_by_id, version)
values (-1, 'FirmaJez', -1, '777876542', 1265485965, now(), now(), -2, -1, -1, 0);
insert into companies(id, name, address_id, phone_number, nip, creation_date_time, last_alter_date_time, alter_type_id,
                      created_by_id,
                      altered_by_id, version)
values (-2, 'GroveStreetFamilly', -2, '777264542', 2354685748, now(), now(), -2, -1, -1, 0);
insert into companies(id, name, address_id, phone_number, nip, creation_date_time, last_alter_date_time, alter_type_id,
                      created_by_id,
                      altered_by_id, version)
values (-3, 'BeautifulCompany', -2, '756458542', 9568545875, now(), now(), -2, -1, -1, 0);
--companies end
--
--
--
-- business_workers start
insert into business_workers (id, phone_number, company_id, confirmed)
values (-4, '0987654321', -2, true);
-- business_workers end
--
--
--
-- clients start
insert into clients(id, phone_number, home_address_id)
values (-2, '123456789', -1);

insert into clients(id, phone_number, home_address_id)
values (-3, '123456789', -2);
-- clients end
--
--
--
-- moderators start
insert into moderators(id)
values (-5);
-- moderators end
--
--
------------Insert Into MOW---------------
--
--
--cruise_addresses start
insert into cruise_addresses(id, street, street_number, harbor_name, city_name, country_name,
                             creation_date_time, last_alter_date_time, alter_type_id, created_by_id, altered_by_id,
                             version)
values (-1, 'street Company', '321', 'ManchesterHabor', 'Manchester', 'United Kingdom', now(), now(), -2, -3, -3, 0);
insert into cruise_addresses(id, street, street_number, harbor_name, city_name, country_name,
                             creation_date_time, last_alter_date_time, alter_type_id, created_by_id, altered_by_id,
                             version)
values (-2, 'Grove Street', '420', 'FamillyHarbor', 'Los Santos', 'USA', now(), now(), -2, -3, -3, 0);
insert into cruise_addresses(id, street, street_number, harbor_name, city_name, country_name,
                             creation_date_time, last_alter_date_time, alter_type_id, created_by_id, altered_by_id,
                             version)
values (-3, 'Ballas', '60', 'PurpleHarBor', 'Idlewood', 'USA', now(), now(), -2, -3, -3, 0);
insert into cruise_addresses(id, street, street_number, harbor_name, city_name, country_name,
                             creation_date_time, last_alter_date_time, alter_type_id, created_by_id, altered_by_id,
                             version)
values (-4, 'Rancho', '88', 'TackoHarbor', 'Las Colinas', 'USA', now(), now(), -2, -3, -3, 0);
--cruise_addresses end
--
--
--
/*--cruise_pictures start
insert into cruise_pictures(id, img_name, img, creation_date_time, last_alter_date_time, alter_type_id, created_by_id,
                            altered_by_id, version)
values (-1, 'LowRidder.jpg', null, now(), now(), -2, -1, -1, 0);
insert into cruise_pictures(id, img_name, img, creation_date_time, last_alter_date_time, alter_type_id, created_by_id,
                            altered_by_id, version)
values (-2, 'kitty.jpg', null, now(), now(), -2, -1, -1, 0);
insert into cruise_pictures(id, img_name, img, creation_date_time, last_alter_date_time, alter_type_id, created_by_id,
                            altered_by_id, version)
values (-3, 'PearlHarbort.jpg', null, now(), now(), -2, -1, -1, 0);
--cruise_pictures end*/
--
--
--
--cruises_groups start
insert into cruises_groups(id, company_id, name, number_of_seats, price, start_address_id, average_rating, active,
                           creation_date_time, last_alter_date_time, alter_type_id, created_by_id, altered_by_id,
                           version)
values (-1, -1, 'Przygoda', 24, 52, -1, 2.5, true, now(), now(), -2, -1, -1, 0);
insert into cruises_groups(id, company_id, name, number_of_seats, price, start_address_id, average_rating, active,
                           creation_date_time, last_alter_date_time, alter_type_id, created_by_id, altered_by_id,
                           version)
values (-2, -3, 'Santa Maria Beach', 42, 152, -2, 4.5, true, now(), now(), -2, -1, -1, 0);
insert into cruises_groups(id, company_id, name, number_of_seats, price, start_address_id, average_rating, active,
                           creation_date_time, last_alter_date_time, alter_type_id, created_by_id, altered_by_id,
                           version)
values (-3, -2, 'Beautiful Sandy Shores', 31, 502, -4, 5, true, now(), now(), -2, -1, -1, 0);
--cruises_groups end
--
--
--
--cruises start
insert into cruises(id, uuid, start_date, end_date, active, description, cruises_group_id, available,
                    creation_date_time,
                    last_alter_date_time, alter_type_id, created_by_id,
                    altered_by_id, version, published)
values (-1, '581d626f-d421-47dd-89ef-b41bc30aa36c', now(), now(), true, 'Beautiful tour', -1, true, now(), now(), -2,
        -1, -1, 0, true);
insert into cruises(id, uuid, start_date, end_date, active, description, cruises_group_id, available,
                    creation_date_time,
                    last_alter_date_time, alter_type_id, created_by_id,
                    altered_by_id, version, published)
values (-2, '181d626f-d421-47dd-89ef-b41bc30aa36c', now(), now(), false, 'Beautiful Beautiful tour', -2, true, now(),
        now(), -2, -1, -1, 0, true);
insert into cruises(id, uuid, start_date, end_date, active, description, cruises_group_id, available,
                    creation_date_time,
                    last_alter_date_time, alter_type_id, created_by_id,
                    altered_by_id, version, published)
values (-3, '481d626f-d421-47dd-89ef-b41bc30aa36c', now(), now(), true, 'The most Beautiful tour', -3, false, now(),
        now(), -2, -1, -1, 0, true);
--cruises end
--
--
--
--attractions start
insert into attractions(id, name, description, price, number_of_seats, has_free_spots, cruise_id, creation_date_time,
                        last_alter_date_time, alter_type_id, created_by_id,
                        altered_by_id, version)
values (-1, 'Bungee', 'Bungee jump', 23, 20, true, -1, now(), now(), -2, -1, -1, 0);
insert into attractions(id, name, description, price, number_of_seats, has_free_spots, cruise_id, creation_date_time,
                        last_alter_date_time, alter_type_id, created_by_id,
                        altered_by_id, version)
values (-2, 'Scuba Diving', 'Scuba diving in the sea', 223, 20, true, -2, now(), now(), -2, -1, -1, 0);
insert into attractions(id, name, description, price, number_of_seats, has_free_spots, cruise_id, creation_date_time,
                        last_alter_date_time, alter_type_id, created_by_id,
                        altered_by_id, version)
values (-3, 'Romantic dinner', 'Romantic dinner on the most beautiful Sandy Shores', 1203, 20, true, -3, now(), now(),
        -2, -1, -1, 0);
--attractions end
--
--
--
--reservation start
insert into reservations(id, uuid, client_id, number_of_seats, cruise_id, price, creation_date_time,
                         last_alter_date_time,
                         alter_type_id, created_by_id,
                         altered_by_id, version)
values (-1, 'ab3e9d82-bbac-11eb-8529-0242ac130003', -2, 2, -1, 50, now(), now(), -2, -1, -1, 0);
insert into reservations(id, uuid, client_id, number_of_seats, cruise_id, price, creation_date_time,
                         last_alter_date_time,
                         alter_type_id, created_by_id,
                         altered_by_id, version)
values (-2, 'ab3e9fd0-bbac-11eb-8529-0242ac130003', -2, 5, -2, 20, now(), now(), -2, -1, -1, 0);
insert into reservations(id, uuid, client_id, number_of_seats, cruise_id, price, creation_date_time,
                         last_alter_date_time,
                         alter_type_id, created_by_id,
                         altered_by_id, version)
values (-3, 'ab3ea0ca-bbac-11eb-8529-0242ac130003', -2, 1, -3, 30, now(), now(), -2, -1, -1, 0);
--reservation end
--
--
--
--ratings start
insert into ratings(id, account_id, cruise_group_id, rating, creation_date_time, last_alter_date_time, alter_type_id,
                    created_by_id,
                    altered_by_id, version)
values (-1, -2, -1, 3, now(), now(), -2, -1, -1, 0);
insert into ratings(id, account_id, cruise_group_id, rating, creation_date_time, last_alter_date_time, alter_type_id,
                    created_by_id,
                    altered_by_id, version)
values (-2, -1, -2, 4.5, now(), now(), -2, -1, -1, 0);
insert into ratings(id, account_id, cruise_group_id, rating, creation_date_time, last_alter_date_time, alter_type_id,
                    created_by_id,
                    altered_by_id, version)
values (-3, -3, -3, 5, now(), now(), -2, -1, -1, 0);
--ratings end
--
--
--
--comments start
insert into comments (id, account_id, cruise_id, comment, creation_date_time, last_alter_date_time, alter_type_id,
                      created_by_id,
                      altered_by_id, version)
values (-1, -2, -1, 'Not bad. Lorem ipsum dolor sit amet is weird', now(), now(), -2, -1, -1, 0);
insert into comments (id, account_id, cruise_id, comment, creation_date_time, last_alter_date_time, alter_type_id,
                      created_by_id,
                      altered_by_id, version)
values (-2, -1, -2, 'It is one of the beautiful travel in my life', now(), now(), -2, -1, -1, 0);
insert into comments (id, account_id, cruise_id, comment, creation_date_time, last_alter_date_time, alter_type_id,
                      created_by_id,
                      altered_by_id, version)
values (-3, -3, -3, 'It is the most beautiful travel in the the World', now(), now(), -2, -1, -1, 0);
--comments end
--
--
--
--
--cruises_group_pictures start
-- insert into cruises_group_pictures (cruises_group_id, cruise_picture_id)
-- values (-3,-1);
-- insert into cruises_group_pictures (cruises_group_id, cruise_picture_id)
-- values (-2,-2);
--insert into cruises_group_pictures (cruises_group_id, cruise_picture_id)
--values (-1,-3);
--cruises_group_pictures end
--
--
--
--reservation_attractions start
insert into reservation_attractions (reservation_id, attraction_id)
values (-1, -1);
insert into reservation_attractions (reservation_id, attraction_id)
values (-2, -2);
insert into reservation_attractions (reservation_id, attraction_id)
values (-3, -3);
--reservation_attractions end
