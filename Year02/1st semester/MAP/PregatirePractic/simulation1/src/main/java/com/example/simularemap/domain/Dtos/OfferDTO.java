package com.example.simularemap.domain.Dtos;

public class OfferDTO {
    private String hotelName;
    private String locationName;
    private String startDate;
    private String endDate;

    public OfferDTO(String hotelName, String locationName, String startDate, String endDate) {
        this.hotelName = hotelName;
        this.locationName = locationName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "OfferDTO{" +
                "hotelName='" + hotelName + '\'' +
                ", locationName='" + locationName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
