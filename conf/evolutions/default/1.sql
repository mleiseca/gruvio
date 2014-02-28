# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table local_token (
  uuid                      varchar(255) not null,
  email                     varchar(255),
  created_at                datetime,
  expire_at                 datetime,
  is_sign_up                tinyint(1) default 0,
  constraint pk_local_token primary key (uuid))
;

create table local_user (
  id                        varchar(255) not null,
  name                      varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  provider                  varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  constraint pk_local_user primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table local_token;

drop table local_user;

SET FOREIGN_KEY_CHECKS=1;

