package org.acme.account.represetion.identitymanagement;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.acme.account.json.StringListMapDeserializer;


public abstract class AbstractUserRepresentation {

  public static String USERNAME = "username";
  public static String FIRST_NAME = "firstName";
  public static String LAST_NAME = "lastName";
  public static String EMAIL = "email";
  public static String LOCALE = "locale";
  protected String id;
  protected String username;
  protected String firstName;
  protected String lastName;
  protected String email;
  protected Boolean emailVerified;
  @JsonDeserialize(using = StringListMapDeserializer.class)
  protected Map<String, List<String>> attributes;

  //private UserProfileMetadata userProfileMetadata;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean isEmailVerified() {
    return emailVerified;
  }

  public void setEmailVerified(Boolean emailVerified) {
    this.emailVerified = emailVerified;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public Map<String, List<String>> getAttributes() {
    return attributes;
  }


  @JsonIgnore
  public Map<String, List<String>> getRawAttributes() {
    Map<String, List<String>> attrs = new HashMap<>(Optional.ofNullable(attributes).orElse(new HashMap<>()));

    if (username != null)
      attrs.put(USERNAME, Collections.singletonList(getUsername()));
    else
      attrs.remove(USERNAME);

    if (email != null)
      attrs.put(EMAIL, Collections.singletonList(getEmail()));
    else
      attrs.remove(EMAIL);

    if (lastName != null)
      attrs.put(LAST_NAME, Collections.singletonList(getLastName()));

    if (firstName != null)
      attrs.put(FIRST_NAME, Collections.singletonList(getFirstName()));

    return attrs;
  }

  public void setAttributes(Map<String, List<String>> attributes) {
    this.attributes = attributes;
  }

  @SuppressWarnings("unchecked")
  public <R extends AbstractUserRepresentation> R singleAttribute(String name, String value) {
    if (this.attributes == null) this.attributes=new HashMap<>();
    attributes.put(name, (value == null ? Collections.emptyList() : Arrays.asList(value)));
    return (R) this;
  }

  public String firstAttribute(String key) {
    return this.attributes == null ? null : this.attributes.get(key) == null ? null : this.attributes.get(key).isEmpty()? null : this.attributes.get(key).get(0);
  }

  //public void setUserProfileMetadata(UserProfileMetadata userProfileMetadata) {
  //  this.userProfileMetadata = userProfileMetadata;
  //}
  //
  //public UserProfileMetadata getUserProfileMetadata() {
  //  return userProfileMetadata;
  //}
}