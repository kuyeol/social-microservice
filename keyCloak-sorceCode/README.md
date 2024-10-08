
# Name	
- Value
# additionalParameters
- {"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}
# algorithm	
- argon2
# hashIterations	
- 5











# Programatic Programmer

## _소통하라_
개발자로서 우리는 여러 층과 소통해야 한다. 회의를 하고, 듣고 말하며 여러 시간을 보낸다. 하루 중 많은 시간을 소통하며 보내기 때문에, 이를 잘해야 할 필요가 있다. 최고의 아이디어, 최상의 코드 혹은 실용주의적인 사고 등이 있다고 해도 다른 사람들과 소통할 수 없다면 아무 소용이 없다.

## _말하고 싶은게 무언지 알아라_
무엇을 말할지 미리 계획하라. 개요를 작성하고 자문하라. “이게 내가 말하고자 하는 것을 잘 전달하는가?” 그렇게 될 때까지 다듬어라.

## _청중을 알아라_
정보를 전달하고 있는 경우에만 소통하고 있다고 말할 수 있다. 그러기 위해서는 청중의 요구와 관심, 능력을 이해할 필요가 있다. 우리는 개발자가 어떤 난해한 기술의 장점에 대해 읊조리는 동안, 앉아 있는 마케팅 부사장의 눈이 점점 흐리멍텅해지는 회의에 참석해봤다. 이것은 소통이 아니다. 단지 지껄임일 뿐, 짜증나는 일이다.

## _설계_
직교적인 설계를 테스트하는 손쉬운 방법이 있다. 컴포넌트들을 나누었을 때 다음과 같이 스스로에게 물어보라. “특정 기능에 대한 요구사항을 극적으로 변경했을 경우, 몇 개의 모듈이 영향을 받는가?” 직교적인 시스템에서는 답이 하나여야 한다.
또한 현실 세계의 변화와 설계 사이의 결합도를 얼마나 줄였는지에 대해서도 스스로에게 물어보기 바란다. 자신의 힘으로 제어할 수 없는 속성에 의존하지 마라.

---

```java

User.findOneByLogin(login):

이 방법( findOneByLogin)은 사용자의 이름, 이메일 등을 기준으로 데이터베이스를 쿼리하여 사용자를 찾을 가능성이 높습니다 login.
이는 Optional<User>사용자를 데이터베이스에서 찾을 수도 있고 찾을 수 없을 수도 있음을 의미하는 를 반환합니다.
    
.ifPresent(user -> { ... }):
이것은 사용자가 발견되었는지 확인합니다. 이 Optional.ifPresent()메서드는 사용자가 존재하는 경우(즉, 존재하는 경우) 실행되는 람다 표현식을 사용합니다.
사용자가 존재하면 람다 코드 블록이 실행되고, 사용자 객체가 블록으로 전달됩니다.
    
String currentEncryptedPassword = user.password;:

이는 사용자의 암호화된 비밀번호(데이터베이스에 저장됨)를 검색하여 currentEncryptedPassword변수에 저장합니다.
if (!passwordHasher.checkPassword(currentClearTextPassword, currentEncryptedPassword)):

제공된 비밀번호(일반 텍스트, 아마도 사용자가 로그인 시 입력한 비밀번호)가 데이터베이스에 저장된 암호화된 비밀번호와 일치하는지 확인합니다.
passwordHasher.checkPassword()해싱 및/또는 솔팅을 사용하여 정확성을 검증할 수 있으며, 일반 텍스트 비밀번호와 암호화된 비밀번호를 비교하는 방법입니다.
비밀번호가 일치하지 않으면(즉, 메서드가 를 반환하는 경우 false) 다음 단계로 진행합니다.
throw new InvalidPasswordException();:

비밀번호가 일치하지 않으면 InvalidPasswordException제공된 비밀번호가 올바르지 않다는 것을 나타내는 이 발생합니다.
요약해서:
이 코드는 다음을 통해 사용자를 인증하려고 시도합니다.

. 을 사용하여 데이터베이스에서 사용자를 찾습니다 login.
사용자가 존재하면 제공된 비밀번호가 저장된(암호화된) 비밀번호와 일치하는지 확인합니다.
비밀번호가 일치하지 않으면 .이 발생합니다 InvalidPasswordException.
이 패턴은 비밀번호가 암호화되어 로그인 시도 중에 안전하게 비교되어야 하는 사용자 인증 논리에서 흔히 사용됩니다.

```
























# account-service

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

You can then execute your native executable with: `./target/account-service-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
