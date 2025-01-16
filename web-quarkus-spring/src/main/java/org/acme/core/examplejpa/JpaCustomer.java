package org.acme.core.examplejpa;

import io.quarkus.security.jpa.Roles;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import java.util.Collection;
import java.util.LinkedList;
import org.acme.client.customer.entity.Credential;
import org.acme.core.model.Typer;
import org.acme.core.model.impl.EntityTyper;
import org.acme.core.utils.ModelUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQuery(name = "findByCustmer", query = "select u from JpaCustomer u where u.customerName  = :name")
public class JpaCustomer extends EntityTyper
{

    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    private String id;

    @Roles
    private final String role = "user";

    private String customerName;

    private String email;

    private String address;

    private Long createdTimestamp;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "customer")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 20)
    protected Collection<Credential> credentials = new LinkedList<>();


    public JpaCustomer()
    {

        this.id               = ModelUtils.generateId();
        this.createdTimestamp = System.currentTimeMillis();
    }


    public String getCustomerName()
    {

        return customerName;
    }


    public Long getCreatedTimestamp()
    {

        return createdTimestamp;
    }


    public void setCreatedTimestamp(Long timestamp)
    {

        createdTimestamp = timestamp;
    }


    public void setCustomerName(String customerName)
    {

        this.customerName = customerName;
    }


    @Override
    public String getId()
    {

        return id;
    }


    public void setId(String id)
    {

        this.id = id;
    }


    public String getEmail()
    {

        return email;
    }


    public void setEmail(String email)
    {

        this.email = email;
    }


    public String getAddress()
    {

        return address;
    }


    public void setAddress(String address)
    {

        this.address = address;
    }


    public Collection<Credential> getCredentials()
    {

        if (credentials == null) {
            credentials = new LinkedList<>();
        }
        return credentials;
    }


    public void setCredentials(Collection<Credential> credentials)
    {

        this.credentials = credentials;
    }


    @Override
    public Typer exists()
    {

        return null;
    }


    @Override
    public boolean equals(Object o)
    {

        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof JpaCustomer)) {
            return false;
        }

        JpaCustomer that = (JpaCustomer) o;

        if (!id.equals(that.id)) {
            return false;
        }

        return true;
    }



}
