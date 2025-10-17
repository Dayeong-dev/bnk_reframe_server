SET DEFINE OFF;

/* 1 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE, SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    1,
    '부산 동백전 체크카드',
    '부산지역 경제활성화를 위한,
    부산지역화폐 동백전!',
    '#생활/쇼핑  #적립/캐시백 #후불교통',
    'K',
    'S',
    '무료',
    '[{"title":"전통시장","desc":"동백전 충전금액, 체크카드 사용 금액 모두 2% 할인"},
    {"title":"전통시장 외","desc":"동백전 충전금액 체크카드 사용금액 모두 0.2% 할인"}]',
    '- 부산시 내 전통시장 이용 시 : 2% 할인
    - 부산 일반가맹점 : 이용금액 건당 1만원 이상 사용시 0.2% 할인',
    388,
    NULL, NULL, NULL, NULL
);

/* 2 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE, SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    2,
    '캐쉬백카드',
    '매달 결제일에 최대 0.7% 캐쉬백혜택으로 돌아온다!',
    '#생활/쇼핑 #여가/엔터 #적립/캐시백 #주유/차량 #기타 #후불교통',
    'P',
    'S',
    '국내전용(BC): 2,000원
    \n국내외겸용(VISA/MASTER): 5,000원',
    '[{"title":"무이자", "desc":"대형백화점, 할인점, 병원, 약국 2~3개월 무이자 할부"},
    {"title":"주유", "desc":"전국 SK, GS 주유소 1.5% 할인(LPG포함)"},
    {"title":"영화", "desc":"전국 모든 영화관 3천원 현장할인(1만원 이상 결제 시)"},
    {"title":"생활", "desc":"파리바게트, 던킨도너츠, 베스킨라빈스 등 10% 할인\n스타벅스, 커피빈, 엔제리너스 10% 할인"},
    {"title":"통신", "desc":"휴대폰요금(SKT, KT, LG) 자동이체 시 최대 1500원 할인"},
    {"title":"놀이공원", "desc":"본인 무료입장 또는 자유이용권 50% 할인"}]',
    '- 국내 가맹점 청구 할인: 이용금액 구간별 0.3%~0.7% 할인
    └ 5만원 이상 ~ 30만원 미만: 0.3%
    └ 30만원 이상 ~ 50만원 미만: 0.4%
    └ 50만원 이상 ~ 100만원 미만: 0.5%
    └ 100만원 이상: 0.7%
    - 주유 할인: 전국 SK주유소, GS칼텍스 1.5% (월 3회, 회당 10만원 한도)
    - 영화관 할인: 1만원 이상 결제 시 3천원 할인 (월 1회, 연 6회)
    - 베이커리 할인: 1만원 이상 10% 할인 (월 3회, 회당 최대 2천원)
    - 커피전문점 할인: 이용액의 10% 할인 (월 2회, 최대 3천원)
    - 이동통신요금 자동납부 할인: 3% 범위 내 최대 1,500원
    - 놀이공원: 자유이용권 최대 50% 현장할인',
    0,
    NULL, NULL, NULL, NULL
);

/* 3 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE, SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    3,
    'BNK 프렌즈 신용카드',
    '간단 명료한 기본 할인! 통큰 연간 캐시백!',
    '#생활/쇼핑 #적립/캐시백 #기타 #후불교통',
    'P',
    'S',
    '국내전용(BC글로벌): 10,000원
    \n해외(Master): 12,000원',
    '[{"title": "기본 할인", "desc": "1만원 이상 결제 시 주중 0.3%, 주말 0.6% 기본할인"}, {"title": "캐시백", "desc": "국내 가맹점 사용 금액에 따라 최대 20만원 연간 캐시백"}, {"title": "대중교통", "desc": "버스, 지하철 등 후불교통 10% 할인"}]',
    '- 기본 할인: 주중 0.3% / 주말 0.6% (건당 1만원 이상)
    - 연간 캐시백: 연간 국내 이용금액 
    └ 500만원 이상 ~ 1,000만원 미만: 5만원 
    └ 1,000만원 이상 ~ 2,000만원 미만: 10만원 
    └ 2,000만원 이상: 20만원
    - 휴대폰 요금 자동이체 5% 할인 (월 최대 3천원)
    - 대중교통 10% 할인 (월 최대 3천원)',
    0,
    NULL, NULL, NULL, NULL
);

/* 4 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE, SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    4,
    'BNK 부자되세요 홈쇼핑카드',
    '한 장의 카드로 폭 넓게 즐기는 쇼핑 특화 카드',
    '#생활/쇼핑 #적립/캐시백 #후불교통',
    'P',
    'S',
    'UnionPay(CUP): 2,000원
    \nVISA/MASTER: 5,000원',
    '[{"title": "6대 홈쇼핑", "desc": "이용액의 6% 할인 (전월 20만원 이상, 건당 5만원 이상)"}, {"title": "백화점", "desc": "5% 할인 (건당 5만원 이상, 월 최대 1만원)"}, {"title": "온라인 쇼핑몰", "desc": "G마켓, 11번가, 옥션 5% 할인"}, {"title": "소셜커머스", "desc": "쿠팡, 티몬, 위메프 5% 할인"}, {"title": "주말 쇼핑", "desc": "백화점/온라인/소셜커머스 할인율 2배 (10%)"}]',
    '- 6대 홈쇼핑: 이용액의 6% 할인 (전월 20만원 이상, 건당 5만원 이상)
    └ CJ, GS, 현대, 롯데, 홈앤쇼핑, NS홈쇼핑
    - 백화점: 5% 할인 (건당 5만원 이상, 월 최대 1만원)
    - 온라인 쇼핑몰(G마켓, 11번가, 옥션): 5% 할인 (건당 3만원 이상, 1회 최대 2천원, 월 2회)
    - 소셜커머스(쿠팡, 티몬, 위메프): 5% 할인 (건당 3만원 이상, 1회 최대 2천원, 월 2회)
    - 주말 쇼핑: 백화점/온라인/소셜커머스 할인율 2배 (10%)',
    0,
    NULL, NULL, NULL, NULL
);

/* 5 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    5,
    '딩딩 신용카드',
    '즐거움 가득, 혜택 가득~ DingDing 신용카드!',
    '#생활/쇼핑 #여가/엔터 #주유/차량 #적립/캐시백 #후불교통',
    'P',
    'S',
    '국내/Global/UnionPay 7,000원
    \nVISA/JCB/MASTER 10,000원',
    '[{"title": "주유", "desc": "전 주유소 리터당 60원 할인(LPG포함)"}, {"title": "생활", "desc": "모든 음식점 업종 10% 할인\n스타벅스, 엔제리너스, 파리바게트 등 10% 할인"}, {"title": "여가", "desc": "전국 영화관 4천원 할인\n쿠팡, 티몬, 위메프 등 인터넷 쇼핑 5% 할인"}, {"title": "통신", "desc": "이동통신요금 자동이체 5% 할인"}, {"title": "대중교통", "desc": "대중교통 이용요금 10% 할인"}, {"title": "놀이공원", "desc": "본인 무료입장 또는 자유이용권 50% 할인"}]',
    '- 주유: 전 주유소 리터당 60원 할인 (5만원 이상 결제, 1회 10만원 한도, 일1회 월4회)
    - 교통: 대중교통 10% 청구할인 (월 최대 3천원)
    - 이동통신: 자동이체 5% 청구할인 (1회 최대 3천원, 월 1회)
    - 외식: 음식점 10% 청구할인 (2만원 이상, 1회 최대 3천원, 월 2회)
    - 커피&베이커리: 10% 청구할인 (5천원 이상, 1회 최대 2천원, 월 2회 연12회)
    - 인터넷쇼핑: 5% 청구할인 (3만원 이상, 1회 최대 3천원, 월 2회)
    - 영화: 전국 영화관 4천원 청구할인 (1만원 이상, 월 1회 연6회)
    - 놀이공원: 자유이용권 50% 현장할인 / 일부 무료입장 및 입장권 30% 현장할인 (통합 월 1회)',
    0,
    NULL, NULL, NULL, NULL
);

/* 6 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    6,
    'REX2_대한항공마일리지형(개인)',
    'The Return of Royalty, REXⅡ',
    '#여행/항공 #생활/쇼핑 #주유/차량 #적립/캐시백 #후불교통',
    'P',
    'S',
    'MASTER (본인회원): 200,000원
    \nMASTER (가족회원): 50,000원',
    '[{"title": "대한항공 마일리지 적립", "desc": "1,500원당 1마일리지 적립"},
      {"title": "프리미엄 바우처 4종 선택", "desc": "호텔 다이닝/백화점상품권/항공업종 할인/골프업종 할인 (연 1회)"},
      {"title": "백화점·마트", "desc": "월 2회 최대 1만원, 10% 할인"},
      {"title": "카카오T 할인", "desc": "월 4회 최대 1만원, 10% 할인"},
      {"title": "스타벅스 할인", "desc": "월 4회 최대 1만원, 10% 할인"},
      {"title": "주유/차량", "desc": "전 주유소 리터당 70원"},
      {"title": "공항라운지 무료 이용", "desc": "이용 조건 충족 시 제공"},
      {"title": "공항/호텔 발렛파킹 무료 이용", "desc": "이용 조건 충족 시 제공"}]',
    '- 마일리지: 국내 가맹점 1,500원당 1마일리지 적립
    - 바우처: 연 1회 15만원 할인 또는 교환권 제공 (호텔 다이닝/백화점상품권/항공업종/골프업종 중 택1)
    - 프리미엄서비스: 백화점·마트/카카오T/스타벅스/주유 할인 (조건별 10% 할인, 월별 한도)
    - 공항라운지: 전월 40만원 이상 이용 시 연 3회 무료
    - 발렛파킹: 전월 40만원 이상 이용 시 공항·호텔 각각 월 2회, 연 5회 무료',
    0,
    NULL, NULL, NULL, NULL
);

/* 7 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    7,
    'REX2_포인트형(개인)',
    'The Return of Royalty, REXⅡ',
    '#생활/쇼핑 #여가/엔터 #주유/차량 #여행/항공 #비즈니스 #적립/캐시백 #후불교통',
    'P',
    'S',
    'MASTER (본인회원) : 200,000원
    \nMASTER (가족회원) : 50,000원',
    '[{"title": "프리미엄 바우처", "desc": "호텔 다이닝, 백화점 상품권, 항공업종 할인, 골프업종 할인(연1회)"},
      {"title": "프리미엄 서비스", "desc": "백화점·마트 할인, 카카오T 할인, 스타벅스 할인, 주유 할인"},
      {"title": "MASTER 브랜드 서비스", "desc": "공항 라운지 연3회 무료/공항호텔 발렛파킹 각각 월 2회/연 5회 무료"},
      {"title": "포인트 적립", "desc": "국내 일시불, 할부 금액에 대한 TOP포인트 적립"}]',
    '- 국내 일시불/할부 1% TOP포인트 적립(실적 제외 업종 제외)
    - 백화점, 마트: 10% 할인(1만원 이상, 월 2회 최대 1만원)
    - 카카오T: 10% 할인(1만원 이상, 월 4회 최대 1만원)
    - 스타벅스: 10% 할인(1만원 이상, 월 4회 최대 1만원)
    - 주유: 전 주유소 리터당 70원 할인(월 2회, 1회 주유금액 최대 10만원)
    - 공항 라운지 연 3회 무료
    - 공항/호텔 발렛파킹 월 2회, 연 5회 무료',
    0,
    NULL, NULL, NULL, NULL
);

/* 8 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    8,
    'ZipL 신용카드',
    '생활에 특별한 혜택, 더 나은 일상을 위한 카드',
    '#생활/쇼핑 #육아/교육 #후불교통',
    'P',
    'S',
    '국내전용(BC): 1만원
    \n국내외겸용(Master): 1만 5천원',
    '[{"title": "아파트관리비", "desc": "월 1회 자동납부 시, 10만원 이상 결제 시 최대 1만원 할인"},
      {"title": "도시가스/전기요금", "desc": "월 1회 자동납부 시, 5만원 이상 결제 시 최대 5천원 할인"},
      {"title": "보험/통신요금", "desc": "월 2회 5만원 이상 결제 시 최대 1만원 할인"},
      {"title": "미용/세탁업종", "desc": "월 4회 현장결제 시, 2만원 이상 결제 시 최대 1만원 할인"},
      {"title": "대형마트", "desc": "월 2회 현장결제 시, 5만원 이상 결제 시 최대 1만원 할인"},
      {"title": "학원/학습지", "desc": "월 1회 현장결제 시, 5만원 이상 결제 시 최대 5천원 할인"},
      {"title": "병의원/약국/동물병원", "desc": "월 2회 1만원 이상 결제 시 최대 5천원 할인"},
      {"title": "후불교통", "desc": "최대 5천원 할인)"}]',
    '- 아파트관리비: 최대 1만원 / 월 1회 / 10만원 이상
    - 도시가스/전기요금: 최대 5천원 / 월 1회 / 5만원 이상
    - 보험, 통신: 최대 1만원 / 월 2회 / 5만원 이상
    - 이미용, 세탁: 최대 1만원 / 월 4회 / 2만원 이상
    - 대형마트: 최대 1만원 / 월 2회 / 5만원 이상
    - 학원/학습지: 최대 5천원 / 월 1회 / 5만원 이상
    - 병의원/약국/동물병원: 최대 5천원 / 월 2회 / 1만원 이상
    - 후불교통: 최대 5천원',
    0,
    NULL, NULL, NULL, NULL
);

/* 9 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    9,
    '어디로든 그린체크카드',
    '지구를 지키는 친환경 업종 특화 카드',
    '#생활/쇼핑 #적립/캐시백 #육아/교육 #여가/엔터',
    'K',
    'S',
    '발급수수료 1,000원',
    '[{"title": "친환경 자동차 충전", "desc": "전기차/수소차 충전 금액 20% 적립"},
      {"title": "공유 모빌리티", "desc": "쏘카, 투루카, 카카오T바이크, 따릉이(서울) 등 5% 적립"},
      {"title": "커피전문점", "desc": "스타벅스, 폴바셋, 이디야 5% 적립"},
      {"title": "국내 가맹점", "desc": "0.1% 적립"}]',
    '- 친환경 자동차 충전 최대 20% 적립 (월 최대 5천 포인트)
    - 공유 모빌리티 5% 적립
    - 커피전문점 5% 적립
    - 국내 전 가맹점 0.1% 적립
    - 월 통합 적립 한도 5천~1만 포인트 (전월 실적에 따라 차등 적용)',
    0,
    NULL, NULL, NULL, NULL
);

/* 10 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    10,
    '부산체육사랑카드',
    'Sports is Busan!! 부산시체육회 지정 체육시설 10%
    스포츠, 의료, 학원 등 생활 곳곳에서 할인 챙기세요!',
    '#생활/쇼핑 #여가/엔터 #육아/교육 #후불교통',
    'P',
    'S',
    '국내전용(BC): 1만원',
    '[{"title": "시설", "desc": "체육회 지정 시설 10% 할인, 최대 1만5천원"},
      {"title": "스포츠", "desc": "골프/스포츠/레저 10% 할인, 최대 1만원"},
      {"title": "의료", "desc": "병의원/약국 10% 할인, 최대 1만원"},
      {"title": "학원", "desc": "학원 5% 할인, 최대 5천원"},
      {"title": "건강식품", "desc": "홍삼/인삼/건강식품 5% 할인, 최대 5천원"},
      {"title": "생활", "desc": "간편결제/CU/GS25/스타벅스 5% 할인, 최대 5천원"}]',
    '- 부산시 체육시설 10% 할인 (월 최대 1만5천원)
    - 스포츠, 의료, 학원, 건강식품, 생활 업종에서 할인율 각각 다름
    - 간편결제 포함하나, 승인처리 가맹점 번호 불일치 시 할인 미적용
    - 스타벅스 할인은 현장결제만 적용, 선불카드 등 제외
    - 대형 할인점/백화점/아울렛 등 제외',
    0,
    NULL, NULL, NULL, NULL
);

/* 11 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    11,
    'ZipL 체크카드',
    '우리집에 플러스되는 체크카드!',
    '#생활/쇼핑 #육아/교육',
    'K',
    'S',
    '무료',
    '[{"title": "생활요금", "desc": "아파트관리비, 보험업종, 도시가스, 통신요금 3% 청구할인"},
      {"title": "생활요금2", "desc": "이미용업종, 세탁업종 3% 청구할인"},
      {"title": "대형마트", "desc": "이마트, 롯데마트, 홈플러스, 트레이더스홀세일클럽, 홈플러스익스프레스 3% 청구할인"},
      {"title": "교육", "desc": "학원업종 3% 청구할인"}]',
    '- 월간통합할인한도: 전월실적 20만원 이상 1만원, 40만원 이상 2만원, 60만원 이상 3만원
    - 전월실적 기준: 전월 1일부터 말일까지 국내 가맹점 이용실적
      (단, 아파트관리비, 보험료, 통신요금, 도시가스, 제세공과금, 전기요금, 대학등록금, 초중고납입금, 유치원, 상품권, 임대료, 무이자할부, 수수료, 이자, 연회비, 후불교통, 취소금액 등은 제외)
    - 간편결제 이용 시 가맹점 등록기준 불일치 시 할인 미적용',
    0,
    NULL, NULL, NULL, NULL
);

/* 12 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    12,
    '팟(pod) 카드',
    '내 맘 속에 팟! 팟(pod) 카드',
    '#생활/쇼핑 #여가/엔터 #적립/캐시백 #후불교통',
    'P',
    'S',
    '국내전용(BC) : 1만원
    \n국내외겸용(Master, VISA) : 1만 5천원',
    '[{"title": "간편결제", "desc": "네이버페이, 카카오페이, 삼성페이 10% 청구할인"},
      {"title": "디지털 구독", "desc": "넷플릭스, 멜론, 유튜브 프리미엄, 디즈니 플러스 8천원 이상 결제 시 30% 청구 할인"},
      {"title": "커피", "desc": "스타벅스 10% 할인"},
      {"title": "웹툰", "desc": "카카오웹툰, 카카오페이지, 네이버 웹툰 10% 청구할인"},
      {"title": "온라인쇼핑", "desc": "무신사, 마켓컬리 등 15% 청구할인"},
      {"title": "배달 앱", "desc": "요기요, 배달의민족 10% 할인"}]',
    '- 월간 통합 할인 한도 : 전월 실적 40만원 이상 2만원, 80만원 이상 4만원 (놀이공원 할인은 별도, 통합한도 제외)
    - 전월 실적 제외 업종 : 해외매출, 제세공과금, 초중고 학교납입금, 아파트관리비, 도시가스, 전기요금, 대학등록금, 상품권, 임대료, 카드대출이자, 연회비, 취소금액, 자동차구매, 후불교통, 페이북 외화머니 이용금액 등
    - 할인은 간편결제 매출 시 미적용(직접 결제 시만 적용)
    - 커피 할인은 현장 결제(사이렌오더 포함)만 적용
    - 디지털구독 등은 공식 앱/홈페이지 결제만 적용',
    0,
    NULL, NULL, NULL, NULL
);

/* 13 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE, SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    13,
    'SK OIL/LPG카드',
    '주유특화 할인 혜택과 
    생활 서비스 할인까지 가능한 
    SK OIL/LPG카드!',
    '#생활/쇼핑 #주유/차량 #후불교통',
    'P',
    'S',
    '국내전용(BC) : 1만원
    \n국내외겸용(Master) : 1만5천원',
    '[{"title": "주유 할인", "desc": "전국 SK주유소/충전소 리터당 50원 기본 할인, 부산/울산/경남 지역 추가 70원 할인 (1회 주유 최대 10만원)"},
      {"title": "커피 할인", "desc": "스타벅스(사이렌오더 포함), 10% 할인, 월 2회, 1만원 이상 결제"},
      {"title": "문화/레저", "desc": "스크린골프, 영화관(CGV, 롯데시네마, 메가박스 등), 10% 할인, 월 2회"},
      {"title": "편의점", "desc": "GS, CU 현장결제, 10% 할인, 월 2회"},
      {"title": "구독 할인", "desc": "넷플릭스, 멜론 공식 홈페이지 및 앱 내 결제, 10% 할인, 월 2회, 8천원 이상 결제"},
      {"title": "주차장 할인", "desc": "주차장업종, 10% 할인, 월 4회"}]',
    '- 생활 할인 월간 통합 할인 한도: 전월 30~60만원 5,000원, 60만원 이상 10,000원 할인
    - 주유 할인 금액은 생활 할인 통합 할인 한도에 포함되지 않음
    - 전월 실적 산정 시 주유금액 제외',
    0,
    NULL, NULL, NULL, NULL
);

/* 14 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    14,
    '2030 언택트 체크카드',
    '일상 속 언택트 서비스로 구성된 비대면 특화 카드! 2030 언택트 체크카드!',
    '#생활/쇼핑 #여가/엔터 #후불교통',
    'K',
    'S',
    '무료',
    '[{"title": "커피", "desc": "스타벅스(사이렌오더 포함) 3% 할인 (1만원 이상 결제)"},
      {"title": "딜리버리", "desc": "배달의민족, 요기요 3% 할인 (2만원 이상 결제)"},
      {"title": "쇼핑", "desc": "쿠팡, 마켓컬리, SSG닷컴 3% 할인 (2만원 이상 결제)"},
      {"title": "구독", "desc": "넷플릭스, 유튜브프리미엄, 멜론 20% 할인 (8천원 이상 결제)"}]',
    '- 월 최대 할인: 각 서비스별 3,000원 한도
    - 간편결제 결제 시 할인 미적용 가능
    - 앱 마켓 내부 결제, 1회성 일반구매 등은 할인 제외',
    0,
    NULL, NULL, NULL, NULL
);

/* 15 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    15,
    '오늘은e 체크카드',
    '각종 페이 및 생활 서비스 할인되는 오늘은e 체크카드!',
    '#생활/쇼핑 #여가/엔터 #후불교통',
    'K',
    'S',
    '무료',
    '[{"title": "간편결제", "desc": "PAYCO, 삼성페이, 네이버페이, 카카오페이, 썸패스 3% 청구할인"},
      {"title": "커피", "desc": "스타벅스 3% 청구할인"},
      {"title": "대중교통", "desc": "버스, 지하철, 택시요금 3% 청구할인"},
      {"title": "영화관", "desc": "롯데시네마 3% 청구할인"},
      {"title": "이동통신", "desc": "휴대폰 요금 3% 청구할인"},
      {"title": "교육", "desc": "전국 모든 학원업종 이용액 3% 청구할인"}]',
    '- 전월 실적 20만원 이상 시 제공
    - 서비스별 건당 최소 결제금액:
      간편결제 1만원 이상, 학원 5만원 이상, 영화관 1만원 이상 등
    - 간편결제 월 최대 4,000원, 그 외 서비스별 월 최대 2,000원 할인
    - 상품권 구매/선불카드 충전 등 할인 제외
    - 일부 간편결제 교통 사용 시 할인 제외',
    0,
    NULL, NULL, NULL, NULL
);

/* 16 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    16,
    '오늘은e 신용카드',
    '각종 페이 및 생활 서비스 할인되는 오늘은e 신용카드!',
    '#생활/쇼핑 #적립/캐시백 #후불교통',
    'P',
    'S',
    '국내전용(BC) : 7천원
    \n국내외겸용(Master, VISA) : 1만원',
    '[{"title": "간편결제", "desc": "PAYCO, 삼성페이, 네이버페이, 카카오페이, 썸패스 5% 청구할인"},
      {"title": "대중교통", "desc": "버스/지하철/택시요금 5% 청구할인"},
      {"title": "영화", "desc": "롯데시네마 5% 청구할인"},
      {"title": "커피", "desc": "스타벅스 5% 청구할인"},
      {"title": "이동통신", "desc": "휴대폰 요금 5% 청구할인"},
      {"title": "교육", "desc": "전국 모든 학원업종 이용액 5% 청구할인 (건당 5만원 이상)"}]',
    '- 전월 실적 30만원 이상 시 할인 제공
    - 간편결제 월 최대 1만원 할인
    - 생활 할인(학원/이동통신/커피/대중교통/영화관) 각 최대 5천원 할인
    - 건당 최소 결제금액 : 간편결제 1만원 이상, 학원 5만원 이상, 영화관 1만원 이상
    - 전월 실적 제외 업종 : 국세/지방세/4대보험료/과태료/학교납입금/관리비/도시가스/전기요금/대학등록금/상품권/임대료/카드대출/무이자할부/수수료/이자/연회비/취소금액/페이북 외화머니',
    0,
    NULL, NULL, NULL, NULL
);

/* 17 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    17,
    '국민행복카드',
    '정부의 다양한 바우처사업을 통합하여 사용 가능한 카드',
    '#생활/쇼핑 #육아/교육 #적립/캐시백 #후불교통',
    'P',
    'S',
    '무료',
    '[{"title": "의료", "desc": "병/의원, 조산원, 산후조리원 5% 청구할인(정부지원금 제외)"},
      {"title": "쇼핑", "desc": "G마켓, 옥션, 11번가, 쿠팡, 위메프, 티몬, 페이북쇼핑 5% 청구할인"},
      {"title": "커피", "desc": "스타벅스, 커피빈, 카페베네 20% 청구할인"},
      {"title": "외식", "desc": "주요 패밀리레스토랑 10% 청구할인"},
      {"title": "통신", "desc": "SKT, LGU+, KT 자동이체 1천원 청구할인"},
      {"title": "육아", "desc": "3딸기가 좋아 키즈파크 매장 5% 청구할인"}]',
    '- 각 할인별 월 최대 한도/횟수 있음:
      의료 월 2회/2만원,
      온라인쇼핑 월 2회,
      커피 월 4회/1만원 이상,
      레스토랑 월 1회,
      키즈 일 1회/1만원 이상
    - 이동통신 할인은 자동이체 최초 접수 1회선만 적용
    - 일부 업종/상품권/입점매장 할인 제외 조건 있음',
    0,
    NULL, NULL, NULL, NULL
);

/* 18 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    18,
    '딩딩 체크카드',
    '즐거움 가득, 혜택 가득~ DingDing 체크카드~!',
    '#생활/쇼핑 #여가/엔터 #적립/캐시백',
    'K',
    'S',
    '발급 수수료 1000원',
    '[{"title": "편의점", "desc": "GS25, CU, 세븐일레븐 10% 할인"},
      {"title": "베이커리", "desc": "파리바게뜨, 뚜레쥬르, 던킨도너츠 10% 할인"},
      {"title": "외식", "desc": "맥도날드, 롯데리아 10% 할인"},
      {"title": "커피", "desc": "스타벅스, 카페베네, 엔제리너스 10% 할인"},
      {"title": "쇼핑", "desc": "CJ올리브영, 다이소 10% 할인"},
      {"title": "인터넷쇼핑", "desc": "G마켓, 옥션, 11번가 5% 할인"},
      {"title": "영화", "desc": "전국 영화관 현장/인터넷 예매 4천원 할인"},
      {"title": "도서", "desc": "교보문고, 영풍문고, YES24, 영광도서 2천원 할인"}]',
    '- 편의점/베이커리/외식/커피: 건당 5천원 이상, 1회 최대 2천원 할인 (월 2회, 연 12회)
    - 쇼핑: 1만원 이상, 1회 최대 3천원 할인 (월 1회, 연 6회)
    - 인터넷쇼핑: 3만원 이상, 1회 최대 3천원 할인 (월 2회, 연 12회)
    - 영화: 1만원 이상 결제 시 4천원 할인 (월 1회, 연 6회)
    - 도서: 3만원 이상 결제 시 2천원 할인 (월 2회, 연 12회)',
    0,
    NULL, NULL, NULL, NULL
);

/* 19 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    19,
    'BNK SIMPLE AMEX BLUE BUSINESS 카드',
    '하나의 카드로 사업을 심플하게! BNK SIMPLE AMEX BLUE BUSINESS 카드',
    '#적립/캐시백 #비즈니스',
    'C',
    'S',
    '국내외겸용(AMEX): 제휴연회비 2,000원',
    '[{"title": "기본 포인트", "desc": "전월 실적 따라 0.2% ~ 0.5% 적립"},
      {"title": "추가 포인트", "desc": "음식, 주유 등 업종 0.1% ~ 0.2% 추가 적립 (개인사업자/소기업 한정)"}]',
    '- 전월 실적별 기본 포인트 적립률:
     ㆍ 100만원 미만: 0.2%
     ㆍ 100만원 이상 ~ 300만원 미만: 0.3%
     ㆍ 300만원 이상 ~ 500만원 미만: 0.4%
     ㆍ 500만원 이상: 0.5%
    - 추가 포인트(음식, 주유 등): 0.1% ~ 0.2% (중소기업확인서 제출 필수)
    - 실적 제외: 제세공과금, 학부모부담금, 관리비, 공과금, 대학등록금, 상품권, 자동차업종, 후불교통, 수수료/이자/대출 등',
    0,
    NULL, NULL, NULL, NULL
);

/* 20 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    20,
    'BNK Simple카드',
    '포인트 적립의 Simple한 상품서비스에 지역사회 공헌하는 ESG 상품',
    '#적립/캐시백 #비즈니스',
    'C',
    'S',
    '국내전용(BC): 1천원 (기본 0원 + 제휴 1천원)
    \n국내외겸용(MASTER): 2천원 (기본 0원 + 제휴 2천원)',
    '[{"title": "기본 포인트", "desc": "전월 실적 따라 0.2% ~ 0.5%"},
      {"title": "추가포인트", "desc": "음식/주유 등 0.1% ~ 0.2% (개인사업자/소기업 한정)"},
      {"title": "사업지원 서비스", "desc": "부가세환급지원, 전자세금계산서(월250건 무료), 온라인 세무상담, 상권분석 서비스"}]',
    '- 기본 적립: 전월 실적 기준
     ㆍ100만원 미만: 0.2%
     ㆍ100만원 이상 ~ 300만원 미만: 0.3%
     ㆍ300만원 이상 ~ 500만원 미만: 0.4%
     ㆍ500만원 이상: 0.5%
    - 추가 적립(음식, 주유 등): 0.1% ~ 0.2% (중소기업확인서 필요)
    - 실적 제외: 제세공과금, 학부모부담금, 관리비, 공과금, 대학등록금, 상품권, 자동차업종, 후불교통, 수수료/이자/대출 등',
    0,
    NULL, NULL, NULL, NULL
);

/* 21 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    21,
    '후불 하이패스카드(기업)',
    '하이패스 차로 통과시 후불방식으로 이용하고
    \n결제일에 결제하는 후불 하이패스카드!',
    '#주유/차량 #비즈니스 #후불교통',
    'C',
    'S',
    '국내전용(BC): 2천원',
    '[{"title": "하이패스 후불 결제", "desc": "전국 고속도로·민자도로 톨게이트 후불 결제\nㆍ하이패스 단말기(OBU) 사용\nㆍ일반차로 결제 가능"}]',
    '- 한국도로공사 전국 고속도로 및 민자도로 통행료 후불 결제 전용
    - 출퇴근 시간대 통행료 자동 할인 (토/일/공휴일 제외)
    ㆍ대상 구간: 한국도로공사 관리 고속국도 요금소 간 거리 20km 미만 (민자도로 제외)
    ㆍ할인 시간 및 할인율:
      · 50% 할인: 출근 05:00~07:00, 퇴근 20:00~22:00 (1종~3종 차량)
      · 20% 할인: 출근 07:00~09:00, 퇴근 18:00~20:00
    - 경차 할인 50% (배기량 1,000cc 미만, 길이 3.6m 이하 등)',
    0,
    NULL, NULL, NULL, NULL
);

/* 22 */
INSERT INTO CARD (
    CARD_ID, NAME, DESCRIPTION, TAGS, CATEGORY_MAJOR, STATUS, ANNUAL_FEE,
    SERVICE, POINT_INFO, VIEW_COUNT,
    GUIDE_INFO, ONLINE_PAYMENT_GUIDE, ETC_GUIDE, TERMS_GUIDE
) VALUES (
    22,
    'SOHO-BIZ카드',
    '당행 최초로 보증료 (신용보증기금,기술보증기금,신용보증재단) 할인 서비스 탑재!',
    '#비즈니스 #주유/차량 #적립/캐시백 #후불교통',
    'C',
    'S',
    '국내전용(BC): 5,000원
    \n국내외겸용(VISA): 10,000원',
    '[{"title": "포인트 적립", "desc": "국내 이용금액 0.2% 적립"}, 
    {"title": "보증보험료 할인", "desc": "신용보증기금, 기술보증기금, 신용보증재단 월 최대 3만원 할인"}, 
    {"title": "주유 할인", "desc": "LPG 포함 3% 할인 (월 4회, 1회 최대 10만원)"}, 
    {"title": "사업지원서비스", "desc": "부가세 신고, 전자세금계산서, 세무상담"}]',
    '- 포인트 적립: 국내 사용금액 0.2% 적립 (일부 업종 제외)
    - 보증보험료 5% 할인 (월 최대 3만원)
    - GS칼텍스, SK주유소 주유 3% 할인 (LPG 포함)
      * 1일 1회, 월 4회, 1회 주유금액 최대 10만원 한도
    - 사업지원서비스는 전월 실적 조건 없이 제공
    단, 보증보험료와 주유 할인은 전월 30만원 이상 이용 시 제공',
    0,
    NULL, NULL, NULL, NULL
);

COMMIT;

SET DEFINE ON;

ALTER SEQUENCE CARD_ID_SEQ RESTART START WITH 23;