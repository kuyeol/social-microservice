package org.acme.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Parameters
{
    private final Map<String, Object> values = new HashMap<>();

    /**
     * Add a parameter to this {@link Parameters}.
     *
     * @param name name of the parameter to add
     * @param value value of the parameter to add
     * @return this instance, modified.
     * @see {@link Parameters#map()}
     */
    public Parameters and(String name, Object value) {
        values.put(name, value);
        return this;
    }

    /**
     * Constructs an unmodifiable {@link Map} with the current parameters.
     *
     * @return an unmodifiable {@link Map} with the current parameters.
     */
    public Map<String, Object> map() {
        return Collections.unmodifiableMap(values);
    }

    /**
     * Build a {@link Parameters} with a single parameter.
     *
     * @param name name of the first parameter
     * @param value value of the first parameter
     * @return a {@link Parameters} with a single parameter.
     * @see {@link Parameters#and(String, Object)}
     * @see {@link Parameters#map()}
     */
    public static Parameters with(String name, Object value) {
        return new Parameters().and(name, value);
    }
}
