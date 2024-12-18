package org.acme.service.customer;

import io.vertx.core.json.JsonObject;
import java.util.HashMap;
import java.util.Map;
import org.acme.repository.FormData;

public class UserForm implements FormData {


    private static final int MIN = 8;
    private static final int ID_MAX = 24;
    private static final int PASS_MAX = 16;
    private static final String ID_REX = "^[a-zA-Z][a-zA-Z0-9_]{1,20}$";

    private final Map<String, String> map = new HashMap<>();


    private String username;
    private String password;

    private boolean isVaild = false;

    private int result = 1;


    //public boolean getStatus() {
    //
    //    if (result == 1) {
    //
    //        map.put("status", "성공");
    //
    //        return true;
    //
    //    } else {
    //
    //        map.put("status", "실패");
    //
    //        return false;
    //    }
    //
    //}

    private int getValid(final String k, final String v, int max) {

        final int value = v.length();

        if (value < MIN) {

            result *= 0;

            int len = MIN - value;
            map.put(k, "글자 수 가" + len + " 만큼 부족 합니다");

            return result;

        } else if (value > max) {

            result *= 0;

            int len = value - max;
            map.put(k, "글자 수 가" + len + " 만큼 초과 하였습니다");

            return result;

        } else {

            map.remove(k);
            // map.put(key, "200");
            return result;
        }

    }

    @Override
    public String getUsername() {

        if (!username.matches(ID_REX)) {
            map.put("username", "허용 되지않은 문자가 포함 되었습니다");
            result *= 0;

            return "";

        } else if (getValid("username", this.username, ID_MAX) == 0) {

            return "";
        } else {

            return this.username;
        }

    }

    @Override
    public String getPassword() {

        if (getValid("password", this.password, PASS_MAX) == 0) {

            return "";
        } else {
            return this.password;
        }
    }


    public JsonObject getMessage() {

        JsonObject message = new JsonObject();

        for (Map.Entry<String, String> entry : this.map.entrySet()) {

            message.put(entry.getKey(), entry.getValue());
        }


        return message;
    }


}
