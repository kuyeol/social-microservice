package org.ung.oauth;


import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.json.annotations.JsonGen;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@DataObject
@JsonGen(publicConverter = false)
public class CusOAuth2AuthorizationURL {

  private List<String> scopes;

  private String state;

  private Map<String, String> additionalParameters;

  public CusOAuth2AuthorizationURL putAdditionalParameter(String key, String value) {
    if (value == null) {

      if (this.additionalParameters != null) {
        this.additionalParameters.remove(key);
      }

      return this;
    }

    if (this.additionalParameters == null) {
      this.additionalParameters = new HashMap<>();
    }

    this.additionalParameters.put(key, value);

    return this;
  }


  public Map<String, String> getAdditionalParameters() {

    return additionalParameters;
  }


  public CusOAuth2AuthorizationURL setAdditionalParameters(Map<String, String> additionalParameters) {
    this.additionalParameters = additionalParameters;

    return this;
  }


  public CusOAuth2AuthorizationURL() {

  }


  public CusOAuth2AuthorizationURL(CusOAuth2AuthorizationURL other) {

    this.state = other.state;

    if (other.scopes != null) {
      this.scopes = new ArrayList<>(other.scopes);
    }
    if (other.additionalParameters != null) {
      this.additionalParameters = new HashMap<>(other.additionalParameters);
    }
  }


  public CusOAuth2AuthorizationURL(JsonObject json) {

    setState(json.getString("state"));
    JsonArray scopes = json.getJsonArray("scopes");
    if (scopes != null) {
      scopes.forEach(scope ->
        addScope((String) scope)
      );
    }
    JsonObject additionalParameters = json.getJsonObject("additionalParameters");
    if (additionalParameters != null) {
      additionalParameters.forEach(entry ->
        putAdditionalParameter(entry.getKey(), (String) entry.getValue()));
    }
  }


  public String getRedirectionUri() {

    return additionalParameters == null ? null : additionalParameters.get("redirect_uri");
  }


  public CusOAuth2AuthorizationURL setRedirectionUri(String redirect_uri) {

    putAdditionalParameter("redirect_uri", "".equals(redirect_uri) ? null : redirect_uri);

    return this;

  }


  public List<String> getScope() {

    return scopes;
  }


  public CusOAuth2AuthorizationURL setScopes(List<String> scopes) {

    this.scopes = scopes;

    return this;

  }


  public CusOAuth2AuthorizationURL addScope(String scope) {

    if (this.scopes == null) {
      this.scopes = new ArrayList<>();
    }
    this.scopes.add(scope);

    return this;
  }


  public String getState() {

    return state;
  }


  public CusOAuth2AuthorizationURL setState(String state) {

    this.state = state;

    return this;
  }


  public String getCodeChallenge() {

    return additionalParameters == null ? null : additionalParameters.get("code_challenge");
  }

  public CusOAuth2AuthorizationURL setCodeChallenge(String code_challenge) {
    putAdditionalParameter("code_challenge", code_challenge);

    return this;
  }

  public String getCodeChallengeMethod() {

    return additionalParameters == null ? null : getAdditionalParameters().get("code_challenge_method");
  }


  public CusOAuth2AuthorizationURL setCodeChallengeMethod(String code_challenge_method) {
    putAdditionalParameter("code_challenge_method", code_challenge_method);

    return this;
  }

  public String getPrompt() {

    return additionalParameters == null ? null : getAdditionalParameters().get("prompt");
  }


  public CusOAuth2AuthorizationURL setPrompt(String prompt) {

    putAdditionalParameter("prompt", prompt);

    return this;
  }


  public String getLoginHint() {
    return additionalParameters == null ? null : getAdditionalParameters().get("login_hint");
  }

  public CusOAuth2AuthorizationURL setLoginHint(String login_hint) {
    putAdditionalParameter("login_hint", login_hint);

    return this;
  }


  public JsonObject toJson() {
    final JsonObject json = new JsonObject();

    if (state != null) {
      json.put("state", state);
    }
    if (scopes != null) {
      json.put("scopes", new JsonArray(scopes));
    }
    if (additionalParameters != null) {
      json.put("additionalParameters", new JsonObject((Map) additionalParameters));
    }

    return json;
  }


  @Override
  public String toString() {

    return toJson().encode();
  }

  public CusOAuth2AuthorizationURL setRedirectUri(String url)
  {
    putAdditionalParameter("redirect_uri","".equals(url) ? null : url);
    return this;
  }
}







