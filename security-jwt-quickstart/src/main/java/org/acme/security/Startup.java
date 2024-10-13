package org.acme.security;


import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;


@Singleton
public class Startup {
  @Transactional
  public void loadUsers(@Observes StartupEvent evt) {
    // reset and load all test users
    User user = new User();
    User admin = new User();
    admin.add("admin", "admin", "admin");
    user.add("user", "user", "user");
  }

  private  class User {

    String name;
    String password;
    String role;


    public User() {
      this.name = name;
      this.password = password;
      this.role = role;

    }

    public  void add(String name, String password, String role){
      User user = new User();
      user.name = name;
      user.password = password;
      user.role = role;

    }

  }
}
