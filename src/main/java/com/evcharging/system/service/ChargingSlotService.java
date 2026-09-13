package com.evcharging.system.service;

import com.evcharging.system.entity.ChargingSlot;
import com.evcharging.system.repository.ChargingSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChargingSlotService {

    private final ChargingSlotRepository chargingSlotRepository;

    public ChargingSlotService(ChargingSlotRepository chargingSlotRepository) {
        this.chargingSlotRepository = chargingSlotRepository;
    }

    public ChargingSlot createSlot(ChargingSlot slot) {
        return chargingSlotRepository.save(slot);
    }

    public List<ChargingSlot> getAllSlots() {
        return chargingSlotRepository.findAll();
    }

    public ChargingSlot getSlotById(Long id) {
        return chargingSlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Charging slot not found"));
    }

    public ChargingSlot updateSlot(Long id, ChargingSlot updatedSlot) {

        ChargingSlot slot = chargingSlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Charging slot not found"));

        slot.setSlotNumber(updatedSlot.getSlotNumber());
        slot.setStatus(updatedSlot.getStatus());

        return chargingSlotRepository.save(slot);
    }

    public void deleteSlot(Long id) {

        if (!chargingSlotRepository.existsById(id)) {
            throw new ResourceNotFoundException("Charging slot not found");
        }

        chargingSlotRepository.deleteById(id);
    }
}