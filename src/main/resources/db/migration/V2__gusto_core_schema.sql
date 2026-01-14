-- V2__gusto_core_schema.sql
-- From V1:
--   ingredient(id, name, created_at)
--   ingredient_link(id, from_ingredient_id, to_ingredient_id, link_type, note, created_at)
-- To V2:
--   ingredient(ingredient_id, thumbnail_url, is_active, created_at, updated_at)
--   ingredient_i18n, ingredient_alias
--   ingredient_edge(+ enums), ingredient_edge_evidence
--   ingredient_collection, ingredient_collection_item
--
-- This migration tries to preserve existing data.

-- =========================================
-- 0) Helper trigger: auto update updated_at
-- =========================================
create or replace function set_updated_at()
returns trigger as $$
begin
  new.updated_at = now();
  return new;
end;
$$ language plpgsql;

-- =========================================
-- 1) ingredient: rename PK + add new columns
-- =========================================
alter table ingredient rename column id to ingredient_id;

alter table ingredient
  add column updated_at timestamptz not null default now(),
  add column thumbnail_url text,
  add column is_active boolean not null default true;

-- ingredient.name -> ingredient_i18n 로 옮길 거라서
-- 우선 i18n 테이블을 만들고 데이터를 이관한 뒤 name 컬럼을 제거한다.

-- updated_at trigger
create trigger trg_ingredient_updated_at
before update on ingredient
for each row execute function set_updated_at();

-- =========================================
-- 2) ingredient_i18n: create + migrate name
-- =========================================
create table ingredient_i18n (
  ingredient_id bigint not null references ingredient(ingredient_id) on delete cascade,
  locale varchar(10) not null,
  name varchar(100) not null,
  description text,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),
  primary key (ingredient_id, locale)
);

create trigger trg_ingredient_i18n_updated_at
before update on ingredient_i18n
for each row execute function set_updated_at();

-- 기본 locale은 일단 ko-KR로 가정(원하면 en-GB 등으로 바꿔도 됨)
insert into ingredient_i18n (ingredient_id, locale, name, description, created_at, updated_at)
select ingredient_id, 'ko-KR', name, null, created_at, now()
from ingredient;

create unique index ux_ingredient_i18n_locale_name
  on ingredient_i18n(locale, name);

create index ix_ingredient_i18n_locale_name
  on ingredient_i18n(locale, name);

-- 이제 ingredient.name은 제거 (의존성/인덱스가 있으면 같이 제거)
alter table ingredient drop column name;

-- =========================================
-- 3) ingredient_alias (i18n)
-- =========================================
create table ingredient_alias (
  alias_id bigserial primary key,
  ingredient_id bigint not null references ingredient(ingredient_id) on delete cascade,
  locale varchar(10) not null,
  alias varchar(100) not null,
  created_at timestamptz not null default now(),
  unique (ingredient_id, locale, alias)
);

create index ix_alias_locale_alias
  on ingredient_alias(locale, alias);

-- =========================================
-- 4) Enums
-- =========================================
do $$ begin
  create type ingredient_relation_type as enum ('PAIR_WELL', 'AVOID', 'NEUTRAL');
exception when duplicate_object then null;
end $$;

do $$ begin
  create type edge_evidence_type as enum ('NOTE', 'BOOK', 'VIDEO', 'EXPERIMENT', 'RECIPE_REFERENCE', 'LINK');
exception when duplicate_object then null;
end $$;

-- =========================================
-- 5) ingredient_edge: create + migrate from ingredient_link
-- =========================================
create table ingredient_edge (
  edge_id bigserial primary key,
  from_ingredient_id bigint not null references ingredient(ingredient_id),
  to_ingredient_id bigint not null references ingredient(ingredient_id),
  relation_type ingredient_relation_type not null,
  score numeric(4,3) not null,
  confidence numeric(4,3),
  reason_summary varchar(255),
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),

  constraint ck_edge_order check (from_ingredient_id < to_ingredient_id),
  constraint ux_edge_pair unique (from_ingredient_id, to_ingredient_id),
  constraint ck_edge_score_range check (score >= -1.000 and score <= 1.000),
  constraint ck_edge_confidence_range check (confidence is null or (confidence >= 0.000 and confidence <= 1.000))
);

create trigger trg_ingredient_edge_updated_at
before update on ingredient_edge
for each row execute function set_updated_at();

create index ix_edge_from on ingredient_edge(from_ingredient_id);
create index ix_edge_to on ingredient_edge(to_ingredient_id);
create index ix_edge_type_score on ingredient_edge(relation_type, score desc);

-- V1의 ingredient_link를 V2의 ingredient_edge로 이관
-- - 무방향 그래프를 위해 (min, max)로 정규화
-- - link_type GOOD/BAD/NEUTRAL -> relation_type
-- - score는 임시로 GOOD=0.700, BAD=-0.700, NEUTRAL=0.000
-- - 동일 pair에 link_type이 여러 개면 우선순위: BAD > GOOD > NEUTRAL
insert into ingredient_edge (
  from_ingredient_id,
  to_ingredient_id,
  relation_type,
  score,
  confidence,
  reason_summary,
  created_at,
  updated_at
)
select distinct on (a, b)
  a,
  b,
  rt,
  sc,
  null::numeric(4,3),
  rs,
  created_at,
  now()
from (
  select
    least(from_ingredient_id, to_ingredient_id) as a,
    greatest(from_ingredient_id, to_ingredient_id) as b,
    case upper(link_type)
      when 'GOOD' then 'PAIR_WELL'::ingredient_relation_type
      when 'BAD' then 'AVOID'::ingredient_relation_type
      else 'NEUTRAL'::ingredient_relation_type
    end as rt,
    case upper(link_type)
      when 'GOOD' then 0.700::numeric(4,3)
      when 'BAD' then (-0.700)::numeric(4,3)
      else 0.000::numeric(4,3)
    end as sc,
    left(coalesce(note, ''), 255) as rs,
    created_at,
    case upper(link_type)
      when 'BAD' then 1
      when 'GOOD' then 2
      else 3
    end as prio
  from ingredient_link
) x
order by a, b, prio;

-- V1 테이블/인덱스 정리
drop table ingredient_link;

-- =========================================
-- 6) ingredient_edge_evidence
-- =========================================
create table ingredient_edge_evidence (
  evidence_id bigserial primary key,
  edge_id bigint not null references ingredient_edge(edge_id) on delete cascade,
  evidence_type edge_evidence_type not null,
  title varchar(200),
  content text,
  source_ref text,
  created_at timestamptz not null default now()
);

create index ix_evidence_edge on ingredient_edge_evidence(edge_id);
create index ix_evidence_type on ingredient_edge_evidence(evidence_type);

-- =========================================
-- 7) Collections
-- =========================================
create table ingredient_collection (
  collection_id bigserial primary key,
  title varchar(120) not null,
  note text,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create trigger trg_collection_updated_at
before update on ingredient_collection
for each row execute function set_updated_at();

create table ingredient_collection_item (
  collection_item_id bigserial primary key,
  collection_id bigint not null references ingredient_collection(collection_id) on delete cascade,
  ingredient_id bigint not null references ingredient(ingredient_id),
  added_order int,
  created_at timestamptz not null default now(),
  unique (collection_id, ingredient_id)
);

create index ix_collection_item_collection
  on ingredient_collection_item(collection_id);

create index ix_collection_item_ingredient
  on ingredient_collection_item(ingredient_id);
