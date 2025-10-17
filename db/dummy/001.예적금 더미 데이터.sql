SET DEFINE OFF;

-- 1: 부산형 내일채움공제적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    1,
    '부산형 내일채움공제적금',
    '적금',
    '목돈마련',
    '부산시 정책성 목돈마련 적금, 월 12.5만원 납입, 최대 연 4.0%',
    '[{
        "title": "원하는 기간으로 자유롭게",
        "content": "가입 기간 3개월~36개월 중 원하는 기간을 자유롭게 선택하여 운용할 수 있습니다.\\n짧게는 3개월, 길게는 3년까지 고객님의 계획에 맞춰 예치하세요.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "높은 금리로 안정적 자산 운용",
        "content": "최대 연 4.0%의 금리 혜택으로 자산을 안정적으로 불리세요.\\n우대조건 충족 시 추가 금리 혜택 제공.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "간편한 가입과 관리",
        "content": "스마트뱅킹 및 인터넷뱅킹으로 언제 어디서나 간편하게 가입 및 관리 가능합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "부산시 정책 연계 혜택",
        "content": "조선·기자재업 및 관광·마이스업 종사 근로자를 위한 부산시 정책성 상품으로 1:3 비율 지원금 혜택 제공.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "세제 혜택",
        "content": "비과세종합저축으로 가입 가능해 이자소득세 비과세 혜택 누림.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "중도해지 유의사항",
        "content": "만기 전 해지 시 약정 이율보다 낮은 중도해지 이율 적용 및 압류·가압류 시 지급 제한 가능.",
        "imageURL": "/im/6.png"
    },
    {
        "title": "예금자보호 및 안전성",
        "content": "이 예금은 예금자보호법에 따라 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]', 
    NULL, NULL, 4.00, 3.80, 12, 'S', SYSDATE, 0
);


-- 2: BNK내맘대로적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    2,
    'BNK내맘대로적금',
    '적금',
    '목돈마련',
    '가입기간, 금액, 우대이율을 마음대로 선택할 수 있는 자유적립식 적금, 최대 연 2.45%',
    '[{
        "title": "마음대로 설계하는 적금",
        "content": "가입기간, 금액, 우대이율을 자유롭게 선택해 나만의 적금을 설계할 수 있습니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "다양한 가입 채널",
        "content": "영업점, 인터넷뱅킹, 모바일뱅킹, 디지털데스크 등 다양한 채널을 통해 언제 어디서나 가입 가능합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "자유적립식 적금",
        "content": "납입금액과 일자에 구애받지 않고 원하는 때에 자유롭게 납입할 수 있습니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "우대이율 제공",
        "content": "가입 및 해지 시 다양한 조건 충족 시 우대이율(최대 0.20%p)을 적용받을 수 있습니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "비과세 혜택",
        "content": "비과세종합저축으로 가입 가능하여 이자소득세 비과세 혜택을 누릴 수 있습니다.",
        "imageURL": "/im/6.png"
    },
    {
        "title": "만기 및 중도해지 유의사항",
        "content": "만기 전 해지 시 중도해지 이율 적용 및 계좌 압류·가압류 시 지급 제한 가능성이 있습니다.",
        "imageURL": "/im/8.png"
    },
    {
        "title": "예금자보호 및 안전성",
        "content": "예금자보호법에 따라 1인당 5천만원까지 보호되며 안심하고 가입할 수 있습니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL, NULL, 2.45, 2.25, 12, 'S', SYSDATE, 0
);


-- 3: 저탄소 실천 적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    3,
    '저탄소 실천 적금',
    '적금',
    '환경실천',
    '생활 속 탄소배출 감축 참여 고객을 위한 환경 실천형 적금, 최대 연 2.6%',
    '[{
        "title": "저탄소 생활 실천",
        "content": "일상 속 작은 실천으로 탄소 배출을 줄이며\\n지구를 지킬 수 있습니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "환경을 위한 금융",
        "content": "친환경 금융 실천으로\\n환경 보호에 동참해 보세요.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "간편한 온라인 가입",
        "content": "모바일뱅킹 및 인터넷뱅킹으로\\n언제 어디서나 편리하게 가입 가능합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "환경 캠페인 연계 혜택",
        "content": "저탄소 실천 캠페인 참여 시\\n우대금리를 적용받을 수 있습니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "세제 혜택",
        "content": "비과세종합저축으로 가입 가능해\\n이자소득세 비과세 혜택을 누릴 수 있습니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "중도해지 유의사항",
        "content": "만기 전 해지 시\\n약정 이율보다 낮은 중도해지 이율이 적용됩니다.",
        "imageURL": "/im/6.png"
    },
    {
        "title": "예금자보호 및 안전성",
        "content": "예금자보호법에 따라\\n1인당 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL, NULL, 2.60, 2.10, 12, 'S', SYSDATE, 0
);


-- 4: BNK지역사랑자유적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    4,
    'BNK지역사랑자유적금',
    '적금',
    '지역사회공헌',
    '지역사회 공헌과 자금 마련을 함께 실천하는 자유적립식 적금, 최대 연 2.0%',
    '[{
        "title": "자유롭게 적립 가능",
        "content": "불입액과 불입일자에 관계없이 자유롭게 적립할 수 있습니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "지역사회 발전 기여",
        "content": "이익의 일부가 지역사회 공헌 활동에 사용됩니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "간편한 가입",
        "content": "모바일뱅킹, 인터넷뱅킹으로 언제든지 편리하게 가입 가능합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "사회공헌 활동 참여",
        "content": "지역사회 기여와 함께 자산 형성을 도모할 수 있습니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "세제 혜택",
        "content": "비과세종합저축으로 가입 가능하여 이자소득세가 면제됩니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "중도해지 유의사항",
        "content": "만기 전 해지 시 약정 금리보다 낮은 중도해지 이율이 적용됩니다.",
        "imageURL": "/im/6.png"
    },
    {
        "title": "예금자보호 및 안전성",
        "content": "예금자보호법에 따라 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL, NULL, 2.00, 1.90, 12, 'S', SYSDATE, 0
);


-- 5: 가계우대정기적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    5,
    '가계우대정기적금',
    '적금',
    '목돈마련',
    '매월 일정 금액으로 목돈 마련에 유리한 정기적금, 최대 연 2.0%',
    '[{
        "title": " 매월 일정 금액 적립",
        "content": "목돈 마련에 유리한 개인 전용 정기적금 상품입니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": " 우대금리 적용",
        "content": "우대조건 충족 시 추가 금리를 제공받을 수 있습니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": " 간편한 가입",
        "content": "모바일뱅킹, 인터넷뱅킹으로 간편하게 가입 가능합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": " 중도해지 유의사항",
        "content": "만기 전 해지 시 약정 금리보다 낮은 중도해지 이율이 적용됩니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": " 예금자보호 및 안전성",
        "content": "예금자보호법에 따라 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL, NULL, 2.00, 2.00, 12, 'S', SYSDATE, 0
);


-- 6: 아이사랑 적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    6,
    '아이사랑 적금',
    '적금',
    '육아자금',
    '자녀 일정 연령 도달 시 우대금리 제공, 최대 연 7.0%',
    '[{
        "title": "육아특화 상품",
        "content": "자녀 일정 연령 도달 시 우대금리를 제공하는 육아 특화 적금 상품입니다.",
        "imageURL": "/ani/mother.gif"
    },
    {
        "title": "높은 금리 혜택",
        "content": "기본금리와 함께 우대금리 적용 시 최대 연 7.0% 제공",
        "imageURL": "/ani/interest.gif"
    },
    {
        "title": "간편한 가입",
        "content": "스마트폰과 인터넷뱅킹으로 쉽고 간편하게 가입 가능",
        "imageURL": "/ani/touchscreen.gif"
    },
    {
        "title": "예금자보호 및 안정성",
        "content": "예금자보호법에 따라 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/ani/protection.gif"
    }]',
    NULL, NULL, 7.00, 1.90, 12, 'S', SYSDATE, 150
);


-- 7: 아기천사 적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    7,
    '아기천사 적금',
    '적금',
    '육아자금',
    '자녀 출산 시 우대금리를 제공하는 출산 특화 적금, 최대 연 7.0%',
    '[{
        "title": "출산 가정 우대 혜택",
        "content": "출산 가정에 우대금리를 제공하여 육아자금 마련을 지원합니다.",
        "imageURL": "/ani/baby.gif"
    },
    {
        "title": "높은 금리 혜택",
        "content": "우대 조건 충족 시 최대 연 7.0% 금리 제공으로 자산 형성을 돕습니다.",
        "imageURL": "/ani/interest.gif"
    },
    {
        "title": "간편한 비대면 가입",
        "content": "모바일뱅킹과 인터넷뱅킹을 통해 간편하게 가입 가능합니다.",
        "imageURL": "/ani/touchscreen.gif"
    },
    {
        "title": "육아자금 목표 설정",
        "content": "매월 납입 금액과 목표 기간을 설정하여 체계적으로 자산을 모을 수 있습니다.",
        "imageURL": "/ani/5.gif"
    },
    {
        "title": "중도해지 유의사항",
        "content": "만기 전 해지 시 약정 이율보다 낮은 중도해지 이율이 적용됩니다.",
        "imageURL": "/ani/alert.gif"
    },
    {
        "title": "예금자보호 및 안정성",
        "content": "예금자보호법에 따라 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/ani/protection.gif"
    }]',
    NULL, NULL, 7.00, 1.90, 12, 'S', SYSDATE, 140
);


-- 8: 너만솔로 적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    8,
    '너만솔로 적금',
    '적금',
    '결혼자금',
    '결혼 시 우대금리를 제공하는 결혼 특화 적금, 최대 연 7.0%',
    '[{
        "title": "1️⃣ 결혼 준비 자금 마련",
        "content": "결혼을 준비하는 고객에게 우대금리를 제공하여 자금 마련을 돕습니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "2️⃣ 높은 금리 혜택",
        "content": "기본금리 외 추가 우대금리로 최대 연 7.0% 금리 제공",
        "imageURL": "/im/2.png"
    },
    {
        "title": "3️⃣ 자유적립 가능",
        "content": "자유롭게 금액과 기간을 설정해 적립할 수 있습니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "4️⃣ 간편한 가입 및 관리",
        "content": "모바일뱅킹, 인터넷뱅킹으로 간편하게 관리 가능",
        "imageURL": "/im/4.png"
    },
    {
        "title": "5️⃣ 중도해지 유의사항",
        "content": "만기 전 해지 시 중도해지 이율 적용",
        "imageURL": "/im/5.png"
    },
    {
        "title": "6️⃣ 예금자보호 및 안정성",
        "content": "예금자보호법에 따라 1인당 5천만원까지 보호",
        "imageURL": "/im/7.png"
    }]',
    NULL, NULL, 7.00, 1.90, 12, 'S', SYSDATE, 0
);


