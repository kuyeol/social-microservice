/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.account.repository;

import org.account.credential.UserCredentialStore;
import org.account.entites.CredentialEntity;
import org.account.entites.UserAttributeEntity;
import org.account.entites.UserEntity;
import org.account.exception.ModelDuplicateException;
import org.account.exception.ModelException;
import org.account.model.CredentialModel;
import org.account.service.RoleModel;
import org.account.service.UserModel;
import org.account.service.UserProvider;
import org.account.util.JpaHashUtils;
import org.account.util.ModelUtil;


import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static org.account.util.JpaHashUtils.predicateForFilteringUsersByAttributes;
import static org.account.util.PaginationUtils.paginateQuery;
import static org.account.util.StreamsUtil.closing;


/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@SuppressWarnings("JpaQueryApiInspection")
public class JpaUserProvider implements UserProvider, UserCredentialStore {

    private static final String EMAIL = "email";
    private static final String EMAIL_VERIFIED = "emailVerified";
    private static final String USERNAME = "username";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final char ESCAPE_BACKSLASH = '\\';

    protected EntityManager em;
    private final JpaUserCredentialStore credentialStore;

    public JpaUserProvider( EntityManager em) {
        this.em = em;
        credentialStore = new JpaUserCredentialStore( em);
    }

    @Override
    public UserModel addUser(String id, String username, boolean addDefaultRoles, boolean addDefaultRequiredActions) {
        if (id == null) {
            id = ModelUtil.generateId();
        }

        UserEntity entity = new UserEntity();
        entity.setId(id);
        entity.setCreatedTimestamp(System.currentTimeMillis());
        entity.setUsername(username.toLowerCase());

        em.persist(entity);
        em.flush();
        UserAdapter userModel = new UserAdapter( em, entity);

        if (addDefaultRoles) {
           //todo: 로직 구현필요
        }

        if (addDefaultRequiredActions) {
          //todo: 로직 구현필요
        }

        return userModel;
    }

  @Override
  public void removeImportedUsers(String storageProviderId) {

  }

  @Override
  public void unlinkUsers(String storageProviderId) {

  }

  @Override
    public UserModel addUser( String username) {
        return addUser( ModelUtil.generateId(), username.toLowerCase(), true, true);
    }

    @Override
    public boolean removeUser( UserModel user) {
        UserEntity userEntity = em.find(UserEntity.class, user.getId(), LockModeType.PESSIMISTIC_WRITE);
        if (userEntity == null) return false;
        removeUser(userEntity);
        return true;
    }

    private void removeUser(UserEntity user) {
        String id = user.getId();
        em.createNamedQuery("deleteUserRoleMappingsByUser").setParameter("user", user).executeUpdate();
        em.createNamedQuery("deleteUserGroupMembershipsByUser").setParameter("user", user).executeUpdate();
        em.createNamedQuery("deleteUserConsentClientScopesByUser").setParameter("user", user).executeUpdate();
        em.createNamedQuery("deleteUserConsentsByUser").setParameter("user", user).executeUpdate();

        em.remove(user);
        em.flush();
    }






    public void setNotBeforeForUser(UserModel user, int notBefore) {
        UserEntity entity = em.getReference(UserEntity.class, user.getId());
        if (entity == null) {
            throw new ModelException("User does not exists");
        }
        entity.setNotBefore(notBefore);
    }

