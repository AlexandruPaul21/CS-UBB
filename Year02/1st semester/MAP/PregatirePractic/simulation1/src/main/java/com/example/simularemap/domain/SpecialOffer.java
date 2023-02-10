package com.example.simularemap.domain;

import java.util.Date;

public class SpecialOffer extends Entity<Double> {
    private Double hotelId;
    private Date startDate;
    private Date endDate;
    private int percent;

    public SpecialOffer(Double hotelId, Date startDate, Date endDate, int percent) {
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
    }

    public Double getHotelId() {
        return hotelId;
    }

    public void setHotelId(Double hotelId) {
        this.hotelId = hotelId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