-- 9: 부산이라 좋다 Big적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    9,
    '부산이라 좋다 Big적금',
    '적금',
    '지역특화',
    '부산 핫플 위치 인증 시 우대금리를 제공하는 지역특화 적금, 최대 연 4.4%',
    '[{
        "title": "1️⃣ 부산 지역 특화 상품",
        "content": "부산 핫플 위치 인증 시 우대금리 제공",
        "imageURL": "/im/2.png"
    },
    {
        "title": "2️⃣ 높은 금리 혜택",
        "content": "기본금리 외 위치 인증 시 우대금리 적용, 최대 연 4.4%",
        "imageURL": "/im/3.png"
    },
    {
        "title": "3️⃣ 자유적립 방식",
        "content": "원하는 금액과 기간을 자유롭게 설정 가능",
        "imageURL": "/im/4.png"
    },
    {
        "title": "4️⃣ 간편한 가입",
        "content": "모바일뱅킹 및 인터넷뱅킹으로 간편하게 가입",
        "imageURL": "/im/5.png"
    },
    {
        "title": "5️⃣ 예금자보호 및 안전성",
        "content": "예금자보호법에 따라 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL, NULL, 4.40, 2.20, 12, 'S', SYSDATE, 0
);


-- 10: 꿈이룸 적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    10,
    '꿈이룸 적금',
    '적금',
    '자녀교육자금',
    '자녀 성장 단계별 맞춤 적립, 최대 연 2.85%의 교육 자금 마련 적금',
    '[{
        "title": "1️⃣ 자녀 교육 자금 특화",
        "content": "자녀 성장 단계에 맞춰 유연하게 자금을 마련할 수 있는 적금 상품입니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "2️⃣ 우대 금리 제공",
        "content": "최대 연 2.85% 금리로 안정적 자산 증식 가능",
        "imageURL": "/im/2.png"
    },
    {
        "title": "3️⃣ 자유적립식",
        "content": "원하는 금액과 기간을 자유롭게 설정하여 적립 가능합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "4️⃣ 간편한 가입",
        "content": "영업점 및 모바일뱅킹으로 손쉽게 가입 가능",
        "imageURL": "/im/4.png"
    },
    {
        "title": "5️⃣ 예금자 보호",
        "content": "예금자보호법에 따라 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL, NULL, 2.85, 2.45, 24, 'S', SYSDATE, 0
);


-- 11: 부산은행 장병내일준비적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    11,
    '부산은행 장병내일준비적금',
    '적금',
    '군복무자금',
    '군 복무 중 목돈 마련을 위한 장병 전용 적금, 최대 연 5.1%',
    '[{
        "title": "1️⃣ 군복무자 전용 적금",
        "content": "군 복무 중인 청년의 목돈 마련을 지원합니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "2️⃣ 높은 금리 혜택",
        "content": "최대 연 5.1% 금리로 자산 증식",
        "imageURL": "/im/2.png"
    },
    {
        "title": "3️⃣ 편리한 적립식",
        "content": "자유롭게 적립하고 관리 가능합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "4️⃣ 간편한 가입",
        "content": "모바일뱅킹 및 영업점 가입 가능",
        "imageURL": "/im/4.png"
    },
    {
        "title": "5️⃣ 예금자 보호",
        "content": "예금자보호법에 따라 1인당 5천만원까지 보호",
        "imageURL": "/im/7.png"
    }]',
    NULL, 
    NULL, 
    5.10, 
    5.00, 
    15, 
    'S', 
    SYSDATE, 
    0
);


-- 12: 부산은행 청년도약계좌
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    12,
    '부산은행 청년도약계좌',
    '적금',
    '청년자립자금',
    '청년 비과세 혜택 및 정부기여금 지원 계좌, 최대 연 6.0%',
    '[{
        "title": "1️⃣ 청년 자립 지원",
        "content": "청년을 위한 비과세 및 정부 기여금 지원 적금",
        "imageURL": "/im/1.png"
    },
    {
        "title": "2️⃣ 높은 금리 혜택",
        "content": "최대 연 6.0% 금리 제공",
        "imageURL": "/im/2.png"
    },
    {
        "title": "3️⃣ 비과세 혜택",
        "content": "이자소득세 면제 가능",
        "imageURL": "/im/3.png"
    },
    {
        "title": "4️⃣ 자유적립식",
        "content": "원하는 금액과 기간을 자유롭게 설정 가능",
        "imageURL": "/im/4.png"
    },
    {
        "title": "5️⃣ 예금자 보호",
        "content": "예금자보호법에 따라 1인당 5천만원까지 보호",
        "imageURL": "/im/7.png"
    }]',
    NULL, 
    NULL, 
    6.00, 
    4.00, 
    60, 
    'S', 
    SYSDATE, 
    0
);


-- 13: 펫 적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME, 
    CATEGORY, 
    PURPOSE, 
    SUMMARY,
    DETAIL, 
    MODAL_DETAIL, 
    MODAL_RATE, 
    MAX_RATE, 
    MIN_RATE,
    PERIOD, 
    STATUS, 
    CREATED_AT, 
    VIEW_COUNT
) VALUES (
    13,
    '펫 적금',
    '적금',
    '목돈마련',
    '펫 다이어리 작성 시 우대금리를 받는 자유/정기적립식 적금, 최대 연 2.9%',
    '[{
        "title": "1️⃣ 펫 다이어리 작성 혜택",
        "content": "펫 다이어리를 작성하여 최대 0.50%p까지 우대금리를 받고 적금을 운영하세요.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "2️⃣ 가입 대상 및 조건",
        "content": "실명의 개인고객 누구나 1인 1계좌 가입 가능하며, 1만원 이상 50만원 이하 자유롭게 불입 가능합니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "3️⃣ 다양한 가입 경로",
        "content": "영업점, 모바일뱅킹을 통해 쉽고 빠르게 가입할 수 있습니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "4️⃣ 우대이율",
        "content": "펫 신용카드 사용, 동물등록증 등록, 자동이체, 펫 다이어리 작성 실적에 따라 최대 0.90%p 우대금리를 받을 수 있습니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "5️⃣ 세제혜택 및 안전성",
        "content": "비과세종합저축으로 가입 시 이자소득세 면제 혜택을 누리며, 예금자보호법에 따라 5천만원까지 보호됩니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "6️⃣ 만기 및 중도해지 안내",
        "content": "만기일시지급식으로 이자가 지급되며, 중도해지 시 약정 이율보다 낮은 중도해지 이율이 적용됩니다.",
        "imageURL": "/im/6.png"
    },
    {
        "title": "7️⃣ 금융소비자 권리",
        "content": "계약체결 전 상품설명서 확인 및 설명 요청 권리가 있으며, 문의는 고객센터 또는 영업점을 이용해 주세요.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    2.90,
    2.00,
    12,
    'S',
    SYSDATE,
    0
);


-- 14: BNK희망가꾸기적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    14,
    'BNK희망가꾸기적금',
    '적금',
    '목돈마련',
    '기초생활수급자, 새터민, 다문화가정 등 목돈마련 지원 고금리 적금, 최대 연 4.70%',
    '[{
        "title": "1️⃣ 지원대상 전용 고금리",
        "content": "기초생활수급자, 소년소녀가장, 새터민, 다문화가정, 한부모가정, 장애인 등 대상 맞춤형 고금리 제공.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "2️⃣ 가입 조건과 한도",
        "content": "가입기간 12~36개월, 매월 1천원~25만원 범위 내 자유롭게 적립 가능.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "3️⃣ 우대이율 혜택",
        "content": "만기해지 시 무조건 2.50%p 우대금리를 적용해 최종 연 4.70% 금리 혜택 제공.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "4️⃣ 안전한 예금자보호",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 합해 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    },
    {
        "title": "5️⃣ 세제 혜택",
        "content": "비과세종합저축 가입 시 이자소득세 면제 혜택을 받을 수 있습니다.",
        "imageURL": "/im/6.png"
    },
    {
        "title": "6️⃣ 편리한 가입 및 관리",
        "content": "영업점 방문을 통해 간편하게 가입 가능, 자유적립식으로 편리하게 관리 가능합니다.",
        "imageURL": "/im/9.png"
    },
    {
        "title": "7️⃣ 유의사항 안내",
        "content": "만기 이전 해지 시 약정 이율보다 낮은 중도해지 이율이 적용되며, 우대금리는 적용되지 않습니다.",
        "imageURL": "/im/10.png"
    }]',
    NULL,
    NULL,
    4.70,
    2.20,
    12,
    'S',
    SYSDATE,
    0
);


-- 15: 백세청춘 실버적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    15,
    '백세청춘 실버적금',
    '적금',
    '시니어',
    '만 65세 이상 시니어 고객 전용, 1년 단위 재예치, 연복리 효과 및 분할인출 가능, 최고 연 2.35%',
    '[{
        "title": "1️⃣ 시니어 전용 적금",
        "content": "만 65세 이상 고객을 위한 시니어 전용 적금 상품으로, 여유 자금을 안정적으로 운용 가능합니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "2️⃣ 1년 단위 재예치 및 연복리",
        "content": "1년 단위로 자동 재예치되며 연복리 효과를 누릴 수 있습니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "3️⃣ 우대금리 제공",
        "content": "만 70세, 80세 이상 고객에게 단계별 우대금리를 제공하여 이율을 높입니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "4️⃣ 분할 인출 기능",
        "content": "적금 해지 없이 최대 3회까지 분할 인출이 가능하여 긴급 자금에도 유연하게 대응 가능합니다.",
        "imageURL": "/im/4.png"
    }]',
    NULL,
    NULL,
    2.35,
    1.75,
    12,
    'S',
    SYSDATE,
    0
);


