-- V15: Explore용 "가장 마인드맵을 넓게 펼칠 수 있는 재료" 저장 테이블
-- use_yn = 'Y'인 행은 하나만 유지. 갱신 시 신규 행 INSERT + 기존 use_yn = 'Y' → 'N' 처리.

CREATE TABLE explore_recommended_ingredient (
  id bigserial primary key,
  ingredient_id bigint not null references ingredient(ingredient_id) on delete cascade,
  use_yn char(1) not null default 'Y',
  created_at timestamptz not null default now(),
  constraint ck_explore_recommended_use_yn check (use_yn in ('Y', 'N'))
);

CREATE INDEX ix_explore_recommended_ingredient_use_yn
  ON explore_recommended_ingredient (use_yn);

CREATE INDEX ix_explore_recommended_ingredient_ingredient_id
  ON explore_recommended_ingredient (ingredient_id);

COMMENT ON TABLE explore_recommended_ingredient IS 'Explore 페이지 기본 중앙 재료(가장 마인드맵을 넓게 펼칠 수 있는 재료). use_yn=Y인 행 1개만 유효.';
