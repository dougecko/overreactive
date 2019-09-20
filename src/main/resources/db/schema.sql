drop table if exists account;

create table account
(
    id          serial primary key,
    name        varchar(255),
    type        varchar(50),
    balance     float,
    date_opened timestamp
);