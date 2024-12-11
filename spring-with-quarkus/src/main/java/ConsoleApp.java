import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleApp {

    private Scanner scanner;
    private boolean done = false;
    private int money;

    private static Collection<String> list = new HashSet<>();


    public static String spaceRemove(String name) {

        return name.replaceAll(" ", "");
    }


    public static void addList() {

        String name1 = "ab";
        String name2 = "a b";
        String name3 = "abc";
        String name4 = "ab c";
        String name5 = "a bc";


        boolean a = list.add(spaceRemove(name1));
        boolean b = list.add(spaceRemove(name2));
        boolean c = list.add(spaceRemove(name3));
        boolean d = list.add(spaceRemove(name4));
        boolean e = list.add(spaceRemove(name5));
        boolean f = list.add("a de");


        System.out.println(a==true ? "성공":"실패");
        System.out.println(b==true ? "성공":"실패");
        System.out.println(c==true ? "성공":"실패");
        System.out.println(d==true ? "성공":"실패");
        System.out.println(e==true ? "성공":"실패");
        System.out.println(f==true ? "성공":"실패");

    }


    public static void findName(String name) {


        Optional<String> output =
            list.stream().filter(x -> x.replaceAll(" ", "").equals(name.replaceAll(" ", ""))).findAny();

        //output.stream().forEach(x -> System.out.println(x));
        //if (output.isPresent()) {
        //    System.out.println(output.get());
        //}

        output.ifPresent(System.out::println);


    }


    public static void main(String[] args) {

        addList();


        List<String> list1 = new ArrayList<>();

        boolean stop = true;
        int i = 0;
        while (stop){
          
            i++;
            list1.add("ab"+i);
            if(i==30){
                stop = false;
            }
        }

for(String t : list1){
    System.out.println(t);
}


        System.out.println("-------");


        findName("a b");


    }


    public void start() {

        scanner = new Scanner(System.in);
        while (!done) {

            System.out.println(
                "\n 0=quit" +
                    "\n 1=addMoney"
            );

            int cmd = scanner.nextInt();

            process(cmd);


            System.out.println("= " + money);
            System.out.print("> ");

        }
    }


    void process(int cmd) {
        if (cmd == 0) {
            quit();
        } else if (cmd == 1) {
            System.out.print("입금액 입력 : ");
            System.out.print(" 원");
            money += scanner.nextInt();

        }


    }

    private void quit() {
        this.done = true;
    }


}
