package com.hotel.restapi.service;

import com.hotel.restapi.model.Reservation;

import java.util.Set;

public interface ReservationService {
    Set<Reservation> findAll();

    Reservation findById(long id);

    Reservation save(Reservation reservation);

    void deleteById(long id);

    void deleteAll();
}
