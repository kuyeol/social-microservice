package org.acme.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReservationManager {

    private static final Map<LocalDateTime, Reservation> reservations = new ConcurrentHashMap<>();

    public List<Reservation> addReservations(Map<LocalDateTime, Reservation> mapTemp, LocalDateTime startTime, int numSlots) throws ReservationConflictException {

        // 1. Generate Time Slots:
        List<LocalDateTime> timeSlots =
            IntStream.range(0, numSlots).mapToObj(i -> startTime.plusMinutes(i * 30)).collect(Collectors.toList());


        // 2. Check for Conflicts *before* any insertions:
        List<LocalDateTime> conflictingSlots =
            timeSlots.stream().filter(reservations::containsKey) // Check if any slot is already booked
                .collect(Collectors.toList());

        if (!conflictingSlots.isEmpty()) {
            String message = "The following time slots are already booked: " +
                conflictingSlots.stream().map(LocalDateTime::toString).collect(Collectors.joining(", "));
            throw new ReservationConflictException("message");  // Fail early
        }


        // 3. Add Reservations (atomically, if possible):
        List<Reservation> newReservations =
            timeSlots.stream().map(slot -> new Reservation(/* ... details, including the 'slot' */))
                .collect(Collectors.toList());

        // Use putAll to more atomically add reservations.
        Map<LocalDateTime, Reservation> reservationsToAdd = new ConcurrentHashMap<>();
        for (Reservation res : newReservations) {
            reservationsToAdd.put(res.getTimeSlot(), res); // Assuming Reservation has a getTimeSlot() method.
        }
        mapTemp = reservations;
        mapTemp.putAll(reservationsToAdd);
        reservations.putAll(reservationsToAdd);  // More atomic

        return newReservations;
    }


    // ... other methods (update, cancel, etc.)


}



