CREATE TABLE ingredient (
  id          BIGSERIAL PRIMARY KEY,
  name        VARCHAR(100) NOT NULL UNIQUE,
  created_at  TIMESTAMPTZ  NOT NULL DEFAULT now()
);

-- 재료 간 연결(궁합/비궁합/설명)
CREATE TABLE ingredient_link (
  id              BIGSERIAL PRIMARY KEY,
  from_ingredient_id BIGINT NOT NULL REFERENCES ingredient(id) ON DELETE CASCADE,
  to_ingredient_id   BIGINT NOT NULL REFERENCES ingredient(id) ON DELETE CASCADE,
  link_type       VARCHAR(20) NOT NULL, -- ex) GOOD, BAD, NEUTRAL
  note            TEXT,
  created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
  UNIQUE (from_ingredient_id, to_ingredient_id, link_type)
);

CREATE INDEX idx_link_from ON ingredient_link(from_ingredient_id);
CREATE INDEX idx_link_to ON ingredient_link(to_ingredient_id);
