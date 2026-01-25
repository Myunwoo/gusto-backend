-- =========================================
-- V9: ingredient_edge 테이블의 score 컬럼을 integer로 변경
-- =========================================

-- 기존 데이터 변환: -1.0~1.0 범위의 점수를 1-10 범위로 변환
-- PAIR_WELL: 0.1~1.0 -> 1~10 (score * 10)
-- AVOID: -1.0~-0.1 -> 1~10 (abs(score) * 10)
-- NEUTRAL: 0.0 -> 0 (또는 NULL, 하지만 제약조건 때문에 0으로 설정)
UPDATE ingredient_edge
SET score = CASE
    WHEN relation_type = 'PAIR_WELL' THEN ROUND(score::numeric * 10)::integer
    WHEN relation_type = 'AVOID' THEN ROUND(ABS(score::numeric) * 10)::integer
    WHEN relation_type = 'NEUTRAL' THEN 0
    ELSE 0
END
WHERE score IS NOT NULL;

-- 컬럼 타입 변경 및 제약조건 수정
ALTER TABLE ingredient_edge
DROP CONSTRAINT IF EXISTS ck_edge_score_range;

ALTER TABLE ingredient_edge
ALTER COLUMN score TYPE integer USING score::integer;

-- 새로운 제약조건: 0-10 범위 (NEUTRAL=0, PAIR_WELL/AVOID=1-10)
ALTER TABLE ingredient_edge
ADD CONSTRAINT ck_edge_score_range CHECK (score >= 0 AND score <= 10);

-- 인덱스는 그대로 유지 (integer로 자동 변환됨)

