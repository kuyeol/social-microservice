package org.acme.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TimeTable {

    private TimeSlot ts;
    private final Map<String, TimeSlot> rentals = new ConcurrentHashMap<>();
    private TimeStatus status;

    private int count;


    public Map<String, TimeSlot> getRental() {
        String idx = String.valueOf(count++);
        rentals.putIfAbsent(idx, ts);
        return rentals;
    }


    TimeTable() {
        this.status = TimeStatus.Available;
    }


    public void addSlot(int n) {
        //if (this.ts == null && status.equals(TimeStatus.Available)) {
        String idx = String.valueOf(count++);

        if (status.equals(TimeStatus.Available)) {
            creatSlot(n);
            rentals.putIfAbsent(idx, ts);
            System.out.println("추가 성공");
        }

        System.out.println("이미 생성된 슬롯");
    }


    public TimeSlot creatSlot(int n) {
        this.ts = new TimeSlot(n);
        this.status = TimeStatus.Unavailable;
        return ts;
    }


    public TimeSlot getTs() {
        return ts;
    }


    @Override
    public String toString() {
        return "\nTimeTable = " + status + "{" +
            "ts: " + ts +
            "}, rentals=" + rentals +
            '}' + "\n";
    }


}
