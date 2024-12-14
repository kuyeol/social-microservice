package org.acme.security;


import io.quarkus.redis.client.RedisHostsProvider;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import java.net.URI;
import java.util.Collections;
import java.util.Set;


@ApplicationScoped
@Identifier("redis-provider")
public class RedisProvider implements RedisHostsProvider {


    @Override
    public Set<URI> getHosts() {
        String host = "redis://182.218.135.247:6379/0";
        return Collections.singleton(URI.create(host));
    }
}