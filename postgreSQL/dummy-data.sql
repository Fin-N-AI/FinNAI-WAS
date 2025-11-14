-- 1-1. 사용자 더미 데이터
-- 유저 생성
INSERT INTO user_account (email, password)
VALUES 
    ('test1@example.com', 'hashed_pw_1'),
    ('test2@example.com', 'hashed_pw_2'),
    ('admin@example.com', 'hashed_pw_admin');

-- 권한 부여
INSERT INTO user_role (user_id, role_name)
VALUES
    (1, 'USER'),
    (2, 'USER'),
    (3, 'ADMIN');

-- 프로필 생성
INSERT INTO user_profile (user_id, username, bio)
VALUES
    (1, '테스터1', '안녕하세요. 테스터1입니다.'),
    (2, '테스터2', '안녕하세요. 테스터2입니다.'),
    (3, '관리자', 'FinNAI 관리자 계정입니다.');



-- 1-2. 기업 더미 데이터
INSERT INTO company (name, stock_code, sector, market)
VALUES
    ('삼성전자', '005930', 'IT/전자', 'KOSPI'),
    ('카카오', '035720', '인터넷서비스', 'KOSPI'),
    ('네이버', '035420', '인터넷서비스', 'KOSPI');


-- 1-3. 기업 북마크 / 구독 더미
INSERT INTO bookmark (user_id, company_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 3);

INSERT INTO following_company (user_id, company_id)
VALUES
    (1, 1),
    (1, 3),
    (2, 1);


-- 1-4. DART 보고서 + 임베딩 더미 데이터
-- 실제 임베딩 벡터는 1536차원이지만,
-- 테스트용으로는 3차원만 넣을 수 없음 → 1536차원 모두 넣어야 함.
-- 테스트 목적으로 랜덤 벡터 생성해서 넣는 방식으로 작성.
-- Helper: 랜덤 vector(1536) 생성 함수
CREATE OR REPLACE FUNCTION gen_vec_1536()
RETURNS vector AS $$
DECLARE
    arr float4[];
    i INT;
BEGIN
    arr := ARRAY[]::float4[];
    FOR i IN 1..1536 LOOP
        arr := arr || random();  -- 0~1 사이 랜덤값
    END LOOP;
    RETURN arr;
END;
$$ LANGUAGE plpgsql;


-- ① 보고서 더미 데이터
INSERT INTO dart_report (company_id, report_type, title, content, published_at)
VALUES
    (1, '사업보고서', '삼성전자 2024 사업보고서', '삼성전자 사업보고서 내용...', '2024-03-31'),
    (2, '사업보고서', '카카오 2024 사업보고서', '카카오 사업보고서 내용...', '2024-03-31'),
    (3, '사업보고서', '네이버 2024 사업보고서', '네이버 사업보고서 내용...', '2024-03-31');

-- ② 보고서 임베딩 더미 데이터 (랜덤 값 1536차원)
INSERT INTO dart_report_embedding (report_id, embedding)
VALUES
    (1, gen_vec_1536()),
    (2, gen_vec_1536()),
    (3, gen_vec_1536());


-- 2. 기업 임베딩 더미 데이터 (회사 자체 임베딩)
INSERT INTO company_embedding (company_id, embedding)
VALUES
    (1, gen_vec_1536()),
    (2, gen_vec_1536()),
    (3, gen_vec_1536());


-- 3. 벡터 검색 테스트
-- 랜덤 쿼리 벡터 생성
-- 벡터 검색은 pgvector의 <-> 연산자를 사용하면 됨.
-- 아래 쿼리는:
-- ✔ query 벡터(1536차원)를 랜덤 생성
-- ✔ company_embedding 테이블에서 가장 가까운 기업 Top 3 가져오기
WITH query_vec AS (
    SELECT gen_vec_1536() AS v
)
SELECT 
    c.name,
    ce.embedding <-> q.v AS distance
FROM company_embedding ce
JOIN company c ON ce.company_id = c.id
JOIN query_vec q ON TRUE
ORDER BY distance ASC
LIMIT 3;


-- 4. 특정 기업과 가장 유사한 기업 찾기 (Self Vector Search)
-- 예시: “삼성전자와 가장 비슷한 기업 찾기”
WITH base AS (
    SELECT embedding
    FROM company_embedding
    WHERE company_id = 1        -- 삼성전자
)
SELECT 
    c.id,
    c.name,
    ce.embedding <-> b.embedding AS distance
FROM company_embedding ce
JOIN company c ON ce.company_id = c.id
JOIN base b ON TRUE
WHERE ce.company_id != 1                -- 자기 자신 제외
ORDER BY distance ASC
LIMIT 5;


-- 5. DART 보고서 기반 RAG 검색
-- 예: 질문 벡터를 생성한 후 → 가장 관련 있는 보고서 chunk 찾기
-- RAG 검색: dart_report_embedding 중에서 가장 가까운 내용 찾기
WITH query_vec AS (
    SELECT gen_vec_1536() AS v
)
SELECT 
    dr.title,
    dr.company_id,
    dre.embedding <-> q.v AS distance
FROM dart_report_embedding dre
JOIN dart_report dr ON dre.report_id = dr.id
JOIN query_vec q ON TRUE
ORDER BY distance ASC
LIMIT 3;



