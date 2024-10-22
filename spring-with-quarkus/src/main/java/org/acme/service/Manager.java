package org.acme.service;

import org.acme.dto.UserModel;

public class Manager {

    public UserModel getUserById(UserModel model, String id) {


        LookupProvider provider = getProviderInstance(model,id, LookupProvider.class);
        if (provider == null) return null;

        return provider.getUserById(model, id);
    }

    protected <T> T getProviderInstance(UserModel model, String providerId, Class<T> capabilityInterface) {
        return getProviderInstance(model, providerId, capabilityInterface);

    }
}