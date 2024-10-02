/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.account.service;



import jakarta.ws.rs.core.HttpHeaders;
import org.account.http.HttpRequest;
import org.account.http.HttpResponse;

import java.net.URI;
import java.util.Locale;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public interface KeycloakContext {

    URI getAuthServerUrl();

    String getContextPath();


  //  KeycloakUriInfo getUri();


   // KeycloakUriInfo getUri(UrlType type);

    HttpHeaders getRequestHeaders();

    /**
     * Will always return null. You should not need access to a general context object.
     *
     * @deprecated
     */
    @Deprecated(forRemoval = true)
    default <T> T getContextObject(Class<T> clazz) {
        return null;
    }









    Locale resolveLocale(UserModel user);

    /**
     * Get current AuthenticationSessionModel, can be null out of the AuthenticationSession context.
     *
     * @return current AuthenticationSessionModel or null
     */
   // AuthenticationSessionModel getAuthenticationSession();

   // void setAuthenticationSession(AuthenticationSessionModel authenticationSession);

    HttpRequest getHttpRequest();

    HttpResponse getHttpResponse();


    void setHttpRequest(HttpRequest httpRequest);

    void setHttpResponse(HttpResponse httpResponse);
}
