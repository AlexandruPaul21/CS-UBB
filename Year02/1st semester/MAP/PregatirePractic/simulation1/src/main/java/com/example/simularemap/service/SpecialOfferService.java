package com.example.simularemap.service;

import com.example.simularemap.SpecialOfferController;
import com.example.simularemap.domain.SpecialOffer;
import com.example.simularemap.repository.dbRepo.SpecialOfferDbRepo;

import java.util.Date;
import java.util.List;

public class SpecialOfferService {
    private SpecialOfferDbRepo specialOfferDbRepo;

    public SpecialOfferService(SpecialOfferDbRepo specialOfferDbRepo) {
        this.specialOfferDbRepo = specialOfferDbRepo;
    }

    public List<SpecialOffer> findByDateAndId(Double hotelId, Date startDate, Date endDate) {
        return specialOfferDbRepo.findBetweenDates(hotelId, startDate, endDate);
    }
}
