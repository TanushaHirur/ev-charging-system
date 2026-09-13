package com.evcharging.system.service;

public class ACChargingSession extends BaseChargingSession {

    public ACChargingSession(double energyConsumed) {
        super(energyConsumed);
    }

    @Override
    public double calculateCost() {
        return energyConsumed * 0.35;
    }
}