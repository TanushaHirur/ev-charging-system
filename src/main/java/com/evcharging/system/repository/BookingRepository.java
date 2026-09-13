package com.evcharging.system.repository;

import com.evcharging.system.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByChargingSlotIdAndStartTimeLessThanAndEndTimeGreaterThan(
            Long chargingSlotId,
            String endTime,
            String startTime
    );
}