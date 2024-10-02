
package org.account.represetion.identitymanagement;

import org.account.exception.ValidationError;

import static java.util.Optional.ofNullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;



public interface Attributes {

    /**
     * Default value for attributes with no value set.
     */
    List<String> EMPTY_VALUE = Collections.emptyList();

    /**
     * Returns the first value associated with the attribute with the given {@name}.
     *
     * @param name the name of the attribute
     *
     * @return the first value
     */
    default String getFirst(String name) {
        List<String> values = ofNullable(get(name)).orElse(List.of());

        if (values.isEmpty()) {
            return null;
        }

        return values.get(0);
    }

    /**
     * Returns all values for an attribute with the given {@code name}.
     *
     * @param name the name of the attribute
     *
     * @return the attribute values
     */
    List<String> get(String name);

    /**
     * Checks whether an attribute is read-only.
     *
     * @param name the attribute name
     *
     * @return {@code true} if the attribute is read-only. Otherwise, {@code false}
     */
    boolean isReadOnly(String name);

    /**
     * Validates the attribute with the given {@code name}.
     *
     * @param name the name of the attribute
     * @param listeners the listeners for listening for errors. <code>ValidationError.inputHint</code> contains name of the attribute in error.
     *
     * @return {@code true} if validation is successful. Otherwise, {@code false}. In case there is no attribute with the given {@code name},
     * {@code false} is also returned but without triggering listeners
     */
    boolean validate(String name, Consumer<ValidationError>... listeners);

    /**
     * Checks whether an attribute with the given {@code name} is defined.
     *
     * @param name the name of the attribute
     *
     * @return {@code true} if the attribute is defined. Otherwise, {@code false}
     */
    boolean contains(String name);

    /**
     * Returns the names of all defined attributes.
     *
     * @return the set of attribute names
     */
    Set<String> nameSet();


    Map<String, List<String>> getWritable();

    /**
     * <p>Returns the metadata associated with the attribute with the given {@code name}.
     *
     * <p>The {@link AttributeMetadata} is a copy of the original metadata. The original metadata
     * keeps immutable.
     *
     * @param name the attribute name
     * @return the metadata
     */
    AttributeMetadata getMetadata(String name);

    /**
     * Returns whether the attribute with the given {@code name} is required.
     *
     * @param name the attribute name
     * @return {@code true} if the attribute is required. Otherwise, {@code false}.
     */
    boolean isRequired(String name);


    Map<String, List<String>> getReadable();


    Map<String, List<String>> toMap();


    Map<String, List<String>> getUnmanagedAttributes();
}
