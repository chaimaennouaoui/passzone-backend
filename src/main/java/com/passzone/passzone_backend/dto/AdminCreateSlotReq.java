package com.passzone.passzone_backend.dto;

public class AdminCreateSlotReq {
    private String matchName;
    private String startTime;
    private int capacity;

    public AdminCreateSlotReq() {}

    public String getMatchName() { return matchName; }
    public void setMatchName(String matchName) { this.matchName = matchName; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}
