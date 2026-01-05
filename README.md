# Planner Backend (Spring Boot)
JWT 기반 인증을 적용한 Spring Boot REST API 서버입니다.  
회원가입/로그인 후 Bearer 토큰으로 인증이 필요한 API를 호출할 수 있으며, Swagger(OpenAPI)로 API 문서를 제공합니다

---

## ✨ 주요 기능
- **회원가입**: 중복 username 체크 후 BCrypt로 비밀번호 암호화 저장 
- **로그인**: username/password 검증 후 **JWT 발급(만료 1시간)** 
- **내 정보 조회**: 인증된 사용자 기반 `/api/users/me` 제공 
- **인증/인가**
  - `/api/users/signup`, `/api/users/login`, `/swagger-ui/**`, `/v3/api-docs/**` 는 **permitAll**
  - 그 외 요청은 **JWT 인증 필터**로 보호 
- **Swagger(OpenAPI) UI** 제공 
- **H2 Console 설정(로컬 확인 용도)** 

---

## 🧰 기술 스택
- Java 17
- Spring Boot 3.5.3, Spring Security 
- Spring Data JPA, Validation, Web 
- JWT (jjwt 0.11.5) 
- springdoc-openapi (Swagger UI) 
- DB: H2(콘솔 설정), MySQL 의존성 포함 
- p6spy (쿼리 로깅) 

---

## 📁 패키지 구조 (요약)
- `my.planner`
  - `controller` : User API 
  - `service` : 비즈니스 로직(회원가입/로그인) 
  - `repository` : JPA Repository 
  - `domain` : JPA Entity(User)
  - `config` : Spring Security 설정
  - `jwt` : JWT 유틸/필터/UserDetails

---

## 🚀 실행 방법 (Local)
### 1) 요구사항
- JDK 17 

### 2) 실행
```bash
./gradlew bootRun
