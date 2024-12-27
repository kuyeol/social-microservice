package org.acme.client.ungorithm;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class JpaEntityRep {

    public String name;
    public String password;

    private JpaEntityRep(String name, String password) {
        this.name = name;
        this.password = password;

    }

    public static JpaEntityRep from(JpaEntity jpa) {


        return new JpaEntityRep(
           jpa.getUsername(),
           jpa.getInputTwo()
        );
    }


}
