-- ========================================
-- pgvector 확장
-- ========================================
CREATE EXTENSION IF NOT EXISTS vector;



-- ========================================
-- 1. 사용자 계정 (Auth)
-- ========================================
CREATE TABLE IF NOT EXISTS user_account (
                                            id          SERIAL PRIMARY KEY,
                                            email       VARCHAR(255) UNIQUE NOT NULL
                                                CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
                                            password    VARCHAR(255) NOT NULL
                                                CHECK (length(password) >= 8),
                                            status      VARCHAR(30) DEFAULT 'ACTIVE'
                                                CHECK (status IN ('ACTIVE', 'WITHDRAWN', 'BANNED')),
                                            created_at  TIMESTAMP DEFAULT NOW(),
                                            updated_at  TIMESTAMP DEFAULT NOW()
);


-- 권한 테이블
CREATE TABLE IF NOT EXISTS user_role (
                                         id         SERIAL PRIMARY KEY,
                                         user_id    INT REFERENCES user_account(id) ON DELETE CASCADE,
                                         role_name  VARCHAR(50) NOT NULL
                                             CHECK (role_name IN ('USER', 'ADMIN'))
);



-- ========================================
-- 2. 사용자 프로필
-- ========================================
CREATE TABLE IF NOT EXISTS user_profile (
                                            id            SERIAL PRIMARY KEY,
                                            user_id       INT UNIQUE REFERENCES user_account(id) ON DELETE CASCADE,
                                            username      VARCHAR(255)
                                                CHECK (char_length(username) >= 2),
                                            profile_image BYTEA
                                                CHECK (octet_length(profile_image) <= 5 * 1024 * 1024),
                                            bio           VARCHAR(500),
                                            created_at    TIMESTAMP DEFAULT NOW(),
                                            updated_at    TIMESTAMP DEFAULT NOW()
);



-- ========================================
-- 3. 기업 정보 (company) — DART 기업개황 API 기반
-- ========================================
CREATE TABLE IF NOT EXISTS company (
                                       id                SERIAL PRIMARY KEY,
                                       corp_code         VARCHAR(20) UNIQUE,            -- DART 고유번호
                                       name              VARCHAR(255) NOT NULL,
                                       stock_code        VARCHAR(50) UNIQUE
                                           CHECK (length(stock_code) >= 3),
                                       sector            VARCHAR(255),
                                       market            VARCHAR(50)
                                           CHECK (market IN ('KOSPI', 'KOSDAQ', 'ETF', 'ETN', 'OTHER')),
                                       homepage_url      VARCHAR(500)
                                           CHECK (homepage_url ~* '^https?://'),
                                       headquarters_addr VARCHAR(500),
                                       founded_date      DATE
                                           CHECK (founded_date <= CURRENT_DATE),
                                       corporate_reg_no  VARCHAR(100),
                                       business_reg_no   VARCHAR(100),
                                       phone_number      VARCHAR(50),
                                       created_at        TIMESTAMP DEFAULT NOW(),
                                       updated_at        TIMESTAMP DEFAULT NOW()
);



