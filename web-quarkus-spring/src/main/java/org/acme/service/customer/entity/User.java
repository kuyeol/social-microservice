package org.acme.service.customer.entity;


import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;

import io.quarkus.security.jpa.Username;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import org.acme.utils.ModelUtils;



@Entity
@Table(name = "USERS")
@NamedQuery(name = "findByName", query = "select u from User u where u.username  = :username")
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

    private String email;

    private String address;

    private Long createdTimestamp;

    public User() {
       this.id = ModelUtils.generateId();
        this.createdTimestamp = System.currentTimeMillis();
    }

    public Long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Long timestamp) {
        createdTimestamp = timestamp;
    }

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
