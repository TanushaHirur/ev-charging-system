package com.evcharging.system.service;

import org.springframework.stereotype.Component;

@Component
public class ACChargingCalculator implements ChargingCalculator {

    @Override
    public double calculateCost(double energyConsumed) {
        return energyConsumed * 0.35;
    }

    @Override
    public double calculateChargingTime(double energyConsumed, double chargingPower) {
        if (chargingPower <= 0) {
            throw new IllegalArgumentException("Charging power must be greater than zero");
        }

        return energyConsumed / chargingPower;
    }
}