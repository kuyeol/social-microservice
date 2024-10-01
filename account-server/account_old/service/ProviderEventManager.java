package org.account.service;


import org.account.represetion.identitymanagement.ProviderEvent;

public interface ProviderEventManager
{
  void register(ProviderEventListener listener);

  void unregister(ProviderEventListener listener);

  void publish( ProviderEvent event);
}
