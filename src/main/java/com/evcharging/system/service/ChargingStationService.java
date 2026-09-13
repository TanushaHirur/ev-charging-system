package com.evcharging.system.service;

import com.evcharging.system.entity.ChargingStation;
import com.evcharging.system.repository.ChargingStationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChargingStationService {

    private final ChargingStationRepository chargingStationRepository;

    public ChargingStationService(ChargingStationRepository chargingStationRepository) {
        this.chargingStationRepository = chargingStationRepository;
    }

    public ChargingStation createStation(ChargingStation station) {
        return chargingStationRepository.save(station);
    }

    public List<ChargingStation> getAllStations() {
        return chargingStationRepository.findAll();
    }

    public ChargingStation getStationById(Long id) {
        return chargingStationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Charging station not found"));
    }

    public ChargingStation updateStation(Long id, ChargingStation updatedStation) {

        ChargingStation station = chargingStationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Charging station not found"));

        station.setName(updatedStation.getName());
        station.setLocation(updatedStation.getLocation());
        station.setCity(updatedStation.getCity());

        return chargingStationRepository.save(station);
    }

    public void deleteStation(Long id) {

        if (!chargingStationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Charging station not found");
        }

        chargingStationRepository.deleteById(id);
    }
}