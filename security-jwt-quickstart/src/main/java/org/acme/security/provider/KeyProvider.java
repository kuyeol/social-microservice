package org.acme.security.provider;

import java.util.stream.Stream;
import org.acme.security.crypto.KeyWrapper;

public interface KeyProvider extends Provider {

    Stream<KeyWrapper> getKeysStream();

    default void close() {

    }
}
