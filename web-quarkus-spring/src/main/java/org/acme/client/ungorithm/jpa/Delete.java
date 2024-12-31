package org.acme.client.ungorithm.jpa;

import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;

public abstract class Delete {

    public void delete() {
        JpaOperations.INSTANCE.delete(this);
    }

}
