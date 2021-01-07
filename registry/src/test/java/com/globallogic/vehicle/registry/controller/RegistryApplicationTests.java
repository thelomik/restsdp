package com.globallogic.vehicle.registry.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegistryApplicationTests {

    protected ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void testCreateClub_positive_ok() throws Exception {
        mockMvc
                .perform(get("/vehicles/vintest").header("Content-Type", "application/json"))
                .andDo(print()) //
                .andExpect(status().isOk()) //
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) //
                .andExpect(jsonPath("$.productionYear", Matchers.is(2000))) //
                .andExpect(jsonPath("$.brand", Matchers.is("VW"))) //
                .andExpect(jsonPath("$.model", Matchers.is("Polo"))) //
                .andExpect(jsonPath("$.vin", Matchers.is("vintest")));
    }

}