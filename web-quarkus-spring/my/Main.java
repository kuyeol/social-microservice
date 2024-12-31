public class Main {

    public static void main(String[] args) throws Exception {


        String strTrim = "  dad   , dsf dd  ".trim()
                                           .replaceAll(" ", "");
        String  afStr = strTrim.substring(0,strTrim.lastIndexOf(','));
       String st2=   strTrim.substring(strTrim.lastIndexOf(','));
        boolean bb    = strTrim.equals(afStr);
        System.out.println(strTrim);
        System.out.println(afStr);
        System.out.println(st2.replaceAll(",", ""));

        System.out.println(bb);
        //
        //
        //    List<Integer> intlist = new ArrayList<>();
        //
        //    intlist.add(1);
        //    intlist.add(2);
        //    intlist.add(3);
        //    intlist.add(4);
        //
        //    List<String> strlist = new ArrayList<>();
        //    strlist.add("Aab");
        //    strlist.add("Bbc");
        //    strlist.add("Cadsf");
        //    strlist.add("Dadsf");
        //
        //    Field[] field = User.class.getFields();
        //
        //    for (Field f : field) {
        //        f.setAccessible(true);
        //    }
        //
        //    final Map<String, String> lengOf = new HashMap<>();
        //    lengOf.put("username", "zimop@gmail.com");
        //    lengOf.put("email", "nicasdfsdfk");
        //
        //    Map<String, String> rs = new HashMap<>();
        //
        //    User user = new User.Builder().create();
        //
        //    List<String> initData = new ArrayList<>();
        //    initData.add("asdf");
        //
        //    for (Map.Entry<String, String> entry : lengOf.entrySet()) {
        //        List<String> stringlist = user.getFieldName();
        //        String       key        = entry.getKey();
        //        String       key2       = stringlist.get(stringlist.indexOf(key));
        //        String       value      = "";
        //        if (entry.getValue()
        //                 .length() > 5 && entry.getValue()
        //                                       .length() < 24) {
        //            value = entry.getValue();
        //        }
        //
        //        if (key.equals(key2)) {
        //            //System.out.println(key2);
        //            // System.out.println(key);
        //            if (!value.isEmpty()) {
        //                rs.put(key, value);
        //            }
        //        }
        //
        //    }
        //
        //    lengOf.forEach((k, v) -> {
        //        user.setUsername(v);
        //    });
        //
        //    User         u           = new User.Builder().create();
        //    User.Builder userBuilder = new User.Builder();
        //    u = userBuilder.create();
        //    u.setUsername("asdf");
        //
        //    System.out.println(u);
        //    try (User tuser = new User.Builder().create()) {
        //        tuser.setUsername("트라이빌드 유저");
        //        System.out.println(tuser);
        //    }
        //
        //    System.out.println(user);
        //    //   System.out.println(rs);
        //    //System.out.println(user.getFieldName());
        //    //   System.out.println(user);
        //
    }


}