    public int getNotBeforeOfUser( UserModel user) {
        UserEntity entity = em.getReference(UserEntity.class, user.getId());
        if (entity == null) {
            throw new ModelException("User does not exists");
        }
        return entity.getNotBefore();
    }

//    @Override
//    public void grantToAllUsers( RoleModel role) {
//        if (realm.equals(role.isClientRole() ? ((ClientModel)role.getContainer()).getRealm() : (RealmModel)role.getContainer())) {
//            em.createNamedQuery("grantRoleToAllUsers")
//                .setParameter("realmId", realm.getId())
//                .setParameter("roleId", role.getId())
//                .executeUpdate();
//        }
//    }




//    @Override
//    public void preRemove(RealmModel realm) {
//        em.createNamedQuery("deleteUserConsentClientScopesByRealm")
//                .setParameter("realmId", realm.getId()).executeUpdate();
//        em.createNamedQuery("deleteUserConsentsByRealm")
//                .setParameter("realmId", realm.getId()).executeUpdate();
//        em.createNamedQuery("deleteUserRoleMappingsByRealm")
//                .setParameter("realmId", realm.getId()).executeUpdate();
//        em.createNamedQuery("deleteUserRequiredActionsByRealm")
//                .setParameter("realmId", realm.getId()).executeUpdate();
//        em.createNamedQuery("deleteFederatedIdentityByRealm")
//                .setParameter("realmId", realm.getId()).executeUpdate();
//        em.createNamedQuery("deleteCredentialsByRealm")
//                .setParameter("realmId", realm.getId()).executeUpdate();
//        em.createNamedQuery("deleteUserAttributesByRealm")
//                .setParameter("realmId", realm.getId()).executeUpdate();
//        em.createNamedQuery("deleteUserGroupMembershipByRealm")
//                .setParameter("realmId", realm.getId()).executeUpdate();
//        em.createNamedQuery("deleteUsersByRealm")
//                .setParameter("realmId", realm.getId()).executeUpdate();
//    }

//    @Override
//    public void removeImportedUsers(RealmModel realm, String storageProviderId) {
//        em.createNamedQuery("deleteUserRoleMappingsByRealmAndLink")
//                .setParameter("realmId", realm.getId())
//                .setParameter("link", storageProviderId)
//                .executeUpdate();
//        em.createNamedQuery("deleteUserRequiredActionsByRealmAndLink")
//                .setParameter("realmId", realm.getId())
//                .setParameter("link", storageProviderId)
//                .executeUpdate();
//        em.createNamedQuery("deleteFederatedIdentityByRealmAndLink")
//                .setParameter("realmId", realm.getId())
//                .setParameter("link", storageProviderId)
//                .executeUpdate();
//        em.createNamedQuery("deleteCredentialsByRealmAndLink")
//                .setParameter("realmId", realm.getId())
//                .setParameter("link", storageProviderId)
//                .executeUpdate();
//        em.createNamedQuery("deleteUserAttributesByRealmAndLink")
//                .setParameter("realmId", realm.getId())
//                .setParameter("link", storageProviderId)
//                .executeUpdate();
//        em.createNamedQuery("deleteUserGroupMembershipsByRealmAndLink")
//                .setParameter("realmId", realm.getId())
//                .setParameter("link", storageProviderId)
//                .executeUpdate();
//        em.createNamedQuery("deleteUserConsentClientScopesByRealmAndLink")
//                .setParameter("realmId", realm.getId())
//                .setParameter("link", storageProviderId)
//                .executeUpdate();
//        em.createNamedQuery("deleteUserConsentsByRealmAndLink")
//                .setParameter("realmId", realm.getId())
//                .setParameter("link", storageProviderId)
//                .executeUpdate();
//        em.createNamedQuery("deleteUsersByRealmAndLink")
//                .setParameter("realmId", realm.getId())
//                .setParameter("link", storageProviderId)
//                .executeUpdate();
//    }



  //todo: 유저 조회 로직

    @Override
    public UserModel getUserById( String id) {
        UserEntity userEntity = em.find(UserEntity.class, id);
        if (userEntity == null ) return null;
        return new UserAdapter( em, userEntity);
    }

    @Override
    public UserModel getUserByUsername(String username) {
        TypedQuery<UserEntity> query = em.createNamedQuery("getRealmUserByUsername", UserEntity.class);
        query.setParameter("username", username.toLowerCase());
        List<UserEntity> results = query.getResultList();
        if (results.isEmpty()) return null;
        return new UserAdapter( em, results.get(0));
    }

    @Override
    public UserModel getUserByEmail(String email) {
        TypedQuery<UserEntity> query = em.createNamedQuery("getRealmUserByEmail", UserEntity.class);
        query.setParameter("email", email.toLowerCase());
        List<UserEntity> results = query.getResultList();

        if (results.isEmpty()) return null;

        ensureEmailConstraint(results);

        return new UserAdapter(em, results.get(0));
    }

