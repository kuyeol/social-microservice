package org.acme.springouauth2.authetication;

import java.util.HashMap;
import java.util.Map;
import org.springframework.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

public class GrantTypeAuthenticationConverter  implements AuthenticationConverter {


  @Nullable
  @Override
  public Authentication convert(HttpServletRequest request) {
    // grant_type (REQUIRED)
    // grant_type (REQUIRED)
    String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
    if (!"urn:ietf:params:oauth:grant-type:custom_code".equals(grantType)) {
      return null;
    }

    Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();

    MultiValueMap<String, String> parameters = getParameters(request);

    // code (REQUIRED)
    String code = parameters.getFirst(OAuth2ParameterNames.CODE);
    if (!StringUtils.hasText(code) ||
        parameters.get(OAuth2ParameterNames.CODE).size() != 1) {
      throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_REQUEST);
    }

    Map<String, Object> additionalParameters = new HashMap<>();
    parameters.forEach((key, value) -> {
      if (!key.equals(OAuth2ParameterNames.GRANT_TYPE) &&
          !key.equals(OAuth2ParameterNames.CLIENT_ID) &&
          !key.equals(OAuth2ParameterNames.CODE)) {
        additionalParameters.put(key, value.get(0));
      }
    });


    return new OAuth2AuthorizationCodeAuthenticationToken(code,clientPrincipal,additionalParameters);
  }

  private MultiValueMap<String, String> getParameters(HttpServletRequest request) {

    Map<String, String[]> parameterMap = request.getParameterMap();

    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());

    parameterMap.forEach((key, values) -> {
      if(values.length > 0) {
        for (String value:values){
          parameters.add(key, value);
        }
      }
    });
    return parameters;

  }
}
