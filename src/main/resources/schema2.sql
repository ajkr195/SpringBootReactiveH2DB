create table IF NOT EXISTS todo (id SERIAL PRIMARY KEY, text varchar (255) not null, completed boolean default false);

insert into todo (id, text, completed) values ('5', 'My forth Todo Task !', TRUE) ;
insert into todo (id, text, completed) values ('6', 'My fifth Todo Task !', TRUE) ;
insert into todo (id, text, completed) values ('7', 'My sixth Todo Task !', TRUE) ;
insert into todo (id, text, completed) values ('8', 'My seventh Todo Task !', TRUE) ;
insert into todo (id, text, completed) values ('9', 'My eighth Todo Task !', TRUE) ;
insert into todo (id, text, completed) values ('10', 'My nineth Todo Task !', TRUE) ;
