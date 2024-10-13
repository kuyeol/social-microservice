package org.acme.security.model.jpa;

import jakarta.persistence.EntityManager;
import org.acme.security.model.jpa.entity.RealmEntity;
import org.jboss.logging.Logger;

public class RealmAdapter  implements StorageProviderRealmModel,JpaModel<RealmEntity>  {

  StorageProviderRealmModel fff;

  protected static final Logger logger = Logger.getLogger(RealmAdapter.class);
  protected RealmEntity realm;
  protected EntityManager em;

  public RealmAdapter( EntityManager em, RealmEntity realm) {
    this.em = em;
    this.realm = realm;
  }
  public RealmEntity getEntity() {
    return realm;
  }

}
