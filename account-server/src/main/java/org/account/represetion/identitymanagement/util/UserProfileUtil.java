/*
 * Copyright 2023 Red Hat, Inc. and/or its affiliates
 *  and other contributors as indicated by the @author tags.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.account.represetion.identitymanagement.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

import org.acme.account.model.KeycloakSession;
import org.acme.account.model.UserModel;
import org.acme.account.represetion.identitymanagement.AttributeContext;
import org.acme.account.represetion.identitymanagement.AttributeMetadata;
import org.acme.account.represetion.identitymanagement.Attributes;
import org.acme.account.represetion.identitymanagement.UserProfileAttributeMetadata;
import org.acme.account.represetion.identitymanagement.UserProfileMetadata;
import org.acme.account.userprofile.UserProfile;
import org.acme.account.userprofile.UserProfileProvider;


/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class UserProfileUtil {

    private static final Logger logger = Logger.getLogger(UserProfileUtil.class);

    public static final String USER_METADATA_GROUP = "user-metadata";

    public static final Predicate< AttributeContext > ONLY_ADMIN_CONDITION = context -> context.getContext().isAdminContext();



    /**
     * Returns whether the attribute with the given {@code name} is a root attribute.
     *
     * @param name the attribute name
     * @return
     */
    public static boolean isRootAttribute(String name) {
        return UserModel.USERNAME.equals( name)
               || UserModel.EMAIL.equals(name)
               || UserModel.FIRST_NAME.equals(name)
               || UserModel.LAST_NAME.equals(name)
               || UserModel.LOCALE.equals(name);
    }



}
