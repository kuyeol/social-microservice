package org.ung.oauth;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.List;
import java.util.Map;

@DataObject
public class OAuth2AuthorizationURL {

  private List<String> scopes;
  private String state;
  private Map<String, String> additionalParameters;

  public OAuth2AuthorizationURL() {
  }

  public OAuth2AuthorizationURL(JsonObject json) {
    setState(json.getString("state"));
    JsonArray scopes = json.getJsonArray("scopes");
    if (scopes != null) {
      scopes.forEach(scope -> {
        addScope((String) scope);
      });
    }
    JsonObject additionalParameters = json.getJsonObject("additionalParameters");
    if (additionalParameters != null) {
      additionalParameters.forEach(entry -> {
        putAdditionalParameter(entry.getKey(),(String) entry.getValue());
      });
    }
  }

  //todo: workPoint


  private void putAdditionalParameter(String key, String value) {
  }

  private void addScope(String scope) {
  }

  private void setState(String state) {
  }
}







