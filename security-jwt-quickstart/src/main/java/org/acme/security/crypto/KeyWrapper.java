package org.acme.security.crypto;

import java.util.HashMap;
import java.util.List;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Map;
import org.acme.security.constants.KeyType;
import org.acme.security.constants.KeyUse;

public class KeyWrapper {


    private static final Map<String, String> DEFAULT_ALGORITHM_BY_TYPE = new HashMap<>();

    static {
        //backwards compatibility: RSA keys without "alg" field set are considered RS256
        DEFAULT_ALGORITHM_BY_TYPE.put(KeyType.RSA, Algorithm.RS256);
    }

    private String providerId;
    private long providerPriority;
    private String kid;
    private String algorithm;
    private String type;
    private KeyUse use;
    private KeyStatus status;
    private SecretKey secretKey;
    private Key publicKey;
    private Key privateKey;
    private X509Certificate certificate;
    private List<X509Certificate> certificateChain;
    private boolean isDefaultClientCertificate;
    private String curve;

    public KeyWrapper cloneKey() {
        KeyWrapper key = new KeyWrapper();
        key.providerId = this.providerId;
        key.providerPriority = this.providerPriority;
        key.kid = this.kid;
        key.algorithm = this.algorithm;
        key.type = this.type;
        key.use = this.use;
        key.status = this.status;
        key.secretKey = this.secretKey;
        key.publicKey = this.publicKey;
        key.privateKey = this.privateKey;
        key.certificate = this.certificate;
        key.curve = this.curve;
        if (this.certificateChain != null) {
            key.certificateChain = new ArrayList<>(this.certificateChain);
        }
        key.isDefaultClientCertificate = this.isDefaultClientCertificate;
        return key;
    }


    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public long getProviderPriority() {
        return providerPriority;
    }

    public void setProviderPriority(long providerPriority) {
        this.providerPriority = providerPriority;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public KeyUse getUse() {
        return use;
    }

    public void setUse(KeyUse use) {
        this.use = use;
    }

    public KeyStatus getStatus() {
        return status;
    }

    public void setStatus(KeyStatus status) {
        this.status = status;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public Key getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(Key publicKey) {
        this.publicKey = publicKey;
    }

    public Key getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(Key privateKey) {
        this.privateKey = privateKey;
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }

    public List<X509Certificate> getCertificateChain() {
        return certificateChain;
    }

    public void setCertificateChain(List<X509Certificate> certificateChain) {
        this.certificateChain = certificateChain;
    }

    public boolean isDefaultClientCertificate() {
        return isDefaultClientCertificate;
    }

    public void setDefaultClientCertificate(boolean defaultClientCertificate) {
        isDefaultClientCertificate = defaultClientCertificate;
    }

    public String getCurve() {
        return curve;
    }

    public void setCurve(String curve) {
        this.curve = curve;
    }
}
