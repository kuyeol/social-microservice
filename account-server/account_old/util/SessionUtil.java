package org.account.util;

import org.account.http.Resteasy;
import org.account.service.Session;

public class SessionUtil {
  private SessionUtil() {

  }

  public static Session getKeycloakSession() {
    return Resteasy.getContextData(Session.class);
  }


  public static Session setKeycloakSession(Session session) {
    return Resteasy.pushContext(Session.class, session);
  }
}
