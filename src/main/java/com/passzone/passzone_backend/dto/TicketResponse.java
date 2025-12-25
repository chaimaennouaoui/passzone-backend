package com.passzone.passzone_backend.dto;

public class TicketResponse {
    private Long reservationId;
    private String ticketCode;
    private String qrPngBase64;

    public TicketResponse() {}

    public TicketResponse(Long reservationId, String ticketCode, String qrPngBase64) {
        this.reservationId = reservationId;
        this.ticketCode = ticketCode;
        this.qrPngBase64 = qrPngBase64;
    }

    public Long getReservationId() { return reservationId; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }

    public String getTicketCode() { return ticketCode; }
    public void setTicketCode(String ticketCode) { this.ticketCode = ticketCode; }

    public String getQrPngBase64() { return qrPngBase64; }
    public void setQrPngBase64(String qrPngBase64) { this.qrPngBase64 = qrPngBase64; }
}
