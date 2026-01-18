-- V4__add_recipe_core.sql
-- Recipe core schema + ingredient mapping + cached ingredient id arrays (GIN indexed)
--
-- Strategy:
-- 1) Normalize: recipe_ingredient table (source of truth)
-- 2) Cache: recipe.required_ingredient_ids, recipe.optional_ingredient_ids (int[])
-- 3) Index: GIN on cached arrays for fast subset checks
-- 4) Cache refresh is handled by application code (explicit update)

-- =========================================
-- 1) Recipe table
-- =========================================
create table recipe (
  recipe_id bigserial primary key,

  title varchar(120) not null,
  description text,                 -- short intro / memo
  instructions text,                -- cooking steps (free text for now)
  servings int,                     -- optional
  cook_time_minutes int,            -- optional

  -- Cached ingredient sets for fast filtering
  required_ingredient_ids int[] not null default '{}',
  optional_ingredient_ids int[] not null default '{}',

  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

-- Candidate filtering acceleration
create index ix_recipe_required_ids_gin on recipe using gin (required_ingredient_ids);
create index ix_recipe_optional_ids_gin on recipe using gin (optional_ingredient_ids);

create index ix_recipe_title on recipe(title);

-- =========================================
-- 2) Recipe - Ingredient mapping (source of truth)
-- =========================================
do $$ begin
  create type recipe_ingredient_role as enum ('REQUIRED', 'OPTIONAL');
exception when duplicate_object then null;
end $$;

create table recipe_ingredient (
  recipe_ingredient_id bigserial primary key,

  recipe_id bigint not null references recipe(recipe_id) on delete cascade,
  ingredient_id bigint not null references ingredient(ingredient_id),

  role recipe_ingredient_role not null default 'REQUIRED',
  amount numeric(10,3),             -- e.g. 120.000
  unit varchar(30),                 -- e.g. g, ml, tbsp, tsp, piece
  note varchar(255),                -- e.g. "thinly sliced", "room temp"

  created_at timestamptz not null default now(),

  unique (recipe_id, ingredient_id)
);

create index ix_recipe_ingredient_recipe on recipe_ingredient(recipe_id);
create index ix_recipe_ingredient_ingredient on recipe_ingredient(ingredient_id);
create index ix_recipe_ingredient_role on recipe_ingredient(role);

-- =========================================
-- 3) Optional: helper function
-- Application can call this after modifying recipe_ingredient rows.
-- =========================================
create or replace function refresh_recipe_ingredient_cache(p_recipe_id bigint)
returns void as $$
begin
  update recipe r
  set
    required_ingredient_ids = coalesce((
      select array_agg(ri.ingredient_id order by ri.ingredient_id)
      from recipe_ingredient ri
      where ri.recipe_id = p_recipe_id
        and ri.role = 'REQUIRED'
    ), '{}'::int[]),

    optional_ingredient_ids = coalesce((
      select array_agg(ri.ingredient_id order by ri.ingredient_id)
      from recipe_ingredient ri
      where ri.recipe_id = p_recipe_id
        and ri.role = 'OPTIONAL'
    ), '{}'::int[]),

    updated_at = now()
  where r.recipe_id = p_recipe_id;
end;
$$ language plpgsql;
