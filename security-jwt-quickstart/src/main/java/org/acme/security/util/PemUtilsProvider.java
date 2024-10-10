package org.acme.security.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public abstract class PemUtilsProvider {

    /**
     * Decode a X509 Certificate from a PEM string
     *
     * @param cert
     * @return
     * @throws Exception
     */
    public X509Certificate decodeCertificate( String cert) {
        if (cert == null) {
            return null;
        }

        try {
            byte[] der = pemToDer(cert);
            ByteArrayInputStream bis = new ByteArrayInputStream( der);
            return DerUtils.decodeCertificate( bis);
        } catch (Exception e) {
            throw new PemException( e);
        }
    }


    /**
     * Decode a Public Key from a PEM string
     *
     * @param pem
     * @return
     * @throws Exception
     */
    public PublicKey decodePublicKey( String pem) {
        return decodePublicKey(pem, "RSA");
    }

    /**
     * Decode a Public Key from a PEM string
     * @param pem The pem encoded pblic key
     * @param type The type of the key (RSA, EC,...)
     * @return The public key or null
     */
    public PublicKey decodePublicKey(String pem, String type) {
        if (pem == null) {
            return null;
        }

        try {
            byte[] der = pemToDer(pem);
            return DerUtils.decodePublicKey(der, type);
        } catch (Exception e) {
            throw new PemException(e);
        }
    }


    /**
     * Decode a Private Key from a PEM string
     *
     * @param pem
     * @return
     * @throws Exception
     */
    public abstract PrivateKey decodePrivateKey( String pem);


    /**
     * Encode a Key to a PEM string
     *
     * @param key
     * @return
     * @throws Exception
     */
    public String encodeKey( Key key) {
        return encode(key);
    }


    /**
     * Encode a X509 Certificate to a PEM string
     *
     * @param certificate
     * @return
     */
    public String encodeCertificate( Certificate certificate) {
        return encode(certificate);
    }

    public byte[] pemToDer(String pem) {
        try {
            pem = removeBeginEnd(pem);
            return Base64.decode( pem);
        } catch (IOException ioe) {
            throw new PemException(ioe);
        }
    }

    public String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    public String generateThumbprint(String[] certChain, String encoding) throws NoSuchAlgorithmException {
        return Base64Url.encode( generateThumbprintBytes( certChain, encoding));
    }

    private byte[] generateThumbprintBytes(String[] certChain, String encoding) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance( encoding).digest( pemToDer( certChain[0]));
    }

    protected abstract String encode(Object obj);
}
