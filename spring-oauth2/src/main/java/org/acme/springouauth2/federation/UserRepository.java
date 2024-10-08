package org.acme.springouauth2.federation;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {


    private final Map<String, OAuth2User> userCache = new ConcurrentHashMap<>();

    public OAuth2User findByName( String name) {
        return this.userCache.get(name);
    }

    public void save(OAuth2User oauth2User) {
        this.userCache.put(oauth2User.getName(), oauth2User);
    }

}


