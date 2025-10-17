CREATE SEQUENCE SEQ_FAQ_ID START WITH 100 INCREMENT BY 1;

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '체크카드는 연회비가 있나요?',
  '체크카드는 별도의 연회비가 없습니다.(발급 수수료 1천원 발생)
단, 하이체크글로벌카드(국내외겸용)의 경우 연회비 1천원(발급비용 없음)이 발생합니다.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '카드 탈회를 신청하고 싶습니다. 어떻게 하나요?',
  '카드 탈회는 가까운 영업점 방문 또는 인터넷(모바일)뱅킹, 고객센터에서 가능합니다.
단, 미결제잔액이 없는 경우 탈회 신청 가능
▶ 인터넷뱅킹 > 카드 > 카드관리 > 카드해지/해지취소
▶ 모바일뱅킹 > 카드 > 내카드 > 카드해지
▶ 부산은행 고객센터(☎1588-6200)',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '아파트 관리비 할인되는 카드는 어떤게 있나요?',
  '현재 당행 상품 중, 할인되는 카드는 BNK 부자되세요 아파트카드, ZipL 신용카드가 있습니다.
▶ 상세 내용은 서식/약관/자료실 -> 카드 -> 상품설명서 참고바랍니다.
▶ BNK 부자되세요 아파트카드는 영업점 발급만 가능하며, ZipL 신용카드는 인터넷뱅킹/모바일뱅킹/영업점 모두 발급 가능합니다.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '해외에서 이용 가능한 체크카드도 있나요?',
  '네, 있습니다.
■ 프렌즈 체크(마스터)카드 : 해외물품구매 및 현금인출 가능
■ 하이체크 글로벌카드 : 오프라인 해외물품구매 및 현금인출 가능, BC카드사 대행발급 (배송기간 7일~10일 소요)
■ CUP(은련) 브랜드 체크카드 : 오프라인 해외물품구매 및 현금인출 가능
■ DFS(글로벌) 브랜드 체크카드 : 현금인출만 가능',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '카드 분실 신고 후 카드를 다시 찾을 경우에는 어떻게 해야 하나요?',
  '카드 분실신고 해제는 가까운 영업점 방문 또는 비씨카드사 고객센터, 모바일뱅킹 앱에서 가능합니다.
▶ 비씨카드사(ARS ☎1588-4515) > 3.분실해제
※ 신고일 포함 3영업일 이내 가능
▶ 모바일뱅킹 > 카드 > 카드관리 > 도난,분실 > 분실신고 취소
※ 등록일로부터 6개월 이내 가능',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '후불하이패스 카드 신청은 어디서 하나요?',
  '후불하이패스 카드는 가까운 영업점 방문 또는 인터넷(모바일)뱅킹에서 발급 가능합니다.
단, 신용카드 미발급 고객의 경우, 신용카드 신규발급 심사를 통한 신용카드 발급 후 후불하이패스카드 신청 가능합니다.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '카드발급 신청은 어디에서 할 수 있나요?',
  '카드 발급 신청은 가까운 영업점 방문 또는 인터넷(모바일)뱅킹, 모바일웹에서 가능합니다.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '미성년자도 체크카드 발급이 가능한가요?',
  '미성년자 체크카드 발급은 만14세 미만 본인 단독발급은 불가하며, 만14세 이상 ~ 만18세 이하는 본인 및 법정대리인 발급이 가능합니다.
※ 금융사고 예방을 위하여 미성년자 단독방문 시 내방지점 문의 필요',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '카드결제계좌 변경은 어떻게 하나요?',
  '카드결제계좌 변경은 가까운 영업점 방문 또는 모바일뱅킹, 고객센터에서 가능합니다.
▶ 모바일뱅킹 > 카드 > 이용금액결제 > 결제정보관리
▶ 부산은행 고객센터(☎1588-6200)',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '전 세계 공항라운지 무료 이용되는 카드는 어떤게 있나요?',
  '현재 당행 상품 중, 전 세계 공항라운지 무료 이용되는 카드는 플래티늄카드가 있습니다.
단, 신용카드 실적에 따라 무료 제공 횟수가 상이하니 참고바랍니다.
▶ 상세 내용은 서식/약관/자료실 -> 카드 -> 상품설명서 참고바랍니다.
▶ 플래티늄카드의 경우, 영업점 발급만 가능합니다.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '신용카드 결제계좌를 타행의 계좌로 바꿀 수 있나요?',
  '네, 타행 계좌로 변경 가능합니다.
