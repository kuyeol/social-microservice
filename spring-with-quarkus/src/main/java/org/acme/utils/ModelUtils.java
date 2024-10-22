package org.acme.utils;

import java.util.UUID;

public final class ModelUtils {

    private ModelUtils() {
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }


}
