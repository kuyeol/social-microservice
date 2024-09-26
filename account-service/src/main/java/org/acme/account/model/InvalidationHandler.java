package org.acme.account.model;


public interface InvalidationHandler
{

  public interface InvalidableObjectType {}

  public enum ObjectType implements InvalidableObjectType {
    _ALL_, REALM, CLIENT, CLIENT_SCOPE, USER, ROLE, GROUP, COMPONENT, PROVIDER_FACTORY
  }


  void invalidate(KeycloakSession session, InvalidableObjectType type, Object... params);
}
