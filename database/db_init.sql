--language_type start
--language_type 1
insert into language_type(id, language_type)
values (-1,'PL');
--language_type 2
insert into language_type(id, language_type)
values (-2,'ENG');
--language_type end

--alter_type start
--alter_type 1
insert into alter_type(id, alter_type)
values (-1,'UPDATE');
--alter_type 2
insert into alter_type(id, alter_type)
values (-2,'INSERT');
--alter_type 3
insert into alter_type(id, alter_type)
values (-3,'DELETE');
--alter_type end

-- accounts start ||| password is 12345678
insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active,
                     last_incorrect_authentication_date_time, last_incorrect_authentication_logical_address,
                     last_correct_authentication_date_time, last_correct_authentication_logical_address,
                     language_type_id,
                     creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-1, 'Richard', 'Branson', 'rbranson', 'rbranson@gmail.com',
        '2634c3097f98e36865f0c572009c4ffd73316bc8b88ccfe8d196af35f46e2394',
        true, true, null, null, null, null, -1, now(), now(), -2, -1, -1, 0);

insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active,
                     last_incorrect_authentication_date_time, last_incorrect_authentication_logical_address,
                     last_correct_authentication_date_time, last_correct_authentication_logical_address,
                     language_type_id,
                     creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-2, 'Elon', 'Musk', 'emusk', 'emusk@gmail.com',
        '2634c3097f98e36865f0c572009c4ffd73316bc8b88ccfe8d196af35f46e2394',
        true, true, null, null, null, null, -1, now(), now(), -2, -2, -2, 0);

insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active,
                     last_incorrect_authentication_date_time, last_incorrect_authentication_logical_address,
                     last_correct_authentication_date_time, last_correct_authentication_logical_address,
                     language_type_id,
                     creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-3, 'Jeff', 'Bezos', 'jbezos', 'jbezos@gmail.com',
        '2634c3097f98e36865f0c572009c4ffd73316bc8b88ccfe8d196af35f46e2394',
        true, true, null, null, null, null, -1, now(), now(), -2, -3, -3, 0);

insert into accounts(id, first_name, second_name, login, email, password_hash, confirmed, active,
                     last_incorrect_authentication_date_time, last_incorrect_authentication_logical_address,
                     last_correct_authentication_date_time, last_correct_authentication_logical_address,
                     language_type_id,
                     creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-4, 'Mark', 'Zuckerberg', 'mzuckerberg', 'mzuckerberg@gmail.com',
        '2634c3097f98e36865f0c572009c4ffd73316bc8b88ccfe8d196af35f46e2394',
        true, true, null, null, null, null, -1, now(), now(), -2, -4, -4, 0);
-- accounts end
--
--
--
-- access_levels start
insert into access_levels (id, access_level, account_id, enabled, creation_date_time, last_alter_date_time,
                           created_by_id, altered_by_id, alter_type, version)
values (-1, 'Administrator', -1, true, now(), now(), -1, -1, -2, 0);

insert into access_levels (id, access_level, account_id, enabled, creation_date_time, last_alter_date_time,
                           created_by_id, altered_by_id, alter_type, version)
values (-2, 'Client', -1, true, now(), now(), -2, -2, -2, 0);

insert into access_levels (id, access_level, account_id, enabled, creation_date_time, last_alter_date_time,
                           created_by_id, altered_by_id, alter_type, version)
values (-3, 'Client', -2, true, now(), now(), -3, -3, -2, 0);

insert into access_levels (id, access_level, account_id, enabled, creation_date_time, last_alter_date_time,
                           created_by_id, altered_by_id, alter_type, version)
values (-4, 'BusinessWorker', -3, true, now(), now(), -3, -3, -2, 0);

insert into access_levels (id, access_level, account_id, enabled, creation_date_time, last_alter_date_time,
                           created_by_id, altered_by_id, alter_type, version)
