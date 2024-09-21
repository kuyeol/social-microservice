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

  public UUID id;

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

  @Size(max = 256)
  public String imageUrl;

  public Boolean activated = false;

  @Size(min = 2, max = 10)
  public String langKey;

  public String createdBy;

  public Instant createdDate;

  public String lastModifiedBy;

  public Instant lastModifiedDate;

  public Set<String> roles;


  public UserDTO() {
    // Empty constructor needed for Jackson.
  }

  public UserDTO( User user) {
    this.id = user.id;
    this.login = user.login;
    this.screenName = user.screenName;
    this.email = user.email;
    this.createdDate = user.createdDate;
    this.roles = user.roles.stream().map(role -> role.name).collect( Collectors.toSet());
  }



}
