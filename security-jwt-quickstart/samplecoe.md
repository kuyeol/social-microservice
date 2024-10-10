```java
/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.jose.jws;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.keycloak.common.util.Base64;
import org.keycloak.common.util.Base64Url;
import org.keycloak.crypto.SignatureSignerContext;
import org.keycloak.jose.jwk.JWK;
import org.keycloak.jose.jws.crypto.HMACProvider;
import org.keycloak.jose.jws.crypto.RSAProvider;
import org.keycloak.util.JsonSerialization;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class JWSBuilder {
    protected String type;
    protected String kid;
    protected String x5t;
    protected JWK jwk;
    protected List<X509Certificate> x5c;
    protected String contentType;
    protected byte[] contentBytes;

    public JWSBuilder type(String type) {
        this.type = type;
        return this;
    }

    public JWSBuilder kid(String kid) {
        this.kid = kid;
        return this;
    }

    public JWSBuilder x5t(String x5t) {
        this.x5t = x5t;
        return this;
    }

    public JWSBuilder jwk(JWK jwk) {
        this.jwk = jwk;
        return this;
    }

    public JWSBuilder x5c(List<X509Certificate> x5c) {
        this.x5c = x5c;
        return this;
    }

    public JWSBuilder contentType(String type) {
        this.contentType = type;
        return this;
    }

    public EncodingBuilder content(byte[] bytes) {
        this.contentBytes = bytes;
        return new EncodingBuilder();
    }

    public EncodingBuilder jsonContent(Object object) {
        try {
            this.contentBytes = JsonSerialization.writeValueAsBytes(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new EncodingBuilder();
    }


    protected String encodeHeader(String sigAlgName) {
        StringBuilder builder = new StringBuilder("{");

        if (org.keycloak.crypto.Algorithm.Ed25519.equals(sigAlgName) || org.keycloak.crypto.Algorithm.Ed448.equals(sigAlgName)) {
            builder.append("\"alg\":\"").append(org.keycloak.crypto.Algorithm.EdDSA).append("\"");
            builder.append(",\"crv\":\"").append(sigAlgName).append("\"");
        } else {
            builder.append("\"alg\":\"").append(sigAlgName).append("\"");
        }

        if (type != null) builder.append(",\"typ\" : \"").append(type).append("\"");
        if (kid != null) builder.append(",\"kid\" : \"").append(kid).append("\"");
        if (x5t != null) builder.append(",\"x5t\" : \"").append(x5t).append("\"");
        if (x5c != null && !x5c.isEmpty()) {
            builder.append(",\"x5c\" : [");
            for (int i = 0; i < x5c.size(); i++) {
                X509Certificate certificate = x5c.get(i);
                if (i > 0) {
                    builder.append(",");
                }
                try {
                    builder.append("\"").append(Base64.encodeBytes(certificate.getEncoded())).append("\"");
                } catch (CertificateEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            builder.append("]");
        }
        if (jwk != null) {
            try {
                builder.append(",\"jwk\" : ").append(JsonSerialization.mapper.writeValueAsString(jwk));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        if (contentType != null) builder.append(",\"cty\":\"").append(contentType).append("\"");
        builder.append("}");
        return Base64Url.encode(builder.toString().getBytes(StandardCharsets.UTF_8));
    }

    protected String encodeAll(StringBuilder encoding, byte[] signature) {
        encoding.append('.');
        if (signature != null) {
            encoding.append(Base64Url.encode(signature));
        }
        return encoding.toString();
    }

    protected void encode(Algorithm alg, byte[] data, StringBuilder encoding) {
        encode(alg.name(), data, encoding);
    }

    protected void encode(String sigAlgName, byte[] data, StringBuilder encoding) {
        encoding.append(encodeHeader(sigAlgName));
        encoding.append('.');
        encoding.append(Base64Url.encode(data));
    }

    protected byte[] marshalContent() {
        return contentBytes;
    }

    public class EncodingBuilder {

        public String sign(SignatureSignerContext signer) {
            kid = signer.getKid();

            StringBuilder buffer = new StringBuilder();
            byte[] data = marshalContent();
            encode(signer.getAlgorithm(), data, buffer);
            byte[] signature = null;
            try {
                signature = signer.sign(buffer.toString().getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return encodeAll(buffer, signature);
        }

        public String none() {
            StringBuilder buffer = new StringBuilder();
            byte[] data = marshalContent();
            encode(Algorithm.none, data, buffer);
            return encodeAll(buffer, null);
        }

        @Deprecated
        public String sign(Algorithm algorithm, PrivateKey privateKey) {
            StringBuilder buffer = new StringBuilder();
            byte[] data = marshalContent();
            encode(algorithm, data, buffer);
            byte[] signature = RSAProvider.sign(buffer.toString().getBytes(StandardCharsets.UTF_8), algorithm, privateKey);
            return encodeAll(buffer, signature);
        }

        @Deprecated
        public String rsa256(PrivateKey privateKey) {
            return sign(Algorithm.RS256, privateKey);
        }

        @Deprecated
        public String rsa384(PrivateKey privateKey) {
            return sign(Algorithm.RS384, privateKey);
        }

        @Deprecated
        public String rsa512(PrivateKey privateKey) {
            return sign(Algorithm.RS512, privateKey);
        }

        @Deprecated
        public String hmac256(byte[] sharedSecret) {
            StringBuilder buffer = new StringBuilder();
            byte[] data = marshalContent();
            encode(Algorithm.HS256, data, buffer);
            byte[] signature = HMACProvider.sign(buffer.toString().getBytes(StandardCharsets.UTF_8), Algorithm.HS256, sharedSecret);
            return encodeAll(buffer, signature);
        }

        @Deprecated
        public String hmac384(byte[] sharedSecret) {
            StringBuilder buffer = new StringBuilder();
            byte[] data = marshalContent();
            encode(Algorithm.HS384, data, buffer);
            byte[] signature = HMACProvider.sign(buffer.toString().getBytes(StandardCharsets.UTF_8), Algorithm.HS384, sharedSecret);
            return encodeAll(buffer, signature);
        }

        @Deprecated
        public String hmac512(byte[] sharedSecret) {
            StringBuilder buffer = new StringBuilder();
            byte[] data = marshalContent();
            encode(Algorithm.HS512, data, buffer);
            byte[] signature = HMACProvider.sign(buffer.toString().getBytes(StandardCharsets.UTF_8), Algorithm.HS512, sharedSecret);
            return encodeAll(buffer, signature);
        }

        @Deprecated
        public String hmac256(SecretKey sharedSecret) {
            StringBuilder buffer = new StringBuilder();
            byte[] data = marshalContent();
            encode(Algorithm.HS256, data, buffer);
            byte[] signature = HMACProvider.sign(buffer.toString().getBytes(StandardCharsets.UTF_8), Algorithm.HS256, sharedSecret);
            return encodeAll(buffer, signature);
        }

        @Deprecated
        public String hmac384(SecretKey sharedSecret) {
            StringBuilder buffer = new StringBuilder();
            byte[] data = marshalContent();
            encode(Algorithm.HS384, data, buffer);
            byte[] signature = HMACProvider.sign(buffer.toString().getBytes(StandardCharsets.UTF_8), Algorithm.HS384, sharedSecret);
            return encodeAll(buffer, signature);
        }

        @Deprecated
        public String hmac512(SecretKey sharedSecret) {
            StringBuilder buffer = new StringBuilder();
            byte[] data = marshalContent();
            encode(Algorithm.HS512, data, buffer);
            byte[] signature = HMACProvider.sign(buffer.toString().getBytes(StandardCharsets.UTF_8), Algorithm.HS512, sharedSecret);
            return encodeAll(buffer, signature);
        }
    }
}
```