values (-5, 'Moderator', -4, false, now(), now(), -4, -4, -2, 0);
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
-- business_workers start
insert into business_workers (id, phone_number)
values (-4, '0987654321');
-- business_workers end
--
--
--
-- addresses start
insert into addresses(id, house_number, street, postal_code, city, country,
                      creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-1, 1, 'street 1', '123', 'London', 'United Kingdom', now(), now(), -2, -2, -2, 0);

insert into addresses(id, house_number, street, postal_code, city, country,
                      creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-2, 1, 'street 2', '321', 'Manchester', 'United Kingdom', now(), now(), -2, -3, -3, 0);
-- addresses end
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
insert into cruise_addresses(id, street, street_number, harbor_name,  city, country,
                      creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-1, 'street Company', '321', 'ManchesterHabor', 'Manchester', 'United Kingdom', now(), now(), -2, -3, -3, 0);
insert into cruise_addresses(id, street, street_number, harbor_name,  city, country,
                      creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-2, 'Grove Street', '420', 'FamillyHarbor', 'Los Santos', 'USA', now(), now(), -2, -3, -3, 0);
insert into cruise_addresses(id, street, street_number, harbor_name,  city, country,
                      creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-3, 'Ballas', '60', 'PurpleHarBor', 'Idlewood', 'USA', now(), now(), -2, -3, -3, 0);
insert into cruise_addresses(id, street, street_number, harbor_name,  city, country,
                      creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-4, 'Rancho', '88', 'TackoHarbor', 'Las Colinas', 'USA', now(), now(), -2, -3, -3, 0);
--cruise_addresses end
--
--
--
/*--cruise_pictures start
insert into cruise_pictures(id, img_name, img, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-1, 'LowRidder.jpg', null, now(), now(), -2, -1, -1, 0);
insert into cruise_pictures(id, img_name, img, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-2, 'kitty.jpg', null, now(), now(), -2, -1, -1, 0);
insert into cruise_pictures(id, img_name, img, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-3, 'PearlHarbort.jpg', null, now(), now(), -2, -1, -1, 0);
--cruise_pictures end*/
--
--
--
--commercial_type start
--commercial_type 1
insert into commercial_type(id, commercial_type)
values (-1,'None');
--commercial_type 2
insert into commercial_type(id, commercial_type)
values (-2,'Normal');
--commercial_type 3
insert into commercial_type(id, commercial_type)
values (-3,'Premium');
--commercial_type end
--
--
--

--companies start
insert into companies(id, name, address_id, phone_number, nip, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-1,'FirmaJez',-1 ,'777876542',1265485965, now(), now(), -2, -1, -1, 0);
insert into companies(id, name, address_id, phone_number, nip, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-2,'GroveStreetFamilly',-2 ,'777264542',2354685748, now(), now(), -2, -1, -1, 0);
insert into companies(id, name, address_id, phone_number, nip, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-3,'BeautifulCompany',-2 ,'756458542',9568545875, now(), now(), -2, -1, -1, 0);
--companies end
--
--
--
--cruises_groups start
insert into cruises_groups(id, company_id, name, number_of_seats, price, start_address_id, average_rating,
                            creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-1,-1,'Przygoda', 24, 52, -1, 2.5, now(), now(), -2, -1, -1, 0);
insert into cruises_groups(id, company_id, name, number_of_seats, price, start_address_id, average_rating,
                            creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-2,-3,'Santa Maria Beach', 42, 152, -2, 4.5, now(), now(), -2, -1, -1, 0);
insert into cruises_groups(id, company_id, name, number_of_seats, price, start_address_id, average_rating,
                            creation_date_time, last_alter_date_time, alter_type, created_by_id, altered_by_id, version)