-- 16: 상호부금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    16,
    '상호부금',
    '부금',
    '목돈마련',
    '매월 일정 금액 납입, 만기 시 목돈 수령 가능한 부금 상품, 최고 연 2.10% 제공',
    '[{
        "title": "1️⃣ 매월 납입하여 목돈 마련",
        "content": "매월 일정 금액을 납입해 만기 시 목돈을 마련할 수 있는 적립형 금융상품입니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "2️⃣ 유연한 납입 구조",
        "content": "가입자가 선택한 기간 동안 일정한 금액을 매월 납입하며 자금 계획에 맞게 운용 가능합니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "3️⃣ 안정적인 금리 혜택",
        "content": "만기 해지 시 최고 연 2.10%의 금리를 제공받으며 목돈 마련에 유리합니다.",
        "imageURL": "/im/6.png"
    },
    {
        "title": "4️⃣ 예금자보호 혜택",
        "content": "예금자보호법에 따라 원금과 이자를 합해 1인당 5천만원까지 보호되어 안심할 수 있습니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    2.10,
    1.70,
    12,
    'S',
    SYSDATE,
    0
);


-- 17: 정기적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    17,
    '정기적금',
    '적금',
    '목돈마련',
    '매월 일정 금액을 적립해 만기 시 목돈을 수령하는 가장 기본적인 적금 상품, 최고 연 2.10% 제공',
    '[{
        "title": "1️⃣ 매월 적립으로 목돈 마련",
        "content": "매월 일정 금액을 적립해 만기 시 목돈을 마련할 수 있는 가장 기본적인 목돈마련 상품입니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "2️⃣ 자유롭거나 정액으로 납입 가능",
        "content": "가입자의 상황에 맞게 자유적립식 또는 정액적립식으로 납입 방식을 선택할 수 있습니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "3️⃣ 만기 시 안정적 이자 수령",
        "content": "만기 해지 시 최고 연 2.10%의 이자를 제공받으며 목돈을 마련할 수 있습니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "4️⃣ 예금자보호로 안전하게",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 합해 5천만원까지 보호되어 안심할 수 있습니다.",
        "imageURL": "/im/4.png"
    }]',
    NULL,
    NULL,
    2.10,
    1.80,
    12,
    'S',
    SYSDATE,
    0
);


-- 18: BNK내맘대로예금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    18,
    'BNK내맘대로예금',
    '예금',
    '목돈마련',
    '기간, 금액, 이율을 고객이 자유롭게 선택하여 운용할 수 있는 맞춤형 예금 상품, 최고 연 2.40%',
    '[{
        "title": "자유롭게 기간과 금액 설정",
        "content": "가입자가 원하는 기간과 금액으로 자유롭게 설정해 목돈을 계획적으로 운용할 수 있습니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "최대 연 2.40%의 금리 혜택",
        "content": "우대이율 조건 충족 시 최고 연 2.40%의 금리를 제공하여 안정적인 수익을 기대할 수 있습니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "만기일시 또는 월이자 지급",
        "content": "이자 수령 방식을 만기일시지급식 또는 월이자지급식으로 선택하여 계획적으로 활용 가능합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "예금자보호로 안전하게",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 합해 5천만원까지 보호되어 안심하고 예치할 수 있습니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    2.40,
    2.10,
    12,
    'S',
    SYSDATE,
    0
);


-- 19: 저탄소 실천 예금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    19,
    '저탄소 실천 예금',
    '예금',
    '환경실천',
    '생활 속 탄소 저감 실천에 참여하며 금리 혜택을 받을 수 있는 친환경 정기예금, 최고 연 2.20%',
    '[{
        "title": "1️⃣ 탄소 저감 실천 참여",
        "content": "일상 속에서 에너지 절약, 대중교통 이용 등 저탄소 생활 실천에 참여해 환경 보호에 기여합니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "2️⃣ 고금리 혜택",
        "content": "우대금리 조건 충족 시 최고 연 2.20% 금리를 제공하여 안정적인 자산 증식을 지원합니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "3️⃣ 환경 보호와 자산 증식",
        "content": "탄소 저감 실천 인증 시 우대금리를 제공하며, 환경 보호와 자산 증식을 동시에 실천할 수 있습니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "4️⃣ 안전한 예금자보호",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 합해 5천만원까지 보호되어 안심하고 예치 가능합니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    2.20,
    1.90,
    12,
    'S',
    SYSDATE,
    0
);


-- 20: 더(The) 특판 정기예금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    20,
    '더(The) 특판 정기예금',
    '예금',
    '목돈굴리기',
    '판매 기간에만 가입 가능한 특판 전용 정기예금, 최고 연 2.60%',
    '[{
        "title": "더(The) 특판 정기예금",
        "content": "판매 기간에만 가입 가능한 특판 전용 정기예금으로, 목돈을 안정적으로 불리며 높은 이율을 제공합니다.",
        "imageURL": "/im/3.png"
    }]',
    NULL,
    NULL,
    2.60,
    1.90,
    12,
    'S',
    SYSDATE,
    0
);


-- 21: LIVE 정기예금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    21,
    'LIVE 정기예금',
    '예금',
    '목돈굴리기',
    '특판 전용 고금리 정기예금, 단기 및 1년 운용에 유리하며 최대 연 2.40%',
    '[{
        "title": "특판 전용 고금리",
        "content": "특판 기간 동안만 가입 가능한 LIVE 정기예금으로 최대 연 2.40% 고금리 혜택을 누릴 수 있습니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "가입 조건과 유연한 기간",
        "content": "1천만원 이상, 1개월부터 60개월까지 유연하게 선택해 가입 가능합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "단기 운용에 유리",
        "content": "특판 기간 3개월 이상, 12개월 이하 가입 시 최대 금리를 적용받아 단기 목돈 운용에 유리합니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "안전한 예금자보호",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 합해 5천만원까지 보호받을 수 있습니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    2.40,
    1.30,
    12,
    'S',
    SYSDATE,
    0
);


-- 22: 메리트정기예금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    22,
    '메리트정기예금',
    '예금',
    '목돈굴리기',
    '시장실세금리 연동 확정금리 정기예금, 최고 연 2.05%',
    '[{
        "title": "메리트정기예금",
        "content": "시장실세금리에 연동한 약정금리를 만기일까지 적용하는 확정금리 단기 고금리 상품입니다.",
        "imageURL": "/im/2.png"
    }]',
    NULL,
    NULL,
    2.05,
    2.05,
    12,
    'S',
    SYSDATE,
    0
);


-- 23: 더(The) 레벨업 정기예금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    23,
    '더(The) 레벨업 정기예금',
    '예금',
    '목돈굴리기',
    '비대면 재예치 시 우대금리를 적용하는 6개월·1년제 전용 정기예금, 최대 연 2.55%',
    '[{
        "title": "1️⃣ 비대면 재예치 시 우대금리",
        "content": "모바일뱅킹 비대면 재예치 시 최대 0.20%p 우대금리를 적용해 이율을 높일 수 있습니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "2️⃣ 가입 조건과 금리",
        "content": "1백만원 이상부터 가입 가능하며, 6개월 또는 1년으로 선택해 운용 가능합니다.",
        "imageURL": "/im/6.png"
    },
    {
        "title": "3️⃣ 안전한 자산관리",
        "content": "가입과 동시에 모바일에서 간편하게 관리 가능하며, 만기 시 자동 재예치 설정으로 편리하게 관리할 수 있습니다.",
        "imageURL": "/im/8.png"
    },
    {
        "title": "4️⃣ 예금자보호 혜택",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 합해 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    2.55,
    2.35,
    12,
    'S',
    SYSDATE,
    0
);


-- 24: 장기회전정기예금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    24,
    '장기회전정기예금',
    '예금',
    '목돈굴리기',
    '1년 단위 회전으로 약정 이율 변경, 최대 15년까지 운용 가능한 장기회전형 정기예금, 연 2.25%',
    '[{
        "title": "장기운용 가능",
        "content": "최대 15년까지 운용 가능해 장기적인 목돈 관리와 자산 증식에 유리합니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "1년 단위 회전 이율 적용",
        "content": "1년 단위로 약정 이율이 변경 적용되며, 시장 금리에 따라 유연한 이자 수취가 가능합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "중도해지 시 유리",
        "content": "1년 미만 회전 기간에 대해서만 중도해지 이율이 적용되므로 일반 정기예금보다 유리합니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "안전한 예금자보호",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 합해 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    2.25,
    2.25,
    12,
    'S',
    SYSDATE,
    0
);


-- 25: 마이플랜 ISA 정기예금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    25,
    '마이플랜 ISA 정기예금',
    '예금',
    'ISA',
    '개인종합자산관리계좌(ISA) 전용 정기예금으로, 1만원 이상 소액부터 최대 연 2.41%의 금리를 제공',
    '[{
        "title": "ISA 전용 고금리 상품",
        "content": "ISA 계좌를 통해서만 가입 가능한 전용 정기예금으로, 목돈 운용 및 절세 효과를 동시에 제공합니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "다양한 가입기간",
        "content": "3개월, 6개월, 1년, 2년, 3년 등 원하는 기간을 선택해 자산을 유연하게 관리할 수 있습니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "안정적인 원금 보장",
        "content": "한국예탁결제원 전문 수신을 통해 ISA 계좌 내 안전하게 운용되며 원금과 이자를 보장합니다.",
        "imageURL": "/im/8.png"
    },
    {
        "title": "예금자보호 적용",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 합산해 5천만원까지 보호됩니다.",
        "imageURL": "/im/9.png"
    }]',
    NULL,
    NULL,
    2.41,
    2.00,
    12,
    'S',
    SYSDATE,
    0
);


-- 26: 마이플랜 퇴직연금 정기예금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    26,
    '마이플랜 퇴직연금 정기예금',
    '예금',
    '퇴직연금',
    '퇴직연금 원리금 보장형 운용을 위한 정기예금으로, 1원부터 가입 가능, 최대 연 1.95%',
    '[{
        "title": "퇴직연금 전용 상품",
        "content": "퇴직금을 원리금 보장형 연금자산으로 안전하게 운용할 수 있는 퇴직연금 전용 정기예금입니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "소액부터 가입 가능",
        "content": "1원부터 소액으로도 운용 가능하여 기업 및 근로자가 유연하게 퇴직연금 자산을 운용할 수 있습니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "다양한 기간 설정",
        "content": "6개월부터 60개월까지 자유롭게 설정 가능하여 퇴직연금의 목표 기간에 맞춰 유연하게 관리할 수 있습니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "안정적인 원금 보장",
        "content": "퇴직연금 전용 계좌로 운용 시 원금과 이자를 안전하게 보장하며, 예금자보호가 적용됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    1.95,
    1.80,
    12,
    'S',
    SYSDATE,
    0
);


