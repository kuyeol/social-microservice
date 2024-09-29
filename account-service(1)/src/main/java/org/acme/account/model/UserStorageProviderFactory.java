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

package org.acme.account.model;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acme.account.admin.Config;


/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface UserStorageProviderFactory<T extends UserStorageProvider>  {


    /**
     * called per Keycloak transaction.
     *
     * @param session
     * @param model
     * @return
     */
    T create(KeycloakSession session);

    /**
     * This is the name of the provider and will be showed in the admin console as an option.
     *
     * @return
     */

    String getId();


    default void init( Config.Scope config) {

    }

    default void postInit(KeycloakSessionFactory factory) {

    }

    default void close() {

    }

    default String getHelpText() {
        return "";
    }





    /**
     * Called when UserStorageProviderModel is created.  This allows you to do initialization of any additional configuration
     * you need to add.  For example, you may be introspecting a database or ldap schema to automatically create mappings.
     *
     * @param session
     * @param realm
     * @param model
     */
    default void onCreate(KeycloakSession session) {

    }

    /**
     * configuration properties that are common across all UserStorageProvider implementations
     *
     * @return
     */



}
