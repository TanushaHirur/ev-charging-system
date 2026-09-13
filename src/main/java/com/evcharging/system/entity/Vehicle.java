package com.evcharging.system.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vehicle {

   @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    private String registrationNumber;
    private String model;
    private double batteryCapacity;

    public Vehicle() {
    }

    public Vehicle(String registrationNumber, String model, double batteryCapacity) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.batteryCapacity = batteryCapacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }
}