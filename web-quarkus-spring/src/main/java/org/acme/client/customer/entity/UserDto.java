package org.acme.client.customer.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Customer}
 */
public class UserDto implements Serializable {
    private final String customerName;
    private final String email;
    private final String address;
    private final Long createdTimestamp;

    public UserDto(String customerName, String email, String address, Long createdTimestamp) {
        this.customerName = customerName;
        this.email = email;
        this.address = address;
        this.createdTimestamp = createdTimestamp;
    }

    public String getCustomerName() {
        return customerName;
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
        return Objects.equals(this.customerName, entity.customerName) &&
            Objects.equals(this.email, entity.email) &&
            Objects.equals(this.address, entity.address) &&
            Objects.equals(this.createdTimestamp, entity.createdTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerName, email, address, createdTimestamp);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
            "customerName = " + customerName + ", " +
            "email = " + email + ", " +
            "address = " + address + ", " +
            "createdTimestamp = " + createdTimestamp + ")";
    }
}