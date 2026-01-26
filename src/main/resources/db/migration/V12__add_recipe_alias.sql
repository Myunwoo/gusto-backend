-- V12__add_recipe_alias.sql
-- Recipe alias 테이블 추가 (검색 편의성을 위한 별칭 관리)
--
-- 변경 사항:
-- 1. recipe_alias 테이블 생성 (ingredient_alias와 유사한 구조)

-- =========================================
-- recipe_alias 테이블 생성
-- =========================================
create table recipe_alias (
  alias_id bigserial primary key,
  recipe_id bigint not null references recipe(recipe_id) on delete cascade,
  locale varchar(10) not null,
  alias varchar(100) not null,
  created_at timestamptz not null default now(),
  unique (recipe_id, locale, alias)
);

-- 검색 성능을 위한 인덱스
create index ix_recipe_alias_locale_alias
  on recipe_alias(locale, alias);

create index ix_recipe_alias_recipe_id
  on recipe_alias(recipe_id);

