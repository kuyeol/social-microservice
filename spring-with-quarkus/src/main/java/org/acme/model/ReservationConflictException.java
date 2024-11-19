package org.acme.model;

public class ReservationConflictException extends Exception {




    public ReservationConflictException(String msg){
        System.out.println(msg);
    }
}