결제일 당일 또는 연체 시 완납 후 익영업일부터 결제일 4영업일까지 가능합니다.
※ 구매신용카드 및 체크카드 회원은 타행결제계좌 신청 불가',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '체크카드를 발급 받으려면 발급 자격이 어떻게 되나요?',
  '체크카드는 만14세 이상 발급 가능하며, 만12~13세 발급 시 법정대리인만 신청 가능합니다. (미성년자 단독신청 불가)
단, 후불교통카드가 포함된 체크카드는 만18세 이상 발급 가능합니다.
※ 은행연합회 등 불량정보등재자는 발급 제외',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '카드 사용한도가 부족하여 한도를 올리고자 합니다. 어떻게 해야 할까요?',
  '카드 사용한도는 카드 이용실적, 한도소진율, 연체유무 등을 감안하여 고객 동의 시 정기적으로 한도 상향 가능하며,
고객요청 시(카드 관리점 신청) 월 소득, 사용실적 등을 심사하여 상향가능합니다.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '분실된 체크카드의 재발급은 어떻게 해야 하나요?',
  '■ 비대면 분실재발급 가능 체크카드
- 오늘은 e카드, 청춘불패369, 마이존, 하이체크, 동백전, DingDing',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '해외에서 신용카드 이용 시 수수료는 어떻게 되나요?',
  '■ 해외 물품구매 : 해외 이용금액의 0.25%
■ 해외 현금서비스 : 해외 이용금액의 0.25% + 고객등급별 국내 현금서비스 수수료율
※ 해당 거래 시 Visa, Master카드는 이용금액의 1.0% 브랜드수수료 부과',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '체크카드를 사용하면 유리한 점이 무엇인가요?',
  '이용 즉시 계좌에서 출금되어 연체부담 없는 계획적인 소비생활이 가능합니다.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '후불교통카드 사용금액은 연말정산 받을 수 있나요?',
  '네, 연말정산 소득공제 가능합니다.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '카드',
  '결제일 변경 방법을 알고 싶습니다.',
  '카드 결제일 변경은 가까운 영업점 방문 또는 고객센터 신청, 모바일뱅킹 앱에서 가능합니다.
단, 결제일 변경에 따른 대금청구 또는 단기카드대출(현금서비스) 한도 생성 관련 확인 필요합니다.
▶ 모바일뱅킹 > 카드 > 이용금액결제 > 결제정보관리',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '예금',
  '정기예금과 자유적금의 차이는 무엇인가요?',
  '정기예금은 일정 기간 동안 목돈을 예치하는 상품이며, 자유적금은 원하는 때에 자유롭게 납입 가능한 적립식 상품입니다.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '예금',
  '적금 만기 전에 해지하면 어떻게 되나요?',
  '중도 해지 시 약정한 금리보다 낮은 중도해지 금리가 적용되며, 일부 상품은 우대금리도 제외될 수 있습니다.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '예금',
  '예금자 보호는 어떻게 적용되나요?',
  '예금보험공사에서 1인당 원금과 이자를 합쳐 5천만 원까지 보호합니다.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '예금',
  '정기예금 자동재예치 설정은 어떻게 하나요?',
  '예금 가입 시 또는 만기 전에 자동재예치를 신청할 수 있으며, 모바일뱅킹 또는 고객센터를 통해 변경 가능합니다.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '예금',
  '적금 이자는 언제 입금되나요?',
  '일반적으로 적금 만기일에 원금과 함께 이자가 일괄 지급됩니다.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);


INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '기타',
  '모바일뱅킹 앱이 자주 종료돼요. 어떻게 하나요?',
  '앱 최신 버전으로 업데이트 후에도 문제가 지속되면, 캐시 삭제 또는 재설치를 권장드립니다.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '기타',
  '인터넷뱅킹 비밀번호를 잊어버렸어요. 어떻게 해야 하나요?',
  '모바일 또는 웹에서 비밀번호 재설정 절차를 진행하거나 가까운 영업점을 방문해 주세요.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '기타',
  '고객센터 운영 시간은 어떻게 되나요?',
  '고객센터는 평일 오전 9시부터 오후 6시까지 운영됩니다.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '기타',
  'OTP 기기가 작동하지 않아요.',
  '배터리를 확인하거나 앱 OTP로 전환 가능합니다. 문제가 지속될 경우 영업점에 문의해 주세요.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

INSERT INTO faq_board (faq_id, category, question, answer, regdate, moddate) VALUES (
  SEQ_FAQ_ID.NEXTVAL,
  '기타',
  '영문통장 발급이 가능한가요?',
  '네, 가까운 영업점 방문 시 영문통장 발급이 가능합니다. 신분증을 지참해 주세요.',
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS'),
  TO_DATE('2025-07-10 01:29:59', 'YYYY-MM-DD HH24:MI:SS')
);

commit;