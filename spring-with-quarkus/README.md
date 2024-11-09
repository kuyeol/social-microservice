


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


                    private static ArrayList<HashMap<RelationshipType, Set<RelationshipType>>> getValidPaths (Node
                    departureAirport, Node arrivalAirport, Double maxDistance){
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
                        private void secondTraversal (ArrayList < MapResult > results, Integer recordLimit, Node
                        departureAirportDay, Node arrivalAirport, Double
                        maxDistance, PathFinder < WeightedPath > dijkstra){
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
                        ArrayList<HashMap<RelationshipType, Set<RelationshipType>>> validRels){
                            this.endCode = endCode;
                            this.stopTime = System.currentTimeMillis() + stopTime;
                            this.validRels = validRels;
                        }


                        @Override
                        public Iterable<Relationship> expand (Path path, BranchState < Double > branchState){
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
                        return results.stream();

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
