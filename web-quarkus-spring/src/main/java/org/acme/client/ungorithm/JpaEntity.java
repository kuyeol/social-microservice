package org.acme.client.ungorithm;


import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.LinkedList;
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
    private String id = ModelUtils.generateId();


    private String input;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 20)
    private Collection<UserAttributes> attributes = new LinkedList<>();

    @OneToMany(mappedBy = "jpa", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 20)
    private Collection<TestCredential> credentials = new LinkedList<>();

    public JpaEntity(String id, String password, String value) {

    }

    public String getId() {
        return id;
    }

    public JpaEntity() {
        super();

    }

    private Collection<UserAttributes> getAttributes() {
        if (attributes == null) {
            attributes = new LinkedList<>();
        }
        return attributes;
    }

    public void addAttributes(String name, String value) {
        UserAttributes att = new UserAttributes();
        att.setAttributeName(name);
        att.setAttributeValue(value);
        att.setUser(this);

        this.attributes.add(att);
    }


   private Collection<TestCredential> getCredentials() {
        if (credentials == null) {
            credentials = new LinkedList<>();
        }
        return credentials;
    }

   public void setCredentials(Collection<TestCredential> cred) {
        this.credentials = cred;
    }



    public void setId(String id) {
        this.id = ModelUtils.generateId();
    }


}
