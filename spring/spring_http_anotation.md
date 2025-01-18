# REST API Anotations

| 어노테이션                 | 데이터 출처     | 주요 사용 사례                          |
|-----------------------|------------|-----------------------------------|
| **@PathVariable**     | URL 경로     | 리소스 식별 (예: `/users/{id}`)         |
| **@RequestParam**     | URL 쿼리     | 필터링, 검색 (예: `/search?query=java`) |
| **@RequestBody**      | HTTP 요청 본문 | JSON 데이터 전송                       |
| **@RequestHeader**    | HTTP 헤더    | 인증, 사용자 에이전트                      |
| **@CookieValue**      | HTTP 쿠키    | 세션 식별, 상태 유지                      |
| **@ModelAttribute**   | 폼 데이터      | 객체 생성 및 초기화                       |
| **@RequestPart**      | 멀티파트 데이터   | 파일 업로드                            |
| **@SessionAttribute** | 세션 데이터     | 사용자 인증 정보                         |

---

## 0. Index

1. **URI 경로 정보**: `@PathVariable`[Go 🚀](#1-PathVariable)
2. **필터/검색 조건**: `@RequestParam`[Go 🚀](#2-RequestParam)
3. **JSON 요청 본문**: `@RequestBody`[Go 🚀](#3-RequestBody)
4. **인증, 사용자 에이전트**: `@RequestHeader`[Go 🚀](#4-RequestHeader)
5. **세션 식별, 상태 유지**: `@CookieValue`  [Go 🚀](#5-CookieValue)
6. **폼 데이터**:`@ModelAttribute`  [Go 🚀](#6-ModelAttribute)
7. **파일 업로드**: `@RequestPart`  [Go 🚀](#7-RequestPart)
8. **사용자 인증 정보**: `@SessionAttribute`  [Go 🚀](#8-SessionAttribute)

---

## 1. @PathVariable

- **설명**: URI 경로에서 값을 추출합니다.
- **특징**:
    - URL 경로의 일부를 매핑합니다.
    - `required` 속성으로 필수 여부를 설정할 수 있습니다.
    - 주로 리소스 식별에 사용됩니다.
- **적절한 사용 예시**:
  ```java
  @GetMapping("/users/{id}")
  public String getUserById(@PathVariable("id") Long id) {
      return "User ID: " + id;
  }
  ```
  **요청 예**: `GET /users/123`  
  **결과**: `id = 123`

---

# [Index ⬆️](#0-Index)

## 2. @RequestParam

- **설명**: URL 쿼리 파라미터를 매핑합니다.
- **특징**:
    - 쿼리 문자열 또는 HTML form 데이터에서 값을 추출합니다.
    - 기본값 설정이 가능하며, 필수 여부를 설정할 수 있습니다.
    - 여러 값(`List`, `Set` 등)도 받을 수 있습니다.
- **적절한 사용 예시**:
  ```java
  @GetMapping("/search")
  public String search(@RequestParam("query") String query,
                       @RequestParam(value = "page", defaultValue = "1") int page) {
      return "Search Query: " + query + ", Page: " + page;
  }
  ```
  **요청 예**: `GET /search?query=java&page=2`  
  **결과**: `query = "java", page = 2`

---

# [Index ⬆️](#0-Index)

## 3. **@RequestBody**

- **설명**: HTTP 요청 본문에서 JSON, XML 등의 데이터를 매핑합니다.
- **특징**:
    - 객체 변환(`@ModelAttribute`와 차이) 및 유효성 검증(@Valid)과 함께 사용 가능합니다.
    - `HttpMessageConverter`를 사용하여 요청 본문을 객체로 변환합니다.
- **적절한 사용 예시**:
  ```java
  @PostMapping("/users")
  public String createUser(@RequestBody User user) {
      return "Created User: " + user.getName();
  }
  ```
  **요청 예**:

```http request
POST http://localhost:8080/api

 {
  "name":"John" ,
  "email":"john@example.com"
  }
```

**결과**: `User 객체로 매핑`

---

# [Index ⬆️](#0-Index)

## 4. **@RequestHeader**

- **설명**: HTTP 헤더에서 값을 추출합니다.
- **특징**:
    - 클라이언트가 보낸 요청 헤더 값을 매핑합니다.
    - `defaultValue`와 `required` 속성 사용 가능.
    - 인증 정보나 클라이언트 정보를 전달받을 때 주로 사용됩니다.
- **적절한 사용 예시**:
  ```java
  @GetMapping("/api")
  public String getApi(@RequestHeader("Authorization") String token) {
      return "Token: " + token;
  }
  ```
  **요청 예**:
  ```
  GET /api
  Authorization: Bearer abc123
  ```
  **결과**: `token = "Bearer abc123"`

---

# [Index ⬆️](#0-Index)

## 5. **@CookieValue**

- **설명**: HTTP 쿠키에서 값을 추출합니다.
- **특징**:
    - 요청에 포함된 쿠키 값을 매핑합니다.
    - `defaultValue`와 `required` 속성 사용 가능.
    - 세션 관리나 사용자 식별 쿠키에 사용됩니다.
- **적절한 사용 예시**:
  ```java
  @GetMapping("/welcome")
  public String welcome(@CookieValue(value = "sessionId", defaultValue = "default") String sessionId) {
      return "Session ID: " + sessionId;
  }
  ```
  **요청 예**:
  ```
  GET /welcome
  Cookie: sessionId=xyz456
  ```
  **결과**: `sessionId = "xyz456"`

---

# [Index ⬆️](#0-Index)

## 6. **@ModelAttribute**

- **설명**: 요청 데이터를 객체에 바인딩합니다. 주로 폼 데이터에서 사용됩니다.
- **특징**:
    - GET/POST 요청에서 폼 데이터를 바인딩합니다.
    - 객체를 뷰로 전달하거나 기본값을 설정할 때 유용합니다.
    - URI 경로 변수와 함께 사용할 수도 있습니다.
- **적절한 사용 예시**:
  ```java
  @PostMapping("/register")
  public String registerUser(@ModelAttribute User user) {
      return "Registered User: " + user.getName();
  }
  ```
  **요청 예**:
  ```
  POST /register
  Content-Type: application/x-www-form-urlencoded
  name=John&email=john@example.com
  ```
  **결과**: `User 객체로 매핑`

---

# [Index ⬆️](#0-Index)

## 7. **@RequestPart**

- **설명**: 멀티파트 요청에서 데이터를 추출합니다.
- **특징**:
    - 파일 업로드 또는 JSON + 파일을 처리할 때 사용됩니다.
    - `MultipartFile` 또는 객체로 매핑 가능합니다.
- **적절한 사용 예시**:
  ```java
  @PostMapping("/upload")
  public String uploadFile(@RequestPart("file") MultipartFile file) {
      return "Uploaded File: " + file.getOriginalFilename();
  }
  ```
  **요청 예**:
  ```
  POST /upload
  Content-Type: multipart/form-data
  file: example.txt
  ```
  **결과**: 파일 처리

---

# [Index ⬆️](#0-Index)

## 8. **@SessionAttribute**

- **설명**: 세션에 저장된 데이터를 바인딩합니다.
- **특징**:
    - 세션에 저장된 데이터를 컨트롤러에서 읽거나 수정할 때 사용됩니다.
    - 주로 인증 상태 유지나 사용자 정보 저장에 사용됩니다.
- **적절한 사용 예시**:
  ```java
  @GetMapping("/dashboard")
  public String getDashboard(@SessionAttribute("user") User user) {
      return "Welcome, " + user.getName();
  }
  ```
  **결과**: 세션에서 `user` 객체를 가져옴.

---


