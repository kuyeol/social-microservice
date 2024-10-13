package org.acme.security.model.jpa.entity;


import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Nationalized;


/**
 * @NamedQueries({
 * @NamedQuery(name="getClientsByRealm", query="select client from ClientEntity client where client.realmId = :realm"),
 * @NamedQuery(name="getClientById", query="select client from ClientEntity client where client.id = :id and
 * client.realmId = :realm"),
 * @NamedQuery(name="getClientIdsByRealm", query="select client.id from ClientEntity client where client.realmId =
 * :realm order by client.clientId"),
 * @NamedQuery(name="getAlwaysDisplayInConsoleClients", query="select client.id from ClientEntity client where
 * client.alwaysDisplayInConsole = true and client.realmId = :realm order by client.clientId"),
 * @NamedQuery(name="findClientIdByClientId", query="select client.id from ClientEntity client where client.clientId =
 * :clientId and client.realmId = :realm"),
 * @NamedQuery(name="searchClientsByClientId", query="select client.id from ClientEntity client where
 * lower(client.clientId) like lower(concat('%',:clientId,'%')) and client.realmId = :realm order by client.clientId"),
 * @NamedQuery(name="getRealmClientsCount", query="select count(client) from ClientEntity client where client.realmId =
 * :realm"),
 * @NamedQuery(name="findClientByClientId", query="select client from ClientEntity client where client.clientId =
 * :clientId and client.realmId = :realm"),
 * @NamedQuery(name="getAllRedirectUrisOfEnabledClients", query="select new map(client as client, r as redirectUri) from
 * ClientEntity client join client.redirectUris r where client.realmId = :realm and client.enabled = true"), })
 */
@Entity
@Table(name = "CLIENT", uniqueConstraints = {@UniqueConstraint(columnNames = {"REALM_ID", "CLIENT_NAME"})})
public class ClientEntity {

  @Id
  @Column(name = "ID", length = 36)
  @Access(AccessType.PROPERTY)
  protected String id;

  @Nationalized
  @Column(name = "CLIENT_NAME")
  private String clientName;

  @Column(name = "REALM_ID")
  protected String realmId;


  @ElementCollection
  @Column(name = "VALUE")
  @CollectionTable(name = "REDIRECT_URIS", joinColumns = {@JoinColumn(name = "CLIENT_ID")})
  protected Set<String> redirectUris;


  @Column(name = "ROOT_URL")
  private String rootUrl;


  public Set<String> getRedirectUris() {
    if (redirectUris == null) {
      redirectUris = new HashSet<>();
    }
    return redirectUris;
  }

  public void setRedirectUris(Set<String> redirectUris) {
    this.redirectUris = redirectUris;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public String getRealmId() {
    return realmId;
  }

  public void setRealmId(String realmId) {
    this.realmId = realmId;
  }

  public String getRootUrl() {
    return rootUrl;
  }

  public void setRootUrl(String rootUrl) {
    this.rootUrl = rootUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (!(o instanceof ClientEntity)) {
      return false;
    }

    ClientEntity that = (ClientEntity) o;

    if (!id.equals(that.getId())) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
