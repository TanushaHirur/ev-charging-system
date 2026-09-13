package com.evcharging.system.controller;

import com.evcharging.system.entity.ChargingSlot;
import com.evcharging.system.service.ChargingSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
public class ChargingSlotController {

    private final ChargingSlotService chargingSlotService;

    public ChargingSlotController(ChargingSlotService chargingSlotService) {
        this.chargingSlotService = chargingSlotService;
    }

    @PostMapping
    public ResponseEntity<ChargingSlot> createSlot(
            @RequestBody ChargingSlot slot) {

        ChargingSlot createdSlot = chargingSlotService.createSlot(slot);

        return new ResponseEntity<>(createdSlot, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ChargingSlot>> getAllSlots() {

        return ResponseEntity.ok(
                chargingSlotService.getAllSlots()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargingSlot> getSlotById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                chargingSlotService.getSlotById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChargingSlot> updateSlot(
            @PathVariable Long id,
            @RequestBody ChargingSlot updatedSlot) {

        return ResponseEntity.ok(
                chargingSlotService.updateSlot(id, updatedSlot)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlot(
            @PathVariable Long id) {

        chargingSlotService.deleteSlot(id);

        return ResponseEntity.noContent().build();
    }
}