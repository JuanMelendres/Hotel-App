package com.hotel.restapi.service;

import com.hotel.restapi.ConsumerTest;
import com.hotel.restapi.model.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest extends ConsumerTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    public void findAll_should_return_reservations_list() {
        // Insert data
        this.reservationService.save(buildNewReservation());
        this.reservationService.save(buildNewReservation());
        this.reservationService.save(buildNewReservation());
        this.reservationService.save(buildNewReservation());
        // When
        Set<Reservation> reservations = this.reservationService.findAll();
        // Then
        assertEquals(4, reservations.size());
    }

    @Test
    public void findById_should_return_reservation() {
        // Insert data
        this.reservationService.save(buildNewReservation());
        // When
        Reservation reservation = this.reservationService.findById(1);
        // Then
        assertNotNull(reservation);
    }

    @Test
    public void save_should_insert_new_reservation() {
        // Given
        Reservation reservation = buildNewReservation();
        // When
        Reservation persistedReservation = this.reservationService.save(reservation);
        // Then
        assertNotNull(persistedReservation);
        assertEquals(2, persistedReservation.getId());
    }

    @Test
    public void save_should_update_existing_reservation() {
        // Given
        Reservation existingReservation = buildNewReservation();
        existingReservation.setId(3);
        // When
        Reservation updatedReservation = this.reservationService.save(existingReservation);
        // Then
        assertNotNull(updatedReservation);
        assertEquals(existingReservation.getClientFullName(), updatedReservation.getClientFullName());
    }

    @Test
    public void deleteById_should_delete_reservation() {
        // When
        this.reservationService.deleteById(5);
        Reservation reservation = this.reservationService.findById(5);
        // Then
        assertNull(reservation);
    }

    @Test
    public void deleteAllReservation_should_delete_all_reservations() {
        // When
        this.reservationService.deleteAll();
        Set<Reservation> reservations = this.reservationService.findAll();
        // Then
        assertEquals(0, reservations.size());
    }

}
