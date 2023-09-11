package com.hotel.restapi.controller;

import com.hotel.restapi.model.Reservation;
import com.hotel.restapi.service.ReservationServiceImpl;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Reservation Controller", description = "Reservation Controller management APIs")
public class ReservationController {

    private final ReservationServiceImpl reservationService;

    @Operation(
            summary = "Retrieve all Reservations",
            description = "Get all Reservations object. The response is List<Reservation>.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Reservation.class),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        try {
            List<Reservation> reservations = new ArrayList<>(reservationService.findAll());
            if (reservations.isEmpty()) {
                log.info("Empty Reservations List");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            log.info("Successfully get all Reservations");
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error: {}", e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Retrieve a Reservation by Id",
            description = "Get a Reservation object by specifying its id. The response is Reservation object with id, " +
                    "client name, reservation dates and room number.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Reservation.class),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/reservation/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") long id) {
        try {
            Reservation reservation = reservationService.findById(id);

            if (reservation != null) {
                log.info("Successfully get Reservation with id: {}", id);
                return new ResponseEntity<>(reservation, HttpStatus.OK);
            } else {
                log.warn("Reservation with id: {} not found", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error: {}", e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(
            summary = "Create a new Reservation",
            description = "Create a Reservation object. The response is Reservation object with id, " +
                    "client name, reservation dates and room number.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Reservation.class),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/reservation")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        try {
            Reservation _reservation = reservationService
                    .save(new Reservation(reservation.getClientFullName(), reservation.getReservationDates()));
            log.info("Successfully created new Reservation");
            return new ResponseEntity<>(_reservation, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error: {}", e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Update a Reservation by Id",
            description = "Update a Reservation object by specifying its id. The response is Reservation object with id, " +
                    "client name, reservation dates and room number.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Reservation.class),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "406", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PutMapping("/reservation/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable("id") long id, @RequestBody Reservation reservation) {
        try {
            Reservation _reservation = reservationService.findById(id);

            if (_reservation != null) {
                _reservation.setClientFullName(reservation.getClientFullName());
                _reservation.setReservationDates(reservation.getReservationDates());
                _reservation.setRoomNumber(reservation.getRoomNumber());

                Reservation update = reservationService.save(_reservation);

                if (update != null) {
                    log.info("Successfully updated Reservation with id: {}", id);
                    return new ResponseEntity<>( reservationService.save(_reservation), HttpStatus.OK);
                }
                else {
                    log.warn("Room number: {} is already in use for reservation dates: {}", reservation.getRoomNumber(), reservation.getReservationDates());
                    return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                }
            } else {
                log.info("Not found Reservation with id: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error: {}", e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Delete a Reservation by Id",
            description = "Delete a Reservation object by specifying its id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<HttpStatus> deleteReservation(@PathVariable("id") long id) {
        try {
            reservationService.deleteById(id);
            log.info("Successfully deleted Reservation with id: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error: {}", e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Delete all Reservations",
            description = "Delete all Reservations.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/reservations")
    public ResponseEntity<HttpStatus> deleteAllReservations() {
        try {
            reservationService.deleteAll();
            log.info("Successfully deleted all Reservations");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error: {}", e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
