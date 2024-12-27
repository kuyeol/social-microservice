package org.acme.client.ungorithm;


import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Collection;
import java.util.LinkedList;
import org.acme.core.utils.ModelUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "JpaEntity", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"realm", "username"})
})
public class JpaEntity extends Repesentaion {


    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    private final String id = ModelUtils.generateId();


    private String inputOne;

    private String inputTwo;


    private final String realm = "USERACCOUNT";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 20)
    private Collection<UserAttributes> attributes = new LinkedList<>();

    @OneToMany(mappedBy = "jpa", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 20)
    private Collection<TestCredential> credentials = new LinkedList<>();

    public JpaEntity() {
        super();

    }

    public JpaEntity(String id, String password, String value) {

    }

    public String getId() {
        return id;
    }


    public String getInputOne() {
        return inputOne;
    }

    public void setInputOne(String input) {
        this.inputOne = input;
    }

    public String getInputTwo() {
        return inputTwo;
    }

    public void setInputTwo(String inputTwo) {
        this.inputTwo = inputTwo;
    }

    private String getRealm() {
        return realm;
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



}
