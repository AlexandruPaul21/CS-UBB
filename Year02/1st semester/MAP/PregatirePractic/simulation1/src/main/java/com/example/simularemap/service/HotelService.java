package com.example.simularemap.service;

import com.example.simularemap.domain.Hotel;
import com.example.simularemap.repository.dbRepo.HotelsDbRepository;

import java.util.List;

public class HotelService {
    private HotelsDbRepository hotelsDbRepository;

    public HotelService(HotelsDbRepository hotelsDbRepository) {
        this.hotelsDbRepository = hotelsDbRepository;
    }

    public List<Hotel> findAll() {
        return hotelsDbRepository.findAll();
    }

    public List<Hotel> findByLocationId(Double id) {
        return hotelsDbRepository.findByLocationId(id);
    }
}
