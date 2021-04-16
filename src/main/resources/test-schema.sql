CREATE SCHEMA IF NOT EXISTS qa_music_project;
USE qa_music_project;

drop table if exists playlist_link CASCADE;
drop table if exists artist_album_link cascade;
drop table if exists playlist CASCADE;
drop table if exists user_details CASCADE;
drop table if exists genre CASCADE;
drop table if exists track CASCADE;
drop table if exists album CASCADE;
drop table if exists artist CASCADE;

CREATE TABLE album
(
    id    BIGINT AUTO_INCREMENT,
    cover VARCHAR(255) NOT NULL,
    name  VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE artist
(
    id   BIGINT AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE genre
(
    id          BIGINT AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(250) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE playlist
(
    id          BIGINT AUTO_INCREMENT,
    artwork     varchar(1000) not null,
    description varchar(500)  not null,
    name        varchar(100)  not null,
    creator_id  bigint,
    PRIMARY KEY (id)
);

CREATE TABLE playlist_link
(
    id          BIGINT AUTO_INCREMENT,
    playlist_id bigint,
    track_id    bigint,
    primary key (id)
);

CREATE TABLE track
(
    id       BIGINT AUTO_INCREMENT,
    duration integer      not null,
    lyrics   varchar(10000),
    name     varchar(100) not null,
    album_id bigint,
    genre_id bigint,
    primary key (id)
);

create table user_details
(
    id       BIGINT AUTO_INCREMENT,
    password varchar(255) not null,
    role     integer,
    username varchar(255) not null,
    primary key (id)
);

create table artist_album_link
(
    id        BIGINT AUTO_INCREMENT,
    album_id  bigint,
    artist_id bigint,
    primary key (id)
);

alter table artist
    add constraint UK_r03a96lqhsb7djq2tn4rq60vn unique (name);
alter table playlist
    add constraint UK_swyw77f2jscjvdd29t0s2jvfv unique (artwork);
alter table user_details
    add constraint UK_qqadnciq8gixe1qmxd0rj9cyk unique (username);
alter table artist_album_link
    add constraint FKbbm7xg59o4wbgjvqse79bx5y foreign key (album_id) references album (id) on delete cascade;
alter table artist_album_link
    add constraint FK681tbl6vj65yuwerjf727ru6b foreign key (artist_id) references artist (id) on delete cascade;
alter table playlist
    add constraint FKak4g1ceepsia8tqc4fnxoi3x0 foreign key (creator_id) references user_details (id);
alter table playlist_link
    add constraint FKbwxn3x5u77l78rc6e67wvosdf foreign key (playlist_id) references playlist (id) on delete cascade;
alter table playlist_link
    add constraint FK66j1rqspu3h5g9bdlvn41ais8 foreign key (track_id) references track (id) on delete cascade;
alter table track
    add constraint FKaxm9pbgk7ptorfyk3o6911q05 foreign key (album_id) references album (id) on delete cascade;
alter table track
    add constraint FKr70fomq4e11qau3l8k8eddcur foreign key (genre_id) references genre (id) on delete cascade;