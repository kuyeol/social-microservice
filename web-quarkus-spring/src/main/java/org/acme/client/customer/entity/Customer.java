package org.acme.client.customer.entity;


import io.quarkus.security.jpa.Roles;


import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.LinkedList;
import org.acme.core.utils.ModelUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name = "CUSTOMER")
@NamedQuery(name = "findByName",
    query = "select u from Customer u where u.customerName  = :name")
public class Customer {


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
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy= "customer")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 20)
    protected Collection<Credential> credentials = new LinkedList<>();
    public Customer() {
       this.id = ModelUtils.generateId();
        this.createdTimestamp = System.currentTimeMillis();
    }

    public Long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Long timestamp) {
        createdTimestamp = timestamp;
    }



    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Customer)) return false;

        Customer that = (Customer) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


}
