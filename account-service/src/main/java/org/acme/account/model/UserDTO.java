package org.acme.account.model;


import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.acme.account.constants.Constants;
import org.acme.account.entity.User;


@RegisterForReflection
public class UserDTO
{

  public UUID userId;

  @NotBlank
  @Pattern(regexp = Constants.LOGIN_REGEX)
  @Size(min = 1, max = 50)
  public String login;

  @Size(max = 50)
  public String screenName;

  @Size(max = 50)
  public String lastName;

  @Email
  @Size(min = 5, max = 254)
  public String email;


  public Boolean activated = false;


  public Instant createdDate;


  public Set<String> roles;


  public UserDTO() {
    // Empty constructor needed for Jackson.
  }

  public UserDTO( User user) {
    this.userId = user.userId;
    this.login  = user.login;
    this.screenName = user.screenName;
    this.email = user.email;
    this.createdDate = user.createdDate;
    this.roles = user.roles.stream().map(role -> role.name).collect( Collectors.toSet());
  }



}
