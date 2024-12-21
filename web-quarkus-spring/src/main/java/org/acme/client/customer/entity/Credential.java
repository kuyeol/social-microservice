package org.acme.client.customer.entity;

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

@Table(name = "CREDENTIAL")
@Entity
@NamedQueries({

    @NamedQuery(name = "credentialByUser", query = "select cred from Credential cred where cred.customer = :customer " +
        "order by cred.priority"),
    @NamedQuery(name = "deleteCredentialsByRealm", query =
        "delete from Credential cred where cred.customer IN (select u from" + " Customer u where u.id=:Id)")
        })

public class Credential {


    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_ID")
    protected Customer customer;


    @Column(name = "TYPE")
    protected String type;

    @Column(name = "CREATED_DATE")
    protected Long createdDate;

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


    public Customer getUser() {
        return customer;
    }

    public void setUser(Customer customer) {
        this.customer = customer;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Credential)) {
            return false;
        }

        Credential that = (Credential) o;

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
