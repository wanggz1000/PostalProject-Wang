create sequence letter_seq increment by 50;

create sequence package_seq increment by 50;

create table letter
(
    id      BIGSERIAL not null primary key,
    country varchar(255),
    name    varchar(255),
    status  varchar(255) default 'waiting'
);

create table package
(
    id     BIGSERIAL        not null primary key,
    name   varchar(255),
    weight double precision not null,
    status varchar(255) default 'waiting'
);

