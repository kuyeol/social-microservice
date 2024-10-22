package org.acme.dto;

public class PersonWrapper {

    private final String id;

    private final String nickName;

    public PersonWrapper(String id, String nickName) {
        this.id = id;
        this.nickName = nickName;
    }

    public String getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }



}
