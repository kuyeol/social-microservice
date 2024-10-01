
package org.account.model;

import org.account.util.json.JsonSerialization;

import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class CredentialModel implements Serializable {


  // Secret is same as password but it is not hashed
  public static final String SECRET = "secret";
  public static final String CLIENT_CERT = "cert";
  public static final String KERBEROS = "kerberos";


  private String id;
  private String type;
  private String userLabel;
  private Long createdDate;

  private String secretData;
  private String credentialData;

  public CredentialModel shallowClone() {
    CredentialModel res = new CredentialModel();
    res.id = id;
    res.type = type;
    res.userLabel = userLabel;
    res.createdDate = createdDate;
    res.secretData = secretData;
    res.credentialData = credentialData;
    return res;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUserLabel() {
    return userLabel;
  }

  public void setUserLabel(String userLabel) {
    this.userLabel = userLabel;
  }

  public Long getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Long createdDate) {
    this.createdDate = createdDate;
  }

  public String getSecretData() {
    return secretData;
  }

  public void setSecretData(String secretData) {
    this.secretData = secretData;
  }

  public String getCredentialData() {
    return credentialData;
  }

  public void setCredentialData(String credentialData) {
    this.credentialData = credentialData;
  }

  public static Comparator<CredentialModel> comparingByStartDateDesc() {
    return (o1, o2) -> { // sort by date descending
      Long o1Date = o1.getCreatedDate() == null ? Long.MIN_VALUE : o1.getCreatedDate();
      Long o2Date = o2.getCreatedDate() == null ? Long.MIN_VALUE : o2.getCreatedDate();
      return (-o1Date.compareTo(o2Date));
    };
  }

  // DEPRECATED - the methods below exists for the backwards compatibility


  private void writeMapAsJson(Map<String, Object> map, boolean secret) {
    try {
      String jsonStr = JsonSerialization.writeValueAsString(map);
      if (secret) {
        this.secretData = jsonStr;
      } else {
        this.credentialData = jsonStr;
      }
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  private Map<String, Object> readMapFromJson(boolean secret) {
    String jsonStr = secret ? secretData : credentialData;
    if (jsonStr == null) {
      return new HashMap<>();
    }

    try {
      return JsonSerialization.readValue(jsonStr, Map.class);
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  private String readString(String key, boolean secret) {
    Map<String, Object> credentialDataMap = readMapFromJson(secret);
    return (String) credentialDataMap.get(key);
  }

  private int readInt(String key, boolean secret) {
    Map<String, Object> credentialDataMap = readMapFromJson(secret);
    Object obj = credentialDataMap.get(key);
    return obj == null ? 0 : (Integer) obj;
  }

  private void writeProperty(String key, Object value, boolean secret) {
    Map<String, Object> credentialDataMap = readMapFromJson(secret);
    if (value == null) {
      credentialDataMap.remove(key);
    } else {
      credentialDataMap.put(key, value);
    }
    writeMapAsJson(credentialDataMap, secret);
  }
}
