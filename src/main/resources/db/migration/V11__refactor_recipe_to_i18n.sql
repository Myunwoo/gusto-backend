-- V11__refactor_recipe_to_i18n.sql
-- Recipe 테이블 리팩토링: 다국어 정보를 recipe_i18n 테이블로 분리
--
-- 변경 사항:
-- 1. recipe_i18n 테이블 생성 (description, instructions)
-- 2. 기존 recipe 테이블의 다국어 정보를 recipe_i18n으로 마이그레이션 (기본 locale: ko-KR)
-- 3. recipe 테이블에서 다국어 컬럼 제거 (description, instructions, servings, cook_time_minutes)

-- =========================================
-- 1) recipe_i18n 테이블 생성
-- =========================================
create table recipe_i18n (
  recipe_id bigint not null references recipe(recipe_id) on delete cascade,
  locale varchar(10) not null,
  description text,                    -- 레시피 설명
  instructions text,                   -- 조리 방법
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),
  primary key (recipe_id, locale)
);


-- 인덱스 생성
create index ix_recipe_i18n_locale on recipe_i18n(locale);
create index ix_recipe_i18n_recipe_id on recipe_i18n(recipe_id);

-- =========================================
-- 2) 기존 데이터 마이그레이션 (기본 locale: ko-KR)
-- =========================================
insert into recipe_i18n (
  recipe_id,
  locale,
  description,
  instructions,
  created_at,
  updated_at
)
select
  recipe_id,
  'ko-KR' as locale,
  description,
  instructions,
  created_at,
  updated_at
from recipe
where description is not null
   or instructions is not null;

-- =========================================
-- 3) recipe 테이블에서 다국어 컬럼 제거
-- =========================================
alter table recipe
  drop column if exists description,
  drop column if exists instructions,
  drop column if exists servings,
  drop column if exists cook_time_minutes;

