package org.ung.oauth;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.OAuth2AuthorizationURL;
import io.vertx.ext.auth.oauth2.OAuth2FlowType;
import io.vertx.ext.auth.oauth2.OAuth2Options;
import jakarta.inject.Inject;

import java.util.Arrays;

public class OAuth2API {

  //
  //public String authorizeURL(CusOAuth2AuthorizationURL params) {
  //  final JsonObject query = new JsonObject();
  //
  //  if (params.getAdditionalParameters() != null) {
  //    params.getAdditionalParameters().forEach(query::put);
  //  }
  //
  //  query.put("state", params.getState());
  //
  //  if (params.getScopes() != null) {
  //    // scopes have been passed as a list so the provider must generate the correct string for it
  //    query.put("scope", String.join(config.getScopeSeparator(), params.getScopes()));
  //  }
  //
  //  query.put("response_type", "code");
  //
  //  String clientId = config.getClientId();
  //
  //  if (clientId != null) {
  //    query.put("client_id", clientId);
  //  } else {
  //    if (config.getClientAssertionType() != null) {
  //      query.put("client_assertion_type", config.getClientAssertionType());
  //    }
  //    if (config.getClientAssertion() != null) {
  //      query.put("client_assertion", config.getClientAssertion());
  //    }
  //  }
  //
  //  final String path = config.getAuthorizationPath();
  //  final String url = path.charAt(0) == '/' ? config.getSite() + path : path;
  //
  //  return url + '?' + SimpleHttpClient.jsonToQuery(query);
  //}

  @Inject
  Vertx vertx;


  OAuth2Options credentials = new OAuth2Options()
    .setFlow(OAuth2FlowType.AUTH_CODE)
    .setClientId("<client-id>")
    .setClientSecret("<client-secret>")
    .setSite("https://api.oauth.com");

  OAuth2Auth oauth2 = OAuth2Auth.create(vertx, credentials);
  String authorization_uri = oauth2.authorizeURL(new OAuth2AuthorizationURL()
    .setRedirectUri("http://localhost:8080/callback")
    .setScopes(Arrays.asList("scope"))
    .setState("state"));

  private int  connectCount;


  @Inject
  HttpServer server;
public void getServer(){

var response =  vertx.createHttpServer().requestHandler();

response.putHeader("Location", authorization_uri)
    .setStatusCode(302)
  .end();

}

}
