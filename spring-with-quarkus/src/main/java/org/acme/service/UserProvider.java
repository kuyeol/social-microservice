package org.acme.service;

import java.util.Map;
import java.util.stream.Stream;
import org.acme.dto.TDTO;
import org.acme.dto.UserModel;

public interface UserProvider {

    TDTO getUserById(UserModel model, String id);

    Stream<TDTO> searchForUserStream(UserModel realm, Map<String, String> params, Integer firstResult,
        Integer maxResults);

    default Stream<TDTO> searchForUserStream(UserModel realm, Map<String, String> params) {
        return searchForUserStream(realm, params, null, null);
    }


    Stream<TDTO> searchForUserStream(UserModel realm, Integer firstResult, Integer maxResults);

    default Stream<TDTO> searchForUserStream(UserModel realm) {
        return searchForUserStream(realm, null, null);
    }


}
