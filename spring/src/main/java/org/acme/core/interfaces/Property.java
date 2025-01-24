package org.acme.core.interfaces;

import java.util.List;

public interface Property<T>
{

    void addCallback(Runnable callback);

    String getName();

    public T get();
    /**
     * Helper methods for wrapping static values and dynamic Archaius (https://github.com/Netflix/archaius) properties in the {@link Property} interface.
     */
    public static class Factory {



        public static <T> Property<T> asProperty(final T value) {
            return new Property<T>() {

                @Override
                public void addCallback(Runnable callback)
                {

                }


                @Override
                public String getName()
                {
                    return "";
                }


                @Override
                public T get() {
                    return value;
                }

            };
        }

        /**
         * @ExcludeFromJavadoc
         */
        public static Property<Integer> asProperty(final Integer value) {
            return new Property<Integer>() {

                @Override
                public void addCallback(Runnable callback)
                {

                }


                @Override
                public String getName()
                {
                    return "";
                }


                @Override
                public Integer get() {
                    return value;
                }

            };
        }

        /**
         * @ExcludeFromJavadoc
         */
        public static Property<Long> asProperty(final Long value) {
            return new Property<Long>() {

                @Override
                public void addCallback(Runnable callback)
                {

                }


                @Override
                public String getName()
                {
                    return "";
                }


                @Override
                public Long get() {
                    return value;
                }

            };
        }

        /**
         * @ExcludeFromJavadoc
         */
        public static Property<String> asProperty(final String value) {
            return new Property<String>() {

                @Override
                public void addCallback(Runnable callback)
                {

                }


                @Override
                public String getName()
                {
                    return "";
                }


                @Override
                public String get() {
                    return value;
                }

            };
        }

        /**
         * @ExcludeFromJavadoc
         */
        public static Property<Boolean> asProperty(final Boolean value) {
            return new Property<Boolean>() {

                @Override
                public void addCallback(Runnable callback)
                {

                }


                @Override
                public String getName()
                {
                    return "";
                }


                @Override
                public Boolean get() {
                    return value;
                }

            };
        }

        /**
         * When retrieved this will return the value from the given {@link Property} or if that returns null then return the <code>defaultValue</code>.
         *
         * @param value
         *            {@link Property} of property value that can return null (meaning no value)
         * @param defaultValue
         *            value to be returned if value returns null
         * @return value or defaultValue if value returns null
         */
        public static <T> Property<T> asProperty(final Property<T> value, final T defaultValue) {
            return new Property<T>() {

                @Override
                public void addCallback(Runnable callback)
                {

                }


                @Override
                public String getName()
                {
                    return "";
                }


                @Override
                public T get() {
                    T v = value.get();
                    if (v == null) {
                        return defaultValue;
                    } else {
                        return v;
                    }
                }

            };
        }

        /**
         * When retrieved this will iterate over the contained {@link Property} instances until a non-null value is found and return that.
         *
         * @param values properties to iterate over
         * @return first non-null value or null if none found
         */
        public static <T> Property<T> asProperty(final Property<T>... values) {
            return new Property<T>() {

                @Override
                public void addCallback(Runnable callback)
                {

                }


                @Override
                public String getName()
                {
                    return "";
                }


                @Override
                public T get() {
                    for (Property<T> v : values) {
                        // return the first one that doesn't return null
                        if (v.get() != null) {
                            return v.get();
                        }
                    }
                    return null;
                }

            };
        }

        /**
         * @ExcludeFromJavadoc
         */


        public static <T> Property<T> nullProperty() {
            return new Property<T>() {

                @Override
                public void addCallback(Runnable callback)
                {

                }


                @Override
                public String getName()
                {
                    return "";
                }


                @Override
                public T get() {
                    return null;
                }

            };
        }

    }

}
