package org.acme.model;

public class TimeSlot {

    private final int a;



    public TimeSlot(int i) {
        this.a = i;
    }


    public int getA() {
        return a;
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
            "a=" + a +
            '}';
    }
}