-- 27: 정기예금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    27,
    '정기예금',
    '예금',
    '목돈굴리기',
    '목돈을 일정 기간 예치해 매월 이자 또는 만기 일시 수령, 최대 연 1.95% 제공',
    '[{
        "title": "목돈 운용의 시작",
        "content": "안정적인 자산 관리를 원하는 고객을 위한 기본 정기예금 상품으로, 단기부터 장기까지 목돈을 안전하게 운용합니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "다양한 기간 선택",
        "content": "1개월부터 60개월까지 원하는 기간으로 설정해 운용할 수 있어 자금 계획에 맞춘 유연한 관리가 가능합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "이자 수령 방식 선택",
        "content": "매월 이자를 수령하거나 만기 시 일시 수령 방식 중 선택해 운영할 수 있어 필요에 따라 활용이 가능합니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "안전한 예금자 보호",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 포함해 5천만원까지 보호받을 수 있어 안심하고 운용할 수 있습니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    1.95,
    1.80,
    12,
    'S',
    SYSDATE,
    0
);


-- 28: 환매조건부매도
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    28,
    '환매조건부매도',
    '예금',
    '단기자금운용',
    '은행 보유 국공채 등을 일정기간 후 다시 매입하는 단기 투자 상품, 최대 연 2.00%',
    '[{
        "title": "단기 자금 운용",
        "content": "짧은 기간 동안 여유 자금을 안정적으로 운용할 수 있는 상품으로, 만기 시 안정적인 수익을 제공합니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "채권 담보 투자 구조",
        "content": "은행이 보유한 국공채 등 채권을 고객에게 판매 후 일정 기간 후 다시 매입하는 방식으로 운영됩니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "유연한 가입 기간",
        "content": "30일에서 365일까지 일 단위로 설정 가능해 자금 운용 계획에 따라 유연하게 관리할 수 있습니다.",
        "imageURL": "/im/9.png"
    },
    {
        "title": "수익률 제공",
        "content": "365일 기준 최대 연 2.00%의 수익률로 단기 자금을 효율적으로 운영할 수 있습니다.",
        "imageURL": "/im/6.png"
    }]',
    NULL,
    NULL,
    2.00,
    1.80,
    12,
    'S',
    SYSDATE,
    0
);


-- 29: 표지어음매출
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    29,
    '표지어음매출',
    '예금',
    '단기자금운용',
    '은행이 발행인 및 지급인이 되어 고객에게 약속어음을 발행, 양도가 자유롭고 단기 고수익 운용 가능, 연 2.00%',
    '[{
        "title": "단기 여유자금 운용",
        "content": "짧은 기간 동안 여유자금을 효율적으로 운용하여 안정적인 수익을 기대할 수 있습니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "약속어음 기반 상품",
        "content": "은행이 발행인 및 지급인이 되어 고객에게 약속어음을 발행하는 구조로 안전하게 운영됩니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "자유로운 양도 가능",
        "content": "무기명식 할인발행 방식으로 언제든지 자유롭게 양도가 가능해 유동성 관리에 유리합니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "고수익 단기 투자",
        "content": "1년 기준 최대 연 2.00%의 수익률로 단기 고수익 투자가 가능합니다.",
        "imageURL": "/im/9.png"
    }]',
    NULL,
    NULL,
    2.00,
    1.80,
    12,
    'S',
    SYSDATE,
    0
);


-- 30: 회전플러스 정기예금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    30,
    '회전플러스 정기예금',
    '예금',
    '단기자금운용',
    '회전기간 단위로 이율이 변경 적용되어 금리 상승기에 유리한 회전식 정기예금, 최대 연 2.00%',
    '[{
        "title": "회전식 정기예금",
        "content": "단기자금 운용 및 금리상승기에 유리하게 회전기간 단위로 이율을 변경 적용합니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "유연한 중도해지",
        "content": "가입 시 선택한 회전기간만 충족 시 중도해지해도 불이익이 없어 유연한 자금운용이 가능합니다.",
        "imageURL": "/im/6.png"
    },
    {
        "title": "우대이율 혜택",
        "content": "6개월 이상 예치 시 회전 시 0.10%p의 우대이율을 제공합니다.",
        "imageURL": "/im/9.png"
    },
    {
        "title": "안전한 예금자보호",
        "content": "예금자보호법에 따라 원금과 이자를 합해 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    2.00,
    1.90,
    12,
    'S',
    SYSDATE,
    0
);


-- 31: 백세청춘 실버정기예금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    31,
    '백세청춘 실버정기예금',
    '예금',
    '시니어',
    '만 65세 이상 시니어 대상 우대금리 제공, 헬스케어 혜택 및 만기 시 고금리 적용, 최대 연 2.35%',
    '[{
        "title": "시니어 전용 우대금리",
        "content": "만 65세 이상 고객에게 연령대별 우대금리를 제공하여 안정적으로 자산을 불릴 수 있는 예금입니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "헬스케어 혜택 제공",
        "content": "30백만원 이상 예치 시 1년간 건강상담, 병원예약 지원, 건강정보 제공 등의 헬스케어 서비스를 제공합니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "유연한 가입 조건",
        "content": "가입기간 6개월~36개월, 5백만원 이상 예치 가능하며, 만 80세 이상 고객에게는 최고 연 2.35% 금리 제공.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "예금자보호 및 안전성",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 합산해 5천만원까지 보호되어 안심하고 가입 가능합니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL, 
    NULL,
    2.35,
    1.75,
    12,
    'S',
    SYSDATE,
    0
);


-- 32: 양도성 예금증서
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    32,
    '양도성 예금증서',
    '예금',
    '단기운용',
    '단기 자금 운용에 적합, 무기명식 할인 발행으로 자유롭게 양도 가능, 최대 연 2.00%',
    '[{
        "title": "단기 자금 운용 최적화",
        "content": "여유자금을 단기로 안전하게 운용할 수 있는 상품으로 개인과 법인 모두 가입 가능합니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "무기명식 할인 발행",
        "content": "무기명식 할인 발행으로 자유롭게 양도가 가능하며, 유동성 자산 확보에 유리합니다.",
        "imageURL": "/im/6.png"
    },
    {
        "title": "다양한 가입 조건",
        "content": "가입 기간 30일~3년, 10백만원 이상 가입 가능, 등록발행은 5백만원 이상부터 가능하여 유연한 자금 운용이 가능합니다.",
        "imageURL": "/im/7.png"
    },
    {
        "title": "안전한 예금자 보호",
        "content": "본 상품은 예금자보호법에 따라 보호되지 않지만, 부산은행의 신용으로 안전하게 운용됩니다.",
        "imageURL": "/im/10.png"
    }]',
    NULL,
    NULL,
    2.00,
    1.80,
    12,
    'S',
    SYSDATE,
    0
);


-- 33: 사장님 월급통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    33,
    '사장님 월급통장',
    '입출금자유',
    '사업자전용',
    '만 17세 이상 신규 개인사업자 대상 파킹금리 제공, 자동저축으로 사업자금 관리, 최대 연 1.8%',
    '[{
        "title": "신규 개인사업자 대상 파킹금리",
        "content": "만 17세 이상 신규 개인사업자 고객에게 파킹금리를 제공하여 여유 자금을 효율적으로 운용할 수 있습니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "자동저축 기능",
        "content": "입출금식 통장이지만 자동저축 기능을 활용해 일정 금액 또는 비율로 매일 자동으로 자금을 모을 수 있습니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "간편한 모바일 가입",
        "content": "영업점 방문 없이 모바일뱅킹을 통해 간편하게 개설 및 관리가 가능합니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "우대금리 혜택",
        "content": "카드가맹점 입금실적이 있는 경우 연 0.2%p의 우대금리를 추가로 제공해 드립니다.",
        "imageURL": "/im/6.png"
    },
    {
        "title": "자유로운 자금 운용",
        "content": "사업자금의 입출금이 자유롭고 최대 1천만원까지 입금 가능하여 자금 관리에 용이합니다.",
        "imageURL": "/im/8.png"
    },
    {
        "title": "안전한 예금자보호",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 합해 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    },
    {
        "title": "유의사항",
        "content": "입금 방식은 자동저축으로만 가능하며, 자동저축 미실행 시 입금이 제한될 수 있습니다.",
        "imageURL": "/im/10.png"
    }]',
    NULL,
    NULL,
    1.80,
    1.60,
    6,
    'S',
    SYSDATE,
    0
);


-- 34: BNK Welcome Global 통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    34,
    'BNK Welcome Global 통장',
    '입출금자유',
    '외국인전용',
    '국내 거주 외국인 전용 입출금통장, 수수료 면제 혜택 제공, 편리한 모바일/영업점 가입 가능',
    '[{
        "title": "외국인 전용 입출금통장",
        "content": "외국인등록증 또는 여권을 보유한 국내 거주 외국인 개인을 위한 입출금 통장입니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "다양한 수수료 면제 혜택",
        "content": "인터넷·모바일뱅킹 이체수수료 무제한 면제, 자동화기기 수수료도 면제 혜택을 제공합니다.",
        "imageURL": "/im/6.png"
    },
    {
        "title": "자유로운 가입 방법",
        "content": "영업점 방문 또는 모바일뱅킹을 통해 간편하게 가입 가능합니다. 단, 여권은 영업점에서만 가입 가능합니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "조건 충족 시 우대 혜택 제공",
        "content": "급여이체, BC카드 사용, 잔액 유지, 공과금 자동이체 등의 조건 충족 시 수수료 면제 혜택이 지속 적용됩니다.",
        "imageURL": "/im/9.png"
    },
    {
        "title": "자유로운 자금 관리",
        "content": "입출금이 자유로워 생활비, 급여 관리에 유용하며 언제든 사용 가능합니다.",
        "imageURL": "/im/10.png"
    },
    {
        "title": "안전한 예금자 보호",
        "content": "예금자보호법에 따라 1인당 5천만원까지 원금과 이자를 보호받을 수 있어 안전합니다.",
        "imageURL": "/im/7.png"
    },
    {
        "title": "유의사항 안내",
        "content": "사기이용계좌 사용 시 지급 정지 등의 제한이 발생할 수 있으니 주의 바랍니다.",
        "imageURL": "/im/11.png"
    }]',
    NULL,
    NULL,
    0.30,
    0.10,
    6,
    'S',
    SYSDATE,
    0
);


