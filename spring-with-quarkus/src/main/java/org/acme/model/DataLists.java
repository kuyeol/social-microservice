package org.acme.model;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    public static void main(String[] a) throws Exep {


        addRental("10:00", "Unavailable");
        addRental("10:00", "Available");


        //Stream testStream = testList.entrySet().stream();
        //testStream.forEach(System.out::println);

    }

    private String k;
    private int i;
    //Available / Unavailable

    public static String addRental(String k, String v) {

        String thisValue = testList.get(k);

        if (thisValue == null) {
            thisValue = testList.put(k, v);
            System.out.println("\t[Notice ]\t: " + k + "에 예약이 성공적으로 추가 되었습니다");
        } else {
            System.out.println("\t[Warning]\t: " + k + "는 이미 예약되어 있습니다");
        }
        return thisValue;
    }


}
