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
import java.io.Closeable;
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
public class JpaEntity extends Repesentaion implements AutoCloseable{


    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    private final String id = ModelUtils.generateId();


    private String inputOne;

    private String inputTwo;


    private final String realm = "USERACCOUNT";

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 20)
    private Collection<UserAttributes> attributes = new LinkedList<>();

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy="user")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 20)
    private Collection<TestCredential> credentials = new LinkedList<>();

    public JpaEntity() {
        super();

    }

    public JpaEntity( String password, String value) {

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


    public Collection<TestCredential> getCredentials() {
        if (credentials == null) {
            credentials = new LinkedList<>();
        }
        return credentials;
    }

    public void setCredentials(Collection<TestCredential> cred) {
        this.credentials = cred;
    }


    /**
     * Closes this resource, relinquishing any underlying resources. This method is invoked automatically on objects
     * managed by the {@code try}-with-resources statement.
     *
     * @throws Exception if this resource cannot be closed
     * @apiNote While this interface method is declared to throw {@code Exception}, implementers are <em>strongly</em>
     * encouraged to declare concrete implementations of the {@code close} method to throw more specific exceptions, or
     * to throw no exception at all if the close operation cannot fail.
     *
     * <p> Cases where the close operation may fail require careful
     * attention by implementers. It is strongly advised to relinquish the underlying resources and to internally
     * <em>mark</em> the resource as closed, prior to throwing the exception. The {@code close} method is unlikely to
     * be
     * invoked more than once and so this ensures that the resources are released in a timely manner. Furthermore it
     * reduces problems that could arise when the resource wraps, or is wrapped, by another resource.
     *
     * <p><em>Implementers of this interface are also strongly advised
     * to not have the {@code close} method throw {@link InterruptedException}.</em>
     * <p>
     * This exception interacts with a thread's interrupted status, and runtime misbehavior is likely to occur if an
     * {@code InterruptedException} is {@linkplain Throwable#addSuppressed suppressed}.
     * <p>
     * More generally, if it would cause problems for an exception to be suppressed, the {@code AutoCloseable.close}
     * method should not throw it.
     *
     * <p>Note that unlike the {@link Closeable#close close}
     * method of {@link Closeable}, this {@code close} method is <em>not</em> required to be idempotent.  In other
     * words, calling this {@code close} method more than once may have some visible side effect, unlike
     * {@code Closeable.close} which is required to have no effect if called more than once.
     * <p>
     * However, implementers of this interface are strongly encouraged to make their {@code close} methods idempotent.
     */
    @Override
    public void close() throws Exception {

    }
}
