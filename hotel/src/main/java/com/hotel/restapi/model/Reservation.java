package com.hotel.restapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Reservation {

    @Setter
    private Integer id;

    @Setter
    private String clientFullName;

    @Setter
    private Integer roomNumber;

    @Setter
    private List<LocalDate> reservationDates;

}
