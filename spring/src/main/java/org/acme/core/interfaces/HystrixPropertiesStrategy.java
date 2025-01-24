package org.acme.core.interfaces;



public abstract class HystrixPropertiesStrategy {


    public HystrixCommandProperties getCommandProperties(CommandKey commandKey, HystrixCommandProperties.Setter builder) {
        return new HystrixPropertiesCommandDefault(commandKey, builder);
    }

  public String getCommandPropertiesCacheKey(CommandKey commandKey, HystrixCommandProperties.Setter builder) {
        return commandKey.name();
    }



    public ThreadPoolProperties getThreadPoolProperties(PoolKey threadPoolKey, ThreadPoolProperties.Setter builder) {
        return new PropertiesThreadPoolDefault(threadPoolKey, builder);
    }


    public String getThreadPoolPropertiesCacheKey(PoolKey threadPoolKey, ThreadPoolProperties.Setter builder) {
        return threadPoolKey.name();
    }



}
