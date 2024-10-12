package org.acme.security.provider;

import java.util.stream.Stream;
import org.acme.security.crypto.KeyWrraper;

public interface KeyProvider extends Provider {

    Stream<KeyWrraper> getKeysStream();

    default void close() {

    }
}
