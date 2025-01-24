/**
 * Copyright 2012 Netflix, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.acme.core.interfaces;



import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.acme.core.interfaces.ChainedProperty.forBoolean;
import static org.acme.core.interfaces.ChainedProperty.forInteger;
import static org.acme.core.interfaces.ChainedProperty.forString;


public abstract class HystrixCommandProperties {
    private static final Logger logger = LoggerFactory.getLogger(HystrixCommandProperties.class);

    /* defaults */
    /* package */ static final Integer default_metricsRollingStatisticalWindow = 10000;// default => statisticalWindow: 10000 = 10 seconds (and default of 10 buckets so each bucket is 1 second)
    private static final Integer default_metricsRollingStatisticalWindowBuckets = 10;// default => statisticalWindowBuckets: 10 = 10 buckets in a 10 second window so each bucket is 1 second
    private static final Integer default_circuitBreakerRequestVolumeThreshold = 20;// default => statisticalWindowVolumeThreshold: 20 requests in 10 seconds must occur before statistics matter
    private static final Integer default_circuitBreakerSleepWindowInMilliseconds = 5000;// default => sleepWindow: 5000 = 5 seconds that we will sleep before trying again after tripping the circuit
    private static final Integer default_circuitBreakerErrorThresholdPercentage = 50;// default => errorThresholdPercentage = 50 = if 50%+ of requests in 10 seconds are failures or latent then we will trip the circuit
    private static final Boolean default_circuitBreakerForceOpen = false;// default => forceCircuitOpen = false (we want to allow traffic)
    /* package */ static final Boolean default_circuitBreakerForceClosed = false;// default => ignoreErrors = false 
    private static final Integer default_executionTimeoutInMilliseconds = 1000; // default => executionTimeoutInMilliseconds: 1000 = 1 second
    private static final Boolean default_executionTimeoutEnabled = true;
    private static final ExecutionIsolationStrategy default_executionIsolationStrategy = ExecutionIsolationStrategy.THREAD;
    private static final Boolean default_executionIsolationThreadInterruptOnTimeout = true;
    private static final Boolean default_executionIsolationThreadInterruptOnFutureCancel = false;
    private static final Boolean default_metricsRollingPercentileEnabled = true;
    private static final Boolean default_requestCacheEnabled = true;
    private static final Integer default_fallbackIsolationSemaphoreMaxConcurrentRequests = 10;
    private static final Boolean default_fallbackEnabled = true;
    private static final Integer default_executionIsolationSemaphoreMaxConcurrentRequests = 10;
    private static final Boolean default_requestLogEnabled = true;
    private static final Boolean default_circuitBreakerEnabled = true;
    private static final Integer default_metricsRollingPercentileWindow = 60000; // default to 1 minute for RollingPercentile 
    private static final Integer default_metricsRollingPercentileWindowBuckets = 6; // default to 6 buckets (10 seconds each in 60 second window)
    private static final Integer default_metricsRollingPercentileBucketSize = 100; // default to 100 values max per bucket
    private static final Integer default_metricsHealthSnapshotIntervalInMilliseconds = 500; // default to 500ms as max frequency between allowing snapshots of health (error percentage etc)

    @SuppressWarnings("unused") private final CommandKey key;
    private final Property<Integer> circuitBreakerRequestVolumeThreshold; // number of requests that must be made within a statisticalWindow before open/close decisions are made using stats
    private final Property<Integer> circuitBreakerSleepWindowInMilliseconds; // milliseconds after tripping circuit before allowing retry
    private final Property<Boolean> circuitBreakerEnabled; // Whether circuit breaker should be enabled.
    private final Property<Integer> circuitBreakerErrorThresholdPercentage; // % of 'marks' that must be failed to trip the circuit
    private final Property<Boolean> circuitBreakerForceOpen; // a property to allow forcing the circuit open (stopping all requests)
    private final Property<Boolean> circuitBreakerForceClosed; // a property to allow ignoring errors and therefore never trip 'open' (ie. allow all traffic through)
    private final Property<ExecutionIsolationStrategy> executionIsolationStrategy; // Whether a command should be executed in a separate thread or not.
    private final Property<Integer> executionTimeoutInMilliseconds; // Timeout value in milliseconds for a command
    private final Property<Boolean> executionTimeoutEnabled; //Whether timeout should be triggered
    private final Property<String> executionIsolationThreadPoolKeyOverride; // What thread-pool this command should run in (if running on a separate thread).
    private final Property<Integer> executionIsolationSemaphoreMaxConcurrentRequests; // Number of permits for execution semaphore
    private final Property<Integer> fallbackIsolationSemaphoreMaxConcurrentRequests; // Number of permits for fallback semaphore
    private final Property<Boolean> fallbackEnabled; // Whether fallback should be attempted.
    private final Property<Boolean> executionIsolationThreadInterruptOnTimeout; // Whether an underlying Future/Thread (when runInSeparateThread == true) should be interrupted after a timeout
    private final Property<Boolean> executionIsolationThreadInterruptOnFutureCancel; // Whether canceling an underlying Future/Thread (when runInSeparateThread == true) should interrupt the execution thread
    private final Property<Integer> metricsRollingStatisticalWindowInMilliseconds; // milliseconds back that will be tracked
    private final Property<Integer> metricsRollingStatisticalWindowBuckets; // number of buckets in the statisticalWindow
    private final Property<Boolean> metricsRollingPercentileEnabled; // Whether monitoring should be enabled (SLA and Tracers).
    private final Property<Integer> metricsRollingPercentileWindowInMilliseconds; // number of milliseconds that will be tracked in RollingPercentile
    private final Property<Integer> metricsRollingPercentileWindowBuckets; // number of buckets percentileWindow will be divided into
    private final Property<Integer> metricsRollingPercentileBucketSize; // how many values will be stored in each percentileWindowBucket
    private final Property<Integer> metricsHealthSnapshotIntervalInMilliseconds; // time between health snapshots
    private final Property<Boolean> requestLogEnabled; // whether command request logging is enabled.
    private final Property<Boolean> requestCacheEnabled; // Whether request caching is enabled.


    public static enum ExecutionIsolationStrategy {
        THREAD, SEMAPHORE
    }

    protected HystrixCommandProperties(CommandKey key) {
        this(key, new Setter(), "hystrix");
    }

    protected HystrixCommandProperties(CommandKey key, Setter builder) {
        this(key, builder, "hystrix");
    }


    protected HystrixCommandProperties(CommandKey key, Setter builder, String propertyPrefix) {
        this.key = key;
        this.circuitBreakerEnabled = getProperty(propertyPrefix, key, "circuitBreaker.enabled", builder.getCircuitBreakerEnabled(), default_circuitBreakerEnabled);
        this.circuitBreakerRequestVolumeThreshold = getProperty(propertyPrefix, key, "circuitBreaker.requestVolumeThreshold", builder.getCircuitBreakerRequestVolumeThreshold(), default_circuitBreakerRequestVolumeThreshold);
        this.circuitBreakerSleepWindowInMilliseconds = getProperty(propertyPrefix, key, "circuitBreaker.sleepWindowInMilliseconds", builder.getCircuitBreakerSleepWindowInMilliseconds(), default_circuitBreakerSleepWindowInMilliseconds);
        this.circuitBreakerErrorThresholdPercentage = getProperty(propertyPrefix, key, "circuitBreaker.errorThresholdPercentage", builder.getCircuitBreakerErrorThresholdPercentage(), default_circuitBreakerErrorThresholdPercentage);
        this.circuitBreakerForceOpen = getProperty(propertyPrefix, key, "circuitBreaker.forceOpen", builder.getCircuitBreakerForceOpen(), default_circuitBreakerForceOpen);
        this.circuitBreakerForceClosed = getProperty(propertyPrefix, key, "circuitBreaker.forceClosed", builder.getCircuitBreakerForceClosed(), default_circuitBreakerForceClosed);
        this.executionIsolationStrategy = getProperty(propertyPrefix, key, "execution.isolation.strategy", builder.getExecutionIsolationStrategy(), default_executionIsolationStrategy);
        //this property name is now misleading.  //TODO figure out a good way to deprecate this property name
        this.executionTimeoutInMilliseconds = getProperty(propertyPrefix, key, "execution.isolation.thread.timeoutInMilliseconds", builder.getExecutionIsolationThreadTimeoutInMilliseconds(), default_executionTimeoutInMilliseconds);
        this.executionTimeoutEnabled = getProperty(propertyPrefix, key, "execution.timeout.enabled", builder.getExecutionTimeoutEnabled(), default_executionTimeoutEnabled);
        this.executionIsolationThreadInterruptOnTimeout = getProperty(propertyPrefix, key, "execution.isolation.thread.interruptOnTimeout", builder.getExecutionIsolationThreadInterruptOnTimeout(), default_executionIsolationThreadInterruptOnTimeout);
        this.executionIsolationThreadInterruptOnFutureCancel = getProperty(propertyPrefix, key, "execution.isolation.thread.interruptOnFutureCancel", builder.getExecutionIsolationThreadInterruptOnFutureCancel(), default_executionIsolationThreadInterruptOnFutureCancel);
        this.executionIsolationSemaphoreMaxConcurrentRequests = getProperty(propertyPrefix, key, "execution.isolation.semaphore.maxConcurrentRequests", builder.getExecutionIsolationSemaphoreMaxConcurrentRequests(), default_executionIsolationSemaphoreMaxConcurrentRequests);
        this.fallbackIsolationSemaphoreMaxConcurrentRequests = getProperty(propertyPrefix, key, "fallback.isolation.semaphore.maxConcurrentRequests", builder.getFallbackIsolationSemaphoreMaxConcurrentRequests(), default_fallbackIsolationSemaphoreMaxConcurrentRequests);
        this.fallbackEnabled = getProperty(propertyPrefix, key, "fallback.enabled", builder.getFallbackEnabled(), default_fallbackEnabled);
        this.metricsRollingStatisticalWindowInMilliseconds = getProperty(propertyPrefix, key, "metrics.rollingStats.timeInMilliseconds", builder.getMetricsRollingStatisticalWindowInMilliseconds(), default_metricsRollingStatisticalWindow);
        this.metricsRollingStatisticalWindowBuckets = getProperty(propertyPrefix, key, "metrics.rollingStats.numBuckets", builder.getMetricsRollingStatisticalWindowBuckets(), default_metricsRollingStatisticalWindowBuckets);
        this.metricsRollingPercentileEnabled = getProperty(propertyPrefix, key, "metrics.rollingPercentile.enabled", builder.getMetricsRollingPercentileEnabled(), default_metricsRollingPercentileEnabled);
        this.metricsRollingPercentileWindowInMilliseconds = getProperty(propertyPrefix, key, "metrics.rollingPercentile.timeInMilliseconds", builder.getMetricsRollingPercentileWindowInMilliseconds(), default_metricsRollingPercentileWindow);
        this.metricsRollingPercentileWindowBuckets = getProperty(propertyPrefix, key, "metrics.rollingPercentile.numBuckets", builder.getMetricsRollingPercentileWindowBuckets(), default_metricsRollingPercentileWindowBuckets);
        this.metricsRollingPercentileBucketSize = getProperty(propertyPrefix, key, "metrics.rollingPercentile.bucketSize", builder.getMetricsRollingPercentileBucketSize(), default_metricsRollingPercentileBucketSize);
        this.metricsHealthSnapshotIntervalInMilliseconds = getProperty(propertyPrefix, key, "metrics.healthSnapshot.intervalInMilliseconds", builder.getMetricsHealthSnapshotIntervalInMilliseconds(), default_metricsHealthSnapshotIntervalInMilliseconds);
        this.requestCacheEnabled = getProperty(propertyPrefix, key, "requestCache.enabled", builder.getRequestCacheEnabled(), default_requestCacheEnabled);
        this.requestLogEnabled = getProperty(propertyPrefix, key, "requestLog.enabled", builder.getRequestLogEnabled(), default_requestLogEnabled);

        // threadpool doesn't have a global override, only instance level makes sense
        this.executionIsolationThreadPoolKeyOverride = forString().add(propertyPrefix + ".command." + key.name() + ".threadPoolKeyOverride", null).build();
    }


    public Property<Boolean> circuitBreakerEnabled() {
        return circuitBreakerEnabled;
    }


    public Property<Integer> circuitBreakerErrorThresholdPercentage() {
        return circuitBreakerErrorThresholdPercentage;
    }


    public Property<Boolean> circuitBreakerForceClosed() {
        return circuitBreakerForceClosed;
    }


    public Property<Boolean> circuitBreakerForceOpen() {
        return circuitBreakerForceOpen;
    }


    public Property<Integer> circuitBreakerRequestVolumeThreshold() {
        return circuitBreakerRequestVolumeThreshold;
    }


    public Property<Integer> circuitBreakerSleepWindowInMilliseconds() {
        return circuitBreakerSleepWindowInMilliseconds;
    }


    public Property<Integer> executionIsolationSemaphoreMaxConcurrentRequests() {
        return executionIsolationSemaphoreMaxConcurrentRequests;
    }


    public Property<ExecutionIsolationStrategy> executionIsolationStrategy() {
        return executionIsolationStrategy;
    }


    public Property<Boolean> executionIsolationThreadInterruptOnTimeout() {
        return executionIsolationThreadInterruptOnTimeout;
    }


    public Property<Boolean> executionIsolationThreadInterruptOnFutureCancel() {
        return executionIsolationThreadInterruptOnFutureCancel;
    }


    public Property<String> executionIsolationThreadPoolKeyOverride() {
        return executionIsolationThreadPoolKeyOverride;
    }






    public Property<Boolean> executionTimeoutEnabled() {
        return executionTimeoutEnabled;
    }


    public Property<Integer> fallbackIsolationSemaphoreMaxConcurrentRequests() {
        return fallbackIsolationSemaphoreMaxConcurrentRequests;
    }


    public Property<Boolean> fallbackEnabled() {
        return fallbackEnabled;
    }


    public Property<Integer> metricsHealthSnapshotIntervalInMilliseconds() {
        return metricsHealthSnapshotIntervalInMilliseconds;
    }

    public Property<Integer> metricsRollingPercentileBucketSize() {
        return metricsRollingPercentileBucketSize;
    }


    public Property<Boolean> metricsRollingPercentileEnabled() {
        return metricsRollingPercentileEnabled;
    }

    public Property<Integer> metricsRollingPercentileWindow() {
        return metricsRollingPercentileWindowInMilliseconds;
    }


    public Property<Integer> metricsRollingPercentileWindowInMilliseconds() {
        return metricsRollingPercentileWindowInMilliseconds;
    }


    public Property<Integer> metricsRollingPercentileWindowBuckets() {
        return metricsRollingPercentileWindowBuckets;
    }


    public Property<Integer> metricsRollingStatisticalWindowInMilliseconds() {
        return metricsRollingStatisticalWindowInMilliseconds;
    }


    public Property<Integer> metricsRollingStatisticalWindowBuckets() {
        return metricsRollingStatisticalWindowBuckets;
    }


    public Property<Boolean> requestCacheEnabled() {
        return requestCacheEnabled;
    }


    public Property<Boolean> requestLogEnabled() {
        return requestLogEnabled;
    }

    private static Property<Boolean> getProperty(String propertyPrefix, CommandKey key, String instanceProperty, Boolean builderOverrideValue, Boolean defaultValue) {
        return forBoolean()
                .add(propertyPrefix + ".command." + key.name() + "." + instanceProperty, builderOverrideValue)
                .add(propertyPrefix + ".command.default." + instanceProperty, defaultValue)
                .build();
    }

    private static Property<Integer> getProperty(String propertyPrefix, CommandKey key, String instanceProperty, Integer builderOverrideValue, Integer defaultValue) {
        return forInteger()
                .add(propertyPrefix + ".command." + key.name() + "." + instanceProperty, builderOverrideValue)
                .add(propertyPrefix + ".command.default." + instanceProperty, defaultValue)
                .build();
    }

    @SuppressWarnings("unused")
    private static Property<String> getProperty(String propertyPrefix, CommandKey key, String instanceProperty, String builderOverrideValue, String defaultValue) {
        return forString()
                .add(propertyPrefix + ".command." + key.name() + "." + instanceProperty, builderOverrideValue)
                .add(propertyPrefix + ".command.default." + instanceProperty, defaultValue)
                .build();
    }

    private static Property<ExecutionIsolationStrategy> getProperty(final String propertyPrefix, final CommandKey key, final String instanceProperty, final ExecutionIsolationStrategy builderOverrideValue, final ExecutionIsolationStrategy defaultValue) {
        return new ExecutionIsolationStrategyHystrixProperty(builderOverrideValue, key, propertyPrefix, defaultValue, instanceProperty);

    }

    /**
     * Property that converts a String to ExecutionIsolationStrategy so we remain TypeSafe.
     */
    private static final class ExecutionIsolationStrategyHystrixProperty implements Property<ExecutionIsolationStrategy> {
        private final Property<String> property;
        private volatile ExecutionIsolationStrategy value;
        private final ExecutionIsolationStrategy defaultValue;

        private ExecutionIsolationStrategyHystrixProperty(ExecutionIsolationStrategy builderOverrideValue, CommandKey key, String propertyPrefix, ExecutionIsolationStrategy defaultValue, String instanceProperty) {
            this.defaultValue = defaultValue;
            String overrideValue = null;
            if (builderOverrideValue != null) {
                overrideValue = builderOverrideValue.name();
            }
            property = forString()
                    .add(propertyPrefix + ".command." + key.name() + "." + instanceProperty, overrideValue)
                    .add(propertyPrefix + ".command.default." + instanceProperty, defaultValue.name())
                    .build();

            // initialize the enum value from the property
            parseProperty();

            // use a callback to handle changes so we only handle the parse cost on updates rather than every fetch

        }


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
        public ExecutionIsolationStrategy get() {
            return value;
        }

        private void parseProperty() {
            try {
                value = ExecutionIsolationStrategy.valueOf(property.get());
            } catch (Exception e) {
                logger.error("Unable to derive ExecutionIsolationStrategy from property value: " + property.get(), e);
                // use the default value
                value = defaultValue;
            }
        }
    }

    /**
     * Factory method to retrieve the default Setter.
     */
    public static Setter Setter() {
        return new Setter();
    }

    /**
     * Factory method to retrieve the default Setter.
     * Groovy has a bug (GROOVY-6286) which does not allow method names and inner classes to have the same name
     * This method fixes Issue #967 and allows Groovy consumers to choose this method and not trigger the bug
     */
    public static Setter defaultSetter() {
        return Setter();
    }


    public static class Setter {

        private Boolean circuitBreakerEnabled = null;
        private Integer circuitBreakerErrorThresholdPercentage = null;
        private Boolean circuitBreakerForceClosed = null;
        private Boolean circuitBreakerForceOpen = null;
        private Integer circuitBreakerRequestVolumeThreshold = null;
        private Integer circuitBreakerSleepWindowInMilliseconds = null;
        private Integer executionIsolationSemaphoreMaxConcurrentRequests = null;
        private ExecutionIsolationStrategy executionIsolationStrategy = null;
        private Boolean executionIsolationThreadInterruptOnTimeout = null;
        private Boolean executionIsolationThreadInterruptOnFutureCancel = null;
        private Integer executionTimeoutInMilliseconds = null;
        private Boolean executionTimeoutEnabled = null;
        private Integer fallbackIsolationSemaphoreMaxConcurrentRequests = null;
        private Boolean fallbackEnabled = null;
        private Integer metricsHealthSnapshotIntervalInMilliseconds = null;
        private Integer metricsRollingPercentileBucketSize = null;
        private Boolean metricsRollingPercentileEnabled = null;
        private Integer metricsRollingPercentileWindowInMilliseconds = null;
        private Integer metricsRollingPercentileWindowBuckets = null;
        /* null means it hasn't been overridden */
        private Integer metricsRollingStatisticalWindowInMilliseconds = null;
        private Integer metricsRollingStatisticalWindowBuckets = null;
        private Boolean requestCacheEnabled = null;
        private Boolean requestLogEnabled = null;

        /* package */ Setter() {
        }

        public Boolean getCircuitBreakerEnabled() {
            return circuitBreakerEnabled;
        }

        public Integer getCircuitBreakerErrorThresholdPercentage() {
            return circuitBreakerErrorThresholdPercentage;
        }

        public Boolean getCircuitBreakerForceClosed() {
            return circuitBreakerForceClosed;
        }

        public Boolean getCircuitBreakerForceOpen() {
            return circuitBreakerForceOpen;
        }

        public Integer getCircuitBreakerRequestVolumeThreshold() {
            return circuitBreakerRequestVolumeThreshold;
        }

        public Integer getCircuitBreakerSleepWindowInMilliseconds() {
            return circuitBreakerSleepWindowInMilliseconds;
        }

        public Integer getExecutionIsolationSemaphoreMaxConcurrentRequests() {
            return executionIsolationSemaphoreMaxConcurrentRequests;
        }

        public ExecutionIsolationStrategy getExecutionIsolationStrategy() {
            return executionIsolationStrategy;
        }

        public Boolean getExecutionIsolationThreadInterruptOnTimeout() {
            return executionIsolationThreadInterruptOnTimeout;
        }

        public Boolean getExecutionIsolationThreadInterruptOnFutureCancel() {
			return executionIsolationThreadInterruptOnFutureCancel;
		}

		/**
         * @deprecated As of 1.4.0, use {@link #getExecutionTimeoutInMilliseconds()}
         */
        @Deprecated
        public Integer getExecutionIsolationThreadTimeoutInMilliseconds() {
            return executionTimeoutInMilliseconds;
        }

        public Integer getExecutionTimeoutInMilliseconds() {
            return executionTimeoutInMilliseconds;
        }

        public Boolean getExecutionTimeoutEnabled() {
            return executionTimeoutEnabled;
        }

        public Integer getFallbackIsolationSemaphoreMaxConcurrentRequests() {
            return fallbackIsolationSemaphoreMaxConcurrentRequests;
        }

        public Boolean getFallbackEnabled() {
            return fallbackEnabled;
        }

        public Integer getMetricsHealthSnapshotIntervalInMilliseconds() {
            return metricsHealthSnapshotIntervalInMilliseconds;
        }

        public Integer getMetricsRollingPercentileBucketSize() {
            return metricsRollingPercentileBucketSize;
        }

        public Boolean getMetricsRollingPercentileEnabled() {
            return metricsRollingPercentileEnabled;
        }

        public Integer getMetricsRollingPercentileWindowInMilliseconds() {
            return metricsRollingPercentileWindowInMilliseconds;
        }

        public Integer getMetricsRollingPercentileWindowBuckets() {
            return metricsRollingPercentileWindowBuckets;
        }

        public Integer getMetricsRollingStatisticalWindowInMilliseconds() {
            return metricsRollingStatisticalWindowInMilliseconds;
        }

        public Integer getMetricsRollingStatisticalWindowBuckets() {
            return metricsRollingStatisticalWindowBuckets;
        }

        public Boolean getRequestCacheEnabled() {
            return requestCacheEnabled;
        }

        public Boolean getRequestLogEnabled() {
            return requestLogEnabled;
        }

        public Setter withCircuitBreakerEnabled(boolean value) {
            this.circuitBreakerEnabled = value;
            return this;
        }

        public Setter withCircuitBreakerErrorThresholdPercentage(int value) {
            this.circuitBreakerErrorThresholdPercentage = value;
            return this;
        }

        public Setter withCircuitBreakerForceClosed(boolean value) {
            this.circuitBreakerForceClosed = value;
            return this;
        }

        public Setter withCircuitBreakerForceOpen(boolean value) {
            this.circuitBreakerForceOpen = value;
            return this;
        }

        public Setter withCircuitBreakerRequestVolumeThreshold(int value) {
            this.circuitBreakerRequestVolumeThreshold = value;
            return this;
        }

        public Setter withCircuitBreakerSleepWindowInMilliseconds(int value) {
            this.circuitBreakerSleepWindowInMilliseconds = value;
            return this;
        }

        public Setter withExecutionIsolationSemaphoreMaxConcurrentRequests(int value) {
            this.executionIsolationSemaphoreMaxConcurrentRequests = value;
            return this;
        }

        public Setter withExecutionIsolationStrategy(ExecutionIsolationStrategy value) {
            this.executionIsolationStrategy = value;
            return this;
        }

        public Setter withExecutionIsolationThreadInterruptOnTimeout(boolean value) {
            this.executionIsolationThreadInterruptOnTimeout = value;
            return this;
        }

        public Setter withExecutionIsolationThreadInterruptOnFutureCancel(boolean value) {
            this.executionIsolationThreadInterruptOnFutureCancel = value;
            return this;
        }

        /**
         * @deprecated As of 1.4.0, replaced with {@link #withExecutionTimeoutInMilliseconds(int)}.  Timeouts are no longer applied only to thread-isolated commands, so a thread-specific name is misleading
         */
        @Deprecated
        public Setter withExecutionIsolationThreadTimeoutInMilliseconds(int value) {
            this.executionTimeoutInMilliseconds = value;
            return this;
        }

        public Setter withExecutionTimeoutInMilliseconds(int value) {
            this.executionTimeoutInMilliseconds = value;
            return this;
        }

        public Setter withExecutionTimeoutEnabled(boolean value) {
            this.executionTimeoutEnabled = value;
            return this;
        }

        public Setter withFallbackIsolationSemaphoreMaxConcurrentRequests(int value) {
            this.fallbackIsolationSemaphoreMaxConcurrentRequests = value;
            return this;
        }

        public Setter withFallbackEnabled(boolean value) {
            this.fallbackEnabled = value;
            return this;
        }

        public Setter withMetricsHealthSnapshotIntervalInMilliseconds(int value) {
            this.metricsHealthSnapshotIntervalInMilliseconds = value;
            return this;
        }

        public Setter withMetricsRollingPercentileBucketSize(int value) {
            this.metricsRollingPercentileBucketSize = value;
            return this;
        }

        public Setter withMetricsRollingPercentileEnabled(boolean value) {
            this.metricsRollingPercentileEnabled = value;
            return this;
        }

        public Setter withMetricsRollingPercentileWindowInMilliseconds(int value) {
            this.metricsRollingPercentileWindowInMilliseconds = value;
            return this;
        }

        public Setter withMetricsRollingPercentileWindowBuckets(int value) {
            this.metricsRollingPercentileWindowBuckets = value;
            return this;
        }

        public Setter withMetricsRollingStatisticalWindowInMilliseconds(int value) {
            this.metricsRollingStatisticalWindowInMilliseconds = value;
            return this;
        }

        public Setter withMetricsRollingStatisticalWindowBuckets(int value) {
            this.metricsRollingStatisticalWindowBuckets = value;
            return this;
        }

        public Setter withRequestCacheEnabled(boolean value) {
            this.requestCacheEnabled = value;
            return this;
        }

        public Setter withRequestLogEnabled(boolean value) {
            this.requestLogEnabled = value;
            return this;
        }
    }
}
