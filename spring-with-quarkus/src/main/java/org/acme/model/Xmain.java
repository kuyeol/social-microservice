package org.acme.model;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;
import org.acme.entity.item.Seat;
import org.acme.entity.location.Hall;
import org.acme.entity.location.Venue;

public class Xmain {
    private int state;
    final int init;

    public Xmain() {
        this.init = 0;
        this.state = init;
    }

    public void setInt() {


        if (state == init) {
            state++;
        } else {
            System.out.println("failed");
        }
    }


    public boolean getOrder() {

        boolean b;
        b = state == init ? true : false;
        String str = state == init ? "가능" : "불가";
        System.out.println(str);

        return b;

    }

    public static Map<String, String> createList() {
        Function<Venue, Hall> fn = new Function<Venue, Hall>() {
            public Hall apply(Venue venue) {
                Hall hall = new Hall();
                hall.setVenue(venue);
                return hall;
            }
        };
        Map<String, String> map = new HashMap<>();
        Venue venue = new Venue("211");
        venue.setVenueName(venue.getId());
        fn.apply(venue);
        map.put(venue.getId(), fn.apply(venue).toString());

        Stream<String> streamKey = map.keySet().stream();
        Stream<String> streamValue = map.values().stream();

        streamKey.forEach(System.out::println);
        streamValue.forEach(System.out::println);

        return map;
    }


    static Scanner scanner = new Scanner(System.in);
    static boolean done = true;

    public static void run() {

        while (done) {
            System.out.println(
                "[0] => quit" +
                    "[1] => new" +
                    "[2] => add" +
                    "[3] => get"
            );

            int select = scanner.nextInt();
            menu(select);
        }

    }

    static Bank bank;


    static Xmain getName(){
            done=false;
        return new Xmain();
    }

    static void makeBank(){
        bank.setName("aaaaa");

    }





    private static void menu(int select) {
     if (select == 0) {
         makeBank();
     }else if (select == 1) {
         getName();
     }



        //return command[select];

    }


    public class Bank {

        String name;

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }


    }

    public static void main(String[] args) {

        run();
    }

    static boolean isO(int i) {
        boolean b;
        b = i % 2 == 0;
        System.out.println(b);
        return 1 % 2 == 0;
    }

    static int[][] capa = new int[][] {{100}, {150}, {50}};


    public static void createSeat(int[][] seats) {
        Set<Seat> seatSet = new LinkedHashSet<>();

        int floor = seats.length;

        for (int i = 0; i < floor; i++) {


            for (int j = 1; j < seats[i][0]; j++) {

                Seat seat = new Seat();
                int floors = i + 1;
                if (i == 0) {
                    seat.setSeatGrade("R" + "-" + floors + "층");
                    seat.setSeatNumber(j);
                    seatSet.add(seat);

                } else {

                    seat.setSeatGrade("A" + "-" + floors + "층");
                    seat.setSeatNumber(j);
                    seatSet.add(seat);
                }

            }
        }

        for (Seat iter : seatSet) {
            System.out.println(iter);
        }


    }


    public static String[][] setRSeat(int n) {

        int[][] grade = new int[n][n];

        // 중앙 시작 index
        int start = n * 1 / 4;

        // 중앙 끝 index
        int end = n * 3 / 4;

        String[][] seatLayout = new String[n][n];

        for (int row = 0; row < n; row++) {


            for (int col = 0; col < n; col++) {
                if (start < row || row < end && start < col || col < end) {
                    seatLayout[row][col] = "MID";
                } else {

                    seatLayout[row][col] = "SIDE";
                }


            }

        }

        return seatLayout;
    }
}