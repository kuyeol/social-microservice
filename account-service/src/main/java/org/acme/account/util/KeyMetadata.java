
package org.acme.account.util;



/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public abstract class KeyMetadata {

    private String providerId;
    private long providerPriority;

    private String kid;

    private KeyStatus status;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public long getProviderPriority() {
        return providerPriority;
    }

    public void setProviderPriority(long providerPriority) {
        this.providerPriority = providerPriority;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public KeyStatus getStatus() {
        return status;
    }

    public void setStatus(KeyStatus status) {
        this.status = status;
    }

}
