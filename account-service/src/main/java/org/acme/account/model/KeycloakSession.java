

package org.acme.account.model;



import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.acme.account.represetion.identitymanagement.util.BruteForceProtector;
import org.acme.account.userprofile.UserProfileProvider;


public interface KeycloakSession extends AutoCloseable {




        KeycloakTransactionManager getTransactionManager();



        <T extends Provider> T getProvider(Class<T> clazz, String id);



        <T extends Provider> Set<String> listProviderIds(Class<T> clazz);

        <T extends Provider> Set<T> getAllProviders(Class<T> clazz);

        Class<? extends Provider> getProviderClass(String providerClassName);

        Object getAttribute(String attribute);

        <T> T getAttribute(String attribute, Class<T> clazz);
        default <T> T getAttributeOrDefault(String attribute, T defaultValue) {
            T value = (T) getAttribute(attribute);

            if (value == null) {
                return defaultValue;
            }

            return value;
        }

        Object removeAttribute(String attribute);
        void setAttribute(String name, Object value);

        Map<String, Object> getAttributes();

        void invalidate( InvalidationHandler.InvalidableObjectType type, Object... params);

        void enlistForClose(Provider provider);




        UserSessionProvider sessions();


        UserLoginFailureProvider loginFailures();


        @Override
        void close();

        KeycloakSessionFactory getKeycloakSessionFactory();

        UserProvider users();


        KeyManager keys();




        boolean isClosed();

        //UserProfileProvider getProvider( Class< UserProfileProvider> userProfileProviderClass );
        //
        //DatastoreProvider getProvider( Class< DatastoreProvider> datastoreProviderClass );


    //    <T extends Provider> T getProvider(Class<T> clazz);

        <T extends Provider>UserProfileProvider  getProvider( Class< UserProfileProvider> userProfileProviderClass );





}