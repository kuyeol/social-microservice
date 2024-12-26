package org.acme.client.ungorithm;


public class Output implements JpaModel {


    private String username;



    @Override
  public  String JpaNname(){
      return "hello";
    }
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long timestamp;

    public String address;

    //@Column(name = "usernapme")


    public String getUsername() {

        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
