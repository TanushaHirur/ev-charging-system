package com.evcharging.system.service;

import com.evcharging.system.entity.Booking;
import com.evcharging.system.entity.ChargingSlot;
import com.evcharging.system.entity.User;
import com.evcharging.system.entity.Vehicle;
import com.evcharging.system.repository.BookingRepository;
import com.evcharging.system.repository.ChargingSlotRepository;
import com.evcharging.system.repository.UserRepository;
import com.evcharging.system.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ChargingSlotRepository chargingSlotRepository;

    public BookingService(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            VehicleRepository vehicleRepository,
            ChargingSlotRepository chargingSlotRepository) {

        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.chargingSlotRepository = chargingSlotRepository;
    }

    public Booking createBooking(Booking booking) {

        // Validate booking time
        if (booking.getStartTime() == null || booking.getEndTime() == null) {
            throw new InvalidBookingTimeException(
                    "Start time and end time are required"
            );
        }

        if (booking.getStartTime().compareTo(booking.getEndTime()) >= 0) {
            throw new InvalidBookingTimeException(
                    "Start time must be before end time"
            );
        }

        // Validate user
        if (booking.getUser() == null || booking.getUser().getId() == null) {
            throw new ResourceNotFoundException("User is required");
        }

        User user = userRepository.findById(booking.getUser().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // Validate vehicle
        if (booking.getVehicle() == null || booking.getVehicle().getId() == null) {
            throw new ResourceNotFoundException("Vehicle is required");
        }

        Vehicle vehicle = vehicleRepository.findById(booking.getVehicle().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found"));

        // Validate charging slot
        if (booking.getChargingSlot() == null
                || booking.getChargingSlot().getId() == null) {

            throw new SlotUnavailableException(
                    "Charging slot is required"
            );
        }

        ChargingSlot chargingSlot =
                chargingSlotRepository.findById(
                        booking.getChargingSlot().getId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException("Charging slot not found"));

        // Check slot status
        if (chargingSlot.getStatus() == null
                || !chargingSlot.getStatus().equalsIgnoreCase("AVAILABLE")) {

            throw new SlotUnavailableException(
                    "Charging slot is not available"
            );
        }

        // Check for overlapping bookings
        Long slotId = chargingSlot.getId();

        List<Booking> conflictingBookings =
                bookingRepository
                        .findByChargingSlotIdAndStartTimeLessThanAndEndTimeGreaterThan(
                                slotId,
                                booking.getEndTime(),
                                booking.getStartTime()
                        );

        if (!conflictingBookings.isEmpty()) {
            throw new BookingConflictException(
                    "Charging slot is already booked for this time"
            );
        }

        // Set managed database entities
        booking.setUser(user);
        booking.setVehicle(vehicle);
        booking.setChargingSlot(chargingSlot);

        // Set default status
        if (booking.getStatus() == null || booking.getStatus().isBlank()) {
            booking.setStatus("CONFIRMED");
        }

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking updateBooking(Long id, Booking updatedBooking) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        if (updatedBooking.getStartTime() == null
                || updatedBooking.getEndTime() == null) {

            throw new InvalidBookingTimeException(
                    "Start time and end time are required"
            );
        }

        if (updatedBooking.getStartTime()
                .compareTo(updatedBooking.getEndTime()) >= 0) {

            throw new InvalidBookingTimeException(
                    "Start time must be before end time"
            );
        }

        booking.setStartTime(updatedBooking.getStartTime());
        booking.setEndTime(updatedBooking.getEndTime());
        booking.setStatus(updatedBooking.getStatus());

        if (updatedBooking.getUser() != null
                && updatedBooking.getUser().getId() != null) {

            User user = userRepository.findById(
                    updatedBooking.getUser().getId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

            booking.setUser(user);
        }

        if (updatedBooking.getVehicle() != null
                && updatedBooking.getVehicle().getId() != null) {

            Vehicle vehicle = vehicleRepository.findById(
                    updatedBooking.getVehicle().getId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException("Vehicle not found"));

            booking.setVehicle(vehicle);
        }

        if (updatedBooking.getChargingSlot() != null
                && updatedBooking.getChargingSlot().getId() != null) {

            ChargingSlot slot = chargingSlotRepository.findById(
                    updatedBooking.getChargingSlot().getId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException("Charging slot not found"));

            booking.setChargingSlot(slot);
        }

        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {

        if (!bookingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking not found");
        }

        bookingRepository.deleteById(id);
    }
}