package com.evcharging.system.service;

import com.evcharging.system.repository.BookingRepository;
import com.evcharging.system.repository.ChargingSessionRepository;
import com.evcharging.system.repository.ChargingSlotRepository;
import com.evcharging.system.repository.ChargingStationRepository;
import com.evcharging.system.repository.UserRepository;
import com.evcharging.system.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class StatisticsService {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ChargingStationRepository chargingStationRepository;
    private final ChargingSlotRepository chargingSlotRepository;
    private final BookingRepository bookingRepository;
    private final ChargingSessionRepository chargingSessionRepository;

    public StatisticsService(
            UserRepository userRepository,
            VehicleRepository vehicleRepository,
            ChargingStationRepository chargingStationRepository,
            ChargingSlotRepository chargingSlotRepository,
            BookingRepository bookingRepository,
            ChargingSessionRepository chargingSessionRepository) {

        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.chargingStationRepository = chargingStationRepository;
        this.chargingSlotRepository = chargingSlotRepository;
        this.bookingRepository = bookingRepository;
        this.chargingSessionRepository = chargingSessionRepository;
    }

    public Map<String, Long> getStatistics() {

        Map<String, Long> statistics = new LinkedHashMap<>();

        statistics.put("totalUsers", userRepository.count());
        statistics.put("totalVehicles", vehicleRepository.count());
        statistics.put("totalChargingStations", chargingStationRepository.count());
        statistics.put("totalChargingSlots", chargingSlotRepository.count());
        statistics.put("totalBookings", bookingRepository.count());
        statistics.put("totalChargingSessions", chargingSessionRepository.count());

        return statistics;
    }
}