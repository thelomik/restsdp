package com.globallogic.vehicle.registry.service;

import com.globallogic.vehicle.registry.controller.VehicleSO;
import com.globallogic.vehicle.registry.entities.Vehicle;
import com.globallogic.vehicle.registry.exceptions.RegistryResourceNotFound;
import com.globallogic.vehicle.registry.repository.RegistryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegistryService {

    @Autowired
    private RegistryRepository registryRepository;

    @Autowired
    protected ModelMapper modelMapper;

    public RegistryService(RegistryRepository repository, ModelMapper model){
        registryRepository = repository;
        modelMapper = model;
    }

    public VehicleSO get(String vin) {
        Vehicle found = registryRepository.findByVin(vin);

        if (found == null) {
            throw new RegistryResourceNotFound("Vehicle with given VIN does not exist.");
        }

        return modelMapper.map(found, VehicleSO.class);
    }

    public VehicleSO create(VehicleSO so) {

        Vehicle vehicle = modelMapper.map(so, Vehicle.class);
        return modelMapper.map(registryRepository.save(vehicle), VehicleSO.class);
    }

    public VehicleSO remove(String vin){
        Vehicle found = registryRepository.findByVin(vin);
        Vehicle vehicleToReturn = found;
        if(found == null){
            throw new RegistryResourceNotFound("Vehicle with given VIN does not exist.");
        }
        else{
            registryRepository.delete(found);
            return modelMapper.map(vehicleToReturn, VehicleSO.class);
        }
    }

    public List<VehicleSO> show(){
        List<VehicleSO> vehicles = new ArrayList<VehicleSO>();
        for ( Vehicle vehicle: registryRepository.findAll()) {
            vehicles.add(modelMapper.map(vehicle, VehicleSO.class));
        }
        return vehicles;
    }

    public VehicleSO edit(VehicleSO vehicle,String vin){
        Vehicle found = registryRepository.findByVin(vin);
        if(found == null){
            throw new RegistryResourceNotFound("Vehicle with given Vin does not exist.");
        }
        else
        {
            found.setModel(vehicle.getModel());
            found.setBrand(vehicle.getBrand());
            found.setProductionYear(vehicle.getProductionYear());
            found.setVin(vehicle.getVin());
            return modelMapper.map(registryRepository.save(found), VehicleSO.class);
        }
    }
}