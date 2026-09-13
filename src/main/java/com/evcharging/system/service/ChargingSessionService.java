package com.evcharging.system.service;

import com.evcharging.system.entity.Booking;
import com.evcharging.system.entity.ChargingSession;
import com.evcharging.system.repository.BookingRepository;
import com.evcharging.system.repository.ChargingSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChargingSessionService {

    private final ChargingSessionRepository chargingSessionRepository;
    private final BookingRepository bookingRepository;
    private final ChargingCalculator chargingCalculator;
    private final ChargingSessionMonitor chargingSessionMonitor;
    private final ChargingDataExporter chargingDataExporter;

    public ChargingSessionService(
            ChargingSessionRepository chargingSessionRepository,
            BookingRepository bookingRepository,
            ChargingCalculator chargingCalculator,
            ChargingSessionMonitor chargingSessionMonitor,
            ChargingDataExporter chargingDataExporter) {

        this.chargingSessionRepository = chargingSessionRepository;
        this.bookingRepository = bookingRepository;
        this.chargingCalculator = chargingCalculator;
        this.chargingSessionMonitor = chargingSessionMonitor;
        this.chargingDataExporter = chargingDataExporter;
    }

    public ChargingSession createSession(ChargingSession session) {

        if (session.getBooking() == null
                || session.getBooking().getId() == null) {

            throw new ResourceNotFoundException("Booking is required");
        }

        Booking booking = bookingRepository.findById(
                session.getBooking().getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException("Booking not found"));

        if (session.getStartTime() == null
                || session.getStartTime().isBlank()) {

            throw new InvalidBookingTimeException(
                    "Start time is required"
            );
        }

        if (session.getEnergyConsumed() < 0) {
            throw new IllegalArgumentException(
                    "Energy consumed cannot be negative"
            );
        }

        session.setBooking(booking);

        if (session.getStatus() == null
                || session.getStatus().isBlank()) {

            session.setStatus("ACTIVE");
        }

        return chargingSessionRepository.save(session);
    }

    public List<ChargingSession> getAllSessions() {
        return chargingSessionRepository.findAll();
    }

    public Optional<ChargingSession> getSessionById(Long id) {
        return chargingSessionRepository.findById(id);
    }

    public ChargingSession startSession(Long id) {

        ChargingSession session = chargingSessionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Charging session not found"
                        ));

        if ("COMPLETED".equalsIgnoreCase(session.getStatus())) {
            throw new IllegalStateException(
                    "Charging session is already completed"
            );
        }

        session.setStartTime(LocalDateTime.now().toString());
        session.setStatus("ACTIVE");

        chargingSessionMonitor.monitorSession(session.getId());

        return chargingSessionRepository.save(session);
    }

    public ChargingSession completeSession(
            Long id,
            double energyConsumed) {

        ChargingSession session = chargingSessionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Charging session not found"
                        ));

        if (energyConsumed < 0) {
            throw new IllegalArgumentException(
                    "Energy consumed cannot be negative"
            );
        }

        if (!"ACTIVE".equalsIgnoreCase(session.getStatus())) {
            throw new IllegalStateException(
                    "Charging session must be ACTIVE before completion"
            );
        }

        session.setEndTime(LocalDateTime.now().toString());
        session.setEnergyConsumed(energyConsumed);
        session.setStatus("COMPLETED");

        ChargingSession savedSession =
                chargingSessionRepository.save(session);

        String exportData =
                "Charging Session ID: " + savedSession.getId()
                + "\nStart Time: " + savedSession.getStartTime()
                + "\nEnd Time: " + savedSession.getEndTime()
                + "\nEnergy Consumed: "
                + savedSession.getEnergyConsumed()
                + "\nStatus: " + savedSession.getStatus();

        chargingDataExporter.exportData(exportData);

        return savedSession;
    }

    public ChargingSession updateSession(
            Long id,
            ChargingSession updatedSession) {

        ChargingSession session = chargingSessionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Charging session not found"
                        ));

        session.setStartTime(updatedSession.getStartTime());
        session.setEndTime(updatedSession.getEndTime());
        session.setEnergyConsumed(updatedSession.getEnergyConsumed());
        session.setStatus(updatedSession.getStatus());

        if (updatedSession.getBooking() != null
                && updatedSession.getBooking().getId() != null) {

            Booking booking = bookingRepository.findById(
                    updatedSession.getBooking().getId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Booking not found"
                    ));

            session.setBooking(booking);
        }

        return chargingSessionRepository.save(session);
    }

    public void deleteSession(Long id) {

        if (!chargingSessionRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Charging session not found"
            );
        }

        chargingSessionRepository.deleteById(id);
    }

    public double calculateSessionCost(double energyConsumed) {

        if (energyConsumed < 0) {
            throw new IllegalArgumentException(
                    "Energy consumed cannot be negative"
            );
        }

        return chargingCalculator.calculateCost(energyConsumed);
    }
}