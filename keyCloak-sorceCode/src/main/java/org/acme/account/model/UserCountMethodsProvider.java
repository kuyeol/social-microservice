package org.acme.account.model;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


public interface UserCountMethodsProvider
{

  default Stream< UserModel > searchForUserStream(  Map< String, String > params )
  {

    return searchForUserStream( params, null, null );
  }


  Stream<UserModel> searchForUserStream(Map<String, String> params, Integer firstResult, Integer maxResults);


  Stream<UserModel> searchForUserByUserAttributeStream( String attrName, String attrValue);

}