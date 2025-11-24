    ------------------------------------------------------------
    -- 1. 더미 데이터 입력
    ------------------------------------------------------------

    -- 사용자
    INSERT INTO user_account (email, password, status, role_name)
    VALUES
        ('test1@example.com', 'encryptedpw123', 'ACTIVE', 'ADMIN'),
        ('test2@example.com', 'encryptedpw123', 'ACTIVE', 'USER');

    INSERT INTO user_profile (user_account_id, username, bio)
    VALUES
        (1, '지석', 'FinNAI 관리자 계정'),
        (2, '투자왕', '주린이 탈출 목표');


    -- 기업 정보
    INSERT INTO company (corp_code, name, stock_code, induty_code, market, homepage_url, headquarters_addr, founded_date, corporate_reg_no, business_reg_no, phone_number)
    VALUES
        ('00126380', '삼성전자', '005930', 'IT 제조', 'KOSPI', 'https://www.samsung.com', '경기도 수원시 영통구', '1969-01-13', '110111-1234567', '220-81-12345', '02-2255-0114'),
        ('00149000', '네이버',   '035420', '포털/플랫폼', 'KOSPI', 'https://www.naver.com', '경기도 성남시 분당구', '1999-06-02', '120111-9876543', '129-81-67890', '1588-3830'),
        ('00142370', '카카오',   '035720', '플랫폼/메신저', 'KOSPI', 'https://www.kakaocorp.com', '제주특별자치도 제주시', '1995-02-16', '130211-5432167', '120-81-98765', '1577-3754');


    -- 기업 임베딩 (랜덤)
    INSERT INTO company_embedding (company_id, embedding)
    VALUES
        (1, ARRAY(SELECT random() FROM generate_series(1,1536))::vector),
        (2, ARRAY(SELECT random() FROM generate_series(1,1536))::vector),
        (3, ARRAY(SELECT random() FROM generate_series(1,1536))::vector);

    -- 북마크 및 관심기업
    INSERT INTO company_bookmark (user_account_id, company_id) VALUES
                                                   (1, 1),
                                                   (1, 2),
                                                   (2, 3);

    INSERT INTO company_following (user_account_id, company_id) VALUES
                                                            (1, 1),
                                                            (2, 1),
                                                            (2, 2);

    -- 회사 파일 및 임베딩 (유저 업로드 → 회사 귀속)
    INSERT INTO company_file (company_id, uploaded_by, file_type, file_url, original_name, raw_content)
    VALUES
        (1, 1, 'PDF',  'https://s3.test/005930_presentation.pdf', '삼성 IR 자료.pdf', '삼성전자 IR 자료 ...'),
        (2, 2, 'HTML', 'https://s3.test/035420_blog.html',        '네이버 블로그.html', '<html>네이버 서비스 개선안 ...</html>');



    -- 피드백 게시판
    INSERT INTO feedback_board (user_account_id, title, content, is_public)
    VALUES
        (1, 'UI 개선 요청', '차트 확대 기능이 필요해요', TRUE),
        (2, '버그 신고', '공시 리스트 무한로딩 발생합니다.', TRUE);

    INSERT INTO feedback_comment (feedback_board_id, user_account_id, content)
    VALUES
        (1, 1, '확인해보겠습니다.'),
        (2, 1, '수정 작업 진행 중입니다.');


    -- 공시 목록
    INSERT INTO disclosure_list (company_id, rcept_no, report_nm, rcept_dt, rpt_type, flr_nm)
    VALUES
        (1, '20240101000001', '사업보고서(2023)', '2024-03-01', 'A', '삼성전자'),
        (2, '20240202000002', '분기보고서(2024.1Q)', '2024-05-15', 'B', '네이버'),
        (3, '20240303000003', '반기보고서(2024.2Q)', '2024-08-10', 'C', '카카오');


    -- 공시 원문 파일
    INSERT INTO disclosure_file (disclosure_id, file_type, file_url, raw_content)
    VALUES
        (1, 'HTML', 'https://s3.test/005930_2023.html', '<html>삼성전자 2023 사업보고서 ...</html>'),
        (2, 'PDF', 'https://s3.test/035420_2024Q1.pdf', 'PDF Text Extracted ...'),
        (3, 'XBRL', 'https://s3.test/035720_2024Q2.xbrl', '<xbrl>카카오 재무제표 ...</xbrl>');


    -- 정기보고서 전문
    INSERT INTO dart_report (company_id, rcept_no, report_type, title, content, published_at)
    VALUES
        (1, '20240101000001', '사업보고서', '삼성전자 사업보고서(2023)', '삼성전자 2023년 사업 개요 ...', '2024-03-01'),
        (2, '20240202000002', '분기보고서', '네이버 2024년 1분기 보고서', '네이버 2024년 1Q 실적 ...', '2024-05-15'),
        (3, '20240303000003', '반기보고서', '카카오 2024년 반기 보고서', '카카오 반기 실적 ...', '2024-08-10');


    -- 정기보고서 임베딩


    -- 재무 데이터
    INSERT INTO financial_account (company_id, bsns_year, reprt_code, account_id, account_nm, thstrm_amount)
    VALUES
        (1, 2023, '11011', 'sales', '매출액', 260000000000),
        (1, 2023, '11011', 'op_profit', '영업이익', 32000000000),
        (1, 2023, '11011', 'net_profit', '당기순이익', 22000000000),

        (2, 2023, '11011', 'sales', '매출액', 89000000000),
        (2, 2023, '11011', 'op_profit', '영업이익', 12000000000),
        (2, 2023, '11011', 'net_profit', '당기순이익', 9000000000),

        (3, 2023, '11011', 'sales', '매출액', 79000000000),
        (3, 2023, '11011', 'op_profit', '영업이익', 6000000000),
        (3, 2023, '11011', 'net_profit', '당기순이익', 3000000000);


    -- 재무 지표
    INSERT INTO financial_index (company_id, bsns_year, reprt_code, index_nm, index_value)
    VALUES
        (1, 2023, '11011', 'ROE', 0.123),
        (1, 2023, '11011', '부채비율', 45.2),

        (2, 2023, '11011', 'ROE', 0.089),
        (2, 2023, '11011', '부채비율', 55.1),

        (3, 2023, '11011', 'ROE', 0.034),
        (3, 2023, '11011', '부채비율', 61.8);


    -- AI 요약 데이터
    ------------------------------------------------------------
    -- 2. 검색 쿼리 세트
    ------------------------------------------------------------

    -- 회사 검색(자동완성)
    SELECT id, corp_code, name, stock_code
    FROM company
    WHERE name ILIKE '%' || '삼성' || '%'
       OR stock_code ILIKE '%' || '삼성' || '%'
       OR corp_code ILIKE '%' || '삼성' || '%'
    ORDER BY name ASC
    LIMIT 10;


    -- 회사 상세 조회
    SELECT *
    FROM company
    WHERE id = 1;


    -- 유사 기업 검색(pgvector)
    SELECT
        c.id, c.name, c.stock_code,
        ce.embedding <-> :query_vector AS distance
    FROM company_embedding ce
             JOIN company c ON ce.company_id = c.id
    ORDER BY ce.embedding <-> :query_vector ASC
    LIMIT 5;


    -- 공시 목록 조회
    SELECT *
    FROM disclosure_list
    WHERE company_id = 1
    ORDER BY rcept_dt DESC
    LIMIT 50;


    -- 공시 파일 포함 상세 조회
    SELECT *
    FROM disclosure_list d
             LEFT JOIN disclosure_file f ON f.disclosure_id = d.id
    WHERE d.rcept_no = '20240101000001';


    -- 정기보고서 전문 조회
    SELECT *
    FROM dart_report
    WHERE rcept_no = '20240101000001';


    -- 재무 계정 조회(매출)
    SELECT *
    FROM financial_account
    WHERE company_id = 1
      AND account_nm = '매출액'
    ORDER BY bsns_year DESC;


    -- 재무지표 조회(ROE)
    SELECT *
    FROM financial_index
    WHERE company_id = 1
      AND index_nm = 'ROE';


    -- 유저 북마크 리스트
    SELECT *
    FROM company_bookmark b
             JOIN company c ON b.company_id = c.id
    WHERE b.user_account_id = 1;


    -- 최신 공시 + AI 요약
    SELECT
        d.report_nm,
        d.rcept_dt
    FROM disclosure_list d
             LEFT JOIN disclosure_file f ON f.disclosure_id = d.id
    WHERE d.company_id = 1
    ORDER BY d.rcept_dt DESC
    LIMIT 1;
