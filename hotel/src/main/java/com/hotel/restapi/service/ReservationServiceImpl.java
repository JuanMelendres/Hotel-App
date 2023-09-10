package com.hotel.restapi.service;

import com.hotel.restapi.model.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

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

        if (!reservations.isEmpty()) {
            for (Reservation res : reservations) {
                if (Objects.equals(res.getRoomNumber(), reservation.getRoomNumber())
                        && Objects.equals(res.getReservationDates(), reservation.getReservationDates())) {
                    log.info("Room already in use");
                    return null;
                }
            }
            if (reservation.getId() != null && reservation.getId() != 0) {
                // update Reservation if already exist
                long _id = reservation.getId();

                this.deleteById(_id);

                reservations.add(reservation);
            }
        }
        else {
            // create new Reservation
            reservation.setId(++id);
            reservation.setRoomNumber(++room);
            reservations.add(reservation);
        }

        return reservation;
    }

    public void deleteById(long id) {
        reservations.removeIf(reservation -> id == reservation.getId());
    }

    public void deleteAll() {
        reservations.removeAll(reservations);
    }

}
