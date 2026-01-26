-- V14: recipe_ingredient_role ENUM을 VARCHAR로 변경

-- 1. recipe_ingredient 테이블의 role 컬럼 기본값 제거 (ENUM 타입 의존성 제거)
ALTER TABLE recipe_ingredient 
  ALTER COLUMN role DROP DEFAULT;

-- 2. recipe_ingredient 테이블의 role 컬럼 타입을 VARCHAR로 변경
ALTER TABLE recipe_ingredient 
  ALTER COLUMN role TYPE VARCHAR(20) USING role::text;

-- 3. VARCHAR 기본값 설정
ALTER TABLE recipe_ingredient 
  ALTER COLUMN role SET DEFAULT 'REQUIRED';

-- 4. recipe_ingredient_role ENUM 타입 삭제
DROP TYPE IF EXISTS recipe_ingredient_role;

