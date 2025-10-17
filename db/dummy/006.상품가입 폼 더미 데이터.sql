-- 69: 오징어 모임 통장 (일일 출석형)
UPDATE deposit_product 
SET payment_cycle     = null,   -- 적금 납입 주기(MONTHLY, DAILY)
    min_period_months = 0,      -- 가입 가능 최소 납입 기간(개월)
    max_period_months = 0,      -- 가입 가능 최대 납입 기간(개월)
    term_mode         = 'NONE', -- 가입 기간 정의 방식(RANGE: 직접 범위 지정 가능, TERMLIST: 3개월, 6개월 등 기간 고정)
    term_list         = null    -- 가입 기간 리스트(TERMLIST일 경우)
WHERE product_id = 69;

INSERT INTO deposit_product_rate (product_id, from_month, to_month, rate, created_at)   -- (상품 ID, 금리 적용 시작 개월, 금리 적용 종료 개월, 적용 금리, 생성 일시)
VALUES (69, 0, NULL, 0.10, SYSDATE);

INSERT INTO product_input_format (
    product_id, 
    input_1,   -- 납입 기간 입력 여부
    input_2,   -- 납입/가입 금액 입력 여부
    input_3,   -- 이체일 입력 여부
    input_4,   -- 시작일 입력 여부
    input_5,   -- 만기 처리 방식 입력 여부
    input_6,   -- 비과세 여부 입력 여부
    input_7,   -- 모임명 입력 여부
    input_8,   -- 모임구분 입력 여부
    from_account_req,     -- 출금계좌 입력 여부
    maturity_account_req  -- 만기 시 입금계좌 필요 여부
) 
VALUES (
    69, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0
);


-- 73: 매일출석적금 (일일 출석형)
UPDATE deposit_product
SET payment_cycle     = 'DAILY',    -- 적금 납입 주기(MONTHLY: 매월, DAILY: 매일)
    min_period_months = 6,          -- 가입 가능 최소 납입 기간(개월)
    max_period_months = 6,          -- 가입 가능 최대 납입 기간(개월)
    term_mode         = 'TERMLIST', -- 가입 기간 정의 방식(RANGE, TERMLIST)
    term_list         = '6'         -- 가입 기간 리스트(TERMLIST일 경우)
WHERE product_id = 73;

INSERT INTO product_input_format (
    product_id, 
    input_1,   -- 납입 기간 입력 여부
    input_2,   -- 납입/가입 금액 입력 여부
    input_3,   -- 이체일 입력 여부
    input_4,   -- 시작일 입력 여부
    input_5,   -- 만기 처리 방식 입력 여부
    input_6,   -- 비과세 여부 입력 여부
    input_7,   -- 모임명 입력 여부
    input_8,   -- 모임구분 입력 여부
    from_account_req,     -- 출금계좌 입력 여부
    maturity_account_req  -- 만기 시 입금계좌 필요 여부
) VALUES (
  73, 1, 1, 0, 0, 0, 0, 0, 0,
  0, 0
);


-- 74: 함께걷는 적금 (월납형: 기간+금액+이체일 필요)
UPDATE deposit_product
SET payment_cycle     = 'MONTHLY',  -- 적금 납입 주기(MONTHLY, DAILY)
    min_period_months = 6,          -- 가입 가능 최소 납입 기간(개월)
    max_period_months = 6,          -- 가입 가능 최대 납입 기간(개월)
    term_mode         = 'TERMLIST', -- 가입 기간 정의 방식(RANGE, TERMLIST)
    term_list         = '6'         -- 가입 기간 리스트(TERMLIST일 경우)
WHERE product_id = 74;

INSERT INTO product_input_format (
  product_id, 
    input_1,   -- 납입 기간 입력 여부
    input_2,   -- 납입/가입 금액 입력 여부
    input_3,   -- 이체일 입력 여부
    input_4,   -- 시작일 입력 여부
    input_5,   -- 만기 처리 방식 입력 여부
    input_6,   -- 비과세 여부 입력 여부
    input_7,   -- 모임명 입력 여부
    input_8,   -- 모임구분 입력 여부
    from_account_req,     -- 출금계좌 입력 여부
    maturity_account_req  -- 만기 시 입금계좌 필요 여부
) VALUES (
  74, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1
);


-- LIVE 정기예금(21)
UPDATE deposit_product
SET payment_cycle     = null,    -- 적금 납입 주기(MONTHLY, DAILY)
    min_period_months = 1,       -- 가입 가능 최소 납입 기간(개월)
    max_period_months = 60,      -- 가입 가능 최대 납입 기간(개월)
    term_mode         = 'RANGE', -- 가입 기간 정의 방식(RANGE, TERMLIST)
    term_list         = NULL     -- 가입 기간 리스트(TERMLIST일 경우)
WHERE product_id = 21;

INSERT INTO product_input_format (
    product_id,
    input_1,   -- 납입 기간 입력 여부
    input_2,   -- 납입/가입 금액 입력 여부
    input_3,   -- 이체일 입력 여부
    input_4,   -- 시작일 입력 여부
    input_5,   -- 만기 처리 방식 입력 여부
    input_6,   -- 비과세 여부 입력 여부
    input_7,   -- 모임명 입력 여부
    input_8,   -- 모임구분 입력 여부
    from_account_req,     -- 출금계좌 입력 여부
    maturity_account_req  -- 만기 시 입금계좌 필요 여부
) VALUES (
    21, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1
);

INSERT INTO deposit_product_rate (product_id, from_month, to_month, rate, created_at)    -- (상품 ID, 금리 적용 시작 개월, 금리 적용 종료 개월, 적용 금리, 생성 일시)
VALUES (21, 1, 2, 1.50, SYSDATE);

INSERT INTO deposit_product_rate (product_id, from_month, to_month, rate, created_at)
VALUES (21, 3, 5, 1.70, SYSDATE);

INSERT INTO deposit_product_rate (product_id, from_month, to_month, rate, created_at)
VALUES (21, 6, 11, 1.80, SYSDATE);

INSERT INTO deposit_product_rate (product_id, from_month, to_month, rate, created_at)
VALUES (21, 12, 23, 1.95, SYSDATE);

INSERT INTO deposit_product_rate (product_id, from_month, to_month, rate, created_at)
VALUES (21, 24, 35, 1.60, SYSDATE);

INSERT INTO deposit_product_rate (product_id, from_month, to_month, rate, created_at)
VALUES (21, 36, 59, 1.60, SYSDATE);

INSERT INTO deposit_product_rate (product_id, from_month, to_month, rate, created_at)
VALUES (21, 60, 60, 1.40, SYSDATE);

commit;