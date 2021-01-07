package com.globallogic.vehicle.registry.service;

import com.globallogic.vehicle.registry.controller.VehicleSO;
import com.globallogic.vehicle.registry.entities.Vehicle;
import com.globallogic.vehicle.registry.repository.RegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistryService {

    @Autowired
    private RegistryRepository registryRepository;

    public VehicleSO get(String vin) {
        Vehicle found = registryRepository.findByVin(vin);

        if (found == null) {
            throw new IllegalArgumentException("No vehicle with given VIN.");
        }

        VehicleSO vehicleSO = new VehicleSO();
        vehicleSO.setVin(found.getVin());
        vehicleSO.setProductionYear(found.getProductionYear());
        vehicleSO.setModel(found.getModel());
        vehicleSO.setBrand(found.getBrand());

        return vehicleSO;
    }

}