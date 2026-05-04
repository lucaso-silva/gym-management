package com.lucas.gym_management.gym.infrastructure.adapters.inbound.rest;

import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymOutput;
import com.lucas.gym_management.gym.factory.GymFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
class GymControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/gyms";

    @Test
    void shouldCreateAGym_whenDataIsValid() throws Exception {
        var createGymInput = GymFactory.buildCreateGymInput();

        var result = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createGymInput)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Any-gym-name"))
                .andExpect(jsonPath("$.phone").value("any-phone-number"))
                .andReturn();

        var responseBody = result.getResponse().getContentAsString();
        CreateGymOutput output = objectMapper.readValue(responseBody, CreateGymOutput.class);
        var gymId = output.id();

        mockMvc.perform(get(BASE_URL + "/" + gymId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(createGymInput.name()))
                .andExpect(jsonPath("$.phone").value(createGymInput.phone()));
    }

    @Test
    @Sql(scripts = "/sql/gym/gyms-setup.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnAGym_whenGymIdIsValid() throws Exception {
        var gymId = "11111111-1111-1111-1111-111111111111";

        mockMvc.perform(get(BASE_URL + "/" + gymId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(gymId))
                .andExpect(jsonPath("$.name").value("First-gym-name"))
                .andExpect(jsonPath("$.phone").value("first-gym-phone-num"))
                .andExpect(jsonPath("$.members").value(0))
                .andExpect(jsonPath("$.activeClasses").value(0))
                .andExpect(jsonPath("$.address.street").value("First-gym-street-name"))
                .andExpect(jsonPath("$.address.number").value("any-number"))
                .andExpect(jsonPath("$.address.neighborhood").value("any-neighborhood"))
                .andExpect(jsonPath("$.address.city").value("any-city"))
                .andExpect(jsonPath("$.address.state").value("any-state"));
    }

    @Test
    @Sql(scripts = "/sql/gym/gyms-setup.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnAListOfGyms_whenThereAreGyms() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @Sql(scripts = "/sql/clear-setup.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnEmptyList_whenThereAreNoGyms() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Sql(scripts = "/sql/gym/gyms-setup.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnNotFound_whenGymIdIsInvalid() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound());

    }
}