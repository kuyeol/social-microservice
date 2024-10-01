package org.account.entity;


import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Collection;
import java.util.LinkedList;

@Table(name = "USER_ENTITY")
@Entity
public class UserEntity {
  @Id
  @Column(name = "ID", length = 36)
  @Access(AccessType.PROPERTY)
  protected String id;

  @Column(name = "USERNAME")
  protected String username;

  @Column(name = "EMAIL")
  protected String email;

  @Column(name = "CREATED_TS")
  protected Long createdTimestamp;

  @Column(name = "EMAIL_VERIFIED")
  protected boolean emailVerified;

  @Column(name = "ENABLED")
  protected boolean enabled;

  @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "user")
  protected Collection<CredentialEntity> credentials = new LinkedList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Long getCreatedTimestamp() {
    return createdTimestamp;
  }

  public void setCreatedTimestamp(Long timestamp) {
    createdTimestamp = timestamp;
  }



  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }


  public boolean isEmailVerified() {
    return emailVerified;
  }

  public void setEmailVerified(boolean emailVerified) {
    this.emailVerified = emailVerified;
  }






  public Collection<CredentialEntity> getCredentials() {
    if (credentials == null) {
      credentials = new LinkedList<>();
    }
    return credentials;
  }

  public void setCredentials(Collection<CredentialEntity> credentials) {
    this.credentials = credentials;
  }




  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (!(o instanceof UserEntity)) return false;

    UserEntity that = (UserEntity) o;

    if (!id.equals(that.id)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }



}
