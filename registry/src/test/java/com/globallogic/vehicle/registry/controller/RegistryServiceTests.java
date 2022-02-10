package com.globallogic.vehicle.registry.controller;

import com.globallogic.vehicle.registry.entities.Vehicle;
import com.globallogic.vehicle.registry.exceptions.RegistryResourceNotFound;
import com.globallogic.vehicle.registry.repository.RegistryRepository;
import com.globallogic.vehicle.registry.service.RegistryService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RegistryServiceTests {

    private Vehicle vehicleTest;
    private VehicleSO vehicleSoTest;

    protected static ModelMapper model = new ModelMapper();

    @Mock
    RegistryRepository repository;

    @InjectMocks
    RegistryService service;

    @BeforeAll
    public static void setModel() {
        model.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    }

    @BeforeEach
    public void setVehicle() {
        service = new RegistryService(repository, model);

        vehicleTest = new Vehicle();
        vehicleTest.setBrand("test1");
        vehicleTest.setModel("test2");
        vehicleTest.setProductionYear(1990);
        vehicleTest.setVin("test3");

        vehicleSoTest = new VehicleSO();
        vehicleSoTest.setBrand("test1");
        vehicleSoTest.setModel("test2");
        vehicleSoTest.setProductionYear(1990);
        vehicleSoTest.setVin("test3");
    }

    @Test
    public void testSearchByVin() {
        doReturn(vehicleTest).when(repository).findByVin("test3");
        VehicleSO vehicleSO = service.get("test3");

        assertThat(vehicleSO).isEqualTo(vehicleSoTest);
    }

    @Test
    public void testNotExistInDatabase() {
        assertThatThrownBy(() -> service.get("testvin"))
                .isInstanceOf(RegistryResourceNotFound.class)
                .hasMessageContaining("Vehicle with given VIN does not exist.");

    }

    @Test
    public void testCreate() {
        doReturn(vehicleTest).when(repository).save(ArgumentMatchers.any(Vehicle.class));
        assertThat(service.create(vehicleSoTest)).isEqualTo(vehicleSoTest);
    }

    @Test
    public void testRemove() {
        doReturn(vehicleTest).when(repository).findByVin("test3");
        VehicleSO vehicleSO = service.remove("test3");

        assertThat(model.map(vehicleTest, VehicleSO.class)).isEqualTo(vehicleSO);
    }



}
