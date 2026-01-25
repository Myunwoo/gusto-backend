-- V10__change_enums_to_varchar.sql
-- Change ingredient_relation_type and edge_evidence_type from ENUM to VARCHAR
-- Remove DB-level constraint and move validation to Java code

-- =========================================
-- 1) ingredient_edge.relation_type: ENUM → VARCHAR
-- =========================================
ALTER TABLE ingredient_edge 
  ALTER COLUMN relation_type TYPE varchar(20) USING relation_type::text;

-- =========================================
-- 2) ingredient_edge_evidence.evidence_type: ENUM → VARCHAR
-- =========================================
ALTER TABLE ingredient_edge_evidence 
  ALTER COLUMN evidence_type TYPE varchar(50) USING evidence_type::text;

-- =========================================
-- 3) Drop ENUM types (no longer needed)
-- =========================================
DROP TYPE IF EXISTS ingredient_relation_type;
DROP TYPE IF EXISTS edge_evidence_type;