---


# 2

---

```java
/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.keycloak.representations;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Claims parameter as described in the OIDC specification https://openid.net/specs/openid-connect-core-1_0.html#ClaimsParameter
 *
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class ClaimsRepresentation {

    @JsonProperty("id_token")
    private Map<String, ClaimValue> idTokenClaims;

    @JsonProperty("userinfo")
    private Map<String, ClaimValue> userinfoClaims;

    public Map<String, ClaimValue> getIdTokenClaims() {
        return idTokenClaims;
    }

    public void setIdTokenClaims(Map<String, ClaimValue> idTokenClaims) {
        this.idTokenClaims = idTokenClaims;
    }

    public Map<String, ClaimValue> getUserinfoClaims() {
        return userinfoClaims;
    }

    public void setUserinfoClaims(Map<String, ClaimValue> userinfoClaims) {
        this.userinfoClaims = userinfoClaims;
    }

    // Helper methods

    /**
     *
     * @param claimName
     * @param ctx Whether we ask for claim to be presented in idToken or userInfo
     * @return true if claim is presented in the claims parameter either as "null" claim (See OIDC specification for definition of null claim) or claim with some value
     */
    public boolean isPresent(String claimName, ClaimContext ctx) {
        if (ctx == ClaimContext.ID_TOKEN) {
            return idTokenClaims != null && idTokenClaims.containsKey(claimName);
        } else if (ctx == ClaimContext.USERINFO){
            return userinfoClaims != null && userinfoClaims.containsKey(claimName);
        } else {
            throw new IllegalArgumentException("Invalid claim context");
        }
    }

    /**
     *
     * @param claimName
     * @param ctx Whether we ask for claim to be presented in idToken or userInfo
     * @return true if claim is presented in the claims parameter as "null" claim (See OIDC specification for definition of null claim)
     */
    public boolean isPresentAsNullClaim(String claimName, ClaimContext ctx) {
        if (!isPresent(claimName, ctx)) return false;

        if (ctx == ClaimContext.ID_TOKEN) {
            return idTokenClaims.get(claimName) == null;
        } else if (ctx == ClaimContext.USERINFO){
            return userinfoClaims.get(claimName) == null;
        } else {
            throw new IllegalArgumentException("Invalid claim context");
        }
    }

    /**
     *
     * @param claimName
     * @param ctx Whether we ask for claim to be presented in idToken or userInfo
     * @param claimType claimType class
     * @return Claim value
     */
    public <CLAIM_TYPE> ClaimValue<CLAIM_TYPE> getClaimValue(String claimName, ClaimContext ctx, Class<CLAIM_TYPE> claimType) {
        if (!isPresent(claimName, ctx)) return null;

        if (ctx == ClaimContext.ID_TOKEN) {
            return (ClaimValue<CLAIM_TYPE>) idTokenClaims.get(claimName);
        } else if (ctx == ClaimContext.USERINFO){
            return (ClaimValue<CLAIM_TYPE>) userinfoClaims.get(claimName);
        } else {
            throw new IllegalArgumentException("Invalid claim context");
        }
    }

    public enum ClaimContext {
        ID_TOKEN, USERINFO
    }

    /**
     * @param <CLAIM_TYPE> Specifies the type of the claim
     */
    public static class ClaimValue<CLAIM_TYPE> {

        private Boolean essential;

        private CLAIM_TYPE value;

        private List<CLAIM_TYPE> values;

        public Boolean getEssential() {
            return essential;
        }

        public boolean isEssential() {
            return essential != null && essential;
        }

        public void setEssential(Boolean essential) {
            this.essential = essential;
        }

        public CLAIM_TYPE getValue() {
            return value;
        }

        public void setValue(CLAIM_TYPE value) {
            this.value = value;
        }

        public List<CLAIM_TYPE> getValues() {
            return values;
        }

        public void setValues(List<CLAIM_TYPE> values) {
            this.values = values;
        }
    }
}
```



