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

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class UserManager {

    private KeycloakSession session;

    public UserManager(KeycloakSession session) {
        this.session = session;
    }

    public boolean removeUser( UserModel user) {
        return removeUser( user, session.users());
    }

    public boolean removeUser( UserModel user, UserProvider userProvider) {
        if (userProvider.removeUser( user)) {
            session.getKeycloakSessionFactory().publish(new UserModel.UserRemovedEvent() {



                @Override
                public UserModel getUser() {
                    return user;
                }

                @Override
                public KeycloakSession getKeycloakSession() {
                    return session;
                }

            });
            return true;
        }
        return false;
    }

}
