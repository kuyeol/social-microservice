package org.acme.client.ungorithm;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.acme.core.utils.ModelUtils;

@Entity
@Table(name = "USER_Attributes")
public class UserAttributes {

    @Id
    @Column(name="ID", length = 36)
    @Access(AccessType.PROPERTY)
    private String Id = ModelUtils.generateId();

    @Column(name = "Attributes_NAME")
    private String attributeName;

    @Column(name = "Attributes_VALUE")
    private String attributeValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "JpaEntity_ID")
    private JpaEntity user;

    // Getters and Setters
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public JpaEntity getUser() {
        return user;
    }

    public void setUser(JpaEntity user) {
        this.user = user;
    }
}