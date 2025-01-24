package org.acme.core.interfaces;

public interface CommandKey extends HystrixKey
{

    class Factory
    {

        private Factory()
        {
        }


        // used to intern instances so we don't keep re-creating them millions of times for the same key
        private static final InternMap<String, HystrixCommandKeyDefault> intern = new InternMap<String, HystrixCommandKeyDefault>(
            new InternMap.ValueConstructor<String, HystrixCommandKeyDefault>()
            {
                @Override
                public HystrixCommandKeyDefault create(String key)
                {
                    return new HystrixCommandKeyDefault(key);
                }
            });


        public static CommandKey asKey(String name)
        {
            return intern.interned(name);
        }


        private static class HystrixCommandKeyDefault extends HystrixKey.HystrixKeyDefault
            implements CommandKey
        {

            public HystrixCommandKeyDefault(String name)
            {
                super(name);
            }


        }


        /* package-private */
        static int getCommandCount()
        {
            return intern.size();
        }


    }


}