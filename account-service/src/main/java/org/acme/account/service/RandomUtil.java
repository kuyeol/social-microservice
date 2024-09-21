package org.acme.account.service;


import java.security.SecureRandom;


public class RandomUtil
{

  private static final int          DEF_COUNT = 8;

  private static final SecureRandom SECURE_RANDOM;



  static{
    SECURE_RANDOM = new SecureRandom();
    SECURE_RANDOM.nextBytes( new byte[64] );

  }



  public static String generateRandomAlphanumericString()
  {

    return generateSecureRandomString(  SECURE_RANDOM.nextInt(DEF_COUNT) );
  }


  public RandomUtil()
  {

    allowedChars = defaultChars;
  }


  private static final char[] defaultChars = {'a', 'b', 'c', 'd', 'e', 'f',
      'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
      't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
      'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
      'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5',
      '6', '7', '8', '9'};

  private static char[] allowedChars;


  public RandomUtil( char[] allowedChars ) throws IllegalArgumentException
  {

    if ( allowedChars == null || allowedChars.length == 0 ) {
      throw new IllegalArgumentException(
          "The allowedChars value must be a non null character array with at least one value" );
    } else {
      this.allowedChars = allowedChars;
    }
  }


  public RandomUtil( String allowedCharsString )
  {

    if ( allowedCharsString == null || allowedCharsString.length() == 0 ) {
      throw new IllegalArgumentException( "The allowedCharsString may non be null or zero length" );
    } else {
      this.allowedChars = allowedCharsString.toCharArray();
    }
  }


  public static   String generateSecureRandomString( int length ) throws IllegalArgumentException
  {

    if ( length <= 0 ) {
      throw new IllegalArgumentException( "Invalid length.  Must be a positive, non-zero integer." );
    }
    String       randomString = "";
    SecureRandom secRnd       = new SecureRandom();

    for ( int i = 0 ; i < length ; i++ ) {
      randomString += allowedChars[secRnd.nextInt( allowedChars.length )];
    }

    return randomString;
  }


  /**
   * Generate a password.
   *
   * @return the generated password.
   */
  public static String generatePassword()
  {

    return generateRandomAlphanumericString();
  }


  /**
   * Generate an activation key.
   *
   * @return the generated activation key.
   */
  public static String generateActivationKey()
  {

    return generateRandomAlphanumericString();
  }


  /**
   * Generate a reset key.
   *
   * @return the generated reset key.
   */
  public static String generateResetKey()
  {

    return generateRandomAlphanumericString();
  }





}
