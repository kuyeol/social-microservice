package com.packt.cantata.temp;


import com.packt.cantata.temp.common.EntityBase;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "HALL")
public class HallEntity extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "VENUE_ID", nullable = false)
    private VenueEntity venue;


    @Column(name = "HALL_NAME")
    private String hallName;

    @Column(name = "LACATION")
    private String location;

    @Column(name = "seat_count")
    private Integer seatCount;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "hall")
    private Set<Seat> seats;

    //공연정보 테이블에 매핑
    @OneToMany(mappedBy = "hallentity",cascade = CascadeType.ALL)
    private List<EventEntity> currentPerformanceInfo;


    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    private int runningTime;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }
}
