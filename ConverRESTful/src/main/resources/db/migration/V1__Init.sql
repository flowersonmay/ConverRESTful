create table information
(
    id         integer generated by default as identity
        primary key,
    username   varchar(100),
    input      varchar(150),
    output     varchar(150),
    created_at timestamp
);

create table users
(
    id       integer generated by default as identity primary key,
    username varchar(100),
    password varchar(100)
);