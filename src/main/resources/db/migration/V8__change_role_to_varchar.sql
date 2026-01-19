-- V8__change_role_to_varchar.sql
-- Change role column from user_role enum to varchar
-- Remove DB-level constraint and move validation to Java code

-- 1. Change role column type from user_role enum to varchar
ALTER TABLE mem_gusto 
  ALTER COLUMN role TYPE varchar(20) USING role::text;

-- 2. Set default value as string
ALTER TABLE mem_gusto 
  ALTER COLUMN role SET DEFAULT 'USER';

DROP TYPE IF EXISTS user_role;