     @Override
    public void close() {
    }



//    @Override
//    public UserModel getServiceAccount(ClientModel client) {
//        TypedQuery<UserEntity> query = em.createNamedQuery("getRealmUserByServiceAccount", UserEntity.class);
//        query.setParameter("realmId", client.getRealm().getId());
//        query.setParameter("clientInternalId", client.getId());
//        List<UserEntity> results = query.getResultList();
//        if (results.isEmpty()) {
//            return null;
//        } else if (results.size() > 1) {
//            throw new IllegalStateException("More service account linked users found for client=" + client.getClientId() +
//                    ", results=" + results);
//        } else {
//            UserEntity user = results.get(0);
//            return new UserAdapter(session, client.getRealm(), em, user);
//        }
//    }

    public int getUsersCount( boolean includeServiceAccount) {
        String namedQuery = "getRealmUserCountExcludeServiceAccount";

        if (includeServiceAccount) {
            namedQuery = "getRealmUserCount";
        }

        Object count = em.createNamedQuery(namedQuery)
                .getSingleResult();
        return ((Number)count).intValue();
    }

    public int getUsersCount( Set<String> groupIds) {
        if (groupIds == null || groupIds.isEmpty()) {
            return 0;
        }

        TypedQuery<Long> query = em.createNamedQuery("userCountInGroups", Long.class);
        query.setParameter("groupIds", groupIds);
        Long count = query.getSingleResult();

        return count.intValue();
    }

    public int getUsersCount(String search) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> queryBuilder = builder.createQuery(Long.class);
        Root<UserEntity> root = queryBuilder.from(UserEntity.class);

        queryBuilder.select(builder.count(root));

        List<Predicate> predicates = new ArrayList<>();


        for (String stringToSearch : search.trim().split("\\s+")) {
            predicates.add(builder.or(getSearchOptionPredicateArray(stringToSearch, builder, root)));
        }

