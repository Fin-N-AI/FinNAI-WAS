-- ========================================
-- pgvector 확장 활성화 (벡터 임베딩 저장/검색 기능 제공)
-- ========================================
CREATE EXTENSION IF NOT EXISTS vector;


-- ========================================
-- 1. 사용자 계정 (Auth)
-- ========================================
CREATE TABLE IF NOT EXISTS user_account
(
    id         SERIAL PRIMARY KEY,
    -- 내부적으로 사용하는 사용자 고유 ID

    email      VARCHAR(255) UNIQUE NOT NULL
        CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    -- 로그인용 이메일 (중복 불가)

    password   VARCHAR(255)        NOT NULL
        CHECK (length(password) >= 8),
    -- 해시된 비밀번호 (평문 저장 금지) / 최소 길이 강제

    -- 계정 상태 제한: ACTIVE / WITHDRAWN / BANNED
    status     VARCHAR(30)
                         DEFAULT 'ACTIVE'
        CHECK (status IN ('ACTIVE', 'WITHDRAWN', 'BANNED')),

    created_at TIMESTAMP DEFAULT NOW(),
    -- 계정 생성 시각

    updated_at TIMESTAMP DEFAULT NOW()
    -- 계정 정보 수정 시각
);


-- 권한 테이블 (유저별 Role 저장)
CREATE TABLE IF NOT EXISTS user_role
(
    id        SERIAL PRIMARY KEY,
    -- 고유 ID

    user_id   INT REFERENCES user_account (id) ON DELETE CASCADE,
    -- 특정 유저의 권한. 유저 삭제 시 권한도 함께 삭제

    role_name VARCHAR(50) NOT NULL
        CHECK (role_name IN ('USER', 'ADMIN'))
    -- 권한 이름(USER/ADMIN 등)
);


-- ========================================
-- 2. 사용자 프로필 (이미지, 기본정보)
-- ========================================
CREATE TABLE IF NOT EXISTS user_profile
(
    id            SERIAL PRIMARY KEY,
    -- 프로필 ID

    user_id       INT UNIQUE REFERENCES user_account (id) ON DELETE CASCADE,
    -- user_account와 1:1, 유저 삭제 시 프로필도 삭제

    username      VARCHAR(255)
        CHECK (char_length(username) >= 2),
    -- 닉네임 (최소 2자)

    profile_image BYTEA
        CHECK (octet_length(profile_image) <= 5 * 1024 * 1024),
    -- 프로필 이미지 (byte 형태로 저장, 5MB 이하)

    bio           VARCHAR(500),
    -- 자기소개 글

    created_at    TIMESTAMP DEFAULT NOW(),
    -- 생성 시각

    updated_at    TIMESTAMP DEFAULT NOW()
    -- 수정 시각
);


-- ========================================
-- 3. 기업 테이블 (기업 정보)
-- ========================================
CREATE TABLE IF NOT EXISTS company
(
    id                SERIAL PRIMARY KEY,
    -- 기업 고유 ID

    name              VARCHAR(255) NOT NULL,
    -- 기업명

    stock_code        VARCHAR(50) UNIQUE
        CHECK (length(stock_code) >= 3),
    -- 종목코드 (중복 불가)

    sector            VARCHAR(255),
    -- 업종/섹터 정보

    market            VARCHAR(255)
        CHECK (market IN ('KOSPI', 'KOSDAQ', 'ETF', 'ETN', 'OTHER')),
    -- 시장 구분(KOSPI, KOSDAQ 등)

    homepage_url      VARCHAR(500)
        CHECK (homepage_url ~* '^https?://'),
    -- 기업 홈페이지 주소

    headquarters_addr VARCHAR(500),
    -- 본사 주소지

    founded_date      DATE
        CHECK (founded_date <= CURRENT_DATE),
    -- 설립일 (미래 금지)

    corporate_reg_no  VARCHAR(100),
    -- 법인등록번호

    business_reg_no   VARCHAR(100),
    -- 사업자등록번호

    phone_number      VARCHAR(50)
        CHECK (phone_number ~ '^[0-9\-+() ]+$'),
    -- 대표 전화번호

    created_at        TIMESTAMP DEFAULT NOW(),
    -- 데이터 생성시각

    updated_at        TIMESTAMP DEFAULT NOW()
    -- 데이터 수정시각
);


