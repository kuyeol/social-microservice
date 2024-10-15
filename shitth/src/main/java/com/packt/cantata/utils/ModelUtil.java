package com.packt.cantata.utils;

import java.util.UUID;

public final class ModelUtil {

private ModelUtil() {}
    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static String toLowerCaseSafe(String str) {
        return str == null ? null : str.toLowerCase();
    }

}
