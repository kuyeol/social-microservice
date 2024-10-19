package model;

public class JpaModelProvider implements ModelProvider {

    private final Session session;
    protected EntityManager em;

    public JpaModelProvider(Session session, EntityManager em) {
        this.session = session;
        this.em = em;
    }


    @Override
    public Model addUser(String id, String username, boolean addDefaultRoles, boolean addDefaultRequiredActions) {
        return null;
    }

    @Override
    public void close() {

    }
}
