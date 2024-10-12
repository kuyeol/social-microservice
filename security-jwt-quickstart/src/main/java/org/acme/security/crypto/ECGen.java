package org.acme.security.crypto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import javax.crypto.KeyGenerator;

public class ECGen {

  private final String ALGORITHM = "SECT163K1";
  private final String KEY_ALGORITHM = "ECDSA";
  private final String PRO_ALGORITHM = "BC";

  public void generate(String prkey, String puKey)throws Exception{

    KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM,PRO_ALGORITHM);

    ECGenParameterSpec ecSpec = new ECGenParameterSpec(ALGORITHM);

    keygen.initialize(ecSpec,new SecureRandom());

    KeyPair keyPair= keygen.generateKeyPair();
    PublicKey publicKey = keyPair.getPublic();
    PrivateKey privateKey = keyPair.getPrivate();
    create(privateKey,"EC PRIVATE",prkey);
    create(publicKey,"EC PUBLIC",puKey);




  }

  private void create(Key key,String s,String name)throws FileNotFoundException, IOException {
    PemCreator creator = new PemCreator(key,s);
    creator.create(name);



  }



}