-- 35: 동백통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    35,
    '동백통장',
    '입출금자유',
    '지역화폐 연계',
    '부산지역화폐 동백전 충전·결제 시 수수료 면제 및 우대금리를 제공하는 부산 경제활성화 특화 입출금 통장',
    '[{
        "title": "부산 지역화폐 연계 통장",
        "content": "부산지역화폐 동백전과 연계하여 경제 활성화 및 고객 혜택을 높인 전용 통장입니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "우대금리 제공",
        "content": "동백전 충전 및 결제 실적에 따라 최대 0.50%p 우대이율을 제공하여 이자 혜택을 높일 수 있습니다.",
        "imageURL": "/im/6.png"
    },
    {
        "title": "수수료 면제 혜택",
        "content": "인터넷·모바일 이체, ATM 출금 및 이체 수수료, SMS 통지 수수료 등 다양한 수수료를 조건 충족 시 횟수 제한 없이 면제받을 수 있습니다.",
        "imageURL": "/im/8.png"
    },
    {
        "title": "자유로운 입출금",
        "content": "입출금이 자유로운 통장으로 생활비 및 지역화폐 연계 자금 관리를 효율적으로 할 수 있습니다.",
        "imageURL": "/im/9.png"
    },
    {
        "title": "간편한 가입과 관리",
        "content": "모바일뱅킹 또는 영업점을 통해 간편하게 개설 가능하며 관리가 쉽습니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "예금자 보호",
        "content": "예금자보호법에 따라 1인당 5천만원까지 원금과 이자를 보호받을 수 있어 안전합니다.",
        "imageURL": "/im/7.png"
    },
    {
        "title": "유의사항 안내",
        "content": "동백전 충전 및 결제 실적이 없거나 해지 시 우대금리가 적용되지 않을 수 있으니 주의하세요.",
        "imageURL": "/im/11.png"
    }]',
    NULL,
    NULL,
    0.51,
    0.10,
    6,
    'S',
    SYSDATE,
    0
);


-- 36: 모임통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    36,
    '모임통장',
    '입출금자유',
    '모임자금관리',
    '가족, 친구, 동호회 등 모임자금을 투명하고 쉽게 관리할 수 있는 전용 입출금 통장',
    '[{
        "title": "모임자금 관리 특화",
        "content": "가족, 친구, 동호회 모임자금을 관리하기 위한 전용 통장으로 모임주, 모임원이 투명하게 자금을 관리할 수 있습니다.",
        "imageURL": "/ani/people.gif"
    },
    {
        "title": "모임관리 서비스 제공",
        "content": "모바일 앱으로 회비 조회, 회비 관리, 모임원 관리 등 모임 전용 서비스를 제공하여 관리 편의성을 높입니다.",
        "imageURL": "/ani/payment.gif"
    },
    {
        "title": "우수모임 혜택",
        "content": "우수모임으로 선정 시 이체·출금 수수료 면제, SMS 수수료 면제 등의 혜택을 제공합니다.",
        "imageURL": "/ani/medal.gif"
    },
    {
        "title": "환전 수수료 할인",
        "content": "모임통장 가입 고객은 환전 시 수수료를 최대 70%까지 할인받을 수 있습니다.",
        "imageURL": "/ani/trip.gif"
    },
    {
        "title": "입출금 자유",
        "content": "보통예금, 자유저축예금으로 자유롭게 입출금하며 모임 자금을 관리할 수 있습니다.",
        "imageURL": "/ani/phone.gif"
    },
    {
        "title": "예금자보호",
        "content": "예금자보호법에 따라 1인당 5천만원까지 원금과 이자를 보호받을 수 있어 안전하게 운용 가능합니다.",
        "imageURL": "/ani/protection.gif"
    },
    {
        "title": "유의사항 안내",
        "content": "모임주의 압류 및 가압류 발생 시 자금 사용 및 서비스가 제한될 수 있으니 주의 바랍니다.",
        "imageURL": "/im/6.png"
    }]',
    NULL,
    NULL,
    0.20,
    0.10,
    6,
    'S',
    SYSDATE,
    0
);


-- 37: BNK사학연금 평생안심통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    37,
    'BNK사학연금 평생안심통장',
    '입출금자유',
    '사학연금전용',
    '사학연금 수급자를 위한 압류방지 및 수수료 혜택 전용 입출금 통장',
    '[{
        "title": "사학연금 전용 통장",
        "content": "사학연금 수급자를 위한 전용 압류방지 통장으로 안심하고 연금을 관리할 수 있습니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "압류방지 기능",
        "content": "법원의 압류명령에도 압류가 불가능하여 연금 수급권 보호가 철저히 이루어집니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "수수료 면제 혜택",
        "content": "연금 입금 실적에 따라 이체, 출금, SMS 등 각종 수수료가 면제되어 부담 없이 이용 가능합니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "입출금 자유",
        "content": "연금 전용 입금 통장이지만 출금은 자유롭게 가능하여 연금 활용이 편리합니다.",
        "imageURL": "/im/8.png"
    },
    {
        "title": "비과세종합저축 가능",
        "content": "비과세종합저축으로 등록하여 이자소득세 면제 혜택까지 받을 수 있습니다.",
        "imageURL": "/im/9.png"
    },
    {
        "title": "예금자보호",
        "content": "예금자보호법에 따라 1인당 5천만원까지 원금과 이자를 보호받을 수 있습니다.",
        "imageURL": "/im/7.png"
    },
    {
        "title": "유의사항 안내",
        "content": "사학연금 전용 통장으로 연금 이외의 자금 입금이 불가하며, 신용카드 결제계좌 등으로 등록 시 유의가 필요합니다.",
        "imageURL": "/im/11.png"
    }]',
    NULL,
    NULL,
    0.10,
    0.05,
    6,
    'S',
    SYSDATE,
    0
);


-- 38: 마!이통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    38,
    '마!이통장',
    '입출금자유',
    'MZ세대전용',
    '만 14세~39세 MZ세대를 위한 수수료 면제 및 우대금리 제공 입출금 통장',
    '[{
        "title": "MZ세대 전용",
        "content": "만 14세 이상 만 39세 이하 MZ세대를 위한 맞춤형 혜택 통장입니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "최대 연 1.50% 금리",
        "content": "평잔 100만원 이하 금액에 대해 최대 연 1.50%의 높은 이율을 제공합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "간편결제 연동 혜택",
        "content": "네이버페이, 카카오페이, 토스 등 간편결제 연동 시 수수료 면제 혜택이 추가로 제공됩니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "다양한 수수료 면제",
        "content": "이체, 출금, 모바일뱅킹 이용 시 수수료 면제 혜택이 적용됩니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "모바일 간편가입",
        "content": "모바일뱅킹을 통해 쉽고 간단하게 비대면으로 가입이 가능합니다.",
        "imageURL": "/im/8.png"
    },
    {
        "title": "예금자보호",
        "content": "예금자보호법에 따라 1인당 5천만원까지 원금과 이자를 보호받을 수 있습니다.",
        "imageURL": "/im/7.png"
    },
    {
        "title": "유의사항 안내",
        "content": "만 40세가 되는 해의 연말까지 혜택 제공 후 다음해에 Only One 통장으로 자동 전환됩니다.",
        "imageURL": "/im/9.png"
    }]',
    NULL,
    NULL,
    1.50,
    0.10,
    6,
    'S',
    SYSDATE,
    0
);


