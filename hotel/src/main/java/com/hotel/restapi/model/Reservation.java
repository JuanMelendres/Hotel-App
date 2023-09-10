package com.hotel.restapi.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {

    private Integer id;

    private String clientFullName;

    private Integer roomNumber;

    private List<LocalDate> reservationDates;

    public Reservation(String clientFullName, List<LocalDate> reservationDates) {
        this.clientFullName = clientFullName;
        this.reservationDates = reservationDates;
    }
}
