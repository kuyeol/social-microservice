package org.acme.account.entity;


import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.panache.common.Page;
import io.smallrye.common.constraint.NotNull;
import io.smallrye.mutiny.Uni;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.acme.account.constants.Constants;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "account_table")
public class User extends PanacheEntityBase
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public UUID        id;

  @Email
  @NotNull
  @Size(min = 5, max = 254)
  @Column(name = "email", nullable = false, unique = true)
  public String      email;

  @NotNull
  public String      screenName;

  @NotNull
  @JsonbTransient
  @Column(name = "password_hash", length = 60, nullable = false)
  public String      password;

  @ManyToMany
  @JoinTable(
      name = "jhi_user_authority",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "name")}
  )
  @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
  @JsonbTransient
  public Set< Role > roles       = new HashSet<>();

  @Column(name = "created_date", updatable = false)
  @JsonbTransient
  public Instant     createdDate = Instant.now();

  @Size(max = 20)
  @Column(name = "activation_key", length = 20)
  @JsonbTransient
  public String      activationKey;

  @NotNull
  @Column(nullable = false)
  public boolean     activated   = false;

  @NotNull
  @Pattern(regexp = Constants.LOGIN_REGEX)
  @Size(min = 1, max = 50)
  @Column(length = 50, unique = true, nullable = false)
  public String      login;


  @Override
  public boolean equals( Object o )
  {

    if ( this == o ) {
      return true;
    }
    if ( ! ( o instanceof User ) ) {
      return false;
    }
    return id != null && id.equals( ( (User) o ).id );
  }


  @Override
  public int hashCode()
  {

    return 31;
  }

  // TODO: static method write finduser,list,delete....


  public static Uni< User > findByName( String name )
  {

    return find( "name", name ).firstResult();
  }


  public static Uni< List< User > > findRole( String roleName )
  {

    return list( "roles", roleName );
  }


  public static Uni< Long > deleteStefs()
  {

    return delete( "name", "Stef" );
  }


  public static Uni< User > findOneByActivationKey( String activationKey )
  {

    return find( "activationKey", activationKey ).firstResult();
  }


  public static Uni< List< User > > findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(
      Instant dateTime )
  {

    return list( "activated = false and activationKey not null and createdDate <= ?1", dateTime );
  }


  public static Uni< User > findOneByResetKey( String resetKey )
  {

    return find( "resetKey", resetKey ).firstResult();
  }


  public static Uni< User > findOneByEmailIgnoreCase( String email )
  {

    return find( "LOWER(email) = LOWER(?1)", email ).firstResult();
  }


  public static Uni< User > findOneByLogin( String login )
  {

    return find( "login", login ).firstResult();
  }


  public static Uni< User > findOneWithAuthoritiesById( Long id )
  {

    return find( "FROM User u LEFT JOIN FETCH u.authorities WHERE u.id = ?1", id ).firstResult();
  }


  public static Uni< User > findOneWithAuthoritiesByLogin( String login )
  {

    return find( "FROM User u LEFT JOIN FETCH u.authorities WHERE u.login = ?1", login ).firstResult();
  }


  public static Uni< User > findOneWithAuthoritiesByEmailIgnoreCase( String email )
  {

    return find( "FROM User u LEFT JOIN FETCH u.authorities WHERE LOWER(u.login) = LOWER(?1)", email ).firstResult();
  }


  public static Uni< List< User > > findAllByLoginNot( Page page, String login )
  {

    return find( "login != ?1", login ).page( page )
                                       .list();
  }





}
