package org.acme.account.model;


import org.acme.account.represetion.identitymanagement.ProviderEvent;


public interface ProviderEventManager
{
  void register(ProviderEventListener listener);

  void unregister(ProviderEventListener listener);

  void publish( ProviderEvent event);
}