-- ========================================
-- 4. 기업 임베딩 (vector)
-- ========================================
CREATE TABLE IF NOT EXISTS company_embedding (
                                                 id          SERIAL PRIMARY KEY,
                                                 company_id  INT REFERENCES company(id) ON DELETE CASCADE,
                                                 embedding   vector(1536) NOT NULL,
                                                 created_at  TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_company_embedding_vector
    ON company_embedding
        USING hnsw (embedding vector_l2_ops);



-- ========================================
-- 5. 북마크 및 구독 (Watchlist)
-- ========================================
CREATE TABLE IF NOT EXISTS bookmark (
                                        id          SERIAL PRIMARY KEY,
                                        user_id     INT REFERENCES user_account(id) ON DELETE CASCADE,
                                        company_id  INT REFERENCES company(id) ON DELETE CASCADE,
                                        created_at  TIMESTAMP DEFAULT NOW(),
                                        UNIQUE (user_id, company_id)
);

CREATE TABLE IF NOT EXISTS following_company (
                                                 id          SERIAL PRIMARY KEY,
                                                 user_id     INT REFERENCES user_account(id) ON DELETE CASCADE,
                                                 company_id  INT REFERENCES company(id) ON DELETE CASCADE,
                                                 created_at  TIMESTAMP DEFAULT NOW(),
                                                 UNIQUE (user_id, company_id)
);



-- ========================================
-- 6. 건의사항/피드백 게시판
-- ========================================
CREATE TABLE IF NOT EXISTS feedback_board (
                                              id          SERIAL PRIMARY KEY,
                                              user_id     INT REFERENCES user_account(id) ON DELETE SET NULL,
                                              title       VARCHAR(255) NOT NULL,
                                              content     TEXT NOT NULL,
                                              is_public   BOOLEAN DEFAULT TRUE,
                                              created_at  TIMESTAMP DEFAULT NOW(),
                                              updated_at  TIMESTAMP DEFAULT NOW(),
                                              deleted_at  TIMESTAMP
);

CREATE TABLE IF NOT EXISTS feedback_comment (
                                                id          SERIAL PRIMARY KEY,
                                                board_id    INT REFERENCES feedback_board(id) ON DELETE CASCADE,
                                                user_id     INT REFERENCES user_account(id) ON DELETE SET NULL,
                                                content     TEXT NOT NULL,
                                                created_at  TIMESTAMP DEFAULT NOW(),
                                                updated_at  TIMESTAMP DEFAULT NOW(),
                                                deleted_at  TIMESTAMP
);



-- ========================================
-- 7. DART 공시 목록 (list API) — ★ 필수
-- ========================================
CREATE TABLE IF NOT EXISTS disclosure_list (
                                               id          SERIAL PRIMARY KEY,
                                               company_id  INT REFERENCES company(id) ON DELETE CASCADE,
                                               rcept_no    VARCHAR(20) UNIQUE NOT NULL,  -- 공시 원문 키
                                               report_nm   VARCHAR(500) NOT NULL,        -- 공시 제목
                                               rcept_dt    DATE NOT NULL,                -- 공시 일자
                                               rpt_type    VARCHAR(50),
                                               flr_nm      VARCHAR(255),
                                               created_at  TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_disclosure_company_date
    ON disclosure_list(company_id, rcept_dt DESC);



-- ========================================
-- 8. 공시 원문 및 첨부파일 (document API)
-- ========================================
CREATE TABLE IF NOT EXISTS disclosure_file (
                                               id             SERIAL PRIMARY KEY,
                                               disclosure_id  INT REFERENCES disclosure_list(id) ON DELETE CASCADE,
                                               file_type      VARCHAR(50)
                                                   CHECK (file_type IN ('HTML', 'PDF', 'XBRL')),
                                               file_url       VARCHAR(500),             -- S3 / MinIO 저장 경로
                                               raw_content    TEXT,                     -- HTML or PDF OCR 텍스트
                                               created_at     TIMESTAMP DEFAULT NOW()
);



-- ========================================
-- 9. DART 정기보고서 전문 (사업/분기/반기 보고서)
-- ========================================
CREATE TABLE IF NOT EXISTS dart_report (
                                           id            SERIAL PRIMARY KEY,
                                           company_id    INT REFERENCES company(id) ON DELETE CASCADE,
                                           rcept_no      VARCHAR(20) UNIQUE,        -- 연결
                                           report_type   VARCHAR(255)
                                               CHECK (report_type IN ('사업보고서', '분기보고서', '반기보고서', '기타')),
                                           title         VARCHAR(500),
                                           content       TEXT,                       -- 전문 텍스트
                                           published_at  TIMESTAMP,
                                           created_at    TIMESTAMP DEFAULT NOW()
);



-- 정기보고서 임베딩
CREATE TABLE IF NOT EXISTS dart_report_embedding (
                                                     id         SERIAL PRIMARY KEY,
                                                     report_id  INT REFERENCES dart_report(id) ON DELETE CASCADE,
                                                     embedding  vector(1536) NOT NULL,
                                                     created_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_report_embedding_vector
    ON dart_report_embedding
        USING hnsw (embedding vector_l2_ops);



-- ========================================
-- 10. 재무 데이터 저장 (fnlttSinglAcnt API)
-- ========================================
CREATE TABLE IF NOT EXISTS financial_account (
                                                 id           SERIAL PRIMARY KEY,
                                                 company_id   INT REFERENCES company(id) ON DELETE CASCADE,
                                                 bsns_year    INT NOT NULL,                   -- 연도
                                                 reprt_code   VARCHAR(10) NOT NULL,           -- Q1/Q2/사업보고서 코드
                                                 account_id   VARCHAR(50),
                                                 account_nm   VARCHAR(255),
                                                 amount       BIGINT,
                                                 created_at   TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_fin_acc_company_year
    ON financial_account(company_id, bsns_year);



-- ========================================
-- 11. 재무 지표 저장 (fnlttSinglIndx API)
-- ========================================
CREATE TABLE IF NOT EXISTS financial_index (
                                               id           SERIAL PRIMARY KEY,
                                               company_id   INT REFERENCES company(id) ON DELETE CASCADE,
                                               bsns_year    INT NOT NULL,
                                               reprt_code   VARCHAR(10),
                                               index_nm     VARCHAR(255),
                                               index_value  NUMERIC,
                                               created_at   TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_fin_idx_company_year
    ON financial_index(company_id, bsns_year);



-- ========================================
-- 12. AI 요약 및 분석 결과 저장
-- ========================================
CREATE TABLE IF NOT EXISTS ai_summary (
                                          id             SERIAL PRIMARY KEY,
                                          disclosure_id  INT REFERENCES disclosure_list(id) ON DELETE CASCADE,
                                          summary_short  TEXT,
                                          summary_long   TEXT,
                                          risk_analysis  TEXT,
                                          created_at     TIMESTAMP DEFAULT NOW()
);
