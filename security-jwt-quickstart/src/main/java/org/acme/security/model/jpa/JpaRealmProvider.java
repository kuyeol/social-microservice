package org.acme.security.model.jpa;


import jakarta.persistence.EntityManager;
import org.acme.security.model.jpa.entity.RealmEntity;
import org.jboss.logging.Logger;

public class JpaRealmProvider {
  protected static final Logger logger = Logger.getLogger(JpaRealmProvider.class);
  protected EntityManager em;

  public RealmModel createRealm(String id, String name) {
    RealmEntity realm = new RealmEntity();
    realm.setName(name);
    realm.setId(id);
    em.persist(realm);
    em.flush();
    final RealmModel adapter = new RealmAdapter( em, realm);

    return adapter;
  }

}
