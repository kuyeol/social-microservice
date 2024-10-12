package org.acme.security.crypto;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.acme.security.util.BouncyIntegration;
import org.jboss.logging.Logger;

public class CryptoIntegration {
  protected static final Logger logger = Logger.getLogger(CryptoIntegration.class);

  private static final Object lock = new Object();


  private static volatile CryptoProvider cryptoProvider;

  public static void init(ClassLoader classLoader) {
    if (cryptoProvider == null) {
      synchronized (lock) {
        if (cryptoProvider == null) {
          cryptoProvider = detectProvider(classLoader);
          logger.debugv("java security provider: {0}", BouncyIntegration.PROVIDER);

        }
      }
    }

    if (logger.isTraceEnabled()) {
      logger.tracef("dumpJavaSecurityProviders()");
      logger.tracef("dumpSecurityProperties()");
    }
  }

  public static CryptoProvider getProvider() {
    if (cryptoProvider == null) {
      throw new IllegalStateException("Illegal state. Please init first before obtaining provider");
    }
    return cryptoProvider;
  }

  private static CryptoProvider detectProvider(ClassLoader classLoader) {
    List<CryptoProvider> foundProviders = StreamSupport.stream(
        ServiceLoader.load(CryptoProvider.class, classLoader).spliterator(), false)
      .collect(Collectors.toList());

    if (foundProviders.isEmpty()) {
      throw new IllegalStateException("Not able to load any cryptoProvider with the classLoader: " + classLoader);
    } else if (foundProviders.size() > 1) {
      throw new IllegalStateException("Multiple crypto providers loaded with the classLoader: " + classLoader +
        ". Make sure only one cryptoProvider available on the classpath. Available providers: " + foundProviders);
    } else {
      logger.debugf("Detected crypto provider: %s", foundProviders.get(0).getClass().getName());
      return foundProviders.get(0);
    }
  }


}
