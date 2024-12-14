public interface Service {
ResponseEntity<?> handle(String method);
String getName();
}


@Path("/api")

public class Resource {

    @Inject
    Service example;

    public Resource(Service exampleResource) {
        this.example = exampleResource;
    }


    @GET
    public String hello() {
        return "Hello World";
    }


    @GET
    @Path("{uri}/{method}")
    public ResponseEntity<?> routeRequest(@PathParam("uri") String uri, @PathVariable String method) {


        try {
            if (example.getName().equals(uri)) {
                System.out.println(example.getName());
                return example.handle(method);
            } else if ("product".equals(uri)) {
                return example.handle(method);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

}



Path("/user")
@RestController
public class ChildService  implements Service{



    private final String serviceName= "user";



    @GET
    @Path("get")
    public Response getChildData( String id) {

        return Response.ok("Data for child with ID: " + id).build();
    }

    @Override
    public ResponseEntity<?> handle(String method) {

        if ("get".equals(method)) {
            return ResponseEntity.ok(getChildData(method));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public String getName() {
        return this.serviceName;
    }
}



----


예시 
베이스url은 보편적인 http url이다

자식서비스 uri = 베이스url/서비스명/{호출메서드}
로 명시적으로 정의 

부모 서비스에서 하나의 RestApi를 이용해 자식서비스명에 따라 호출하도록한다




디자인 패턴 문제:

상황: 다양한 종류의 커피
(아메리카노, 라떼, 카푸치노 등)를 만드는 프로그램을 작성해야 합니다.
각 커피 종류마다 만드는 방법이 다르지만,
공통적인 과정(물 끓이기, 원두 갈기 등)도 있습니다.
만약 새로운 커피 종류가 추가될 때마다 코드를 수정해야 한다면 유지보수가 어려워집니다.
이러한 문제를 해결하기 위해 어떤 디자인 패턴을 적용할 수 있을까요?
패턴 이름과 함께 간략한 설명을 작성해 주세요.
그리고 자바 코드 예시를 작성해 주시면 더 좋습니다.

데코레이터 패턴 또는 컴포사이트 패턴을 사용할수 있다

interface MachineManual {

void boiled(int temperature);
void Mixed(Bean bean);

}


class CoffeeMachine implement MachineManual{

private int temperature;


public void setTemperature(int temp){
this.temperature = temp;
}

public int putIngredients(int gram){
return gram;
}


public void boiled()
{
System.out.print("Boiled Set Temperature :"+ temperature);
System.out.print("Boiled Complete");
}

public void boiled(int temp)
{

setTemperature(temp)
 boiled();
}


public void Mixed(Bean bean){
bean.setGram(grams);
System.out.print("mixing Complete"+bean.getName());
}


}




class Ingredients {
String name();
public getName(){
return this.name;
}

class Water  extends Ingredients{


}
class Bean extends Ingredients{

public String setName(String name){
this.name=name;
}

public String setName(String name){
this.name=name;

}

class ColumbiaBean extends Bean{

}

class EhiopiaBean extends Bean{

}

class Toping extends Ingredients{
public String setName(String name){
this.name=name;
}

class Milk extends Toping {

}

class Cream extends Toping {

}

class Sugar extends Toping {

}




class Recipe extends CoffieMachine {

private Toping toping;
private Bean bean;

public void MachineOn(){
boiled();
Mixed();
}

public Menu Capppuchino(Bean bean, Toping toping){
return cappuchino;
}

public Order orderMenu(Menu menu ){

return order;
}

}




class Menu extends Bean , Ingredients{

private int size;
private Recipe recipe;
private Collection<Menu> toppings;

public Menu(int size , Recipe recipe){
this.size=size;
this.recipe=recipe;
}

public void addToppings(List<Toping> toppings){
tippings.add(toppings);

}
public int getSize(){
return this.size;
}


public void setSize(){
this.size;
}

}

class Order{
Menu menu;

public Menu orderCoffee(Menu menu){

menu = new Menu();
menu.setSize(menu.getSize());
mune.capuuchino();

}




상황: 게임 캐릭터를 생성하는 프로그램을 작성 중입니다. 캐릭터는 종족(인간, 엘프, 오크 등)과 직업(전사, 마법사, 궁수 등)에 따라 다른 능력치를 가집니다. 각 종족과 직업의 조합에 따라 캐릭터를 생성하는
로직을 일일이 작성하는 것은 비효율적입니다. 이 문제를 해결하기 위해 어떤 디자인 패턴을 적용할 수 있을까요? 패턴 이름과 함께 간략한 설명, 그리고 자바 코드 예시를 작성해 주세요.

SOLID 원칙 문제:

상황: 결제 시스템을 개발 중입니다. 현재는 카드 결제만 지원하지만, 향후 모바일 결제, 포인트 결제 등 다양한 결제 수단을 추가해야 합니다. 새로운 결제 수단이 추가될 때마다 기존 코드를 수정해야 한다면 유지보수가
어려워집니다. 어떤 SOLID 원칙을 적용하여 이 문제를 해결할 수 있을까요? 원칙의 이름과 함께 간략한 설명을 작성해 주세요. 자바 코드 예시를 통해 설명하면 더 좋습니다.

상황: 로깅 라이브러리를 사용하는 프로그램을 개발 중입니다. 특정 로깅 라이브러리에 의존적으로 코드를 작성하면 나중에 다른 로깅 라이브러리로 변경하기 어렵습니다. 어떤 SOLID 원칙을 적용하여 이 문제를 해결할 수
있을까요? 원칙의 이름과 함께 간략한 설명, 그리고 자바 코드 예시를 작성해 주세요.

위 문제들을 보고 떠오르는 답변을 자유롭게 작성해 주세요. 정답을 맞추는 것보다 어떤 방식으로 문제에 접근하고 해결하려고 하는지 과정을 보는 것이 더 중요합니다. 코드 예시를 작성할 때는 완벽한 코드보다는 핵심적인
부분을 보여주는 코드 스케치를 작성하는 것이 좋습니다. 답변을 기다리겠습니다!

public class Data {

private String name;
private String id;
private int size;
private Collection<Data> dataList= new ...<>();

//...todos

}

interface ReadOnly
{
Object find(T t);
Object find(Collection<T> tList);
}

interface WriteOnly
{
boolean add(Object o);
}

interface DataManger extends ReadOnly , WriteOnly
{
Object Update(T t);
boolean delete(T t);
}

public class Employee{
private String name;
private String grade;
//....todo field & Access &Construtor

}

public class JuniorEmployee extends Employee , implements ReadOnly{

private Data data;

Object find(T t){
//todo interface implements...
}
Object find(Collection<T> tList){
//todo interface implements...
}

}

public class MasterEmployee extends JuniorEmployee , implements DataManger{

//todo interface implements...

}

#Neo4j SAMPLE CODE Part.

```java

@Description("com.maxdemarzi.flightSearch() | Find Routes between Airports")
@Procedure(name = "com.maxdemarzi.flightSearch", mode = Mode.SCHEMA)
public Stream<MapResult> flightSearch(@Name("from") List<String> from,
    @Name("to") List<String> to,
    @Name("day") String day,
    @Name("recordLimit") Number recordLimit,
    @Name("timeLimit") Number timeLimit) {

    try (Transaction tx = db.beginTx()) {
        for (String fromKey : getAirportDayKeys(from, day)) {
            Node departureAirport = db.findNode(Labels.Airport, "code", fromKey.substring(0, 3));
            Node departureAirportDay = db.findNode(Labels.AirportDay, "key", fromKey);

            if (!(departureAirportDay == null)) {
                for (String toKey : getAirportDayKeys(to, day)) {
                    Node arrivalAirport = db.findNode(Labels.Airport, "code", toKey.substring(0, 3));
                    Double maxDistance = getMaxDistance(departureAirport, arrivalAirport);


                    // Get Valid Traversals from Each Departure Airport at each step along the valid paths
                    ArrayList<HashMap<RelationshipType, Set<RelationshipType>>> validRels =
                        allowedCache.get(fromKey.substring(0, 3) + "-" + toKey.substring(0, 3));


                    private static final LoadingCache<String, ArrayList<HashMap<RelationshipType, Set<RelationshipType>>>>
                        allowedCache = Caffeine.newBuilder()
                        .maximumSize(10_000)
                        .expireAfterWrite(1, TimeUnit.HOURS)
                        .refreshAfterWrite(1, TimeUnit.HOURS)
                        .build(Flights::allowedRels);

                    private static ArrayList<HashMap<RelationshipType, Set<RelationshipType>>> allowedRels (String key){
                        // calculate valid Relationships
                        Node departureAirport = graph.findNode(Labels.Airport, "code", key.substring(0, 3));
                        Node arrivalAirport = graph.findNode(Labels.Airport, "code", key.substring(4, 7));
                        Double maxDistance = getMaxDistance(departureAirport, arrivalAirport);
                        return getValidPaths(departureAirport, arrivalAirport, maxDistance);
                    }

```

```java


private static ArrayList<HashMap<RelationshipType, Set<RelationshipType>>> getValidPaths(Node
    departureAirport, Node arrivalAirport, Double maxDistance) {
    ArrayList<HashMap<RelationshipType, Set<RelationshipType>>> validRels = new ArrayList<>();

    // Traverse just the Airport to Airport  FLIES_TO relationships to get possible routes for second traversal
    TraversalDescription td = graph.traversalDescription()
        .breadthFirst()
        .expand(bidirectionalFliesToExpander, ibs)
        .uniqueness(Uniqueness.NODE_PATH)
        .evaluator(Evaluators.toDepth(2));

    // Since we know the start and end of the path, we can make use of a fast bidirectional traverser
    BidirectionalTraversalDescription bidirtd = graph.bidirectionalTraversalDescription()
        .mirroredSides(td)
        .collisionEvaluator(new CollisionEvaluator());

    for (org.neo4j.graphdb.Path route : bidirtd.traverse(departureAirport, arrivalAirport)) {
        Double distance = 0D;
        for (Relationship relationship : route.relationships()) {
            distance += (Double) relationship.getProperty("distance", 25000D);
        }


        // Yes this is a bit crazy to follow but it does the job
        if (distance < maxDistance) {
            String code;
            RelationshipType relationshipType = null;
            int count = 0;
            for (Node node : route.nodes()) {
                if (relationshipType == null) {
                    code = (String) node.getProperty("code");
                    relationshipType = RelationshipType.withName(code + "_FLIGHT");
                } else {
                    HashMap<RelationshipType, Set<RelationshipType>> validAt;
                    if (validRels.size() <= count) {
                        validAt = new HashMap<>();
                    } else {
                        validAt = validRels.get(count);
                    }
                    Set<RelationshipType> valid =
                        validAt.getOrDefault(relationshipType, new HashSet<>());
                    String newcode = (String) node.getProperty("code");
                    RelationshipType newRelationshipType =
                        RelationshipType.withName(newcode + "_FLIGHT");
                    valid.add(newRelationshipType);
                    validAt.put(relationshipType, valid);
                    if (validRels.size() <= count) {
                        validRels.add(count, validAt);
                    } else {
                        validRels.set(count, validAt);
                    }
                    relationshipType = newRelationshipType;
                    count++;
                }
            }
        }
    }
    return validRels;

```

```java

// Each path found is a valid set of flights,
private void secondTraversal(ArrayList<MapResult> results, Integer recordLimit, Node
    departureAirportDay, Node arrivalAirport, Double
    maxDistance, PathFinder<WeightedPath> dijkstra) {
    for (org.neo4j.graphdb.Path position : dijkstra.findAllPaths(departureAirportDay,
        arrivalAirport)) {
        if (results.size() < recordLimit) {
            HashMap<String, Object> result = new HashMap<>();
            ArrayList<Map> flights = new ArrayList<>();
            Double distance = 0D;
            ArrayList<Node> nodes = new ArrayList<>();
            for (Node node : position.nodes()) {
                nodes.add(node);
            }

            for (int i = 1; i < nodes.size() - 1; i += 2) {
                Map<String, Object> flightInfo = nodes.get(i).getAllProperties();
                flightInfo.put("origin",
                    ((String) nodes.get(i - 1).getProperty("key")).substring(0, 3));
                flightInfo.put("destination",
                    ((String) nodes.get(i + 1).getProperty("key")).substring(0, 3));
                // These are the epoch time date fields we are removing
                // flight should have departs_at and arrives_at with human readable date times (ex: 2016-04-28T18:30)
                flightInfo.remove("departs");
                flightInfo.remove("arrives");
                flights.add(flightInfo);
                distance += ((Number) nodes.get(i).getProperty("distance", 0)).doubleValue();
            }

            result.put("flights", flights);
            result.put("score", position.length() - 2);
            result.put("distance", distance.intValue());
            results.add(new MapResult(result));
        }
    }
}
```

```java


public PathRestrictedExpander(String endCode, long stopTime,
    ArrayList<HashMap<RelationshipType, Set<RelationshipType>>> validRels) {
    this.endCode = endCode;
    this.stopTime = System.currentTimeMillis() + stopTime;
    this.validRels = validRels;
}


@Override
public Iterable<Relationship> expand(Path path, BranchState<Double> branchState) {
    // Stop if we are over our time limit
    if (System.currentTimeMillis() < stopTime) {
        if (path.length() < 8) {

            if (((path.length() % 2) == 0) &&
                ((String) path.endNode().getProperty("key")).substring(0, 3).equals(endCode)) {
                return path.endNode()
                    .getRelationships(Direction.INCOMING, RelationshipTypes.HAS_DAY);
            }

            if (path.length() > 2 && ((path.length() % 2) == 1)) {
                Iterator<Node> nodes = path.reverseNodes().iterator();
                Long departs = (Long) nodes.next().getProperty("departs");
                nodes.next(); // skip AirportDay node
                Node lastFlight = nodes.next();
                if (((Long) lastFlight.getProperty("arrives") + minimumConnectTime) > departs) {
                    return Collections.emptyList();
                }
            }

            if (path.length() < 2) {
                RelationshipType firstRelationshipType =
                    RelationshipType.withName(((String) path.startNode()
                        .getProperty("key")).substring(0, 3) + "_FLIGHT");
                RelationshipType[] valid = validRels.get(0).get(firstRelationshipType)
                    .toArray(new RelationshipType[validRels.get(0).get(firstRelationshipType)
                        .size()]);
                return path.endNode().getRelationships(Direction.OUTGOING, valid);
            } else {
                int location = path.length() / 2;

                if (((path.length() % 2) == 0)) {
                    return path.endNode()
                        .getRelationships(Direction.OUTGOING, validRels.get(location)
                            .get(path.lastRelationship().getType())
                            .toArray(new RelationshipType[validRels.get(location)
                                .get(path.lastRelationship().getType()).size()]));
                } else {
                    Iterator<Relationship> iter = path.reverseRelationships().iterator();
                    iter.next();
                    RelationshipType lastRelationshipType = iter.next().getType();
                    return path.endNode().getRelationships(Direction.OUTGOING,
                        validRels.get(location).get(lastRelationshipType)
                            .toArray(new RelationshipType[validRels.get(location)
                                .get(lastRelationshipType).size()]));
                }
            }
        }
    }

    return Collections.emptyList();
}
```

```java
                        // Order the flights by # of hops, departure time, distance and the first flight code if all else is equal
                        results.sort(FLIGHT_COMPARATOR);
                        return results.

stream();

```

```java



@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "user")
@Fetch(FetchMode.SELECT)
@BatchSize(size = 20)
protected Collection<CredentialEntity> credentials = new LinkedList<>();

---

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "USER_ID")
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
repository.save(new Aircraft(81L,            "AAL608", "1451", "N754UW", "AA608", "IND-PHX", "A319", "A3", 36000, 255,
423, 0, 36000, 39.150284, -90.684795, 1012.8, 26.575562, 295.501994, true, false, Instant.parse("2020-11-27T21:29:35Z"),
Instant.parse("2020-11-27T21:29:34Z"), Instant.parse("2020-11-27T21:29:27Z")));

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

- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes
  with Swagger UI
