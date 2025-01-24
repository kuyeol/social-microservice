package org.acme.core.interfaces;

public interface PoolKey extends HystrixKey
{

    class Factory {
        private Factory() {
        }

        // used to intern instances so we don't keep re-creating them millions of times for the same key
        private static final InternMap<String, PoolKey> intern
            = new InternMap<String, PoolKey>(
            new InternMap.ValueConstructor<String, PoolKey>() {
                @Override
                public PoolKey create(String key) {
                    return new HystrixThreadPoolKeyDefault(key);
                }
            });

        /**
         * Retrieve (or create) an interned HystrixThreadPoolKey instance for a given name.
         *
         * @param name thread pool name
         * @return HystrixThreadPoolKey instance that is interned (cached) so a given name will always retrieve the same instance.
         */
        public static PoolKey asKey(String name) {
            return intern.interned(name);
        }

        private static class HystrixThreadPoolKeyDefault extends HystrixKeyDefault implements PoolKey {
            public HystrixThreadPoolKeyDefault(String name) {
                super(name);
            }
        }

        /* package-private */ static int getThreadPoolCount() {
            return intern.size();
        }
    }


}
