package org.acme.security.jwt;

import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.smallrye.jwt.runtime.auth.JsonWebTokenCredential;
import io.smallrye.jwt.algorithm.ContentEncryptionAlgorithm;
import io.smallrye.jwt.algorithm.KeyEncryptionAlgorithm;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.crypto.Cipher;
import org.acme.security.constants.KeyType;
import org.acme.security.constants.KeyUse;
import org.acme.security.crypto.ECGen;
import org.acme.security.util.Base64Url;
import org.acme.security.util.KeyUtils;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import static io.quarkus.arc.ComponentsProvider.LOG;
import static org.acme.security.util.JWKUtil.toIntegerBytes;

@Path("/jca")
@RequestScoped
public class JwtBuilder {

  private final String ALGORITHM = "RSA";
  protected String kid;

  protected String algorithm;
  public static final KeyUse DEFAULT_PUBLIC_KEY_USE = KeyUse.SIG;

  @Inject
  SecurityIdentity identity;


  @Produces
  @RequestScoped
  JsonWebTokenCredential currentToken() {
    identity.getPrincipal();
    JsonWebTokenCredential cred = identity.getCredential(JsonWebTokenCredential.class);
    if (cred == null || cred.getToken() == null) {
      LOG.trace("JsonWebToken is null");
      cred = new JsonWebTokenCredential("adsfsdf");
    }
    System.out.println(cred.getToken());
    System.out.println(cred.getType());
    return cred;
  }


  @GET
  @Path("TO")
  @Produces(MediaType.TEXT_PLAIN)
  public String adsfa() {


    return currentToken().getToken();
  }


  @GET
  @Path("listProviders")
  public String listProviders() {
    return Arrays.asList(Security.getProviders()).stream()
      .filter(p -> (p.getName().equals("BC") || p.getName().equals("SunPKCS11")))
      .map(p -> p.getName()).collect(Collectors.joining(","));
  }


  @GET
  @Path("generateEcDsaKeyPair")
  public String generateEcDsaKeyPair() throws Exception {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
    keyPairGenerator.generateKeyPair();
    String aa = keyPairGenerator.getAlgorithm();
    return "success" + aa + keyPairGenerator;
  }


  @GET
  @Path("generateEcKeyPair")
  public String generateEcKeyPair() throws Exception {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
    keyPairGenerator.generateKeyPair();
    return "success";
  }

  @GET
  @Path("SHA256withRSAandMGF1")
  public String checkSHA256withRSAandMGF1() throws Exception {
    // This algorithm name is only supported with BC, Java (11+) equivalent is `RSASSA-PSS`
    Signature.getInstance("SHA256withRSAandMGF1", "BC");
    return "success";
  }


  @GET
  @Path("readEcPrivatePemKey")
  public String readEcPrivatePemKey() throws Exception {
    KeyFactory factory = KeyFactory.getInstance("EC", "BC");

    try (InputStream pemKeyInputStream = Thread.currentThread().getContextClassLoader()
      .getResourceAsStream("ecPrivateKey.pem")) {
      PemReader pemReader = new PemReader(new InputStreamReader(pemKeyInputStream));
      PemObject pemObject = pemReader.readPemObject();

      PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(pemObject.getContent());
      BCECPrivateKey ecPrivateKey = (BCECPrivateKey) factory.generatePrivate(privKeySpec);

      return ecPrivateKey.getD() != null ? "success" : "failure";
    }
  }

  @GET
  @Path("readRsaPrivatePemKey")
  public String readRsaPrivatePemKey() throws Exception {
    KeyFactory factory = KeyFactory.getInstance("RSA", "BC");

    try (InputStream pemKeyInputStream = Thread.currentThread().getContextClassLoader()
      .getResourceAsStream("rsaPrivateKey.pem")) {
      PemReader pemReader = new PemReader(new InputStreamReader(pemKeyInputStream));
      PemObject pemObject = pemReader.readPemObject();

      PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(pemObject.getContent());
      BCRSAPrivateKey ecPrivateKey = (BCRSAPrivateKey) factory.generatePrivate(privKeySpec);

      return ecPrivateKey.getPrivateExponent() != null ? "success" : "failure";
    }
  }

  @GET
  @Path("checkAesCbcPKCS7PaddingCipher")
  public String checkAesCbcPKCS7PaddingCipher() throws Exception {
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
    return cipher.getAlgorithm();
  }


  private PemObject pemObject;
  private Key key;

  @GET
  @Path("ADSF")
  @Produces(MediaType.TEXT_PLAIN)
  public ECGen jwtcreste() throws FileNotFoundException, IOException, NoSuchAlgorithmException,
    NoSuchProviderException,Exception {

   // Security.addProvider(new BouncyCastleProvider());
String A="A";
int a=1;
    ECGen eCGen = new ECGen();
    eCGen.generate(a+"PRIVATE.pem","public.pem");


    return eCGen;


  }


@Path("ASDF")
@POST
@Produces(MediaType.APPLICATION_JSON)
  public String asdfs(KeyUse keyUse) throws NoSuchAlgorithmException, NoSuchProviderException {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BCFIPS");
    generator.initialize(2048);
    KeyFactory factory = KeyFactory.getInstance(ALGORITHM, "BCFIPS");
  KeyUse keyUses= KeyUse.SIG;
    ECPublicKey ecKey = (ECPublicKey) key;
    RSAPublicKey rsaKey = (RSAPublicKey) key;
    ECPublicJWK jwk = new ECPublicJWK();
    rsaKey.getModulus();
    rsaKey.getPublicExponent();
    String kid = this.kid != null ? this.kid : KeyUtils.createKeyId(key);
    int fieldSize = ecKey.getParams().getCurve().getField().getFieldSize();
   // RSAPublicJWK jwk = new RSAPublicJWK();
    jwk.setKeyId(kid);
    jwk.setKeyType(KeyType.EC);
    jwk.setAlgorithm(algorithm);
    jwk.setPublicKeyUse(keyUses == null ? DEFAULT_PUBLIC_KEY_USE.getSpecName() : keyUse.getSpecName());
    jwk.setCrv("P-" + fieldSize);
    jwk.setX(Base64Url.encode(toIntegerBytes(ecKey.getW().getAffineX(), fieldSize)));
    jwk.setY(Base64Url.encode(toIntegerBytes(ecKey.getW().getAffineY(), fieldSize)));
  System.out.println(jwk.getPublicKeyUse());
return jwk.getAlgorithm();
  }


}
