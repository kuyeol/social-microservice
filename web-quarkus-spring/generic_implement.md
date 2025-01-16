import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry {
private Map<Class<? extends Provider>, Provider> providers = new HashMap<>();

    // Provider 등록 메서드
    public <T extends Provider> void registerProvider(Class<T> clazz, T provider) {
        providers.put(clazz, provider);
    }

    // Provider 반환 메서드
    public <T extends Provider> T getProvider(Class<T> clazz) {
        return clazz.cast(providers.get(clazz));
    }
}


public class Main {
public static void main(String[] args) {
ServiceRegistry registry = new ServiceRegistry();

        // Provider 등록
        registry.registerProvider(ServiceProvider.class, new ServiceProvider("Service A"));
        registry.registerProvider(AnotherProvider.class, new AnotherProvider("Service B"));

        // Provider 가져오기 및 사용
        ServiceProvider serviceProvider = registry.getProvider(ServiceProvider.class);
        serviceProvider.provide();  // 출력: ServiceProvider provides: Service A

        AnotherProvider anotherProvider = registry.getProvider(AnotherProvider.class);
        anotherProvider.provide();  // 출력: AnotherProvider provides: Service B
    }
}