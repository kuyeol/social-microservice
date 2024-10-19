package model;


public interface ConnectionProvider  extends Provider {

    EntityManager getEntityManager();
}
