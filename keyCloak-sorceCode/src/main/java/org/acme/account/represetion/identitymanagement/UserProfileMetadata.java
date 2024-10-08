/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates
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
package org.acme.account.represetion.identitymanagement;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;

/**
 * @author Vlastimil Elias <velias@redhat.com>
 */
public class UserProfileMetadata {

    private List<UserProfileAttributeMetadata> attributes;

    public UserProfileMetadata() {

    }



    public List<UserProfileAttributeMetadata> getAttributes() {
        return attributes;
    }



    public void setAttributes(List<UserProfileAttributeMetadata> attributes) {
        this.attributes = attributes;
    }

    public UserProfileAttributeMetadata getAttributeMetadata(String name) {
        for (UserProfileAttributeMetadata m : Optional.ofNullable(getAttributes()).orElse(emptyList())) {
            if (m.getName().equals(name)) {
                return m;
            }
        }

        return null;
    }


}
