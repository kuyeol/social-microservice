package org.acme.ext.terran.model;

public record TablePrinter<A, B>(A instance) {

    public B mapTo(Class<B> targetClass) {
        try {
            B targetInstance = targetClass.getDeclaredConstructor().newInstance();

            for (var sourceField : instance.getClass().getDeclaredFields()) {
                try {
                    String fieldName = sourceField.getName();
                    sourceField.setAccessible(true);
                    Object value = sourceField.get(instance);

                    var targetField = targetClass.getDeclaredField(fieldName);
                    targetField.setAccessible(true);
                    targetField.set(targetInstance, value);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    // Ignore fields that don't exist in the target class or are inaccessible
                }
            }

            return targetInstance;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping fields to target class: " + targetClass.getName(), e);
        }
    }

    public <V> V getFieldValue(String fieldName, Class<V> fieldType) {
        try {
            var field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true); // Bypass access modifiers
            Object value = field.get(instance);

            if (!fieldType.isInstance(value)) {
                throw new IllegalArgumentException("Field value is not of the expected type: " + fieldType.getName());
            }

            return fieldType.cast(value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error accessing field: " + fieldName, e);
        }
    }

    public <V> void setFieldValue(String fieldName, V value, Class<V> fieldType) {
        try {
            var field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true); // Bypass access modifiers

            if (!fieldType.isInstance(value)) {
                throw new IllegalArgumentException("Provided value is not compatible with field type: " + fieldType.getName());
            }

            field.set(instance, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error accessing field: " + fieldName, e);
        }
    }

    public boolean hasField(String fieldName) {
        try {
            instance.getClass().getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }
}
