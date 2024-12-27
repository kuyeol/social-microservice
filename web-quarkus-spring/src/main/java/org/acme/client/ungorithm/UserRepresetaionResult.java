package org.acme.client.ungorithm;

import java.util.Optional;

public class UserRepresetaionResult {


    private final JpaEntityRep result;

    private UserRepresetaionResult(JpaEntityRep result) {
        this.result = result;
    }

    public static UserRepresetaionResult found(JpaEntityRep data) {
        return new UserRepresetaionResult(data);
    }
    public Optional<JpaEntityRep> getResult() {
        return Optional.ofNullable(result);
    }
}
