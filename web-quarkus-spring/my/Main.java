import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) {
        System.out.println("hello");


       boolean f= strFilter("hello","hello");
        System.out.println(f);

    }


    public static boolean strFilter(String s1,String s2) {

        List<String> strList = new ArrayList<>();

        strList.add(s1);
        strList.add(s2);


      boolean rs =
          strList.stream().map(s->s1.replaceAll(" ", "")).filter(s->s1.equalsIgnoreCase(s2)).findFirst().isPresent();


        return rs;
    }


}
