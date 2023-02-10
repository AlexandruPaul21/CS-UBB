package com.example.simularemap.domain;

import java.time.LocalDateTime;

public class Reservation extends Entity<Double> {
    private Long clientId;
    private Double hotelId;
    private LocalDateTime startDate;
    private int noNights;

    public Reservation(Long clientId, Double hotelId, LocalDateTime startDate, int noNights) {
        this.clientId = clientId;
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.noNights = noNights;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Double getHotelId() {
        return hotelId;
    }

    public void setHotelId(Double hotelId) {
        this.hotelId = hotelId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public int getNoNights() {
        return noNights;
    }

    public void setNoNights(int noNights) {
        this.noNights = noNights;
    }
}
