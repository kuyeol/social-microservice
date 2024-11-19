package org.acme.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {

    private LocalDateTime timeSlot; // You will need to implement this field.

    public LocalDateTime getTimeSlot(){
        return this.timeSlot;
    }

    // ... Constructor etc.


    @Override
    public String toString(){
        String toMonth = timeSlot.getMonth().name();
        String toDay =  String.valueOf(timeSlot.getDayOfMonth());
        String toHour =  String.valueOf(timeSlot.getHour());
        String toMinute = String.valueOf(timeSlot.getMinute());

        return toMonth+toDay+toHour+toMinute;
    }


}
