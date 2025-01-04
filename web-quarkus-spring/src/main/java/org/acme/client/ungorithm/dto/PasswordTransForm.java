package org.acme.client.ungorithm.dto;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.acme.client.ungorithm.TestCredential;

public class PasswordTransForm {

    @Inject
    EntityManager em;


    //todo: null 테이블 처리 중복 불가

    private              String         id;
    private              String         password;
    private              TestCredential credential;
    private final static String         MASK = "********";

    public PasswordTransForm(TestCredential credential) {

        if (credential.getUser() == null) {
            this.id       = credential.getId();
            this.password = MASK;
        } else {
            this.id       = credential.getId();
            this.password = maskString(credential.getSecretData(), 4);
        }
    }


    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }


   private PasswordTransForm() {
     super();
    }

    public TestCredential input(TestCredential credential) {

        TestCredential newCredential = new TestCredential();
        this.id       = credential.getId();
        this.password = credential.getSecretData();
        newCredential.setUser(credential.getUser());
        newCredential.setSecretData(credential.getSecretData());
        newCredential.setCreatedDate(Instant.now()
                                            .toEpochMilli());
        return newCredential;
    }

    public PasswordTransForm output(TestCredential credential) {
        this.id       = credential.getId();
        this.password = credential.getSecretData();
        credential.setSecretData(credential.getSecretData());
        credential.setCreatedDate(Instant.now()
                                         .toEpochMilli());
        return new PasswordTransForm(credential);
    }


    private static String maskString(String input, int startIndex) {

        if (input == null || startIndex >= input.length()) {
            return "[" + MASK + "]";
        } else {
            StringBuilder masked = new StringBuilder(input.substring(0, startIndex));
            for (int i = startIndex; i < input.length(); i++) {
                masked.append('*');
            }
            return masked.toString();
        }
    }


}
