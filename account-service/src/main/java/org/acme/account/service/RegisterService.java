package org.acme.account.service;


import io.smallrye.mutiny.Uni;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import org.acme.account.constants.RolesConstants;
import org.acme.account.entity.Role;
import org.acme.account.entity.User;
import org.acme.account.exception.EmailAlreadyUsedException;
import org.acme.account.exception.UsernameAlreadyUsedException;
import org.acme.account.model.UserDTO;
import org.acme.account.util.BCryptPasswordHasher;
import org.slf4j.Logger;


@ApplicationScoped
@Transactional
public class RegisterService
{

  final         BCryptPasswordHasher passwordHasher;
  private final Logger               log = (Logger) LoggerFactory.getLogger( RegisterService.class);


  @Inject
  public RegisterService( BCryptPasswordHasher passwordHasher )
  {

    this.passwordHasher = passwordHasher;
  }


  private boolean removeNonActivatedUser( User existingUser )
  {

    if ( existingUser.activated ) {
      return false;
    }
    User.delete( "id", existingUser.id );

    this.clearUserCaches( existingUser );
    return true;
  }


  public User registerUser( UserDTO userDTO, String password )
  {

    User.findOneByLogin( userDTO.login.toLowerCase() )
        .onItem()
        .ifNotNull()
        .transformToUni( exit ->
                             removeNonActivatedUser( exit )
                             ? Uni.createFrom()
                                  .voidItem()
                             : Uni.createFrom()
                                  .failure( new UsernameAlreadyUsedException() ) );

    User.findOneByEmailIgnoreCase( userDTO.email )
        .onItem()
        .ifNotNull()
        .transformToUni( exit -> removeNonActivatedUser( exit )
                                 ? Uni.createFrom()
                                      .voidItem()
                                 : Uni.createFrom()
                                      .failure( new EmailAlreadyUsedException() ) );

    var newUser = new User();
    newUser.login = userDTO.login.toLowerCase();
    // new user gets initially a generated password
    newUser.password  = passwordHasher.hash( password );
    newUser.screenName = userDTO.screenName;

    if ( userDTO.email != null ) {
      newUser.email = userDTO.email.toLowerCase();
    }


    newUser.activated = false;
    // new user gets registration key
    newUser.activationKey = RandomUtil.generateActivationKey();


    Set< Role > addRole = new HashSet<>();
    Role.<Role >findById( RolesConstants.USER ).onItem().ifNotNull().transform( addRole::add );

    newUser.roles =addRole;
    User.persist( newUser );

    this.clearUserCaches( newUser );
    log.debug( "Created Information for User: {}", newUser );
    return newUser;
  }



  public void clearUserCaches(User user) {
    this.clearUserCachesByLogin(user.login);
    if (user.email != null) {
      this.clearUserCachesByEmail(user.email);
    }
  }

  public void clearUserCachesByEmail(String email) {}

  public void clearUserCachesByLogin(String login) {}


}
