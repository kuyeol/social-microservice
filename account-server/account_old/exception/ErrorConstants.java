package org.account.exception;



  import java.net.URI;

  public final class ErrorConstants {

    public static final String PROBLEM_BASE_URL = "https://ErrorConstants";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");

    private ErrorConstants() {}
  }
