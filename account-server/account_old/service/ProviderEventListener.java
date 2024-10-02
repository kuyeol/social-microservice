package org.account.service;


import org.account.represetion.identitymanagement.ProviderEvent;

public interface ProviderEventListener
{
  void onEvent( ProviderEvent event);
}
