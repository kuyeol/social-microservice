package org.acme.client.ungorithm.jpa;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.acme.client.ungorithm.JpaEntity;

public abstract class PersistAndFlush<T> {



    @Transactional
    public abstract void persistAndFlush(JpaEntity entity);




}
