-- V13__add_recipe_source.sql
-- Recipe 테이블에 출처(source) 컬럼 추가
--
-- 변경 사항:
-- 1. recipe 테이블에 source 컬럼 추가 (VARCHAR(500), nullable)

-- =========================================
-- recipe 테이블에 source 컬럼 추가
-- =========================================
alter table recipe
  add column source varchar(500);

-- 출처 검색을 위한 인덱스 추가 (선택사항)
create index ix_recipe_source on recipe(source) where source is not null;

