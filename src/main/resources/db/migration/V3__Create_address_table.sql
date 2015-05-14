create table address (
    person_id int not null,
    location varchar(100) not null
);
insert into address (person_id, location) values (1, 'Japan');
insert into address (person_id, location) values (2, 'America');
