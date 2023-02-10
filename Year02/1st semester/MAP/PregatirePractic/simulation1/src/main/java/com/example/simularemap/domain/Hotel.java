package com.example.simularemap.domain;

import com.example.simularemap.domain.enums.HotelType;

public class Hotel extends Entity<Double> {
    private double locationId;
    private String locationName;
    private int noRooms;
    private double pricePerNight;
    private HotelType hotelType;

    public Hotel(double locationId, String locationName, int noRooms, double pricePerNight, HotelType hotelType) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.noRooms = noRooms;
        this.pricePerNight = pricePerNight;
        this.hotelType = hotelType;
    }

    public double getLocationId() {
        return locationId;
    }

    public void setLocationId(double locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getNoRooms() {
        return noRooms;
    }

    public void setNoRooms(int noRooms) {
        this.noRooms = noRooms;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public HotelType getHotelType() {
        return hotelType;
    }

    public void setHotelType(HotelType hotelType) {
        this.hotelType = hotelType;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "locationId=" + locationId +
                ", locationName='" + locationName + '\'' +
                ", noRooms=" + noRooms +
                ", pricePerNight=" + pricePerNight +
                ", hotelType=" + hotelType +
                '}';
    }
}
