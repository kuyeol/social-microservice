
package org.account.authentication;

import org.account.service.Provider;
import org.account.service.UserModel;
import org.account.util.ValidationContext;


public interface FormAction extends Provider {

    void buildPage(FormContext context, LoginFormsProvider form);

    void validate(ValidationContext context);


    void success(FormContext context);

    boolean requiresUser();



    boolean configuredFor(UserModel user);



    void setRequiredActions( UserModel user);


}
