package org.acme.entity.date;


import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.Table;
import org.acme.entity.item.Performance;

@Entity
@Table
public class Schedule {
    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    private String id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;


    //////////////////////////////////////////////

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    private LocalDateTime date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Duration runingTime = Duration.between(startTime, endTime);
    ;

    public LocalDateTime getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return date.toLocalTime();
    }

    public LocalTime getEndTime() {
        return endTime;
    }


    public void testOutput() {

        int year = 2024;
        int month = 8;
        int day = 12;
        int hour = 9;
        int min = 30;

        DateTimeFormatter formatt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime st = LocalDateTime.of(year, month, day, hour, min);
        System.out.println(st.format(formatt));
    }


    private boolean isOverlapping(Schedule r1, Schedule r2) {
        if (!r1.getDate().equals(r2.getDate())) {
            return false; // 다른 날짜는 중복 아님
        }
        // 시작 시간과 끝 시간이 겹치는지 확인
        return !r1.getEndTime().isBefore(r2.getStartTime()) && !r2.getEndTime().isBefore(r1.getStartTime());
    }

    List<LocalTime[]> generateTimeSlots(LocalTime start, LocalTime end, int intervalMinutes) {
        List<LocalTime[]> slots = new ArrayList<>();
        LocalTime slotStart = start;

        while (slotStart.isBefore(end)) {
            LocalTime slotEnd = slotStart.plusMinutes(intervalMinutes);
            if (slotEnd.isAfter(end)) {
                break;
            }
            slots.add(new LocalTime[] {slotStart, slotEnd});
            slotStart = slotEnd;
        }
        return slots;
    }
    //System.out.println("시간 차이: " + duration.toMinutes() + "분"); // 150분

    public Stream<LocalTime> getTime(List<LocalTime> timeslot) {


        Stream<LocalTime> s = timeslot.stream();
        s.forEach(System.out::println);
        return s;
    }

    public void sdfsd() {


        LocalDate start = LocalDate.of(2000, 1, 1);
        LocalDate endExclusive = LocalDate.now();
        Stream<LocalDate> allDays = start.datesUntil(endExclusive);

        allDays.forEach(System.out::println);

    }


}
