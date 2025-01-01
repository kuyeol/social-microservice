package security;

import io.quarkus.security.credential.Credential;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import java.security.Guard;
import java.security.Principal;
import java.util.Map;
import java.util.Set;

public abstract class Permission implements Guard {

    public void checkGuard() throws SecurityException {
        SecurityIdentity si = new SecurityIdentity() {
            @Override
            public Principal getPrincipal() {
                return null;
            }

            @Override
            public <T extends Principal> T getPrincipal(Class<T> clazz) {
                return SecurityIdentity.super.getPrincipal(clazz);
            }

            @Override
            public boolean isAnonymous() {
                return false;
            }

            @Override
            public Set<String> getRoles() {
                return Set.of();
            }

            @Override
            public boolean hasRole(String s) {
                return false;
            }

            @Override
            public <T extends Credential> T getCredential(Class<T> aClass) {
                return null;
            }

            @Override
            public Set<Credential> getCredentials() {
                return Set.of();
            }

            @Override
            public <T> T getAttribute(String s) {
                return null;
            }

            @Override
            public Map<String, Object> getAttributes() {
                return Map.of();
            }

            @Override
            public Uni<Boolean> checkPermission(java.security.Permission permission) {
                return null;
            }

            @Override
            public boolean checkPermissionBlocking(java.security.Permission permission) {
                return SecurityIdentity.super.checkPermissionBlocking(permission);
            }
        };
    }


}
