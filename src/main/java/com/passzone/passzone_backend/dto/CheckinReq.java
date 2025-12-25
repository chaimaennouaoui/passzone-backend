package com.passzone.passzone_backend.dto;

public class CheckinReq {
    private String ticketCode;

    public CheckinReq() {}

    public String getTicketCode() { return ticketCode; }
    public void setTicketCode(String ticketCode) { this.ticketCode = ticketCode; }
}
