package com.passzone.passzone_backend.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private FanZone fanZone;

    private String matchName;
    private Instant startTime;
    private int capacity;
    private int booked;

    public TimeSlot() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public FanZone getFanZone() { return fanZone; }
    public void setFanZone(FanZone fanZone) { this.fanZone = fanZone; }

    public String getMatchName() { return matchName; }
    public void setMatchName(String matchName) { this.matchName = matchName; }

    public Instant getStartTime() { return startTime; }
    public void setStartTime(Instant startTime) { this.startTime = startTime; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getBooked() { return booked; }
    public void setBooked(int booked) { this.booked = booked; }
}
