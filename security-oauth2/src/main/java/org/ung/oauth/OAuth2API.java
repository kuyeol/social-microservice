package org.ung.oauth;

import io.vertx.core.json.JsonObject;

public class OAuth2API {


  public String authorizeURL(OAuth2AuthorizationURL params) {
    final JsonObject query = new JsonObject();

    if (params.getAdditionalParameters() != null) {
      params
          .getAdditionalParameters()
          .forEach(query::put);
    }

    query.put("state", params.getState());

    if (params.getScopes() != null) {
      // scopes have been passed as a list so the provider must generate the correct string for it
      query
          .put("scope", String.join(config.getScopeSeparator(), params.getScopes()));
    }

    query
        .put("response_type", "code");

    String clientId = config.getClientId();

    if (clientId != null) {
      query
          .put("client_id", clientId);
    } else {
      if (config.getClientAssertionType() != null) {
        query
            .put("client_assertion_type", config.getClientAssertionType());
      }
      if (config.getClientAssertion() != null) {
        query
            .put("client_assertion", config.getClientAssertion());
      }
    }

    final String path = config.getAuthorizationPath();
    final String url = path.charAt(0) == '/' ? config.getSite() + path : path;

    return url + '?' + SimpleHttpClient.jsonToQuery(query);
  }
}