-- 39: BNK주택연금 지킴이 통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    39,
    'BNK주택연금 지킴이 통장',
    '입출금자유',
    '주택연금수급자전용',
    '주택담보노후연금 수급자를 위한 압류방지 및 수수료 면제 전용 입출금 통장',
    '[{
        "title": "주택연금 전용 압류방지",
        "content": "주택담보노후연금 수급자의 연금 수급권 보호를 위한 압류방지 통장입니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "연금만 입금 가능",
        "content": "주택담보노후연금만 입금 가능하여 자금 보호 기능을 강화하였습니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "다양한 수수료 면제 혜택",
        "content": "연금 입금 실적에 따라 이체, 출금, 증명서 발급 등 각종 수수료 면제 혜택이 제공됩니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "예금자보호 적용",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 합산하여 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    },
    {
        "title": "개설 및 이용 절차",
        "content": "‘주택연금 전용계좌 이용대상 확인서’를 지참하여 영업점에서 개설 후 주택연금 입금계좌로 등록해야 합니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "비대면 가입 불가",
        "content": "해당 상품은 반드시 영업점 창구 방문을 통해서만 가입 가능합니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "유의사항 안내",
        "content": "연금 외 입금 불가, 타계좌 전환 불가, 연금 입금 등록 필수, 미등록 시 연금 입금 불가 등의 유의사항이 있습니다.",
        "imageURL": "/im/8.png"
    }]',
    NULL,
    NULL,
    0.10,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 40: 기업자유예금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    40,
    '기업자유예금',
    '입출금자유',
    '기업자금운용',
    '기업의 단기운전자금 운용에 적합한 입출금 자유 예금',
    '[{
        "title": "기업 단기자금 운용에 적합",
        "content": "기업의 단기 운전자금 운용에 적합하며 유동성이 높은 입출금 자유 예금 상품입니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "입금 및 출금 자유",
        "content": "사업 운영상 필요한 자금을 수시로 입출금하며 효율적으로 관리할 수 있습니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "안정적인 이자 수익",
        "content": "일일 최종 잔액 기준으로 결산 시 이자를 지급하여 안정적인 수익을 제공합니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "예금자보호 적용",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 합하여 5천만원까지 보호됩니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "간편한 가입 절차",
        "content": "사업자등록증과 실명확인증표를 지참하여 가까운 영업점에서 간편하게 가입할 수 있습니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "사업자 전용 예금",
        "content": "법인 및 개인사업자 전용 상품으로 1계좌 이상 자유롭게 운용 가능합니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "유의사항 안내",
        "content": "계좌에 압류, 가압류 등이 등록될 경우 원금 및 이자 지급이 제한되며, 금융사기 피해 방지법에 따라 거래 제한이 될 수 있습니다.",
        "imageURL": "/im/6.png"
    }]',
    NULL,
    NULL,
    0.10,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 41: 보통예금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    41,
    '보통예금',
    '입출금자유',
    '일반자금관리',
    '가입제한 없이 입출금이 자유롭고 자금 관리에 적합한 일반 예금',
    '[{
        "title": "입출금 자유",
        "content": "가입 대상 및 가입 금액에 제한이 없으며 수시로 입출금이 가능합니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "자금 관리 효율성",
        "content": "일상 생활비, 비상금, 급여 수령, 각종 자동이체 등 다양한 자금 관리에 활용할 수 있습니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "안정적인 이자 제공",
        "content": "일일 잔액을 기준으로 분기별 결산 시 안정적으로 이자를 지급합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "예금자보호 적용",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 합해 5천만원까지 보호됩니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "간단한 가입 절차",
        "content": "영업점 방문 시 실명확인증표만으로 쉽게 가입할 수 있습니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "다양한 활용 가능",
        "content": "급여 이체, 공과금 납부, 생활비 관리 등 다양한 목적으로 활용이 가능합니다.",
        "imageURL": "/im/8.png"
    },
    {
        "title": "유의사항 안내",
        "content": "계좌에 압류, 가압류 등이 등록될 경우 원금 및 이자 지급이 제한되며, 금융사기 방지법에 따라 거래가 제한될 수 있습니다.",
        "imageURL": "/im/6.png"
    }]',
    NULL,
    NULL,
    0.10,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 42: Only One 통장 (개인사업자)
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    42,
    'Only One 통장 (개인사업자)',
    '입출금자유',
    '사업자자금관리',
    '개인 및 개인사업자의 결제성 자금을 통합 관리하며 우대 수수료 면제 혜택을 제공하는 주거래 통장',
    '[{
        "title": "개인·개인사업자 전용",
        "content": "개인 및 개인사업자의 모든 결제성 자금을 통합 관리할 수 있는 부산은행 대표 주거래 통장입니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "입출금 자유",
        "content": "보통예금, 자유저축예금, 기업자유예금으로 자유롭게 입출금 가능해 유동성 관리를 효율적으로 할 수 있습니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "우대 수수료 혜택",
        "content": "급여 입금, 카드 매출 입금, 공과금 자동이체 등 실적 충족 시 월 10회~무제한으로 수수료 면제 혜택이 제공됩니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "수수료 면제 이월 기능",
        "content": "사용하지 않은 수수료 면제 횟수를 최대 10회까지 다음 달로 이월하여 더욱 효율적으로 활용 가능합니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "간편한 가입 및 관리",
        "content": "영업점 및 모바일뱅킹으로 간편하게 가입 가능하며 주거래 계좌로 편리하게 관리할 수 있습니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "다양한 부가 서비스 제공",
        "content": "썸패스, 노란우산공제, 외환실적 등 다양한 서비스와 연계해 혜택을 극대화할 수 있습니다.",
        "imageURL": "/im/8.png"
    },
    {
        "title": "예금자보호 및 유의사항",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 합해 5천만원까지 보호되며, 금융사기 방지법에 따라 거래가 제한될 수 있습니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    0.10,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 43: 부산은행 군인연금 평생안심통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    43,
    '부산은행 군인연금 평생안심통장',
    '입출금자유',
    '연금수급자',
    '군인연금 수급자를 위한 전용 압류방지 통장으로 안전하고 편리한 연금 수령 관리 가능',
    '[{
        "title": "군인연금 수급자 전용",
        "content": "국군재정관리단의 군인연금 급여를 수령하는 고객 전용으로 개설 가능한 안전한 연금수령 통장입니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "압류방지 기능",
        "content": "법원의 압류명령이 있더라도 군인연금 급여는 압류가 불가하여 안정적인 연금 수령이 가능합니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "입금제한 및 보장",
        "content": "군인연금 급여만 입금 가능하며 건당 185만원 한도 내에서 연금 수령이 가능합니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "수수료 면제 혜택",
        "content": "연금 입금 실적에 따라 각종 수수료 면제 혜택(최대 10회/월)이 제공됩니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "이자 및 관리의 편리함",
        "content": "자유저축예금 형태로 이자가 분기별 지급되며 입출금 및 관리가 간편합니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "간편한 개설 절차",
        "content": "영업점 방문 후 간단한 신청으로 개설 가능하며 국군재정관리단에 등록만 하면 바로 연금 수령이 가능합니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "예금자보호 안내",
        "content": "예금자보호법에 따라 원금과 이자를 합산하여 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    0.00,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 44: Only One 기업통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    44,
    'Only One 기업통장',
    '입출금자유',
    '기업자금관리',
    '기업의 결제성 자금을 효율적으로 관리하며 수수료 면제 혜택이 제공되는 주거래 기업통장',
    '[{
        "title": "기업 전용 주거래 통장",
        "content": "법인 및 개인사업자의 결제성 자금을 효율적으로 관리할 수 있는 대표 주거래 통장입니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "수수료 면제 혜택",
        "content": "실적 조건에 따라 월 10회, 20회, 무제한 수수료 면제 혜택이 제공되어 비용을 절감합니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "다양한 수수료 면제 항목",
        "content": "인터넷·모바일·폰뱅킹 이체, 자동화기기 수수료, SMS 통지, 증명서 발급 수수료 등이 면제됩니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "특화 부가서비스 제공",
        "content": "기업 대표자에게 무제한 수수료 면제 및 기업자금관리서비스 할인 혜택을 제공합니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "간편한 개설 및 관리",
        "content": "영업점 방문으로 간편하게 개설 가능하며 모바일로도 관리가 용이합니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "안정적인 이자관리",
        "content": "분기별로 평균잔액 기준 이자가 지급되며 기업 자금의 안정적 운용이 가능합니다.",
        "imageURL": "/im/6.png"
    },
    {
        "title": "예금자보호 안내",
        "content": "예금자보호법에 따라 원금과 이자를 합산하여 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    0.00,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 45: Only One 통장 (개인)
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    45,
    'Only One 통장 (개인)',
    '입출금자유',
    '개인자금관리',
    '개인 및 개인사업자의 결제성 자금을 통합 유치할 수 있는 부산은행 대표 주거래 통장',
    '[{
        "title": "가입대상 및 예금과목",
        "content": "개인 및 개인사업자가 가입 가능하며 보통예금, 자유저축예금, 기업자유예금(MMDA 제외)에 해당됩니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "수수료 면제 혜택",
        "content": "조건 충족 시 수수료를 월 10회/30회/무제한으로 면제하며 자동화기기, 이체, 증명서, SMS 등의 수수료가 포함됩니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "손쉬운 개설과 관리",
        "content": "영업점 방문 외에도 모바일뱅킹을 통해 손쉽게 개설 및 관리할 수 있습니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "안정적인 이자지급",
        "content": "예금일 기준으로 매일 잔액 평균을 계산해 결산일마다 이자를 지급합니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "예금자보호 안내",
        "content": "예금자보호법에 따라 1인당 원금과 이자를 합산하여 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    0.00,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 46: Only One 주니어 통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    46,
    'Only One 주니어 통장',
    '입출금자유',
    '청소년자금관리',
    '손쉬운 우대조건으로 수수료 면제 혜택이 제공되는 영유아, 아동, 청소년 용돈 관리 전용 통장',
    '[{
        "title": "가입대상 및 예금과목",
        "content": "만 19세 이하 개인 고객이 가입할 수 있으며, 예금과목은 자유저축예금(MMDA 제외)입니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "다양한 수수료 면제 혜택",
        "content": "자동이체, 평잔, 체크카드 이용 등 다양한 우대조건 충족 시 수수료를 월 10회~무제한까지 면제 받을 수 있습니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "첫 거래 우대 및 보험 혜택",
        "content": "부산은행 첫 거래 고객은 6개월 간 수수료 면제, 평균잔액 10만원 이상이면 사이버 금융범죄 보험도 제공됩니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "안정적인 이자 관리",
        "content": "최종 잔액 평균 기준으로 분기별 이자가 지급되며, 자유저축예금 결산 기준이 적용됩니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "예금자보호 안내",
        "content": "예금자보호법에 따라 원금과 이자를 합산하여 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    0.00,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 47: BNK보증부대출입금통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    47,
    'BNK보증부대출입금통장',
    '입출금자유',
    '대출입금전용',
    '보증기관의 보증서 기반 대출금만 입금 가능한 법인사업자 전용 통장',
    '[{
        "title": "전용 통장 안내",
        "content": "보증부 대출금 전용 입금계좌로, 보증기관에서 발급한 보증서 대출금만 입금 가능합니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "이자 지급 방식",
        "content": "예금일로부터 결산일까지 매일 잔액 평균 기준으로 이자가 산정되며, 보통예금 이자 결산 주기를 따릅니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "거래 제한 및 주의사항",
        "content": "신용카드 결제, 공과금 납부 계좌로의 등록은 제한되며, 예금잔액이 10만원 미만일 경우에만 해지가 가능합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "정보 전송",
        "content": "대출금 입금 후 6개월 경과 시 통장 거래 내역이 보증기관으로 자동 전송됩니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "예금자보호 안내",
        "content": "본 상품은 예금자보호법에 따라 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    0.00,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 48: 공무원연금 평생안심통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    48,
    '공무원연금 평생안심통장',
    '입출금자유',
    '연금수급보호',
    '공무원연금을 안전하게 수령할 수 있도록 설계된 압류방지 전용 0원 통장',
    '[{
        "title": "연금수급자 전용 통장",
        "content": "공무원연금 수급자만 가입 가능한 압류방지 전용 통장으로, 법원 압류 명령에도 지급이 제한되지 않습니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "입금 제한",
        "content": "공무원연금공단에서 입금하는 공무원연금(건당 185만원 이하)만 입금 가능하며, 타 자금은 입금 불가합니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "수수료 면제 혜택",
        "content": "신규 가입 후 일정 기간 및 입금 실적에 따라 이체, 출금 등 다양한 수수료가 면제됩니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "가입 절차",
        "content": "영업점에서 신규 개설 후 고객이 직접 공무원연금공단에 수급계좌 등록 시 효력이 발생합니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "예금자보호 안내",
        "content": "예금자보호법에 따라 원금과 이자를 합산하여 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL, 
    NULL,
    0.00,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 49: BNK바운스기업통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    49,
    'BNK바운스기업통장',
    '입출금자유',
    '기업단기운용',
    '예치기간에 따라 높은 금리를 적용받는 단기자금 운용 전용 기업 통장',
    '[{
        "title": "단기 고금리 구조",
        "content": "입금기간에 따라 차등금리가 적용되어 자금 예치일수가 길어질수록 높은 금리가 적용됩니다.",
        "imageURL": "/im/1.png"
    },
    {
        "title": "발생이자 선지급",
        "content": "직전 원가일 이후 발생한 이자를 출금 시 지급해 기업 자금 유동성을 높일 수 있습니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "사훈인자 서비스",
        "content": "기업 철학(사훈, 경영슬로건 등)을 통장에 인자하여 기업 정체성을 반영할 수 있습니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "복잡한 이자계산 지원",
        "content": "회차별 입금과 예치기간을 고려한 이자계산으로 고도화된 자금 운용이 가능합니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "예금자보호 안내",
        "content": "예금자보호법에 따라 원금과 이자를 합산하여 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/im/5.png"
    }]',
    NULL,
    NULL,
    0.35,
    0.20,
    6,
    'S',
    SYSDATE,
    0
);


