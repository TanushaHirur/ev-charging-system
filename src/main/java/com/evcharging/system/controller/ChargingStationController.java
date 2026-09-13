package com.evcharging.system.controller;

import com.evcharging.system.entity.ChargingStation;
import com.evcharging.system.service.ChargingStationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class ChargingStationController {

    private final ChargingStationService chargingStationService;

    public ChargingStationController(ChargingStationService chargingStationService) {
        this.chargingStationService = chargingStationService;
    }

    @PostMapping
    public ResponseEntity<ChargingStation> createStation(
            @RequestBody ChargingStation station) {

        ChargingStation createdStation =
                chargingStationService.createStation(station);

        return new ResponseEntity<>(createdStation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ChargingStation>> getAllStations() {
        return ResponseEntity.ok(
                chargingStationService.getAllStations()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargingStation> getStationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                chargingStationService.getStationById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChargingStation> updateStation(
            @PathVariable Long id,
            @RequestBody ChargingStation updatedStation) {

        ChargingStation station =
                chargingStationService.updateStation(id, updatedStation);

        return ResponseEntity.ok(station);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(
            @PathVariable Long id) {

        chargingStationService.deleteStation(id);

        return ResponseEntity.noContent().build();
    }
}