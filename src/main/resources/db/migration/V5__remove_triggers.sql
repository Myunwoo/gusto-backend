-- V5__remove_triggers.sql
-- Remove all database triggers and replace with Java code
-- 
-- Strategy:
-- 1) Drop all triggers that auto-update updated_at
-- 2) Drop the set_updated_at() function
-- 3) Application code will handle updated_at updates using JPA Entity Listeners

-- =========================================
-- 1) Drop all triggers
-- =========================================

-- Drop ingredient trigger
drop trigger if exists trg_ingredient_updated_at on ingredient;

-- Drop ingredient_i18n trigger
drop trigger if exists trg_ingredient_i18n_updated_at on ingredient_i18n;

-- Drop ingredient_edge trigger
drop trigger if exists trg_ingredient_edge_updated_at on ingredient_edge;

-- Drop ingredient_collection trigger
drop trigger if exists trg_collection_updated_at on ingredient_collection;

-- =========================================
-- 2) Drop the helper function
-- =========================================
drop function if exists set_updated_at();

-- Note: updated_at columns remain in the tables.
-- They will be updated by Java code using JPA Entity Listeners (@PreUpdate).

