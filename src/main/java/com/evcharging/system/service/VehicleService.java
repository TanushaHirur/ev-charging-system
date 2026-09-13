package com.evcharging.system.service;

import com.evcharging.system.entity.Vehicle;
import com.evcharging.system.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found"));

        vehicle.setRegistrationNumber(
                updatedVehicle.getRegistrationNumber()
        );
        vehicle.setModel(updatedVehicle.getModel());
        vehicle.setBatteryCapacity(
                updatedVehicle.getBatteryCapacity()
        );

        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {

        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle not found");
        }

        vehicleRepository.deleteById(id);
    }
}