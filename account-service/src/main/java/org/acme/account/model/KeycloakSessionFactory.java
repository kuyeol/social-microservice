

package org.acme.account.model;



import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface KeycloakSessionFactory extends org.acme.account.model.ProviderEventManager, org.acme.account.model.InvalidationHandler
{

    KeycloakSession create();




    <T extends Provider> ProviderFactory<T> getProviderFactory(Class<T> clazz);

    <T extends Provider> ProviderFactory<T> getProviderFactory(Class<T> clazz, String id);


    /**
     * Returns stream of provider factories for the given provider.
     * @param clazz {@code Class<? extends Provider>}
     * @return {@code Stream<ProviderFactory>} Stream of provider factories. Never returns {@code null}.
     */
    Stream<ProviderFactory> getProviderFactoriesStream(Class<? extends Provider> clazz);
    
    long getServerStartupTimestamp();

    void close();
}
