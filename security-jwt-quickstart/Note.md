    

    // TODO : 개발 절차 

## 1. 간단하게 엔터티와 모델 작성
> [!Note]
> 렐름 -> 리소스 서버
> 
> 클라이언트 -> 마이크로서비스
> 
> 컴포넌트 -> 리소스 서버에 제공된키

## 2. 모델에 RSA 키 저장

> [!Note]   
> .aaa

## 3. KeyLoad implements
> [!Note]
> .

## 4. RSA 사용 JWT 발급
> [!Note] 
> .

- 쿼리파라미터로 엔드포인트에 요청
- 일다 클라이언트아이디와 비번을 헤더에 같이 보내서 검증후 발급

토큰 요청 엔드포인트 구성
[TokenManager.java](referenceSource/oidc/TokenManager.javaㄴ)

[TokenEndpoint.java](referenceSource/oidc/endpoints/TokenEndpoint.java)
referenceSource/oidc/OIDCLoginProtocolService.java
[OIDCLoginProtocolService.java](referenceSource/oidc/OIDCLoginProtocolService.java)

# jwt + 클레임 구성 참고

- [코드경로 바로 가기](referenceSource/microprofile-jwt-auth/api/src/main/java/org/eclipse/microprofile)

# 바운시캐슬 using quarkus 테스트 코드 레포 링크

- https://github.com/quarkusio/quarkus/blob/main/integration-tests/bouncycastle-jsse/src/main/java/io/quarkus/it/bouncycastle/BouncyCastleJsseEndpoint.java
- https://github.com/quarkusio/quarkus/blob/main/integration-tests/bouncycastle/src/main/java/io/quarkus/it/bouncycastle/BouncyCastleEndpoint.java

eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.

eyJpc3MiOiJodHRwczovL2V4YW1wbGUuY29tL2lzc3VlciIsInVwbiI6ImpxdWFya3VzLmlvIiwiZ3Jvd
XBzIjpbIlVzZXIiLCJBZG1pbiJdLCJiaXJ0aGRhdGUiOiIyMDAxLTA3LTEzIiwiaWF0IjoxNzI4NzAwOTQwLCJleHAiOjE3Mjg3MDEyNDAsImp0aSI6IjV
hZGQzOWIzLTA1MzAtNGZmZS05YjE0LTkxODY5ODNmOGVjOCJ9.

kyZ3SXNDBXzs_LEzcS2_bDphrFpE8a2r-62PHo0glpwkKK8_hUwMKTc1iz7rkZvFZySU
LeB4R41IRyT5lux9uDVJy24zLR_qPAr7wvY8BT33lH9k-_BUjj6TtMwYrbAsiFtc-zb5utoi34wQCpINgw0jMLom1bQIoscG4p2Tp5Yhw0S2lzHvQBcu5y
YNl5jvnQW2KGekRBWot5N_suua4FeSFazBKWYehrBjHeU0ChmSG_FZGm76CIeKVIbQ07zMzsnJKHxZHCyt4_kHtoLTJIRJZAH5z5wJhisO_zSrCekSRP0SvUVNdLideLJuP_NbzMoN6narA7M-yam7EVwp1A

eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.

eyJpc3MiOiJodHRwczovL2V4YW1wbGUuY29tL2lzc3VlciIsInVwbiI6ImpxdWFya3VzLmlvIiwiZ3Jvd
XBzIjpbIlVzZXIiLCJBZG1pbiJdLCJiaXJ0aGRhdGUiOiIyMDAxLTA3LTEzIiwiaWF0IjoxNzI4NzAwOTE2LCJleHAiOjE3Mjg3MDEyMTYsImp0aSI6ImU
xZjI5Yzc4LWRmNmYtNDZhYS05MWM5LTJmYjkzMWM4YWNhZSJ9.

ay83SWLZ06S32kNOT7aofMabzyotaFUunF-gBL05-ckL6eY20L92zgdVNEuUGPVNCDF3
gqbguOcQ4eeUt6HlvPrb5asW19J-cOD6LV_I0AtYQlbU68D_SPE_D4-uAEvnXuMUHr2ePEfGtwq7YmswQwEckGSDQhwd51pEzd8mS1ztDUKM8QdDD1aDMM
ONZtK9GP-8aAyrpldK-KviHoX54qq6clCzvplJjvVirubi-O9Oqj-CrTLIlDrycqvLkvPKab2mcmCd5toCIvmjm0Elo78vmtZcbXT6Ges9UO1iDK4-4BeyRz900jgAh2nrn1CyG_V26Htl_Yw6hvrpUH154w

```json
{
  "hashIterations": 27500,
  "algorithm": "pbkdf2-sha256",
  "additionalParameters": {}
}
```

```json

{
  "value": "z7rOzXFHm04t3NFf/lqnvpAfFmjUtF6G6Dg6K8nqweI=",
  "salt": "MkRLUbCul+DaT13z96kczw==",
  "additionalParameters": {}
}

```

# 키클록 키생성 로직 경로

keycloak/common/crypto/

---

```
curl -v http://127.0.0.1:9000/secured/roles-allowed; echo
```

```http request

curl -H "Authorization: Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuY29tL2lzc3VlciIsInVwbiI6ImpxdWFya3VzLmlvIiwiZ3JvdXBzIjpbIlVzZXIiLCJBZG1pbiJdLCJiaXJ0aGRhdGUiOiIyMDAxLTA3LTEzIiwiaWF0IjoxNzI4NTI2NTQyLCJleHAiOjE3Mjg1MjY4NDIsImp0aSI6IjU5Y2UzNTE3LTk2ZjktNDE4Ni1iNzY0LTkxMmVmODMzMmY1ZSJ9.arsibdRDYQWNvtpXMIfwku1_3VUwIBRvXrBIuh8UDBDusO8i543V5Dix-RUH9CgZ5pgxdw9FzCfb9MP-5pSGmxxcdt_DjZla7bGKhFH1j02H2_ew_CivPBFYLKM_Wmg7tyQFcGhmiTekT77yuM6fZllXPznZEvGm5mD8f3aF35KfkkLt68AoRR-ViBBykffyYFq-XJ5h6hA44HtXXr0odJizjvFmUqAhOAPD1A_qK6HJYjHUe1XSpTEmhvt_8rCzGBt8vnebUig4joRoHrw3QmgV3rnQn803u9j9fyrAs3a63rp6vLpJGrPQYsSRemWM4azu5gL7tNFkk2rDIUPfvA
    "
http://127.0.0.1:9000/secured/roles-allowed; echo


```

> [ 예상 응답 내용 ]
>> hello jdoe@quarkus.io, isHttps: false, authScheme: Bearer, hasJWT: true, birthdate: 2001-07-13


---
토큰 요청 생성 최소 구성으로 정의

ctx.getUserPrincipal().getName() -> [ jquarkus.io]
ctx.isSecure() -> [ isHttps: false],
ctx.getAuthenticationScheme() -> [authScheme: Bearer],
jwt.getClaimNames() -> [hasJWT: true],
jwt.getClaim("birthdate").toString() -> [birthdate: 2001-07-13]

---
