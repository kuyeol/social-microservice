package org.acme.account.constants;


public class Constants
{

  public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

  public static final String SYSTEM_ACCOUNT = "system";
  public static final String ANONYMOUS_USER = "anonymoususer";

  public static final String DEFAULT_LANGUAGE = "en";

  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  public static final String LOCAL_DATE_FORMAT = "yyyy-MM-dd";

  public static final String USERS_BY_EMAIL_CACHE = "usersByEmail";
  public static final String USERS_BY_LOGIN_CACHE = "usersByLogin";

  private Constants() {}
}
