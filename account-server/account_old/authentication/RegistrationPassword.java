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

package org.account.authentication;

import jakarta.ws.rs.core.MultivaluedMap;
import org.account.cofig.Config;
import org.account.credential.PasswordCredentialModel;
import org.account.model.AuthenticationExecutionModel;
import org.account.model.UserCredentialModel;
import org.account.policy.PolicyError;
import org.account.represetion.identitymanagement.Validation;
import org.account.service.ProviderConfigProperty;
import org.account.service.UserModel;
import org.account.util.FormMessage;
import org.account.util.ValidationContext;


import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class RegistrationPassword implements FormAction, FormActionFactory {
    public static final String PROVIDER_ID = "registration-password-action";

    @Override
    public String getHelpText() {
        return "Validates that password matches password confirmation field.  It also will store password in user's credential store.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return null;
    }

    @Override
    public void validate(ValidationContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        List<FormMessage> errors = new ArrayList<>();

        if (Validation.isBlank(formData.getFirst(RegistrationPage.FIELD_PASSWORD))) {
            errors.add(new FormMessage(RegistrationPage.FIELD_PASSWORD," Messages.MISSING_PASSWORD"));
        } else if (!formData.getFirst(RegistrationPage.FIELD_PASSWORD).equals(formData.getFirst(RegistrationPage.FIELD_PASSWORD_CONFIRM))) {
            errors.add(new FormMessage(RegistrationPage.FIELD_PASSWORD_CONFIRM, "Messages.INVALID_PASSWORD_CONFIRM"));
        }
        if (formData.getFirst(RegistrationPage.FIELD_PASSWORD) != null) {

         formData.getFirst(RegistrationPage.FIELD_EMAIL);
          formData.getFirst(RegistrationPage.FIELD_USERNAME);
          formData.getFirst(RegistrationPage.FIELD_PASSWORD);
            if (context != null)
                errors.add(new FormMessage(RegistrationPage.FIELD_PASSWORD, "err.getMessage()","err.getParameters()" ));
        }

        if (errors.size() > 0) {

            formData.remove(RegistrationPage.FIELD_PASSWORD);
            formData.remove(RegistrationPage.FIELD_PASSWORD_CONFIRM);
            context.validationError(formData, errors);
            return;
        } else {
            context.success();
        }
    }

    @Override
    public void success(FormContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String password = formData.getFirst(RegistrationPage.FIELD_PASSWORD);
        UserModel user = context.getUser();
        try {
            user.credentialManager().updateCredential(UserCredentialModel.password(formData.getFirst("password"), false));
        } catch (Exception me) {
            user.addRequiredAction(UserModel.RequiredAction.UPDATE_PASSWORD);
        }

    }

    @Override
    public void buildPage(FormContext context, LoginFormsProvider form) {
        form.setAttribute("passwordRequired", true);
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(UserModel user) {

    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public String getDisplayType() {
        return "Password Validation";
    }

    @Override
    public String getReferenceCategory() {
        return PasswordCredentialModel.TYPE;
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    private static AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.DISABLED
    };
    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }


    public FormAction create() {
        return this;
    }

    @Override
    public void init(Config.Scope config) {

    }


    public void postInit() {

    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
