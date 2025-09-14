# BNK Reframe Server
### Spring Boot 기반 은행상품 판매/관리 서버 & 관리자 웹

이 레포지토리는 **BNK Reframe 2차 프로젝트**의 서버 및 관리자 웹 애플리케이션입니다.  
- [사용자 모바일 앱(Flutter)](https://github.com/Dayeong-dev/bnk_reframe)과 연동되는 백엔드 API 제공  
- 관리자 웹을 통해 상품, 가입 내역, 통계, 푸시 발송을 관리  

👉 전체 프로젝트 개요는 [허브 레포지토리](https://github.com/Dayeong-dev/bnk-project-2)에서 확인할 수 있습니다.  


## ✨ 주요 기능
### 서버(API)
- JWT 기반 로그인/회원가입, 본인인증
- 예·적금 상품 등록/수정/가입 처리
- 리뷰, FAQ, 1:1 문의 관리
- Excel/PDF 리포트 생성, FCM 푸시 발송
- Netty WebSocket 기반 실시간 알림

### 관리자 웹
- 상품 등록/수정/승인/결재 프로세스
- 가입 현황·이탈율·리뷰 통계 대시보드
- 푸시 발송 관리(즉시/예약, 발송 이력)
- 리뷰/FAQ/문의 관리 UI


## 🛠 기술 스택
- **백엔드**: Java 21, Spring Boot 3.5, Gradle
- **보안**: Spring Security, JWT(OAuth2 Resource Server)
- **데이터베이스**: Oracle DB, Spring Data JPA
- **웹/관리자 페이지**: Spring MVC, Thymeleaf
- **실시간**: Netty(WebSocket), Firebase Push
- **리포트/보고서**: Apache POI(Excel), OpenPDF(PDF)
- **기타**: Mail(SMTP), Jackson, Lombok, JUnit


## 📚 참고 문서
- [요구사항 명세서](https://github.com/Dayeong-dev/bnk-project-2/blob/main/docs/BNK_2차_프로젝트_요구사항명세서.xlsx)
- [ERD - 사용자, 인증, 계정 테이블](https://github.com/Dayeong-dev/bnk-project-2/blob/main/docs/BNK_2차_프로젝트_ERD_사용자_인증_계정.png)
- [ERD - 예·적금 상품, 상품 설명서, 금리 테이블](https://github.com/Dayeong-dev/bnk-project-2/blob/main/docs/BNK_2차_프로젝트_ERD_상품_설명서_금리.png)
- [ERD - 상품 가입, 신청, 납입 테이블](https://github.com/Dayeong-dev/bnk-project-2/blob/main/docs/BNK_2차_프로젝트_ERD_가입_신청_납입.png)
- [ERD - 앱푸시 알림 마케팅 분석 추천 테이블](https://github.com/Dayeong-dev/bnk-project-2/blob/main/docs/BNK_2차_프로젝트_ERD_앱푸시.png)


## ⚙️ 환경 설정
> 아래 내용을 참고하여 `src/main/resources/application.properties` 파일을 생성한 후 실행해주세요.

```properties
spring.application.name=[your_project_name]

server.port=8090

# Oracle DB 설정
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@localhost:1521/[데이터베이스 명]
spring.datasource.username=[데이터베이스 계정이름]
spring.datasource.password=[데이터베이스 비밀번호]

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
spring.jpa.show-sql=true

# SMTP 기본 설정
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=[이메일 계정 주소]
spring.mail.password=[앱 비밀번호/SMTP 인증 비밀번호]

# SMTP 인증 및 보안 
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# 공공데이터포털 API 키(국세청_사업자등록정보 진위확인 및 상태조회 서비스)
public.api.biz.service-key=[국세청_사업자등록정보 진위확인 및 상태조회 서비스 키]

# OpenAI API 키
openai.api.key=[OpenAI API 키]

# reCAPTCHA secret key
google.recaptcha.secret=[구글 reCAPTCHA 시크릿 키]

#jwt
spring.jwt.secret=[JWT 서명 키]
spring.jwt.issuer=reframe-api

spring.web.resources.static-locations=file:./uploads/,classpath:/static/

# Netty WebSocket 서버 포트
netty.port=8081

# WebSocket 접속 경로
netty.ws-path=/ws

# 발행 인증 토큰 (Spring이 이벤트 push할 때만 사용, 클라이언트 구독에는 필요 없음)
netty.publish-secret=[발행용 인증 토큰]

```

> 관리자 페이지 접속 링크는 아래를 참고해주세요. 
- 기본 포트: http://localhost:8090
- 관리자 페이지: http://localhost:8090/admin/signin-form

