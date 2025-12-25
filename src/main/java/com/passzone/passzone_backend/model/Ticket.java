package com.passzone.passzone_backend.model;

import jakarta.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Reservation reservation;

    private String ticketCode;

    @Column(length = 100000)
    private String barcodePngBase64;

    public Ticket() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }

    public String getTicketCode() { return ticketCode; }
    public void setTicketCode(String ticketCode) { this.ticketCode = ticketCode; }

    public String getBarcodePngBase64() { return barcodePngBase64; }
    public void setBarcodePngBase64(String barcodePngBase64) { this.barcodePngBase64 = barcodePngBase64; }
}
