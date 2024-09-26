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

package org.acme.account.model;



import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.acme.account.entity.UserAttributeEntity;
import org.acme.account.entity.UserEntity;
import org.acme.account.entity.UserRoleMappingEntity;
import org.acme.account.util.CollectionUtil;
import org.acme.account.util.KeycloakModelUtils;
import org.acme.account.util.MultivaluedHashMap;
import org.acme.account.util.ObjectUtil;


import static org.acme.account.util.StreamsUtil.closing;


/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class UserAdapter implements UserModel, JpaModel< UserEntity > {

    protected UserEntity user;
    protected EntityManager em;
    private final KeycloakSession session;

    public UserAdapter(KeycloakSession session, EntityManager em, UserEntity user) {
        this.em = em;
        this.user = user;

        this.session = session;
    }

    @Override
    public UserEntity getEntity() {
        return user;
    }

    @Override
    public String getId() {
        return user.getId();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public void setUsername(String username) {
        username = KeycloakModelUtils.toLowerCaseSafe( username);
        user.setUsername(username);
    }

    @Override
    public Long getCreatedTimestamp() {
        return user.getCreatedTimestamp();
    }

    @Override
    public void setCreatedTimestamp(Long timestamp) {
        user.setCreatedTimestamp(timestamp);
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        user.setEnabled(enabled);
    }


    /**
     * Set single value of specified attribute. Remove all other existing values of this attribute
     *
     * @param name
     * @param value
     */
    @Override
    public void setSingleAttribute( String name, String value )
    {

    }


    @Override
    public void setAttribute( String name, List< String > values )
    {

    }


    private void persistAttributeValue(String name, String value) {
        UserAttributeEntity attr = new UserAttributeEntity();
        attr.setId(KeycloakModelUtils.generateId());
        attr.setName(name);
        attr.setValue(value);
        attr.setUser(user);
        em.persist(attr);
        user.getAttributes().add(attr);
    }

    @Override
    public void removeAttribute(String name) {
        List<UserAttributeEntity> customAttributesToRemove = new ArrayList<>();
        for (UserAttributeEntity attr : user.getAttributes()) {
            if (attr.getName().equals(name)) {
                customAttributesToRemove.add(attr);
            }
        }

        if (customAttributesToRemove.isEmpty()) {
            // make sure root user attributes are set to null
            if (UserModel.FIRST_NAME.equals(name)) {
                setFirstName(null);
            } else if (UserModel.LAST_NAME.equals(name)) {
                setLastName(null);
            } else if (UserModel.EMAIL.equals(name)) {
                setEmail(null);
            }
            return;
        }

        // KEYCLOAK-3296 : Remove attribute through HQL to avoid StaleUpdateException
        Query query = em.createNamedQuery("deleteUserAttributesByNameAndUser");
        query.setParameter("name", name);
        query.setParameter("userId", user.getId());
        query.executeUpdate();
        // KEYCLOAK-3494 : Also remove attributes from local user entity
        user.getAttributes().removeAll(customAttributesToRemove);
    }

    @Override
    public String getFirstAttribute(String name) {
        if (UserModel.FIRST_NAME.equals(name)) {
            return user.getFirstName();
        } else if (UserModel.LAST_NAME.equals(name)) {
            return user.getLastName();
        } else if (UserModel.EMAIL.equals(name)) {
            return user.getEmail();
        } else if (UserModel.USERNAME.equals(name)) {
            return user.getUsername();
        }
        for (UserAttributeEntity attr : user.getAttributes()) {
            if (attr.getName().equals(name)) {
                return attr.getValue();
            }
        }
        return null;
    }

    @Override
    public Stream<String> getAttributeStream(String name) {
        if (UserModel.FIRST_NAME.equals(name)) {
            return Stream.of(user.getFirstName());
        } else if (UserModel.LAST_NAME.equals(name)) {
            return Stream.of(user.getLastName());
        } else if (UserModel.EMAIL.equals(name)) {
            return Stream.of(user.getEmail());
        } else if (UserModel.USERNAME.equals(name)) {
            return Stream.of(user.getUsername());
        }
        return user.getAttributes().stream().filter(attribute -> Objects.equals(attribute.getName(), name)).
                map(attribute -> attribute.getValue());
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        MultivaluedHashMap<String, String> result = new MultivaluedHashMap<>();
        for (UserAttributeEntity attr : user.getAttributes()) {
            result.add(attr.getName(), attr.getValue());
        }
        result.add(UserModel.FIRST_NAME, user.getFirstName());
        result.add(UserModel.LAST_NAME, user.getLastName());
        result.add(UserModel.EMAIL, user.getEmail());
        result.add(UserModel.USERNAME, user.getUsername());
        return result;
    }


    /**
     * Obtains the aliases of required actions associated with the user.
     *
     * @return a non-null {@link Stream} of required action aliases.
     */
    @Override
    public Stream< String > getRequiredActionsStream()
    {

        return Stream.empty();
    }


    @Override
    public void addRequiredAction( String action )
    {

    }


    @Override
    public void removeRequiredAction( String action )
    {

    }


    @Override
    public String getFirstName() {
        return user.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        user.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return user.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        user.setLastName(lastName);
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public void setEmail(String email) {
        if ( ObjectUtil.isBlank( email)) {
            email = null;
        }
        email = KeycloakModelUtils.toLowerCaseSafe(email);
        user.setEmail(email, true);
    }

    @Override
    public boolean isEmailVerified() {
        return user.isEmailVerified();
    }

    @Override
    public void setEmailVerified(boolean verified) {
        user.setEmailVerified(verified);
    }


    @Override
    public String getFederationLink()
    {

        return "";
    }


    @Override
    public void setFederationLink( String link )
    {

    }


    @Override
    public String getServiceAccountClientLink()
    {

        return "";
    }


    @Override
    public void setServiceAccountClientLink( String clientInternalId )
    {

    }


    @Override
    public SubjectCredentialManager credentialManager()
    {

        return null;
    }


    private TypedQuery<String> createGetGroupsQuery() {
        // we query ids only as the group  might be cached and following the @ManyToOne will result in a load
        // even if we're getting just the id.
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<String> queryBuilder = builder.createQuery(String.class);


        List<Predicate> predicates = new ArrayList<>();

        queryBuilder.where(predicates.toArray(Predicate[]::new));

        return em.createQuery(queryBuilder);
    }

    private TypedQuery<Long> createCountGroupsQuery() {
        // we query ids only as the group  might be cached and following the @ManyToOne will result in a load
        // even if we're getting just the id.
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> queryBuilder = builder.createQuery(Long.class);

        List<Predicate> predicates = new ArrayList<>();

        queryBuilder.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(queryBuilder);
    }





    protected TypedQuery< UserRoleMappingEntity > getUserRoleMappingEntityTypedQuery(RoleModel role) {
        TypedQuery<UserRoleMappingEntity> query = em.createNamedQuery("userHasRole", UserRoleMappingEntity.class);
        query.setParameter("user", getEntity());
        query.setParameter("roleId", role.getId());
        return query;
    }



    @Override
    public Stream< RoleModel > getRealmRoleMappingsStream()
    {

        return Stream.empty();
    }



    @Override
    public boolean hasRole( RoleModel role )
    {

        return false;
    }


    @Override
    public void grantRole(RoleModel role) {
        if (hasDirectRole(role)) return;
        grantRoleImpl(role);
    }


    /**
     * Returns stream of all role (both realm all client) that are directly set to this object.
     *
     * @return Stream of {@link RoleModel}. Never returns {@code null}.
     */
    @Override
    public Stream< RoleModel > getRoleMappingsStream()
    {

        return Stream.empty();
    }


    public void grantRoleImpl(RoleModel role) {
        UserRoleMappingEntity entity = new UserRoleMappingEntity();
        entity.setUser(getEntity());
        entity.setRoleId(role.getId());
        em.persist(entity);
        em.flush();
        em.detach(entity);
    }





    @Override
    public void deleteRoleMapping(RoleModel role) {
        if (user == null || role == null) return;

        TypedQuery<UserRoleMappingEntity> query = getUserRoleMappingEntityTypedQuery(role);
        query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        List<UserRoleMappingEntity> results = query.getResultList();
        if (results.isEmpty()) return;
        for (UserRoleMappingEntity entity : results) {
            em.remove(entity);
        }
        em.flush();
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof UserModel)) return false;

        UserModel that = (UserModel) o;
        return that.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
