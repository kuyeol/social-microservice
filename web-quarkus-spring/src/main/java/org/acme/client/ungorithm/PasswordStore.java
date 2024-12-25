package org.acme.client.ungorithm;

import java.util.Collection;
import java.util.LinkedList;

public class PasswordStore {

    private final JpaEntity jpaEntity;

    public PasswordStore(JpaEntity jpaEntity) {
        this.jpaEntity = jpaEntity;
    }


    public JpaEntity getJpaEntity() {


        return jpaEntity;
    }

    private Collection<TestCredential> credentials = new LinkedList<>();

    public void setCredentials(Collection<TestCredential> cred) {
        this.credentials = cred;
    }

    public TestCredential createCredential(String data, String secret) {

        if (jpaEntity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }


        try {

            TestCredential credential = new TestCredential();
            credential.setCredentialData(data);
            credential.setSecretData(secret);
            credential.setJpaEntity(jpaEntity);
            credentials.add(credential);
            return credential;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create entity", e);
        }
    }

}
