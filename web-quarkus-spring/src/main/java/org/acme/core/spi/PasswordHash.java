package org.acme.core.spi;


import org.acme.core.model.PasswordCredentialModel;
import org.acme.core.security.hash.PasswordPolicy;

public interface PasswordHash {


    boolean policyCheck(PasswordPolicy policy, PasswordCredentialModel credential);

    PasswordCredentialModel encodedCredential(String rawPassword, int iterations);


    boolean verify(String rawPassword, PasswordCredentialModel credential);



    /**
     * @deprecated Exists due the backwards compatibility. It is recommended to use {@link #encodedCredential(String, int)}}
     */

}
