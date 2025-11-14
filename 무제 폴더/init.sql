-- ========================================
-- pgvector 확장 활성화 (벡터 임베딩 저장/검색 기능 제공)
-- ========================================
CREATE EXTENSION IF NOT EXISTS vector;


-- ========================================
-- 1. 사용자 계정 (Auth)
-- ========================================
CREATE TABLE IF NOT EXISTS user_account (
    id              SERIAL PRIMARY KEY,                 -- 내부적으로 사용하는 사용자 고유 ID
    email           VARCHAR(255) UNIQUE NOT NULL,       -- 로그인용 이메일 (중복 불가)
    password        VARCHAR(255) NOT NULL,              -- 해시된 비밀번호 (평문 저장 금지)
    status          VARCHAR(30) DEFAULT 'ACTIVE',       -- 계정 상태: ACTIVE / WITHDRAWN / BANNED
    created_at      TIMESTAMP DEFAULT NOW(),            -- 계정 생성 시각
    updated_at      TIMESTAMP DEFAULT NOW()             -- 계정 정보 수정 시각
);

-- 권한 테이블 (유저별 Role 저장)
CREATE TABLE IF NOT EXISTS user_role (
    id          SERIAL PRIMARY KEY,                     -- 고유 ID
    user_id     INT REFERENCES user_account(id) ON DELETE CASCADE,  
        -- 특정 유저의 권한. 유저 삭제 시 권한도 함께 삭제
    role_name   VARCHAR(50) NOT NULL                    -- 권한 이름(USER/ADMIN 등)
);


-- ========================================
-- 2. 사용자 프로필 (이미지, 기본정보)
-- ========================================
CREATE TABLE IF NOT EXISTS user_profile (
    id              SERIAL PRIMARY KEY,                 -- 프로필 ID
    user_id         INT UNIQUE REFERENCES user_account(id) ON DELETE CASCADE,
        -- user_account와 1:1, 유저 삭제 시 프로필도 삭제
    username        VARCHAR(255),                       -- 닉네임
    profile_image   BYTEA,                              -- 프로필 이미지 (byte 형태로 저장)
    bio             VARCHAR(500),                       -- 자기소개 글
    created_at      TIMESTAMP DEFAULT NOW(),            -- 생성 시각
    updated_at      TIMESTAMP DEFAULT NOW()             -- 수정 시각
);


-- ========================================
-- 3. 기업 테이블 (기업 정보)
-- ========================================
CREATE TABLE IF NOT EXISTS company (
    id              SERIAL PRIMARY KEY,                 -- 기업 고유 ID
    name            VARCHAR(255) NOT NULL,              -- 기업명
    stock_code      VARCHAR(50) UNIQUE,                 -- 종목코드 (중복 불가)
    sector          VARCHAR(255),                       -- 업종/섹터 정보
    market          VARCHAR(255),                       -- 시장 구분(KOSPI, KOSDAQ 등)
    created_at      TIMESTAMP DEFAULT NOW(),            -- 등록 시각
    updated_at      TIMESTAMP DEFAULT NOW()             -- 수정 시각
);


-- ========================================
-- 4. 기업 벡터 임베딩 (pgvector)
-- ========================================
CREATE TABLE IF NOT EXISTS company_embedding (
    id              SERIAL PRIMARY KEY,                 -- 임베딩 고유 ID
    company_id      INT REFERENCES company(id) ON DELETE CASCADE,
        -- 어떤 회사의 임베딩인지 연결
    embedding       vector(1536),                       -- 회사 특징을 표현하는 임베딩 벡터
    created_at      TIMESTAMP DEFAULT NOW()             -- 생성 시각
);

-- 벡터 검색 최적화를 위한 인덱스 (필요 시 활성화)
-- CREATE INDEX company_embedding_idx ON company_embedding USING hnsw (embedding vector_l2_ops);