-- 50: BNK공직자우대통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    50,
    'BNK공직자우대통장',
    '입출금자유',
    '공무원우대혜택',
    '공무원 및 공공기관 임직원에게 수수료 무제한 면제와 예적금 우대금리를 제공하는 입출금 자유 통장',
    '[{
        "title": "가입 대상",
        "content": "공무원, 군인, 경찰, 교사, 교수, 지방자치단체장, 공공기관 및 지방공기업 임직원 (1인 1계좌)",
        "imageURL": "/im/1.png"
    },
    {
        "title": "급여이체 기반 수수료 무제한 면제",
        "content": "급여이체 실적만 있어도 당행 전자금융 및 자동화기기 이용 수수료가 무제한 면제되며, 추가요건 충족 시 타 통장도 포함하여 전면 면제됩니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "예적금 우대금리",
        "content": "급여이체 실적이 있는 고객은 지정된 예적금 가입 시 연 0.1% 우대금리를 추가로 적용받을 수 있습니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "환율 우대",
        "content": "환전 및 유학경비 송금 시 외국통화 매매 마진율 최대 60% 우대 혜택이 제공됩니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "예금자보호 안내",
        "content": "본 상품은 예금자보호법에 따라 원금과 이자를 포함하여 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/im/7.png"
    }]',
    NULL,
    NULL,
    0.10,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 51: BNK국민연금안심통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    51,
    'BNK국민연금안심통장',
    '입출금자유',
    '국민연금수급자보호',
    '국민연금 수급 고객의 연금 급여를 압류로부터 안전하게 보호하는 전용 통장',
    '[{
        "title": "국민연금 압류방지 통장",
        "content": "국민연금(노령·장애·유족) 수급 고객 전용으로, 압류 및 법적 지급 제한으로부터 연금 급여를 보호합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "수수료 면제 혜택",
        "content": "신규일로부터 2개월간 수수료 전액 면제, 이후에도 입금 실적에 따라 월 10회까지 면제 혜택을 제공합니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "환율우대",
        "content": "영업점 창구에서 환전, 유학경비 송금 시 최대 50% 마진율 우대 제공",
        "imageURL": "/im/5.png"
    }]',
    NULL,
    NULL,
    0.00,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 52: BNK행복한아파트통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    52,
    'BNK행복한아파트통장',
    '입출금자유',
    '아파트관리비수납',
    '아파트관리비 자동이체 및 수납 실적에 따라 수수료 면제와 우대금리를 제공하는 입출금 자유 통장',
    '[{
        "title": "아파트관리비 자동이체 혜택",
        "content": "입주민 또는 관리사무소 명의로 아파트관리비 자동이체 등록 시 수수료 면제 및 각종 금융 우대 혜택 제공",
        "imageURL": "/im/3.png"
    },
    {
        "title": "우대금리 혜택",
        "content": "BNK지역사랑자유적금 또는 메리트정기예금 가입 시 우대이율 0.10%p 제공",
        "imageURL": "/im/5.png"
    },
    {
        "title": "수수료 면제조건",
        "content": "관리비 자동이체 실적에 따라 인터넷/ATM 이체, 증명서 발급, 창구 송금 수수료 등 최대 월 20회 면제",
        "imageURL": "/im/8.png"
    },
    {
        "title": "아파트발전기금 출연",
        "content": "관리사무소용 집금계좌로 활용 시 연 평균 잔액의 0.2%를 발전기금으로 출연",
        "imageURL": "/im/9.png"
    }]',
    NULL,
    NULL,
    0.00,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 53: 백세청춘 연금통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    53,
    '백세청춘 연금통장',
    '입출금자유',
    '연금수급자전용',
    '만 56세 이상 개인 고객을 위한 연금수급 특화 통장으로, 수수료 면제, 우대금리, 보험 가입 혜택을 제공',
    '[{
        "title": "연금 수급 실적 우대",
        "content": "공적연금, 연금신탁, 역모기지 등 연금 실적이 있는 경우 다양한 수수료를 횟수 제한 없이 면제",
        "imageURL": "/im/5.png"
    },
    {
        "title": "평균잔액 구간별 우대금리",
        "content": "3개월 이상 연금 수급 및 공과금 이체 실적 시, 평균잔액 구간에 따라 최대 1.50%까지 금리 우대",
        "imageURL": "/im/4.png"
    },
    {
        "title": "상해보험 무료가입",
        "content": "메리츠화재 골절수술 위로금 보험 서비스 제공 (가입 시 동의 필요)",
        "imageURL": "/im/3.png"
    },
    {
        "title": "모바일 통지·환율 우대",
        "content": "모바일통지 연간 20건 무료, 창구 환전 시 환율 최대 50% 우대",
        "imageURL": "/im/2.png"
    }]',
    NULL,
    NULL,
    1.50,
    0.01,
    6,
    'S',
    SYSDATE,
    0
);


-- 54: BNK행복지킴이통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    54,
    'BNK행복지킴이통장',
    '입출금자유',
    '압류금지급여보호',
    '기초생활수급자 등 법령에 의해 보호되는 압류금지 수급금만 입금 가능한 통장',
    '[{
        "title": "압류금지 수급금 전용",
        "content": "기초생활수급자, 기초연금, 장애수당, 실업급여 등 법령에 의해 보호되는 수급금만 입금 가능합니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "수수료 면제 혜택",
        "content": "이체, ATM, 제증명서 발급 등 수수료가 전액 면제됩니다. 입금 실적이 있는 경우 매월 자동 적용됩니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "전환 가입 불가",
        "content": "기존 통장 전환이 불가능하며 반드시 신규로만 개설 가능. 타 요구불 통장 기능과 중복 등록 불가",
        "imageURL": "/im/6.png"
    }]',
    NULL,
    NULL,
    0.00,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 55: 당선드림통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    55,
    '당선드림통장',
    '입출금자유',
    '선거자금관리',
    '공직선거 후보자 및 관계자가 선거자금을 수수료 없이 관리할 수 있는 전용 통장',
    '[{
        "title": "선거자금 전용 통장",
        "content": "공직선거입후보자 및 회계책임자, 선관위 등 선거 관련 자금의 입출금 전용으로 개설 가능한 통장입니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "다양한 수수료 면제 혜택",
        "content": "이체수수료, ATM 이용 수수료, 창구송금 등 여러 수수료가 면제되어 선거자금 운영의 효율을 높여줍니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "별도 서류 제출 필요",
        "content": "입후보자등록신청서, 회계책임자 선임신고서 등 선관위 발급 공식 문서 제출 필요",
        "imageURL": "/im/8.png"
    }]',
    NULL,
    NULL,
    0.00,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 56: 종교우대통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    56,
    '종교우대통장',
    '입출금자유',
    '종교단체연동',
    '종교단체와 신도 예금을 연결하여 기부금을 조성하고 우대이율과 수수료 혜택을 제공하는 통장',
    '[{
        "title": "모계좌·자계좌 구조",
        "content": "종교단체가 개설한 모계좌와 신도 또는 교인이 개설한 자계좌를 연결해 예금실적에 따라 기부금이 모계좌로 지급됩니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "예금실적 기반 기부금 지급",
        "content": "매년 신도 예금 평균잔액의 0.2% 범위 내에서 종교단체 모계좌에 기부금이 입금됩니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "우대이율 제공",
        "content": "신도 자계좌의 평균잔액에 따라 최대 0.1% 우대이율을 정기예금 신규 시 적용받을 수 있습니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "수수료 면제 혜택",
        "content": "자계좌 3개 이상 보유 + 평균잔액 1천만원 이상 시 이체 및 각종 수수료 면제",
        "imageURL": "/im/5.png"
    }]',
    NULL,
    NULL,
    0.10,
    0.00,
    6,
    'S',
    SYSDATE,
    0
);


-- 57: 초단기슈퍼통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    57,
    '초단기슈퍼통장',
    '입출금자유',
    '단기자금운용',
    '단 하루를 맡겨도 고금리를 적용받을 수 있는 입출금식 단기자금운용 통장',
    '[{
        "title": "단기 자금에도 고금리 제공",
        "content": "예금 기간이 짧아도 예금잔액에 따라 차등 금리가 적용되며, 5백만 원 이상 보유 시 우대 금리를 제공합니다.",
        "imageURL": "/im/5.png"
    },
    {
        "title": "다양한 고객 대상",
        "content": "개인, 개인사업자, 법인 모두 가입 가능하며, 여유자금을 효율적으로 관리할 수 있습니다.",
        "imageURL": "/im/4.png"
    },
    {
        "title": "입출금 자유 & 자동납부 가능",
        "content": "일반 입출금 통장처럼 자유롭게 사용할 수 있으며 자동납부 등 기능도 동일하게 지원됩니다.",
        "imageURL": "/im/2.png"
    }]',
    NULL,
    NULL,
    3.50,
    0.10,
    6,
    'S',
    SYSDATE,
    0
);


-- 58: 뱅크라인통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    58,
    '뱅크라인통장',
    '입출금자유',
    '공동상품/학생우대',
    '6개 지방은행 공동상품으로 수수료 부담 없이 이용 가능하며, 실적에 따라 신용대출도 제공되는 입출금 통장',
    '[{
        "title": "지방은행 공동 입출금 통장",
        "content": "전국 6개 지방은행이 공동으로 제공하는 입출금 통장으로, 어디서나 수수료 없이 자유롭게 이용할 수 있습니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "실적에 따라 신용대출 가능",
        "content": "거래실적에 따라 부산은행에서 신용대출 혜택도 받을 수 있습니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "학생 전용 수수료 우대",
        "content": "예금주가 중ㆍ고ㆍ대학생인 경우, 학업을 위한 송금 시 수수료 면제 혜택이 제공됩니다.",
        "imageURL": "/im/4.png"
    }]',
    NULL,
    NULL,
    1.50,
    0.10,
    6,
    'S',
    SYSDATE,
    0
);


