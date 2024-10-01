
package org.account.policy;


import org.account.service.Provider;
import org.account.service.UserModel;


public interface PasswordPolicyProvider extends Provider {

    String STRING_CONFIG_TYPE = "String";
    String INT_CONFIG_TYPE = "int";

    PolicyError validate( UserModel user, String password);
    PolicyError validate(String user, String password);
    Object parseConfig(String value);

    default Integer parseInteger(String value, Integer defaultValue) {
        try {
            return value != null ? Integer.valueOf(value) : defaultValue;
        } catch (NumberFormatException e) {
            throw new PasswordPolicyConfigException("Not a valid number");
        }
    }
}
