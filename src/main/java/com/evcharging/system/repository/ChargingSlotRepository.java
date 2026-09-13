package com.evcharging.system.repository;

import com.evcharging.system.entity.ChargingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargingSlotRepository extends JpaRepository<ChargingSlot, Long> {
}