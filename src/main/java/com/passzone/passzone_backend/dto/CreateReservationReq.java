package com.passzone.passzone_backend.dto;

public class CreateReservationReq {
    private Long slotId;
    private int qty;

    public CreateReservationReq() {}

    public Long getSlotId() { return slotId; }
    public void setSlotId(Long slotId) { this.slotId = slotId; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }
}