        queryBuilder.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(queryBuilder).getSingleResult().intValue();
    }

    public int getUsersCount(String search, Set<String> groupIds) {
        if (groupIds == null || groupIds.isEmpty()) {
            return 0;
        }
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> queryBuilder = builder.createQuery(Long.class);


        List<Predicate> predicates = new ArrayList<>();



        queryBuilder.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(queryBuilder).getSingleResult().intValue();
    }

    public int getUsersCount(Map<String, String> params) {
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> userQuery = qb.createQuery(Long.class);
        Root<UserEntity> from = userQuery.from(UserEntity.class);
        Expression<Long> count = qb.count(from);

        userQuery = userQuery.select(count);
        List<Predicate> restrictions = predicates(params, from, Map.of());

        userQuery = userQuery.where(restrictions.toArray(new Predicate[0]));
        TypedQuery<Long> query = em.createQuery(userQuery);
        Long result = query.getSingleResult();

        return result.intValue();
    }

    @SuppressWarnings("unchecked")
    public int getUsersCount( Map<String, String> params, Set<String> groupIds) {
        if (groupIds == null || groupIds.isEmpty()) {
            return 0;
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<UserEntity> root = countQuery.from(UserEntity.class);
        countQuery.select(cb.count(root));

        List<Predicate> restrictions = predicates(params, root, Map.of());


        countQuery.where(restrictions.toArray(new Predicate[0]));
        TypedQuery<Long> query = em.createQuery(countQuery);
        Long result = query.getSingleResult();

        return result.intValue();
    }





  @Override
  public Stream<UserModel> getRoleMembersStream( RoleModel role) {
    TypedQuery<UserEntity> query = em.createNamedQuery("usersInRole", UserEntity.class);
    query.setParameter("roleId", role.getId());
    return closing(query.getResultStream().map(entity -> new UserAdapter( em, entity)));
  }



    public Stream<UserModel> searchForUserStream( String search, Integer firstResult, Integer maxResults) {
        Map<String, String> attributes = new HashMap<>(2);
        attributes.put(UserModel.SEARCH, search);
        attributes.put(UserModel.INCLUDE_SERVICE_ACCOUNT, Boolean.FALSE.toString());

        return searchForUserStream( attributes, firstResult, maxResults);
    }




    @Override
    public void updateCredential( UserModel user, CredentialModel cred) {
        credentialStore.updateCredential(user, cred);
    }

    @Override
    public CredentialModel createCredential(UserModel user, CredentialModel cred) {
        CredentialEntity entity = credentialStore.createCredentialEntity( user, cred);

        UserEntity userEntity = userInEntityManagerContext(user.getId());
        if (userEntity != null) {
            userEntity.getCredentials().add(entity);
        }
        return toModel(entity);
    }

    @Override
    public boolean removeStoredCredential(UserModel user, String id) {
        CredentialEntity entity = credentialStore.removeCredentialEntity( user, id);
        UserEntity userEntity = userInEntityManagerContext(user.getId());
        if (entity != null && userEntity != null) {
            userEntity.getCredentials().remove(entity);
        }
        return entity != null;
    }

    @Override
    public CredentialModel getStoredCredentialById(UserModel user, String id) {
        return credentialStore.getStoredCredentialById(user, id);
    }

    protected CredentialModel toModel(CredentialEntity entity) {
        return credentialStore.toModel(entity);
    }

    @Override
    public Stream<CredentialModel> getStoredCredentialsStream( UserModel user) {
        return credentialStore.getStoredCredentialsStream( user);
    }

    @Override
    public Stream<CredentialModel> getStoredCredentialsByTypeStream( UserModel user, String type) {
        UserEntity userEntity = userInEntityManagerContext(user.getId());
        if (userEntity != null) {
            // user already in persistence context, no need to execute a query
            return userEntity.getCredentials().stream().filter(it -> type.equals(it.getType()))
                    .sorted(Comparator.comparingInt(CredentialEntity::getPriority))
                    .map(this::toModel);
        } else {
           return credentialStore.getStoredCredentialsByTypeStream( user, type);
        }
    }

    @Override
    public CredentialModel getStoredCredentialByNameAndType( UserModel user, String name, String type) {
        return credentialStore.getStoredCredentialByNameAndType(user, name, type);
    }

    @Override
    public boolean moveCredentialTo( UserModel user, String id, String newPreviousCredentialId) {
        return credentialStore.moveCredentialTo( user, id, newPreviousCredentialId);
    }

    // Could override this to provide a custom behavior.
    protected void ensureEmailConstraint(List<UserEntity> users) {
        UserEntity user = users.get(0);

        if (users.size() > 1) {
            // Realm settings have been changed from allowing duplicate emails to not allowing them
            // but duplicates haven't been removed.
            throw new ModelDuplicateException("Multiple users with email '" + user.getEmail() + "' exist in Keycloak.");
        }


        if (user.getEmail() != null && !user.getEmail().equals(user.getEmailConstraint())) {
            // Realm settings have been changed from allowing duplicate emails to not allowing them.
            // We need to update the email constraint to reflect this change in the user entities.
            user.setEmailConstraint(user.getEmail());
            em.persist(user);
        }
    }

    private Predicate[] getSearchOptionPredicateArray(String value, CriteriaBuilder builder, From<?, UserEntity> from) {
        value = value.toLowerCase();

        List<Predicate> orPredicates = new ArrayList<>();

        if (value.length() >= 2 && value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') {
            // exact search
            value = value.substring(1, value.length() - 1);

            orPredicates.add(builder.equal(from.get(USERNAME), value));
            orPredicates.add(builder.equal(from.get(EMAIL), value));
            orPredicates.add(builder.equal(builder.lower(from.get(FIRST_NAME)), value));
            orPredicates.add(builder.equal(builder.lower(from.get(LAST_NAME)), value));
        } else {
            value = value.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
            value = value.replace("*", "%");
            if (value.isEmpty() || value.charAt(value.length() - 1) != '%') value += "%";

            orPredicates.add(builder.like(from.get(USERNAME), value, ESCAPE_BACKSLASH));
            orPredicates.add(builder.like(from.get(EMAIL), value, ESCAPE_BACKSLASH));
            orPredicates.add(builder.like(builder.lower(from.get(FIRST_NAME)), value, ESCAPE_BACKSLASH));
            orPredicates.add(builder.like(builder.lower(from.get(LAST_NAME)), value, ESCAPE_BACKSLASH));
        }

        return orPredicates.toArray(new Predicate[0]);
    }

    private UserEntity userInEntityManagerContext(String id) {
        UserEntity user = em.getReference(UserEntity.class, id);
        return em.contains(user) ? user : null;
    }

    private List<Predicate> predicates(Map<String, String> attributes, Root<UserEntity> root, Map<String, String> customLongValueSearchAttributes) {
        CriteriaBuilder builder = em.getCriteriaBuilder();

        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> attributePredicates = new ArrayList<>();

        Join<Object, Object> federatedIdentitiesJoin = null;

        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value == null) {
                continue;
            }

            switch (key) {
                case UserModel.SEARCH:
                    for (String stringToSearch : value.trim().split("\\s+")) {
                        predicates.add(builder.or(getSearchOptionPredicateArray(stringToSearch, builder, root)));
                    }
                    break;
                case FIRST_NAME:
                case LAST_NAME:
                    if (Boolean.parseBoolean(attributes.get(UserModel.EXACT))) {
                        predicates.add(builder.equal(builder.lower(root.get(key)), value.toLowerCase()));
                    } else {
                        predicates.add(builder.like(builder.lower(root.get(key)), "%" + value.toLowerCase() + "%"));
                    }
                    break;
                case USERNAME:
                case EMAIL:
                    if (Boolean.parseBoolean(attributes.get(UserModel.EXACT))) {
                        predicates.add(builder.equal(root.get(key), value.toLowerCase()));
                    } else {
                        predicates.add(builder.like(root.get(key), "%" + value.toLowerCase() + "%"));
                    }
                    break;
                case EMAIL_VERIFIED:
                    predicates.add(builder.equal(root.get(key), Boolean.valueOf(value.toLowerCase())));
                    break;
                case UserModel.ENABLED:
                    predicates.add(builder.equal(root.get(key), Boolean.valueOf(value)));
                    break;
                case UserModel.IDP_ALIAS:
                    if (federatedIdentitiesJoin == null) {
                        federatedIdentitiesJoin = root.join("federatedIdentities");
                    }
                    predicates.add(builder.equal(federatedIdentitiesJoin.get("identityProvider"), value));
                    break;
                case UserModel.IDP_USER_ID:
                    if (federatedIdentitiesJoin == null) {
                        federatedIdentitiesJoin = root.join("federatedIdentities");
                    }
                    predicates.add(builder.equal(federatedIdentitiesJoin.get("userId"), value));
                    break;
                case UserModel.EXACT:
                    break;
                // All unknown attributes will be assumed as custom attributes
                default:
                    Join<UserEntity, UserAttributeEntity> attributesJoin = root.join("attributes", JoinType.LEFT);
                    if (value.length() > 255) {
                        customLongValueSearchAttributes.put(key, value);
                        attributePredicates.add(builder.and(
                                builder.equal(attributesJoin.get("name"), key),
                                builder.equal(attributesJoin.get("longValueHashLowerCase"), JpaHashUtils.hashForAttributeValueLowerCase(value))));
                    } else {
                        attributePredicates.add(builder.and(
                                builder.equal(attributesJoin.get("name"), key),
                                builder.equal(builder.lower(attributesJoin.get("value")), value.toLowerCase())));
                    }
                    break;
                case UserModel.INCLUDE_SERVICE_ACCOUNT: {
                    if (!attributes.containsKey(UserModel.INCLUDE_SERVICE_ACCOUNT)
                            || !Boolean.parseBoolean(attributes.get(UserModel.INCLUDE_SERVICE_ACCOUNT))) {
                        predicates.add(root.get("serviceAccountClientLink").isNull());
                    }
                    break;
                }
            }
        }

        if (!attributePredicates.isEmpty()) {
            predicates.add(builder.and(attributePredicates.toArray(new Predicate[0])));
        }

        return predicates;
    }


  @Override
  public Stream<UserModel> searchForUserStream(Map<String, String> params, Integer firstResult, Integer maxResults) {
    return Stream.empty();
  }

  @Override
  public Stream<UserModel> searchForUserByUserAttributeStream(String attrName, String attrValue) {
    return Stream.empty();
  }
}
