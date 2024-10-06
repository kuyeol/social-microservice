
> [!note] 
> 서버 엔드포인트 정의 목록 확인
> ``` http://localhost:9000/.well-known/openid-configuration   ```
> 




![img.png](img.png)


1. 인증 흐름 사양 정의 문서 링크[^1]

https://docs.spring.io/spring-authorization-server/reference/guides/how-to-ext-grant-type.html

[^1]: https://openid.net/specs/openid-connect-core-1_0.html#CodeFlowAuth

http://localhost:8080/hello?continue

http://localhost:9000/oauth2/authorize?
➥response_type=code&
➥client_id=client&
➥scope=openid&
➥redirect_uri=http://localhost:8080/login/ouath/code/login-client&
➥code_challenge=QYPAZ5NU8yvtlQ9erXrUYR-T5AGCjCF47vN-KsaI2A8&
➥code_challenge_method=S256

curl -X POST 'http://localhost:8080/oauth2/token?
➥client_id=client&
➥redirect_uri=http://localhost:8080/login/ouath/code/login-client&
➥grant_type=authorization_code&
➥code=ao2oz47zdM0D5gbAqtZVB…
➥code_verifier=qPsH306-… \
--header 'Authorization: Basic Y2xpZW50OnNlY3JldA=='