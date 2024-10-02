
package org.account.rest;


import java.util.Collections;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.account.service.Session;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.extensions.Extension;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;





/**
 * @author Vlastimil Elias <velias@redhat.com>
 */
public class UserProfileResource {

    protected final Session session;
   // private final   AdminPermissionEvaluator auth;

//    public UserProfileResource(Session session, AdminPermissionEvaluator auth) {
//        this.session = session;
//        this.auth = auth;
//    }


  public UserProfileResource(Session session) {
    this.session = session;

  }


}
