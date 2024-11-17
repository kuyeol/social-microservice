package org.acme.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TimeTable {

    private final TimeSlot ts;
    private final Map<String , TimeSlot> rentals = new ConcurrentHashMap<>();

    TimeTable() {
        this.ts = null;
    }


    TimeTable(int i) {
        this.ts = new TimeSlot(i);
    }


    public TimeSlot getTs() {
        return ts;
    }


    public class TimeSlot {
        private final int a;

        public TimeSlot(int i) {
            this.a = i;
        }


        public int getA() {
            return a;
        }
    }

}
