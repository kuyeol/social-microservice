# vertx OAuth2
https://github.com/eclipse-vertx/vertx-auth/tree/master/vertx-auth-oauth2/src/main/java/io/vertx/ext/auth/oauth2

# Apache CFX
https://github.com/apache/cxf/tree/main/rt/rs/security/oauth-parent/oauth2/src/main/java/org/apache/cxf/rs/security/oauth2


# 쿼커스 ___GrantType___ 코드플로우 가이드
https://quarkus.io/guides/security-oidc-code-flow-authentication#accessing-authorization-data


https://datatracker.ietf.org/doc/html/draft-ietf-oauth-v2-31#section-4.1


https://github.com/vert-x3/vertx-redis-client/blob/master/src/main/java/io/vertx/redis/client/impl/BaseRedisClient.java
https://github.com/vert-x3/vertx-cassandra-client/blob/master/src/main/java/io/vertx/cassandra/impl/ResultSetImpl.java
# 리디렉션uri를 외부 노출중인 엔드포인트로 출력하기
## 역방향 프록시 뒤에서 Quarkus 애플리케이션 실행
[쿼커리레퍼런스링크](https://quarkus.io/guides/security-oidc-code-flow-authentication#running-quarkus-application-behind-a-reverse-proxy)
Quarkus 애플리케이션이 리버스 프록시, 게이트웨이 또는 방화벽 뒤에서 실행 중일 때 OIDC 인증 메커니즘이 영향을 받을 수 있으며, HTTP Host헤더가 내부 IP 주소로 재설정되고 HTTPS 연결이 종료될 수 있습니다. 예를 들어, 권한 부여 코드 흐름 redirect_uri매개변수가 예상 외부 호스트 대신 내부 호스트로 설정될 수 있습니다.

이런 경우, 프록시에서 전달된 원래 헤더를 인식하도록 Quarkus를 구성하는 것이 필요합니다. 자세한 내용은 역방향 프록시 Vert.x 문서 섹션 뒤에서 실행을 참조하세요.

예를 들어, Quarkus 엔드포인트가 Kubernetes Ingress 뒤의 클러스터에서 실행되는 경우 계산된 redirect_uri매개변수가 내부 엔드포인트 주소를 가리킬 수 있으므로 OIDC 공급자에서 이 엔드포인트로 리디렉션하는 것이 작동하지 않을 수 있습니다. 다음 구성을 사용하여 이 문제를 해결할 수 있습니다. 여기서 X-ORIGINAL-HOSTKubernetes Ingress에서 외부 엔드포인트 주소를 나타내도록 를 설정합니다.:


















# code-with-quarkus

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- OpenID Connect Database Token State Manager ([guide](https://quarkus.io/guides/security-openid-connect-client)): Store an OpenID Connect token state in a database
- OpenID Connect Client ([guide](https://quarkus.io/guides/security-openid-connect-client)): Get and refresh access tokens from OpenID Connect providers
- OpenID Connect ([guide](https://quarkus.io/guides/security-openid-connect)): Verify Bearer access tokens and authenticate users with Authorization Code Flow
- RESTEasy Client - OpenID Connect Filter ([guide](https://quarkus.io/guides/security-openid-connect-client)): Use a RESTEasy Client filter to get and refresh access tokens with OpenID Connect Client and send them as HTTP Authorization Bearer tokens
- SmallRye JWT ([guide](https://quarkus.io/guides/security-jwt)): Secure your applications with JSON Web Token
- Vault ([guide](https://quarkiverse.github.io/quarkiverse-docs/quarkus-vault/dev/index.html)): Store your credentials securely in HashiCorp Vault
- RESTEasy Client - OpenID Connect Token Propagation ([guide](https://quarkus.io/guides/security-openid-connect-client)): Use RESTEasy Client filter to propagate the incoming Bearer access token or token acquired from Authorization Code Flow as HTTP Authorization Bearer token
- SmallRye JWT Build ([guide](https://quarkus.io/guides/security-jwt-build)): Create JSON Web Token with SmallRye JWT Build API
