

```java



@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy="user")
@Fetch(FetchMode.SELECT)
@BatchSize(size = 20)
protected Collection<CredentialEntity> credentials = new LinkedList<>();

---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    protected UserEntity user;


```


```sql
create table hall
(
seatcount integer     not null,
id        varchar(36) not null
primary key,
venue_id  varchar(36)
constraint fkx55jd3ym293mw39i0sfnb16v
references venue,
hallname  varchar(255)
);

alter table hall
owner to quarkus;
```



```sql
-- auto-generated definition
create table credential
(
    id              varchar(36) not null
        constraint constraint_f
            primary key,
    salt            bytea,
    type            varchar(255),
    user_id         varchar(36)
        constraint fk_pfyr0glasqyl0dei3kl69r6v0
            references user_entity,
    created_date    bigint,
    user_label      varchar(255),
    secret_data     text,
    credential_data text,
    priority        integer
);

alter table credential
    owner to quarkus;

create index idx_user_credential
    on credential (user_id);

```











@PostConstruct
private void loadData() {    
repository.deleteAll();    
repository.save(new Aircraft(81L,            "AAL608", "1451", "N754UW", "AA608", "IND-PHX", "A319", "A3",            36000, 255, 423, 0, 36000,            39.150284, -90.684795, 1012.8, 26.575562, 295.501994,            true, false,            Instant.parse("2020-11-27T21:29:35Z"),            Instant.parse("2020-11-27T21:29:34Z"),            Instant.parse("2020-11-27T21:29:27Z")));














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

- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
