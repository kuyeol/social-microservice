package org.acme.client.ungorithm;


import io.quarkus.grpc.GrpcClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.acme.client.PersonsService;
import org.acme.client.ungorithm.jpa.PersistAndFlush;

@ApplicationScoped
@Transactional
public class Dao extends PersistAndFlush<JpaEntity> {


    @Inject
    EntityManager em;

    @GrpcClient
    PersonsService proto;


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
    public Repesentaion passwordCreate(String password) {

        return new Repesentaion();
    }

    @Transactional
    public Repesentaion save(JpaEntity entity) {
        return em.merge(entity);
    }

    @Transactional
    public void protoAdd(JpaEntity entity) {

        em.persist(entity);
    }


    @Transactional
    public TestCredential findCred(String id) {


        String Q = TestCredential.FIND_PARENT_user;


        JpaEntity jpaEntity;
        jpaEntity = reference(id);

        if (jpaEntity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        TestCredential cred;


        try {
            cred = em.createNamedQuery(Q, TestCredential.class)
                     .setParameter("user", jpaEntity)
                     .getSingleResult();

            return toDTO(cred);

        } catch (Exception e) {


            return new TestCredential();
        }


    }


    private static TestCredential toDTO(TestCredential entity) {
        TestCredential typedQuery = new TestCredential();

        typedQuery.setType(entity.getType() + "88");
        typedQuery.setSecretData(entity.getSecretData()
                                       .substring(0, 4) + "88");
        typedQuery.setCredentialData(entity.getCredentialData() + "88");
        return typedQuery;
    }


    @Transactional
    public Optional<Boolean> update(String id) {


        if (findID(id).isPresent()) {
            JpaEntity entity = em.find(JpaEntity.class, id);

            entity.setUsername("uodate");


            em.persist(entity);
            em.flush();
            return Optional.of(Boolean.TRUE);

        } else {

            return Optional.of(Boolean.FALSE);

        }

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


    public Optional<Boolean> remove(String id) {


        if (findID(id).isPresent()) {
            JpaEntity entity = em.find(JpaEntity.class, id);

            em.remove(entity);

            return Optional.of(Boolean.TRUE);

        } else {

            return Optional.of(Boolean.FALSE);

        }

    }


    public void close() {
        em.close();
    }

    @Transactional
    public Optional<JpaEntity> findID(String id) {

        return Optional.ofNullable(em.find(JpaEntity.class, id));

    }

    @Inject
    EntityManagerFactory emf;


    @Transactional
    @Override
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
