-- V7__rename_user_to_mem_gusto.sql
-- Rename user table to mem_gusto to avoid PostgreSQL reserved word conflict

-- Rename table
ALTER TABLE "user" RENAME TO mem_gusto;

-- Rename indexes
ALTER INDEX ix_user_user_num RENAME TO ix_mem_gusto_user_num;
ALTER INDEX ix_user_email RENAME TO ix_mem_gusto_email;
ALTER INDEX ix_user_role RENAME TO ix_mem_gusto_role;
ALTER INDEX ix_user_is_active RENAME TO ix_mem_gusto_is_active;
ALTER INDEX ix_user_refresh_token RENAME TO ix_mem_gusto_refresh_token;

