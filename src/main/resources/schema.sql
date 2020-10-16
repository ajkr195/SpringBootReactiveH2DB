create table IF NOT EXISTS todo (id BIGINT AUTO_INCREMENT PRIMARY KEY, text varchar (255) not null, completed boolean default false);
