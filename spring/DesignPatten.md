# Chain of Responsibility

var king = new OrcKing();
```java

king.makeRequest(new Request(RequestType.DEFEND_CASTLE, "defend castle"));
```

 1. 오크킹 클래스에 인터페이스 리쿼스트핸들러를 필드로 정의 
    - `private List<RequestHandler> handlers;`

2. 오크킹 인스턴스 생성 메서드 파라미터로 클래스 리퀘스트 전달
    - king.makeRequest(`new Request(RequestType.DEFEND_CASTLE, "defend castle")`);
    - 
3. 리퀘스트 클래스에는 열거형 필드가 정의되어있고 생성자에 포함됨
    -  private final RequestType requestType;
```java
public enum RequestType {
DEFEND_CASTLE, TORTURE_PRISONER, COLLECT_TAX
}
```

4. 열거형 으로 전달 받은 타입을 리퀘스트핸들러 구현클래스 인스턴스 생성
    - class OrcCommander implements RequestHandler


# Pattern Example

---

```java
public static void main(String[] args) {

    var king = new OrcKing();
    king.makeRequest(new Request(RequestType.DEFEND_CASTLE, "defend castle"));
    king.makeRequest(new Request(RequestType.TORTURE_PRISONER, "torture prisoner"));
    king.makeRequest(new Request(RequestType.COLLECT_TAX, "collect tax"));
}

```

```java
public class OrcKing {

    private List<RequestHandler> handlers;

    public OrcKing() {
        buildChain();
    }

    private void buildChain() {
        handlers = Arrays.asList(new OrcCommander(), new OrcOfficer(), new OrcSoldier());
    }

    public void makeRequest(Request req) {
        handlers
                .stream()
                .sorted(Comparator.comparing(RequestHandler::getPriority))
                .filter(handler -> handler.canHandleRequest(req))
                .findFirst()
                .ifPresent(handler -> handler.handle(req));
    }
}
```

```java
public interface RequestHandler {

    boolean canHandleRequest(Request req);

    int getPriority();

    void handle(Request req);

    String name();
}

```

```java

@Getter
public class Request {

    private final RequestType requestType;
    private final String requestDescription;
    private boolean handled;

    public Request(final RequestType requestType, final String requestDescription) {
        this.requestType = Objects.requireNonNull(requestType);
        this.requestDescription = Objects.requireNonNull(requestDescription);
    }

    public void markHandled() {
        this.handled = true;
    }

    @Override
    public String toString() {
        return getRequestDescription();
    }
}
```


```java

public enum RequestType {
    DEFEND_CASTLE, TORTURE_PRISONER, COLLECT_TAX
}
```

```java

@Slf4j
public class OrcCommander implements RequestHandler {
    @Override
    public boolean canHandleRequest(Request req) {
        return req.getRequestType() == RequestType.DEFEND_CASTLE;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public void handle(Request req) {
        req.markHandled();
        LOGGER.info("{} handling request \"{}\"", name(), req);
    }

    @Override
    public String name() {
        return "Orc commander";
    }
}
```
