

package org.acme.core.utils;

import jakarta.persistence.TypedQuery;

public class PaginationUtils {

    public static final int DEFAULT_MAX_RESULTS = Integer.MAX_VALUE >> 1;

    public static <T> TypedQuery<T> paginateQuery(TypedQuery<T> query, Integer first, Integer max) {
        if (first != null && first >= 0) {
            query = query.setFirstResult(first);

            // Workaround for https://hibernate.atlassian.net/browse/HHH-14295
            if (max == null || max < 0) {
                max = DEFAULT_MAX_RESULTS;
            }
        }

        if (max != null && max >= 0) {
            query = query.setMaxResults(max);
        }

        return query;
    }

}
