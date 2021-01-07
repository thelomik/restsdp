package com.globallogic.vehicle.registry.controller;

import com.globallogic.vehicle.registry.service.RegistryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registry")
@Slf4j
@Api("Article Management API")
public class RegistryController {

    @Autowired
    private RegistryService registryService;

    @ApiOperation(value = "Returns a specified entity.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entity found") })
    @GetMapping(path = "/{vin}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleSO get(@PathVariable(name = "vin") String vin) {
        VehicleSO vehicleSO = registryService.get(vin);

        log.info("Returning vehicle={}", vehicleSO);
        return vehicleSO;
    }
}