values (-3,-2,'Beautiful Sandy Shores', 31, 502, -4, 5, now(), now(), -2, -1, -1, 0);
--cruises_groups end
--
--
--
--cruises start
insert into cruises(id, start_date, end_date, active, description, cruises_groups_id, available, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-1,now(),now(),true,'Beautiful tour', -1, true, now(), now(), -2, -1, -1, 0);
insert into cruises(id, start_date, end_date, active, description, cruises_groups_id, available, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-2,now(),now(),false,'Beautiful Beautiful tour', -2, true, now(), now(), -2, -1, -1, 0);
insert into cruises(id, start_date, end_date, active, description, cruises_groups_id, available, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-3,now(),now(),true,'The most Beautiful tour', -3, false, now(), now(), -2, -1, -1, 0);
--cruises end
--
--
--
--attractions start
insert into attractions(id,name,description,price,number_of_seats, available, cruise_id, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-1,'Bungee','Bungee jump',23,20,true, -1, now(), now(), -2, -1, -1, 0);
insert into attractions(id,name,description,price,number_of_seats, available, cruise_id, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-2,'Scuba Diving','Scuba diving in the sea',223,20,true, -2, now(), now(), -2, -1, -1, 0);
insert into attractions(id,name,description,price,number_of_seats, available, cruise_id, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-3,'Romantic dinner','Romantic dinner on the most beautiful Sandy Shores',1203,20,true, -3, now(), now(), -2, -1, -1, 0);
--attractions end
--
--
--
--reservation start
insert into reservations(id,client_id,number_of_seats,cruise_id, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-1,-2,2,-1, now(), now(), -2, -1, -1, 0);
insert into reservations(id,client_id,number_of_seats,cruise_id, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-2,-2,5,-2, now(), now(), -2, -1, -1, 0);
insert into reservations(id,client_id,number_of_seats,cruise_id, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-3,-2,1,-3, now(), now(), -2, -1, -1, 0);
--reservation end
--
--
--
--ratings start
insert into ratings(id, account_id, cruise_id, rating, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-1, -2, -1, 3, now(), now(), -2, -1, -1, 0);
insert into ratings(id, account_id, cruise_id, rating, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-2, -1, -2, 4.5, now(), now(), -2, -1, -1, 0);
insert into ratings(id, account_id, cruise_id, rating, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-3, -3, -3, 5, now(), now(), -2, -1, -1, 0);
--ratings end
--
--
--
--comments start
insert into comments (id, account_id, cruise_id, comment, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-1, -2, -1, 'Not bad. Lorem ipsum dolor sit amet is weird', now(), now(), -2, -1, -1, 0);
insert into comments (id, account_id, cruise_id, comment, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-2, -1, -2, 'It is one of the beatufiul travel in my life', now(), now(), -2, -1, -1, 0);
insert into comments (id, account_id, cruise_id, comment, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-3, -3, -3, 'It is the most beatufiul travel in the the Wolrd', now(), now(), -2, -1, -1, 0);
--comments end
--
--
--
--commercials start
insert into commercials (id, commercial_type_id, cruises_group_id, start_date, end_date, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-1, -1, -1, now(), now(), now(), now(), -2, -1, -1, 0);
insert into commercials (id, commercial_type_id, cruises_group_id, start_date, end_date, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-2, -2, -2, now(), now(), now(), now(), -2, -1, -1, 0);
insert into commercials (id, commercial_type_id, cruises_group_id, start_date, end_date, creation_date_time, last_alter_date_time, alter_type, created_by_id,
                            altered_by_id, version)
values (-3, -3, -3, now(), now(), now(), now(), -2, -1, -1, 0);
--commercials end
--
--
--
--company_workers start
insert into company_workers (id,companies_id, business_workers_id)
values (-1, -1, -4);
--company_workers end
--
--
--
--cruises_groups_pictures start
insert into cruises_groups_pictures (id, cruises_groups_id, cruise_pictures_id)
values (-1, -3,null);
insert into cruises_groups_pictures (id, cruises_groups_id, cruise_pictures_id)
values (-2, -2,null);
insert into cruises_groups_pictures (id, cruises_groups_id, cruise_pictures_id)
values (-3, -1,null);
--cruises_groups_pictures end
--
--
--
--reservations_attractions start
insert into reservations_attractions (id, reservations_id, attractions_id )
values (-1, -1, -1);
insert into reservations_attractions (id, reservations_id, attractions_id )
values (-2, -2, -2);
insert into reservations_attractions (id, reservations_id, attractions_id )
values (-3, -3, -3);
--reservations_attractions end
