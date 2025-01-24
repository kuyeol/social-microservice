package org.acme.core.interfaces;

import static org.acme.core.interfaces.ChainedProperty.forInteger;

public abstract class ThreadPoolProperties
{  /* defaults */

    static int default_coreSize = 10;

    static int default_maximumSize = 10;

    static int default_keepAliveTimeMinutes = 1;

    static int default_maxQueueSize = -1;

    static Boolean default_allow_maximum_size_to_diverge_from_core_size = false;

    static int default_queueSizeRejectionThreshold = 5;

    static int default_threadPoolRollingNumberStatisticalWindow = 10000;

    static int default_threadPoolRollingNumberStatisticalWindowBuckets = 10;

    private final Property<Integer> corePoolSize;

    private final Property<Integer> maximumPoolSize;

    private final Property<Integer> keepAliveTime;

    private final Property<Integer> maxQueueSize;

    private final Property<Integer> queueSizeRejectionThreshold;


    private final Property<Integer> threadPoolRollingNumberStatisticalWindowInMilliseconds;

    private final Property<Integer> threadPoolRollingNumberStatisticalWindowBuckets;


    protected ThreadPoolProperties(PoolKey key)
    {
        this(key, new Setter(), "hystrix");
    }


    protected ThreadPoolProperties(PoolKey key, Setter builder)
    {
        this(key, builder, "hystrix");
    }
    protected ThreadPoolProperties(PoolKey key, Setter builder, String propertyPrefix) {

        this.corePoolSize = getProperty(propertyPrefix, key, "coreSize", builder.getCoreSize(), default_coreSize);
        //this object always contains a reference to the configuration value for the maximumSize of the threadpool
        //it only gets applied if allowMaximumSizeToDivergeFromCoreSize is true
        this.maximumPoolSize = getProperty(propertyPrefix, key, "maximumSize", builder.getMaximumSize(), default_maximumSize);

        this.keepAliveTime = getProperty(propertyPrefix, key, "keepAliveTimeMinutes", builder.getKeepAliveTimeMinutes(), default_keepAliveTimeMinutes);
        this.maxQueueSize = getProperty(propertyPrefix, key, "maxQueueSize", builder.getMaxQueueSize(), default_maxQueueSize);
        this.queueSizeRejectionThreshold = getProperty(propertyPrefix, key, "queueSizeRejectionThreshold", builder.getQueueSizeRejectionThreshold(), default_queueSizeRejectionThreshold);
        this.threadPoolRollingNumberStatisticalWindowInMilliseconds = getProperty(propertyPrefix, key, "metrics.rollingStats.timeInMilliseconds", builder.getMetricsRollingStatisticalWindowInMilliseconds(), default_threadPoolRollingNumberStatisticalWindow);
        this.threadPoolRollingNumberStatisticalWindowBuckets = getProperty(propertyPrefix, key, "metrics.rollingStats.numBuckets", builder.getMetricsRollingStatisticalWindowBuckets(), default_threadPoolRollingNumberStatisticalWindowBuckets);
    }










    private static Property<Integer> getProperty(String propertyPrefix, PoolKey key,
                                                 String instanceProperty,
                                                 Integer builderOverrideValue, Integer defaultValue)
    {
        return forInteger().add(propertyPrefix + ".threadpool." + key + "." + instanceProperty,
                                builderOverrideValue)
                           .add(propertyPrefix + ".threadpool.default." + instanceProperty,
                                defaultValue)
                           .build();
    }


    public Property<Integer> coreSize()
    {
        return corePoolSize;
    }


    public Property<Integer> maximumSize()
    {
        return maximumPoolSize;
    }


    public Integer actualMaximumSize()
    {
        final int coreSize    = coreSize().get();
        final int maximumSize = maximumSize().get();

        if (coreSize().get()<maximumSize) {
            if (coreSize > maximumSize) {
                return coreSize;
            } else {
                return maximumSize;
            }
        } else {
            return coreSize;
        }
    }


    public Property<Integer> keepAliveTimeMinutes()
    {
        return keepAliveTime;
    }


    public Property<Integer> maxQueueSize()
    {
        return maxQueueSize;
    }


    public Property<Integer> queueSizeRejectionThreshold()
    {
        return queueSizeRejectionThreshold;
    }



    public Property<Integer> metricsRollingStatisticalWindowInMilliseconds()
    {
        return threadPoolRollingNumberStatisticalWindowInMilliseconds;
    }


    public Property<Integer> metricsRollingStatisticalWindowBuckets()
    {
        return threadPoolRollingNumberStatisticalWindowBuckets;
    }


    public static Setter Setter()
    {
        return new Setter();
    }


    public static Setter defaultSetter()
    {
        return Setter();
    }


    public static class Setter
    {

        private Integer coreSize = null;
        private Integer maximumSize = null;
        private Integer keepAliveTimeMinutes = null;
        private Integer maxQueueSize = null;
        private Integer queueSizeRejectionThreshold = null;
        private Boolean allowMaximumSizeToDivergeFromCoreSize = null;
        private Integer rollingStatisticalWindowInMilliseconds = null;
        private Integer rollingStatisticalWindowBuckets = null;



        private Setter()
        {
        }


        public Integer getCoreSize()
        {
            return coreSize;
        }


        public Integer getMaximumSize()
        {
            return maximumSize;
        }


        public Integer getKeepAliveTimeMinutes()
        {
            return keepAliveTimeMinutes;
        }


        public Integer getMaxQueueSize()
        {
            return maxQueueSize;
        }


        public Integer getQueueSizeRejectionThreshold()
        {
            return queueSizeRejectionThreshold;
        }


        public Boolean getAllowMaximumSizeToDivergeFromCoreSize()
        {
            return allowMaximumSizeToDivergeFromCoreSize;
        }


        public Integer getMetricsRollingStatisticalWindowInMilliseconds()
        {
            return rollingStatisticalWindowInMilliseconds;
        }


        public Integer getMetricsRollingStatisticalWindowBuckets()
        {
            return rollingStatisticalWindowBuckets;
        }


        public Setter withCoreSize(int value)
        {
            this.coreSize = value;
            return this;
        }


        public Setter withMaximumSize(int value)
        {
            this.maximumSize = value;
            return this;
        }


        public Setter withKeepAliveTimeMinutes(int value)
        {
            this.keepAliveTimeMinutes = value;
            return this;
        }


        public Setter withMaxQueueSize(int value)
        {
            this.maxQueueSize = value;
            return this;
        }


        public Setter withQueueSizeRejectionThreshold(int value)
        {
            this.queueSizeRejectionThreshold = value;
            return this;
        }


        public Setter withAllowMaximumSizeToDivergeFromCoreSize(Boolean value)
        {
            this.allowMaximumSizeToDivergeFromCoreSize = value;
            return this;
        }


        public Setter withMetricsRollingStatisticalWindowInMilliseconds(int value)
        {
            this.rollingStatisticalWindowInMilliseconds = value;
            return this;
        }


        public Setter withMetricsRollingStatisticalWindowBuckets(int value)
        {
            this.rollingStatisticalWindowBuckets = value;
            return this;
        }


    }


}
