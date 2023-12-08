create table if not exists user_table
(
    id       serial primary key,
    name     varchar(255) not null,
    username varchar(30) unique,
    password text
);

create table if not exists token
(
    id            serial primary key,
    access_token  text,
    refresh_token text,
    expired_at    timestamptz,
    user_id       integer references user_table (id) on delete cascade
);