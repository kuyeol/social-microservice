package org.acme.core.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectionUtil {
    public static String join(Collection<String> strings) {
        return join(strings, ", ");
    }

    public static String join(Collection<String> strings, String separator) {
        return strings.stream().collect(Collectors.joining(String.valueOf(separator)));
    }

    // Return true if all items from col1 are in col2 and viceversa. Order is not taken into account
    public static <T> boolean collectionEquals(Collection<T> col1, Collection<T> col2) {
        if (col1.size()!=col2.size()) {
            return false;
        }
        Map<T, Integer> countMap = new HashMap<>();
        for(T o : col1) {
            countMap.merge(o, 1, (v1, v2) -> v1 + v2);
        }
        for(T o : col2) {
            Integer v = countMap.get(o);
            if (v==null) {
                return false;
            }
            if (v == 1) {
                countMap.remove(o);
            } else {
                countMap.put(o, v-1);
            }
        }
        return countMap.isEmpty();
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static <T> Set<T> collectionToSet(Collection<T> collection) {
        return collection == null ? null : new HashSet<>(collection);
    }
}