-- ========================================
-- 4. 기업 벡터 임베딩 (pgvector)
-- ========================================
CREATE TABLE IF NOT EXISTS company_embedding
(
    id         SERIAL PRIMARY KEY,
    -- 임베딩 고유 ID

    company_id INT REFERENCES company (id) ON DELETE CASCADE,
    -- 어떤 회사의 임베딩인지 연결

    embedding  vector(1536)
        CHECK (embedding IS NOT NULL),
    -- 회사 특징을 표현하는 임베딩 벡터

    created_at TIMESTAMP DEFAULT NOW()
    -- 생성 시각
);



-- ========================================
-- 5. 북마크 (기업 북마크)
-- ========================================
CREATE TABLE IF NOT EXISTS bookmark
(
    id         SERIAL PRIMARY KEY,
    -- 북마크 ID

    user_id    INT REFERENCES user_account (id) ON DELETE CASCADE,
    -- 북마크를 단 사용자

    company_id INT REFERENCES company (id) ON DELETE CASCADE,
    -- 북마크된 기업

    created_at TIMESTAMP DEFAULT NOW(),
    -- 북마크한 시각

    UNIQUE (user_id, company_id),
    -- 같은 기업 중복 북마크 방지

    CHECK (user_id > 0 AND company_id > 0)
);


-- ========================================
-- 6. 관심기업 구독 (following)
-- ========================================
CREATE TABLE IF NOT EXISTS following_company
(
    id         SERIAL PRIMARY KEY,
    -- 팔로우 ID

    user_id    INT REFERENCES user_account (id) ON DELETE CASCADE,
    -- 구독한 사용자

    company_id INT REFERENCES company (id) ON DELETE CASCADE,
    -- 구독된 기업

    created_at TIMESTAMP DEFAULT NOW(),
    -- 구독한 시각

    UNIQUE (user_id, company_id)
    -- 동일 기업 중복 구독 방지
);


-- ========================================
-- 7. 건의/피드백 게시판
-- ========================================
CREATE TABLE IF NOT EXISTS feedback_board
(
    id         SERIAL PRIMARY KEY,
    -- 게시글 ID

    user_id    INT          REFERENCES user_account (id) ON DELETE SET NULL,
    -- 작성자가 탈퇴해도 게시글은 남기기 위해 SET NULL

    title      VARCHAR(255) NOT NULL
        CHECK (char_length(title) > 0),
    -- 제목

    content    TEXT         NOT NULL
        CHECK (char_length(content) > 0),
    -- 게시물 내용

    is_public  BOOLEAN   DEFAULT TRUE,
    -- 공개 여부(TRUE면 모든 유저 열람 가능)

    created_at TIMESTAMP DEFAULT NOW(),
    -- 작성 시각

    updated_at TIMESTAMP DEFAULT NOW(),
    -- 수정 시각

    deleted_at TIMESTAMP
    -- soft delete 처리 시각
);

-- 댓글 (관리자만 댓글 허용 가능)
CREATE TABLE IF NOT EXISTS feedback_comment
(
    id         SERIAL PRIMARY KEY,
    -- 댓글 ID

    board_id   INT REFERENCES feedback_board (id) ON DELETE CASCADE,
    -- 원본 게시글 삭제 시 댓글도 함께 삭제

    user_id    INT  REFERENCES user_account (id) ON DELETE SET NULL,
    -- 유저 삭제 시 댓글은 유지하되 작성자 NULL 처리

    content    TEXT NOT NULL
        CHECK (char_length(content) > 0),
    -- 댓글 내용

    created_at TIMESTAMP DEFAULT NOW(),
    -- 작성 시각

    updated_at TIMESTAMP DEFAULT NOW(),
    -- 수정 시각

    deleted_at TIMESTAMP
    -- soft delete 시각
);


