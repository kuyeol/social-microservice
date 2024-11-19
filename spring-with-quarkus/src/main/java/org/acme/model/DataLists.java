package org.acme.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class DataLists {

    private String[] array;


    public DataLists() {

    }


    public DataLists(String[] array) {
        this.array = array;
    }

    public void setData(int start, int end) {
        this.array = new String[end - start];

        for (int i = 0; i < end; i++) {
            if (i % 2 == 0) {

                array[i + 1] = start + i + " : " + 00;
            } else {

                array[i - 1] = start + i + " : " + 30;
            }
        }

    }

    public void getArray() {
        for (String temp : array) {
            System.out.println(temp);
        }

        //return ;
    }

    static int rr;

    public static int getR() {
        return rr;
    }

    public static int factori(int n) throws Exep {
        rr = n;
        getR();
        System.out.println("\t" + "(" + n + ")");
        if (n < 0) {
            throw new Exep("0보다 작음");
        } else if (n == 0) {
            return 1;
        } else {
            System.out.print("\t - " + n + " * ");
            System.out.print(rr);
            return n * factori(n - 1);
        }


    }


    public static int pow1(int n) {
        int p = 0;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + n; j++) {
                for (int k = 0; k < 1; k++) {
                    p += n;

                    System.out.println(p);
                }

            }
        }

        return p;
    }


    public static int pow(int x, int n) {
        int p = 0;
        int y = 0;
        for (int i = 0; i < x * n; i++) {
            y += x;
            System.out.println(i + ": " + y);
            for (int j = 0; j < n; j++) {

                p += y;


            }
        }

        return p;
    }


    public static long[] f(int x) {

        if (x <= 1) {
            long[] temp = {x, 0};
            return temp;
        } else {

            long[] temp = f(x - 1);
            for (long t : temp) {
                System.out.print("'" + t + ",");
            }
            long[] temp2 = {temp[0] + temp[1], temp[0]};
            System.out.println("\n / " + temp[0] + " / " + temp[1]);

            return temp2;
        }
    }

    static final Map<String, String> testList = new ConcurrentHashMap<>();

    public static void main(String[] a) throws ReservationConflictException {


        int k = 10;
        int s = 0;
        while (k >= 1) {
            s += k;
            k -= k & -k;

        }
        System.out.println(s);


        //Stream testStream = testList.entrySet().stream();
        //testStream.forEach(System.out::println);

        addRental("10:00", "Unavailable");
        addRental("10:00", "Available");

        String dateString = "2024-03-10 09:30";
        String endString = "2024-03-10 22:00";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime parsedDateTime = LocalDateTime.parse(dateString, formatter);
        LocalDateTime parsedEndTime = LocalDateTime.parse(endString, formatter);

        enum State {
            START, END
        }

        class Slot {
            LocalDateTime time;
            State init;
            Slot slot;

            private final State set = State.END;

            public Slot(LocalDateTime time) {
                this.time = time;

            }

            public void setStatus(State s) {
                this.init = s;
            }

            public void setState() {
                slot = new Slot(time);
                slot.init = set;

            }


            public Slot getSlot() {
                return slot;
            }


            @Override
            public String toString() {
                return "\t" + time + " : " + init;
            }


        }

        LocalDateTime slotT = LocalDateTime.parse(dateString, formatter);

        //TODO: 범위 계산 메서드 작성 참고
        /**
         *   Period ,Duration
         *   .isBefore() , .isAfter()
         */


        Map<String, State> timeSlots = new LinkedHashMap<>();


        while (parsedDateTime.isBefore(parsedEndTime)) {

            LocalDateTime temp = parsedDateTime;
            parsedDateTime = temp.plusMinutes(30);
            if (temp.getMinute() == 30) {
                timeSlots.putIfAbsent(temp.format(formatter), State.END);
            }
            timeSlots.putIfAbsent(temp.format(formatter), State.START);
        }

        LocalDateTime date = LocalDateTime.of(2024, 03, 10, 9, 00);
        LocalDateTime end = LocalDateTime.of(2023, 10, 10, 20, 20);


        //

        for (int i = 0; i < 10; i++) {
            timeSlots.get(date.format(formatter));
           // System.out.println(timeSlots.get(date.format(formatter)));
            String d = String.valueOf(timeSlots.get(date.format(formatter)));

            String dd = "hi";

            if(dd !=null) {
                System.out.println("FOR LOOP : "+d);
                continue;
            }else {
                System.out.println("FOR LOOP : "+d);
            }

        }


        System.out.println(timeSlots.get("2024-03-10 13:00").equals(State.START));

        System.out.println();

        Stream tableStream = timeSlots.entrySet().stream();


        tableStream.forEach(System.out::println);


        String formatDateTime = parsedDateTime.format(formatter);
        // Display the parsed LocalDateTime object
        System.out.println("Parsed Date and Time: " + parsedDateTime);
        System.out.println("Parsed Date and Time: " + formatDateTime);

    }

    private String k;
    private int i;
    //Available / Unavailable

    public static String addRental(String k, String v) {

        String thisValue = testList.get(k);

        if (thisValue == null) {
            thisValue = testList.put(k, v);
            System.out.println("\t[Notice ]\t: " + k + " 예약이 성공적으로 추가 되었습니다");
        } else {
            System.out.println("\t[Warning]\t: " + k + "는 이미 예약되어 있습니다");
        }
        return thisValue;
    }


}
