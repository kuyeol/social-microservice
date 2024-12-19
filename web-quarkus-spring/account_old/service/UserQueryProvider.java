package org.account.service;


import java.util.Map;
import java.util.stream.Stream;


public interface UserQueryProvider extends UserQueryMethodsProvider, UserCountMethodsProvider
{

  @Override
  default Stream< UserModel > searchForUserStream( Map< String, String > params )
  {

    return UserQueryMethodsProvider.super.searchForUserStream( params );
  }

  @Override
  Stream< UserModel > searchForUserStream( Map< String, String > params, Integer firstResult, Integer maxResults );

  @Override
  Stream< UserModel > searchForUserByUserAttributeStream( String attrName, String attrValue );





}
