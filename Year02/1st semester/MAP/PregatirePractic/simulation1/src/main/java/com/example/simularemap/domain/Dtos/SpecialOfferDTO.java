package com.example.simularemap.domain.Dtos;

import com.example.simularemap.domain.SpecialOffer;
import com.example.simularemap.service.SpecialOfferService;

import java.util.Date;

public class SpecialOfferDTO {
    private Double id;
    private String startDate;
    private String endDate;
    private String percent;

    public SpecialOfferDTO(SpecialOffer specialOffer) {
        id = specialOffer.getId();
        startDate = specialOffer.getStartDate().toString();
        endDate = specialOffer.getEndDate().toString();
        percent = String.valueOf(specialOffer.getPercent());
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
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

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
