package org.acme.service.customer.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link User}
 */
public class UserDto implements Serializable {
    private final String username;
    private final String email;
    private final String address;
    private final Long createdTimestamp;

    public UserDto(String username, String email, String address, Long createdTimestamp) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.createdTimestamp = createdTimestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Long getCreatedTimestamp() {
        return createdTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDto entity = (UserDto) o;
        return Objects.equals(this.username, entity.username) &&
            Objects.equals(this.email, entity.email) &&
            Objects.equals(this.address, entity.address) &&
            Objects.equals(this.createdTimestamp, entity.createdTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, address, createdTimestamp);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
            "username = " + username + ", " +
            "email = " + email + ", " +
            "address = " + address + ", " +
            "createdTimestamp = " + createdTimestamp + ")";
    }
}