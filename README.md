# Planner Backend (Spring Boot)
JWT 기반 인증을 적용한 Spring Boot REST API 서버입니다.  
회원가입/로그인 후 Bearer 토큰으로 인증이 필요한 API를 호출할 수 있으며, Swagger(OpenAPI)로 API 문서를 제공합니다. :contentReference[oaicite:1]{index=1}

---

## ✨ 주요 기능
- **회원가입**: 중복 username 체크 후 BCrypt로 비밀번호 암호화 저장 :contentReference[oaicite:2]{index=2}
- **로그인**: username/password 검증 후 **JWT 발급(만료 1시간)** :contentReference[oaicite:3]{index=3}
- **내 정보 조회**: 인증된 사용자 기반 `/api/users/me` 제공 :contentReference[oaicite:4]{index=4}
- **인증/인가**
  - `/api/users/signup`, `/api/users/login`, `/swagger-ui/**`, `/v3/api-docs/**` 는 **permitAll**
  - 그 외 요청은 **JWT 인증 필터**로 보호 :contentReference[oaicite:5]{index=5}
- **Swagger(OpenAPI) UI** 제공 :contentReference[oaicite:6]{index=6}
- **H2 Console 설정(로컬 확인 용도)** :contentReference[oaicite:7]{index=7}

---

## 🧰 기술 스택
- Java 17 :contentReference[oaicite:8]{index=8}
- Spring Boot 3.5.3, Spring Security :contentReference[oaicite:9]{index=9}
- Spring Data JPA, Validation, Web :contentReference[oaicite:10]{index=10}
- JWT (jjwt 0.11.5) :contentReference[oaicite:11]{index=11}
- springdoc-openapi (Swagger UI) :contentReference[oaicite:12]{index=12}
- DB: H2(콘솔 설정), MySQL 의존성 포함 :contentReference[oaicite:13]{index=13}
- p6spy (쿼리 로깅) :contentReference[oaicite:14]{index=14}

---

## 📁 패키지 구조 (요약)
- `my.planner`
  - `controller` : User API :contentReference[oaicite:15]{index=15}
  - `service` : 비즈니스 로직(회원가입/로그인) :contentReference[oaicite:16]{index=16}
  - `repository` : JPA Repository :contentReference[oaicite:17]{index=17}
  - `domain` : JPA Entity(User) :contentReference[oaicite:18]{index=18}
  - `config` : Spring Security 설정 :contentReference[oaicite:19]{index=19}
  - `jwt` : JWT 유틸/필터/UserDetails :contentReference[oaicite:20]{index=20}

---

## 🚀 실행 방법 (Local)
### 1) 요구사항
- JDK 17 :contentReference[oaicite:21]{index=21}

### 2) 실행
```bash
./gradlew bootRun
