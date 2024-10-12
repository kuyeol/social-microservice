package org.acme.security.crypto;

public enum KeyStatus {

    ACTIVE, PASSIVE, DISABLED;

    public static KeyStatus from(boolean active, boolean enabled) {
        if (!enabled) {
            return KeyStatus.DISABLED;
        } else {
            return active ? KeyStatus.ACTIVE : KeyStatus.PASSIVE;
        }
    }

    public boolean isActive() {
        return this.equals(ACTIVE);
    }

    public boolean isEnabled() {
        return this.equals(ACTIVE) || this.equals(PASSIVE);
    }

}
