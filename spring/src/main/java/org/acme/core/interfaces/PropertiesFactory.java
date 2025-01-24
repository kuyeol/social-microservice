package org.acme.core.interfaces;

import java.util.concurrent.ConcurrentHashMap;

public class PropertiesFactory
{

    /**
     * Clears all the defaults in the static property cache. This makes it possible for property defaults to not persist for
     * an entire JVM lifetime.  May be invoked directly, and also gets invoked by <code>Hystrix.reset()</code>
     */
    public static void reset() {
        commandProperties.clear();
        threadPoolProperties.clear();
        collapserProperties.clear();
    }


    private static final ConcurrentHashMap<String, HystrixCommandProperties> commandProperties = new ConcurrentHashMap<String, HystrixCommandProperties>();


    public static HystrixCommandProperties getCommandProperties(CommandKey key, HystrixCommandProperties.Setter builder) {
        HystrixPropertiesStrategy hystrixPropertiesStrategy =null;
        String cacheKey = hystrixPropertiesStrategy.getCommandPropertiesCacheKey(key, builder);
        if (cacheKey != null) {
            HystrixCommandProperties properties = commandProperties.get(cacheKey);
            if (properties != null) {
                return properties;
            } else {
                if (builder == null) {
                    builder = HystrixCommandProperties.Setter();
                }
                // create new instance
                properties = hystrixPropertiesStrategy.getCommandProperties(key, builder);
                // cache and return
                HystrixCommandProperties existing = commandProperties.putIfAbsent(cacheKey, properties);
                if (existing == null) {
                    return properties;
                } else {
                    return existing;
                }
            }
        } else {
            // no cacheKey so we generate it with caching
            return hystrixPropertiesStrategy.getCommandProperties(key, builder);
        }
    }

    // String is ThreadPoolKey.name() (we can't use ThreadPoolKey directly as we can't guarantee it implements hashcode/equals correctly)
    private static final ConcurrentHashMap<String, ThreadPoolProperties> threadPoolProperties = new ConcurrentHashMap<String, ThreadPoolProperties>();


    public static ThreadPoolProperties getThreadPoolProperties(PoolKey key, ThreadPoolProperties.Setter builder) {
        HystrixPropertiesStrategy hystrixPropertiesStrategy = null;

        String cacheKey = hystrixPropertiesStrategy.getThreadPoolPropertiesCacheKey(key, builder);
        if (cacheKey != null) {
            ThreadPoolProperties properties = threadPoolProperties.get(cacheKey);
            if (properties != null) {
                return properties;
            } else {
                if (builder == null) {
                    builder = ThreadPoolProperties.Setter();
                }
                // create new instance
                properties = hystrixPropertiesStrategy.getThreadPoolProperties(key, builder);
                // cache and return
                ThreadPoolProperties existing = threadPoolProperties.putIfAbsent(cacheKey, properties);
                if (existing == null) {
                    return properties;
                } else {
                    return existing;
                }
            }
        } else {
            // no cacheKey so we generate it with caching
            return hystrixPropertiesStrategy.getThreadPoolProperties(key, builder);
        }
    }

    // String is CollapserKey.name() (we can't use CollapserKey directly as we can't guarantee it implements hashcode/equals correctly)
    private static final ConcurrentHashMap<String, String> collapserProperties = new ConcurrentHashMap<String,
        String>();




}
