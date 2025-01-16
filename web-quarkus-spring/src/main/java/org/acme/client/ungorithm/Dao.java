package org.acme.client.ungorithm;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;

@ApplicationScoped
public class Dao {


    @Inject
    EntityManager em;


        @Transactional
    public JpaEntity reference(String id) {

        return em.getReference(JpaEntity.class, id);
    }

    @Transactional
    public Repesentaion findByName(String name) {

        return em.find(JpaEntity.class, name);
    }

    @Transactional
    public UserAttributes findProp(String name) {

        return em.find(UserAttributes.class, name);
    }

    @Transactional
    public static Repesentaion passwordCreate(Class<?> c, EntityManager em) {

        em.getReference(c, "");

        return new Repesentaion();
    }

    @Transactional
    public void save(JpaType entity) {

        em.persist(entity);
    }

    @Transactional
    public void protoAdd(JpaEntity entity) {

        em.persist(entity);
    }

    @Transactional
    public synchronized TestCredential findCred(String id, Class<?> c) {


        String Q = TestCredential.FIND_PARENT_user;

        if (reference(id) == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        TestCredential cred;
        try {

            cred = em.createNamedQuery(Q, TestCredential.class)
                     .setParameter("user", c)
                     .getSingleResult();
            cred.setType("findcred");
            em.persist(cred);
        } catch (Exception e) {

            return new TestCredential();
        }

        return new TestCredential(cred);
    }

    @Transactional
    public synchronized TestCredential findCred(JpaEntity entity,Class<?> c) {


        String Q = TestCredential.FIND_PARENT_user;

        if (reference(id) == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        TestCredential cred;

        try {

            cred = (TestCredential) em.createNamedQuery(query, c)
                                      .setParameter("user", entity)
                                      .getSingleResult();

            cred.setCreatedDate(Instant.now()
                                       .toEpochMilli());

        } catch (Exception e) {

            return new TestCredential();
        }

        return new TestCredential(cred);
    }

    private String query;
    private String id;

    public Dao query(String query, String id) {
        this.query = query;
        this.id    = id;
        return this;
    }


    private static TestCredential toDTO(TestCredential entity) {

        entity.setType(entity.getType() + "testType");
        entity.setSecretData(entity.getSecretData() + "88");
        entity.setCredentialData(entity.getCredentialData() + "88");
        return entity;
    }

    @Transactional
    public <T extends JpaType> Optional<Boolean> update(String id, JpaType jpaType) {


        TestCredential testCredential = em.find(TestCredential.class, jpaType.getId());

        if (!checkCredentialEntity(testCredential, id)) {
            return Optional.empty();
        } else {
            em.persist(jpaType);
            em.flush();


            return Optional.of(Boolean.TRUE);
        }
    }

    private boolean checkCredentialEntity(TestCredential entity, String ID) {
        if (entity.getUser()
                  .getId()
                  .equals(ID)) {
            System.out.println("TRUE~~~");
            return true;
        } else {
            System.out.println("FALSE~~~");
            return false;
        }


    }

    private boolean checkCredentialEntity(TestCredential entity, JpaEntity user) {
        return entity != null && entity.getUser() != null && entity.getUser()
                                                                   .getId()
                                                                   .equals(user.getId());
    }


    @Transactional
    public Repesentaion create(JpaEntity entity) {

        JpaEntity ref = em.getReference(JpaEntity.class, entity.getId());

        if (findByName(entity.getId()) != null) {
            throw new IllegalArgumentException("Entity is Exist");
        }

        try {

            persistAndFlush(entity);


            UserAttributes userProperties = new UserAttributes();
            TestCredential ts             = new TestCredential();


            userProperties.setUser(ref);
            ts.setUser(ref);


            em.persist(userProperties);
            em.persist(ts);


            return entity;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create entity", e);
        }


    }


    public <T extends JpaType> Optional<Boolean> remove(String id, Class<T> entity) {


        if (findID(id, entity) != null) {
            JpaEntity tar = em.find(JpaEntity.class, id);

            em.remove(tar);

            return Optional.of(Boolean.TRUE);

        } else {

            return Optional.of(Boolean.FALSE);

        }

    }


    public void close() {
        em.close();
    }

    @Transactional
    public <T extends JpaType> JpaType findID(String id, Class<T> c) {

        return em.find(c, id);
    }

    @Inject
    EntityManagerFactory emf;


    @Transactional
    public void persistAndFlush(JpaEntity entity) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction()
              .begin();
            em.persist(entity);
            em.flush();
            em.getTransaction()
              .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
