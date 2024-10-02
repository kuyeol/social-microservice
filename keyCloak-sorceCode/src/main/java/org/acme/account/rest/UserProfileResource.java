
package org.acme.account.rest;


import java.util.Collections;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.extensions.Extension;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import org.acme.account.admin.AdminPermissionEvaluator;
import org.acme.account.model.KeycloakSession;
import org.acme.account.represetion.identitymanagement.UserProfileMetadata;
import org.acme.account.represetion.identitymanagement.util.UPConfig;
import org.acme.account.userprofile.UserProfile;
import org.acme.account.userprofile.UserProfileContext;
import org.acme.account.userprofile.UserProfileProvider;




/**
 * @author Vlastimil Elias <velias@redhat.com>
 */
public class UserProfileResource {

    protected final KeycloakSession          session;
    private final   AdminPermissionEvaluator auth;

    public UserProfileResource(KeycloakSession session, AdminPermissionEvaluator auth) {
        this.session = session;
        this.auth = auth;
    }




}
