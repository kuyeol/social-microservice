package org.acme.client.ungorithm;

public interface JpaType {

String getId();
    default JpaType getType() {
        return this;
    }
}
