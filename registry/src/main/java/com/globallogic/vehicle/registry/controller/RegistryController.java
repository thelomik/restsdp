package com.globallogic.vehicle.registry.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class RegistryController {

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello world!!";
    }
}
