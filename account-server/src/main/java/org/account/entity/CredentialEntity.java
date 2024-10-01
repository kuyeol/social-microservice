package org.account.entity;


import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({@NamedQuery(name = "credentialByUser",
  query = "select cred from CredentialEntity cred where cred.user = :user order by cred.priority"),

//  @NamedQuery(name = "deleteCredentialsByRealm",
//  query = "delete from CredentialEntity cred where cred.user IN (select u from UserEntity u where u.realmId=:realmId)"),
})

@Table(name = "CREDENTIAL")
@Entity
public class CredentialEntity {

  @Id
  @Access(AccessType.PROPERTY)
  @Column(name = "ID", length = 36)
  protected String id;

  @Column(name = "TYPE")
  protected String type;


  @Column(name = "CREATED_DATE")
  protected Long createdDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  protected UserEntity user;

  @Column(name = "SECRET_DATA")
  protected String secretData;

  @Column(name = "CREDENTIAL_DATA")
  protected String credentialData;

  @Column(name = "PRIORITY")
  protected int priority;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }


  public Long getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Long createdDate) {
    this.createdDate = createdDate;
  }

  public String getSecretData() {
    return secretData;
  }

  public void setSecretData(String secretData) {
    this.secretData = secretData;
  }

  public String getCredentialData() {
    return credentialData;
  }

  public void setCredentialData(String credentialData) {
    this.credentialData = credentialData;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (!(o instanceof CredentialEntity)) return false;

    CredentialEntity that = (CredentialEntity) o;

    if (!id.equals(that.getId())) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }


}
