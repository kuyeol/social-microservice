
package org.account.credential;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import org.account.entites.CredentialEntity;
import org.account.entites.UserEntity;
import org.account.model.CredentialModel;
import org.account.service.UserModel;
import org.account.util.ModelUtil;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.account.util.StreamsUtil.closing;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class JpaUserCredentialStore implements UserCredentialStore {

  // Typical priority difference between 2 credentials
  public static final int PRIORITY_DIFFERENCE = 10;

  protected static final Logger logger = Logger.getLogger(JpaUserCredentialStore.class);

  protected final EntityManager em;

  public JpaUserCredentialStore(EntityManager em) {

    this.em = em;
  }

  @Override
  public void updateCredential(UserModel user, CredentialModel cred) {
    CredentialEntity entity = em.find(CredentialEntity.class, cred.getId());
    if (!checkCredentialEntity(entity, user)) return;
    entity.setCreatedDate(cred.getCreatedDate());
    entity.setUserLabel(cred.getUserLabel());
    entity.setType(cred.getType());
    entity.setSecretData(cred.getSecretData());
    entity.setCredentialData(cred.getCredentialData());
  }

  @Override
  public CredentialModel createCredential(UserModel user, CredentialModel cred) {
    CredentialEntity entity = createCredentialEntity(user, cred);
    return toModel(entity);
  }

  @Override
  public boolean removeStoredCredential(UserModel user, String id) {
    CredentialEntity entity = removeCredentialEntity(user, id);
    return entity != null;
  }

  @Override
  public CredentialModel getStoredCredentialById(UserModel user, String id) {
    CredentialEntity entity = em.find(CredentialEntity.class, id);
    if (!checkCredentialEntity(entity, user)) return null;
    CredentialModel model = toModel(entity);
    return model;
  }

  CredentialModel toModel(CredentialEntity entity) {
    CredentialModel model = new CredentialModel();
    model.setId(entity.getId());
    model.setType(entity.getType());
    model.setCreatedDate(entity.getCreatedDate());
    model.setUserLabel(entity.getUserLabel());
    model.setSecretData(entity.getSecretData());
    model.setCredentialData(entity.getCredentialData());
    return model;
  }

  @Override
  public Stream<CredentialModel> getStoredCredentialsStream(UserModel user) {
    return this.getStoredCredentialEntities(user).map(this::toModel);
  }

  private Stream<CredentialEntity> getStoredCredentialEntities(UserModel user) {
    UserEntity userEntity = em.getReference(UserEntity.class, user.getId());
    TypedQuery<CredentialEntity> query = em.createNamedQuery("credentialByUser", CredentialEntity.class)
      .setParameter("customer", userEntity);
    return closing(query.getResultStream());
  }

  @Override
  public Stream<CredentialModel> getStoredCredentialsByTypeStream(UserModel user, String type) {
    return getStoredCredentialsStream(user).filter(credential -> Objects.equals(type, credential.getType()));
  }

  @Override
  public CredentialModel getStoredCredentialByNameAndType(UserModel user, String name, String type) {
    return getStoredCredentialsStream(user).filter(credential ->
        Objects.equals(type, credential.getType()) && Objects.equals(name, credential.getUserLabel()))
      .findFirst().orElse(null);
  }

  @Override
  public void close() {

  }

  CredentialEntity createCredentialEntity(UserModel user, CredentialModel cred) {
    CredentialEntity entity = new CredentialEntity();
    String id = cred.getId() == null ? ModelUtil.generateId() : cred.getId();
    entity.setId(id);
    entity.setCreatedDate(cred.getCreatedDate());
    entity.setUserLabel(cred.getUserLabel());
    entity.setType(cred.getType());
    entity.setSecretData(cred.getSecretData());
    entity.setCredentialData(cred.getCredentialData());
    UserEntity userRef = em.getReference(UserEntity.class, user.getId());
    entity.setUser(userRef);

    //add in linkedlist to last position
    List<CredentialEntity> credentials = getStoredCredentialEntities(user).collect(Collectors.toList());
    int priority = credentials.isEmpty() ? PRIORITY_DIFFERENCE : credentials.get(credentials.size() - 1).getPriority() + PRIORITY_DIFFERENCE;
    entity.setPriority(priority);

    em.persist(entity);
    return entity;
  }

  CredentialEntity removeCredentialEntity(UserModel user, String id) {
    CredentialEntity entity = em.find(CredentialEntity.class, id, LockModeType.PESSIMISTIC_WRITE);
    if (!checkCredentialEntity(entity, user)) return null;

    int currentPriority = entity.getPriority();

    this.getStoredCredentialEntities(user).forEach(cred -> {
      if (cred.getPriority() > currentPriority) {
        cred.setPriority(cred.getPriority() - PRIORITY_DIFFERENCE);
      }
    });

    em.remove(entity);
    em.flush();
    return entity;
  }

  ////Operations to handle the linked list of credentials
  @Override
  public boolean moveCredentialTo(UserModel user, String id, String newPreviousCredentialId) {

    // 1 - Create new list and move everything to it.
    List<CredentialEntity> newList = this.getStoredCredentialEntities(user).collect(Collectors.toList());

    // 2 - Find indexes of our and newPrevious credential
    int ourCredentialIndex = -1;
    int newPreviousCredentialIndex = -1;
    CredentialEntity ourCredential = null;
    int i = 0;
    for (CredentialEntity credential : newList) {
      if (id.equals(credential.getId())) {
        ourCredentialIndex = i;
        ourCredential = credential;
      } else if (newPreviousCredentialId != null && newPreviousCredentialId.equals(credential.getId())) {
        newPreviousCredentialIndex = i;
      }
      i++;
    }

    if (ourCredentialIndex == -1) {
      logger.warnf("Not found credential with id [%s] of customer [%s]", id, user.getUsername());
      return false;
    }

    if (newPreviousCredentialId != null && newPreviousCredentialIndex == -1) {
      logger.warnf("Can't move up credential with id [%s] of customer [%s]", id, user.getUsername());
      return false;
    }

    // 3 - Compute index where we move our credential
    int toMoveIndex = newPreviousCredentialId == null ? 0 : newPreviousCredentialIndex + 1;

    // 4 - Insert our credential to new position, remove it from the old position
    newList.add(toMoveIndex, ourCredential);
    int indexToRemove = toMoveIndex < ourCredentialIndex ? ourCredentialIndex + 1 : ourCredentialIndex;
    newList.remove(indexToRemove);

    // 5 - newList contains credentials in requested order now. Iterate through whole list and change priorities accordingly.
    int expectedPriority = 0;
    for (CredentialEntity credential : newList) {
      expectedPriority += PRIORITY_DIFFERENCE;
      if (credential.getPriority() != expectedPriority) {
        credential.setPriority(expectedPriority);

        logger.tracef("Priority of credential [%s] of customer [%s] changed to [%d]", credential.getId(), user.getUsername(), expectedPriority);
      }
    }
    return true;
  }

  private boolean checkCredentialEntity(CredentialEntity entity, UserModel user) {
    return entity != null && entity.getUser() != null && entity.getUser().getId().equals(user.getId());
  }

}
