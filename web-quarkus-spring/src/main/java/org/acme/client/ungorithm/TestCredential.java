package org.acme.client.ungorithm;

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
import org.acme.core.utils.ModelUtils;

@Table(name = "TestCredential")
@Entity
@NamedQueries({

    @NamedQuery(name = "testcredentialByUser", query = "select cred from TestCredential cred where cred.user = :user "),
    @NamedQuery(name = "deletetestCredentialsByUser", query =
        "delete from TestCredential cred where cred.user IN (select u from" + " JpaEntity u where u.id =:Id )")})

public class TestCredential implements JpaType {


    public static String FIND_PARENT      = "lockPersonQuery";
    public static String FIND_PARENT_user = "testcredentialByUser";


    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    private String id ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JPAENTITY_ID")
    protected JpaEntity user;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "CREATED_DATE")
    private Long createdDate;

    @Column(name = "SECRET_DATA")
    private String secretData;

    @Column(name = "CREDENTIAL_DATA")
    private String credentialData;

    @Column(name = "PRIORITY")
    private int priority;


    public TestCredential() {
        if(id == null) {
            this.id=ModelUtils.generateId();
        }

    }

    public TestCredential(TestCredential cred) {
        this.id = cred.id;
        this.user = cred.user;
        this.type = cred.type;
        this.createdDate = cred.createdDate;
        this.secretData = cred.secretData;
    }

    public JpaEntity getUser() {
        return this.user;
    }

    public void setUser(JpaEntity user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public String getSecretData() {
        return secretData;
    }

    public void setSecretData(String secretData) {
        this.secretData = secretData;
    }

    public String getCredentialData() {
        return credentialData;
    }

    public void setCredentialData(String credentialData) {
        this.credentialData = credentialData;
    }

    private int getPriority() {
        return priority;
    }

    private void setPriority(int priority) {
        this.priority = priority;
    }


}
