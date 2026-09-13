package com.evcharging.system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String startTime;
    private String endTime;
    private String status;

    @ManyToOne
    private User user;

    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    private ChargingSlot chargingSlot;

    public Booking() {
    }

    public Booking(String startTime, String endTime, String status,
                   User user, Vehicle vehicle, ChargingSlot chargingSlot) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.user = user;
        this.vehicle = vehicle;
        this.chargingSlot = chargingSlot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ChargingSlot getChargingSlot() {
        return chargingSlot;
    }

    public void setChargingSlot(ChargingSlot chargingSlot) {
        this.chargingSlot = chargingSlot;
    }
}