-- ========================================
-- 8. DART 공시 데이터 (향후 Agent 분석용)
-- ========================================
CREATE TABLE IF NOT EXISTS dart_report
(
    id           SERIAL PRIMARY KEY,
    -- 보고서 ID

    company_id   INT REFERENCES company (id) ON DELETE CASCADE,
    -- 해당 보고서가 어떤 회사 것인지

    report_type  VARCHAR(255)
        CHECK (report_type IN ('사업보고서', '분기보고서', '반기보고서', '기타')),
    -- 보고서 종류(사업보고서, 분기보고서 등)

    title        VARCHAR(500),
    -- 보고서 제목

    content      TEXT,
    -- 보고서 전문 텍스트

    published_at TIMESTAMP
        CHECK (published_at <= NOW()),
    -- DART에 실제 공개된 날짜

    created_at   TIMESTAMP DEFAULT NOW()
    -- 등록 시각
);


CREATE TABLE IF NOT EXISTS dart_report_embedding
(
    id         SERIAL PRIMARY KEY,
    -- 임베딩 ID

    report_id  INT REFERENCES dart_report (id) ON DELETE CASCADE,
    -- 어떤 보고서의 임베딩인지

    embedding  vector(1536)
        CHECK (embedding IS NOT NULL),
    -- 보고서 내용을 벡터화한 데이터

    created_at TIMESTAMP DEFAULT NOW()
    -- 저장 시각
);


-- ========================================
-- 랜덤 1536차원 벡터 생성 함수
-- ========================================
CREATE OR REPLACE FUNCTION gen_vec_1536()
    RETURNS vector AS
$$
DECLARE
    arr float4[];
    i   INT;
BEGIN
    arr := ARRAY []::float4[];
    FOR i IN 1..1536
        LOOP
            arr := arr || random();
        END LOOP;
    RETURN arr;
END;
$$ LANGUAGE plpgsql;



-- ========================================
-- 1. 사용자 더미 데이터
-- ========================================
INSERT INTO user_account (email, password, status)
VALUES ('test1@example.com', 'hashed_pw_1', 'ACTIVE'),
       ('test2@example.com', 'hashed_pw_2', 'ACTIVE'),
       ('admin@example.com', 'hashed_pw_admin', 'ACTIVE'),
       ('withdraw@example.com', 'hashed_pw_4', 'WITHDRAWN'),
       ('banned@example.com', 'hashed_pw_5', 'BANNED');


INSERT INTO user_role (user_id, role_name)
VALUES (1, 'USER'),
       (2, 'USER'),
       (3, 'ADMIN'),
       (4, 'USER'),
       (5, 'USER');


INSERT INTO user_profile (user_id, username, bio)
VALUES (1, '테스터1', '안녕하세요. 테스터1입니다.'),
       (2, '테스터2', '두 번째 사용자입니다.'),
       (3, '관리자', 'FinNAI 관리자 계정입니다.'),
       (4, '탈퇴유저', '현재 탈퇴 상태입니다.'),
       (5, '차단유저', '현재 차단 상태입니다.');



-- ========================================
-- 2. 기업 더미 데이터
-- ========================================
INSERT INTO company (name, stock_code, sector, market, homepage_url, headquarters_addr, founded_date, corporate_reg_no,
                     business_reg_no, phone_number)
