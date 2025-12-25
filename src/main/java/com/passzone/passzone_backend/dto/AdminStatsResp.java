package com.passzone.passzone_backend.dto;

public class AdminStatsResp {

    private long totalReservations;
    private long confirmedReservations;
    private long canceledReservations;
    private long checkedInReservations;
    private double fillRate;   // % capacity used (booked / capacity)
    private double noShowRate; // % confirmed but not checked in (for past slots)

    public AdminStatsResp() {}

    public AdminStatsResp(long totalReservations, long confirmedReservations,
                          long canceledReservations, long checkedInReservations,
                          double fillRate, double noShowRate) {
        this.totalReservations = totalReservations;
        this.confirmedReservations = confirmedReservations;
        this.canceledReservations = canceledReservations;
        this.checkedInReservations = checkedInReservations;
        this.fillRate = fillRate;
        this.noShowRate = noShowRate;
    }

    public long getTotalReservations() { return totalReservations; }
    public void setTotalReservations(long totalReservations) { this.totalReservations = totalReservations; }

    public long getConfirmedReservations() { return confirmedReservations; }
    public void setConfirmedReservations(long confirmedReservations) { this.confirmedReservations = confirmedReservations; }

    public long getCanceledReservations() { return canceledReservations; }
    public void setCanceledReservations(long canceledReservations) { this.canceledReservations = canceledReservations; }

    public long getCheckedInReservations() { return checkedInReservations; }
    public void setCheckedInReservations(long checkedInReservations) { this.checkedInReservations = checkedInReservations; }

    public double getFillRate() { return fillRate; }
    public void setFillRate(double fillRate) { this.fillRate = fillRate; }

    public double getNoShowRate() { return noShowRate; }
    public void setNoShowRate(double noShowRate) { this.noShowRate = noShowRate; }
}
