create table IF NOT EXISTS todo (id SERIAL PRIMARY KEY, text varchar (255) not null, completed boolean default false);
