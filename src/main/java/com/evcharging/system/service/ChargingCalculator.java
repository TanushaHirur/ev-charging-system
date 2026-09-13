package com.evcharging.system.service;

public interface ChargingCalculator {

    double calculateCost(double energyConsumed);

    double calculateChargingTime(double energyConsumed, double chargingPower);
}