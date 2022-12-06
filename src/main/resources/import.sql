-- USERS
insert into users(username, password) values('user1@mail.com','$2a$12$sitQVmDxNCN4I9X5UchZO.Scy3/ULMF1aSf5jDz90G0bK0QOrMMTC');
insert into users(username, password) values('user2@mail.com','$2a$12$sitQVmDxNCN4I9X5UchZO.Scy3/ULMF1aSf5jDz90G0bK0QOrMMTC');
insert into users(username, password) values('user3@mail.com','12345678');

-- FEDERATIONS 
insert into federations(name, acronym) values('Real Federación Española de Fútbol', 'RFEF');
insert into federations(name, acronym) values('Federazione Italiana Giuoco Calci', 'FIGC');
insert into federations(name, acronym) values('The Football Association', 'FA');
insert into federations(name, acronym) values('Fédération Française de Football', 'FFF');

-- CLUBS 
insert into clubs(official_name, popular_name, public_details, number_of_players) values('Real Madrid C.F.', 'Real Madrid', 1, 2);
insert into clubs(official_name, popular_name, public_details, number_of_players) values('Fútbol Club Barcelona', 'Barça', 0, 1);
insert into clubs(official_name, popular_name, public_details, number_of_players) values('Sevilla C.F.', 'Sevilla', 1, 0);

-- PLAYERS 
insert into players(given_name, family_name, nationality, email, date_of_birth, club_id) values('Daniel', 'Carvajal', 'Spanish', 'dcarvajal@gmail.com', '1980-01-01', 1);
insert into players(given_name, family_name, nationality, email, date_of_birth, club_id) values('Luka', 'Modric', 'Croatian', 'lmodric@gmail.com', '1980-01-01', 1); 
insert into players(given_name, family_name, nationality, email, date_of_birth, club_id) values('Sergio', 'Busquets', 'Spanish', 'sbusquets@gmail.com','1980-01-01', 2); 


update clubs set user_id = (select id from users where username = 'user1@mail.com') where official_name = 'Real Madrid C.F.';
update clubs set user_id = (select id from users where username = 'user2@mail.com') where official_name = 'Fútbol Club Barcelona';
update clubs set user_id = (select id from users where username = 'user3@mail.com') where official_name = 'Sevilla C.F.';

update clubs set federation_id = (select id from federations where acronym = 'RFEF') where official_name in('Real Madrid C.F.',  'Fútbol Club Barcelona', 'Sevilla C.F.');

-- ASSIG PLAYERS TO CLUBS
update players set club_id = (select id from clubs where official_name = 'Real Madrid C.F.') where family_name in ('Carvajal', 'Modric');
update players set club_id = (select id from clubs where official_name = 'Fútbol Club Barcelona') where family_name in ('Busquets');
