package org.acme.security.util;


import org.acme.security.crypto.CryptoIntegration;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;


public class PemUtil {



    public static final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
    public static final String END_CERT = "-----END CERTIFICATE-----";

    public static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
    public static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";
    public static final String BEGIN_RSA_PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----";
    public static final String END_RSA_PRIVATE_KEY = "-----END RSA PRIVATE KEY-----";


    public static X509Certificate decodeCertificate(String cert) {
        return CryptoIntegration.getProvider().getPemUtils().decodeCertificate(cert);
    }


    /**
     * Decode a Public Key from a PEM string
     *
     * @param pem
     * @return
     * @throws Exception
     */
    public static PublicKey decodePublicKey(String pem) {
        return CryptoIntegration.getProvider().getPemUtils().decodePublicKey(pem);
    }

    /**
     * Decode a Public Key from a PEM string
     * @param pem The pem encoded pblic key
     * @param type The type of the key (RSA, EC,...)
     * @return The public key or null
     */
    public static PublicKey decodePublicKey(String pem, String type){
        return CryptoIntegration.getProvider().getPemUtils().decodePublicKey(pem, type);
    }


    /**
     * Decode a Private Key from a PEM string
     *
     * @param pem
     * @return
     * @throws Exception
     */
    public static PrivateKey decodePrivateKey(String pem){
        return CryptoIntegration.getProvider().getPemUtils().decodePrivateKey(pem);
    }


    /**
     * Encode a Key to a PEM string
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encodeKey(Key key){
        return CryptoIntegration.getProvider().getPemUtils().encodeKey(key);
    }

    /**
     * Encode a X509 Certificate to a PEM string
     *
     * @param certificate
     * @return
     */
    public static String encodeCertificate(Certificate certificate){
        return CryptoIntegration.getProvider().getPemUtils().encodeCertificate(certificate);
    }

    public static byte[] pemToDer(String pem){
        return CryptoIntegration.getProvider().getPemUtils().pemToDer( pem);
    }





    public static String removeBeginEnd(String pem){
        return CryptoIntegration.getProvider().getPemUtils().removeBeginEnd(pem);
    }

    public static String addPrivateKeyBeginEnd(String privateKeyPem) {
        return new StringBuilder(PemUtil.BEGIN_PRIVATE_KEY + "\n")
                .append(privateKeyPem)
                .append("\n" + PemUtil.END_PRIVATE_KEY)
                .toString();
    }

    public static String addRsaPrivateKeyBeginEnd(String privateKeyPem) {
        return new StringBuilder(PemUtil.BEGIN_RSA_PRIVATE_KEY + "\n")
                .append(privateKeyPem)
                .append("\n" + PemUtil.END_RSA_PRIVATE_KEY)
                .toString();
    }

    public static String generateThumbprint(String[] certChain, String encoding) throws NoSuchAlgorithmException{
        return CryptoIntegration.getProvider().getPemUtils().generateThumbprint(certChain, encoding);
    }



}
