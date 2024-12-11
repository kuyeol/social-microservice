package org.acme.model;

public class SquareSeq implements IntSeq {


     public int j;
    public SquareSeq sq;


    public static double average(IntSeq seq, int n) {
int i;
        i = n;

        double sum = 0;

        while (seq.hasNext()) {
            sum += seq.next();
            i--;
        }

        return sum / n;

    }


    public SquareSeq getInst(int i, int j) {
        this.j = j;
        System.out.println(i +"/"+ j);
        return this.sq;
    }

    public int getI() {
        j += 1;
        System.out.println(j);
        return this.j;
    }

    public void setI(int j) {
        this.j = j;
    }


    @Override
    public boolean hasNext() {

        if (i == 0) {

            return false;
        }

        return true;
    }

    @Override
    public int next() {


        return i * i;
    }


    @Override
    public final boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof final IntSeq squareSeq)) {
            return false;
        }

        return i == squareSeq.i;
    }

    @Override
    public int hashCode() {
        return i;
    }
}
