

# Device Sample Convert To Token OAuth Flow
- refactoring Reference sample code 
- 

- [OAuth2 장치 검증 엔드포인트](https://docs.spring.io/spring-authorization-server/reference/1.4/protocol-endpoints.
html#oauth2-token-endpoint)

- [DeviceClientAuthenticationProvider](https://github.com/spring-projects/spring-authorization-server/tree/main/samples/demo-authorizationserver/src/main/java/sample/authentication)

---

> [!note] 
> 서버 엔드포인트 정의 목록 확인
> ```http request 
> http://localhost:9000/.well-known/openid-configuration   
> ```
 

- 클라이언트가 사용자를 인증하기 위해 리디렉션할 권한 부여 엔드포인트
  - "http://localhost:8080/oauth2/authorize"     

- 클라이언트가 액세스 토큰을 요청하기 위해 호출할 토큰 엔드포인트
  - "token_endpoint": "http://localhost:8080/oauth2/token" 

- 리소스 서버가 토큰을 검증에 사용할 수 있는 공개 키,세트를 얻기 위해 호출하는 엔드포인트
  - "jwks_uri": "http://localhost:8080/oauth2/jwks"   

- 리소스 서버가 불투명 토큰을 검증하기 위해 호출할 수 있는 인트로스펙트 엔드포인트
  - "http://localhost:8080/oauth2/introspect"                  

 ---
           /oauth2/authorize?


![img.png](img.png)


1. 인증 흐름 사양 정의 문서 링크[^1]

https://docs.spring.io/spring-authorization-server/reference/guides/how-to-ext-grant-type.html

[^1]: https://openid.net/specs/openid-connect-core-1_0.html#CodeFlowAuth

http://localhost:8080/hello?continue



```bash

domain=localhost;
port = 9000;


curl --request POST \
  --url 'https://{yourDomain}/oauth/token' \
  --header 'content-type: application/x-www-form-urlencoded' \
  --data grant_type=authorization_code \
  --data 'client_id={yourClientId}' \
  --data 'client_secret={yourClientSecret}' \
  --data 'code=yourAuthorizationCode}' \
  --data 'redirect_uri={https://yourApp/callback}'
  
  
  
http://{domain}:port/authorize?
    response_type=code&
    client_id={ClientId}&
    redirect_uri={http://yourApp/callback}&
    scope={scope}&
    state={state}
   

    
http://localhost:9000/oauth2/authorize?
  response_type=code&
  client_id=client&
  scope=openid&
  redirect_uri=http://localhost:8080/login/ouath/code/login-client&
  code_challenge=QYPAZ5NU8yvtlQ9erXrUYR-T5AGCjCF47vN-KsaI2A8&
  code_challenge_method=S256
    
```



curl -X POST 'http://localhost:8080/oauth2/token?
➥client_id=client&
➥redirect_uri=http://localhost:8080/login/ouath/code/login-client&
➥grant_type=authorization_code&
➥code=ao2oz47zdM0D5gbAqtZVB…
➥code_verifier=qPsH306-… \
--header 'Authorization: Basic Y2xpZW50OnNlY3JldA=='