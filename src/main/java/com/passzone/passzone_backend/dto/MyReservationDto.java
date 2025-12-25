package com.passzone.passzone_backend.dto;

public class MyReservationDto {
    private Long id;
    private String fanZoneName;
    private String city;
    private String matchName;
    private String startTime;
    private int qty;
    private String status;
    private String ticketCode;

    public MyReservationDto() {}

    public MyReservationDto(Long id, String fanZoneName, String city,
                            String matchName, String startTime,
                            int qty, String status, String ticketCode) {
        this.id = id;
        this.fanZoneName = fanZoneName;
        this.city = city;
        this.matchName = matchName;
        this.startTime = startTime;
        this.qty = qty;
        this.status = status;
        this.ticketCode = ticketCode;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFanZoneName() { return fanZoneName; }
    public void setFanZoneName(String fanZoneName) { this.fanZoneName = fanZoneName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getMatchName() { return matchName; }
    public void setMatchName(String matchName) { this.matchName = matchName; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTicketCode() { return ticketCode; }
    public void setTicketCode(String ticketCode) { this.ticketCode = ticketCode; }
}
