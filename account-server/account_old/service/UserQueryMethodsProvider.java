

package org.account.service;


import java.util.Map;
import java.util.stream.Stream;


public interface UserQueryMethodsProvider
{

  default Stream< UserModel > searchForUserStream( Map< String, String > params )
  {

    return searchForUserStream( params, null, null );
  }

  Stream< UserModel > searchForUserStream( Map< String, String > params, Integer firstResult, Integer maxResults );

  default Stream< UserModel > getRoleMembersStream( RoleModel role )
  {

    return getRoleMembersStream( role, null, null );
  }

  default Stream< UserModel > getRoleMembersStream( RoleModel role, Integer firstResult, Integer maxResults )
  {

    return Stream.empty();
  }

  Stream< UserModel > searchForUserByUserAttributeStream( String attrName, String attrValue );





}
