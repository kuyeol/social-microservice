package org.acme.core.security.jwt;


import io.vertx.reactivex.ext.web.sstore.redis.RedisSessionStore;
import io.vertx.redis.client.Response;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import io.quarkus.redis.client.RedisClient;








@ApplicationScoped
public class RedisSessionManager {


    @Inject
    RedisClient redisClient;

    @ConfigProperty(name = "session.timeout")
    long sessionTimeout;

    RedisSessionStore sessionStore;

    public String createSession(String userId) {
        String sessionId = UUID.randomUUID().toString();
        // Store the customer ID associated with the session in redis.



        redisClient.setex(sessionId, String.valueOf(Duration.ofSeconds(sessionTimeout).toSeconds()), userId);
        return sessionId;
    }


    public String getUserIdFromSession(String sessionId) {
        Response response = redisClient.get(sessionId);
        if (response == null){
            return null;
        }
        return response.toString();
    }


    public void invalidateSession(List<String> sessionId) {
        redisClient.del(sessionId);
    }
}