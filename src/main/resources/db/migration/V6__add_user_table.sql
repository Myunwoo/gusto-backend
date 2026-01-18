-- V6__add_user_table.sql
-- User table for authentication and authorization

-- =========================================
-- 1) User role enum
-- =========================================
do $$ begin
  create type user_role as enum ('USER', 'ADMIN');
exception when duplicate_object then null;
end $$;

-- =========================================
-- 2) User table
-- =========================================
create table "user" (
  user_id bigserial primary key,
  user_num char(8) not null unique,
  email varchar(255) not null unique,
  password_hash varchar(255) not null,
  nickname varchar(100),
  role user_role not null default 'USER',
  is_active boolean not null default true,
  refresh_token varchar(500),
  refresh_token_expires_at timestamptz,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create index ix_user_user_num on "user"(user_num);
create index ix_user_email on "user"(email);
create index ix_user_role on "user"(role);
create index ix_user_is_active on "user"(is_active);
create index ix_user_refresh_token on "user"(refresh_token) where refresh_token is not null;

