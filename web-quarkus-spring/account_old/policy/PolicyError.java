
package org.account.policy;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public final class PolicyError {
    private String message;
    private Object[] parameters;

    public PolicyError(String message, Object... parameters) {
        this.message = message;
        this.parameters = parameters;
    }

    public String getMessage() {
        return message;
    }

    public Object[] getParameters() {
        return parameters;
    }
}
