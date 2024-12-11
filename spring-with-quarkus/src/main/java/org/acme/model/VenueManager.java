package org.acme.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.acme.entity.location.Venue;

public class VenueManager extends Venue {

    private int size;

    private Set<Venue> venues = new HashSet<>();


    public boolean removeVenue(String name) {


        for (Venue temp : venues) {
            temp.setVenueName(name);

            if (temp.getVenueName().equals(name)) {
                System.out.println(temp.getVenueName() + " 찾음" + "\n" + temp + "" + "\n");
                venues.remove(temp);
                System.out.println(temp.getVenueName() + " 삭제 \n");
                return true;
            }
        }
        System.out.println("없음");
        return false;
    }
    //public boolean removeVenue(String name) {
    //    Iterator<Venue> iterator = venues.iterator();
    //    while (iterator.hasNext()) {
    //        Venue temp = iterator.next();
    //        if (temp.getVenueName().equals(name)) {
    //            System.out.println(temp.getVenueName() + " 찾음" + "\n" + temp + "\n");
    //            iterator.remove(); // Iterator의 remove 메서드 사용
    //            System.out.println(temp.getVenueName() + " 삭제 \n");
    //            return true;
    //        }
    //    }
    //    System.out.println("없음");
    //    return false;
    //}


    //public boolean removeVenue(String name) {
    //    boolean removed = venues.removeIf(venue -> venue.getVenueName().equals(name));
    //    if (removed) {
    //        System.out.println(name + " 삭제 \n");
    //    } else {
    //        System.out.println("없음");
    //    }
    //    return removed;
    //}
    //
    //

    public boolean addVenue(Venue venue) {


        if (venue.getVenueName() == null) {

            return warning();
        }

        venues.add(venue);

        return true;
    }

    public int getSizes() {
        this.size = venues.size();


        return size;

    }

    public boolean warning() {
        System.out.println("Venue 이름에 문제가 있습니다");
        return false;
    }


    @Override
    public String toString() {

        if (venues.size() == 0) {

            return "아무것도 없음";
        }

        return "" + venues;
    }


}
