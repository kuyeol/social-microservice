package org.acme.service.customer.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.NamedQuery;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.acme.utils.ModelUtils;

@Entity
@Table(name = "users")
@NamedQuery(name = "findByName",
    query = "select u from User u where u.firstName  = :name")
public class User {

    @Id
    @Column(name="ID", length = 36)
    @Access(AccessType.PROPERTY)
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }





    private String firstName;
    private String lastName;
    private String address;

    //@OneToMany(cascade = CascadeType.ALL)
    //private List<Book> books;

    // Default constructor for JPA deserialization
    public User() {
    }



    public User(String firstName, String lastName, String address) {
        this.id = ModelUtils.generateId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        if (!(o instanceof User)) return false;

        User that = (User) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
