


# jwt + 클레임 구성 참고
[코드 바로 가기](referenceSource/microprofile-jwt-auth/api/src/main/java/org/eclipse/microprofile)


# 바운시캐슬 using quarkus 테스트 코드 레포 링크
https://github.com/quarkusio/quarkus/blob/main/integration-tests/bouncycastle-jsse/src/main/java/io/quarkus/it/bouncycastle/BouncyCastleJsseEndpoint.java
https://github.com/quarkusio/quarkus/blob/main/integration-tests/bouncycastle/src/main/java/io/quarkus/it/bouncycastle/BouncyCastleEndpoint.java


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