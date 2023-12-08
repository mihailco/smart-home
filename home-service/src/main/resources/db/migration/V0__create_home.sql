create table if not exists homes
(
    id      serial primary key,
    name    varchar(255) not null,
    address varchar(255)
);
