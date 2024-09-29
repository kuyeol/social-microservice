package org.acme.account.model;





public interface DatastoreProvider extends Provider
{




  ExportImportManager getExportImportManager();

  UserLoginFailureProvider loginFailures();



  UserProvider users();

  UserSessionProvider userSessions();
}
