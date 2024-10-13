package org.acme.security.model.jpa.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKey;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Table(name = "REALM")
@Entity
@NamedQueries({@NamedQuery(name = "getAllRealmIds", query = "select realm.id from RealmEntity realm"),
    @NamedQuery(name = "getRealmIdsWithNameContaining", query = "select realm.id from RealmEntity realm where LOWER(realm.name) like CONCAT('%', LOWER(:search), '%')"),
    @NamedQuery(name = "getRealmIdByName", query = "select realm.id from RealmEntity realm where realm.name = :name"),
    @NamedQuery(name = "getRealmIdsWithProviderType", query = "select distinct c.realm.id from ComponentEntity c where c.providerType = :providerType"),})
public class RealmEntity {



    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    protected String id;

    @Column(name = "NAME", unique = true)
    protected String name;

    @Column(name = "ENABLED")
    protected boolean enabled;

    @Column(name = "TOKEN_LIFESPAN")
    protected int tokenLifespan;

    @Column(name = "ADMIN_CLIENT")
    protected String adminClient;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "realm")
    Set<ComponentEntity> components = new HashSet<>();

    public int getTokenLifespan() {
        return tokenLifespan;
    }

    public void setTokenLifespan(int tokenLifespan) {
        this.tokenLifespan = tokenLifespan;
    }

    public String getAdminClient() {
        return adminClient;
    }

    public void setAdminClient(String adminClient) {
        this.adminClient = adminClient;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<ComponentEntity> getComponents() {
        if (components == null) {
            components = new HashSet<>();
        }
        return components;
    }

    public void setComponents(Set<ComponentEntity> components) {
        this.components = components;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof RealmEntity)) {
            return false;
        }

        RealmEntity that = (RealmEntity) o;

        if (!id.equals(that.getId())) {
            return false;
        }

        return true;
    }

    public String toString() {
        return "Realm{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
