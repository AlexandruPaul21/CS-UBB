package com.example.simularemap.service;

import com.example.simularemap.domain.Reservation;
import com.example.simularemap.repository.dbRepo.ReservationDbRepo;
import com.example.simularemap.utils.Observable;

public class ReservationService extends Observable{
    private final ReservationDbRepo reservationDbRepo;

    public ReservationService(ReservationDbRepo reservationDbRepo) {
        this.reservationDbRepo = reservationDbRepo;
    }

    public Reservation save(Reservation reservation) {
        Reservation reservation1 = reservationDbRepo.save(reservation);
        notifyObs();
        return reservation1;
    }

    public Double getLowestId() {
        return reservationDbRepo.getValidId();
    }
}
