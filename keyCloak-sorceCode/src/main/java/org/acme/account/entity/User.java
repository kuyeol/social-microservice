package org.acme.account.entity;


import io.smallrye.common.constraint.NotNull;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.acme.account.constants.Constants;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
public class User
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "userId", nullable = false, unique = true)
  public UUID userId;

  @Email
  @NotNull
  @Size(min = 5, max = 254)
  @Column(name = "email", nullable = false, unique = true)
  public String      email;

  @NotNull
  public String      screenName;

  @NotNull
  @JsonbTransient
  @Column( length = 60, nullable = false)
  public String      password;

  @ManyToMany
  @JoinTable(
      name = "user_roles",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")},
      inverseJoinColumns = {@JoinColumn(name = "roles_name", referencedColumnName = "name")}
  )
  @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
  @JsonbTransient
  public Set< Role > roles       = new HashSet<>();

  @Column( updatable = false)
  @JsonbTransient
  public Instant     createdDate = Instant.now();

  @Size(max = 20)
  @Column( length = 20)
  @JsonbTransient
  public String      activationKey;

  @NotNull
  @Column(nullable = false)
  public  boolean   activated   = false;

  @NotNull
  @Pattern(regexp = Constants.LOGIN_REGEX)
  @Size(min = 1, max = 50)
  @Column(length = 50, unique = true, nullable = false)
  public  String    login;

  @ManyToOne
  @JoinColumn(name = "new_entity_id")
  private NewEntity newEntity;


  public NewEntity getNewEntity()
  {

    return newEntity;
  }


  public void setNewEntity( NewEntity newEntity )
  {

    this.newEntity = newEntity;
  }


  @Override
  public boolean equals( Object o )
  {

    if ( this == o ) {
      return true;
    }
    if ( ! ( o instanceof User ) ) {
      return false;
    }
    return userId != null && userId.equals( ( (User) o ).userId );
  }


  @Override
  public int hashCode()
  {

    return 31;
  }

  // TODO: static method write finduser,list,delete....

  //
  //public static Uni< User > findByName( String name )
  //{
  //
  //  return find( "name", name ).firstResult();
  //}
  //
  //
  //public static Uni< List< User > > findRole( String roleName )
  //{
  //
  //  return list( "roles", roleName );
  //}
  //
  //
  //public static Uni< Long > deleteStefs()
  //{
  //
  //  return delete( "name", "Stef" );
  //}
  //
  //
  //public static Uni< User > findOneByActivationKey( String activationKey )
  //{
  //
  //  return find( "activationKey", activationKey ).firstResult();
  //}
  //
  //
  //public static Uni< List< User > > findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(
  //    Instant dateTime )
  //{
  //
  //  return list( "activated = false and activationKey not null and createdDate <= ?1", dateTime );
  //}
  //
  //
  //public static Uni< User > findOneByResetKey( String resetKey )
  //{
  //
  //  return find( "resetKey", resetKey ).firstResult();
  //}
  //
  //
  //public static Uni< User > findOneByEmailIgnoreCase( String email )
  //{
  //
  //  return find( "LOWER(email) = LOWER(?1)", email ).firstResult();
  //}
  //
  //
  //public static Uni< User > findOneByLogin( String login )
  //{
  //
  //  return find( "login", login ).firstResult();
  //}
  //
  //
  //public static Uni< User > findOneWithAuthoritiesById( UUID userId )
  //{
  //
  //  return find( "FROM User u LEFT JOIN FETCH u.authorities WHERE u.userId = ?1", userId ).firstResult();
  //}
  //
  //
  //public static Uni< User > findOneWithAuthoritiesByLogin( String login )
  //{
  //
  //  return find( "FROM User u LEFT JOIN FETCH u.authorities WHERE u.login = ?1", login ).firstResult();
  //}
  //
  //
  //public static Uni< User > findOneWithAuthoritiesByEmailIgnoreCase( String email )
  //{
  //
  //  return find( "FROM User u LEFT JOIN FETCH u.authorities WHERE LOWER(u.login) = LOWER(?1)", email ).firstResult();
  //}
  //
  //
  //public static Uni< List< User > > findAllByLoginNot( Page page, String login )
  //{
  //
  //  return find( "login != ?1", login ).page( page )
  //                                     .list();
  //}





}
