package org.acme.client.ungorithm;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class Dao {


    @Inject
    EntityManager em;


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
    public Repesentaion create(JpaEntity entity) {

        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        try {

            UserAttributes userProperties = new UserAttributes();
            userProperties.setUser(entity);
            userProperties.setAttributeName("testKey");
            userProperties.setAttributeValue("TestValue");
          TestCredential ts = new TestCredential();
          ts.setJpaEntity(entity);


            em.persist(entity);

            return entity;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create entity", e);
        }


    }

    public void close(){
        em.close();
}

}