---

# 3
---


```java

/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keycloak.protocol.oidc.mappers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.oidc.OIDCLoginProtocol;
import org.keycloak.protocol.oidc.OIDCWellKnownProvider;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.IDToken;
import org.keycloak.util.JsonSerialization;
import org.keycloak.util.TokenUtil;

import com.fasterxml.jackson.databind.JsonNode;

public class ClaimsParameterTokenMapper extends AbstractOIDCProtocolMapper implements OIDCIDTokenMapper, UserInfoTokenMapper {

    public static final String PROVIDER_ID = "oidc-claims-param-token-mapper";

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();

    static {
        OIDCAttributeMapperHelper.addIncludeInTokensConfig(configProperties, ClaimsParameterTokenMapper.class);
    }

    @Override
    public String getDisplayCategory() {
        return TOKEN_MAPPER_CATEGORY;
    }

    @Override
    public String getDisplayType() {
        return "Claims parameter Token";
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getHelpText() {
        return "Claims specified by Claims parameter are put into tokens.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    protected void setClaim(IDToken token, ProtocolMapperModel mappingModel, UserSessionModel userSession, KeycloakSession keycloakSession, ClientSessionContext clientSessionCtx) {
        String claims = clientSessionCtx.getClientSession().getNote(OIDCLoginProtocol.CLAIMS_PARAM);
        if (claims == null) return;

        if (TokenUtil.TOKEN_TYPE_ID.equals(token.getType())) {
            // ID Token
            putClaims("id_token", claims, token, mappingModel, userSession);
        } else {
            // UserInfo
            putClaims("userinfo", claims, token, mappingModel, userSession);
        }
    }

    private void putClaims(String tokenType, String claims, IDToken token, ProtocolMapperModel mappingModel, UserSessionModel userSession) {
        JsonNode requestParams = null;

        try {
            requestParams = JsonSerialization.readValue(claims, JsonNode.class);
        } catch (IOException e) {
            return;
        }
        if (!requestParams.has(tokenType)) return;

        JsonNode tokenNode = requestParams.findValue(tokenType);

        OIDCWellKnownProvider.DEFAULT_CLAIMS_SUPPORTED.stream()
            .filter(i->tokenNode.has(i))
            .filter(i->tokenNode.findValue(i).has("essential"))
            .filter(i->tokenNode.findValue(i).findValue("essential").isBoolean())
            .filter(i->tokenNode.findValue(i).findValue("essential").asBoolean())
            .forEach(i -> {
                    // insert claim to Token
                    // "aud", "sub", "iss", "auth_time", "acr" are set as default.
                    // "name", "given_name", "family_name", "preferred_username", "email" need to be set explicitly using existing mapper.
                    if (i.equals(IDToken.NAME)) {
                        FullNameMapper fullNameMapper = new FullNameMapper();
                        fullNameMapper.setClaim(token, mappingModel, userSession);
                    } else if (i.equals(IDToken.GIVEN_NAME)) {
                        UserAttributeMapper userPropertyMapper = new UserAttributeMapper();
                        userPropertyMapper.setClaim(token, UserAttributeMapper.createClaimMapper("requested firstName", "firstName", IDToken.GIVEN_NAME, "String", false, true, false), userSession);
                    } else if (i.equals(IDToken.FAMILY_NAME)) {
                        UserAttributeMapper userPropertyMapper = new UserAttributeMapper();
                        userPropertyMapper.setClaim(token, UserAttributeMapper.createClaimMapper("requested lastName", "lastName", IDToken.FAMILY_NAME, "String", false, true, false), userSession);
                    } else if (i.equals(IDToken.PREFERRED_USERNAME)) {
                        UserAttributeMapper userPropertyMapper = new UserAttributeMapper();
                        userPropertyMapper.setClaim(token, UserAttributeMapper.createClaimMapper("requested username", "username", IDToken.PREFERRED_USERNAME, "String", false, true, false), userSession);
                    } else if (i.equals(IDToken.EMAIL)) {
                        UserAttributeMapper userPropertyMapper = new UserAttributeMapper();
                        userPropertyMapper.setClaim(token, UserAttributeMapper.createClaimMapper("requested email", "email", IDToken.EMAIL, "String", false, true, false), userSession);
                    }
            });
    }

    public static ProtocolMapperModel createMapper(String name, boolean idToken, boolean userInfo) {
        ProtocolMapperModel mapper = new ProtocolMapperModel();
        mapper.setName(name);
        mapper.setProtocolMapper(PROVIDER_ID);
        mapper.setProtocol(OIDCLoginProtocol.LOGIN_PROTOCOL);
        Map<String, String> config = new HashMap<String, String>();
        if (idToken) config.put(OIDCAttributeMapperHelper.INCLUDE_IN_ID_TOKEN, "true");
        if (userInfo) config.put(OIDCAttributeMapperHelper.INCLUDE_IN_USERINFO, "true");
        mapper.setConfig(config);
        return mapper;
    }

}

```