package org.acme.model;

public class InterfaceMain {

    static int n = 2;

    public static void minus() {


        n -= 1;
    }


    static void call(Abst a) {
        a.play();
    }

    static void meeting(Abst[] a) {
        int n = a.length;
        int i=0;
        for (Abst b : a) {
            if (a[i] != null) {
                b.play();
            i++;
            }
            System.out.println("null");
        }

    }

    public static void main(String[] args) {


        IntSeq is = new SquareSeq();
        double avg = SquareSeq.average(is, 10);
        int size = 0;
        Abst[] arr = new Abst[11];


        arr[0] = new AbstExt();
        arr[1] = new AbstExt();
        arr[2] = new AbstExt();

        call(arr[0]);
        meeting(arr);

    }


    public static boolean hiNext() {

        return n > 0;
    }


    public static double logal(double x, double n) {

        double rs = 0.0;

        for (int i = 0; i < n; i++) {
            double a = power(-1, i - 1);
            double b = power(x - 1, i);
            if (n % 2 == 0) {
                rs -= (a * b) / i;
            } else {
                rs += (a * b) / i;
            }
        }

        return rs;
    }


    private static double power(double base, int exp) {
        if (exp < 0) {
            return base;
        }
        exp--;
        return base * power(base, exp);
    }

}
