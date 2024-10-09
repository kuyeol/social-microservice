
# Keycloak의 주요 아키텍처   

---
작업 순서

1. http 클라이언트 생성 

2. 보안 필터 구성 http 요청 응답
   - 클라이언트 요청이 일치 검증 제약 조건 생성
   - 
3. 클라이언트 등록

4. JWT 토큰 발급 프로세서 
5. 토큰 프로바이더

6. 클라이언트 토큰 발급
7. 클라이언트 인증 인가

---


### integration
- a
https://github.com/keycloak/keycloak/tree/main/integration


### rest
- a
https://github.com/keycloak/keycloak/tree/main/rest/admin-ui-ext/src/main/java/org/keycloak/admin/ui/rest


### services 
- a
https://github.com/keycloak/keycloak/tree/main/services/src/main/java/org/keycloak




--- 
- 컴파일
    - Maven 빌드 도구:
- 런타임
    - WildFly (기존):
    - Quarkus (최신):
---
## Authorization Server:
    - OAuth2 권한 서버로서의 역할
    - 인증된 사용자에게 액세스 토큰을 발급
    - 권한 서버로서 클라이언트 애플리케이션을 관리
    - 자체적으로 IdP로 동작하여 사용자의 신원을 확인함

- OAuth2 (OAuth 2.0):
    - 액세스 권한 위임을 관리하는 프로토콜
    - 자신의 자격 증명을 노출하지 않고 제3의 애플리케이션이 자신의 리소스에 접근할 수 있도록 허용
    - 액세스 토큰을 발급하고 관리
 



## Identity Provider (IdP):
    - 자체 IdP로 동작하여 사용자의 신원을 확인
    - 여러 **아이덴티티 제공자(IdP)**와 연동
- OpenID Connect (OIDC):
    - OAuth2 위에 구축된 프로토콜로, 사용자 인증을 지원
    - Auth2의 권한 위임을 확장하여 사용자의 신원(identity)을 인증할 수 있는 추가적인 정보를 제공
    - OIDC는 인증된 사용자에 대한 정보를 제공하는 ID 토큰을 발급
    - ID 토큰은 사용자의 신원 확인에 사용
## Admin Console:
  - 웹 기반의 관리 콘솔
  - 사용자, 클라이언트, 역할, 토큰 정책, 인증 정책 등을 관리

## Token Endpoint:
    - 토큰 엔드포인트를 통해 클라이언트에게 액세스 토큰과 리프레시 토큰을 발급
    - 엔드포인트는 OAuth2와 OIDC 흐름에 필수적인 역할

- JWT (JSON Web Token):
    - JWT를 사용하여 토큰을 발급
    - JWT는 Access Token, ID Token 형태로 사용
  



# 주요 기능

---
#### Role-Based Access Control (RBAC):
  - 사용자에게 **역할(Role)**을 부여하여 접근 제어를 관리합니다. 사용자는 여러 역할을 가질 수 있으며, 각 역할은 특정 리소스에 대한 권한을 정의
  - RBAC 모델은 Keycloak의 기본적인 권한 관리 방식이며 세분화된 리소스 접근 권한 제공

#### SSO (Single Sign-On):
  - 한 번 로그인하면 사용자는 여러 애플리케이션에 다시 로그인할 필요 없이 접근할 수 있습니다.
  - OAuth2 및 OIDC와 SAML을 통해 SSO를 지원
---

### Vert.x
- 비동기 이벤트 루프를 처리
- API 요청 처리
- 세션 데이터 및 클러스터 전반의 일관성을 유지

### Infinispan
- HA (High Availability)
- 분산 캐시 솔루션
- 세션 정보 및 인증 관련 데이터를 캐싱

### Hibernate 및 JPA (Java Persistence API)
- 클라이언트, 역할 및 권한 데이터 등을 저장하고 관리

### RESTEasy: RESTful 웹 서비스
- 인증 및 권한 부여 관련 작업을 처리
- 상호작용하는 주요 방식
