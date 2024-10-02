package org.acme.account.entity;


import jakarta.inject.Singleton;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.acme.account.model.UserDTO;


@Singleton
public class UserMapper {

  public List< UserDTO > usersToUserDTOs(List< User > users) {
    return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).collect(Collectors.toList());
  }

  public UserDTO userToUserDTO(User user) {
    return new UserDTO(user);
  }

  public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
    return userDTOs.stream().filter(Objects::nonNull).map(this::userDTOToUser).collect(Collectors.toList());
  }

  public User userDTOToUser(UserDTO userDTO) {
    if (userDTO == null) {
      return null;
    } else {
      User user = new User();
      user.userId = userDTO.userId;
      user.login  = userDTO.login;
      user.email = userDTO.email;
  
      user.activated = userDTO.activated;

      Set< Role > roles = this.authoritiesFromStrings( userDTO.roles);
      user.roles = roles;
      return user;
    }
  }

  private Set<Role> authoritiesFromStrings(Set<String> roleesAsString) {
    Set<Role> roles = new HashSet<>();

    if (roleesAsString != null) {
      roles = roleesAsString
          .stream()
          .map(string -> {
            org.acme.account.entity.Role auth = new Role();
            auth.name = string;
            return auth;
          })
          .collect(Collectors.toSet());
    }

    return roles;
  }

  public User userFromId( UUID id) {
    if (id == null) {
      return null;
    }
    User user = new User();
    user.userId = id;
    return user;
  }
}
