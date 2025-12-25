package com.passzone.passzone_backend.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Reservation {

    public enum Status { CONFIRMED, CANCELED, CHECKED_IN }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userUid;
    private String userEmail;

    @ManyToOne
    private TimeSlot slot;

    private int qty;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Instant createdAt;

    public Reservation() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserUid() { return userUid; }
    public void setUserUid(String userUid) { this.userUid = userUid; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public TimeSlot getSlot() { return slot; }
    public void setSlot(TimeSlot slot) { this.slot = slot; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
