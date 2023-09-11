package com.hotel.restapi.controller;

import com.hotel.restapi.ConsumerTest;
import com.hotel.restapi.model.Reservation;
import com.hotel.restapi.service.ReservationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationControllerTests extends ConsumerTest  {

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private ReservationServiceImpl reservationService;

    @Test
    public void getAllReservationsEmptyList_should_return_reservations_list() {
        // When
        ResponseEntity<List<Reservation>> response = this.reservationController.getAllReservations();
        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void getReservationByIdNotFound_should_return_reservation() {
        // When
        ResponseEntity<Reservation> response = this.reservationController.getReservationById(1);
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void createReservation_save_should_insert_new_reservation() {
        // Given
        Reservation reservation = buildNewReservation();
        // When
        ResponseEntity<Reservation> response = this.reservationController.createReservation(reservation);
        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void updateReservation_update_not_existing_reservation() {
        // Given
        Reservation existingReservation = buildNewReservation();
        // When
        ResponseEntity<Reservation> response = this.reservationController.updateReservation(1, existingReservation);
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteById_should_delete_reservation() {
        // When
        ResponseEntity<HttpStatus> response = this.reservationController.deleteReservation(5);
        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteAllReservations_should_delete_all_reservations() {
        // When
        ResponseEntity<HttpStatus> response = this.reservationController.deleteAllReservations();
        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
