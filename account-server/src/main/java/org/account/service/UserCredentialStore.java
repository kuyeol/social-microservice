package org.account.service;


import java.util.stream.Stream;




public interface UserCredentialStore extends Provider
{

  void updateCredential(UserModel user, CredentialModel cred);
  CredentialModel createCredential(UserModel user, CredentialModel cred);


  boolean removeStoredCredential( UserModel user, String id);
  CredentialModel getStoredCredentialById( UserModel user, String id);


  Stream<CredentialModel> getStoredCredentialsStream( UserModel user);


  Stream<CredentialModel> getStoredCredentialsByTypeStream( UserModel user, String type);

  CredentialModel getStoredCredentialByNameAndType( UserModel user, String name, String type);

  //list operations
  boolean moveCredentialTo(UserModel user, String id, String newPreviousCredentialId);



}
