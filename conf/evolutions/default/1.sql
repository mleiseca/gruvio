# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table groove (
  id                        varchar(255) not null,
  name                      varchar(255),
  description               varchar(255),
  motivation                varchar(255),
  verification_mechanism    varchar(255),
  verification_frequency    varchar(255),
  last_verification         timestamp,
  created_at                timestamp,
  local_user_id             varchar(255) not null,
  constraint pk_groove primary key (id))
;

create table groove_verification_check (
  id                        varchar(255) not null,
  groove_id                 varchar(255),
  checked_at                timestamp,
  check_was_successful      boolean,
  constraint pk_groove_verification_check primary key (id))
;

create table local_token (
  uuid                      varchar(255) not null,
  email                     varchar(255),
  created_at                timestamp,
  expire_at                 timestamp,
  is_sign_up                boolean,
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

create sequence groove_seq;

create sequence groove_verification_check_seq;

create sequence local_token_seq;

create sequence local_user_seq;




# --- !Downs

drop table if exists groove cascade;

drop table if exists groove_verification_check cascade;

drop table if exists local_token cascade;

drop table if exists local_user cascade;

drop sequence if exists groove_seq;

drop sequence if exists groove_verification_check_seq;

drop sequence if exists local_token_seq;

drop sequence if exists local_user_seq;

