package org.acme.client.ungorithm;


import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import org.acme.core.utils.ModelUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "JpaEntity")
public class JpaEntity extends Repesentaion {


    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    private String id;
    private String password;






    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 20)
    private List<UserProperties> properties = new ArrayList<>();


    private List<UserProperties> getProperties() {
        return properties;
    }

    public void addProperty(String name, String value) {
        UserProperties property = new UserProperties();
        property.setPropertyName(name);
        property.setPropertyValue(value);
        property.setUser(this);

        this.properties.add(property);
    }

    public void removeProperty(UserProperties property) {
        properties.remove(property);
        property.setUser(null);
    }


    private String input;

    public JpaEntity() {
        super();

    }


    public JpaEntity(String id, String password, String value) {

    }

    private String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = ModelUtils.generateId();
    }


    private String getPassword() {
        return this.password;
    }

    private void setPassword(String password) {
        this.password = password;
    }


}
