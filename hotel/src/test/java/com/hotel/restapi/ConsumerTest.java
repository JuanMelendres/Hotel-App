package com.hotel.restapi;

import com.hotel.restapi.model.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConsumerTest {

    // define few firstNames and lastNames
    private static String[] firstNames = {"John", "Emma", "Olivia", "Ava", "Isabella", "Sophia", "Robin"};

    private static String[] lastNames = {"Doe", "Smith", "Johnson", "Williams", "Jones", "Brown", "Hood"};

    // define few firstNames and lastNames
    private static String[] checkIn = {"2023-10-15", "2023-10-20", "2023-10-25", "2023-10-30", "2023-11-01", "2023-11-05", "2023-11-10"};

    private static String[] checkOut = {"2023-10-18", "2023-10-23", "2023-10-28", "2023-10-03", "2023-10-04", "2023-10-08", "2023-10-13"};

    private static Integer id = 0;

    private static Integer room = 0;

    // create an obj
    private static Random random = new Random();

    protected Reservation buildNewReservation() {
        List<LocalDate> reservationDates = new ArrayList<>();

        reservationDates.add(LocalDate.parse(checkIn[random.nextInt(checkIn.length)]));
        reservationDates.add(LocalDate.parse(checkOut[random.nextInt(checkOut.length)]));

        String fullName = firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];

        return new Reservation(++id, fullName, ++room, reservationDates);
    }
}
