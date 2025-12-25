package com.passzone.passzone_backend.dto;

public class SlotDto {
    private Long id;
    private Long zoneId;
    private String matchName;
    private String startTime;
    private int capacity;
    private int booked;

    public SlotDto() {}

    public SlotDto(Long id, Long zoneId, String matchName, String startTime, int capacity, int booked) {
        this.id = id;
        this.zoneId = zoneId;
        this.matchName = matchName;
        this.startTime = startTime;
        this.capacity = capacity;
        this.booked = booked;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getZoneId() { return zoneId; }
    public void setZoneId(Long zoneId) { this.zoneId = zoneId; }

    public String getMatchName() { return matchName; }
    public void setMatchName(String matchName) { this.matchName = matchName; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getBooked() { return booked; }
    public void setBooked(int booked) { this.booked = booked; }
}
