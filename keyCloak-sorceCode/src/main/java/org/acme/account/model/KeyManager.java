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

package org.acme.account.model;



import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.acme.account.util.KeyUse;
import org.acme.account.util.KeyWrapper;
import org.acme.account.util.RsaKeyMetadata;
import org.acme.account.util.SecretKeyMetadata;


/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public interface KeyManager {

    KeyWrapper getActiveKey( KeyUse use, String algorithm);

    KeyWrapper getKey( String kid, KeyUse use, String algorithm);

    Stream<KeyWrapper> getKeysStream();


    Stream< KeyWrapper > getKeysStream( KeyUse use, String algorithm);

    @Deprecated
    ActiveRsaKey getActiveRsaKey();

    @Deprecated
    PublicKey getRsaPublicKey(String kid);

    @Deprecated
    Certificate getRsaCertificate( String kid);

    @Deprecated
    List< RsaKeyMetadata > getRsaKeys();

    @Deprecated
    ActiveHmacKey getActiveHmacKey();

    @Deprecated
    SecretKey getHmacSecretKey( String kid);

    @Deprecated
    List< SecretKeyMetadata > getHmacKeys();

    @Deprecated
    ActiveAesKey getActiveAesKey();

    @Deprecated
    SecretKey getAesSecretKey( String kid);

    @Deprecated
    List<SecretKeyMetadata> getAesKeys();

    class ActiveRsaKey {
        private final String kid;
        private final PrivateKey privateKey;
        private final PublicKey publicKey;
        private final X509Certificate certificate;

        public ActiveRsaKey(String kid, PrivateKey privateKey, PublicKey publicKey, X509Certificate certificate) {
            this.kid = kid;
            this.privateKey = privateKey;
            this.publicKey = publicKey;
            this.certificate = certificate;
        }

        public ActiveRsaKey(KeyWrapper keyWrapper) {
            this(keyWrapper.getKid(), (PrivateKey) keyWrapper.getPrivateKey(), (PublicKey) keyWrapper.getPublicKey(), keyWrapper.getCertificate());
        }

        public String getKid() {
            return kid;
        }

        public PrivateKey getPrivateKey() {
            return privateKey;
        }

        public PublicKey getPublicKey() {
            return publicKey;
        }

        public X509Certificate getCertificate() {
            return certificate;
        }
    }

    class ActiveHmacKey {
        private final String kid;
        private final SecretKey secretKey;

        public ActiveHmacKey(String kid, SecretKey secretKey) {
            this.kid = kid;
            this.secretKey = secretKey;
        }

        public String getKid() {
            return kid;
        }

        public SecretKey getSecretKey() {
            return secretKey;
        }
    }

    class ActiveAesKey {
        private final String kid;
        private final SecretKey secretKey;

        public ActiveAesKey(String kid, SecretKey secretKey) {
            this.kid = kid;
            this.secretKey = secretKey;
        }

        public String getKid() {
            return kid;
        }

        public SecretKey getSecretKey() {
            return secretKey;
        }
    }


}
