package com.globallogic.vehicle.registry.service;

import com.globallogic.vehicle.registry.controller.VehicleSO;
import com.globallogic.vehicle.registry.entities.Vehicle;
import com.globallogic.vehicle.registry.exceptions.RegistryResourceNotFound;
import com.globallogic.vehicle.registry.repository.RegistryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistryService {

    @Autowired
    private RegistryRepository registryRepository;

    @Autowired
    protected ModelMapper modelMapper;

    public VehicleSO get(String vin) {
        Vehicle found = registryRepository.findByVin(vin);

        if (found == null) {
            throw new RegistryResourceNotFound("Vehicle with given VIN does not exist.");
        }

//        VehicleSO vehicleSO = new VehicleSO();
//        vehicleSO.setVin(found.getVin());
//        vehicleSO.setProductionYear(found.getProductionYear());
//        vehicleSO.setModel(found.getModel());
//        vehicleSO.setBrand(found.getBrand());

        return modelMapper.map(found, VehicleSO.class);
    }

    public VehicleSO create(VehicleSO so) {
        Vehicle vehicle = modelMapper.map(so, Vehicle.class);
//        Vehicle vehicle = new Vehicle();
//        vehicle.setBrand(so.getBrand());
//        vehicle.setModel(so.getModel());
//        vehicle.setProductionYear(so.getProductionYear());
//        vehicle.setVin(so.getVin());

        Vehicle save = registryRepository.save(vehicle);

//        VehicleSO created = new VehicleSO();
//        created.setBrand(save.getBrand());
//        created.setModel(save.getModel());
//        created.setProductionYear(save.getProductionYear());
//        created.setId(save.getId());
//        created.setVin(save.getVin());

        return modelMapper.map(save, VehicleSO.class);
    }
}