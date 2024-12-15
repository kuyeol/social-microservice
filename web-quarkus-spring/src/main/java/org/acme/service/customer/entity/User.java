package org.acme.service.customer.entity;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import org.acme.utils.ModelUtils;


@UserDefinition
@Entity
@Table(name = "users")
@NamedQuery(name = "findByName", query = "select u from User u where u.username  = :name")
@NamedQuery(name = "findByPassword", query = "select u from User u where u.password  = :pw")
public class User {

    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    private String id;

    @Roles
    private final String role = "user";


    @Username
    private String username;


    @Password
    private String password;

    private String firstName;
    private String lastName;
    private String address;



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    // Default constructor for JPA deserialization
    public User() {
        this.id = ModelUtils.generateId();
    }


    public User(String firstName, String lastName, String address) {
        this.id = ModelUtils.generateId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;

    }

    public String getName() {


        return firstName + lastName;
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
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User that = (User) o;

        if (!id.equals(that.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            ", role='" + role + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", address='" + address + '\'' +
            '}';
    }
}
