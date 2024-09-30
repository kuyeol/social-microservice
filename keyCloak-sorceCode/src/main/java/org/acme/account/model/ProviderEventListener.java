package org.acme.account.model;


import org.acme.account.represetion.identitymanagement.ProviderEvent;


public interface ProviderEventListener
{
  void onEvent( ProviderEvent event);
}
