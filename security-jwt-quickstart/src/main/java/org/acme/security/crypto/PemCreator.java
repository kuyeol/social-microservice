package org.acme.security.crypto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.Key;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

public class PemCreator {

  private PemObject pemObject;

  public PemCreator(Key key,String description) {

    this.pemObject = new PemObject("RSA PRIVATE KEY", key.getEncoded());
  }


  public void create(String description) throws IOException {
    PemWriter pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(description)));
    try {
      pemWriter.writeObject(this.pemObject);
    }finally {
      pemWriter.close();
    }
  }


}