-- 59: 자유저축예금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    59,
    '자유저축예금',
    '입출금자유',
    '일반자금관리',
    '평균잔액 기준 이자를 3개월마다 원금에 가산해주는 자유입출금식 저축예금',
    '[{
        "title": "평균잔액 기준 이자 산출",
        "content": "예금 이자계산 기간 동안의 평균잔액에 대해 금액별로 이자를 산출하여 3개월마다 지급합니다.",
        "imageURL": "/im/7.png"
    },
    {
        "title": "입출금이 자유로운 기본예금",
        "content": "가입 대상은 실명의 개인이며, 제한 없이 입금·출금이 가능합니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "기본 예금상품으로 사용 편의성↑",
        "content": "자산관리의 기본이 되는 예금으로, 정기이자 결산 시 자동으로 원금에 가산됩니다.",
        "imageURL": "/im/3.png"
    }]',
    NULL,
    NULL,
    1.30,
    0.10,
    6,
    'S',
    SYSDATE,
    0
);


-- 60: BNK증권플러스통장
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    60,
    'BNK증권플러스통장',
    '입출금자유',
    '증권연동예금',
    '은행 거래와 증권 계좌를 연동해 자동이체와 수수료 면제 혜택까지 제공하는 입출금 통장',
    '[{
        "title": "은행과 증권 거래를 한번에",
        "content": "은행 입출금 통장과 증권사 계좌를 연동하여 편리한 금융 거래를 제공합니다.",
        "imageURL": "/im/2.png"
    },
    {
        "title": "자동이체·증권 실적 우대금리",
        "content": "자동이체 실적 또는 전월 증권매매 실적에 따라 최대 연 0.1%의 우대이율을 제공합니다.",
        "imageURL": "/im/3.png"
    },
    {
        "title": "각종 수수료 무제한 면제",
        "content": "이체·출금·제증명 발급 등 다양한 수수료를 조건 충족 시 무제한 면제합니다.",
        "imageURL": "/im/5.png"
    }]',
    NULL,
    NULL,
    1.40,
    0.10,
    6,
    'S',
    SYSDATE,
    0
);

commit;

SET DEFINE ON;


-- 69: 오징어 모임통장 시즌2
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE, 
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    69,
    '오징어 모임통장 시즌2',
    '입출금자유',
    '모임관리',
    '눈치 보지 말고! 오랫동안 징글징글하게 어울리는 우리 모임만의 통장.<br>회비 관리부터 수수료 혜택까지, 모임통장의 끝판왕!',
    '[{
        "title": "모임을 위한 특별한 통장",
        "content": "친구, 가족, 동호회 등 다양한 모임을 위한 맞춤 통장!\n모임의 수입·지출을 투명하게 관리할 수 있어요.",
        "imageURL": "/ani/people.gif"
    },
    {
        "title": "우수모임 수수료 면제 혜택",
        "content": "전월 회비 수납 3건 이상 또는 평균잔액 조건 충족 시\n인터넷뱅킹·모바일 이체 수수료 면제 혜택 제공!",
        "imageURL": "/ani/medal.gif"
    },
    {
        "title": "투명한 회비 관리 및 알림 서비스",
        "content": "모임원 누구나 거래내역 확인 가능하며\n카카오톡, 문자, 푸시 알림으로 회비 납입 요청도 손쉽게!",
        "imageURL": "/ani/payment.gif"
    },
    {
        "title": "모바일로 손쉽게 초대/조회",
        "content": "카카오톡, 연락처 등으로 간편하게 초대하고\n언제 어디서나 모바일로 확인 가능!",
        "imageURL": "/ani/phone.gif"
    },
    {
        "title": "여행환전 수수료 할인",
        "content": "모임 여행 시 환전수수료 최대 70% 할인 혜택!\n여행 준비까지 함께 챙기세요.",
        "imageURL": "/ani/trip.gif"
    },
    {
        "title": "다양한 모임지원 기능",
        "content": "회비 걷기, 정기회비 설정, 납부요청 등 \n100명까지 관리 가능한 풍부한 기능 제공",
        "imageURL": "/ani/boss.gif"
    },
    {
        "title": "예금자 보호 및 안정성",
        "content": "예금자보호법에 따라 1인당 5천만원까지 보호되며\n공헌이율 3.20%를 기록한 Lock-in 효과 우수 상품입니다.",
        "imageURL": "/ani/protection.gif"
    }]', 
    NULL, 
    NULL, 
    0.01,
    0.01,
    0,
    'S',
    SYSDATE,
    200
);


-- 70: 롯데자이언츠 승리기원적금 UPGRADE
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    70,
    '롯데자이언츠 승리기원적금 UPGRADE',
    '적금',
    '목돈마련',
    '롯데자이언츠의 승리를 응원하며 최대 연 3.30%까지!<br>야구팬이라면 꼭 들어야 할 특별한 우대금리 적금',
    '[{
        "title": "야구팬을 위한 특별한 적금",
        "content": "롯데자이언츠의 정규시즌 성적에 따라 사은 금리를 제공하는 응원형 상품입니다.",
        "imageURL": "/ani/baseball.gif"
    },
    {
        "title": "기본 + 우대금리로 최대 연 3.30%",
        "content": "기본 연 2.50%에 다양한 우대 조건을 만족하면 최고 연 3.30%까지 금리 제공!",
        "imageURL": "/ani/interest.gif"
    },
    {
        "title": "사직야구장 방문 우대이율",
        "content": "홈경기 당일 현장 방문 인증만으로도 우대금리 추가! 팬심을 금리로 보상받는 특별한 경험.",
        "imageURL": "/ani/baseball2.gif"
    },
    {
        "title": "거래 실적 우대",
        "content": "입출금 통장 평균잔액 또는 카드 실적 충족 시 추가 우대금리 혜택이 주어집니다.",
        "imageURL": "/ani/card.gif"
    },
    {
        "title": "신규고객 우대",
        "content": "최근 3년간 부산은행 예·적금 미보유 고객이라면 0.10%p의 반가운 손님 우대이율 제공!",
        "imageURL": "/ani/new.gif"
    },
    {
        "title": "다양한 가입 방법",
        "content": "영업점, 모바일뱅킹, 디지털데스크 어디서나 간편하게 가입 가능.",
        "imageURL": "/ani/digital.gif"
    },
    {
        "title": "안전한 금융 상품",
        "content": "이 예금은 예금자보호법에 따라 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/ani/protection.gif"
    }]',
    NULL,
    NULL,
    3.30,
    2.50,
    12,
    'S',
    SYSDATE,
    200
);


-- 73: 매일출석적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    73,
    '매일출석적금',
    '적금',
    '지역상생',
    '매일 홈페이지에 접속해서 버튼만 누르면 천원이 저축돼요!<br>7일마다 착한 리워드도 챙기고, 부산 소상공인 가게에서 사용하세요!',
    '[{
        "title": "클릭 한 번으로 하루 천원 저축",
        "content": "자동이체 없이, 고객이 매일 직접 접속해 버튼을 누르면 1,000원이 저축됩니다.",
        "imageURL": "/ani/click.gif"
    },
    {
        "title": "7일마다 착한 리워드",
        "content": "매일 성공적으로 납입하면, 7일 또는 10일마다\\n부산 지역 가게에서 쓸 수 있는 쿠폰 리워드를 드립니다.",
        "imageURL": "/ani/cash.gif"
    },
    {
        "title": "소상공인과 함께하는 소비",
        "content": "리워드는 부산 지역 제휴 소상공인 매장에서 자유롭게 사용할 수 있어요!\\n지역상권을 함께 응원해요.",
        "imageURL": "/ani/store.gif"
    },
    {
        "title": "내가 직접 쌓는 적금 재미",
        "content": "매일 출석하며 성취감을 얻고, 금리와 리워드를 함께 챙겨보세요!",
        "imageURL": "/ani/dollar.gif"
    },
    {
        "title": "모바일에서도 간편하게",
        "content": "PC는 물론 모바일뱅킹 앱에서도 간편하게 참여할 수 있어요.",
        "imageURL": "/ani/touchscreen.gif"
    },
    {
        "title": "비과세 혜택도 가능",
        "content": "조건 충족 시 비과세종합저축으로 가입해 이자소득세를 면제받을 수 있습니다.",
        "imageURL": "/ani/taxfree.gif"
    },
    {
        "title": "예금자보호로 안전하게",
        "content": "이 상품은 예금자보호법에 따라 1인당 5천만원까지 보호됩니다.",
        "imageURL": "/ani/protection.gif"
    }]',
    NULL,
    NULL,
    3.10,
    2.20,
    6,
    'S',
    SYSDATE,
    200
);


-- 74: 함께걷는 적금
INSERT INTO DEPOSIT_PRODUCT (
    PRODUCT_ID,
    NAME,
    CATEGORY,
    PURPOSE,
    SUMMARY,
    DETAIL,
    MODAL_DETAIL,
    MODAL_RATE,
    MAX_RATE,
    MIN_RATE,
    PERIOD,
    STATUS,
    CREATED_AT,
    VIEW_COUNT
) VALUES (
    74,
    '함께걷는 적금',
    '적금',
    '건강관리',
    '매일의 걸음을 모아 저축하는 따뜻한 금융 습관.<br>걷기 성과에 따라 금리가 최대 연 6.0%까지 높아집니다.',
    '[{
        "title": "걸음으로 만드는 저축",
        "content": "걷기를 통해 우대금리를 쌓아 <br>건강과 재테크를 동시에 실현할 수 있습니다.",
        "imageURL": "/ani/walk.gif"
    },
    {
        "title": "걷기 우대금리",
        "content": "매월 10만 걸음(만 60세 이상은 5만 걸음)을 달성하면 <br>월 연 0.83%p까지 우대금리를 제공합니다.",
        "imageURL": "/ani/steps.gif"
    },
    {
        "title": "만기일시지급식",
        "content": "이자는 만기해지 또는 중도해지 시 일시에 지급됩니다.",
        "imageURL": "/ani/clock.gif"
    },
    {
        "title": "비과세 가능",
        "content": "조건을 충족하면 비과세종합저축으로 가입하여 <br>이자소득세를 면제받을 수 있습니다.",
        "imageURL": "/ani/tax.gif"
    },
    {
        "title": "예금자보호",
        "content": "예금자보호법에 따라 1인당 최고 5천만원까지 보호됩니다.",
        "imageURL": "/ani/protect.gif"
    }]',
    NULL,
    NULL,
    6.00,
    1.00,
    6,
    'S',
    SYSDATE,
    500
);


COMMIT;
ALTER SEQUENCE PRODUCT_ID_SEQ RESTART START WITH 75;
