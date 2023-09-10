package com.hotel.restapi.service;

import com.hotel.restapi.model.Reservation;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class ReservationService {

    protected static Set<Reservation> reservations = new HashSet<>();
    private static Integer id = 0;

    private static Integer room = 0;

    public Set<Reservation> findAll() {
        return reservations;
    }

    public Reservation findById(long id) {
        return reservations.stream().filter(reservation -> id == reservation.getId()).findAny().orElse(null);
    }

    public Reservation save(Reservation reservation) {
        // update Reservation if already exist
        if (reservation.getId() != null && reservation.getId() != 0) {
            long _id = reservation.getId();

            this.deleteById(_id);

            reservations.add(reservation);

            return reservation;
        }
        else {
            reservation.setId(++id);
            reservation.setRoomNumber(++room);
        }

        // create new Reservation
        reservations.add(reservation);
        return reservation;
    }

    public void deleteById(long id) {
        reservations.removeIf(reservation -> id == reservation.getId());
    }

    public void deleteAll() {
        reservations.removeAll(reservations);
    }

}
