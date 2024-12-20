package org.acme.core.spi;

import java.util.stream.Stream;
import org.acme.core.model.CredentialModel;

public interface CredentialStore<T> {

    void updateCredential(T t, CredentialModel cred);

    CredentialModel createCredential(T t, CredentialModel cred);

    boolean removeStoredCredential( T t, String id);
    CredentialModel getStoredCredentialById( T t, String id);

    Stream<CredentialModel> getStoredCredentialsStream( T t);


    Stream<CredentialModel> getStoredCredentialsByTypeStream(T t, String type);

    CredentialModel getStoredCredentialByNameAndType( T t, String name, String type);

    //list operations
    boolean moveCredentialTo(T t, String id, String newPreviousCredentialId);

}
