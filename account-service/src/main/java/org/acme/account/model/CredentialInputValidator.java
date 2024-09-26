package org.acme.account.model;


import org.acme.account.util.CredentialInput;


public interface CredentialInputValidator
{
  boolean supportsCredentialType(String credentialType);
  boolean isConfiguredFor( UserModel user, String credentialType);


  boolean isValid( UserModel user, CredentialInput credentialInput);
}
