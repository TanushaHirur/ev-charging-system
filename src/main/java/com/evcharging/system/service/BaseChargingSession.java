package com.evcharging.system.service;

public abstract class BaseChargingSession {

    protected double energyConsumed;

    public BaseChargingSession(double energyConsumed) {
        this.energyConsumed = energyConsumed;
    }

    public double getEnergyConsumed() {
        return energyConsumed;
    }

    public abstract double calculateCost();
}