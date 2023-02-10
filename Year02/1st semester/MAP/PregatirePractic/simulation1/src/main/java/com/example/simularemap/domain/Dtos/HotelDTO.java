package com.example.simularemap.domain.Dtos;

import com.example.simularemap.domain.Hotel;

public class HotelDTO {
    private Double id;
    private String hotelName;
    private String noRooms;
    private String pricePerNight;
    private String hotelType;

    public HotelDTO(Hotel hotel) {
        id = hotel.getId();
        hotelName = hotel.getLocationName();
        noRooms = String.valueOf(hotel.getNoRooms());
        pricePerNight = String.valueOf(hotel.getPricePerNight());
        hotelType = String.valueOf(hotel.getHotelType());
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getNoRooms() {
        return noRooms;
    }

    public void setNoRooms(String noRooms) {
        this.noRooms = noRooms;
    }

    public String getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(String pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getHotelType() {
        return hotelType;
    }

    public void setHotelType(String hotelType) {
        this.hotelType = hotelType;
    }
}
