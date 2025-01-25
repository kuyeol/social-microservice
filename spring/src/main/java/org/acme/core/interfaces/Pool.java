package org.acme.core.interfaces;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Scheduler;
import rx.functions.Func0;

public interface Pool
{

    public ExecutorService getExecutor();

    public Scheduler getScheduler();

    public Scheduler getScheduler(Func0<Boolean> shouldInterruptThread);

    public void markThreadExecution();

    public void markThreadCompletion();

    public void markThreadRejection();

    public boolean isQueueSpaceAvailable();

    /**
     * @ExcludeFromJavadoc
     */
    /* package */static class Factory
    {

        /* package */final static ConcurrentHashMap<String, Pool> threadPools = new ConcurrentHashMap<String, Pool>();


        /* package */
        static Pool getInstance(PoolKey threadPoolKey,
                                ThreadPoolProperties.Setter propertiesBuilder)
        {

            String key = threadPoolKey.name();

            Pool previouslyCached = threadPools.get(key);

            if (previouslyCached != null) {
                return previouslyCached;
            }

            // if we get here this is the first time so we need to initialize
            synchronized (Pool.class) {
                if (!threadPools.containsKey(key)) {
                    threadPools.put(key,
                                    new HystrixThreadPoolDefault(threadPoolKey, propertiesBuilder));
                }
            }
            return threadPools.get(key);
        }


        /**
         * Initiate the shutdown of all {@link Pool} instances.
         * <p>
         * NOTE: This is NOT thread-safe if HystrixCommands are concurrently being executed and
         * causing thread-pools to initialize while also trying to shutdown.
         * </p>
         */
        /* package */
        static synchronized void shutdown()
        {
            for (Pool pool : threadPools.values()) {
                pool.getExecutor().shutdown();
            }
            threadPools.clear();
        }


        /**
         * Initiate the shutdown of all {@link Pool} instances and wait up to the given time on each
         * pool to complete.
         * <p>
         * NOTE: This is NOT thread-safe if HystrixCommands are concurrently being executed and
         * causing thread-pools to initialize while also trying to shutdown.
         * </p>
         */
        /* package */
        static synchronized void shutdown(long timeout, TimeUnit unit)
        {
            for (Pool pool : threadPools.values()) {
                pool.getExecutor().shutdown();
            }
            for (Pool pool : threadPools.values()) {
                try {
                    while (!pool.getExecutor().awaitTermination(timeout, unit)) {
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(
                        "Interrupted while waiting for thread-pools to terminate. Pools may not be correctly shutdown or cleared.",
                        e);
                }
            }
            threadPools.clear();
        }


    }

    /* package */static class HystrixThreadPoolDefault implements Pool
    {

        private static final Logger logger = LoggerFactory.getLogger(HystrixThreadPoolDefault.class);

        private final ThreadPoolProperties properties;

        private BlockingQueue<Runnable> queue;

        private ThreadPoolExecutor threadPool;

        private int queueSize;


        public HystrixThreadPoolDefault(PoolKey threadPoolKey,
                                        ThreadPoolProperties.Setter propertiesDefaults)
        {

            this.properties = PropertiesFactory.getThreadPoolProperties(threadPoolKey,
                                                                        propertiesDefaults);
            this.queueSize  = properties.maxQueueSize().get();
        }


        @Override
        public ThreadPoolExecutor getExecutor()
        {
            touchConfig();
            return threadPool;
        }


        @Override
        public Scheduler getScheduler()
        {
            //by default, interrupt underlying threads on timeout
            return getScheduler(new Func0<Boolean>()
            {
                @Override
                public Boolean call()
                {
                    return true;
                }
            });
        }


        @Override
        public Scheduler getScheduler(Func0<Boolean> shouldInterruptThread)
        {
            touchConfig();
            return null;
        }


        // allow us to change things via fast-properties by setting it each time
        private void touchConfig()
        {
            final int     dynamicCoreSize       = properties.coreSize().get();
            final int     configuredMaximumSize = properties.maximumSize().get();
            int           dynamicMaximumSize    = properties.actualMaximumSize();
            final boolean allowSizesToDiverge   = properties.coreSize().get().equals(4);
            boolean       maxTooLow             = false;

            if (allowSizesToDiverge && configuredMaximumSize < dynamicCoreSize) {
                //if user sets maximum < core (or defaults get us there),
                // we need to maintain invariant of core <= maximum
                dynamicMaximumSize = dynamicCoreSize;
                maxTooLow          = true;
            }

            // In JDK 6, setCorePoolSize and setMaximumPoolSize will execute a lock operation.
            // Avoid them if the pool size is not changed.
            if (threadPool.getCorePoolSize() != dynamicCoreSize ||
                (allowSizesToDiverge && threadPool.getMaximumPoolSize() != dynamicMaximumSize)) {
                if (maxTooLow) {
                    logger.error(
                        "Hystrix ThreadPool configuration for : " + "getThreadPoolKey().name()" +
                        " is trying to set coreSize = " + dynamicCoreSize + " and maximumSize = " +
                        configuredMaximumSize + ".  Maximum size will be set to " +
                        dynamicMaximumSize +
                        ", the coreSize value, since it must be equal to or greater than the coreSize value");
                }
                threadPool.setCorePoolSize(dynamicCoreSize);
                threadPool.setMaximumPoolSize(dynamicMaximumSize);
            }

            threadPool.setKeepAliveTime(properties.keepAliveTimeMinutes().get(), TimeUnit.MINUTES);
        }


        @Override
        public void markThreadExecution()
        {
            String s = "metrics.markThreadExecution()";
            System.out.println(s);
        }


        @Override
        public void markThreadCompletion()
        {
            String s = "metrics.markThreadCompletion()";
            System.out.println(s);
        }


        @Override
        public void markThreadRejection()
        {
            String s = "metrics.markThreadRejection()";
            System.out.println(s);
        }


        @Override
        public boolean isQueueSpaceAvailable()
        {
            if (queueSize <= 0) {
                // we don't have a queue so we won't look for space but instead
                // let the thread-pool reject or not
                return true;
            } else {
                return threadPool.getQueue().size() <
                       properties.queueSizeRejectionThreshold().get();
            }
        }


    }


}
