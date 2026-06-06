package com.lucas.gym_management.gymclass.infrastructure.adapters.inbound.rest;

import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassOutput;
import com.lucas.gym_management.gymclass.factory.GymClassFactory;
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

import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
@Sql(scripts = "/sql/clear-setup.sql",
executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GymClassControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/gym-classes";

    @Test
    @Sql(scripts = "/sql/gymclass/gymclass-setup.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldCreateAGymClass_whenDataIsValid() throws Exception {
        var createGymClassInput = GymClassFactory.buildCreateGymClassInput();

        var result = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createGymClassInput)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value(createGymClassInput.name()))
                .andExpect(jsonPath("$.instructorId").value(createGymClassInput.instructorId().toString()))
                .andExpect(jsonPath("$.capacity").value(createGymClassInput.capacity()))
                .andExpect(jsonPath("$.schedule.dayOfWeek").value(createGymClassInput.schedule().dayOfWeek().toString()))
                .andExpect(jsonPath("$.schedule.room").value(createGymClassInput.schedule().room()))
                .andExpect(jsonPath("$.schedule.startTime").value(createGymClassInput.schedule().startTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))))
                .andExpect(jsonPath("$.schedule.endTime").value(createGymClassInput.schedule().endTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))))
                .andReturn();

        var responseBody = result.getResponse().getContentAsString();
        CreateGymClassOutput output = objectMapper.readValue(responseBody, CreateGymClassOutput.class);
        var gymClassId = output.id();

        mockMvc.perform(get(BASE_URL+"/"+gymClassId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(createGymClassInput.name()))
                .andExpect(jsonPath("$.schedule.dayOfWeek").value(createGymClassInput.schedule().dayOfWeek().toString()))
                .andExpect(jsonPath("$.schedule.room").value(createGymClassInput.schedule().room()));
    }

    //TODO: implement error scenarios tests

}
