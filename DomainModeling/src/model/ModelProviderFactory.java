package model;

public class ModelProviderFactory implements BaseFactory{


    public ModelProvider create(Session session) {
        EntityManager em = session.getProvider(ConnectionProvider.class).getEntityManager();
        return new JpaModelProvider(session, em);
    }

}
