package org.acme.account.model;


import org.acme.account.represetion.identitymanagement.UserRepresentation;


public interface ExportImportManager
{



  UserModel createUser( UserRepresentation userRep);


}
