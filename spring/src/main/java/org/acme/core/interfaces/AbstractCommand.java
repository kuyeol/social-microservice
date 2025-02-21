package org.acme.core.interfaces;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.subjects.ReplaySubject;

abstract class AbstractCommand<T>
{

  protected Pool threadPool;

  protected PoolKey threadPoolKey;

  protected HystrixCommandProperties properties;

  private CommandKey commandKey;

  protected AtomicReference<CommandState> commandState = new AtomicReference<CommandState>(
    CommandState.NOT_STARTED );

  protected AtomicReference<ThreadState> threadState = new AtomicReference<ThreadState>( ThreadState.NOT_USING_THREAD );

  protected volatile boolean isResponseFromCache = false;

  protected volatile long commandStartTimestamp = -1L;

  private static ConcurrentHashMap<Class<?>, String> defaultNameCache = new ConcurrentHashMap<Class<?>, String>();

  protected static ConcurrentHashMap<CommandKey, Boolean> commandContainsFallback = new ConcurrentHashMap<CommandKey, Boolean>();

  protected enum TimedOutStatus
  {
    NOT_EXECUTED, COMPLETED, TIMED_OUT
  }

  protected enum CommandState
  {
    NOT_STARTED, OBSERVABLE_CHAIN_CREATED, USER_CODE_EXECUTED, UNSUBSCRIBED, TERMINAL
  }

  protected enum ThreadState
  {
    NOT_USING_THREAD, STARTED, UNSUBSCRIBED, TERMINAL
  }


  AbstractCommand(Pool threadPool , PoolKey threadPoolKey , HystrixCommandProperties properties ,
                  CommandKey commandKey)
  {
    this.threadPool    = threadPool;
    this.threadPoolKey = threadPoolKey;
    this.properties    = properties;
    this.commandKey    = commandKey;
  }


  public AbstractCommand(Object o , PoolKey threadPool , Object o1 , Object o2 ,
                         HystrixCommandProperties.Setter setter , Object o3 , Object o4 ,
                         Object o5 , Object o6 , Object o7 , Object o8 , Pool threadPool1 ,
                         PoolKey threadPoolKey , HystrixCommandProperties properties ,
                         CommandKey commandKey)
  {

    this.threadPool    = threadPool1;
    this.threadPoolKey = threadPoolKey;
    this.properties    = properties;
    this.commandKey    = commandKey;
  }


  public AbstractCommand(Object o , PoolKey threadPool , Object o1 , Object o2 , Object o3 ,
                         Object o4 , Object o5 , Object o6 , Object o7 , Object o8 , Object o9)
  {

  }


  public AbstractCommand(CommandKey key , PoolKey threadPoolKey , Pool threadPool ,
                         HystrixCommandProperties.Setter commandPropertiesDefaults ,
                         ThreadPoolProperties.Setter threadPoolPropertiesDefaults ,
                         HystrixPropertiesStrategy propertiesStrategy)
  {

  }


  /* package */
  static String getDefaultNameFromClass(Class<?> cls)
  {
    String fromCache = defaultNameCache.get( cls );
    if ( fromCache != null ) {
      return fromCache;
    }

    String name = cls.getSimpleName();
    if ( name.equals( "" ) ) {
      name = cls.getName();
      name = name.substring( name.lastIndexOf( '.' ) + 1 , name.length() );
    }
    defaultNameCache.put( cls , name );
    return name;
  }


  protected AbstractCommand(CommandKey key , PoolKey threadPoolKey , Pool threadPool ,
                            HystrixPropertiesStrategy propertiesStrategy ,
                            HystrixCommandProperties.Setter commandPropertiesDefaults ,
                            ThreadPoolProperties.Setter threadPoolPropertiesDefaults ,
                            Pool threadPool1)
  {

    this.commandKey    = initCommandKey( key , getClass() );
    this.threadPool    = threadPool1;
    this.properties    = initCommandProperties( this.commandKey ,
                                                propertiesStrategy ,
                                                commandPropertiesDefaults );
    this.threadPoolKey = initThreadPoolKey( threadPoolKey ,
                                            this.properties.executionIsolationThreadPoolKeyOverride()
                                                           .get() );
  }


  private static HystrixCommandProperties initCommandProperties(CommandKey commandKey ,
                                                                HystrixPropertiesStrategy propertiesStrategy ,
                                                                HystrixCommandProperties.Setter commandPropertiesDefaults)
  {
    if ( commandKey == null ) {
      return PropertiesFactory.getCommandProperties( commandKey , commandPropertiesDefaults );
    } else {

      // used for unit testing
      return propertiesStrategy.getCommandProperties( commandKey , commandPropertiesDefaults );
    }
  }


  private static PoolKey initThreadPoolKey(PoolKey threadPoolKey , String threadPoolKeyOverride)
  {
    if ( threadPoolKeyOverride == null ) {
      if ( threadPoolKey == null ) {

        return PoolKey.Factory.asKey( threadPoolKey.name() );
      } else {
        return threadPoolKey;
      }
    } else {
      return PoolKey.Factory.asKey( threadPoolKeyOverride );
    }
  }


  public HystrixCommandProperties getProperties()
  {
    return properties;
  }


  private static CommandKey initCommandKey(final CommandKey fromConstructor , Class<?> clazz)
  {
    if ( fromConstructor == null || fromConstructor.name().trim().equals( "" ) ) {
      final String keyName = getDefaultNameFromClass( clazz );
      return CommandKey.Factory.asKey( keyName );
    } else {
      return fromConstructor;
    }
  }


  public Observable<T> observe()
  {
    ReplaySubject<T>   subject            = ReplaySubject.create();
    final Subscription sourceSubscription = toObservable().subscribe( subject );

    return subject.doOnUnsubscribe( new Action0()
    {
      @Override
      public void call()
      {
        sourceSubscription.unsubscribe();
      }
    } );
  }


  public Observable<T> toObservable()
  {
    final AbstractCommand<T> _cmd = this;
    final Action0 terminateCommandCleanup = new Action0()
    {

      @Override
      public void call()
      {

      }
    };

    return null;
  }


}
