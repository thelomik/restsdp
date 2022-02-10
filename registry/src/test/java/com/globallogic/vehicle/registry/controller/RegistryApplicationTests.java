package com.globallogic.vehicle.registry.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.vehicle.registry.service.RegistryService;
import org.hamcrest.Matchers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistryApplicationTests {

    protected ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    protected MockMvc mockMvc;

    @Mock
    RegistryService registryService;

    @InjectMocks
    RegistryController registryController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(registryController).build();
    }

    @Test
    public void testCreateClub_positive_ok() throws Exception {
        VehicleSO testVeh = new VehicleSO();
        testVeh.setProductionYear(1990);
        testVeh.setBrand("test1");
        testVeh.setModel("test2");
        testVeh.setVin("test3");
        doReturn(testVeh).when(registryService).get("test3");

        mockMvc
                .perform(get("/vehicles/test3").header("Content-Type", "application/json"))
                .andDo(print()) //
                .andExpect(status().isOk()) //
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) //
                .andExpect(jsonPath("$.productionYear", Matchers.is(1990))) //
                .andExpect(jsonPath("$.brand", Matchers.is("test1"))) //
                .andExpect(jsonPath("$.model", Matchers.is("test2"))) //
                .andExpect(jsonPath("$.vin", Matchers.is("test3")));
    }


}