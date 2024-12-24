package org.acme.client.ungorithm;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.acme.core.utils.ModelUtils;

@ApplicationScoped
@Transactional
public class Dao {


    @Inject
    EntityManager em;

    @Transactional
    public Repesentaion find(String name) {


        JpaEntity copy = em.find(JpaEntity.class, name);


        return copy;
    }

    @Transactional
    public  UserProperties findProp(Long name) {


        return em.find(UserProperties.class, name);
    }

    public Repesentaion create(JpaEntity entity) {

        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        try {
            // ID 생성 및 설정
            String id = ModelUtils.generateId();
            entity.setId(id);

            Map<String, String> map = new HashMap<String,String>();

            for (Map.Entry<String, String> entry : map.entrySet()) {

                entity.addProperty(entry.getKey(), entry.getValue());
            }


            // 엔티티 저장
            em.persist(entity);

            // 추가 속성 생성 및 설정
            UserProperties userProperties = new UserProperties();
            // userProperties.setUser(entity);
            // userProperties.setPropertyName(entity.getTimestamp().toString());
            //userProperties.setPropertyValue(entity.address);

            // em.persist(userProperties);
            em.flush();

            // 저장된 엔티티의 정보를 포함하여 반환
            Repesentaion result = entity;

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create entity", e);
        }


    }

    @Transactional
    public Repesentaion create2(JpaEntity entity) {
        // ID 생성 및 설정
        String id = ModelUtils.generateId();
        entity.setId(id);

        // 기본 속성 추가

        // 엔티티 저장
        em.persist(entity);

        // 추가 속성 생성 및 설정
        UserProperties userProperties = new UserProperties();
        userProperties.setUser(entity);
        userProperties.setPropertyName("user");
        userProperties.setPropertyValue("additional value");  // 값 설정 추가

        // propertyId는 @GeneratedValue를 사용하도록 수정 권장
        // userProperties.setPropertyId(3333L);  // 직접 ID 설정은 권장하지 않음

        em.persist(userProperties);
        em.flush();

        return new Repesentaion();
    }




}
