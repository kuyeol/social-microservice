package org.acme.core.interfaces;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func0;

public abstract class Command<T> extends AbstractCommand<T>
{

    protected Command(Pool threadPool, PoolKey threadPoolKey, HystrixCommandProperties properties,
                      CommandKey commandKey)
    {
        super(threadPool, threadPoolKey, properties, commandKey);
    }


    protected Command(PoolKey threadPool)
    {
        super(null, threadPool, null, null, null, null, null, null, null, null, null);
    }


    protected Command(PoolKey threadPool, int executionIsolationThreadTimeoutInMilliseconds)
    {
        super(null,
              threadPool,
              null,
              null,
              HystrixCommandProperties.Setter()
                                      .withExecutionTimeoutInMilliseconds(
                                          executionIsolationThreadTimeoutInMilliseconds),
              null,
              null,
              null,
              null,
              null,
              null);
    }


    protected Command(Setter setter)
    {

        this(setter.commandKey,
             setter.threadPoolKey,
             null,
             setter.commandPropertiesDefaults,
             setter.threadPoolPropertiesDefaults,
             null);
    }


    Command(CommandKey key, PoolKey threadPoolKey, Pool threadPool,
            HystrixCommandProperties.Setter commandPropertiesDefaults,
            ThreadPoolProperties.Setter threadPoolPropertiesDefaults,

            HystrixPropertiesStrategy propertiesStrategy)
    {
        super(key,
              threadPoolKey,
              threadPool,
              commandPropertiesDefaults,
              threadPoolPropertiesDefaults,
              propertiesStrategy);
    }


    public Command(CommandKey key, PoolKey threadPoolKey, Pool threadPool, HystrixPropertiesStrategy propertiesStrategy, HystrixCommandProperties.Setter commandPropertiesDefaults, ThreadPoolProperties.Setter threadPoolPropertiesDefaults, Pool threadPool1)
    {
        super(  key,
                threadPoolKey,
                threadPool,
                propertiesStrategy,
                commandPropertiesDefaults,
                threadPoolPropertiesDefaults,
                threadPool1);
    }


    final public static class Setter
    {

        protected CommandKey commandKey;

        protected PoolKey threadPoolKey;

        protected HystrixCommandProperties.Setter commandPropertiesDefaults;

        protected ThreadPoolProperties.Setter threadPoolPropertiesDefaults;


        public Setter andCommandKey(CommandKey commandKey)
        {
            this.commandKey = commandKey;
            return this;
        }


        public Setter andThreadPoolKey(PoolKey threadPoolKey)
        {
            this.threadPoolKey = threadPoolKey;
            return this;
        }


        public Setter andCommandPropertiesDefaults(
            HystrixCommandProperties.Setter commandPropertiesDefaults)
        {
            this.commandPropertiesDefaults = commandPropertiesDefaults;
            return this;
        }


        public Setter andThreadPoolPropertiesDefaults(
            ThreadPoolProperties.Setter threadPoolPropertiesDefaults)
        {
            this.threadPoolPropertiesDefaults = threadPoolPropertiesDefaults;
            return this;
        }


    }

    private final AtomicReference<Thread> executionThread = new AtomicReference<Thread>();

    private final AtomicBoolean interruptOnFutureCancel = new AtomicBoolean(false);


    protected T getFallback()
    {
        throw new UnsupportedOperationException("No fallback available.");
    }


    final protected Observable<T> getExecutionObservable()
    {
        return Observable.defer(new Func0<Observable<T>>()
        {
            @Override
            public Observable<T> call()
            {
                try {
                    return Observable.just(run());
                } catch (Throwable ex) {
                    return Observable.error(ex);
                }
            }
        }).doOnSubscribe(new Action0()
        {
            @Override
            public void call()
            {
                // Save thread on which we get subscribed so that we can interrupt it later if needed
                executionThread.set(Thread.currentThread());
            }
        });
    }


    final protected Observable<T> getFallbackObservable()
    {
        return Observable.defer(new Func0<Observable<T>>()
        {
            @Override
            public Observable<T> call()
            {
                try {
                    return Observable.just(getFallback());
                } catch (Throwable ex) {
                    return Observable.error(ex);
                }
            }
        });
    }


    protected abstract T run() throws Exception;


    public T execute()
    {
        try {
            return queue().get();
        } catch (Exception e) {
            try {
                throw new Exception(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    public Future<T> queue()
    {

        final Future<T> delegate = toObservable().toBlocking().toFuture();

        final Future<T> f = new Future<T>()
        {

            @Override
            public boolean cancel(boolean mayInterruptIfRunning)
            {
                if (delegate.isCancelled()) {
                    return false;
                }

                if (Command.this.getProperties()
                                .executionIsolationThreadInterruptOnFutureCancel()
                                .get()) {
                    /*
                     * The only valid transition here is false -> true. If there are two futures, say f1 and f2, created by this command
                     * (which is super-weird, but has never been prohibited), and calls to f1.cancel(true) and to f2.cancel(false) are
                     * issued by different threads, it's unclear about what value would be used by the time mayInterruptOnCancel is checked.
                     * The most consistent way to deal with this scenario is to say that if *any* cancellation is invoked with interruption,
                     * than that interruption request cannot be taken back.
                     */
                    interruptOnFutureCancel.compareAndSet(false, mayInterruptIfRunning);
                }

                final boolean res = delegate.cancel(interruptOnFutureCancel.get());

                if ( interruptOnFutureCancel.get()) {
                    final Thread t = executionThread.get();
                    if (t != null && !t.equals(Thread.currentThread())) {
                        t.interrupt();
                    }
                }

                return res;
            }


            @Override
            public boolean isCancelled()
            {
                return delegate.isCancelled();
            }


            @Override
            public boolean isDone()
            {
                return delegate.isDone();
            }


            @Override
            public T get() throws InterruptedException, ExecutionException
            {
                return delegate.get();
            }


            @Override
            public T get(long timeout, TimeUnit unit)
                throws InterruptedException, ExecutionException, TimeoutException
            {
                return delegate.get(timeout, unit);
            }
        };

        /* special handling of error states that throw immediately */
        if (f.isDone()) {
            try {
                f.get();
                return f;
            } catch (Exception e) {
                Throwable t = new Exception(e);
                if (t instanceof Exception) {
                    return f;
                } else if (t instanceof RuntimeException) {
                    RuntimeException hre = (RuntimeException) t;
                    switch (hre.getLocalizedMessage()) {
                        case " ":
                            // we don't throw these types from queue() only from queue().get() as they are execution errors
                            return f;
                        default:
                            // these are errors we throw from queue() as they as rejection type errors
                            throw hre;
                    }
                } else {
                    try {
                        throw new Exception();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }

        return f;
    }


    protected String getFallbackMethodName()
    {
        return "getFallback";
    }


    protected boolean isFallbackUserDefined()
    {
        Boolean containsFromMap = commandContainsFallback.get(commandState);
        if (containsFromMap != null) {
            return containsFromMap;
        } else {
            Boolean toInsertIntoMap;
            try {
                getClass().getDeclaredMethod("getFallback");
                toInsertIntoMap = true;
            } catch (NoSuchMethodException nsme) {
                toInsertIntoMap = false;
            }
            commandContainsFallback.put(new Setter().commandKey, toInsertIntoMap);
            return toInsertIntoMap;
        }
    }


    protected boolean commandIsScalar()
    {
        return true;
    }


}
