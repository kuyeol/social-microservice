package org.account.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.account.service.userprofile.UserProfileProvider;



public interface Session extends AutoCloseable {
  <T extends Provider> T getProvider(Class<T> clazz, String id);
  // <T extends Provider> T getProvider(Class<T> clazz);
  KeycloakContext getContext();
  KeycloakTransactionManager getTransactionManager();

  @Override
  void close();
  KeycloakSessionFactory getKeycloakSessionFactory();
  UserProvider users();
  UserSessionProvider sessions();
  UserProfileProvider getProvider(Class<UserProfileProvider> userProfileProviderClass);
}