-- ========================================
-- 5. 북마크 (기업 북마크)
-- ========================================
CREATE TABLE IF NOT EXISTS bookmark (
    id              SERIAL PRIMARY KEY,                 -- 북마크 ID
    user_id         INT REFERENCES user_account(id) ON DELETE CASCADE,
        -- 북마크를 단 사용자
    company_id      INT REFERENCES company(id) ON DELETE CASCADE,
        -- 북마크된 기업
    created_at      TIMESTAMP DEFAULT NOW(),            -- 북마크한 시각
    UNIQUE(user_id, company_id)                         -- 같은 기업 중복 북마크 방지
);


-- ========================================
-- 6. 관심기업 구독 (following)
-- ========================================
CREATE TABLE IF NOT EXISTS following_company (
    id              SERIAL PRIMARY KEY,                 -- 팔로우 ID
    user_id         INT REFERENCES user_account(id) ON DELETE CASCADE,
        -- 구독한 사용자
    company_id      INT REFERENCES company(id) ON DELETE CASCADE,
        -- 구독된 기업
    created_at      TIMESTAMP DEFAULT NOW(),            -- 구독한 시각
    UNIQUE(user_id, company_id)                         -- 동일 기업 중복 구독 방지
);


-- ========================================
-- 7. 건의/피드백 게시판
-- ========================================
CREATE TABLE IF NOT EXISTS feedback_board (
    id              SERIAL PRIMARY KEY,                 -- 게시글 ID
    user_id         INT REFERENCES user_account(id) ON DELETE SET NULL,
        -- 작성자가 탈퇴해도 게시글은 남기기 위해 SET NULL
    title           VARCHAR(255) NOT NULL,              -- 제목
    content         TEXT NOT NULL,                      -- 게시물 내용
    is_public       BOOLEAN DEFAULT TRUE,               -- 공개 여부(TRUE면 모든 유저 열람 가능)
    created_at      TIMESTAMP DEFAULT NOW(),            -- 작성 시각
    updated_at      TIMESTAMP DEFAULT NOW(),            -- 수정 시각
    deleted_at      TIMESTAMP                           -- soft delete 처리 시각(NULL이면 정상 글)
);

-- 댓글 (관리자만 댓글 허용 가능)
CREATE TABLE IF NOT EXISTS feedback_comment (
    id              SERIAL PRIMARY KEY,                 -- 댓글 ID
    board_id        INT REFERENCES feedback_board(id) ON DELETE CASCADE,
        -- 원본 게시글 삭제 시 댓글도 함께 삭제
    user_id         INT REFERENCES user_account(id) ON DELETE SET NULL,
        -- 유저 삭제 시 댓글은 유지하되 작성자 NULL 처리
    content         TEXT NOT NULL,                      -- 댓글 내용
    created_at      TIMESTAMP DEFAULT NOW(),            -- 작성 시각
    updated_at      TIMESTAMP DEFAULT NOW(),            -- 수정 시각
    deleted_at      TIMESTAMP                           -- soft delete 시각
);


-- ========================================
-- 8. DART 공시 데이터 (향후 Agent 분석용)
-- ========================================
CREATE TABLE IF NOT EXISTS dart_report (
    id              SERIAL PRIMARY KEY,                 -- 보고서 ID
    company_id      INT REFERENCES company(id) ON DELETE CASCADE,
        -- 해당 보고서가 어떤 회사 것인지
    report_type     VARCHAR(255),                       -- 보고서 종류(사업보고서, 분기보고서 등)
    title           VARCHAR(500),                       -- 보고서 제목
    content         TEXT,                               -- 보고서 전문 텍스트
    published_at    TIMESTAMP,                          -- DART에 실제 공개된 날짜
    created_at      TIMESTAMP DEFAULT NOW()             -- 등록 시각
);

CREATE TABLE IF NOT EXISTS dart_report_embedding (
    id              SERIAL PRIMARY KEY,                 -- 임베딩 ID
    report_id       INT REFERENCES dart_report(id) ON DELETE CASCADE,
        -- 어떤 보고서의 임베딩인지
    embedding       vector(1536),                       -- 보고서 내용을 벡터화한 데이터
    created_at      TIMESTAMP DEFAULT NOW()             -- 저장 시각
);