VALUES ('삼성전자', '005930', 'IT/전자', 'KOSPI', 'https://www.samsung.com', '경기도 수원시 영통구', '1969-01-13', '123-45-67890',
        '111-22-33333', '02-2255-0114'),
       ('카카오', '035720', '인터넷서비스', 'KOSPI', 'https://www.kakaocorp.com', '경기도 성남시 분당구', '2010-09-01', '222-33-44444',
        '333-44-55555', '031-123-4567'),
       ('네이버', '035420', '인터넷서비스', 'KOSPI', 'https://www.naver.com', '경기도 성남시 분당구 불정로', '1999-06-02', '444-55-66666',
        '555-66-77777', '1588-3820'),
       ('LG전자', '066570', 'IT/전자', 'KOSPI', 'https://www.lge.co.kr', '서울특별시 영등포구', '1958-10-01', '777-88-99999',
        '888-99-00000', '02-3777-1114'),
       ('현대자동차', '005380', '자동차', 'KOSPI', 'https://www.hyundai.com', '서울특별시 서초구', '1967-12-29', '999-00-11111',
        '222-11-33333', '02-3464-1114');



-- ========================================
-- 3. 북마크/구독 더미 데이터
-- ========================================
INSERT INTO bookmark (user_id, company_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (3, 1),
       (3, 4);

INSERT INTO following_company (user_id, company_id)
VALUES (1, 1),
       (1, 3),
       (2, 1),
       (3, 2),
       (4, 5);



-- ========================================
-- 4. DART 보고서 + 임베딩 더미
-- ========================================
INSERT INTO dart_report (company_id, report_type, title, content, published_at)
VALUES (1, '사업보고서', '삼성전자 2024 사업보고서', '삼성전자 사업보고서 내용...', '2024-03-31'),
       (2, '사업보고서', '카카오 2024 사업보고서', '카카오 사업보고서 내용...', '2024-03-31'),
       (3, '사업보고서', '네이버 2024 사업보고서', '네이버 사업보고서 내용...', '2024-03-31'),
       (4, '사업보고서', 'LG전자 2024 사업보고서', 'LG전자 사업보고서 내용...', '2024-03-31'),
       (5, '사업보고서', '현대자동차 2024 사업보고서', '현대차 사업보고서 내용...', '2024-03-31');


INSERT INTO dart_report_embedding (report_id, embedding)
VALUES (1, gen_vec_1536()),
       (2, gen_vec_1536()),
       (3, gen_vec_1536()),
       (4, gen_vec_1536()),
       (5, gen_vec_1536());



-- ========================================
-- 5. 기업 자체 임베딩 더미
-- ========================================
INSERT INTO company_embedding (company_id, embedding)
VALUES (1, gen_vec_1536()),
       (2, gen_vec_1536()),
       (3, gen_vec_1536()),
       (4, gen_vec_1536()),
       (5, gen_vec_1536());



-- ========================================
-- 6. 벡터 검색 테스트 예제
-- ========================================

-- 랜덤 쿼리 벡터 생성 후 가장 가까운 기업 Top 3
WITH query_vec AS (SELECT gen_vec_1536() AS v)
SELECT c.name,
       ce.embedding <-> q.v AS distance
FROM company_embedding ce
         JOIN company c ON ce.company_id = c.id
         JOIN query_vec q ON TRUE
ORDER BY distance ASC
LIMIT 3;


-- 특정 기업과 가장 유사한 기업 찾기 (삼성전자 → Top 5)
WITH base AS (SELECT embedding
              FROM company_embedding
              WHERE company_id = 1)
SELECT c.id,
       c.name,
       ce.embedding <-> b.embedding AS distance
FROM company_embedding ce
         JOIN company c ON ce.company_id = c.id
         JOIN base b ON TRUE
WHERE ce.company_id != 1
ORDER BY distance ASC
LIMIT 5;


-- 특정 섹터 내 RAG 검색 + 벡터 필터링
WITH query_vec AS (SELECT gen_vec_1536() AS v)
SELECT dr.title,
       dr.company_id,
       dre.embedding <-> q.v AS distance
FROM dart_report_embedding dre
         JOIN dart_report dr ON dre.report_id = dr.id
         JOIN query_vec q ON TRUE
WHERE dr.company_id = 1 -- 삼성전자 보고서만
ORDER BY distance ASC
LIMIT 3;
