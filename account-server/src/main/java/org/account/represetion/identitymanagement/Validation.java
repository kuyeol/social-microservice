

package org.account.represetion.identitymanagement;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.acme.account.exception.ValidationException;
import org.acme.account.represetion.identitymanagement.util.EmailValidationUtil;
import org.acme.account.util.FormMessage;


public class Validation {

    public static final String FIELD_PASSWORD_CONFIRM = "password-confirm";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_OTP_CODE = "totp";
    public static final String FIELD_OTP_LABEL = "userLabel";

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[\\p{IsLatin}|\\p{IsCommon}]+$");

    private static void addError(List< FormMessage > errors, String field, String message, Object... parameters){
        errors.add(new FormMessage(field, message, parameters));
    }


    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    /**
     * Check if string is blank (null or lenght is 0 or contains only white characters)
     *
     * @param s to check
     * @return true if string is blank
     */
    public static boolean isBlank(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static boolean isEmailValid(String email) {
        return EmailValidationUtil.isValidEmail( email);
    }

    public static boolean isUsernameValid(String username) {

        return USERNAME_PATTERN.matcher(username).matches();
    }

    public static List<FormMessage> getFormErrorsFromValidation(List< ValidationException.Error> errors) {
        List<FormMessage> messages = new ArrayList<>();
        for (ValidationException.Error error : errors) {
            addError(messages, error.getAttribute(), error.getMessage(), error.getMessageParameters());
        }
        return messages;

    }
}
