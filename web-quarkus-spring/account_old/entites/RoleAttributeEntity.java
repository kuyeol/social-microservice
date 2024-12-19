

package org.account.entites;

import org.hibernate.annotations.Nationalized;

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

/**
 * @author <a href="mailto:leon.graser@bosch-si.com">Leon Graser</a>
 */
@NamedQueries({
        @NamedQuery(name = "deleteRoleAttributesByNameAndUser", query = "delete from RoleAttributeEntity attr where attr.role.id = :roleId and attr.name = :name"),
})
@Table(name = "ROLE_ATTRIBUTE")
@Entity
public class RoleAttributeEntity {

    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    protected String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    protected RoleEntity role;

    @Column(name = "NAME")
    protected String name;

    @Nationalized
    @Column(name = "VALUE")
    protected String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;

        if (o instanceof RoleAttributeEntity) {
            RoleAttributeEntity otherRole = (RoleAttributeEntity) o;
            result = id.equals(otherRole.id);
        }

        return result;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
