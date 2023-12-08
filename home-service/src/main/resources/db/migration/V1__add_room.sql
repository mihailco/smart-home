create table if not exists rooms
(
    id serial primary key,
    name varchar(255) not null,
    home_id int references homes(id) on delete cascade
)