-- V3__add_ingredient_name.sql
-- ingredient 테이블에 국문명(name) 칼럼 추가
-- 한국 서비스이므로 기본적으로 국문명을 관리

-- ingredient 테이블에 name 칼럼 추가
alter table ingredient
  add column name varchar(100);

-- 기존 데이터가 있는 경우, ingredient_i18n의 ko-KR 이름을 사용하여 채움
update ingredient i
set name = (
  select i18n.name
  from ingredient_i18n i18n
  where i18n.ingredient_id = i.ingredient_id
    and i18n.locale = 'ko-KR'
  limit 1
)
where exists (
  select 1
  from ingredient_i18n i18n
  where i18n.ingredient_id = i.ingredient_id
    and i18n.locale = 'ko-KR'
);

-- name 칼럼을 NOT NULL로 변경 (기존 데이터가 모두 채워진 후)
alter table ingredient
  alter column name set not null;

-- name 칼럼에 인덱스 추가 (검색 성능 향상)
create index ix_ingredient_name
  on ingredient(name);
