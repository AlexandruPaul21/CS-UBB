package com.example.simularemap.service;

import com.example.simularemap.domain.Location;
import com.example.simularemap.repository.dbRepo.LocationsDbRepo;

import java.util.List;

public class LocationService {
    private final LocationsDbRepo locationsDbRepo;

    public LocationService(LocationsDbRepo locationsDbRepo) {
        this.locationsDbRepo = locationsDbRepo;
    }

    public List<Location> findAll() {
        return locationsDbRepo.findAll();
    }
}
