package org.account.exception;


public class UsernameAlreadyUsedException extends RuntimeException
{

  public UsernameAlreadyUsedException()
  {

    super( "Login name already used!" );
  }





}
